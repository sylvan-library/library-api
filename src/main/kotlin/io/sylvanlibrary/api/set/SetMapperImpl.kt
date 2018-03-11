package io.sylvanlibrary.api.set

import io.sylvanlibrary.api.util.Config
import io.sylvanlibrary.api.util.Paths
import io.sylvanlibrary.api.util.ResourceReference
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.mapper.RowMapper
import org.jdbi.v3.core.statement.StatementContext
import java.sql.ResultSet
import java.util.*

class SetMapperImpl(private val config: Config, private val connectionPool: Jdbi) : SetMapper {
  companion object {
    const val URL_TEMPLATE = "https://sylvan-library.s3.amazonaws.com/sets"
  }

  private inner class SetRowMapper : RowMapper<Set> {
    override fun map(rs: ResultSet?, ctx: StatementContext?): Set {
      val abbreviation = rs!!.getString("abbreviation")

      return Set(
          rs.getInt("id"),
          rs.getString("name"),
          getCanonicalUrl(abbreviation),
          abbreviation,
          rs.getString("block_name"),
          Optional.ofNullable(rs.getString("icon")).map { "$URL_TEMPLATE/$it" },
          Optional.ofNullable(rs.getString("logo")).map { "$URL_TEMPLATE/$it" },
          rs.getDate("release_date").toLocalDate()
      )
    }
  }

  override fun all(): List<Set> {
    return connectionPool.withHandle<List<Set>, RuntimeException> {
      it.createQuery("select b.name as block_name, s.* from sets as s join blocks as b on s.block_id = b.id")
          .map(SetRowMapper())
          .toList()
    }
  }

  override fun allForFormats(formatIds: List<Int>): Map<Int, List<ResourceReference>> {
    val result = connectionPool.withHandle<List<Map<String, Any>>, RuntimeException> {
      it.createQuery("select l.format_id, s.name, s.abbreviation from sets as s join format_to_sets as l on s.id = l.set_id where l.format_id in (<formats>)")
          .bindList("formats", formatIds)
          .mapToMap()
          .list()
    }

    return result
        .groupBy { it["format_id"] as Int }
        .mapValues {
          it.value.map { row ->
            ResourceReference(
                row["name"] as String,
                getCanonicalUrl(row["abbreviation"] as String)
            )
          }
        }
  }

  override fun get(abbreviation: String): Optional<Set> {
    val format = connectionPool.withHandle<Optional<Set>, RuntimeException> {
      it.createQuery("select b.name as block_name, s.* from sets as s join blocks as b on s.block_id = b.id where lower(abbreviation) = :abbreviation")
          .bind("abbreviation", abbreviation.toLowerCase())
          .map(SetRowMapper())
          .findFirst()
    }

    return format
  }

  private fun getCanonicalUrl(abbreviation: String): String {
    return "${config.host}/${Paths.Sets.ROOT}/${abbreviation.toLowerCase()}"
  }
}

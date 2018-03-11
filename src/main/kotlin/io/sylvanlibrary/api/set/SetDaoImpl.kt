package io.sylvanlibrary.api.set

import io.sylvanlibrary.api.util.Paths
import io.sylvanlibrary.api.util.ResourceReference
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.mapper.RowMapper
import org.jdbi.v3.core.statement.StatementContext
import java.sql.ResultSet
import java.util.*

class SetDaoImpl(private val connectionPool: Jdbi) : SetDao {
  companion object {
    const val URL_TEMPLATE = "https://sylvan-library.s3.amazonaws.com/sets"
  }

  private class SetRowMapper : RowMapper<DbSet> {
    override fun map(rs: ResultSet?, ctx: StatementContext?): DbSet {
      return DbSet(
          rs!!.getInt("id"),
          rs.getInt("block_id"),
          rs.getString("name"),
          rs.getString("abbreviation"),
          Optional.ofNullable(rs.getString("icon")).map { "$URL_TEMPLATE/$it" },
          Optional.ofNullable(rs.getString("logo")).map { "$URL_TEMPLATE/$it" },
          Optional.ofNullable(rs.getDate("release_date").toLocalDate()),
          rs.getTimestamp("created").toLocalDateTime()
      )
    }
  }

  override fun all(): List<DbSet> {
    return connectionPool.withHandle<List<DbSet>, RuntimeException> {
      it.createQuery("select * from sets")
          .map<DbSet>(SetRowMapper())
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
                buildCanonicalUrl(row["abbreviation"] as String)
            )
          }
        }
  }

  private fun buildCanonicalUrl(abbreviation: String): String {
    return "${Paths.ROOT}/${Paths.Sets.ROOT}/${abbreviation.toLowerCase()}"
  }
}

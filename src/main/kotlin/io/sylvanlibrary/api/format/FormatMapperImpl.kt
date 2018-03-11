package io.sylvanlibrary.api.format

import io.sylvanlibrary.api.set.SetMapper
import io.sylvanlibrary.api.util.Config
import io.sylvanlibrary.api.util.Paths
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.mapper.RowMapper
import org.jdbi.v3.core.statement.StatementContext
import java.sql.ResultSet
import java.util.*

class FormatMapperImpl(
    private val config: Config,
    private val connectionPool: Jdbi,
    private val setMapper: SetMapper
) : FormatMapper {
  private inner class FormatRowMapper : RowMapper<Format> {
    override fun map(rs: ResultSet?, ctx: StatementContext?): Format {
      val name = rs!!.getString("name")

      return Format(rs.getInt("id"), name, getCanonicalUrl(name))
    }
  }

  override fun all(): List<Format> {
    val formats: List<Format> = connectionPool.withHandle<List<Format>, RuntimeException> {
      it.createQuery("select * from formats")
          .map(FormatRowMapper())
          .list()
    }

    val sets = setMapper.allForFormats(formats.map(Format::id))

    formats.forEach() {
      it.legalSets = sets[it.id] ?: listOf()
    }

    return formats
  }

  override fun get(name: String): Optional<Format> {
    val format = connectionPool.withHandle<Optional<Format>, RuntimeException> {
      it.createQuery("select * from formats where lower(name) = :name")
          .bind("name", name.toLowerCase())
          .map(FormatRowMapper())
          .findFirst()
    }

    format.ifPresent {
      val sets = setMapper.allForFormats(listOf(it.id))

      it.legalSets = sets.getOrDefault(it.id, listOf())
    }

    return format
  }

  fun getCanonicalUrl(name: String): String {
    return "${config.host}/${Paths.Formats.ROOT}/${name.toLowerCase()}"
  }
}

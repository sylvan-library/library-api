package io.sylvanlibrary.api.daos

import org.jdbi.v3.core.Jdbi
import io.sylvanlibrary.api.entities.Set
import org.jdbi.v3.core.mapper.RowMapper
import org.jdbi.v3.core.statement.StatementContext
import java.sql.ResultSet
import java.util.*

class SetDao(private val pool: Jdbi) {
  companion object {
    const val ALL_QUERY = "select * from sets"
    const val ALL_FROM_FORMAT_QUERY = "select * from sets as s join format_to_sets as l on s.id = l.set_id join formats as f on l.format_id = f.id where lower(f.name) = lower(:format);"
    const val URL_TEMPLATE = "https://sylvan-library.s3.amazonaws.com/sets"
  }

  class SetMapper : RowMapper<Set> {
    override fun map(rs: ResultSet?, ctx: StatementContext?): Set {
      return Set(
          rs!!.getInt("id"),
          rs.getInt("block_id"),
          rs.getString("name"),
          rs.getString("abbreviation"),
          Optional.ofNullable(rs.getString("icon")).map { "$URL_TEMPLATE/$it" },
          Optional.ofNullable(rs.getString("keyimage")).map { "$URL_TEMPLATE/$it" },
          Optional.ofNullable(rs.getString("logo")).map { "$URL_TEMPLATE/$it" },
          Optional.ofNullable(rs.getDate("release_date")?.toLocalDate()),
          rs.getTimestamp("created").toLocalDateTime()
      )
    }
  }

  fun all(): List<Set> {
    return pool.withHandle<List<Set>, RuntimeException> { handle ->
      handle
          .createQuery(ALL_QUERY)
          .map<Set>(SetMapper())
          .list()
    }
  }

  fun allForFormat(format: String): List<Set> {
    return pool.withHandle<List<Set>, RuntimeException> { handle ->
      handle
          .createQuery(ALL_FROM_FORMAT_QUERY)
          .bind("format", format)
          .map<Set>(SetMapper())
          .list()
    }
  }
}

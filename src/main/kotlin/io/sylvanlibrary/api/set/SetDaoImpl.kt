package io.sylvanlibrary.api.set

import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.mapper.RowMapper
import org.jdbi.v3.core.statement.StatementContext
import java.sql.ResultSet
import java.util.*

class SetDao(private val pool: Jdbi) {
  companion object {
    const val URL_TEMPLATE = "https://sylvan-library.s3.amazonaws.com/sets"
  }

  class SetMapper : RowMapper<DbSet> {
    override fun map(rs: ResultSet?, ctx: StatementContext?): DbSet {
      return DbSet(
          rs!!.getInt("id"),
          rs.getInt("block_id"),
          rs.getString("name"),
          rs.getString("abbreviation"),
          Optional.ofNullable(rs.getString("icon")).map { "${URL_TEMPLATE}/$it" },
          Optional.ofNullable(rs.getString("keyimage")).map { "${URL_TEMPLATE}/$it" },
          Optional.ofNullable(rs.getString("logo")).map { "${URL_TEMPLATE}/$it" },
          Optional.ofNullable(rs.getDate("release_date")?.toLocalDate()),
          rs.getTimestamp("created").toLocalDateTime()
      )
    }
  }

  fun all(): List<DbSet> {
    return pool.withHandle<List<DbSet>, RuntimeException> { handle ->
      handle
          .createQuery(select * from sets)
          .map<DbSet>(SetMapper())
          .list()
    }
  }

  fun allForFormat(format: String): List<DbSet> {
    return pool.withHandle<List<DbSet>, RuntimeException> { handle ->
      handle
          .createQuery("select s.* from sets as s join format_to_sets as l on s.id = l.set_id join format as f on l.format_id = f.id where lower(f.name) = :format")
          .bind("format", format.toLowerCase())
          .map<DbSet>(SetMapper())
          .list()
    }
  }
}

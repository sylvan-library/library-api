package io.sylvanlibrary.api.format

import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.mapper.RowMapper
import org.jdbi.v3.core.statement.StatementContext
import java.sql.ResultSet
import java.util.*

class FormatDaoImpl(private val connectionPool: Jdbi) : FormatDao {
  private class FormatRowMapper : RowMapper<DbFormat> {
    override fun map(rs: ResultSet?, ctx: StatementContext?): DbFormat {
      return DbFormat(
          rs!!.getInt("id"),
          rs.getString("name"),
          rs.getTimestamp("created").toLocalDateTime()
      )
    }
  }

  override fun all(): List<DbFormat> {
    return connectionPool.withHandle<List<DbFormat>, RuntimeException> {
      it.createQuery("select * from formats")
          .map(FormatRowMapper())
          .list()
    }
  }

  override fun get(name: String): Optional<DbFormat> {
    return connectionPool.withHandle<Optional<DbFormat>, RuntimeException> {
      it.createQuery("select * from formats where lower(name) = :name")
          .bind("name", name.toLowerCase())
          .map(FormatRowMapper())
          .findFirst()
    }
  }
}

package io.sylvanlibrary.api.format

import java.util.*

interface FormatDao {
  fun all(): List<DbFormat>
  fun get(name: String): Optional<DbFormat>
}

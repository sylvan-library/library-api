package io.sylvanlibrary.api.format

import java.util.*

interface FormatMapper {
  fun all(): List<Format>
  fun get(name: String): Optional<Format>
}

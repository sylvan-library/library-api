package io.sylvanlibrary.api.set

import io.sylvanlibrary.api.util.ResourceReference
import java.util.*

interface SetMapper {
  fun all(): List<Set>
  fun allForFormats(formatIds: List<Int>): Map<Int, List<ResourceReference>>
  fun get(abbreviation: String): Optional<Set>
}

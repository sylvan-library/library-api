package io.sylvanlibrary.api.set

import io.sylvanlibrary.api.util.ResourceReference

interface SetDao {
  fun all(): List<DbSet>
  fun allForFormats(formatIds: List<Int>): Map<Int, List<ResourceReference>>
}

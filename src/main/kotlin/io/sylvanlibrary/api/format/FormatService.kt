package io.sylvanlibrary.api.format

import io.sylvanlibrary.api.card.Card
import io.sylvanlibrary.api.set.Set
import java.util.*

interface FormatService {
  fun all(): List<Format>
  fun get(name: String): Optional<Format>
  fun sets(name: String): List<Set>
  fun cards(name: String): List<Card>
}

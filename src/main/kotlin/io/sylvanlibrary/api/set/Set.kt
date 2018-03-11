package io.sylvanlibrary.api.set

import com.fasterxml.jackson.annotation.JsonIgnore
import io.sylvanlibrary.api.util.ResourceReference
import java.time.LocalDate
import java.util.*

data class Set(
    @JsonIgnore val id: Int,
    val name: String,
    val canonicalUrl: String,
    val abbreviation: String,
    val block: String,
    val logoUrl: Optional<String>,
    val iconUrl: Optional<String>,
    val releaseDate: LocalDate
) {

  var legalFormats: List<ResourceReference> = listOf()
  var cards: List<ResourceReference> = listOf()

  val numberOfCards: Int
    get() = cards.size
}

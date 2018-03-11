package io.sylvanlibrary.api.format

import com.fasterxml.jackson.annotation.JsonIgnore
import io.sylvanlibrary.api.util.ResourceReference

data class Format(
    @JsonIgnore val id: Int,
    val name: String,
    val canonicalUrl: String
) {
  var legalSets: List<ResourceReference> = listOf()
  var bannedCards: List<ResourceReference> = listOf()
  var restrictedCards: List<ResourceReference> = listOf()

  val numberOfSets: Int
    get() = this.legalSets.size
}

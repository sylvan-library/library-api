package io.sylvanlibrary.api.format

import io.sylvanlibrary.api.util.ResourceReference

data class Format(
    val name: String,
    val canonicalUrl: String,
    val numberOfSets: Int,
    val legalSets: List<ResourceReference>,
    val bannedCards: List<ResourceReference>,
    val restrictedCards: List<ResourceReference>
)

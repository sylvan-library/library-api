package io.sylvanlibrary.api.set

import io.sylvanlibrary.api.util.ResourceReference
import java.time.LocalDate

data class Set(
    val name: String,
    val canonicalUrl: String,
    val abbreviation: String,
    val logoUrl: String,
    val iconUrl: String,
    val releaseDate: LocalDate,
    val numberOfCards: Int,
    val block: String,
    val legalFormats: List<ResourceReference>,
    val card: List<ResourceReference>
)

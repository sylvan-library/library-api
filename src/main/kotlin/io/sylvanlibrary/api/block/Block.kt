package io.sylvanlibrary.api.block

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class Block(
    val name: String,
    val canonicalUrl: String,
    val numberOfSets: Int,
    val firstSetAbbreviation: String,
    val firstSetReleaseDate: LocalDate,
    val lastSetReleaseDate: LocalDate
)

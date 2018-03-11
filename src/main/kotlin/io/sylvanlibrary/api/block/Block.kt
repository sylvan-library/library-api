package io.sylvanlibrary.api.block

import java.time.LocalDate

data class Block(
    val name: String,
    val canonicalUrl: String,
    val numberOfSets: Int,
    val firstSetAbbreviation: String,
    val firstSetReleaseDate: LocalDate,
    val lastSetReleaseDate: LocalDate
)

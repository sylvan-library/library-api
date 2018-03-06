package io.sylvanlibrary.api.entities

import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

data class Set(
    val id: Int,
    val blockId: Int,
    val name: String,
    val abbreviation: String,
    val icon: Optional<String>,
    val keyImage: Optional<String>,
    val logo: Optional<String>,
    val releaseDate: Optional<LocalDate>,
    val created: LocalDateTime
)

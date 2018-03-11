package io.sylvanlibrary.api.set

import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

data class DbSet(
    val id: Int,
    val blockId: Int,
    val name: String,
    val abbreviation: String,
    val icon: Optional<String>,
    val logo: Optional<String>,
    val releaseDate: Optional<LocalDate>,
    val created: LocalDateTime
)

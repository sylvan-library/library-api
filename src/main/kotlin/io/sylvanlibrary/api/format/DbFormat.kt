package io.sylvanlibrary.api.format

import java.time.LocalDateTime

data class DbFormat(
    val id: Int,
    val name: String,
    val created: LocalDateTime
)

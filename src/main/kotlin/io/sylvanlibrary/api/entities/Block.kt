package io.sylvanlibrary.api.entities

import java.time.LocalDateTime

data class Block(val id: Int, val name: String, val image: String, val created: LocalDateTime)

package io.sylvanlibrary.api.card

data class CardVersion(
    val gathererId: String,
    val set: String,
    val rarity: String,
    val setNumber: Int,
    val artist: String,
    val imageUrl: String
)

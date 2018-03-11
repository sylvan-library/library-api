package io.sylvanlibrary.api.card

data class Card(
    val name: String,
    val canonicalUrl: String,
    val color: String,
    val castingCost: String,
    val type: String,
    val supertypes: List<String>,
    val types: List<String>,
    val subtypes: List<String>,
    val rulesText: String,

    val thisVersion: CardVersion,
    val otherVersions: List<CardVersion>
)

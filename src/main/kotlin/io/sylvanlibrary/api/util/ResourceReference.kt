package io.sylvanlibrary.api.util

import com.fasterxml.jackson.annotation.JsonProperty

data class ResourceReference(
    val name: String,
    val canonicalUrl: String
)

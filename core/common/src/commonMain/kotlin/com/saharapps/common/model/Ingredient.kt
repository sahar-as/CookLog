package com.saharapps.common.model

import kotlinx.serialization.Serializable

@Serializable
data class Ingredient(
    val name: String = "",
    val amount: String = "",
    val unit: String = "",
)
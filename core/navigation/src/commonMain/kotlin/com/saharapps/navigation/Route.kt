package com.saharapps.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Route {
    @Serializable data object Catalog: Route()
    @Serializable data class RecipeList(val catalogId: Long): Route()
    @Serializable data class RecipeDetail(val recipeId: Long): Route()
    @Serializable data class RecipeEdit(
        val catalogId: Long,
        val recipeId: Long?
    ): Route()
}
package com.saharapps.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Route {
    @Serializable data object Catalog: Route()
    @Serializable data class RecipeList(val catalogId: Long): Route()
    @Serializable data object Recipe: Route()
}
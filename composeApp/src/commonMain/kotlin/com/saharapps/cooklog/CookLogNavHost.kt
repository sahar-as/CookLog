package com.saharapps.cooklog

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.saharapps.catalog.ui.CatalogScreen
import com.saharapps.navigation.Route

@Composable
fun CookLogNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Route.Catalog
    ) {
        composable<Route.Catalog> {
            CatalogScreen(
                onClickCatalog = {}
            )
        }
    }
}
package com.saharapps.cooklog

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.saharapps.catalog.ui.CatalogScreen
import com.saharapps.catalog.ui.CatalogViewModel
import com.saharapps.navigation.Route
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CookLogNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Route.Catalog
    ) {
        composable<Route.Catalog> {
            val catalogViewModel: CatalogViewModel = koinViewModel()
            CatalogScreen(
                onClickCatalog = { id ->
                    println("TAG-sahar 4444 $id")
                },
                viewModel = catalogViewModel
            )
        }
    }
}
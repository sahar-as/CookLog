package com.saharapps.cooklog

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.saharapps.catalog.ui.CatalogScreen
import com.saharapps.catalog.ui.CatalogViewModel
import com.saharapps.navigation.Route
import com.saharapps.recipe_list.ui.RecipeListScreen
import com.saharapps.recipe_list.ui.RecipeListViewModel
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
                    navController.navigate(Route.RecipeList(id))
                },
                viewModel = catalogViewModel
            )
        }
        composable<Route.RecipeList> { backStackEntry ->
            val args = backStackEntry.toRoute<Route.RecipeList>()
            val recipeListViewModel: RecipeListViewModel = koinViewModel()
            RecipeListScreen(
                catalogId = args.catalogId,
                viewModel = recipeListViewModel,
                onRecipeClick = {},
                onBack = {}
            )
        }
    }
}
package com.saharapps.recipe_list.di

import com.saharapps.recipe_list.ui.RecipeListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val recipeListModule = module  {
    viewModel { RecipeListViewModel() }
}
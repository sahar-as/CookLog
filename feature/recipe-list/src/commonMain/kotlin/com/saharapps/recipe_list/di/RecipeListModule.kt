package com.saharapps.recipe_list.di

import com.saharapps.recipe_list.data.RecipeListRepository
import com.saharapps.recipe_list.data.RecipeListRepositoryImpl
import com.saharapps.recipe_list.domain.DeleteRecipeUseCase
import com.saharapps.recipe_list.domain.DeleteRecipeUseCaseImpl
import com.saharapps.recipe_list.domain.GetRecipesByCatalogUseCase
import com.saharapps.recipe_list.domain.GetRecipesByCatalogUseCaseImpl
import com.saharapps.recipe_list.ui.RecipeListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val recipeListModule = module {
    factory<RecipeListRepository> { RecipeListRepositoryImpl(get()) }
    factory<GetRecipesByCatalogUseCase> { GetRecipesByCatalogUseCaseImpl(get()) }
    factory<DeleteRecipeUseCase> { DeleteRecipeUseCaseImpl(get()) }
    viewModel { RecipeListViewModel() }
}
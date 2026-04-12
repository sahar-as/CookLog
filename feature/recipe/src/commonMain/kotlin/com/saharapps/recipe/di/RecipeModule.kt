package com.saharapps.recipe.di

import com.saharapps.recipe.data.RecipeRepository
import com.saharapps.recipe.data.RecipeRepositoryImpl
import com.saharapps.recipe.domain.GetRecipeByIdUseCase
import com.saharapps.recipe.domain.GetRecipeByIdUseCaseImpl
import com.saharapps.recipe.domain.SaveRecipeUseCase
import com.saharapps.recipe.domain.SaveRecipeUseCaseImpl
import com.saharapps.recipe.domain.UpdateFavoriteStatusUseCase
import com.saharapps.recipe.domain.UpdateFavoriteStatusUseCaseImpl
import com.saharapps.recipe.ui.RecipeDetailViewModel
import com.saharapps.recipe.ui.RecipeEditViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val recipeModule = module {
    factory<RecipeRepository> { RecipeRepositoryImpl(get()) }
    factory<GetRecipeByIdUseCase> { GetRecipeByIdUseCaseImpl(get()) }
    factory<SaveRecipeUseCase> { SaveRecipeUseCaseImpl(get()) }
    factory<UpdateFavoriteStatusUseCase> { UpdateFavoriteStatusUseCaseImpl(get()) }
    viewModel {
        RecipeEditViewModel(
            get(),
            get()
        )
    }
    viewModel {
        RecipeDetailViewModel(
            get(),
            get()
        )
    }
}
package com.saharapps.recipe.di

import com.saharapps.recipe.data.RecipeRepository
import com.saharapps.recipe.data.RecipeRepositoryImpl
import com.saharapps.recipe.domain.GetRecipeByIdUseCase
import com.saharapps.recipe.domain.GetRecipeByIdUseCaseImpl
import org.koin.dsl.module

val recipeModule = module {
    factory<RecipeRepository> { RecipeRepositoryImpl(get()) }
    factory<GetRecipeByIdUseCase> { GetRecipeByIdUseCaseImpl(get()) }
}
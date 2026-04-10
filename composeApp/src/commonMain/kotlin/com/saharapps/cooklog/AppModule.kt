package com.saharapps.cooklog

import com.saharapps.catalog.di.catalogModule
import com.saharapps.database.di.databaseModule
import com.saharapps.recipe.di.recipeModule
import com.saharapps.recipe_list.di.recipeListModule
import org.koin.dsl.module

val appModule = module {
    includes(catalogModule, databaseModule, recipeListModule, recipeModule)
}
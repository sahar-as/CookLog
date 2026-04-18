package com.saharapps.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import com.saharapps.database.catalog.CatalogDao
import com.saharapps.database.catalog.CatalogEntity
import com.saharapps.database.recipe.RecipeDao
import com.saharapps.database.recipe.RecipeEntity

@Database(
    entities = [CatalogEntity::class, RecipeEntity::class],
    version = 1
)
@ConstructedBy(CookLogDatabaseConstructor::class)
@TypeConverters(ByteArrayListConverter::class)
abstract class CookLogDatabase : RoomDatabase() {
    abstract fun catalogDao(): CatalogDao
    abstract fun recipeDao(): RecipeDao
}

expect class DatabaseFactory {
    fun create(): CookLogDatabase
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object CookLogDatabaseConstructor : RoomDatabaseConstructor<CookLogDatabase>
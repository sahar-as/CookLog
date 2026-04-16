package com.saharapps.recipe.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.saharapps.common.model.CookLogImage
import com.saharapps.common.model.RecipeItem
import com.saharapps.common.rememberImageListPicker
import com.saharapps.recipe.ui.component.RecipeImageRenderer
import com.saharapps.ui.theme.LightColorScheme
import cooklog.feature.recipe.generated.resources.Res
import cooklog.feature.recipe.generated.resources.close
import cooklog.feature.recipe.generated.resources.cook_time_minutes
import cooklog.feature.recipe.generated.resources.create
import cooklog.feature.recipe.generated.resources.create_new_entry
import cooklog.feature.recipe.generated.resources.edit_recipe
import cooklog.feature.recipe.generated.resources.mins
import cooklog.feature.recipe.generated.resources.name
import cooklog.feature.recipe.generated.resources.recipe
import cooklog.feature.recipe.generated.resources.recipe_images
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeEditScreen(
    catalogId: Long,
    recipeId: Long?,
    viewModel: RecipeEditViewModel,
    onCancel: () -> Unit
) {
    val uiState by viewModel.recipeUiState.collectAsStateWithLifecycle()

    var name by rememberSaveable { mutableStateOf("") }
    var explanation by rememberSaveable { mutableStateOf("") }
    var isFavorite by rememberSaveable { mutableStateOf(false) }
    var cookTime by rememberSaveable { mutableStateOf("") }
    val selectedImages = rememberSaveable { mutableStateListOf<CookLogImage>() }

    val imagePicker = rememberImageListPicker { bytes ->
        if (bytes != null) {
            selectedImages.add(CookLogImage.Bitmap(bytes))
        }
    }

    LaunchedEffect(recipeId) {
        if (recipeId != null && recipeId != 0L) {
            viewModel.getRecipeById(recipeId)
        }
    }

    LaunchedEffect(uiState.recipe) {
        uiState.recipe?.let { recipe ->
            name = recipe.name
            explanation = recipe.explanation
            isFavorite = recipe.isFavorite
            cookTime = recipe.cookTime.toString()
            selectedImages.clear()
            selectedImages.addAll(recipe.images)
        }
    }

    MaterialTheme(colorScheme = LightColorScheme) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            if (recipeId == null)
                                stringResource(Res.string.create_new_entry)
                            else
                                stringResource(Res.string.edit_recipe)
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    navigationIcon = {
                        IconButton(onClick = onCancel) {
                            Icon(
                                Icons.Default.Close,
                                stringResource(Res.string.close)
                            )
                        }
                    }
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(stringResource(Res.string.name)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        focusedLabelColor = MaterialTheme.colorScheme.primary
                    )
                )

                Text(stringResource(Res.string.recipe_images), style = MaterialTheme.typography.titleSmall)
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(selectedImages) { img ->
                        Box(modifier = Modifier.size(100.dp)) {
                            RecipeImageRenderer(img)
                            IconButton(
                                onClick = {
                                    selectedImages.remove(img)
                                          },
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .background(Color.Black.copy(0.4f), CircleShape)
                                    .size(24.dp)
                            ) {
                                Icon(
                                    Icons.Default.Close,
                                    null,
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }

                    item {
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                                .clickable {
                                    imagePicker.launch()
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    imageVector = Icons.Default.AddAPhoto,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = "Add",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }

                OutlinedTextField(
                    value = cookTime,
                    onValueChange = { if (it.all { char -> char.isDigit() }) cookTime = it },
                    label = { Text(stringResource(Res.string.cook_time_minutes)) },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    trailingIcon = {
                        Text(
                            stringResource(Res.string.mins),
                            modifier = Modifier.padding(end = 8.dp)
                        )
                    }
                )

                OutlinedTextField(
                    value = explanation,
                    onValueChange = { explanation = it },
                    label = { Text(stringResource(Res.string.recipe)) },
                    modifier = Modifier.fillMaxWidth().height(150.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        focusedLabelColor = MaterialTheme.colorScheme.primary
                    )
                )

                Button(
                    onClick = {
                        val recipeToSave = RecipeItem(
                            id = recipeId ?: 0L,
                            name = name,
                            explanation = explanation,
                            images = selectedImages.toList(),
                            cookTime = cookTime.toIntOrNull() ?: 0,
                            isFavorite = isFavorite,
                            catalogId = catalogId
                        )
                        viewModel.saveRecipe(recipeToSave)
                        onCancel()
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        stringResource(Res.string.create),
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }
            }
        }
    }
}
package com.saharapps.recipe_list.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.decodeToImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.saharapps.common.model.CookLogImage
import com.saharapps.recipe_list.domain.model.RecipeItem
import com.saharapps.ui.theme.LightColorScheme
import cooklog.feature.recipe_list.generated.resources.Res
import cooklog.feature.recipe_list.generated.resources.default
import cooklog.feature.recipe_list.generated.resources.recipes
import cooklog.feature.recipe_list.generated.resources.search
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeListScreen(
    catalogId: Long,
    viewModel: RecipeListViewModel,
    onRecipeClick: (Long) -> Unit,
    onBack: () -> Unit
) {
//    val uiState by viewModel.recipeUiState.collectAsStateWithLifecycle()

    var isSearchExpanded by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    var showAddDialog by remember { mutableStateOf(false) }

//    val filteredRecipes = uiState.recipes.filter {
//        it.name.contains(searchQuery, ignoreCase = true)
//    }

    val filteredRecipes = listOf<RecipeItem>(
        RecipeItem(id = 1, name = "Food", explanation = "This is explenation",
            image = CookLogImage.Resource(Res.drawable.default), true, 1),
        RecipeItem(id = 2, name = "Foodiiii", explanation = "This is explenation",
            image = CookLogImage.Resource(Res.drawable.default), false, 1)
    )

    MaterialTheme(colorScheme = LightColorScheme) {
        // reuse your Add logic here if needed, or create AddRecipeDialog
        if (showAddDialog) {
            // AddRecipeDialog(...)
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.Default.ArrowBack, contentDescription = null)
                        }
                    },
                    title = {
                        if (!isSearchExpanded) {
                            Text(stringResource(Res.string.recipes))
                        } else {
                            TextField(
                                value = searchQuery,
                                onValueChange = { searchQuery = it },
                                modifier = Modifier.fillMaxWidth().focusRequester(focusRequester),
                                placeholder = {
                                    Text(
                                        stringResource(Res.string.search),
                                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                                    )
                                },
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    cursorColor = MaterialTheme.colorScheme.onPrimary,
                                    focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                                    unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                                    focusedIndicatorColor = Color.White,
                                    unfocusedIndicatorColor = Color.White.copy(alpha = 0.5f)
                                ),
                                singleLine = true
                            )
                            LaunchedEffect(Unit) { focusRequester.requestFocus() }
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            isSearchExpanded = !isSearchExpanded
                            if (!isSearchExpanded) searchQuery = ""
                        }) {
                            Icon(
                                imageVector = if (isSearchExpanded) Icons.Default.Close else Icons.Default.Search,
                                contentDescription = null
                            )
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { showAddDialog = true },
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                }
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
//                items(filteredRecipes) { recipe ->
//                    RecipeHorizontalCard(
//                        item = recipe,
//                        onClick = { onRecipeClick(recipe.id) },
//                        onFavoriteToggle = { viewModel.toggleFavorite(recipe.id) }
//                    )
//                }
                items(filteredRecipes) { recipe ->
                    RecipeHorizontalCard(
                        item = recipe,
                        onClick = { onRecipeClick(recipe.id) },
                        onFavoriteToggle = {
//                            viewModel.toggleFavorite(recipe.id)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun RecipeHorizontalCard(
    item: RecipeItem,
    onClick: () -> Unit,
    onFavoriteToggle: () -> Unit
) {
    val painter = when (val img = item.image) {
        is CookLogImage.Resource -> painterResource(img.res)
        is CookLogImage.Bitmap -> {
            val bitmap = remember(img.data) { img.data.decodeToImageBitmap() }
            remember(bitmap) { BitmapPainter(bitmap) }
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        shape = RoundedCornerShape(12.dp),
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            // 1. Image
            Box(modifier = Modifier.width(120.dp).fillMaxHeight()) {
                Image(
                    painter = painter,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(120.dp)
                        .fillMaxHeight()
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.6f)),
                                startY = 150f
                            )
                        )
                )
            }

            // 2. Heart Button (Between Image and Text)
            Box(
                modifier = Modifier.fillMaxHeight().padding(horizontal = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                IconButton(onClick = onFavoriteToggle) {
                    Icon(
                        imageVector = if (item.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint = if (item.isFavorite) Color.Red else Color.Gray
                    )
                }
            }

            // 3. Name and Explanation
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.explanation,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
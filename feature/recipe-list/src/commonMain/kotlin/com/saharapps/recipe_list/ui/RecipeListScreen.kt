package com.saharapps.recipe_list.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.decodeToImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.saharapps.common.model.RecipeItem
import com.saharapps.ui.theme.LightColorScheme
import cooklog.feature.recipe_list.generated.resources.Res
import cooklog.feature.recipe_list.generated.resources.cancel
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
    onBack: () -> Unit,
    onClickAddRecipe: (Long, Long?) -> Unit
) {
    val uiState by viewModel.recipeListUiState.collectAsStateWithLifecycle()
    var isSearchExpanded by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    var selectedIds by remember { mutableStateOf(setOf<Long>()) }
    val isSelectionMode = selectedIds.isNotEmpty()

    LaunchedEffect(isSearchExpanded) {
        if (isSearchExpanded) selectedIds = emptySet()
    }
    LaunchedEffect(Unit) {
        viewModel.getRecipesByCatalog(catalogId)
    }

    val filteredRecipes = uiState.recipes.filter {
        it.name.contains(searchQuery, ignoreCase = true)
    }

    MaterialTheme(colorScheme = LightColorScheme) {
        Scaffold(
            topBar = {
                if (isSelectionMode) {
                    TopAppBar(
                        title = { Text("${selectedIds.size} Selected") },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.onSecondaryContainer
                        ),
                        navigationIcon = {
                            IconButton(onClick = { selectedIds = emptySet() }) {
                                Icon(Icons.Default.Close, null)
                            }
                        },
                        actions = {
                            IconButton(onClick = {
                                viewModel.deleteRecipes(selectedIds.toList(), catalogId)
                                selectedIds = emptySet()
                            }) {
                                Icon(Icons.Default.Delete, null)
                            }
                        }
                    )
                }else{
                    TopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            titleContentColor = MaterialTheme.colorScheme.onPrimary,
                            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        navigationIcon = {
                            IconButton(onClick = onBack) {
                                Icon(
                                    Icons.Default.ArrowBack,
                                    contentDescription = stringResource(Res.string.cancel),
                                    tint = Color.White
                                )
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
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { onClickAddRecipe(catalogId, null) },
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
                items(
                    items = filteredRecipes,
                    key = { it.id }
                ) { recipe ->
                    val isSelected = selectedIds.contains(recipe.id)

                    Box(modifier = Modifier.fillMaxWidth()) {
                        RecipeHorizontalCard(
                            item = recipe,
                            isSelected = isSelected,
                            onClick = { id ->
                                if (isSelectionMode) {
                                    selectedIds = if (isSelected) selectedIds - id else selectedIds + id
                                } else {
                                    onRecipeClick(id)
                                }
                            },
                            onLongClick = { id ->
                                if (!isSelectionMode) {
                                    selectedIds = selectedIds + id
                                }
                            },
                            onFavoriteClick = {
                                if (!isSelectionMode) {
                                    viewModel.updateFavoriteState(
                                        recipeId = recipe.id,
                                        isFavorite = !recipe.isFavorite
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecipeHorizontalCard(
    item: RecipeItem,
    isSelected: Boolean,
    onClick: (Long) -> Unit,
    onLongClick: (Long) -> Unit,
    onFavoriteClick: () -> Unit
) {
    val imageData = item.images?.firstOrNull()

    val painter = if (imageData != null && imageData.isNotEmpty()) {
        val bitmap = remember(imageData) {
            try {
                imageData.decodeToImageBitmap()
            } catch (e: Exception) {
                null
            }
        }

        if (bitmap != null) {
            remember(bitmap) { BitmapPainter(bitmap) }
        } else {
            painterResource(Res.drawable.default)
        }
    } else {
        painterResource(Res.drawable.default)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clip(RoundedCornerShape(12.dp))
            .combinedClickable(
                onClick = { onClick(item.id) },
                onLongClick = { onLongClick(item.id) }
            ),
        shape = RoundedCornerShape(12.dp),
        border = if (isSelected) BorderStroke(2.dp, MaterialTheme.colorScheme.primary) else null,
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
            } else {
                Color.White
            }
        ),
        elevation = CardDefaults.cardElevation(if (isSelected) 0.dp else 2.dp)
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.width(120.dp).fillMaxHeight()) {
                Image(
                    painter = painter,
                    contentDescription = "Recipe Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.4f)),
                                startY = 100f
                            )
                        )
                )

                if (isSelected) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .padding(6.dp)
                            .size(20.dp)
                            .align(Alignment.TopStart)
                            .background(Color.White, CircleShape)
                    )
                }
            }

            Box(
                modifier = Modifier.fillMaxHeight().padding(horizontal = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = { onFavoriteClick() },
                    enabled = !isSelected
                ) {
                    Icon(
                        imageVector = if (item.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint = if (item.isFavorite) Color.Red else Color.Gray
                    )
                }
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
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
                    color = Color.DarkGray,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
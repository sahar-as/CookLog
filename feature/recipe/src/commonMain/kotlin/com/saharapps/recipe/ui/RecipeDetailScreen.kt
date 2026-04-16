package com.saharapps.recipe.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.saharapps.recipe.ui.component.RecipeImageRenderer
import com.saharapps.ui.ViewStatus
import com.saharapps.ui.theme.LightColorScheme
import cooklog.feature.recipe.generated.resources.Res
import cooklog.feature.recipe.generated.resources.recipe
import org.jetbrains.compose.resources.stringResource

@Composable
fun RecipeDetailScreen(
    recipeId: Long,
    viewModel: RecipeDetailViewModel,
    onBack: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
) {
    val uiState by viewModel.recipeUiState.collectAsStateWithLifecycle()
    LaunchedEffect(recipeId) {
        viewModel.getRecipeById(recipeId)
    }

    MaterialTheme(colorScheme = LightColorScheme) {
        val pagerState = rememberPagerState(pageCount = { 1 }) // todo Replace '1' with images list size

        Scaffold(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            bottomBar = {
                BottomAppBar(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    actions = {
                        IconButton(onClick = onDelete) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                        IconButton(
                            onClick = {
                                val currentStatus = uiState.recipe?.isFavorite ?: false
                                viewModel.updateFavoriteState(
                                    recipeId = recipeId,
                                    isFavorite = !currentStatus
                                )
                            }
                        ) {
                            val favoriteActive = uiState.recipe?.isFavorite == true
                            Icon(
                                imageVector = if (favoriteActive) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = null,
                                tint = if (favoriteActive) Color.Red else MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = onEdit,
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.onSecondary
                        ) {
                            Icon(Icons.Default.Edit, contentDescription = null)
                        }
                    }
                )
            }
        ) { innerPadding ->
            when (uiState.viewStatus) {
                ViewStatus.INITIAL, ViewStatus.LOADING -> {
                    Box(
                        modifier = Modifier.fillMaxSize().padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    }
                }

                ViewStatus.SUCCESS -> {
                    uiState.recipe?.let { recipe ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                                .verticalScroll(rememberScrollState())
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(onClick = onBack) {
                                    Icon(Icons.Default.ArrowBack, null, tint = MaterialTheme.colorScheme.primary)
                                }
                                Text(
                                    text = recipe.name,
                                    style = MaterialTheme.typography.headlineMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }

                            HorizontalPager(
                                state = pagerState,
                                modifier = Modifier.fillMaxWidth().height(320.dp),
                                contentPadding = PaddingValues(horizontal = 24.dp),
                                pageSpacing = 16.dp
                            ) {
                                Card(
                                    shape = RoundedCornerShape(24.dp),
                                    elevation = CardDefaults.cardElevation(4.dp)
                                ) {
                                    RecipeImageRenderer(recipe.images)
                                }
                            }

                            Surface(
                                modifier = Modifier.padding(16.dp),
                                shape = RoundedCornerShape(12.dp),
                                color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Default.Timer, null, tint = MaterialTheme.colorScheme.secondary)
                                    Spacer(Modifier.width(8.dp))
                                    Text("Ready in 40 mins", color = MaterialTheme.colorScheme.secondary)
                                }
                            }

                            Text(
                                text = stringResource(Res.string.recipe),
                                modifier = Modifier.padding(horizontal = 16.dp),
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.primary
                            )

                            Text(
                                text = recipe.explanation,
                                modifier = Modifier.padding(16.dp),
                                style = MaterialTheme.typography.bodyLarge,
                                lineHeight = 24.sp
                            )
                        }
                    }
                }

                ViewStatus.FAILED -> {
                    Box(
                        modifier = Modifier.fillMaxSize().padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = uiState.failedMessage ?: "An error occurred",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}
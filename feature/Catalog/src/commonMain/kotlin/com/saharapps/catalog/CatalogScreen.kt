package com.saharapps.catalog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.saharapps.catalog.utils.Constant
import com.saharapps.common.rememberImagePicker
import cooklog.feature.catalog.generated.resources.Res
import cooklog.feature.catalog.generated.resources.default
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.decodeToImageBitmap


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(onClickCatalog: () -> Unit) {
    var isSearchExpanded by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    var showAddDialog by remember { mutableStateOf(false) }

    val recipeList = remember { //todo we will get this from database
        mutableStateListOf(
            RecipeItem("Pasta", CatalogImage.Resource(Res.drawable.default)),
            RecipeItem("Pizza", CatalogImage.Resource(Res.drawable.default))
        )
    }
    val filteredRecipes = recipeList.filter {
        it.name.contains(searchQuery, ignoreCase = true)
    }

    if (showAddDialog) {
        AddCatalogDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { name, imageSource ->
                recipeList.add(RecipeItem(name, imageSource))
                showAddDialog = false
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (!isSearchExpanded) {
                        Text(Constant.CATALOGS)
                    } else {
                        TextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            modifier = Modifier.fillMaxWidth().focusRequester(focusRequester),
                            placeholder = { Text(Constant.SEARCH_HINT) },
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
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = Constant.ADD_ITEM
                )
            }
        }
    ) { innerPadding ->
        CatalogGrid(
            padding = innerPadding,
            recipes = filteredRecipes
        )
    }
}

@Composable
fun CatalogGrid(padding: PaddingValues, recipes: List<RecipeItem>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize().padding(padding),
        contentPadding = PaddingValues(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(recipes) { recipe ->
            CatalogCard(item = recipe)
        }
    }
}

@Composable
fun CatalogCard(item: RecipeItem) {
    val painter = when (val img = item.image) {
        is CatalogImage.Resource -> painterResource(img.res)
        is CatalogImage.Bitmap -> {
            val bitmap = remember(img.data) {
                img.data.decodeToImageBitmap()
            }
            remember(bitmap) { BitmapPainter(bitmap) }
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f), //To have square card
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painter,
                contentDescription = item.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier.fillMaxSize().background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f)),
                        startY = 300f
                    )
                )
            )

            Text(
                text = item.name,
                color = Color.White,
                modifier = Modifier.align(Alignment.BottomStart).padding(12.dp),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun AddCatalogDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, CatalogImage) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var selectedImage by remember { mutableStateOf<CatalogImage?>(null) }

    val picker = rememberImagePicker { bytes ->
        if (bytes != null) {
            selectedImage = CatalogImage.Bitmap(bytes)
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(Constant.CREATE_NEW_CATALOG) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(Constant.NAME) },
                    modifier = Modifier.fillMaxWidth()
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { picker.launch() }) {
                        Text(Constant.GALLERY)
                    }

                    OutlinedButton(onClick = {
                        selectedImage = CatalogImage.Resource(Res.drawable.default)
                    }) {
                        Text(Constant.DEFAULT)
                    }
                }

                when (val image = selectedImage) {
                    is CatalogImage.Bitmap -> {
                        val bitmap = remember(image.data) { image.data.decodeToImageBitmap() }
                        Image(
                            bitmap = bitmap,
                            contentDescription = null,
                            modifier = Modifier.size(100.dp).clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }

                    is CatalogImage.Resource -> {
                        Image(
                            painter = painterResource(image.res),
                            contentDescription = null,
                            modifier = Modifier.size(100.dp).clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }

                    null -> Text(Constant.NO_IMAGE_SELECTED, style = MaterialTheme.typography.bodySmall)
                }
            }
        },
        confirmButton = {
            Button(
                enabled = name.isNotBlank() && selectedImage != null,
                onClick = { onConfirm(name, selectedImage!!) }
            ) { Text(Constant.CREATE) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text(Constant.CANCEL) }
        }
    )
}
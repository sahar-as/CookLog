package com.saharapps.catalog.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.saharapps.catalog.CatalogItem
import com.saharapps.common.model.CookLogImage
import com.saharapps.common.rememberImagePicker
import com.saharapps.ui.ViewStatus
import com.saharapps.ui.theme.LightColorScheme
import cooklog.feature.catalog.generated.resources.Res
import cooklog.feature.catalog.generated.resources.*
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.decodeToImageBitmap
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    viewModel: CatalogViewModel,
    onClickCatalog: (Long) -> Unit
) {
    val catalogUiState by viewModel.catalogUiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.getCatalogs()
    }

    var catalogToDelete by remember { mutableStateOf<CatalogItem?>(null) }

    var isSearchExpanded by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    var showAddDialog by remember { mutableStateOf(false) }

    val filteredRecipes = catalogUiState.catalogs.filter {
        it.name.contains(searchQuery, ignoreCase = true)
    }

    MaterialTheme(colorScheme = LightColorScheme) {
        if (showAddDialog) {
            AddCatalogDialog(
                onDismiss = { showAddDialog = false },
                onConfirm = { name, imageSource ->
                    val newCatalog = CatalogItem(name = name, image = imageSource)
                    viewModel.saveCatalog(newCatalog)
                    showAddDialog = false
                }
            )
        }

        catalogToDelete?.let { catalog ->
            DeleteDialog(
                item = catalog,
                onDismiss = { catalogToDelete = null },
                onConfirm = { id ->
                    id.let { viewModel.deleteCatalog(it) }
                    catalogToDelete = null
                }
            )
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    title = {
                        if (!isSearchExpanded) {
                            Text(stringResource(Res.string.catalogs))
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
                    Icon(
                        Icons.Default.Add,
                        contentDescription = stringResource(Res.string.add_item)
                    )
                }
            }
        ) { innerPadding ->
            when (catalogUiState.viewStatus) {
                ViewStatus.INITIAL -> {}
                ViewStatus.LOADING -> {
                    //todo should show Shimmer
                }

                ViewStatus.SUCCESS -> {
                    CatalogGrid(
                        padding = innerPadding,
                        recipes = filteredRecipes,
                        onClickCatalog = onClickCatalog,
                        onLongClickCatalog = { item -> catalogToDelete = item }
                    )
                }

                ViewStatus.FAILED -> {
                    //todo should show failed screen
                }
            }
        }
    }
}

@Composable
fun CatalogGrid(
    padding: PaddingValues,
    recipes: List<CatalogItem>,
    onClickCatalog: (Long) -> Unit,
    onLongClickCatalog: (CatalogItem) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .background(MaterialTheme.colorScheme.primaryContainer),
        contentPadding = PaddingValues(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(recipes) { recipe ->
            CatalogCard(
                item = recipe,
                onClickCatalog = onClickCatalog,
                onLongClickCatalog = onLongClickCatalog
            )
        }
    }
}

@Composable
fun CatalogCard(
    item: CatalogItem,
    onClickCatalog: (Long) -> Unit,
    onLongClickCatalog: (CatalogItem) -> Unit
) {
    val painter = when (val img = item.image) {
        is CookLogImage.Resource -> painterResource(img.res)
        is CookLogImage.Bitmap -> {
            val bitmap = remember(img.data) {
                img.data.decodeToImageBitmap()
            }
            remember(bitmap) { BitmapPainter(bitmap) }
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f) //To have square card
            .combinedClickable(
                onClick = { onClickCatalog(item.id) },
                onLongClick = { onLongClickCatalog(item) }
            ),
        shape = RoundedCornerShape(12.dp),
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
    onConfirm: (String, CookLogImage) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var selectedImage by remember { mutableStateOf<CookLogImage?>(null) }

    val picker = rememberImagePicker { bytes ->
        if (bytes != null) {
            selectedImage = CookLogImage.Bitmap(bytes)
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        title = {
            Text(
                stringResource(Res.string.create_new_entry),
                color = MaterialTheme.colorScheme.primary
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
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

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = { picker.launch() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary
                        )
                    ) {
                        Text(stringResource(Res.string.gallery), color = MaterialTheme.colorScheme.onSecondary)
                    }

                    OutlinedButton(
                        onClick = { selectedImage = CookLogImage.Resource(Res.drawable.default) },
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                    ) {
                        Text(stringResource(Res.string.default), color = MaterialTheme.colorScheme.primary)
                    }
                }

                when (val image = selectedImage) {
                    is CookLogImage.Bitmap -> {
                        val bitmap = remember(image.data) { image.data.decodeToImageBitmap() }
                        Image(
                            bitmap = bitmap,
                            contentDescription = null,
                            modifier = Modifier.size(100.dp).clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }

                    is CookLogImage.Resource -> {
                        Image(
                            painter = painterResource(image.res),
                            contentDescription = null,
                            modifier = Modifier.size(100.dp).clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }

                    null -> Text(
                        stringResource(Res.string.no_image_selected),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        },
        confirmButton = {
            Button(
                enabled = name.isNotBlank() && selectedImage != null,
                onClick = { onConfirm(name, selectedImage!!) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) { Text(stringResource(Res.string.create), color = MaterialTheme.colorScheme.onSecondary) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    stringResource(Res.string.cancel),
                    color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.6f)
                )
            }
        }
    )
}

@Composable
fun DeleteDialog(
    item: CatalogItem,
    onDismiss: () -> Unit,
    onConfirm: (Long) -> Unit,
) {
    AlertDialog(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        onDismissRequest = { },
        title = { Text(stringResource(Res.string.delete_catalog)) },
        text = { Text(stringResource(Res.string.delete_confirmation, item.name)) },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(item.id)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text(stringResource(Res.string.delete), color = Color.White)
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(
                    stringResource(Res.string.cancel),
                    color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.6f)
                )
            }
        }
    )
}
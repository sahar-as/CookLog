package com.saharapps.catalog.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.Image
import coil3.compose.rememberAsyncImagePainter
import com.saharapps.catalog.CatalogItem
import com.saharapps.common.rememberImagePicker
import com.saharapps.ui.ViewStatus
import com.saharapps.ui.theme.LightColorScheme
import cooklog.feature.catalog.generated.resources.Res
import cooklog.feature.catalog.generated.resources.*
import org.jetbrains.compose.resources.painterResource
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
    var isSearchExpanded by rememberSaveable { mutableStateOf(false) }
    var searchQuery by rememberSaveable { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    var showAddDialog by rememberSaveable { mutableStateOf(false) }

    val filteredRecipes = catalogUiState.catalogs.filter {
        it.name.contains(searchQuery, ignoreCase = true)
    }

    MaterialTheme(colorScheme = LightColorScheme) {
        if (showAddDialog) {
            AddCatalogDialog(
                onDismiss = { showAddDialog = false },
                onConfirm = { name, imagePath ->
                    val newCatalog = CatalogItem(name = name, imagePath = imagePath)
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

                ViewStatus.LOADING -> LoadingUi(Modifier)

                ViewStatus.SUCCESS -> {
                    if (filteredRecipes.isEmpty()) {
                        ShowEmptyState()
                    } else {
                        CatalogGrid(
                            padding = innerPadding,
                            recipes = filteredRecipes,
                            onClickCatalog = onClickCatalog,
                            onLongClickCatalog = { item -> catalogToDelete = item }
                        )
                    }
                }

                ViewStatus.FAILED -> {
                    FailedUi(
                        Modifier,
                        onClickTryAgain = {
                            viewModel.getCatalogs()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ShowEmptyState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Catalog is Empty",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .padding(bottom = 4.dp),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSecondary,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp
        )

        Text(
            text = "Ready to start your culinary journey? \n Start adding your favorite Catalogs",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .padding(bottom = 16.dp),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSecondary,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primary,
                    RoundedCornerShape(16.dp)
                ),
            contentAlignment = Alignment.BottomCenter
        ) {
            Image(
                painter = painterResource(Res.drawable.empty),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
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
                rememberAsyncImagePainter(
                    model = item.imagePath,
                    error = painterResource(Res.drawable.default),
                    placeholder = painterResource(Res.drawable.default)
                ),
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
    onConfirm: (String, String?) -> Unit
) {
    var name by rememberSaveable { mutableStateOf("") }
    var selectedImage by rememberSaveable { mutableStateOf<String?>(null) }

    val picker = rememberImagePicker { path ->
        if (path != null) {
            selectedImage = path
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
                        Text(
                            stringResource(Res.string.gallery),
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                }

                when (val image = selectedImage) {
                    null -> Text(
                        stringResource(Res.string.no_image_selected),
                        style = MaterialTheme.typography.bodySmall
                    )

                    else -> {
                        Image(
                            painter = rememberAsyncImagePainter(image),
                            contentDescription = null,
                            modifier = Modifier
                                .size(100.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                enabled = name.isNotBlank(),
                onClick = { onConfirm(name, selectedImage) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text(
                    stringResource(Res.string.create),
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
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

@Composable
fun LoadingUi(modifier: Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = modifier.size(50.dp),
            color = MaterialTheme.colorScheme.secondary,
            strokeWidth = 6.dp
        )
    }
}

@Composable
fun FailedUi(
    modifier: Modifier,
    onClickTryAgain: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer),
        verticalArrangement = Arrangement.Center,
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(Res.string.failed_to_load),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(24.dp),
                style = MaterialTheme.typography.titleLarge
            )
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(Res.drawable.error),
                    contentDescription = stringResource(Res.string.failed_to_load),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                modifier = modifier.padding(24.dp),
                onClick = { onClickTryAgain.invoke() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                ),
            ) {
                Text(
                    stringResource(Res.string.try_again),
                    color = MaterialTheme.colorScheme.onSecondary,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
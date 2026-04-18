package com.saharapps.recipe.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.decodeToImageBitmap

@Composable
fun RecipeImageRenderer(
    imageData: ByteArray?,
    modifier: Modifier = Modifier
) {
    val bitmap = remember(imageData) {
        if (imageData != null && imageData.isNotEmpty()) {
            try {
                imageData.decodeToImageBitmap()
            } catch (e: Exception) {
                null
            }
        } else null
    }

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (bitmap != null) {
            Image(
                painter = BitmapPainter(bitmap),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray.copy(alpha = 0.3f)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.BrokenImage,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(48.dp)
                )
            }
        }
    }
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecipeImageRenderer(
    images: List<ByteArray>?,
    modifier: Modifier = Modifier
) {
    if (images.isNullOrEmpty()) {
        RecipeImageRenderer(imageData = null, modifier = modifier)
        return
    }

    val pagerState = rememberPagerState(pageCount = { images.size })

    Box(modifier = modifier) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { pageIndex ->
            RecipeImageRenderer(imageData = images[pageIndex])
        }

        if (images.size > 1) {
            Row(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(images.size) { iteration ->
                    val color = if (pagerState.currentPage == iteration)
                        Color.White
                    else
                        Color.White.copy(alpha = 0.5f)

                    Box(
                        modifier = Modifier
                            .padding(horizontal = 3.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(6.dp)
                    )
                }
            }
        }
    }
}
package com.saharapps.recipe.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import com.saharapps.common.model.CookLogImage
import org.jetbrains.compose.resources.decodeToImageBitmap
import org.jetbrains.compose.resources.painterResource

@Composable
fun RecipeImageRenderer(image: CookLogImage) {
    val painter = when (image) {
        is CookLogImage.Resource -> painterResource(image.res)
        is CookLogImage.Bitmap -> BitmapPainter(image.data.decodeToImageBitmap())
    }
    Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
}
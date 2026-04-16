package com.saharapps.common

import androidx.compose.runtime.Composable

interface ImagePicker {
    fun launch()
}

@Composable
expect fun rememberImagePicker(onImagePicked: (ByteArray?) -> Unit): ImagePicker
@Composable

expect fun rememberImageListPicker(onImagePicked: (ByteArray?) -> Unit): ImagePicker
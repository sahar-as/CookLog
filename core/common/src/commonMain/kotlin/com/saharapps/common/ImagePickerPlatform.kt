package com.saharapps.common

import androidx.compose.runtime.Composable

interface ImagePicker {
    fun launch()
}
@Composable
expect fun rememberImagePicker(onImagePicked: (String?) -> Unit): ImagePicker

@Composable
expect fun rememberImageListPicker(onImagePicked: (String?) -> Unit): ImagePicker
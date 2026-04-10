package com.saharapps.common.model

import org.jetbrains.compose.resources.DrawableResource

sealed class CookLogImage {
    data class Resource(val res: DrawableResource) : CookLogImage()
    data class Bitmap(val data: ByteArray) : CookLogImage()
}
package com.saharapps.common

import android.content.Context
import androidx.compose.runtime.Composable
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import java.io.File

@Composable
actual fun rememberImagePicker(onImagePicked: (String?) -> Unit): ImagePicker {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            try {
                val path = copyUriToLocalFile(context, uri)
                onImagePicked(path)
            } catch (e: Exception) {
                e.printStackTrace()
                onImagePicked(null)
            }
        } else {
            onImagePicked(null)
        }
    }

    return remember {
        object : ImagePicker {
            override fun launch() {
                launcher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            }
        }
    }
}


@Composable
actual fun rememberImageListPicker(onImagePicked: (String?) -> Unit): ImagePicker {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(maxItems = 5)
    ) { uris: List<Uri> ->
        uris.forEach { uri ->
            try {
                val path = copyUriToLocalFile(context, uri)
                onImagePicked(path)
            } catch (e: Exception) {
                e.printStackTrace()
                onImagePicked(null)
            }
        }
    }

    return remember {
        object : ImagePicker {
            override fun launch() {
                launcher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            }
        }
    }
}


fun copyUriToLocalFile(context: Context, uri: Uri): String? {
    val fileName = "img_${System.currentTimeMillis()}.jpg"
    val file = File(context.filesDir, fileName)

    context.contentResolver.openInputStream(uri)?.use { input ->
        file.outputStream().use { output ->
            input.copyTo(output)
        }
    }

    return if (file.exists()) file.absolutePath else null
}
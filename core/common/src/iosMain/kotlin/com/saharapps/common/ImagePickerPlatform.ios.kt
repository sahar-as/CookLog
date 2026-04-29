package com.saharapps.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.cinterop.ExperimentalForeignApi
import platform.PhotosUI.PHPickerConfiguration
import platform.PhotosUI.PHPickerFilter
import platform.PhotosUI.PHPickerResult
import platform.PhotosUI.PHPickerViewController
import platform.PhotosUI.PHPickerViewControllerDelegateProtocol
import platform.UIKit.UIApplication
import platform.darwin.NSObject
import platform.Foundation.NSDate
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask
import platform.Foundation.timeIntervalSince1970

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun rememberImagePicker(onImagePicked: (String?) -> Unit): ImagePicker {
    val delegate = remember {
        object : NSObject(), PHPickerViewControllerDelegateProtocol {
            override fun picker(picker: PHPickerViewController, didFinishPicking: List<*>) {
                val result = didFinishPicking.firstOrNull() as? PHPickerResult

                if (result == null) {
                    onImagePicked(null)
                    picker.dismissViewControllerAnimated(true, null)
                    return
                }

                result.itemProvider.loadFileRepresentationForTypeIdentifier("public.image") { url, error ->
                    val savedPath = url?.let { copyToDocuments(it) }
                    onImagePicked(savedPath)
                }

                picker.dismissViewControllerAnimated(true, null)
            }
        }
    }

    return remember {
        object : ImagePicker {
            override fun launch() {
                val configuration = PHPickerConfiguration()
                configuration.filter = PHPickerFilter.imagesFilter
                configuration.selectionLimit = 1

                val picker = PHPickerViewController(configuration)
                picker.delegate = delegate

                val window = UIApplication.sharedApplication.keyWindow
                val rootViewController = window?.rootViewController
                rootViewController?.presentViewController(picker, true, null)
            }
        }
    }
}

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun rememberImageListPicker(onImagePicked: (String?) -> Unit): ImagePicker {
    val delegate = remember {
        object : NSObject(), PHPickerViewControllerDelegateProtocol {
            override fun picker(picker: PHPickerViewController, didFinishPicking: List<*>) {
                picker.dismissViewControllerAnimated(true, null)
                val results = didFinishPicking.mapNotNull { it as? PHPickerResult }

                if (results.isEmpty()) {
                    onImagePicked(null)
                    return
                }

                results.forEach { result ->
                    result.itemProvider.loadFileRepresentationForTypeIdentifier("public.image") { url, error ->
                        val savedPath = url?.let { copyToDocuments(it) }
                        onImagePicked(savedPath)
                    }
                }
            }
        }
    }

    return remember {
        object : ImagePicker {
            override fun launch() {
                val configuration = PHPickerConfiguration()
                configuration.filter = PHPickerFilter.imagesFilter()
                configuration.selectionLimit = 5

                val picker = PHPickerViewController(configuration)
                picker.delegate = delegate

                val window = UIApplication.sharedApplication.keyWindow
                val rootViewController = window?.rootViewController
                rootViewController?.presentViewController(picker, true, null)
            }
        }
    }
}

@OptIn(ExperimentalForeignApi::class)
private fun copyToDocuments(tempUrl: NSURL): String? {
    val fileName = "img_${NSDate().timeIntervalSince1970.toLong()}.jpg"

    val docsDir = NSSearchPathForDirectoriesInDomains(
        NSDocumentDirectory,
        NSUserDomainMask,
        true
    ).firstOrNull() as? String ?: return null

    val destPath = "$docsDir/$fileName"
    val destURL = NSURL.fileURLWithPath(destPath)

    val fileManager = NSFileManager.defaultManager

    if (fileManager.fileExistsAtPath(destPath)) {
        fileManager.removeItemAtPath(destPath, error = null)
    }

    val copied = fileManager.copyItemAtURL(tempUrl, toURL = destURL, error = null)

    return if (copied) destPath else null
}
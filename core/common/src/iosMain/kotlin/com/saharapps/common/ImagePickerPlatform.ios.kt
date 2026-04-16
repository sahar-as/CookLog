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
import platform.Foundation.NSData
import platform.darwin.NSObject
import kotlinx.cinterop.get
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.refTo
import platform.posix.memcpy

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun rememberImagePicker(onImagePicked: (ByteArray?) -> Unit): ImagePicker {
    val delegate = remember {
        object : NSObject(), PHPickerViewControllerDelegateProtocol {
            override fun picker(picker: PHPickerViewController, didFinishPicking: List<*>) {
                val result = didFinishPicking.firstOrNull() as? PHPickerResult

                if (result == null) {
                    onImagePicked(null)
                    picker.dismissViewControllerAnimated(true, null)
                    return
                }

                result.itemProvider.loadDataRepresentationForTypeIdentifier("public.image") { data, error ->
                    if (data != null) {
                        val nsData = data as NSData

                        val length = nsData.length.toInt()
                        val bytes = ByteArray(length)
                        val pointer = nsData.bytes?.reinterpret<ByteVar>()

                        if (pointer != null) {
                            for (i in 0 until length) {
                                bytes[i] = pointer[i]
                            }
                            onImagePicked(bytes)
                        } else {
                            onImagePicked(null)
                        }
                    } else {
                        onImagePicked(null)
                    }
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
actual fun rememberImageListPicker(onImagePicked: (ByteArray?) -> Unit): ImagePicker {
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
                    result.itemProvider.loadDataRepresentationForTypeIdentifier("public.image") { data, error ->
                        if (data != null) {
                            val nsData = data as NSData
                            val bytes = ByteArray(nsData.length.toInt())

                            memcpy(bytes.refTo(0), nsData.bytes, nsData.length)
                            onImagePicked(bytes)
                        }
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
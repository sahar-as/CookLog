package com.saharapps.cooklog

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication

@Composable
@Preview
fun App() {
    KoinApplication(application = {
        printLogger()
        modules(appModule)
    }) {
        MaterialTheme {
            val navController = rememberNavController()
            CookLogNavHost(navController)
        }
    }
}
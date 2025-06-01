package com.example.passwordmanager.UI

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import android.content.Context
import androidx.compose.runtime.*
import androidx.navigation.compose.*
import com.example.passwordmanager.UI.screens.*
import com.example.passwordmanager.data.*
import com.example.passwordmanager.UI.screens.LoginScreen

@Composable
fun Navigation(context: Context = LocalContext.current) {
    val navController = rememberNavController()
    var isAuthenticated by remember { mutableStateOf(false) }
    var showLogin by remember { mutableStateOf(false) }

    if (isAuthenticated) {
        MainAppScaffold(navController = navController)  // tutaj wstawiamy nasz scaffold z drawerem i nav hostem
    } else if (showLogin) {
        LoginScreen(onAuthenticated = { isAuthenticated = true })
    } else {
        WelcomeScreen(onStartLogin = { showLogin = true })
    }
}

package com.example.passwordmanager.UI

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import android.content.Context
import androidx.compose.runtime.*
import androidx.navigation.compose.*
import com.example.passwordmanager.UI.screens.*
import com.example.passwordmanager.data.*
import com.example.passwordmanager.ui.screens.LoginScreen

@Composable
fun Navigation(context: Context = LocalContext.current) {
    val navController = rememberNavController()
    var isAuthenticated by remember { mutableStateOf(false) }
    var showLogin by remember { mutableStateOf(false) }

    if (isAuthenticated) {
        NavHost(navController, startDestination = "passwords") {
            composable("passwords") { PasswordListScreen(navController) }
            composable("generate") { GeneratePasswordScreen() }
            composable("add") { AddEditPasswordScreen(navController, null) }
            composable("edit") {
                val entry = navController.previousBackStackEntry?.savedStateHandle?.get<PasswordEntry>("entry")
                AddEditPasswordScreen(navController, entry)
            }
            composable("detail") {
                val entry = navController.previousBackStackEntry?.savedStateHandle?.get<PasswordEntry>("entry")
                if (entry != null) {
                    PasswordDetailScreen(navController, entry)
                }
            }
        }
    } else if (showLogin){
        LoginScreen(onAuthenticated = {isAuthenticated = true})
    } else {
        WelcomeScreen(onStartLogin = {showLogin = true})
    }
}
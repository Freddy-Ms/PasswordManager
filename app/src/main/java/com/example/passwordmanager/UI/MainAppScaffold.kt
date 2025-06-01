package com.example.passwordmanager.UI

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.passwordmanager.UI.screens.*
import com.example.passwordmanager.data.PasswordEntry
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppScaffold(navController: NavHostController) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination?.route

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    text = "Menu",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(16.dp)
                )
                NavigationDrawerItem(
                    label = { Text("Show Passwords") },
                    icon = { Icon(Icons.Default.List, contentDescription = "Show Passwords") },
                    selected = currentDestination == "passwords",
                    onClick = {
                        scope.launch { drawerState.close() }
                        scope.launch {
                            navController.navigate("passwords") {
                                launchSingleTop = true
                                popUpTo("passwords") {
                                    inclusive = false
                                }
                            }
                        }
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
                NavigationDrawerItem(
                    label = { Text("Add Password") },
                    icon = { Icon(Icons.Default.Add, contentDescription = "Add Password") },
                    selected = currentDestination == "add",
                    onClick = {
                        navController.navigate("add") {
                            launchSingleTop = true
                        }
                        scope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
                NavigationDrawerItem(
                    label = { Text("Generate Password") },
                    icon = { Icon(Icons.Default.Lock, contentDescription = "Generate Password") },
                    selected = currentDestination == "generate",
                    onClick = {
                        navController.navigate("generate") {
                            launchSingleTop = true
                        }
                        scope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Password Manager") },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    if (drawerState.isClosed) drawerState.open()
                                    else drawerState.close()
                                }
                            }
                        ) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = "passwords",
                modifier = Modifier.padding(paddingValues)
            ) {
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
        }
    }
}

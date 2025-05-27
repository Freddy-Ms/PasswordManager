package com.example.passwordmanager.UI.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.passwordmanager.data.PasswordRepository
import com.example.passwordmanager.data.PasswordEntry

@Composable
fun PasswordListScreen(navController: NavController) {
    val context = LocalContext.current
    val repo = remember { PasswordRepository(context) }
    var passwords by remember { mutableStateOf(repo.getAllPasswords()) }
    var searchQuery by remember { mutableStateOf("") }

    val filteredPasswords = passwords.filter {
        it.service.contains(searchQuery, ignoreCase = true) ||
                it.login.contains(searchQuery, ignoreCase = true) ||
                it.password.contains(searchQuery, ignoreCase = true)
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Szukaj") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { navController.navigate("generate") }) {
            Text("Generuj hasło")
        }
        Spacer(Modifier.height(8.dp))
        Button(onClick = { navController.navigate("add") }) {
            Text("Dodaj hasło")
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(filteredPasswords) { entry ->
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)) {
                    Column(modifier = Modifier
                        .padding(16.dp)
                        .clickable {
                            navController.currentBackStackEntry?.savedStateHandle?.set("entry", entry)
                            navController.navigate("edit")
                        }) {
                        Text("Serwis: ${entry.service}")
                        Text("Login: ${entry.login}")
                        Text("Hasło: ${entry.password}")
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = {
                                repo.deletePassword(entry)
                                passwords = repo.getAllPasswords()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                        ) {
                            Text("Usuń")
                        }
                    }
                }
            }
        }
    }
}

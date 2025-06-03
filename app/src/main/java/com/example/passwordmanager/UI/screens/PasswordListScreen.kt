package com.example.passwordmanager.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.passwordmanager.data.PasswordRepository

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

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Szukaj") },
            placeholder = { Text("Search...") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(filteredPasswords) { entry ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .heightIn(min = 100.dp)
                        .clickable {
                            navController.currentBackStackEntry?.savedStateHandle?.set("entry", entry)
                            navController.navigate("detail")
                        }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Service: ${entry.service}", style = MaterialTheme.typography.titleMedium)
                            Text("Login: ${entry.login}", style = MaterialTheme.typography.bodyMedium)
                            Text("Password: ${entry.password}", style = MaterialTheme.typography.bodyMedium)
                        }
                        Button(
                            onClick = {
                                repo.deletePassword(entry)
                                passwords = repo.getAllPasswords()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                        ) {
                            Text("Delete")
                        }
                    }
                }
            }
        }
    }
}

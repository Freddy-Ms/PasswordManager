package com.example.passwordmanager.UI.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.passwordmanager.data.PasswordEntry
import com.example.passwordmanager.data.PasswordRepository

@Composable
fun PasswordDetailScreen(navController: NavController, entry: PasswordEntry) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Text("Serwis: ${entry.service}", style = MaterialTheme.typography.titleLarge)
        Text("Login: ${entry.login}")
        Text("Hasło: ${entry.password}")
        Text("Notatka: ${entry.note}")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            navController.currentBackStackEntry?.savedStateHandle?.set("entry", entry)
            navController.navigate("edit")
        }) {
            Text("Edytuj")
        }

        Spacer(modifier = Modifier.height(8.dp))

        val context = LocalContext.current
        val repo = remember { PasswordRepository(context) }

        Button(
            onClick = {
                repo.deletePassword(entry)
                navController.popBackStack("passwords", inclusive = false)
            },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
            Text("Usuń")
        }
    }
}

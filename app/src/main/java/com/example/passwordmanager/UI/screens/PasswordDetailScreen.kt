package com.example.passwordmanager.UI.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.passwordmanager.data.PasswordEntry
import com.example.passwordmanager.data.PasswordRepository

@Composable
fun PasswordDetailScreen(navController: NavController, entry: PasswordEntry) {
    val context = LocalContext.current
    val repo = remember { PasswordRepository(context) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Service: ${entry.service}",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text("Login: ${entry.login}")
        Text("Password: ${entry.password}")

        Spacer(modifier = Modifier.height(16.dp))

        Text("Note: ${entry.note}")

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Button(
                onClick = {
                    navController.currentBackStackEntry?.savedStateHandle?.set("entry", entry)
                    navController.navigate("edit")
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Edit")
            }

            Button(
                onClick = {
                    repo.deletePassword(entry)
                    navController.popBackStack("passwords", inclusive = false)
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                modifier = Modifier.weight(1f)
            ) {
                Text("Delete")
            }
        }
    }
}

package com.example.passwordmanager.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.passwordmanager.data.PasswordEntry
import com.example.passwordmanager.data.PasswordRepository

@Composable
fun AddEditPasswordScreen(navController: NavController, initialData: PasswordEntry?) {
    val context = LocalContext.current
    val repo = remember { PasswordRepository(context) }

    var service by remember { mutableStateOf(initialData?.service ?: "") }
    var login by remember { mutableStateOf(initialData?.login ?: "") }
    var password by remember { mutableStateOf(initialData?.password ?: "") }
    var note by remember { mutableStateOf(initialData?.note ?: "") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(
                value = service,
                onValueChange = { service = it },
                label = { Text("Service") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = login,
                onValueChange = { login = it },
                label = { Text("Login") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = note,
                onValueChange = { note = it },
                label = { Text("Note") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                maxLines = 6
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                val newEntry = PasswordEntry(
                    id = initialData?.id ?: 0,
                    service = service,
                    login = login,
                    password = password,
                    note = note
                )
                if (initialData == null) {
                    repo.addPassword(newEntry)
                } else {
                    repo.updatePassword(newEntry)
                    navController.popBackStack()
                }
                navController.popBackStack()
            }) {
                Text(if (initialData == null) "Save" else "Update")
            }


        }
    }
}

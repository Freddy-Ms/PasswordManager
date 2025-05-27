package com.example.passwordmanager.UI.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(value = service, onValueChange = { service = it }, label = { Text("Serwis") })
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = login, onValueChange = { login = it }, label = { Text("Login") })
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Hasło") })
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val newEntry = PasswordEntry(id = initialData?.id ?: 0, service = service, login = login, password = password)
            if (initialData == null) {
                repo.addPassword(newEntry)
            } else {
                repo.updatePassword(newEntry)
            }
            navController.popBackStack()
        }) {
            Text(if (initialData == null) "Zapisz" else "Zaktualizuj")
        }

        if (initialData != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                repo.deletePassword(initialData)
                navController.popBackStack()
            }, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)) {
                Text("Usuń")
            }
        }
    }
}
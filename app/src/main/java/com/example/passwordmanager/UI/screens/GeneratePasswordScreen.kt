package com.example.passwordmanager.UI.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlin.random.Random

@Composable
fun GeneratePasswordScreen() {
    val context = LocalContext.current
    var generatedPassword by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Wygenerowane hasło:", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = generatedPassword, style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            generatedPassword = generateRandomPassword()
        }) {
            Text("Generuj nowe")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("password", generatedPassword)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(context, "Skopiowano do schowka", Toast.LENGTH_SHORT).show()
        }) {
            Text("Kopiuj")
        }
    }
}

fun generateRandomPassword(length: Int = 12): String {
    val allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#\$%^&*()-_=+"
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}

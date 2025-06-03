package com.example.passwordmanager.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WelcomeScreen(onStartLogin: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                buildAnnotatedString {
                    append("Welcome in ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("PasswordManager")
                    }
                },
                style = MaterialTheme.typography.headlineLarge,
                fontSize = 32.sp
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text("• Securely store your passwords", fontSize = 20.sp)
            Text("• Attach notes to your accounts", fontSize = 20.sp)
            Text("• Generate strong and safe passwords", fontSize = 20.sp)
        }

        Button(
            onClick = onStartLogin,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text("Log In", fontSize = 18.sp)
        }
    }
}
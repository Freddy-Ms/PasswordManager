package com.example.passwordmanager.data

import java.io.Serializable

data class PasswordEntry(
    val id: Int = 0,
    val service: String,
    val login: String,
    val password: String,
    val note: String = ""
) : Serializable


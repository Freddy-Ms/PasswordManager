package com.example.passwordmanager

import android.os.Bundle
import androidx.fragment.app.FragmentActivity

import androidx.activity.compose.setContent
import com.example.passwordmanager.UI.Navigation


class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Navigation()
        }
    }
}



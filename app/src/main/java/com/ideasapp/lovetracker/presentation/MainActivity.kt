package com.ideasapp.lovetracker.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import com.ideasapp.lovetracker.presentation.elements.StartScreen
import com.ideasapp.lovetracker.ui.theme.LoveTrackerTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        subscribeToTopicGlobal()
        setContent {
            LoveTrackerTheme {
                StartScreen()
            }
        }
    }
    private fun subscribeToTopicGlobal() {
        FirebaseMessaging
            .getInstance()
            .subscribeToTopic("global")
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("FCM", "Success subscribeToTopic(\"global\")")
                } else {
                    Log.e("FCM", "Error", task.exception)
                }
            }
    }
}


package com.ideasapp.lovetracker.presentation.activity
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.ideasapp.lovetracker.domain.entity.NotificationData
import com.ideasapp.lovetracker.presentation.elements.StartScreen
import com.ideasapp.lovetracker.presentation.models.MainViewModel
import com.ideasapp.lovetracker.ui.theme.LoveTrackerTheme


class MainActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private val viewModel: MainViewModel by viewModels()
    private lateinit var dbCollection: CollectionReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        viewModel.subscribeToLoveTopic()
        requestNotificationPermission()
        setContent {
            LoveTrackerTheme {
                StartScreen(onSendClick = { notificationData ->
                    viewModel.sendNotificationToTopicLove(this, notificationData)
                })
            }
        }
        db = FirebaseFirestore.getInstance()
        dbCollection = FirebaseFirestore.getInstance().collection("Notifications")
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    POST_NOTIFICATIONS_PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    companion object {
        const val TOPIC = "love"
        const val POST_NOTIFICATIONS_PERMISSION_REQUEST_CODE = 1
    }
}


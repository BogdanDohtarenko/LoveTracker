package com.ideasapp.lovetracker.presentation.activity
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.ideasapp.lovetracker.R
import com.ideasapp.lovetracker.presentation.elements.StartScreen
import com.ideasapp.lovetracker.ui.theme.LoveTrackerTheme
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var dbCollection: CollectionReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        subscribeToLoveTopic()
        setContent {
            LoveTrackerTheme {
                StartScreen(onClick = {sendNotificationToAllDevices()})
            }
        }
        db = FirebaseFirestore.getInstance()
        dbCollection = FirebaseFirestore.getInstance().collection("Notifications")
    }

    private fun subscribeToLoveTopic() {
        FirebaseMessaging.getInstance().subscribeToTopic("allUsers")
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("FCM", "Successfully subscribed to topic")
                } else {
                    Log.e("FCM", "Failed to subscribe to topic")
                }
            }
    }

    private fun sendNotificationToAllDevices() {
        val url = "https://fcm.googleapis.com/fcm/send"
        val serverKey = R.string.server_key

        val jsonData = """
    {
        "to": "/topics/TOPIC",
        "notification": {
            "title": "Notification",
            "body": "This is a broadcast message to all users."
        }
    }
    """

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .post(jsonData.toRequestBody("application/json".toMediaType()))
            .addHeader("Authorization", "key=$serverKey")
            .build()

        client.newCall(request).execute().use { response ->
            if (response.isSuccessful) {
                Log.d("FCM", "Notification successfully sent to topic")
            } else {
                Log.e("FCM", "Failed to send notification: ${response.body?.string()}")
            }
        }
    }

    companion object {
        const val TOPIC = "love"
    }
}

data class NotificationModel(
    val token: String,
    val title: String,
    val body: String
)
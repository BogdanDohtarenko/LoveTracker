package com.ideasapp.lovetracker.presentation.activity
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
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
import org.json.JSONObject
import com.android.volley.Request.Method
import com.android.volley.Request.Method.POST
import com.android.volley.Response
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
        val queue = Volley.newRequestQueue(this)
        val url = "https://fcm.googleapis.com/fcm/send"
        val serverKey = resources.getString(R.string.server_key)

        val jsonData = JSONObject().apply {
            put("to", "/topics/TOPIC")
            put("notification", JSONObject().apply {
                put("title", "Notification")
                put("body", "This is a broadcast message to all users.")
            })
        }

        val jsonObjectRequest = object : JsonObjectRequest(POST, url, jsonData,
            { response ->
                Log.d("FCM", "Notification successfully sent to topic: $response")
            },
            { error ->
                if (error.networkResponse?.statusCode == 401) {
                    Log.e("FCM", "Failed to send notification: Unauthorized. Check server key.")
                } else {
                    Log.e("FCM", "Failed to send notification: ${error.message}")
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                headers["Authorization"] = "key=$serverKey"
                return headers
            }
        }

        queue.add(jsonObjectRequest)

        queue.add(jsonObjectRequest)
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
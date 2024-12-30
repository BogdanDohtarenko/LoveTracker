package com.ideasapp.lovetracker.presentation.activity
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.ideasapp.lovetracker.R
import com.ideasapp.lovetracker.presentation.elements.StartScreen
import com.ideasapp.lovetracker.ui.theme.LoveTrackerTheme
import org.json.JSONException
import org.json.JSONObject
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var dbCollection: CollectionReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        subscribeToLoveTopic()
        requestNotificationPermissionIfNeeded()
        setContent {
            LoveTrackerTheme {
                StartScreen(onClick = {sendNotificationToAllDevices()})
            }
        }
        db = FirebaseFirestore.getInstance()
        dbCollection = FirebaseFirestore.getInstance().collection("Notifications")
    }

    private fun subscribeToLoveTopic() {
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("FCM", "Successfully subscribed to topic")
                } else {
                    Log.e("FCM", "Failed to subscribe to topic")
                }
            }
    }

    private fun requestNotificationPermissionIfNeeded() {
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun sendNotificationToAllDevices() {
        val mRequestQue = Volley.newRequestQueue(this)

        val json = JSONObject()
        try {
            json.put("to", "/topics/love")
            val notificationObj = JSONObject()
            notificationObj.put("title", "new Order")
            notificationObj.put("body", "New")
            // Replace "notification" with "data" if you want to send custom data
            json.put("notification", notificationObj)

            val url = "https://fcm.googleapis.com/fcm/send"
            val request = object : JsonObjectRequest(
                Request.Method.POST, url, json,
                { response ->
                    Log.d("MUR", "onResponse: $response")
                },
                { error ->
                    Log.d("MUR", "onError: ${error.networkResponse}")
                }
            ) {
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers["content-type"] = "application/json"
                    headers["authorization"] = "key=${R.string.server_key}"
                    return headers
                }
            }

            mRequestQue.add(request)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    companion object {
        const val TOPIC = "love"
        const val POST_NOTIFICATIONS_PERMISSION_REQUEST_CODE = 1
    }
}

data class NotificationModel(
    val token: String,
    val title: String,
    val body: String
)
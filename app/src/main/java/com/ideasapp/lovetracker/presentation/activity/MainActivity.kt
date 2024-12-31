package com.ideasapp.lovetracker.presentation.activity
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.res.stringResource
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
import com.ideasapp.lovetracker.domain.repository.FcmService
import com.ideasapp.lovetracker.presentation.elements.StartScreen
import com.ideasapp.lovetracker.ui.theme.LoveTrackerTheme
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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

    private fun sendNotificationToAllDevices() {
        // 1. Get the Firebase Server Key (from your Firebase project settings)
        val serverKey = getString(R.string.server_key) // Replace with your actual key
        // 2. Create Retrofit instance
        val retrofit = Retrofit.Builder()
            .baseUrl("https://fcm.googleapis.com/fcm/send/") // Use your project ID
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        // 3. Create FCM service instance
        val fcmService = retrofit.create(FcmService::class.java)
        // 4. Prepare notification data
        val notificationData = NotificationData(
            title = "New Order",
            body = "New"
        )
        // 5. Prepare FCM request
        val fcmRequest = FcmRequest(
            to = "/topics/love", // Use your topic name
            notification = notificationData
        )
        // 6. Send the notification
        val call = fcmService.sendNotification(fcmRequest)
        call.enqueue(object : Callback<FcmResponse> {
            override fun onResponse(call: Call<FcmResponse>, response: Response<FcmResponse>) {
                if (response.isSuccessful) {
                    val fcmResponse = response.body()
                    Log.d("FCM", "Notification sent successfully: $fcmResponse")
                    // Handle success (e.g., update UI, log event)
                } else {
                    Log.d("FCM", "Failed to send notification: ${response.errorBody()?.string()}")
                    // Handle failure (e.g., show error message, retry)
                }
            }

            override fun onFailure(call: Call<FcmResponse>, t: Throwable) {
                Log.d("FCM", "Error sending notification: ${t.message}")
                // Handle network errors (e.g., show error message, retry)
            }
        })
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

data class FcmRequest(
    val to: String,
    val notification: NotificationData
)

data class NotificationData(
    val title: String,
    val body: String
)

data class FcmResponse(
    val message_id: String
)

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
import com.ideasapp.lovetracker.data.FirebaseTokenManager
import com.ideasapp.lovetracker.domain.repository.FcmService
import com.ideasapp.lovetracker.presentation.elements.StartScreen
import com.ideasapp.lovetracker.ui.theme.LoveTrackerTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Interceptor
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
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val token = withContext(Dispatchers.IO) {
                    FirebaseTokenManager.getAccessToken(this@MainActivity)
                }

                val retrofit = Retrofit.Builder()
                    .baseUrl("https://fcm.googleapis.com/fcm/send/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                val fcmService = retrofit.create(FcmService::class.java)

                val notificationData = NotificationData(
                    title = "New Order",
                    body = "New"
                )

                val fcmRequest = FcmRequest(
                    to = "/topics/love",
                    notification = notificationData
                )

                val authorizationHeader = "Bearer $token"

                fcmService.sendNotification(authorizationHeader, fcmRequest)
                    .enqueue(object : Callback<FcmResponse> {
                        override fun onResponse(call: Call<FcmResponse>, response: Response<FcmResponse>) {
                            if (response.isSuccessful) {
                                val fcmResponse = response.body()
                                Log.d("FCM", "Notification sent successfully: $fcmResponse")
                            } else {
                                Log.d("FCM", "Failed to send notification: ${response.errorBody()?.string()}")
                            }
                        }

                        override fun onFailure(call: Call<FcmResponse>, t: Throwable) {
                            Log.d("FCM", "Error sending notification: ${t.message}")
                        }
                    })
            } catch (e: Exception) {
                Log.e("FCM", "Error: ${e.message}")
            }
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

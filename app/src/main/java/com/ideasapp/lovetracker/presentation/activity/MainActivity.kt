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
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.annotations.SerializedName
import com.ideasapp.lovetracker.data.FirebaseTokenManager
import com.ideasapp.lovetracker.domain.repository.FcmService
import com.ideasapp.lovetracker.presentation.elements.StartScreen
import com.ideasapp.lovetracker.ui.theme.LoveTrackerTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.Serializable


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
                Log.d("FCM", "Authorization Header: Bearer $token")
                val loggingInterceptor = HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
                val httpClient = OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build()
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://fcm.googleapis.com/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .build()
                val fcmService = retrofit.create(FcmService::class.java)

                val notificationData = NotificationData(
                    title = "Your partner",
                    body = "Miss you"
                )

                val messageData = MessageData(
                    topic = "love",
                    notification = notificationData
                )

                val fcmRequest = FcmRequest(message = messageData)

                val authorizationHeader = "Bearer $token"

                // Send the notification
                fcmService.sendNotification(authorizationHeader, fcmRequest)
                    .enqueue(object : Callback<FcmResponse> {
                        override fun onResponse(call: Call<FcmResponse>, response: Response<FcmResponse>) {
                            if (response.isSuccessful) {
                                val fcmResponse = response.body()
                                Log.d("FCM", "Notification sent successfully: $fcmResponse")
                            } else {
                                val errorBody = response.errorBody()?.string()
                                Log.d("FCM", "Failed to send notification: HTTP ${response.code()} - ${response.message()}")
                                Log.d("FCM", "Error body: $errorBody")
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
    @SerializedName("message")
    val message: MessageData
) : Serializable

data class MessageData(
    @SerializedName("topic")
    val topic: String,

    @SerializedName("notification")
    val notification: NotificationData
) : Serializable

data class NotificationData(
    @SerializedName("title")
    val title: String,

    @SerializedName("body")
    val body: String
) : Serializable

data class FcmResponse(
    val message_id: String
)

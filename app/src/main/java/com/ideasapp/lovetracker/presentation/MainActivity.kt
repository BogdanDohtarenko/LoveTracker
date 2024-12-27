package com.ideasapp.lovetracker.presentation
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.R
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.ideasapp.lovetracker.presentation.elements.StartScreen
import com.ideasapp.lovetracker.ui.theme.LoveTrackerTheme
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var dbCollection: CollectionReference
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var id: String
    private var token: String = "DefaultToken"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            LoveTrackerTheme {
                StartScreen(onClick = {sendNotificationToAllDevices()})
            }
        }
        db = FirebaseFirestore.getInstance()
        dbCollection = FirebaseFirestore.getInstance().collection("Notifications")
        checkAndSaveUserId(this)
    }

    private fun checkAndSaveUserId(context: Context) {
        sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("user_id", null)
        if (userId == null) {
            val currentTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
            sharedPreferences.edit().putString("user_id", currentTime).apply()
            id = currentTime
        } else {
            id = userId
        }
    }

    private fun sendNotificationToAllDevices() {
        Log.d("FCM", "sendNotificationToAllDevices()")
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.d("FCM", "failed token $token")
                return@addOnCompleteListener
            }

            token = task.result
            val notificationMessage = NotificationModel(
                token = token,
                title = "New Notification",
                body = "This is a broadcast message to all users."
            )

            dbCollection.document(id).set(notificationMessage)
                .addOnSuccessListener {
                    Log.d("FCM", "Notification sent successfully")
                }
                .addOnFailureListener { e ->
                    Log.e("FCM", "Failed to send notification", e)
                }

        }
    }
}

data class NotificationModel(
    val token: String,
    val title: String,
    val body: String
)
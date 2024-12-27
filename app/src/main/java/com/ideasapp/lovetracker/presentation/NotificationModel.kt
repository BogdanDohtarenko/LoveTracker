
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage
import com.google.firestore.v1.DocumentChange

// Data class for notifications (adjust as needed)
data class NotificationModel(val title: String, val body: String)

class NotificationManager {

    private val db = FirebaseFirestore.getInstance()
    private val notificationsCollection = db.collection("notifications") // Collection for notifications
    private val tokensCollection = db.collection("fcmTokens") // Collection for FCM tokens (user_id -> token)

    init {
        // Listen for changes in the notifications collection
        notificationsCollection.addSnapshotListener(EventListener<QuerySnapshot> { snapshots, e ->
            if (e != null) {
                Log.w("FCM", "Listen failed.", e)
                return@EventListener
            }

            for (documentChange in snapshots!!.documentChanges) {
                if (documentChange.type == DocumentChange.Type.ADDED) {
                    val notification = documentChange.document.toObject(NotificationModel::class.java)
                    sendNotificationToAllDevices(notification)
                }
            }
        })
    }


    private fun sendNotificationToAllDevices(notification: NotificationModel) {
        //Retrieve all tokens
        tokensCollection.get().addOnSuccessListener { querySnapshot ->
            for (document in querySnapshot.documents) {
                val token = document.getString("token") ?: continue //Handle missing tokens gracefully

                // Send notification using FCM
                sendNotification(token, notification.title, notification.body)
            }
        }.addOnFailureListener { e ->
            Log.e("FCM", "Error retrieving tokens", e)
        }
    }

    private fun sendNotification(token: String, title: String, body: String) {
        // Construct your FCM message here.  This is a simplified example.
        val message = RemoteMessage.Builder(token)
            .setNotification(RemoteMessage.Notification(title, body))
            .build()

        FirebaseMessaging.getInstance().send(message)
            .addOnSuccessListener {
                Log.d("FCM", "Message sent successfully")
            }
            .addOnFailureListener { e ->
                Log.e("FCM", "Error sending message", e)
            }
    }
}
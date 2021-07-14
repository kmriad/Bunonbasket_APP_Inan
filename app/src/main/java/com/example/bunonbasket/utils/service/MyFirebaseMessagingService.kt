package com.example.bunonbasket.utils.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.bunonbasket.R
import com.example.bunonbasket.ui.component.HomeActivity
import com.example.bunonbasket.utils.Constants
import com.example.bunonbasket.utils.Constants.CHANNEL_DESCRIPTION
import com.example.bunonbasket.utils.Constants.CHANNEL_ID
import com.example.bunonbasket.utils.Constants.CHANNEL_NAME
import com.example.bunonbasket.utils.Constants.NOTIFICATION_ID
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MyFirebaseMessagingService : FirebaseMessagingService() {

    val TAG = "MyFirebaseMessaging"
    override fun onNewToken(token: String) {
        // Log.d(TAG, "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String) {
        Log.d("MyFirebaseMessaging", token)
        try {
            val sharedPref =
                applicationContext.getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPref.edit()
            editor.putString(Constants.DEVICE_AUTH, token)
            editor.apply()
            val value = editor.commit()
            Log.d("MyFirebaseMessaging", value.toString())
        } catch (e: Exception) {
            Log.d("MyFirebaseMessaging", e.message!!)
        }
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use WorkManager.
               // scheduleJob()
            } else {
                // Handle message within 10 seconds
              //  handleNow()
            }
        }

        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
        }

        createNotificationChannel(remoteMessage.notification!!.title!!, remoteMessage.notification!!.body.toString())
    }

    private fun createNotificationChannel(textTitle: String, textContent: String) {
        val intent = Intent(this, HomeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_bell)
            .setContentTitle(textTitle)
            .setContentIntent(pendingIntent)
            .setContentText(textContent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = CHANNEL_NAME
            val descriptionText = CHANNEL_DESCRIPTION
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(NOTIFICATION_ID, builder.build())
        }
    }
}
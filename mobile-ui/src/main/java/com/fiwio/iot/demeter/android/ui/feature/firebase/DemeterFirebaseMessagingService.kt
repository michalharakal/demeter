package com.fiwio.iot.demeter.android.ui.feature.firebase

import android.util.Base64
import com.fiwio.iot.demeter.android.ui.ext.getAppComponent
import com.fiwio.iot.demeter.android.ui.feature.firebase.di.FirebaseComponent
import com.fiwio.iot.demeter.android.ui.feature.firebase.di.FirebaseModule
import com.fiwio.iot.demeter.domain.features.messages.ShowNotification
import com.fiwio.iot.demeter.domain.features.messages.StoreMessage
import com.fiwio.iot.demeter.domain.model.Message
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import io.reactivex.observers.DisposableSingleObserver
import mu.KotlinLogging
import org.joda.time.DateTime
import javax.inject.Inject

private val logger = KotlinLogging.logger {}

class DemeterFirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var storeMessage: StoreMessage

    @Inject
    lateinit var showNotification: ShowNotification

    inline fun <reified T> DemeterFirebaseMessagingService.injector() = lazy(LazyThreadSafetyMode.NONE) {
        component = getAppComponent().plus(FirebaseModule())
        component.inject(this)
    }


    private val injection by injector<DemeterFirebaseMessagingService>()


    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {

        logger.debug { "onMessageReceived" }

        if (injection == null) {
            return
        }

        if (remoteMessage == null) {
            return
        }

        // notification with payload
        if (remoteMessage.data.isNotEmpty()) {
            handleNotificationWithPayload(remoteMessage.data)
        }
    }


    private fun handleNotificationWithPayload(data: MutableMap<String, String>) {
        val title = String(Base64.decode(data["title"], Base64.DEFAULT))
        val message = String(Base64.decode(data["message"], Base64.DEFAULT))
        storeMessage.execute(StoreExecutionObserver(), Message(DateTime.now(), title, message))

        //Log.d(TAG, message)
        /*
        if ((message.startsWith("PIN")) || (message.startsWith("ACTION"))) {
            var mBuilder: NotificationCompat.Builder = NotificationCompat.Builder(this, "cml")
                    .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            val notificationManager = NotificationManagerCompat.from(this)

// notificationId is a unique int for each notification that you must define
            notificationManager.notify(counter++, mBuilder.build())
            */

    }


    class StoreExecutionObserver : DisposableSingleObserver<Unit>() {
        override fun onSuccess(t: Unit) {

        }

        override fun onError(e: Throwable) {

        }

    }


    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private fun sendNotification(messageBody: String) {
        /*
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT)

        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle("FCM Message")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
        */
    }

    lateinit var component: FirebaseComponent

    override fun getSystemService(name: String?): Any {
        return when (name) {
            "component" -> component
            else -> super.getSystemService(name)
        }
    }

}

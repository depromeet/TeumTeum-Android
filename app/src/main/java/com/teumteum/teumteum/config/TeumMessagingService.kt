package com.teumteum.teumteum.config

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.teumteum.data.model.request.toDeviceToken
import com.teumteum.data.service.UserService
import com.teumteum.domain.TeumTeumDataStore
import com.teumteum.domain.entity.Message
import com.teumteum.domain.repository.UserRepository
import com.teumteum.teumteum.R
import com.teumteum.teumteum.presentation.splash.SplashActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class TeumMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var dataStore: TeumTeumDataStore

    @Inject
    lateinit var userService: UserService

    @Inject
    lateinit var userRepository: UserRepository

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        dataStore.deviceToken = token

        if (dataStore.userToken != "") {
            GlobalScope.launch {
                kotlin.runCatching {
                    userService.patchDeviceToken(
                        token.toDeviceToken()
                    )
                }.onFailure {
                    Timber.e(it.message)
                    userService.postDeviceToken(
                        token.toDeviceToken()
                    )
                }
            }
        }
    }

    override fun handleIntent(intent: Intent?) {
        super.handleIntent(intent)

        if (dataStore.onNotification) {
            if (dataStore.isLogin) {
                intent?.let {
                    val alertMessage = Message("", "", "")
                    alertMessage.title = intent.getStringExtra("title").toString()
                    alertMessage.body = intent.getStringExtra("body").toString()
                    alertMessage.type = intent.getStringExtra("type").toString()
                    if (alertMessage.type == END_MEETING) {
                        alertMessage.meetingId = intent.getStringExtra("meetingId")?.toLong()
                        alertMessage.participants =
                            intent.getStringExtra("participants")?.split(",")?.map { it.toInt() }
                        val userId = userRepository.getUserInfo()?.id?.toInt()
                        if (alertMessage.participants?.contains(userId) == true && alertMessage.participants?.size!! > 2)
                            sendNotificationAlarm(alertMessage)
                    } else if (alertMessage.title.isNotEmpty()) sendNotificationAlarm(alertMessage)
                }
            }
        }
    }

    private fun sendNotificationAlarm(message: Message) {
        val requestCode = (System.currentTimeMillis() % 10000).toInt()
        val intent = Intent(this, SplashActivity::class.java)
        intent.putExtra("isFromAlarm", true)
        intent.putExtra("message", message)
        Timber.tag("teum-alerts").d("message: ${message.title}, ${message.type}, ${message.body}, ${message.meetingId}, ${message.participants.toString()}")
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent =
            PendingIntent.getActivity(
                this, requestCode, intent, PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_MUTABLE
            )
        val channelId = getString(R.string.teum_notification_channel_id)
        val notificationBuilder = NotificationCompat.Builder(this, channelId).setSmallIcon(R.mipmap.ic_teum_teum_launcher_round)
            .setContentTitle(message.title).setContentText(message.body)
            .setPriority(NotificationCompat.PRIORITY_HIGH).setAutoCancel(true)
            .setContentIntent(pendingIntent)

        val channel = NotificationChannel(channelId, "teumteum", NotificationManager.IMPORTANCE_HIGH)
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        notificationManager.run {
            createNotificationChannel(channel)
            notify(requestCode, notificationBuilder.build())
        }
    }

    companion object {
        const val EMPTY = "null"
        private const val END_MEETING = "END_MEETING"
    }
}
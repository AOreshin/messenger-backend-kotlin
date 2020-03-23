package com.github.aoreshin.android.messengerbackend.service

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.BatchResponse
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import org.springframework.stereotype.Service
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@Service
class FirebaseService(val fcmSettings: FcmSettings) {
    init {
        val p: Path = Paths.get(fcmSettings.serviceAccountFile)
        val options = FirebaseOptions
            .builder()
            .setCredentials(GoogleCredentials.fromStream(Files.newInputStream(p)))
            .build()

        FirebaseApp.initializeApp(options)
    }

    fun updateReceiverChat(
        message: com.github.aoreshin.android.messengerbackend.models.Message,
        appTokens: Set<String>?
    ) : BatchResponse {
        val messaging = FirebaseMessaging
            .getInstance()

        val messages = appTokens?.map { Message
                .builder()
                .setToken(it)
                .putData("body", message.body)
                .build();
            }

        return messaging
            .sendAllAsync(messages)
            .get()
    }
}
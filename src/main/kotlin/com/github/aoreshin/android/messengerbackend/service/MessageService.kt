package com.github.aoreshin.android.messengerbackend.service

import com.github.aoreshin.android.messengerbackend.models.Message
import com.github.aoreshin.android.messengerbackend.models.User

interface MessageService {
    fun sendMessage(sender: User, recipientId: Long, messageText: String): Message
}
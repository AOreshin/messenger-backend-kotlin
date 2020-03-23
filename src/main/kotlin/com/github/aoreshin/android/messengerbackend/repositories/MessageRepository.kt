package com.github.aoreshin.android.messengerbackend.repositories

import com.github.aoreshin.android.messengerbackend.models.Message
import org.springframework.data.repository.CrudRepository

interface MessageRepository : CrudRepository<Message, Long> {
    fun findByConversationId(conversationId: Long): List<Message>
}
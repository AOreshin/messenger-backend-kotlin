package com.github.aoreshin.android.messengerbackend.service

import com.github.aoreshin.android.messengerbackend.models.Conversation
import com.github.aoreshin.android.messengerbackend.models.User

interface ConversationService {
    fun createConversation(userA: User, userB: User): Conversation
    fun conversationExists(userA: User, userB: User): Boolean
    fun getConversation(userA: User, userB: User): Conversation?
    fun retrieveThread(conversationId: Long): Conversation
    fun listUserConversations(userId: Long): List<Conversation>
    fun nameSecondParty(conversation: Conversation, userId: Long): String
}
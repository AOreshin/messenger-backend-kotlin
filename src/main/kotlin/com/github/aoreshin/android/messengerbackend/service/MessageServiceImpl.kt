package com.github.aoreshin.android.messengerbackend.service

import com.github.aoreshin.android.messengerbackend.exceptions.MessageEmptyException
import com.github.aoreshin.android.messengerbackend.exceptions.MessageRecipientInvalidException
import com.github.aoreshin.android.messengerbackend.models.Conversation
import com.github.aoreshin.android.messengerbackend.models.Message
import com.github.aoreshin.android.messengerbackend.models.User
import com.github.aoreshin.android.messengerbackend.repositories.ConversationRepository
import com.github.aoreshin.android.messengerbackend.repositories.MessageRepository
import com.github.aoreshin.android.messengerbackend.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class MessageServiceImpl(val repository: MessageRepository,
                         val conversationRepository: ConversationRepository,
                         val conversationService: ConversationService,
                         val firebaseService: FirebaseService,
                         val userRepository: UserRepository) : MessageService {
    override fun sendMessage(sender: User, recipientId: Long, messageText: String): Message {
        val optional = userRepository.findById(recipientId)

        if (optional.isPresent) {
            val recipient = optional.get()

            if (messageText.isNotEmpty()) {
                val conversation: Conversation? = if (conversationService.conversationExists(sender, recipient)) {
                    conversationService.getConversation(sender, recipient)
                } else {
                    conversationService.createConversation(sender, recipient)
                }
                conversation?.let { conversationRepository.save(it) }
                val message = Message(sender, recipient, messageText, conversation)
                repository.save(message)

                firebaseService.updateReceiverChat(message, recipient.appTokens)
                return message
            }
        } else {
            throw MessageRecipientInvalidException("The recipient id '$recipientId' is invalid.")
        }

        throw MessageEmptyException()
    }
}
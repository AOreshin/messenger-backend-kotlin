package com.github.aoreshin.android.messengerbackend.components

import com.github.aoreshin.android.messengerbackend.helpers.objects.ConversationListVO
import com.github.aoreshin.android.messengerbackend.helpers.objects.ConversationVO
import com.github.aoreshin.android.messengerbackend.helpers.objects.MessageVO
import com.github.aoreshin.android.messengerbackend.models.Conversation
import com.github.aoreshin.android.messengerbackend.service.ConversationServiceImpl
import org.springframework.stereotype.Component

@Component
class ConversationAssembler(val conversationService:
                            ConversationServiceImpl,
                            val messageAssembler: MessageAssembler) {
    fun toConversationVO(conversation: Conversation, userId: Long): ConversationVO {
        val conversationMessages: ArrayList<MessageVO> = ArrayList()
        conversation.messages?.mapTo(conversationMessages) { messageAssembler.toMessageVO(it) }
        return ConversationVO(conversation.id, conversationService.nameSecondParty(conversation, userId), conversationMessages)
    }

    fun toConversationListVO(conversations: List<Conversation>, userId: Long): ConversationListVO {
        val conversationVOList = conversations.map { toConversationVO(it, userId) }
        return ConversationListVO(conversationVOList)
    }
}
package com.github.aoreshin.android.messengerbackend.models

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Message(
    sender: User,
    recipient: User,
    messageText: String,
    conversation: Conversation?
) {
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    var sender: User? = sender

    @ManyToOne(optional = false)
    @JoinColumn(name = "recipient_id", referencedColumnName = "id")
    var recipient: User? = recipient

    var body: String? = messageText

    @ManyToOne(optional = false)
    @JoinColumn(name = "conversation_id", referencedColumnName = "id")
    var conversation: Conversation? = conversation

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    @DateTimeFormat
    var createdAt: LocalDateTime = LocalDateTime.now()
}
package com.github.aoreshin.android.messengerbackend.models

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Conversation(sender: User, recipient: User) {
    @ManyToOne(optional = false)
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    var sender: User? = sender

    @ManyToOne(optional = false)
    @JoinColumn(name = "recipient_id", referencedColumnName = "id")
    var recipient: User? = recipient

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    @DateTimeFormat
    var createdAt: LocalDateTime = LocalDateTime.now()

    @OneToMany(mappedBy = "conversation", targetEntity = Message::class)
    var messages: Collection<Message>? = null
}
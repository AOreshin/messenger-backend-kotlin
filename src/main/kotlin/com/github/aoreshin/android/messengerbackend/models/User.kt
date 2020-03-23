package com.github.aoreshin.android.messengerbackend.models

import com.github.aoreshin.android.messengerbackend.listeners.UserListener
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

@Entity
@Table(name = "users")
@EntityListeners(UserListener::class)
class User {
    @Column(unique = true)
    @Size(min = 2)
    var username: String = ""

    @Size(min = 10)
    @Column(unique = true)
    @Pattern(regexp = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$")
    var phoneNumber: String = ""

    @Size(min = 5, max = 255)
    var password: String = ""

    var status: String = ""

    @Column(unique = true)
    var appTokens: MutableSet<String> = mutableSetOf()

    @Pattern(regexp = "\\A(activated|deactivated)\\z")
    var accountStatus: String = "activated"

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    @DateTimeFormat
    var createdAt: LocalDateTime = LocalDateTime.now()

    @OneToMany(mappedBy = "sender", targetEntity = Message::class)
    private var sentMessages: Collection<Message>? = null

    @OneToMany(mappedBy = "recipient", targetEntity = Message::class)
    private var receivedMessages: Collection<Message>? = null
}
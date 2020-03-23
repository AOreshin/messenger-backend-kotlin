package com.github.aoreshin.android.messengerbackend.repositories

import com.github.aoreshin.android.messengerbackend.models.User
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Long> {
    fun findByUsername(username: String): User?
    fun findByPhoneNumber(phoneNumber: String): User?
}
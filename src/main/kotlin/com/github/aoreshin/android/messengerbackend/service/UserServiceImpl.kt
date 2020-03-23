package com.github.aoreshin.android.messengerbackend.service

import com.github.aoreshin.android.messengerbackend.exceptions.InvalidUserIdException
import com.github.aoreshin.android.messengerbackend.exceptions.UserStatusEmptyException
import com.github.aoreshin.android.messengerbackend.exceptions.UsernameUnavailableException
import com.github.aoreshin.android.messengerbackend.models.User
import com.github.aoreshin.android.messengerbackend.repositories.UserRepository
import org.springframework.stereotype.Service
import java.lang.RuntimeException

@Service
class UserServiceImpl(val repository: UserRepository) : UserService {
    @Throws(UsernameUnavailableException::class)
    override fun attemptRegistration(userDetails: User): User {
        if (!usernameExists(userDetails.username)) {
            val user = User()
            user.username = userDetails.username
            user.phoneNumber = userDetails.phoneNumber
            user.password = userDetails.password
            repository.save(user)
            obscurePassword(user)
            return user
        }

        throw UsernameUnavailableException("The username ${userDetails.username} is unavailable.")
    }

    override fun listUsers(currentUser: User): List<User> {
        return repository.findAll().mapTo(ArrayList(), { it }).filter{ it != currentUser }
    }

    @Throws(UserStatusEmptyException::class)
    override fun updateUserStatus(currentUser: User, updateDetails: User): User {
        if (updateDetails.status.isNotEmpty()) {
            currentUser.status = updateDetails.status
            repository.save(currentUser)
            return currentUser
        }

        throw UserStatusEmptyException()
    }

    override fun updateUserToken(currentUser: User, userUpdate: User): User {
        if (userUpdate.appTokens.isNotEmpty()) {
            currentUser.appTokens.clear()
            currentUser.appTokens.addAll(userUpdate.appTokens)
            repository.save(currentUser)
            return currentUser
        }

        throw RuntimeException("User app tokens are empty!")
    }

    override fun retrieveUserData(username: String): User? {
        val user = repository.findByUsername(username)
        obscurePassword(user)
        return user
    }

    @Throws(InvalidUserIdException::class)
    override fun retrieveUserData(id: Long): User? {
        val userOptional = repository.findById(id)
        if (userOptional.isPresent) {
            val user = userOptional.get()
            obscurePassword(user)
            return user
        }

        throw InvalidUserIdException("A user with an id of '$id' does not exist.")
    }

    override fun usernameExists(username: String): Boolean {
        return repository.findByUsername(username) != null
    }

    private fun obscurePassword(user: User?) {
        user?.password = "XXX XXXX XXX"
    }
}
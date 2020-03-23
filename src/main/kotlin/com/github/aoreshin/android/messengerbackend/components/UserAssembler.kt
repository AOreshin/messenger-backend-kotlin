package com.github.aoreshin.android.messengerbackend.components

import com.github.aoreshin.android.messengerbackend.helpers.objects.UserListVO
import com.github.aoreshin.android.messengerbackend.helpers.objects.UserVO
import com.github.aoreshin.android.messengerbackend.models.User
import org.springframework.stereotype.Component

@Component
class UserAssembler {
    fun toUserVO(user: User): UserVO {
        return UserVO(user.id, user.username, user.phoneNumber,
            user.status, user.createdAt.toString())
    }

    fun toUserListVO(users: List<User>): UserListVO {
        val userVOList = users.map { toUserVO(it) }
        return UserListVO(userVOList)
    }
}
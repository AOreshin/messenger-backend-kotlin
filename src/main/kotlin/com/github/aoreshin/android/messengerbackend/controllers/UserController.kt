package com.github.aoreshin.android.messengerbackend.controllers

import com.github.aoreshin.android.messengerbackend.components.UserAssembler
import com.github.aoreshin.android.messengerbackend.helpers.objects.UserListVO
import com.github.aoreshin.android.messengerbackend.helpers.objects.UserVO
import com.github.aoreshin.android.messengerbackend.models.User
import com.github.aoreshin.android.messengerbackend.repositories.UserRepository
import com.github.aoreshin.android.messengerbackend.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/users")
class UserController(val userService: UserService, val userRepository: UserRepository, val userAssembler: UserAssembler) {
    @PostMapping
    @RequestMapping("/registration")
    fun registration(@Validated @RequestBody user: User): ResponseEntity<UserVO> {
        val user = userService.attemptRegistration(user);
        return ResponseEntity.ok(userAssembler.toUserVO(user))
    }

    @GetMapping
    @RequestMapping("/{user_id}")
    fun show(@PathVariable("user_id") userId: Long): ResponseEntity<UserVO> {
        val user = userRepository.findById(userId).orElseThrow()
        return ResponseEntity.ok(userAssembler.toUserVO(user))
    }

    @GetMapping
    @RequestMapping("/details")
    fun echoDetails(request: HttpServletRequest): ResponseEntity<UserVO> {
        val user = userRepository.findByUsername(request.userPrincipal.name) as User

        return ResponseEntity.ok(userAssembler.toUserVO(user))
    }

    @GetMapping
    fun index(request: HttpServletRequest): ResponseEntity<UserListVO> {
        val user = userRepository.findByUsername(request.userPrincipal.name) as User
        val users = userService.listUsers(user)

        return ResponseEntity.ok(userAssembler.toUserListVO(users))
    }

    @PutMapping
    fun update(@RequestBody userDetails: User, request: HttpServletRequest): ResponseEntity<UserVO> {
        val user = userRepository.findByUsername(request.userPrincipal.name) as User

        val updatedUser = userService.updateUserStatus(user, userDetails)
        return ResponseEntity.ok(userAssembler.toUserVO(updatedUser))
    }

    @PostMapping("/updateToken")
    fun updateToken(@RequestBody userUpdate: User, request: HttpServletRequest): ResponseEntity<UserVO> {
        val user = userRepository.findByUsername(request.userPrincipal.name) as User

        val updatedUser = userService.updateUserToken(user, userUpdate)
        return ResponseEntity.ok(userAssembler.toUserVO(updatedUser))
    }
}
package com.github.aoreshin.android.messengerbackend.controllers

import com.github.aoreshin.android.messengerbackend.components.ConversationAssembler
import com.github.aoreshin.android.messengerbackend.helpers.objects.ConversationListVO
import com.github.aoreshin.android.messengerbackend.helpers.objects.ConversationVO
import com.github.aoreshin.android.messengerbackend.models.User
import com.github.aoreshin.android.messengerbackend.repositories.UserRepository
import com.github.aoreshin.android.messengerbackend.service.ConversationService
import com.github.aoreshin.android.messengerbackend.service.ConversationServiceImpl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/conversations")
class ConversationController(val conversationService: ConversationService,
                             val conversationAssembler: ConversationAssembler,
                             val userRepository: UserRepository) {
    @GetMapping
    fun list(request: HttpServletRequest): ResponseEntity<ConversationListVO> {
        val user = userRepository.findByUsername(request.userPrincipal.name) as User

        val conversations = conversationService.listUserConversations(user.id)

        return ResponseEntity.ok(conversationAssembler.toConversationListVO(conversations, user.id))
    }

    @GetMapping
    @RequestMapping("/{conversation_id}")
    fun show(@PathVariable(name = "conversation_id") conversationId: Long, request: HttpServletRequest): ResponseEntity<ConversationVO> {
        val user = userRepository.findByUsername(request.userPrincipal.name) as User

        val conversations = conversationService.retrieveThread(conversationId)

        return ResponseEntity.ok(conversationAssembler.toConversationVO(conversations, user.id))
    }
}
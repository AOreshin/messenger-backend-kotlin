package com.github.aoreshin.android.messengerbackend.components

import com.github.aoreshin.android.messengerbackend.constants.ErrorResponse
import com.github.aoreshin.android.messengerbackend.exceptions.ConversationIdInvalidException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ConversationControllerAdvice {
    @ExceptionHandler
    fun conversationIdInvalidException(conversationIdInvalidException:
                                       ConversationIdInvalidException
    ): ResponseEntity<ErrorResponse> {
        val res = ErrorResponse("", conversationIdInvalidException.message)
        return ResponseEntity.unprocessableEntity().body(res)
    }
}
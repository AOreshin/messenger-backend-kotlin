package com.github.aoreshin.android.messengerbackend.components

import com.github.aoreshin.android.messengerbackend.constants.ErrorResponse
import com.github.aoreshin.android.messengerbackend.constants.ResponseConstants
import com.github.aoreshin.android.messengerbackend.exceptions.MessageEmptyException
import com.github.aoreshin.android.messengerbackend.exceptions.MessageRecipientInvalidException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class MessageControllerAdvice {
    @ExceptionHandler(MessageEmptyException::class)
    fun messageEmpty(messageEmptyException: MessageEmptyException):
            ResponseEntity<ErrorResponse> {//ErrorResponse object creation
        val res = ErrorResponse(
            ResponseConstants.MESSAGE_EMPTY.value,
            messageEmptyException.message)
// Returning ResponseEntity containing appropriate ErrorResponse
        return ResponseEntity.unprocessableEntity().body(res)
    }
    @ExceptionHandler(MessageRecipientInvalidException::class)
    fun messageRecipientInvalid(messageRecipientInvalidException:
                                MessageRecipientInvalidException):
            ResponseEntity<ErrorResponse> {
        val res = ErrorResponse(ResponseConstants.MESSAGE_RECIPIENT_INVALID
            .value, messageRecipientInvalidException.message)
        return ResponseEntity.unprocessableEntity().body(res)
    }
}
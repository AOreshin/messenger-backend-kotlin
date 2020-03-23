package com.github.aoreshin.android.messengerbackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MessengerBackendApplication

fun main(args: Array<String>) {
	runApplication<MessengerBackendApplication>(*args)
}

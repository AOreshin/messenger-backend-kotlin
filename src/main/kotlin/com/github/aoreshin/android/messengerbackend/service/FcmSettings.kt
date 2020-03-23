package com.github.aoreshin.android.messengerbackend.service

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@ConfigurationProperties(prefix = "fcm")
@Component
class FcmSettings {
    var serviceAccountFile: String? = null
}
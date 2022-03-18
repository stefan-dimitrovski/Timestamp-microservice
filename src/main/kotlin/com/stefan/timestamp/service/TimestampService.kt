package com.stefan.timestamp.service

import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class TimestampService {
    fun getTime(): LocalDateTime = LocalDateTime.now()
}
package com.stefan.timestamp.api

import com.stefan.timestamp.service.TimestampService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/v1/timestamp")
class TimestampController(val timestampService: TimestampService) {

    @GetMapping
    fun getTimestamp(): LocalDateTime = timestampService.getTime()
}
package com.stefan.timestamp.api

import com.stefan.timestamp.service.TimestampService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/timestamp")
class TimestampController(val timestampService: TimestampService) {

    @GetMapping
    fun getTimestampWithoutParam(): ResponseEntity<TimestampResponse> =
        ResponseEntity.ok(timestampService.getTimestamp(null))

    @GetMapping("/{param}")
    fun getTimestamp(@PathVariable param: String): ResponseEntity<Any> =
        timestampService.getTimestamp(param)?.let {
            ResponseEntity.ok().body(it)
        } ?: ResponseEntity(TimestampError(), HttpStatus.NOT_FOUND)

}
package com.stefan.timestamp.service

import com.stefan.timestamp.api.TimestampResponse
import org.springframework.stereotype.Service
import java.time.*
import java.time.format.DateTimeFormatter

@Service
class TimestampService {

    val zoneId: ZoneId = ZoneId.of("GMT")
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("E, dd MMM yyyy HH:mm:ss zzz")

    fun convert(param: String?): TimestampResponse = param?.let {
        when (param.toLongOrNull()) {
            is Long -> {
                convertUnix(it.toLong())
            }
            else -> {
                convertUTC(param)
            }
        }
    } ?: run {
        val zonedDateTime = ZonedDateTime.of(LocalDateTime.now(), zoneId)
        TimestampResponse(zonedDateTime.toEpochSecond(), zonedDateTime.format(formatter))
    }

    fun convertUnix(unix: Long): TimestampResponse {
        val utc = ZonedDateTime.ofInstant(Instant.ofEpochMilli(unix), zoneId)
        return TimestampResponse(unix, utc.format(formatter))
    }

    fun convertUTC(date: String): TimestampResponse {
        val utc = ZonedDateTime.of(LocalDate.parse(date), LocalTime.MIDNIGHT, zoneId)
        val unix = utc.toInstant().toEpochMilli()
        return TimestampResponse(unix, utc.format(formatter))
    }

}
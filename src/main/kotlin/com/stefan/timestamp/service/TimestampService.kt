package com.stefan.timestamp.service

import com.stefan.timestamp.api.TimestampResponse
import org.springframework.stereotype.Service
import java.time.*
import java.time.format.DateTimeFormatter

@Service
class TimestampService {

    val zoneId: ZoneId = ZoneId.of("GMT")
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("E, dd MMM yyyy HH:mm:ss zzz")

    fun getTimestamp(param: String?): TimestampResponse? = param?.let {
        return when (param.toLongOrNull()) {
            is Long -> getTimestampFromDate(convertFromUnixToZonedDateTime(it.toLong()))
            else -> getTimestampFromDate(convertFromStringToZonedDateTime(param))
        }
    } ?: getTimestampFromDate(currentZonedDateTime())

    fun convertFromUnixToZonedDateTime(unix: Long): ZonedDateTime {
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(unix), zoneId)
    }

    fun convertFromStringToZonedDateTime(date: String): ZonedDateTime? = try {
        ZonedDateTime.of(LocalDate.parse(date), LocalTime.MIDNIGHT, zoneId)
    } catch (e: DateTimeException) {
        null
    }

    fun currentZonedDateTime(): ZonedDateTime = ZonedDateTime.of(LocalDateTime.now(), zoneId)

    fun getTimestampFromDate(zonedDateTime: ZonedDateTime?): TimestampResponse? =
        zonedDateTime?.let {
            TimestampResponse(
                zonedDateTime.toInstant().toEpochMilli(),
                zonedDateTime.format(formatter)
            )
        }

}
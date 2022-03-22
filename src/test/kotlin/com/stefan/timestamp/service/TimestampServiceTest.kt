package com.stefan.timestamp.service

import org.junit.jupiter.api.Test
import java.time.*

internal class TimestampServiceTest {

    private val timestampService: TimestampService = object : TimestampService() {
        override fun currentZonedDateTime(): ZonedDateTime {
            return ZonedDateTime.now(clock)
        }
    }

    private val clock = Clock.fixed(Instant.parse("2018-08-22T10:00:00Z"), timestampService.zoneId)

    @Test
    fun `parse ZonedDateTime from string`() {
        val date = "2015-12-30"
        val zonedDateTime = timestampService.convertFromStringToZonedDateTime(date)!!
        println(zonedDateTime)
        assert(
            zonedDateTime.year == 2015
                    && zonedDateTime.month == Month.DECEMBER
                    && zonedDateTime.dayOfMonth == 30
                    && zonedDateTime.hour == 0
                    && zonedDateTime.minute == 0
                    && zonedDateTime.second == 0
        )
    }

    @Test
    fun `get ZonedDateTime from unix`() {
        val unix: Long = 1451001600000
        val zonedDateTime = timestampService.convertFromUnixToZonedDateTime(unix)
        println(zonedDateTime)
        assert(
            zonedDateTime.year == 2015
                    && zonedDateTime.month == Month.DECEMBER
                    && zonedDateTime.dayOfMonth == 25
                    && zonedDateTime.hour == 0
                    && zonedDateTime.minute == 0
                    && zonedDateTime.second == 0
        )
    }

    @Test
    fun `handle invalid parameter`() {
        val param = "2015-2-4"
        val zonedDateTime = timestampService.convertFromStringToZonedDateTime(param)
        println(zonedDateTime)
        assert(zonedDateTime == null)
    }

    @Test
    fun `handle empty parameter`() {
        val currentZonedDateTime = timestampService.currentZonedDateTime()
        println(currentZonedDateTime)
        assert(currentZonedDateTime == ZonedDateTime.now(clock))
    }

    @Test
    fun `get timestamp from not null date`() {
        val zoneDateTime = ZonedDateTime.now(clock)
        val timestampResponse = timestampService.getTimestampFromDate(zoneDateTime)!!
        println(timestampResponse)
        assert(
            timestampResponse.unix == 1534932000000 && timestampResponse.utc == "Wed, 22 Aug 2018 10:00:00 GMT"
        )
    }

    @Test
    fun `get timestamp from null date`() {
        val timestampResponse = timestampService.getTimestampFromDate(null)
        assert(timestampResponse == null)
    }
}
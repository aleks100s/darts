package com.alextos.darts.game.domain.models

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import kotlinx.datetime.LocalDateTime
import kotlin.test.Test

class GameTests {
    // test Game getDateString function
    @Test
    fun testGetDateString() {
        val game = Game(
            id = 1,
            players = listOf(),
            gameGoal = 501,
            finishTimestamp = LocalDateTime(2021, 10, 10, 10, 10),
            startTimestamp = LocalDateTime(2021, 10, 10, 10, 10)
        )
        val date = game.getFinishDateString()
        assertThat(date).isNotNull()
    }

    // test Game getDayString function
    @Test
    fun testGetDayStringFollowsPattern() {
        val game = Game(
            id = 1,
            players = listOf(),
            gameGoal = 501,
            finishTimestamp = LocalDateTime(2021, 10, 10, 10, 10),
            startTimestamp = LocalDateTime(2021, 10, 10, 10, 10)
        )
        val day = game.getDayString(game.finishTimestamp)
        assertThat(day).isEqualTo("10.10.2021")
    }

    // test Game getTimeString function
    @Test
    fun testGetTimeStringFollowsPattern() {
        val game = Game(
            id = 1,
            players = listOf(),
            gameGoal = 501,
            finishTimestamp = LocalDateTime(2021, 10, 10, 10, 10),
            startTimestamp = LocalDateTime(2021, 10, 10, 10, 10)
        )
        val time = game.getTimeString(game.finishTimestamp)
        assertThat(time).isEqualTo("10:10")
    }

    // test Game getHourString function
    @Test
    fun testGetHourStringFollowsPatternWhenHourContainsTwoDigits() {
        val game = Game(
            id = 1,
            players = listOf(),
            gameGoal = 501,
            finishTimestamp = LocalDateTime(2021, 10, 10, 10, 10),
            startTimestamp = LocalDateTime(2021, 10, 10, 10, 10)
        )
        val hour = game.getHourString(game.finishTimestamp)
        assertThat(hour).isEqualTo("10")
    }

    // test Game getHourString function when hour contains one digit
    @Test
    fun testGetHourStringFollowsPatternWhenHourContainsOneDigit() {
        val game = Game(
            id = 1,
            players = listOf(),
            gameGoal = 501,
            finishTimestamp = LocalDateTime(2021, 10, 10, 1, 10),
            startTimestamp = LocalDateTime(2021, 10, 10, 10, 10)
        )
        val hour = game.getHourString(game.finishTimestamp)
        assertThat(hour).isEqualTo("01")
    }

    // test Game getMinuteString function when minute contains two digits
    @Test
    fun testGetMinuteStringFollowsPatternWhenMinuteContainsTwoDigits() {
        val game = Game(
            id = 1,
            players = listOf(),
            gameGoal = 501,
            finishTimestamp = LocalDateTime(2021, 10, 10, 10, 10),
            startTimestamp = LocalDateTime(2021, 10, 10, 10, 10)
        )
        val minute = game.getMinuteString(game.finishTimestamp)
        assertThat(minute).isEqualTo("10")
    }

    // test Game getMinuteString function when minute contains one digit
    @Test
    fun testGetMinuteStringFollowsPatternWhenMinuteContainsOneDigit() {
        val game = Game(
            id = 1,
            players = listOf(),
            gameGoal = 501,
            finishTimestamp = LocalDateTime(2021, 10, 10, 10, 1),
            startTimestamp = LocalDateTime(2021, 10, 10, 10, 10)
        )
        val minute = game.getMinuteString(game.finishTimestamp)
        assertThat(minute).isEqualTo("01")
    }
}
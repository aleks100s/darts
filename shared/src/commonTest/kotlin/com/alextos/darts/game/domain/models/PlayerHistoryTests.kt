package com.alextos.darts.game.domain.models

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.alextos.darts.core.domain.model.Player
import com.alextos.darts.core.domain.model.Sector
import com.alextos.darts.core.domain.model.Shot
import com.alextos.darts.core.domain.model.Turn
import kotlin.test.Test

class PlayerHistoryTests {
    // test PlayerHistory.biggestTurn() with empty list of turns
    @Test
    fun test_PlayerHistoryBiggestTurn_WithEmptyList_IsNull() {
        val playerHistory = PlayerHistory(
            player = Player(0, "Player 1"),
            turns = listOf()
        )
        val biggestTurn = playerHistory.biggestTurn()
        assertThat(biggestTurn).isEqualTo(null)
    }

    // test PlayerHistory.smallestTurn() with empty list of turns
    @Test
    fun test_PlayerHistorySmallestTurn_WithEmptyList_IsNull() {
        val playerHistory = PlayerHistory(
            player = Player(0, "Player 1"),
            turns = listOf()
        )
        val smallestTurn = playerHistory.smallestTurn()
        assertThat(smallestTurn).isEqualTo(null)
    }

    // test PlayerHistory.numberOfMisses() with empty list of turns
    @Test
    fun test_PlayerHistoryNumberOfMisses_WithEmptyList_IsEqualToZero() {
        val playerHistory = PlayerHistory(
            player = Player(0, "Player 1"),
            turns = listOf()
        )
        val numberOfMisses = playerHistory.numberOfMisses()
        assertThat(numberOfMisses).isEqualTo(0)
    }

    // test PlayerHistory.numberOfOverkills() with empty list of turns
    @Test
    fun test_PlayerHistoryNumberOfOverkills_WithEmptyList_IsEqualToZero() {
        val playerHistory = PlayerHistory(
            player = Player(0, "Player 1"),
            turns = listOf()
        )
        val numberOfOverkills = playerHistory.numberOfOverkills()
        assertThat(numberOfOverkills).isEqualTo(0)
    }

    // test PlayerHistory.average() with empty list of turns
    @Test
    fun test_PlayerHistoryAverage_WithEmptyList_IsEqualToZero() {
        val playerHistory = PlayerHistory(
            player = Player(0, "Player 1"),
            turns = listOf()
        )
        val average = playerHistory.average()
        assertThat(average).isEqualTo(0)
    }

    // test PlayerHistory.biggestTurn() with non-empty list of turns
    @Test
    fun test_PlayerHistoryBiggestTurn_WithNonEmptyList_IsEqualToBiggestTurn() {
        val playerHistory = PlayerHistory(
            player = Player(0, "Player 1"),
            turns = listOf(
                Turn(
                    shots = listOf(
                        Shot(Sector.SingleInner1),
                        Shot(Sector.SingleInner2),
                        Shot(Sector.SingleInner3)
                    ),
                    leftAfter = 0,
                    isOverkill = false
                ),
                Turn(
                    shots = listOf(
                        Shot(Sector.Triple1),
                        Shot(Sector.Triple2),
                        Shot(Sector.Triple3)
                    ),
                    leftAfter = 0,
                    isOverkill = false
                )
            )
        )
        val biggestTurn = playerHistory.biggestTurn()
        assertThat(biggestTurn).isEqualTo(
            Turn(
                shots = listOf(
                    Shot(Sector.Triple1),
                    Shot(Sector.Triple2),
                    Shot(Sector.Triple3)
                ),
                leftAfter = 0,
                isOverkill = false
            )
        )
    }

    // test PlayerHistory.smallestTurn() with non-empty list of turns
    @Test
    fun test_PlayerHistorySmallestTurn_WithNonEmptyList_IsEqualToSmallestTurn() {
        val playerHistory = PlayerHistory(
            player = Player(0, "Player 1"),
            turns = listOf(
                Turn(
                    shots = listOf(
                        Shot(Sector.SingleInner1),
                        Shot(Sector.SingleInner2),
                        Shot(Sector.SingleInner3)
                    ),
                    leftAfter = 0,
                    isOverkill = false
                ),
                Turn(
                    shots = listOf(
                        Shot(Sector.Triple1),
                        Shot(Sector.Triple2),
                        Shot(Sector.Triple3)
                    ),
                    leftAfter = 0,
                    isOverkill = false
                )
            )
        )
        val smallestTurn = playerHistory.smallestTurn()
        assertThat(smallestTurn).isEqualTo(
            Turn(
                shots = listOf(
                    Shot(Sector.SingleInner1),
                    Shot(Sector.SingleInner2),
                    Shot(Sector.SingleInner3)
                ),
                leftAfter = 0,
                isOverkill = false
            )
        )
    }

    // test PlayerHistory.numberOfMisses() with non-empty list of turns
    @Test
    fun test_PlayerHistoryNumberOfMisses_WithNonEmptyListWithoutMisses_IsEqualToZero() {
        val playerHistory = PlayerHistory(
            player = Player(0, "Player 1"),
            turns = listOf(
                Turn(
                    shots = listOf(
                        Shot(Sector.SingleInner1),
                        Shot(Sector.SingleInner2),
                        Shot(Sector.SingleInner3)
                    ),
                    leftAfter = 0,
                    isOverkill = false
                ),
                Turn(
                    shots = listOf(
                        Shot(Sector.Triple1),
                        Shot(Sector.Triple2),
                        Shot(Sector.Triple3)
                    ),
                    leftAfter = 0,
                    isOverkill = false
                )
            )
        )
        val numberOfMisses = playerHistory.numberOfMisses()
        assertThat(numberOfMisses).isEqualTo(0)
    }

    // test PlayerHistory.numberOfMisses() with non-empty list of turns
    @Test
    fun test_PlayerHistoryNumberOfMisses_WithNonEmptyListWithMisses_IsEqualToNumberOfMisses() {
        val playerHistory = PlayerHistory(
            player = Player(0, "Player 1"),
            turns = listOf(
                Turn(
                    shots = listOf(
                        Shot(Sector.SingleInner1),
                        Shot(Sector.Miss),
                        Shot(Sector.SingleInner3)
                    ),
                    leftAfter = 0,
                    isOverkill = false
                ),
                Turn(
                    shots = listOf(
                        Shot(Sector.Miss),
                        Shot(Sector.Triple2),
                        Shot(Sector.Triple3)
                    ),
                    leftAfter = 0,
                    isOverkill = false
                )
            )
        )
        val numberOfMisses = playerHistory.numberOfMisses()
        assertThat(numberOfMisses).isEqualTo(2)
    }

    // test PlayerHistory.numberOfOverkills() with non-empty list of turns
    @Test
    fun test_PlayerHistoryNumberOfOverkills_WithNonEmptyListWithoutOverkills_IsEqualToZero() {
        val playerHistory = PlayerHistory(
            player = Player(0, "Player 1"),
            turns = listOf(
                Turn(
                    shots = listOf(
                        Shot(Sector.SingleInner1),
                        Shot(Sector.SingleInner2),
                        Shot(Sector.SingleInner3)
                    ),
                    leftAfter = 0,
                    isOverkill = false
                ),
                Turn(
                    shots = listOf(
                        Shot(Sector.Triple1),
                        Shot(Sector.Triple2),
                        Shot(Sector.Triple3)
                    ),
                    leftAfter = 0,
                    isOverkill = false
                )
            )
        )
        val numberOfOverkills = playerHistory.numberOfOverkills()
        assertThat(numberOfOverkills).isEqualTo(0)
    }

    // test PlayerHistory.numberOfOverkills() with non-empty list of turns
    @Test
    fun test_PlayerHistoryNumberOfOverkills_WithNonEmptyListWithOverkills_IsEqualToNumberOfOverkills() {
        val playerHistory = PlayerHistory(
            player = Player(0, "Player 1"),
            turns = listOf(
                Turn(
                    shots = listOf(
                        Shot(Sector.SingleInner1),
                        Shot(Sector.SingleInner2),
                        Shot(Sector.SingleInner3)
                    ),
                    leftAfter = 0,
                    isOverkill = true
                ),
                Turn(
                    shots = listOf(
                        Shot(Sector.Triple1),
                        Shot(Sector.Triple2),
                        Shot(Sector.Triple3)
                    ),
                    leftAfter = 0,
                    isOverkill = true
                )
            )
        )
        val numberOfOverkills = playerHistory.numberOfOverkills()
        assertThat(numberOfOverkills).isEqualTo(2)
    }

    // test PlayerHistory.average() with non-empty list of turns
    @Test
    fun test_PlayerHistoryAverage_WithNonEmptyList_IsEqualToAverage() {
        val playerHistory = PlayerHistory(
            player = Player(0, "Player 1"),
            turns = listOf(
                Turn(
                    shots = listOf(
                        Shot(Sector.SingleInner1),
                        Shot(Sector.SingleInner2),
                        Shot(Sector.SingleInner3)
                    ),
                    leftAfter = 0,
                    isOverkill = false
                ),
                Turn(
                    shots = listOf(
                        Shot(Sector.Triple1),
                        Shot(Sector.Triple2),
                        Shot(Sector.Triple3)
                    ),
                    leftAfter = 0,
                    isOverkill = false
                )
            )
        )
        val average = playerHistory.average()
        assertThat(average).isEqualTo(12)
    }

    // test PlayerHistory.average() with non-empty list of turns where last turn has less than 3 shots
    @Test
    fun test_PlayerHistoryAverage_WithNonEmptyListWithLastTurnLessThan3Shots_IsEqualToAverage() {
        val playerHistory = PlayerHistory(
            player = Player(0, "Player 1"),
            turns = listOf(
                Turn(
                    shots = listOf(
                        Shot(Sector.SingleInner1),
                        Shot(Sector.SingleInner2),
                        Shot(Sector.SingleInner3)
                    ),
                    leftAfter = 0,
                    isOverkill = false
                ),
                Turn(
                    shots = listOf(
                        Shot(Sector.Triple1),
                        Shot(Sector.Triple2)
                    ),
                    leftAfter = 0,
                    isOverkill = false
                )
            )
        )
        val average = playerHistory.average()
        assertThat(average).isEqualTo(6)
    }
}
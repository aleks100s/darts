package com.alextos.darts.debug

import com.alextos.darts.core.domain.model.Player
import com.alextos.darts.core.domain.model.Sector
import com.alextos.darts.core.domain.model.Shot
import com.alextos.darts.core.domain.model.Turn
import com.alextos.darts.game.domain.models.Game
import com.alextos.darts.game.domain.models.GameHistory
import com.alextos.darts.game.domain.models.PlayerHistory
import com.alextos.darts.game.domain.useCases.CreatePlayerUseCase
import com.alextos.darts.game.domain.useCases.SaveGameHistoryUseCase
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlin.random.Random

class PrepopulateDatabaseUseCase(
    private val createPlayerUseCase: CreatePlayerUseCase,
    private val saveGameHistoryUseCase: SaveGameHistoryUseCase
) {
    private val playerA = Player(1, "Alexander")
    private val playerB = Player(2, "Ilja")
    private val playerC = Player(3, "Carlos")

    suspend fun execute() {
        savePlayers()
        saveGames()
    }

    private suspend fun savePlayers() {
        allPlayers().forEach { player ->
            createPlayerUseCase.execute(player.name)
        }
    }

    private suspend fun saveGames() {
        val games = createTestGames()
        for (game in games) {
            saveGameHistoryUseCase.execute(game)
        }
    }

    private fun createTestGames() = listOf(
        createGame(),
        createGame(finishWithDoubles = true),
        createGame(randomPlayerOrder = true),
        createGame(finishWithDoubles = true, randomPlayerOrder = true),
        createTraining(player = randomPlayer(), goal = randomGoal()),
        createTraining(player = randomPlayer(), goal = randomGoal(), finishWithDoubles = true),
        createTraining(player = randomPlayer(), goal = randomGoal(), randomPlayerOrder = true),
        createTraining(player = randomPlayer(), goal = randomGoal(), finishWithDoubles = true, randomPlayerOrder = true)
    )

    private fun createGame(
        finishWithDoubles: Boolean = false,
        randomPlayerOrder: Boolean = false
    ): GameHistory {
        val winner = randomPlayer()
        val goal = randomGoal()
        return GameHistory(
            game = Game(
                players = allPlayers(),
                winner = winner,
                gameGoal = goal,
                startTimestamp = randomTime(inPast = true),
                finishTimestamp = randomTime(inPast = false),
                isWinWithDoublesEnabled = finishWithDoubles,
                isRandomPlayerOrderEnabled = randomPlayerOrder,
                isStatisticsEnabled = true,
                isTurnLimitEnabled = true,
                isOngoing = false
            ),
            playerHistories = allPlayers().map { player ->
                PlayerHistory(
                    player = player,
                    turns = generateTurns(goal = goal, winner = player == winner, finishWithDoubles)
                )
            }
        )
    }

    private fun createTraining(
        player: Player,
        goal: Int,
        finishWithDoubles: Boolean = false,
        randomPlayerOrder: Boolean = false
    ): GameHistory {
        return GameHistory(
            game = Game(
                players = listOf(player),
                winner = null,
                gameGoal = goal,
                startTimestamp = randomTime(inPast = true),
                finishTimestamp = randomTime(inPast = false),
                isWinWithDoublesEnabled = finishWithDoubles,
                isRandomPlayerOrderEnabled = randomPlayerOrder,
                isStatisticsEnabled = true,
                isTurnLimitEnabled = true,
                isOngoing = false
            ),
            playerHistories = listOf(
                PlayerHistory(
                    player = player,
                    turns = generateTurns(goal = goal, winner = true, finishWithDoubles = finishWithDoubles)
                )
            )
        )
    }

    private fun generateTurns(goal: Int, winner: Boolean, finishWithDoubles: Boolean): List<Turn> {
        return when(goal) {
            101 -> generate101Turns(winner, finishWithDoubles)
            301 -> generate301Turns(winner, finishWithDoubles)
            else -> emptyList()
        }
    }

    private fun generate101Turns(winner: Boolean, finishWithDoubles: Boolean): List<Turn> {
        return listOf(
            Turn(
                shots = listOf(
                    Shot(Sector.SingleInner20),
                    Shot(Sector.SingleInner5),
                    Shot(Sector.SingleInner1)
                ),
                leftAfter = 75,
                isOverkill = false
            ),
            Turn(
                shots = listOf(
                    Shot(Sector.SingleInner15),
                    Shot(Sector.SingleInner20),
                    Shot(Sector.Double4)
                ),
                leftAfter = 32,
                isOverkill = false
            ),
            Turn(
                shots = listOf(
                    Shot(Sector.SingleInner12),
                    Shot(Sector.SingleInner18),
                    Shot(Sector.SingleInner5)
                ),
                leftAfter = 32,
                isOverkill = true
            ),
            if (winner) {
                if (finishWithDoubles) {
                    Turn(
                        shots = listOf(
                            Shot(Sector.Triple5),
                            Shot(Sector.SingleInner7),
                            Shot(Sector.Double5)
                        ),
                        leftAfter = 0,
                        isOverkill = false
                    )
                } else {
                    Turn(
                        shots = listOf(
                            Shot(Sector.Triple5),
                            Shot(Sector.SingleInner17)
                        ),
                        leftAfter = 0,
                        isOverkill = false
                    )
                }
            } else {
                Turn(
                    shots = listOf(
                        Shot(Sector.SingleInner12),
                        Shot(Sector.SingleInner18),
                        Shot(Sector.SingleInner5)
                    ),
                    leftAfter = 32,
                    isOverkill = true
                )
            }
        )
    }

    private fun generate301Turns(winner: Boolean, finishWithDoubles: Boolean): List<Turn> {
        return listOf(
            Turn(
                shots = listOf(
                    Shot(Sector.SingleInner20),
                    Shot(Sector.SingleInner5),
                    Shot(Sector.SingleInner1)
                ).shuffled(),
                leftAfter = 275,
                isOverkill = false
            ),
            Turn(
                shots = listOf(
                    Shot(Sector.Triple18),
                    Shot(Sector.SingleInner12),
                    Shot(Sector.Double3)
                ).shuffled(),
                leftAfter = 203,
                isOverkill = false
            ),
            Turn(
                shots = listOf(
                    Shot(Sector.SingleInner20),
                    Shot(Sector.SingleInner5),
                    Shot(Sector.SingleInner1)
                ).shuffled(),
                leftAfter = 177,
                isOverkill = false
            ),
            Turn(
                shots = listOf(
                    Shot(Sector.SingleInner20),
                    Shot(Sector.SingleInner20),
                    Shot(Sector.SingleInner5)
                ).shuffled(),
                leftAfter = 158,
                isOverkill = false
            ),
            Turn(
                shots = listOf(
                    Shot(Sector.Miss),
                    Shot(Sector.SingleInner20),
                    Shot(Sector.Miss)
                ).shuffled(),
                leftAfter = 138,
                isOverkill = false
            ),
            Turn(
                shots = listOf(
                    Shot(Sector.SingleInner10),
                    Shot(Sector.SingleInner2),
                    Shot(Sector.SingleBullseye)
                ).shuffled(),
                leftAfter = 126,
                isOverkill = false
            ),
        ) + generate101Turns(winner, finishWithDoubles)
    }

    private fun randomTime(inPast: Boolean): LocalDateTime {
        return Clock.System.now()
            .plus(randomMinuteCount(inPast), DateTimeUnit.MINUTE, TimeZone.UTC)
            .plus(randomSecondCount(inPast), DateTimeUnit.SECOND, TimeZone.UTC)
            .toLocalDateTime(TimeZone.UTC)
    }

    private fun randomMinuteCount(isNegative: Boolean): Int {
        val count = Random.nextInt(1, 10)
        return if (isNegative) -count else count
    }

    private fun randomSecondCount(isNegative: Boolean): Int {
        val count = Random.nextInt(0, 60)
        return if (isNegative) -count else count
    }

    private fun randomGoal() = listOf(101, 301).random()

    private fun randomPlayer() = allPlayers().random()

    private fun allPlayers() = listOf(playerA, playerB, playerC)
}
package com.alextos.darts.game.data

import com.alextos.darts.database.DartsDatabase
import com.alextos.darts.game.domain.GameDataSource
import com.alextos.darts.game.domain.models.Game
import com.alextos.darts.game.domain.models.GameHistory
import com.alextos.darts.game.domain.models.PlayerHistory
import com.alextos.darts.core.domain.model.Player
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.*

class SqlDelightGameDataSource(
    database: DartsDatabase
): GameDataSource {
    private val queries = database.dartsQueries

    override fun getGames(): Flow<List<Game>> {
        return queries.getGames()
            .asFlow()
            .mapToList()
            .map { it.mapToGames() }
    }

    override suspend fun saveGameHistory(gameHistory: GameHistory) {
        val game = gameHistory.game

        queries.transaction {
            queries.insertGameEntity(
                id = null,
                game_goal = game.gameGoal.toLong(),
                timestamp = game.finishTimestamp.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds(),
                timestamp_start = game.startTimestamp?.toInstant(TimeZone.currentSystemDefault())?.toEpochMilliseconds(),
                finish_with_doubles = if (game.finishWithDoubles) 1 else 0,
                random_player_order = if (game.randomPlayerOrder) 1 else 0
            )

            val gameId = getLastInsertedId()

            game.players.forEach {
                queries.insertGamePlayerEntity(
                    id = null,
                    game_id = gameId,
                    player_id = it.id,
                    game_order = game.players.indexOf(it).toLong(),
                    is_winner = if (game.winner == it) 1 else if (game.winner == null) -1 else 0
                )
            }

            gameHistory.playerHistories.forEach { history ->
                val player = history.player
                history.turns.forEach { set ->
                    queries.insertSetEntity(
                        id = null,
                        game_id = gameId,
                        player_id = player.id,
                        score = set.score().toLong(),
                        leftAfter = set.leftAfter.toLong(),
                        isOverkill = if (set.isOverkill) 1 else 0
                    )

                    val setId = getLastInsertedId()

                    set.shots.forEach { shot ->
                        queries.insertShotEntity(
                            id = null,
                            set_id = setId,
                            player_id = player.id,
                            order_number = set.shots.indexOf(shot).toLong(),
                            sector = shot.sector.id.toLong(),
                            shot_value = shot.sector.value.toLong()
                        )
                    }
                }
            }
        }
    }

    override fun getPlayerHistory(gameId: Long, player: Player): Flow<PlayerHistory> {
        return queries.getPlayerHistory(game_id = gameId, player_id = player.id)
            .asFlow()
            .mapToList()
            .map {
                it.toPlayerHistory(player)
            }
    }

    override suspend fun deleteGame(game: Game) {
        game.id?.let { gameId ->
            queries.transaction {
                queries.getGameSets(gameId).executeAsList()
                    .forEach {
                        queries.deleteShots(it.id)
                    }
                queries.deleteSet(gameId)
                queries.deleteGamePlayer(gameId)
                queries.deleteGame(gameId)
            }
        }
    }

    private fun getLastInsertedId(): Long {
        return queries.getLastInsertedId().executeAsOne()
    }
}
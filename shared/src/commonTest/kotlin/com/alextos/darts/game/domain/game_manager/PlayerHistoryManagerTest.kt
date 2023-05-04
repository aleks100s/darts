package com.alextos.darts.game.domain.game_manager

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import com.alextos.darts.core.domain.model.Player
import com.alextos.darts.core.domain.model.Sector
import com.alextos.darts.core.domain.model.Shot
import com.alextos.darts.core.domain.model.Turn
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

class PlayerHistoryManagerTest {
    private val defaultPlayer = Player(1, "Player")

    @Test
    fun `test sumUpShotsValue`() {
        val sector1 = Sector.sectors.random().random()
        val sector2 = Sector.sectors.random().random()
        val sector3 = Sector.sectors.random().random()
        val value = sector1.value + sector2.value + sector3.value
        val shots = listOf(sector1, sector2, sector3).map { Shot(it) }
        assertThat(sumUpShotsValue(shots)).isEqualTo(value)
    }

    @Test
    fun `test createTurn 1`() {
        val sector1 = Sector.sectors.random().random()
        val sector2 = Sector.sectors.random().random()
        val sector3 = Sector.sectors.random().random()
        val shots = listOf(sector1, sector2, sector3).map { Shot(it) }
        val turn = createTurn(shots, initialScore = 180)
        assertThat(turn).isEqualTo(Turn(shots = shots, leftAfter = 180 - sumUpShotsValue(shots), isOverkill = false))
    }

    @Test
    fun `test createTurn 2`() {
        val sector1 = Sector.sectors.random().random()
        val sector2 = Sector.sectors.random().random()
        val sector3 = Sector.sectors.random().random()
        val sector4 = Sector.sectors.random().random()
        val shots = listOf(sector1, sector2, sector3, sector4).map { Shot(it) }
        val turn = createTurn(shots, initialScore = 180)
        assertThat(turn).isEqualTo(Turn(shots = shots.subList(0, 3), leftAfter = 180 - sumUpShotsValue(shots.subList(0, 3)), isOverkill = false))
    }

    @Test
    fun `test createTurn 3`() {
        val sector1 = Sector.Triple20
        val sector2 = Sector.Triple20
        val sector3 = Sector.Triple20
        val shots = listOf(sector1, sector2, sector3).map { Shot(it) }
        val turn = createTurn(shots, initialScore = 100)
        assertThat(turn).isEqualTo(Turn(shots = shots, leftAfter = 100, isOverkill = true))
    }

    @Test
    fun `test player history initially is empty`() = runBlocking {
        val sut = PlayerHistoryManager(
            player = defaultPlayer,
            goal = 101,
            finishWithDoubles = false
        )

        sut.playerHistory.test {
            val initialValue = awaitItem()
            assertThat(initialValue.turns).isEmpty()
        }
    }

    @Test
    fun `test player history after regular shot is updated correctly`() = runBlocking {
        val initialScore = 101
        val sut = PlayerHistoryManager(
            player = defaultPlayer,
            goal = initialScore,
            finishWithDoubles = false
        )

        val shot = Shot(Sector.Double14)
        sut.makeShot(shot)

        sut.playerHistory.test {
            val value = awaitItem()
            assertThat(value.turns).isEqualTo(listOf(createTurn(shots = listOf(shot), initialScore = initialScore)))
        }
    }

    @Test
    fun `test player history after two regular shots is updated correctly`() = runBlocking {
        val initialScore = 101
        val sut = PlayerHistoryManager(
            player = defaultPlayer,
            goal = initialScore,
            finishWithDoubles = false
        )

        val shot1 = Shot(Sector.Double14)
        val shot2 = Shot(Sector.SingleInner13)
        sut.makeShot(shot1)
        sut.makeShot(shot2)

        sut.playerHistory.test {
            val value = awaitItem()
            assertThat(value.turns).isEqualTo(listOf(createTurn(shots = listOf(shot1, shot2), initialScore = initialScore)))
        }
    }

    @Test
    fun `test player history after three regular shots is updated correctly`() = runBlocking {
        val initialScore = 101
        val sut = PlayerHistoryManager(
            player = defaultPlayer,
            goal = initialScore,
            finishWithDoubles = false
        )

        val shot1 = Shot(Sector.Double14)
        val shot2 = Shot(Sector.SingleInner13)
        val shot3 = Shot(Sector.Triple1)
        sut.makeShot(shot1)
        sut.makeShot(shot2)
        sut.makeShot(shot3)

        sut.playerHistory.test {
            val value = awaitItem()
            assertThat(value.turns).isEqualTo(listOf(createTurn(shots = listOf(shot1, shot2, shot3), initialScore = initialScore)))
        }
    }

    @Test
    fun `test manager after three regular shots starts new turn`() = runBlocking {
        val initialScore = 101
        val sut = PlayerHistoryManager(
            player = defaultPlayer,
            goal = initialScore,
            finishWithDoubles = false
        )

        val shot1 = Shot(Sector.Double14)
        val shot2 = Shot(Sector.SingleInner13)
        val shot3 = Shot(Sector.Triple1)
        val shot4 = Shot(Sector.SingleInner10)
        sut.makeShot(shot1)
        sut.makeShot(shot2)
        sut.makeShot(shot3)
        sut.makeShot(shot4)

        sut.playerHistory.test {
            val value = awaitItem()
            assertThat(value.turns).isEqualTo(listOf(
                createTurn(shots = listOf(shot1, shot2, shot3), initialScore = initialScore),
                createTurn(shots = listOf(shot4), initialScore = initialScore - sumUpShotsValue(listOf(shot1, shot2, shot3)))
            ))
        }
    }

    @Test
    fun `test manager after first shot overkill shot fills up current turn with none sectors`() = runBlocking {
        val initialScore = 1
        val sut = PlayerHistoryManager(
            player = defaultPlayer,
            goal = initialScore,
            finishWithDoubles = false
        )

        val shot = Shot(Sector.Double14)
        sut.makeShot(shot)

        sut.playerHistory.test {
            val value = awaitItem()
            assertThat(value.turns).isEqualTo(listOf(createTurn(shots = listOf(shot, Shot(Sector.None), Shot(Sector.None)), initialScore = initialScore)))
        }
    }

    @Test
    fun `test manager after second shot overkill shot fills up current turn with none sectors`() = runBlocking {
        val initialScore = 30
        val sut = PlayerHistoryManager(
            player = defaultPlayer,
            goal = initialScore,
            finishWithDoubles = false
        )

        val shot1 = Shot(Sector.Double14)
        val shot2 = Shot(Sector.Double14)
        sut.makeShot(shot1)
        sut.makeShot(shot2)

        sut.playerHistory.test {
            val value = awaitItem()
            assertThat(value.turns).isEqualTo(listOf(createTurn(shots = listOf(shot1, shot2, Shot(Sector.None)), initialScore = initialScore)))
        }
    }

    @Test
    fun `test manager after overkill shot starts new turn`() = runBlocking {
        val initialScore = 10
        val sut = PlayerHistoryManager(
            player = defaultPlayer,
            goal = initialScore,
            finishWithDoubles = false
        )

        val shot1 = Shot(Sector.Double14)
        val shot2 = Shot(Sector.SingleInner1)
        sut.makeShot(shot1)
        sut.makeShot(shot2)

        sut.playerHistory.test {
            val value = awaitItem()
            assertThat(value.turns).isEqualTo(listOf(
                createTurn(shots = listOf(shot1, Shot(Sector.None), Shot(Sector.None)), initialScore),
                createTurn(shots = listOf(shot2), initialScore)
            ))
        }
    }

    @Test
    fun `test undo shot when history is empty`() = runBlocking {
        val initialScore = 10
        val sut = PlayerHistoryManager(
            player = defaultPlayer,
            goal = initialScore,
            finishWithDoubles = false
        )

        sut.undoLastShot()

        sut.playerHistory.test {
            val value = awaitItem()
            assertThat(value.turns).isEqualTo(listOf())
        }
    }

    @Test
    fun `test undo shot when last turn is full does nothing`() = runBlocking {
        val initialScore = 10
        val sut = PlayerHistoryManager(
            player = defaultPlayer,
            goal = initialScore,
            finishWithDoubles = false
        )

        val shot = Shot(Sector.Triple20)
        sut.makeShot(shot)
        sut.undoLastShot()

        sut.playerHistory.test {
            val value = awaitItem()
            assertThat(value.turns).isEqualTo(listOf(createTurn(listOf(shot, Shot(Sector.None), Shot(Sector.None)), initialScore = 10)))
        }
    }

    @Test
    fun `test undo shot when last turn has two shots removes last shot`() = runBlocking {
        val initialScore = 101
        val sut = PlayerHistoryManager(
            player = defaultPlayer,
            goal = initialScore,
            finishWithDoubles = false
        )

        val shot1 = Shot(Sector.Triple2)
        val shot2 = Shot(Sector.Triple3)
        sut.makeShot(shot1)
        sut.makeShot(shot2)
        sut.undoLastShot()

        sut.playerHistory.test {
            val value = awaitItem()
            assertThat(value.turns).isEqualTo(listOf(createTurn(listOf(shot1), initialScore = 101)))
        }
    }

    @Test
    fun `test undo shot called two times when last turn has one shot removes only last shot`() = runBlocking {
        val initialScore = 101
        val sut = PlayerHistoryManager(
            player = defaultPlayer,
            goal = initialScore,
            finishWithDoubles = false
        )

        val shot1 = Shot(Sector.Triple2)
        val shot2 = Shot(Sector.Triple3)
        val shot3 = Shot(Sector.SingleInner1)
        val shot4 = Shot(Sector.Double5)
        sut.makeShot(shot1)
        sut.makeShot(shot2)
        sut.makeShot(shot3)
        sut.makeShot(shot4)
        sut.undoLastShot()
        sut.undoLastShot()

        sut.playerHistory.test {
            val value = awaitItem()
            assertThat(value.turns).isEqualTo(listOf(
                createTurn(listOf(shot1, shot2, shot3), initialScore = 101),
                createTurn(listOf(), initialScore = 101 - sumUpShotsValue(shots = listOf(shot1, shot2, shot3)))
            ))
        }
    }

    private fun createTurn(shots: List<Shot>, initialScore: Int): Turn {
        val shots = if (shots.count() > 3) shots.subList(0, 3) else shots
        val sum = sumUpShotsValue(shots)
        val isOverkill = initialScore < sum
        return Turn(
            shots = shots,
            leftAfter = if (isOverkill) initialScore else initialScore - sum,
            isOverkill = isOverkill
        )
    }

    private fun sumUpShotsValue(shots: List<Shot>): Int {
        return shots.fold(0) { acc, shot -> acc + shot.sector.value }
    }

    // write tests for undo shot when last turn has one shot removes last shot
    @Test
    fun `test undo shot when last turn has one shot removes last shot`() = runBlocking {
        val initialScore = 101
        val sut = PlayerHistoryManager(
            player = defaultPlayer,
            goal = initialScore,
            finishWithDoubles = false
        )

        val shot1 = Shot(Sector.Triple2)
        val shot2 = Shot(Sector.Triple3)
        val shot3 = Shot(Sector.SingleInner1)
        val shot4 = Shot(Sector.Double5)
        sut.makeShot(shot1)
        sut.makeShot(shot2)
        sut.makeShot(shot3)
        sut.makeShot(shot4)
        sut.undoLastShot()
        sut.undoLastShot()

        sut.playerHistory.test {
            val value = awaitItem()
            assertThat(value.turns).isEqualTo(listOf(
                createTurn(listOf(shot1, shot2, shot3), initialScore = 101),
                createTurn(listOf(), initialScore = 101 - sumUpShotsValue(shots = listOf(shot1, shot2, shot3)))
            ))
        }
    }

    @Test
    fun `test undo shot when last turn has one shot removes last shot 2`() = runBlocking {
        val initialScore = 101
        val sut = PlayerHistoryManager(
            player = defaultPlayer,
            goal = initialScore,
            finishWithDoubles = false
        )

        val shot1 = Shot(Sector.Triple2)
        val shot2 = Shot(Sector.Triple3)
        val shot3 = Shot(Sector.SingleInner1)
        val shot4 = Shot(Sector.Double5)
        sut.makeShot(shot1)
        sut.makeShot(shot2)
        sut.makeShot(shot3)
        sut.makeShot(shot4)
        sut.undoLastShot()
        sut.undoLastShot()
        sut.undoLastShot()

        sut.playerHistory.test {
            val value = awaitItem()
            assertThat(value.turns).isEqualTo(listOf(
                createTurn(listOf(shot1, shot2, shot3), initialScore = 101),
                createTurn(listOf(), initialScore = 101 - sumUpShotsValue(shots = listOf(shot1, shot2, shot3)))
            ))
        }
    }

    @Test
    fun `test undo shot when last turn has one shot removes last shot 3`() = runBlocking {
        val initialScore = 101
        val sut = PlayerHistoryManager(
            player = defaultPlayer,
            goal = initialScore,
            finishWithDoubles = false
        )

        val shot1 = Shot(Sector.Triple2)
        val shot2 = Shot(Sector.Triple3)
        val shot3 = Shot(Sector.SingleInner1)
        val shot4 = Shot(Sector.Double5)
        sut.makeShot(shot1)
        sut.makeShot(shot2)
        sut.makeShot(shot3)
        sut.makeShot(shot4)
        sut.undoLastShot()
        sut.undoLastShot()
        sut.undoLastShot()
        sut.undoLastShot()

        sut.playerHistory.test {
            val value = awaitItem()
            assertThat(value.turns).isEqualTo(listOf(
                createTurn(listOf(shot1, shot2, shot3), initialScore = 101),
                createTurn(listOf(), initialScore = 101 - sumUpShotsValue(shots = listOf(shot1, shot2, shot3)))
            ))
        }
    }
}
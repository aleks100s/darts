package com.alextos.darts.statistics.presentation.most_frequent_shots

import com.alextos.darts.core.util.toCommonFlow
import com.alextos.darts.core.domain.GetPlayersUseCase
import com.alextos.darts.statistics.domain.use_cases.most_frequent_shots.GetPlayersMostFrequentShotsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class MostFrequentShotsViewModel(
    getPlayersUseCase: GetPlayersUseCase,
    getPlayersMostFrequentShotsUseCase: GetPlayersMostFrequentShotsUseCase,
    coroutineScope: CoroutineScope?
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(MostFrequentShotsState())
    val state = getPlayersUseCase.execute()
        .map { players ->
            getPlayersMostFrequentShotsUseCase.execute(players)
        }
        .combine(_state) { playersShots, state ->
            state.copy(mostFrequentShots = playersShots)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            MostFrequentShotsState()
        )
        .toCommonFlow()
}
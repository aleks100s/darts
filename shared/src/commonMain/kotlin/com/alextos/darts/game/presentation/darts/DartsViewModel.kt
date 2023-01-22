package com.alextos.darts.game.presentation.darts

import com.alextos.darts.core.util.toCommonFlow
import com.alextos.darts.game.domain.models.Set
import com.alextos.darts.game.domain.models.Shot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class DartsViewModel(
    turns: List<List<Shot>>,
    currentPage: Int,
    coroutineScope: CoroutineScope?
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)
    private val _state = MutableStateFlow(
        DartsState(
            turns = turns.map { turn -> turn.filter { !it.sector.isNone() }},
            currentPage = currentPage)
    )

    val state = _state
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            DartsState()
        )
        .toCommonFlow()
}
package com.alextos.darts.android.common.presentation.screens.darts

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alextos.darts.android.common.util.toShots
import com.alextos.darts.game.presentation.darts.DartsViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidDartsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val viewModel by lazy {
        val turns = savedStateHandle.get<String>("turns")?.toShots() ?: listOf()
        val currentPage = savedStateHandle.get<String>("currentPage")?.toInt() ?: 0
        DartsViewModel(turns = turns, currentPage = currentPage, viewModelScope)
    }

    val state = viewModel.state
}
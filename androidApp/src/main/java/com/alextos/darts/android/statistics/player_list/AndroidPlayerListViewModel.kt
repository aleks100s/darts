package com.alextos.darts.android.statistics.player_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alextos.darts.core.domain.useCases.GetPlayersUseCase
import com.alextos.darts.statistics.presentation.player_list.PlayerListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidPlayerListViewModel @Inject constructor(
    getPlayersUseCase: GetPlayersUseCase
): ViewModel() {
    private val viewModel by lazy {
        PlayerListViewModel(getPlayersUseCase, viewModelScope)
    }

    val state = viewModel.state
}
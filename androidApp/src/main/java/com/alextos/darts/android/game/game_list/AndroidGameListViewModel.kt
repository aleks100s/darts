package com.alextos.darts.android.game.game_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alextos.darts.game.domain.useCases.DeleteGameUseCase
import com.alextos.darts.game.domain.useCases.GetGamesUseCase
import com.alextos.darts.game.presentation.game_list.GameListEvent
import com.alextos.darts.game.presentation.game_list.GameListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidGameListViewModel @Inject constructor(
    getGamesUseCase: GetGamesUseCase,
    deleteGameUseCase: DeleteGameUseCase
): ViewModel() {
    private val viewModel by lazy {
        GameListViewModel(deleteGameUseCase, getGamesUseCase, viewModelScope)
    }

    val state = viewModel.state

    fun onEvent(event: GameListEvent) {
        viewModel.onEvent(event)
    }
}
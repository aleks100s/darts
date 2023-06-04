package com.alextos.darts.android.game.game_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alextos.darts.debug.PrepopulateDatabaseUseCase
import com.alextos.darts.game.domain.useCases.DeleteGameUseCase
import com.alextos.darts.game.domain.useCases.GetGamesUseCase
import com.alextos.darts.game.presentation.game_list.GameListEvent
import com.alextos.darts.game.presentation.game_list.GameListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidGameListViewModel @Inject constructor(
    getGamesUseCase: GetGamesUseCase,
    deleteGameUseCase: DeleteGameUseCase,
    prepopulateDatabaseUseCase: PrepopulateDatabaseUseCase
): ViewModel() {
    private val viewModel by lazy {
        GameListViewModel(deleteGameUseCase, prepopulateDatabaseUseCase, getGamesUseCase, viewModelScope)
    }

    val state = viewModel.state

    fun onEvent(event: GameListEvent) {
        viewModel.onEvent(event)
    }

    fun prepopulateDatabase() {
        viewModel.prepopulateDatabase()
    }
}
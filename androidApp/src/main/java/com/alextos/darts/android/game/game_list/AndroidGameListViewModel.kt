package com.alextos.darts.android.game.game_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alextos.darts.game.domain.useCases.GetGamesUseCase
import com.alextos.darts.game.presentation.game_list.GameListEvent
import com.alextos.darts.game.presentation.game_list.GameListViewModel
import com.revenuecat.purchases.Offerings
import com.revenuecat.purchases.Purchases
import com.revenuecat.purchases.PurchasesError
import com.revenuecat.purchases.getOfferingsWith
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AndroidGameListViewModel @Inject constructor(
    private val getGamesUseCase: GetGamesUseCase
): ViewModel() {
    private val viewModel by lazy {
        GameListViewModel(getGamesUseCase, viewModelScope)
    }

    val state = viewModel.state

    val billing = MutableStateFlow<Offerings?>(null)
    val error = MutableStateFlow<PurchasesError?>(null)

    init {
        Purchases.sharedInstance.getOfferingsWith(
            onError = { e ->
                error.update { e }
            },
            onSuccess = { b ->
                billing.update { b }
            }
        )
    }

    fun onEvent(event: GameListEvent) {
        viewModel.onEvent(event)
    }
}
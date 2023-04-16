package com.alextos.darts.android.statistics.biggest_final_turn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alextos.darts.core.domain.useCases.GetPlayersUseCase
import com.alextos.darts.statistics.domain.use_cases.biggest_final_turn.GetPlayersBiggestFinalTurnUseCase
import com.alextos.darts.statistics.presentation.biggest_final_turn.BiggestFinalTurnViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidBiggestFinalTurnViewModel @Inject constructor(
    getPlayersUseCase: GetPlayersUseCase,
    getPlayersBiggestFinalTurnUseCase: GetPlayersBiggestFinalTurnUseCase
): ViewModel() {
    private val viewModel = BiggestFinalTurnViewModel(
        getPlayersUseCase,
        getPlayersBiggestFinalTurnUseCase,
        viewModelScope
    )

    val state = viewModel.state
}
package com.alextos.darts.android.game.calculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alextos.darts.game.domain.calculator.CalculatorManager
import com.alextos.darts.game.presentation.calculator.CalculatorEvent
import com.alextos.darts.game.presentation.calculator.CalculatorViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidCalculatorViewModel @Inject constructor(): ViewModel(){
    private val viewModel by lazy {
        CalculatorViewModel(CalculatorManager(), viewModelScope)
    }

    val state = viewModel.state

    fun onEvent(event: CalculatorEvent) {
        viewModel.onEvent(event)
    }
}
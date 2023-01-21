package com.alextos.darts.android.statistics.statistics

import androidx.lifecycle.ViewModel
import com.alextos.darts.statistics.presentation.statistics.StatisticsEvent
import com.alextos.darts.statistics.presentation.statistics.StatisticsViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidStatisticsViewModel @Inject constructor(): ViewModel() {
    private val viewModel = StatisticsViewModel()
}
package com.alextos.darts.android.statistics.statistics

import androidx.lifecycle.ViewModel
import com.revenuecat.purchases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AndroidStatisticsViewModel @Inject constructor(): ViewModel() {

    private val _isStatisticsAvailable = MutableStateFlow(false)
    val isStatisticsAvailable: StateFlow<Boolean> = _isStatisticsAvailable

    private val _pack = MutableStateFlow<Package?>(null)
    val pack: StateFlow<Package?> = _pack

    init {
        checkPremium()
    }

    fun checkCustomerInfo(customerInfo: CustomerInfo) {
        if (customerInfo.entitlements["Premium"]?.isActive == true) {
            _isStatisticsAvailable.update { true }
        } else {
            fetchPremiumPackage()
        }
    }

    private fun checkPremium() {
        Purchases.sharedInstance.getCustomerInfoWith { customerInfo ->
            checkCustomerInfo(customerInfo)
        }
    }

    private fun fetchPremiumPackage() {
        Purchases.sharedInstance.getOfferingsWith(
            onSuccess = { offerings ->
                offerings["premium_statistics"]?.get("Lifetime")?.let { p ->
                    _pack.update { p }
                }
            }
        )
    }
}
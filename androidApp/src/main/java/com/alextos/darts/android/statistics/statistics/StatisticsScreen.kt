package com.alextos.darts.android.statistics.statistics

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alextos.darts.android.R
import com.alextos.darts.android.common.presentation.components.SingleSelectableItem
import com.alextos.darts.android.common.presentation.screens.Screen
import com.alextos.darts.android.common.util.getActivity
import com.alextos.darts.statistics.presentation.statistics.StatisticsEvent
import com.revenuecat.purchases.CustomerInfo
import com.revenuecat.purchases.Package
import com.revenuecat.purchases.Purchases
import com.revenuecat.purchases.purchasePackageWith

@Composable
fun StatisticsScreen(onEvent: (StatisticsEvent) -> Unit) {
    val viewModel: AndroidStatisticsViewModel = hiltViewModel()
    val isStatisticsAvailable = viewModel.isStatisticsAvailable.collectAsState()
    val pack = viewModel.pack.collectAsState()
    val context = LocalContext.current

    val events = listOf(
        StatisticsEvent.ShowBestSet to stringResource(id = R.string.best_set_statistics),
        StatisticsEvent.ShowMostFrequentShots to stringResource(id = R.string.most_frequent_shots_statistics),
        StatisticsEvent.ShowBiggestFinalSet to stringResource(id = R.string.biggest_final_set_statistics),
        StatisticsEvent.ShowAverageValues to stringResource(id = R.string.average_values_statistics),
        StatisticsEvent.ShowShotDistribution to stringResource(id = R.string.shot_distribution_statistics),
        StatisticsEvent.ShowVictoryDistribution to stringResource(id = R.string.victory_distribution_statistics)
    )

    if (isStatisticsAvailable.value) {
        Screen(title = stringResource(id = R.string.statistics)) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(events) { event ->
                    SingleSelectableItem(text = event.second) {
                        onEvent(event.first)
                    }
                }
            }
        }
    } else {
        Screen(title = stringResource(id = R.string.statistics)) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Text(text = stringResource(id = R.string.premium_info))

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        pack.value?.let { pack ->
                            unlockPremium(context, pack) {
                                viewModel.checkCustomerInfo(it)
                            }
                        }
                    },
                    enabled = pack.value != null
                ) {
                    Text(text = stringResource(id = R.string.buy_premium))
                }
            }
        }
    }
}

private fun unlockPremium(
    context: Context,
    pack: Package,
    onSuccess: (CustomerInfo) -> Unit
) {
    context.getActivity()?.let { activity ->
        Purchases.sharedInstance.purchasePackageWith(
            activity,
            pack,
            onError = { error, userCancelled ->
                if (!userCancelled) {
                    Toast.makeText(context, error.message, Toast.LENGTH_LONG).show()
                }
            },
            onSuccess = { product, customerInfo ->
                onSuccess(customerInfo)
            }
        )
    }
}
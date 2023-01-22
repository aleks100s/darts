package com.alextos.darts.statistics.domain.models

data class ShotDistribution(
    val missesCount: Int,
    val doubleBullseyeCount: Int,
    val bullseyeCount: Int,
    val singlesCount: Int,
    val doublesCount: Int,
    val triplesCount: Int
) {
    fun missesPercent(): Float {
        return missesCount.toFloat() / totalCount() * 100
    }

    fun doubleBullseyePercent(): Float {
        return doubleBullseyeCount.toFloat() / totalCount() * 100
    }

    fun bullseyePercent(): Float {
        return bullseyeCount.toFloat() / totalCount() * 100
    }

    fun singlesPercent(): Float {
        return singlesCount.toFloat() / totalCount() * 100
    }

    fun doublesPercent(): Float {
        return doublesCount.toFloat() / totalCount() * 100
    }

    fun triplesPercent(): Float {
        return triplesCount.toFloat() / totalCount() * 100
    }

    private fun totalCount(): Int {
        return missesCount + doubleBullseyeCount + bullseyeCount + singlesCount + doublesCount + triplesCount
    }
}

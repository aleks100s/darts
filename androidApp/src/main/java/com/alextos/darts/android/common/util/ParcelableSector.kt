package com.alextos.darts.android.common.util

import com.alextos.darts.core.domain.Sector
import com.alextos.darts.core.domain.Shot

fun List<List<Sector>>.toStringNavArgument(): String {
    return joinToString(";") { sectors ->
        "{${sectors.map { it.id }.joinToString(",")}}"
    }
}

fun String.toShots(): List<List<Shot>> {
    return split(";")
        .map { it.trim('{', '}') }
        .map { sectorsString ->
            sectorsString.split(",")
                .map { it.toInt() }
                .map { Sector.getSector(it) }
                .map { Shot(it) }
        }
}
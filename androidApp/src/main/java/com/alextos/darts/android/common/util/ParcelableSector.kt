package com.alextos.darts.android.common.util

import com.alextos.darts.core.domain.Sector
import com.alextos.darts.core.domain.Shot

fun List<List<Sector>>.toStringNavArgument(): String {
    return joinToString(";") { sectors ->
        "{${sectors.map { it.id }.joinToString(",")}}"
    }
}

fun String.toShots(): List<List<Shot>> {
    val bb = split(";")
        .map { it.trim('{', '}') }
        .map { sectorsString ->
            val aaa = sectorsString.split(",")
                .map { it.toInt() }
                .map { Sector.getSector(it) }
                .map { Shot(it) }
                aaa
        }
    return bb
}
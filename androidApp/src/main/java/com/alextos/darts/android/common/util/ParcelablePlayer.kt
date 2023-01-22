package com.alextos.darts.android.common.util

import com.alextos.darts.core.domain.Player

fun List<Player>.toStringNavArgument(): String {
    return joinToString(";") { "{id=${it.id},name=${it.name}}" }
}

fun String.toPlayerList(): List<Player> {
    return split(";")
        .map { it.trim('{', '}') }
        .map { argument ->
            val fields = argument.split(",")
            val id = fields[0].split("=")[1].toLong()
            val name = fields[1].split("=")[1]
            Player(id, name)
        }
}
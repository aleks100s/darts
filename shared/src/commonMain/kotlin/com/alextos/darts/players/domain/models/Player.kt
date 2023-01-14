package com.alextos.darts.players.domain.models

import com.alextos.darts.core.util.initial

data class Player(
    val id: Long? = null,
    val name: String
) {
    fun initials(): String {
        val parts = name.split(" ")
        return when (parts.size) {
            0 -> ""
            1 -> name.first().toString()
            2 -> parts.joinToString(separator = "") { it.initial() }
            else -> parts.subList(0, 2).joinToString(separator = "") { it.initial() }
        }
    }
}

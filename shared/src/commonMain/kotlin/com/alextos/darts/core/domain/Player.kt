package com.alextos.darts.core.domain

data class Player(
    val id: Long,
    val name: String
) {
    fun initials(): String {
        return name.substring(0 until 1)
    }
}

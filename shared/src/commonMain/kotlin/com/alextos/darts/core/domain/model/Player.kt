package com.alextos.darts.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Player(
    val id: Long,
    val name: String
) {
    fun initials(): String {
        return name.substring(0 until 1)
    }
}

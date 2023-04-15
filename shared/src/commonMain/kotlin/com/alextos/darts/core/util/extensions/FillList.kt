package com.alextos.darts.core.util.extensions

fun<E> MutableList<E>.fill(value: E, toSize: Int) {
    val count = toSize - count()
    if (count > 0) {
        repeat(count) {
            add(value)
        }
    }
}
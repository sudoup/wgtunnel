package com.zaneschepke.wireguardautotunnel.util.extensions

import kotlin.math.pow
import kotlin.math.roundToInt

fun <T> List<T>.update(index: Int, item: T): List<T> = toMutableList().apply { this[index] = item }

typealias TunnelName = String?

typealias QuickConfig = String

fun <T, R : Comparable<R>> List<T>.isSortedBy(selector: (T) -> R): Boolean {
    return zipWithNext().all { (a, b) -> selector(a) <= selector(b) }
}

fun Int.toMillis(): Long {
    return this * 1_000L
}

fun Double.round(decimals: Int): Double {
    val factor = 10.0.pow(decimals)
    return (this * factor).roundToInt() / factor
}

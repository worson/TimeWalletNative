package com.worson.lib.appbasic.kotlinex

inline fun <T> T.notEquals(other: T, block: (T) -> Unit) {

    if (this != null && this != other) {
        block(this)
    }
}


inline fun <T> T.ifNull(block: () -> T): T {
    return this ?: block()
}

inline fun Boolean.ifTrue(block: () -> Unit): Boolean {
    return this.also {
        if (it) block()
    }
}

inline fun Boolean.ifFalse(block: () -> Unit): Boolean {
    return this.also {
        if (!it) block()
    }
}

/**
 * return x is in [y, z)
 */
infix fun Int.rangeIn(pair: Pair<Int, Int>): Boolean {
    return this >= pair.first && this < pair.second
}

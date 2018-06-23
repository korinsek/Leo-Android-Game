package com.mag.denis.game.ui.main.model

open class Condition(open val type: String) {
    companion object {
        const val TYPE_TRUE = "TYPE_TRUE"
        const val TYPE_FALSE = "TYPE_FALSE"
    }
}

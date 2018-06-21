package com.mag.denis.game.ui.main.model

data class Condition(val value: String, val type: String){
    companion object {
        const val TYPE_TRUE = "TYPE_TRUE"
        const val TYPE_FALSE = "TYPE_FALSE"
    }
}

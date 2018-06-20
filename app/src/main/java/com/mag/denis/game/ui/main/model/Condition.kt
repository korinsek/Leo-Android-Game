package com.mag.denis.game.ui.main.model

data class Condition(val condition: String, val trueCommands: List<Command>, val falseCommands: List<Command>) : Command

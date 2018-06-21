package com.mag.denis.game.ui.main.model

data class IfCondition(val condition: Condition, val trueCommands: List<Command>, val falseCommands: List<Command>) : Command

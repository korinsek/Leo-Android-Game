package com.mag.denis.game.manager

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LevelManager @Inject constructor(private val gameManager: GameManager) {
    private val level1 = listOf(
            listOf("1", "1", "1", "2", "1"),
            listOf("0", "0", "0", "2", "0"),
            listOf("0", "0", "0", "2", "0"),
            listOf("0", "0", "0", "1", "1"))

    private val level2 = listOf(
            listOf("1", "1", "0", "0", "0", "0", "0"),
            listOf("0", "1", "1", "0", "0", "0", "0"),
            listOf("0", "0", "1", "1", "0", "0", "0"),
            listOf("0", "0", "0", "1", "1", "0", "0"),
            listOf("0", "0", "0", "0", "1", "1", "0"),
            listOf("0", "0", "0", "0", "0", "1", "2"))

    fun getCurrentLevel(): List<List<String>> {
        return when (gameManager.getCurrentLevel()) {
            1 -> level1
            2 -> level2
            else -> throw IllegalStateException("Level don't exists")
        }
    }
}

package com.mag.denis.game.manager

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LevelManager @Inject constructor(private val gameManager: GameManager) {
    private val level1 = listOf(
            listOf("0", "0", "0", "0", "0"),
            listOf("1", "1", "1S", "1S", "1S", "F"))

    private val level2 = listOf(
            listOf("0", "0", "0", "0", "0"),
            listOf("1", "1", "1", "1", "0"),
            listOf("0", "0", "0", "1", "F"))

    private val level3 = listOf(
            listOf("1", "1", "1", "2", "1"),
            listOf("0", "0", "0", "2", "0"),
            listOf("0", "0", "0", "2", "0"),
            listOf("0", "0", "0", "1", "F"))

    private val level4 = listOf(
            listOf("1", "1", "0", "0", "0", "0", "0"),
            listOf("0", "1", "1", "0", "0", "0", "0"),
            listOf("0", "0", "1", "1", "0", "0", "0"),
            listOf("0", "0", "0", "1", "1", "0", "0"),
            listOf("0", "0", "0", "0", "1", "1", "0"),
            listOf("0", "0", "0", "0", "0", "1", "F"))

    private val level5 = listOf(
            listOf("1", "1", "1", "1", "1", "1", "1", "2"),
            listOf("0", "0", "0", "0", "0", "0", "0", "1"),
            listOf("1", "1", "1", "1", "1", "1", "1", "1"),
            listOf("1", "0", "0", "0", "0", "0", "0", "0"),
            listOf("1", "1", "1", "1", "1", "1", "1", "F"))

    private val level6 = listOf(
            listOf("1", "1", "1", "0", "0", "0", "0"),
            listOf("1", "0", "1", "0", "0", "0", "0"),
            listOf("1", "0", "1", "1", "1", "1", "0"),
            listOf("1", "0", "0", "0", "0", "1", "0"),
            listOf("1", "1", "1", "1", "1", "1", "F"))

    private val level7 = listOf(
            listOf("1", "1", "1", "1", "1", "1", "2"),
            listOf("0", "1", "0", "0", "0", "0", "0"),
            listOf("0", "1", "1", "1", "0", "0", "0"),
            listOf("0", "0", "0", "1", "0", "0", "0"),
            listOf("0", "0", "0", "1", "1", "1", "F"))

    private val level8 = listOf(
            listOf("0", "0", "0", "0", "0", "0", "1"),
            listOf("0", "2", "0", "0", "0", "0", "1"),
            listOf("0", "1", "1", "1", "0", "0", "1"),
            listOf("0", "0", "0", "1", "0", "1", "1"),
            listOf("0", "0", "0", "1", "1", "1", "F"))

    private val level9 = listOf(
            listOf("0", "0", "0", "1", "0", "0", "0"),
            listOf("2", "0", "0", "1", "0", "0", "1"),
            listOf("1", "0", "0", "1", "0", "0", "1"),
            listOf("1", "1", "1", "1", "1", "1", "1"),
            listOf("0", "0", "0", "F", "0", "0", "0"))

    val numOfLevels = 8

    private val starsLevels = mutableListOf<Int>()

    init {
        for (i in 0 until numOfLevels) {
            starsLevels.add(0)
        }
    }

    fun getCurrentLevel(): List<List<String>> {
        //TODO more levels
        return when (gameManager.getCurrentLevel()) {
            1 -> level1
            2 -> level2
            3 -> level3
            4 -> level4
            5 -> level5
            6 -> level6
            7 -> level7
            8 -> level8
            else -> throw IllegalStateException("Level don't exists")
        }
    }

    fun setStarsForLevel(level: Int, stars: Int) {
        val value = starsLevels[level - 1]
        if (value < stars) {
            starsLevels[level - 1] = stars
        }
    }

    fun getStarsForLevel(level: Int): Int {
        return starsLevels[level - 1]
    }

    fun getAvilableCommandsForLevel(level: Int): List<Int> {
        return when (gameManager.getCurrentLevel()) {
            1 -> listOf(COMMAND_ACTIONS, COMMAND_LOOP, COMMAND_CONDITION)
            2 -> listOf(COMMAND_ACTIONS)
            3 -> listOf(COMMAND_ACTIONS, COMMAND_LOOP)
            4, 5, 6, 7, 8 -> listOf(COMMAND_ACTIONS, COMMAND_LOOP, COMMAND_CONDITION)
            else -> throw IllegalStateException("Level don't exists")
        }
    }

    companion object {
        const val COMMAND_ACTIONS = 1
        const val COMMAND_LOOP = 2
        const val COMMAND_CONDITION = 3
    }
}

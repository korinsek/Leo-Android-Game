package com.mag.denis.game.manager

import android.content.SharedPreferences
import com.mag.denis.game.manager.GameManager.Companion.STAGE_BLOCK
import com.mag.denis.game.manager.GameManager.Companion.STAGE_FLOW
import com.mag.denis.game.manager.GameManager.Companion.STAGE_PSEUDO
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LevelManager @Inject constructor(private val gameManager: GameManager, private val sharedPreferences: SharedPreferences) {

    private val level1 = listOf(
            listOf("0", "0", "0", "0", "0"),
            listOf("1", "1", "1S", "1S", "1S", "F"))

    private val level2 = listOf(
            listOf("0", "0", "0", "0"),
            listOf("1", "1S", "1", "1S"),
            listOf("0", "0", "1S", "0"),
            listOf("0", "0", "F", "0"))

    private val level3 = listOf(
            listOf("1", "1", "1", "1", "1S", "1"),
            listOf("0", "0", "0", "0", "1", "1"),
            listOf("0", "0", "0", "1S", "1", "1S"),
            listOf("F", "1", "1", "1", "1", "1"))

    private val level3Flow = listOf(
            listOf("0", "0", "0", "0", "0", "0"),
            listOf("1", "1", "1", "1", "1", "2S"),
            listOf("0", "0", "0", "0", "0", "F"))

    private val level4 = listOf(
            listOf("1", "1", "0", "0", "0", "0", "0"),
            listOf("0", "1", "1S", "0", "0", "0", "0"),
            listOf("0", "0", "1", "1", "0", "0", "0"),
            listOf("0", "0", "0", "1S", "1", "0", "0"),
            listOf("0", "0", "0", "0", "1", "1S", "0"),
            listOf("0", "0", "0", "0", "0", "F", "0"))

    private val level5 = listOf(
            listOf("1", "1", "1", "1S", "1", "1", "2S"),
            listOf("0", "0", "0", "0", "0", "2", "2"),
            listOf("0", "0", "0", "0", "2", "2", "0"),
            listOf("0", "0", "0", "2", "2", "0", "0"),
            listOf("0", "0", "1S", "2", "0", "0", "0"),
            listOf("0", "0", "0", "F", "0", "0", "0"))

    private val level6 = listOf(
            listOf("1", "1", "1", "1", "2S", "0", "0"),
            listOf("2", "0", "1", "0", "2", "0", "0"),
            listOf("2", "0", "1", "1", "2", "0", "0"),
            listOf("2", "0", "0", "0", "2", "0", "0"),
            listOf("2", "1", "1", "1S", "1", "1S", "F"))

    private val level7 = listOf(
            listOf("0", "0", "0", "0", "0", "0", "0"),
            listOf("1", "2", "1", "2", "1", "2", "F"),
            listOf("0", "1S", "1", "1S", "1", "1S", "0"),
            listOf("0", "0", "2", "1", "2", "0", "0"),
            listOf("0", "0", "0", "2", "0", "0", "0"),
            listOf("0", "0", "0", "0", "0", "0", "0"))

    private val level8 = listOf(
            listOf("0", "0", "0", "0", "0", "0", "1"),
            listOf("0", "0", "0", "0", "0", "1", "2"),
            listOf("0", "0", "0", "0", "1S", "2", "1"),
            listOf("0", "0", "0", "1", "2S", "1", "0"),
            listOf("0", "0", "1S", "2", "1", "1", "F"))


    private val starsBlockLevels = mutableListOf<Int>()
    private val starsFlowLevels = mutableListOf<Int>()
    private val starsPseudoLevels = mutableListOf<Int>()

    init {
        sharedPreferences.getString(STARS_STAGE_BLOCK_PREFERENCES_ID, "0".repeat(NUM_LEVELS_BLOCKS))
                .forEach { starsBlockLevels.add(it.toString().toInt()) }


        sharedPreferences.getString(STARS_STAGE_FLOW_PREFERENCES_ID, "0".repeat(NUM_LEVELS_FLOW))
                .forEach { starsFlowLevels.add(it.toString().toInt()) }

        sharedPreferences.getString(STARS_STAGE_PSEUDO_PREFERENCES_ID, "0".repeat(NUM_LEVELS_PSEUDO))
                .forEach { starsPseudoLevels.add(it.toString().toInt()) }
    }

    fun getCurrentLevel(): List<List<String>> {
        if (gameManager.getCurrentStage() == STAGE_FLOW) {
            return when (gameManager.getCurrentLevel()) {
                1 -> level1
                2 -> level2
                3 -> level3Flow
                4 -> level4
                else -> throw IllegalStateException("Level don't exists")
            }
        } else {
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
    }

    fun setStarsForLevel(level: Int, stars: Int) {
        val starsLevels = getCurrentLevelStarsList()
        val value = starsLevels[level - 1]
        if (value < stars) {
            starsLevels[level - 1] = stars
        }
    }

    fun getStarsForLevel(level: Int): Int {
        return getCurrentLevelStarsList()[level - 1]
    }

    fun getAvailableCommands(): List<Int> {
        return when (gameManager.getCurrentLevel()) {
            1, 2 -> listOf(COMMAND_ACTIONS)
            3, 4 -> listOf(COMMAND_ACTIONS, COMMAND_LOOP)
            5, 6, 7, 8 -> listOf(COMMAND_ACTIONS, COMMAND_LOOP, COMMAND_CONDITION)
            else -> throw IllegalStateException("Level don't exists")
        }
    }

    fun saveScores() {
        val editor = sharedPreferences.edit()
        editor.putString(getStagePreference(), getCurrentLevelStarsList().joinToString(separator = ""))
        editor.apply()
    }

    fun numOfLevelsForStage(): Int {
        return when (gameManager.getCurrentStage()) {
            STAGE_BLOCK -> NUM_LEVELS_BLOCKS
            STAGE_FLOW -> NUM_LEVELS_FLOW
            STAGE_PSEUDO -> NUM_LEVELS_PSEUDO
            else -> throw IllegalStateException("Invalid stage preferences")
        }
    }

    private fun getStagePreference(): String {
        return when (gameManager.getCurrentStage()) {
            STAGE_BLOCK -> STARS_STAGE_BLOCK_PREFERENCES_ID
            STAGE_FLOW -> STARS_STAGE_FLOW_PREFERENCES_ID
            STAGE_PSEUDO -> STARS_STAGE_PSEUDO_PREFERENCES_ID
            else -> throw IllegalStateException("Invalid stage preferences")
        }
    }

    private fun getCurrentLevelStarsList(): MutableList<Int> {
        return when (gameManager.getCurrentStage()) {
            STAGE_BLOCK -> starsBlockLevels
            STAGE_FLOW -> starsFlowLevels
            STAGE_PSEUDO -> starsPseudoLevels
            else -> throw IllegalStateException("Invalid stage preferences")
        }
    }

    fun isGameFinished(): Boolean {
        val stageBlockCompleted = starsBlockLevels.count { it > 0 } == NUM_LEVELS_BLOCKS
        val stageFlowCompleted = starsFlowLevels.count { it > 0 } == NUM_LEVELS_FLOW
        val stagePseudoCompleted = starsPseudoLevels.count { it > 0 } == NUM_LEVELS_PSEUDO
        return stageBlockCompleted && stageFlowCompleted && stagePseudoCompleted
    }

    fun getSumStars(): Int {
        return starsBlockLevels.sumBy { it } + starsFlowLevels.sumBy { it } + starsPseudoLevels.sumBy { it }
    }


    companion object {
        const val COMMAND_ACTIONS = 1
        const val COMMAND_LOOP = 2
        const val COMMAND_CONDITION = 3

        const val STARS_STAGE_BLOCK_PREFERENCES_ID = "com.mag.denis.game.manager.levelManager.starsBlock"
        const val STARS_STAGE_FLOW_PREFERENCES_ID = "com.mag.denis.game.manager.levelManager.starsFlow"
        const val STARS_STAGE_PSEUDO_PREFERENCES_ID = "com.mag.denis.game.manager.levelManager.starsPseudo"

        const val NUM_LEVELS_BLOCKS = 8
        const val NUM_LEVELS_FLOW = 4
        const val NUM_LEVELS_PSEUDO = 8
    }
}

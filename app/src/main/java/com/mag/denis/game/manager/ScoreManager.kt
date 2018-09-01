package com.mag.denis.game.manager

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScoreManager @Inject constructor(private val sharedPreferences: SharedPreferences) {

    private val scores = mutableListOf<Int>()

    init {
        sharedPreferences.getString(SCORE_PREFERENCES_ID, "").forEach { scores.add(it.toString().toInt()) }
    }

    fun addScore(newCompletedStars: Int) {
        scores.add(newCompletedStars)
        val editor = sharedPreferences.edit()
        editor.putString(SCORE_PREFERENCES_ID, scores.joinToString(separator = ""))
        editor.apply()
    }

    fun getScores(): MutableList<Int> {
        return scores
    }

    companion object {
        const val SCORE_PREFERENCES_ID = "com.mag.denis.game.manager.ScoreManager.score"
    }
}

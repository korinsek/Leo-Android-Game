package com.mag.denis.game.manager

import android.content.SharedPreferences
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameManager @Inject constructor(private val sharedPreferences: SharedPreferences) {
    private var stages = arrayListOf(STAGE_BLOCK, STAGE_FLOW, STAGE_PSEUDO)

    fun getLanguage(): String {
        return sharedPreferences.getString(LANGUAGE_PREFERENCES_ID, LANGUAGE_KOTLIN)
    }

    fun saveLanguage(selectedLanguage: String?) {
        val editor = sharedPreferences.edit()
        editor.putString(LANGUAGE_PREFERENCES_ID, selectedLanguage)
        editor.apply()
    }

    fun setCurrentLevel(level: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(CURRENT_LEVEL_PREFERENCES_ID, level)
        editor.apply()
    }

    fun setMaxLevelAchieved(level: Int) {
        if (getMaxLevelAchieved() < level) {
            val editor = sharedPreferences.edit()
            editor.putInt("$MAX_LEVEL_PREFERENCES_ID${getCurrentStage()}", level)
            editor.apply()
        }
    }

    fun getMaxLevelAchieved(): Int {
        return sharedPreferences.getInt("$MAX_LEVEL_PREFERENCES_ID${getCurrentStage()}", 1)
    }

    fun getCurrentLevel(): Int {
        return sharedPreferences.getInt(CURRENT_LEVEL_PREFERENCES_ID, 1)
    }

    fun setStages(stages: ArrayList<String>) {
        this.stages = stages
    }

    fun getStages(): ArrayList<String> {
        return stages
    }

    fun getCurrentStage(): String {
        return sharedPreferences.getString(CURRENT_STAGE_PREFERENCES_ID, stages.firstOrNull())
    }

    fun setCurrentStage(stage: String) {
        val editor = sharedPreferences.edit()
        editor.putString(CURRENT_STAGE_PREFERENCES_ID, stage)
        editor.apply()
    }

    fun getGameLoop(fps: Long): Observable<Long> {
        return Observable.interval(1000L / fps, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
    }

    companion object {
        const val LANGUAGE_PREFERENCES_ID = "com.mag.denis.game.ui.settings.language"
        const val CURRENT_LEVEL_PREFERENCES_ID = "com.mag.denis.game.ui.settings.currentLevel"
        const val MAX_LEVEL_PREFERENCES_ID = "com.mag.denis.game.ui.settings.maxLevel"
        const val CURRENT_STAGE_PREFERENCES_ID = "com.mag.denis.game.ui.settings.currentStage"

        const val LANGUAGE_PYTHON = "Python"
        const val LANGUAGE_KOTLIN = "Kotlin"

        const val STAGE_BLOCK = "BLOCKS"
        const val STAGE_FLOW = "FLOWCHART"
        const val STAGE_PSEUDO = "PSEUDOCODE"
    }
}

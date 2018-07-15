package com.mag.denis.game.ui.settings

import android.content.SharedPreferences
import com.mag.denis.game.manager.GameManager
import com.mag.denis.game.manager.GameManager.Companion.LANGUAGE_KOTLIN
import com.mag.denis.game.manager.GameManager.Companion.LANGUAGE_PYTHON


class SettingsPresenterImpl(private val view: SettingsView, private val sharedPreferences: SharedPreferences, private val gameManager: GameManager) : SettingsPresenter {

    private var selectedLanguage: String? = null

    override fun onCreate() {
        //TODO load stages to view
        selectedLanguage = gameManager.getLanguage()
        view.selectLanguage(selectedLanguage == LANGUAGE_KOTLIN)
    }

    override fun onBackClicked(stages: ArrayList<String>) {
        saveSelectedLanguage(stages)
        view.closeView()
    }

    override fun onBackPressed(stages: ArrayList<String>) {
        saveSelectedLanguage(stages)
    }

    private fun saveSelectedLanguage(stages: ArrayList<String>) {
        gameManager.setStages(stages)
        gameManager.saveLanguage(selectedLanguage)
    }

    override fun onPythonClicked() {
        selectedLanguage = LANGUAGE_PYTHON
        view.selectLanguage(false)
    }

    override fun onKotlinClicked() {
        selectedLanguage = LANGUAGE_KOTLIN
        view.selectLanguage(true)
    }
}

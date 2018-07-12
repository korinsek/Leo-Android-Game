package com.mag.denis.game.ui.settings

import android.content.SharedPreferences


class SettingsPresenterImpl(private val view: SettingsView, private val sharedPreferences: SharedPreferences) : SettingsPresenter {

    private var selectedLanguage: String? = null

    override fun onCreate() {
        selectedLanguage = sharedPreferences.getString(LANGUAGE_PREFERENCES_ID, LANGUAGE_KOTLIN)
        view.selectLanguage(selectedLanguage == LANGUAGE_KOTLIN)
    }

    override fun onBackClicked() {
        saveSelectedLanguage()
        view.closeView()
    }

    override fun onBackPressed() {
        saveSelectedLanguage()
    }

    private fun saveSelectedLanguage() {
        val editor = sharedPreferences.edit()
        editor.putString(LANGUAGE_PREFERENCES_ID, selectedLanguage)
        editor.apply()
    }

    override fun onPythonClicked() {
        selectedLanguage = LANGUAGE_PYTHON
        view.selectLanguage(false)
    }

    override fun onKotlinClicked() {
        selectedLanguage = LANGUAGE_KOTLIN
        view.selectLanguage(true)
    }

    companion object {
        const val LANGUAGE_PREFERENCES_ID = "com.mag.denis.game.ui.settings.language"

        const val LANGUAGE_PYTHON = "Python"
        const val LANGUAGE_KOTLIN = "Kotlin"
    }
}

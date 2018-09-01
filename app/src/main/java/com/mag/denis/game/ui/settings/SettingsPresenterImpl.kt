package com.mag.denis.game.ui.settings

import com.mag.denis.game.manager.GameManager
import com.mag.denis.game.manager.GameManager.Companion.LANGUAGE_KOTLIN
import com.mag.denis.game.manager.GameManager.Companion.LANGUAGE_PYTHON
import com.mag.denis.game.manager.LanguageManager
import com.mag.denis.game.ui.ActivityScope
import javax.inject.Inject

@ActivityScope
class SettingsPresenterImpl @Inject constructor(private val view: SettingsView, private val gameManager: GameManager, private val languageManager: LanguageManager) : SettingsPresenter {

    private var selectedLanguage: String? = null

    override fun onCreate() {
        val stages = gameManager.getStages()
        view.setupStages(stages[0], stages[1], stages[2])
        selectedLanguage = gameManager.getLanguage()
        view.selectProgLanguage(selectedLanguage == LANGUAGE_KOTLIN)
        view.selectLanguage(languageManager.getLang() == LanguageManager.LANG_CODE_EN)
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
        view.selectProgLanguage(false)
    }

    override fun onKotlinClicked() {
        selectedLanguage = LANGUAGE_KOTLIN
        view.selectProgLanguage(true)
    }

    override fun onEngClicked() {
        languageManager.setLang(LanguageManager.LANG_CODE_EN)
        view.recreateView()
    }

    override fun onSloClicked() {
        languageManager.setLang(LanguageManager.LANG_CODE_SL)
//        view.selectLanguage(false)
        view.recreateView()
    }


}

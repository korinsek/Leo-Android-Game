package com.mag.denis.game.ui.settings

interface SettingsPresenter {
    fun onCreate()
    fun onBackClicked(stages: ArrayList<String>)
    fun onBackPressed(stages: ArrayList<String>)
    fun onPythonClicked()
    fun onKotlinClicked()
    fun onEngClicked()
    fun onSloClicked()
}

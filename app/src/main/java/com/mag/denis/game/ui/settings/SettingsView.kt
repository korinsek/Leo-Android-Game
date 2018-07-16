package com.mag.denis.game.ui.settings

interface SettingsView {
    fun closeView()
    fun selectLanguage(isKotlin: Boolean)
    fun setupStages(stage1: String, stage2: String, stage3: String)
}

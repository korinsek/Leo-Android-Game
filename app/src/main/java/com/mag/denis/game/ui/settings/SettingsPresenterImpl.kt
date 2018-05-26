package com.mag.denis.game.ui.settings

class SettingsPresenterImpl(private val view: SettingsView) : SettingsPresenter {
    override fun onBackClicked() {
        view.closeView()
    }
}

package com.mag.denis.game.ui.about

class AboutPresenterImpl(private val view: AboutView) : AboutPresenter {
    override fun onBackClicked() {
        view.closeView()
    }
}

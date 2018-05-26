package com.mag.denis.game.ui.map

class MapPresenterImpl(private val view: MapView) : MapPresenter {
    override fun onLevelClicked() {
        view.openGameView()
    }

    override fun onBackClicked() {
        view.closeView()
    }
}

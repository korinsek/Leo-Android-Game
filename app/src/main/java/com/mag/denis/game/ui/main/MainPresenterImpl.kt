package com.mag.denis.game.ui.main

class MainPresenterImpl(private val view: MainView) : MainPresenter {

    private lateinit var actions: HashMap<Int, String>

    override fun onCreate(placeHoldersCount: Int) {
        actions = HashMap(placeHoldersCount)
    }

    override fun onStartClick() {
        val sortedActions = actions.toList()
                .sortedBy { (key, value) -> key }
                .map { (key, value) -> value }
        view.doActionsInGame(sortedActions)
    }

    override fun addAction(actionType: String, position: Int) {
        actions[position] = actionType
    }
}

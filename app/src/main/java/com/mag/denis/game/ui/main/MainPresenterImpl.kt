package com.mag.denis.game.ui.main

import com.mag.denis.game.R

class MainPresenterImpl(private val view: MainView) : MainPresenter {

    private lateinit var actions: HashMap<Int, String>
    private var placeHoldersCount: Int = 0

    override fun onCreate(placeHoldersCount: Int) {
        this.placeHoldersCount = placeHoldersCount
        actions = HashMap(placeHoldersCount)
    }

    override fun onStartClick() {
        if (actions.size == placeHoldersCount) {
            val sortedActions = actions.toList()
                    .sortedBy { (key, value) -> key }
                    .map { (key, value) -> value }
            view.doActionsInGame(sortedActions)
        } else {
            view.showMessageDialog(R.string.error_empty_actions)
        }
    }

    override fun addAction(actionType: String, position: Int) {
        actions[position] = actionType
    }

    override fun onMenuClick() {
        view.openMenuActivity()
    }
}

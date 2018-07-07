package com.mag.denis.game.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.StringRes
import com.mag.denis.game.R
import com.mag.denis.game.ui.main.dialog.MessageDialog
import com.mag.denis.game.ui.main.model.Command
import com.mag.denis.game.ui.main.view.GameView
import com.mag.denis.game.ui.menu.MenuActivity
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity(), MainView, GameView.OnMessageCallback {

    @Inject lateinit var presenter: MainPresenter

    private var messageDialog: MessageDialog? = null

    private val level1 = listOf(
            listOf("1", "1", "1", "2", "1"),
            listOf("0", "0", "0", "2", "0"),
            listOf("0", "0", "0", "2", "0"),
            listOf("0", "0", "0", "1", "1"))

    private val level2 = listOf(
            listOf("1", "1", "0", "0", "0", "0", "0"),
            listOf("0", "1", "1", "0", "0", "0", "0"),
            listOf("0", "0", "1", "1", "0", "0", "0"),
            listOf("0", "0", "0", "1", "1", "0", "0"),
            listOf("0", "0", "0", "0", "1", "1", "0"),
            listOf("0", "0", "0", "0", "0", "1", "2"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        actionView.setupViews(supportFragmentManager)

        btStart.setOnClickListener {
            gameView.resetGame()
            presenter.onStartClick(actionView.getActions())
        }
        btnMenu.setOnClickListener { presenter.onMenuClick() }

        gameView.setOnMessageCallback(this)
        gameView.setLevel(level2)
    }

    override fun doActionsInGame(actions: List<Command>) {
        gameView.doActions(actions)
    }

    override fun onResume() {
        super.onResume()
        gameView.invalidate()
    }

    override fun openMenuActivity() {
        val intent = MenuActivity.newIntent(this)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    override fun showGameMessage(messageId: Int) {
        showMessageDialog(messageId)
    }

    override fun showMessageDialog(@StringRes messageId: Int) {
        messageDialog = MessageDialog.show(supportFragmentManager, messageId)
    }

    companion object {
        const val ACTION_UP = "action_up"
        const val ACTION_DOWN = "action_down"
        const val ACTION_RIGHT = "action_right"
        const val ACTION_LEFT = "action_left"

        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }

    }

}

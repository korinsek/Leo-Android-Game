package com.mag.denis.game.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.StringRes
import android.view.View
import com.mag.denis.game.R
import com.mag.denis.game.manager.GameManager
import com.mag.denis.game.manager.LevelManager
import com.mag.denis.game.ui.main.actionblockview.ActionBlockView
import com.mag.denis.game.ui.main.actionflowview.FlowView1
import com.mag.denis.game.ui.main.actionpseudoview.PseudoKotlinView
import com.mag.denis.game.ui.main.actionpseudoview.PseudoPythonView
import com.mag.denis.game.ui.main.actionpseudoview.dialog.HelpPythonDialog
import com.mag.denis.game.ui.main.dialog.MessageDialog
import com.mag.denis.game.ui.main.model.Command
import com.mag.denis.game.ui.main.view.GameView
import com.mag.denis.game.ui.menu.MenuActivity
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity(), MainView, GameView.OnMessageCallback {

    @Inject lateinit var presenter: MainPresenter
    @Inject lateinit var levelManager: LevelManager
    @Inject lateinit var gameManager: GameManager

    private var messageDialog: MessageDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val stage = gameManager.getCurrentStage()

        val actionView = when (stage) {
            GameManager.STAGE_BLOCK -> ActionBlockView(this)
            GameManager.STAGE_FLOW -> {
                when (gameManager.getCurrentLevel()) {
                    1 -> FlowView1(this)
                    2 -> FlowView1(this)
                    else -> throw IllegalStateException("Invalid level flowview select")
                }
            }
            GameManager.STAGE_PSEUDO -> {
                when (gameManager.getLanguage()) {
                    GameManager.LANGUAGE_KOTLIN -> PseudoKotlinView(this)
                    GameManager.LANGUAGE_PYTHON -> PseudoPythonView(this)
                    else -> throw IllegalStateException("Invalid programing langugage in manager")
                }
            }
            else -> throw IllegalStateException("Invalid stage")
        }

        actionViewPlaceholder.addView(actionView)
//        actionView.setupViews(supportFragmentManager) TODO SETUP VIEWS, GENERAL OBJECT

        btStart.setOnClickListener {
            gameView.resetGame()
//            presenter.onStartClick(actionView.getActions())//TODO ON START CLICK GET ACTIONS
        }
        btnMenu.setOnClickListener { presenter.onMenuClick() }

        gameView.setOnMessageCallback(this)
        gameView.setLevel(levelManager.getCurrentLevel())

        btnHelp.visibility = View.VISIBLE
        btnHelp.setOnClickListener { HelpPythonDialog.show(supportFragmentManager) }//Todo show only when is pseudo mode
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

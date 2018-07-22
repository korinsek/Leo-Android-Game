package com.mag.denis.game.ui.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.StringRes
import android.view.View
import com.mag.denis.game.R
import com.mag.denis.game.manager.GameManager
import com.mag.denis.game.manager.LevelManager
import com.mag.denis.game.ui.main.actions.actionblockview.ActionBlockView
import com.mag.denis.game.ui.main.actions.actionflowview.FlowView1
import com.mag.denis.game.ui.main.actions.actionflowview.FlowView2
import com.mag.denis.game.ui.main.actions.actionflowview.FlowView3
import com.mag.denis.game.ui.main.actions.actionflowview.FlowView4
import com.mag.denis.game.ui.main.actions.actionpseudoview.PseudoKotlinView
import com.mag.denis.game.ui.main.actions.actionpseudoview.PseudoPythonView
import com.mag.denis.game.ui.main.actions.actionpseudoview.dialog.HelpPythonDialog
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

        setupActionView()

        btnMenu.setOnClickListener { presenter.onMenuClick() }

        gameView.setOnMessageCallback(this)
        gameView.setLevel(levelManager.getCurrentLevel())
    }


    private fun setupActionView() {
        val stage = gameManager.getCurrentStage()
        val avilableCommands = levelManager.getAvilableCommandsForLevel(gameManager.getCurrentLevel())
        when (stage) {
            GameManager.STAGE_BLOCK -> {
                val actionView = ActionBlockView(this)
                actionViewPlaceholder.addView(actionView)
                actionView.setupViews(supportFragmentManager, avilableCommands)
                btStart.setOnClickListener {
                    gameView.resetGame()
                    presenter.onStartClick(actionView.getActions())
                }
                btnHelp.visibility = View.GONE
            }
            GameManager.STAGE_FLOW -> {
                val actionView = when (gameManager.getCurrentLevel()) {
                    1 -> FlowView1(this)
                    2 -> FlowView2(this)
                    3 -> FlowView3(this)
                    4 -> FlowView4(this)
                    else -> throw IllegalStateException("Invalid level flowview select")
                }
                actionViewPlaceholder.addView(actionView)
                actionView.setupViews(supportFragmentManager, avilableCommands)
                btStart.setOnClickListener {
                    gameView.resetGame()
                    presenter.onStartClick(actionView.getActions())
                }
                btnHelp.visibility = View.GONE
            }
            GameManager.STAGE_PSEUDO -> {
                val actionView = when (gameManager.getLanguage()) {
                    GameManager.LANGUAGE_KOTLIN -> PseudoKotlinView(this)
                    GameManager.LANGUAGE_PYTHON -> PseudoPythonView(this)
                    else -> throw IllegalStateException("Invalid programing langugage in manager")
                }
                actionViewPlaceholder.addView(actionView)
                actionView.setupViews(supportFragmentManager, avilableCommands)
                btStart.setOnClickListener {
                    gameView.resetGame()
                    presenter.onStartClick(actionView.getActions())
                }
                btnHelp.visibility = View.VISIBLE
                btnHelp.setOnClickListener { HelpPythonDialog.show(supportFragmentManager) }//Todo show only when is pseudo mode
            }
            else -> throw IllegalStateException("Invalid stage")
        }
    }

    override fun doActionsInGame(actions: List<Command>) {
        gameView.doActions(actions)
    }

    override fun onLevelFinished(starsAchieved: Int) {
        levelManager.setStarsForLevel(gameManager.getCurrentLevel(), starsAchieved)
        presenter.onLevelFinished()
    }

    override fun closeOk() {
        setResult(Activity.RESULT_OK)
        finish()
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

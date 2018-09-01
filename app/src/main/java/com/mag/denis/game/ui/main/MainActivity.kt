package com.mag.denis.game.ui.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.ActivityCompat
import android.view.View
import com.mag.denis.game.R
import com.mag.denis.game.manager.GameManager
import com.mag.denis.game.manager.LevelManager
import com.mag.denis.game.service.MusicService
import com.mag.denis.game.ui.BaseActivity
import com.mag.denis.game.ui.main.actions.actionblockview.ActionBlockView
import com.mag.denis.game.ui.main.actions.actionflowview.FlowView1
import com.mag.denis.game.ui.main.actions.actionflowview.FlowView2
import com.mag.denis.game.ui.main.actions.actionflowview.FlowView3
import com.mag.denis.game.ui.main.actions.actionflowview.FlowView4
import com.mag.denis.game.ui.main.actions.actionpseudoview.PseudoKotlinView
import com.mag.denis.game.ui.main.actions.actionpseudoview.PseudoPythonView
import com.mag.denis.game.ui.main.actions.actionpseudoview.dialog.HelpKotlinDialog
import com.mag.denis.game.ui.main.actions.actionpseudoview.dialog.HelpPythonDialog
import com.mag.denis.game.ui.main.actions.intro.IntroView
import com.mag.denis.game.ui.main.dialog.ErrorDialog
import com.mag.denis.game.ui.main.dialog.MessageDialog
import com.mag.denis.game.ui.main.model.Command
import com.mag.denis.game.ui.main.view.GameView
import com.mag.denis.game.ui.menu.MenuActivity
import com.mag.denis.game.ui.score.ScoreActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.partial_action_animation.*
import javax.inject.Inject


class MainActivity : BaseActivity(), MainView, GameView.OnMessageCallback, IntroView.AnimationIntroCallback {

    @Inject lateinit var presenter: MainPresenter
    @Inject lateinit var levelManager: LevelManager
    @Inject lateinit var gameManager: GameManager

    private var messageDialog: MessageDialog? = null
    private var errorDialog: ErrorDialog? = null

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
        val avilableCommands = levelManager.getAvailableCommands()
        when (stage) {
            GameManager.STAGE_BLOCK -> {
                val actionView = ActionBlockView(this)
                actionViewPlaceholder.addView(actionView)
                actionView.setupViews(supportFragmentManager, avilableCommands)
                btStart.setOnClickListener {
                    gameView.resetGame()
                    try {
                        presenter.onStartClick(actionView.getActions())
                    } catch (e: IllegalStateException) {
                        showErrorDialog(e.message!!)
                    }
                }
                btnHelp.visibility = View.GONE

                when (gameManager.getCurrentLevel()) {
                    1 -> animationIntroView.startAnimationAction()
                    3 -> animationIntroView.startAnimationLoop()
                    5 -> animationIntroView.startAnimationLoopAndIf(supportFragmentManager)
                    else -> animationIntroView.hide()
                }
                animationIntroView.setAnimationIntroCallback(this)
                listOf(animationIntroView, delayedTextView).forEach {
                    it.setOnClickListener {
                        gameView.resetGame()
                        animationIntroView.hide()
                    }
                }
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
                    try {
                        presenter.onStartClick(actionView.getActions())
                    } catch (e: IllegalStateException) {
                        showErrorDialog(e.message!!)
                    }
                }
                btnHelp.visibility = View.GONE

                if (gameManager.getCurrentLevel() == 1) {
                    animationIntroView.startAnimationFlowAction()
                    animationIntroView.setAnimationIntroCallback(this)
                    listOf(animationIntroView, delayedTextView).forEach {
                        it.setOnClickListener {
                            gameView.resetGame()
                            animationIntroView.hide()
                        }
                    }
                }
            }
            GameManager.STAGE_PSEUDO -> {
                val actionView = when (gameManager.getLanguage()) {
                    GameManager.LANGUAGE_KOTLIN -> PseudoKotlinView(this)
                    GameManager.LANGUAGE_PYTHON -> PseudoPythonView(this)
                    else -> throw IllegalStateException("Invalid programing langugage in manager")
                }
                actionViewPlaceholder.addView(actionView)
                actionView.setupViews()
                btStart.setOnClickListener {
                    gameView.resetGame()
                    try {
                        presenter.onStartClick(actionView.getActions())
                    } catch (e: IllegalStateException) {
                        showErrorDialog(e.message!!)
                    }
                }
                btnHelp.visibility = View.VISIBLE
                btnHelp.setOnClickListener {
                    when (gameManager.getLanguage()) {
                        GameManager.LANGUAGE_KOTLIN -> HelpKotlinDialog.show(supportFragmentManager)
                        GameManager.LANGUAGE_PYTHON -> HelpPythonDialog.show(supportFragmentManager)
                        else -> throw IllegalStateException("Invalid programing langugage in manager")
                    }
                }

                when (gameManager.getCurrentLevel()) {
                    1 -> animationIntroView.startAnimationPseudo1Action(actionView.reservedWordsPattern) //TODO Get reserved for correct lang
                    2 -> animationIntroView.startAnimationPseudo2Action(actionView.reservedWordsPattern)
                    3 -> animationIntroView.startAnimationPseudo3Action(actionView.reservedWordsPattern, actionView.getIntroCodeLoop())
                    5 -> animationIntroView.startAnimationPseudo4Action(actionView.reservedWordsPattern, actionView.getIntroCodeLoopIf())
                    else -> animationIntroView.hide()
                }

                animationIntroView.setAnimationIntroCallback(this)
                listOf(animationIntroView, delayedTextView).forEach {
                    it.setOnClickListener {
                        gameView.resetGame()
                        animationIntroView.hide()
                    }
                }

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
        ActivityCompat.startForegroundService(this, MusicService.newIntent(this))
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
        messageDialog?.dismiss()
        messageDialog = MessageDialog.show(supportFragmentManager, messageId)
    }

    override fun showErrorDialog(message: String) {
        errorDialog?.dismiss()
        errorDialog = ErrorDialog.show(supportFragmentManager, message)
    }

    override fun onPause() {
        stopService(MusicService.newIntent(this))
        super.onPause()
    }

    override fun onDestroy() {
        messageDialog?.dismiss()
        errorDialog?.dismiss()
        super.onDestroy()
    }

    override fun openScoreScreen() {
        val intent = MenuActivity.newIntent(this)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivities(arrayOf(intent, ScoreActivity.newIntent(this)))
        overridePendingTransition(0, 0)
    }

    override fun onStartAnimationClick(commands: ArrayList<Command>) {
        presenter.onStartClick(commands)
    }

    override fun animationWantResetGame() {
        gameView.resetGame()
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

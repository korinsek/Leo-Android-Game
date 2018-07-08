package com.mag.denis.game.ui.main.actionpseudoview

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v4.app.FragmentManager
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View
import com.mag.denis.game.R
import com.mag.denis.game.ui.main.model.Command
import kotlinx.android.synthetic.main.partial_pseudo_view.view.*


abstract class AbsPseudoView(context: Context, attributes: AttributeSet) : ConstraintLayout(context, attributes), TextWatcher, View.OnKeyListener {
    internal open val reservedLoopDefenition = "for"
    internal val reservedConditionIf = "if"
    internal val reservedConditionElse = "else"
    internal val reservedIn = "in"
    internal val reservedRange = "range"
    internal val moveUp = "moveUp()"
    internal val moveDown = "moveDown()"
    internal val moveRight = "moveRight()"
    internal val moveLeft = "moveLeft()"
    internal val conditionGreenLeaf = "isGreenLeaf()"
    internal val conditionBrownLeaf = "isBrownLeaf()"
    internal val space = "    "

    internal var backspacePressed = false

    init {
        inflate(context, R.layout.partial_pseudo_view, this)
    }

    fun setupViews(fragmentManager: FragmentManager) {
        super.onAttachedToWindow()
        etCode.addTextChangedListener(this)
        etCode.setOnKeyListener(this)
    }

    override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                backspacePressed = true
            }
        }
        return false
    }

    abstract fun getActions(): ArrayList<Command>

    internal fun getCondition(child: String): String {
        val conditionStartIndex = child.indexOfFirst { it == '(' } + 1
        val conditionEndIndex = child.indexOfLast { it == ')' }
        if (conditionStartIndex == -1 || conditionEndIndex == -1 || (conditionStartIndex > conditionEndIndex)) {
            //TODO handle error
            throw IllegalStateException("Problem with condition")
        }
        return child.substring(conditionStartIndex, conditionEndIndex).trim()
    }
}

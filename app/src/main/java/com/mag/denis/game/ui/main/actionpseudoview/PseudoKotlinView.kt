package com.mag.denis.game.ui.main.actionpseudoview

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.Spannable
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View
import android.widget.TextView
import com.mag.denis.game.R
import com.mag.denis.game.ui.main.MainActivity.Companion.ACTION_DOWN
import com.mag.denis.game.ui.main.MainActivity.Companion.ACTION_LEFT
import com.mag.denis.game.ui.main.MainActivity.Companion.ACTION_RIGHT
import com.mag.denis.game.ui.main.MainActivity.Companion.ACTION_UP
import com.mag.denis.game.ui.main.model.*
import com.mag.denis.game.ui.main.objects.FloorSet.Companion.TYPE_LEAF_BROWN
import com.mag.denis.game.ui.main.objects.FloorSet.Companion.TYPE_LEAF_GREEN
import kotlinx.android.synthetic.main.partial_pseudo_view.view.*
import java.util.regex.Pattern


class PseudoKotlinView(context: Context, attributes: AttributeSet) : ConstraintLayout(context, attributes), TextWatcher, View.OnKeyListener {
    private val reservedLoopDefenition = "repeat"
    private val reservedConditionIf = "if"
    private val reservedConditionElse = "else"
    private val moveUp = "moveUp()"
    private val moveDown = "moveDown()"
    private val moveRight = "moveRight()"
    private val moveLeft = "moveLeft()"
    private val conditionGreenLeaf = "isGreenLeaf()"
    private val conditionBrownLeaf = "isBrownLeaf()"
    private val space = "    "


    init {
        inflate(context, R.layout.partial_pseudo_view, this)
    }

    fun setupViews(fragmentManager: FragmentManager) {
        super.onAttachedToWindow()
        etCode.addTextChangedListener(this)
        etCode.setOnKeyListener(this)
    }

    private var maxLastIndex = -1
    private var backspacePressed = false

    override fun afterTextChanged(s: Editable) {
        //TODO can be optimized now on each change go through all text
        etCode.removeTextChangedListener(this)

        val pattern = Pattern.compile("$reservedLoopDefenition|$reservedConditionIf|$reservedConditionElse")
        val matcher = pattern.matcher(s)
        while (matcher.find()) {
            s.setSpan(ForegroundColorSpan(ContextCompat.getColor(context, R.color.loop_text_color)), matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        }


        maxLastIndex = etCode.selectionStart

        val line = s.split("\n").last { it != "}" }

        val command = if (line.contains(reservedLoopDefenition)) {
            reservedLoopDefenition
        } else if (line.contains(reservedConditionIf)) {
            reservedConditionIf
        } else if (line.contains(reservedConditionElse)) {
            reservedConditionElse
        } else {
            null
        }

        if (command != null && !backspacePressed) {
            if (line.endsWith('{')) {
                val brackedIndex = s.lastIndexOf('{') + 1
                val spaceBefore = line.substringBefore(command).filter { it == ' ' }
                s.insert(brackedIndex, "\n$space$spaceBefore\n$spaceBefore}")
                maxLastIndex = brackedIndex + space.length + spaceBefore.length + 1
            }
        }
        backspacePressed = false

        etCode.setText(s, TextView.BufferType.SPANNABLE)
        etCode.setSelection(maxLastIndex)
        etCode.addTextChangedListener(this)
    }

    override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                backspacePressed = true
            }
        }
        return false
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        //Nothing to do
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        //Nothing to do
    }

    fun getActions(): ArrayList<Command> {
        val code = etCode.text
        val numOfOpenBrackets = code.count { it == '{' }
        val numOfCloseBrackets = code.count { it == '}' }
        if (numOfOpenBrackets != numOfCloseBrackets) {
            //TODO show error
            throw IllegalStateException("Brackets missing")
        }
        val codeLines = code.split("\n") as ArrayList

        val commands = getCommands(codeLines)



        return commands
    }

    private fun getCommands(codeLines: ArrayList<String>): ArrayList<Command> {

        val list = ArrayList<Command>()

        while (codeLines.isNotEmpty()) {
            val child = codeLines.firstOrNull()

            if (child != null) {
                if (child.isEmpty()) {
                    codeLines.removeAt(0)
                    continue
                }
                if (child.contains("move")) {
                    val action = when (child) {
                        moveUp -> Action(ACTION_UP)
                        moveDown -> Action(ACTION_DOWN)
                        moveRight -> Action(ACTION_RIGHT)
                        moveLeft -> Action(ACTION_LEFT)
                        else -> {
                            throw if (child.contains("(") && child.contains(")")) {
                                IllegalStateException("Mising brackets () in command: $child")//TODO handle error
                            } else {
                                IllegalStateException("Command dont exists: $child")//TODO handle error
                            }

                        }
                    }
                    list.add(action)
                    codeLines.removeAt(0)
                } else if (child.contains(reservedLoopDefenition)) {
                    var bracketsOpened = 0
                    val whileCodeLines = ArrayList<String>()

                    val conditionRepeat = getCondition(child).toIntOrNull() ?: 0

                    codeLines.removeAt(0)
                    while (codeLines.isNotEmpty()) {
                        val line = codeLines.firstOrNull()
                        if (line != null) {
                            if (line.contains(reservedLoopDefenition) || line.contains(reservedConditionIf) || line.contains(reservedConditionElse)) {
                                bracketsOpened++
                            }
                            codeLines.removeAt(0)
                            if (line.contains("}")) {
                                if (bracketsOpened == 0) {
                                    break
                                } else {
                                    bracketsOpened--
                                }
                            }
                            whileCodeLines.add(line)
                        }
                    }
                    //TODO VALUE
                    list.add(Loop(conditionRepeat, getCommands(whileCodeLines)))
                } else if (child.contains(reservedConditionIf)) {
                    var bracketsOpened = 0
                    val trueCodeLines = ArrayList<String>()


                    val conditionColor = when (getCondition(child)) {
                        conditionGreenLeaf -> TYPE_LEAF_GREEN
                        conditionBrownLeaf -> TYPE_LEAF_BROWN
                        else -> {
                            throw if (child.contains("(") && child.contains(")")) {
                                IllegalStateException("Mising brackets () in condition.")//TODO handle error
                            } else {
                                IllegalStateException("Command dont exists: $child")//TODO handle error
                            }
                        }
                    }

                    codeLines.removeAt(0)
                    while (codeLines.isNotEmpty()) {
                        val line = codeLines.firstOrNull()
                        if (line != null) {
                            if (line.contains(reservedLoopDefenition) || line.contains(reservedConditionIf) || line.contains(reservedConditionElse)) {
                                bracketsOpened++
                            }
                            codeLines.removeAt(0)
                            if (line.contains("}")) {
                                if (bracketsOpened == 0 || line.contains(reservedConditionElse)) {
                                    if (line.contains(reservedConditionElse)) {
                                        codeLines.add(0, line)
                                    }
                                    break
                                } else {
                                    bracketsOpened--
                                }
                            }
                            trueCodeLines.add(line)

                        }
                    }

                    val falseCodeLines = ArrayList<String>()
                    val elseLine = codeLines.firstOrNull()
                    if (elseLine != null && elseLine.contains(reservedConditionElse)) {
                        bracketsOpened = 0
                        codeLines.removeAt(0)
                        while (codeLines.isNotEmpty()) {
                            val line = codeLines.firstOrNull()
                            if (line != null) {
                                if (line.contains(reservedLoopDefenition) || line.contains(reservedConditionIf) || line.contains(reservedConditionElse)) {
                                    bracketsOpened++
                                }
                                codeLines.removeAt(0)
                                if (line.contains("}")) {
                                    if (bracketsOpened == 0) {
                                        break
                                    } else {
                                        bracketsOpened--
                                    }
                                }
                                falseCodeLines.add(line)
                            }
                        }
                    }
                    //TODO condition
                    list.add(IfCondition(ColorCondition(conditionColor, Condition.TYPE_TRUE), getCommands(trueCodeLines), getCommands(falseCodeLines)))
                } else {
                    throw IllegalStateException("Cannot recognize command: $child")
                }
            } else {
                //TODO show error
                break
            }
        }
        return list
    }

    private fun getCondition(child: String): String {
        val conditionStartIndex = child.indexOfFirst { it == '(' } + 1
        val conditionEndIndex = child.indexOfLast { it == ')' }
        if (conditionStartIndex == -1 || conditionEndIndex == -1 || (conditionStartIndex > conditionEndIndex)) {
            //TODO handle error
            throw IllegalStateException("Problem with condition")
        }
        return child.substring(conditionStartIndex, conditionEndIndex)
    }
}

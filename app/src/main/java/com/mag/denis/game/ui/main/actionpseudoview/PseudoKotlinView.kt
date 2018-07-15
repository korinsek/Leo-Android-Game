package com.mag.denis.game.ui.main.actionpseudoview

import android.content.Context
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
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


class PseudoKotlinView : AbsPseudoView {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}


    override fun getActions(): ArrayList<Command> {
        val code = etCode.text
        val numOfOpenBrackets = code.count { it == '{' }
        val numOfCloseBrackets = code.count { it == '}' }
        if (numOfOpenBrackets != numOfCloseBrackets) {
            //TODO show error
            throw IllegalStateException("Brackets missing")
        }
        val commands = if (code.isNotEmpty()) {
            //TODO execute this in background and subscribe on obervable
            val codeLines = code.split("\n") as ArrayList
            getCommands(codeLines)
        } else {
            arrayListOf()
        }
        return commands
    }

    override fun colorAndAddSpacing(s: Editable): Editable {
        val pattern = Pattern.compile("$RESERVED_LOOP|$RESERVED_CONDITION_IF|$RESERVED_CONDITION_ELSE")
        val matcher = pattern.matcher(s)
        while (matcher.find()) {
            s.setSpan(ForegroundColorSpan(ContextCompat.getColor(context, R.color.loop_text_color)), matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        maxLastIndex = etCode.selectionStart

        val line = s.split("\n").last { it != "}" }

        val command = if (line.contains(RESERVED_LOOP)) {
            RESERVED_LOOP
        } else if (line.contains(RESERVED_CONDITION_IF)) {
            RESERVED_CONDITION_IF
        } else if (line.contains(RESERVED_CONDITION_ELSE)) {
            RESERVED_CONDITION_ELSE
        } else {
            null
        }

        if (command != null && !backspacePressed) {
            if (line.endsWith('{')) {
                val brackedIndex = s.lastIndexOf('{') + 1
                val spaceBefore = line.substringBefore(command).filter { it == ' ' }
                s.insert(brackedIndex, "\n$SPACE$spaceBefore\n$spaceBefore}")
                maxLastIndex = brackedIndex + SPACE.length + spaceBefore.length + 1
            }
        }
        backspacePressed = false
        return s
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
                    val action = when (child.trim()) {
                        MOVE_UP -> Action(ACTION_UP)
                        MOVE_DOWN -> Action(ACTION_DOWN)
                        MOVE_RIGHT -> Action(ACTION_RIGHT)
                        MOVE_LEFT -> Action(ACTION_LEFT)
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
                } else if (child.contains(RESERVED_LOOP)) {
                    var bracketsOpened = 0
                    val whileCodeLines = ArrayList<String>()

                    val conditionRepeat = getCondition(child).toIntOrNull() ?: 0

                    codeLines.removeAt(0)
                    while (codeLines.isNotEmpty()) {
                        val line = codeLines.firstOrNull()
                        if (line != null) {
                            if (line.contains(RESERVED_LOOP) || line.contains(RESERVED_CONDITION_IF) || line.contains(RESERVED_CONDITION_ELSE)) {
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
                } else if (child.contains(RESERVED_CONDITION_IF)) {
                    var bracketsOpened = 0
                    val trueCodeLines = ArrayList<String>()


                    val conditionColor = when (getCondition(child)) {
                        CONDITION_GREEN_LEAF -> TYPE_LEAF_GREEN
                        CONDITION_BROWN_LEAF -> TYPE_LEAF_BROWN
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
                            if (line.contains(RESERVED_LOOP) || line.contains(RESERVED_CONDITION_IF) || line.contains(RESERVED_CONDITION_ELSE)) {
                                bracketsOpened++
                            }
                            codeLines.removeAt(0)
                            if (line.contains("}")) {
                                if (bracketsOpened == 0 || line.contains(RESERVED_CONDITION_ELSE)) {
                                    if (line.contains(RESERVED_CONDITION_ELSE)) {
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
                    if (elseLine != null && elseLine.contains(RESERVED_CONDITION_ELSE)) {
                        bracketsOpened = 0
                        codeLines.removeAt(0)
                        while (codeLines.isNotEmpty()) {
                            val line = codeLines.firstOrNull()
                            if (line != null) {
                                if (line.contains(RESERVED_LOOP) || line.contains(RESERVED_CONDITION_IF) || line.contains(RESERVED_CONDITION_ELSE)) {
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

    companion object {
        const val RESERVED_LOOP = "repeat"
    }
}

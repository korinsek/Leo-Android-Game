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


class PseudoPythonView(context: Context, attributes: AttributeSet) : AbsPseudoView(context, attributes) {

    override fun colorAndAddSpacing(s:Editable):Editable{
        val pattern = Pattern.compile("$RESERVED_LOOP|$RESERVED_CONDITION_IF|$RESERVED_CONDITION_ELSE|$RESERVED_IN|$RESERVED_RANGE")
        val matcher = pattern.matcher(s)
        while (matcher.find()) {
            s.setSpan(ForegroundColorSpan(ContextCompat.getColor(context, R.color.loop_text_color)), matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        maxLastIndex = etCode.selectionStart

        val line = s.split("\n").last { it != "\n" }

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
            if (line.endsWith(':')) {
                val brackedIndex = s.lastIndexOf(':') + 1
                val spaceBefore = line.substringBefore(command).filter { it == ' ' }
                s.insert(brackedIndex, "\n$SPACE$spaceBefore\n$spaceBefore")
                maxLastIndex = brackedIndex + SPACE.length + spaceBefore.length + 1
            }
        }
        backspacePressed = false
        return s
    }

    override fun getActions(): ArrayList<Command> {
        val codeLines = etCode.text.split("\n") as ArrayList

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
                    val whileCodeLines = ArrayList<String>()

                    val conditionRepeat = getCondition(child).toIntOrNull() ?: 0
                    val loopSpaceRange = child.substringBefore(RESERVED_LOOP).filter { it == ' ' } + SPACE

                    codeLines.removeAt(0)
                    while (codeLines.isNotEmpty()) {
                        val line = codeLines.firstOrNull()
                        if (line != null) {
                            if (line.substring(0, loopSpaceRange.length) != loopSpaceRange) {
                                break
                            }
                            codeLines.removeAt(0)
                            whileCodeLines.add(line)
                        }
                    }
                    //TODO VALUE
                    list.add(Loop(conditionRepeat, getCommands(whileCodeLines)))
                } else if (child.contains(RESERVED_CONDITION_IF)) {
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
                            codeLines.removeAt(0)
                            //TODO handle this

                            if (line.contains(RESERVED_CONDITION_ELSE)) {
                                if (line.contains(RESERVED_CONDITION_ELSE)) {
                                    codeLines.add(0, line)
                                }
                                break
                            }
                            trueCodeLines.add(line)

                        }
                    }

                    val falseCodeLines = ArrayList<String>()
                    val elseLine = codeLines.firstOrNull()
                    if (elseLine != null && elseLine.contains(RESERVED_CONDITION_ELSE)) {
                        codeLines.removeAt(0)
                        val loopSpaceRange = elseLine.substringBefore(RESERVED_LOOP).filter { it == ' ' } + SPACE
                        while (codeLines.isNotEmpty()) {
                            val line = codeLines.firstOrNull()
                            if (line != null) {
                                if (line.substring(0, loopSpaceRange.length) != loopSpaceRange) {
                                    break
                                }
                                codeLines.removeAt(0)
                                falseCodeLines.add(line)
                            }
                        }
                    }
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
}

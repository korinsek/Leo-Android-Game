package com.mag.denis.game.ui.main.view.pseudo.interactionview

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
import com.mag.denis.game.ui.main.exception.GameParserException
import com.mag.denis.game.ui.main.model.*
import com.mag.denis.game.ui.main.objects.FloorSet.Companion.TYPE_LEAF_BROWN
import com.mag.denis.game.ui.main.objects.FloorSet.Companion.TYPE_LEAF_GREEN
import kotlinx.android.synthetic.main.partial_pseudo_view.view.*
import java.util.regex.Pattern


class PseudoKotlinView : AbsPseudoView {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    override val reservedWordsPattern = "$RESERVED_LOOP|$RESERVED_CONDITION_IF|$RESERVED_CONDITION_ELSE"

    override fun getActions(): ArrayList<Command> {
        val code = "${etCode.text}\n"
        val numOfOpenBrackets = code.count { it == '{' }
        val numOfCloseBrackets = code.count { it == '}' }
        if (numOfOpenBrackets != numOfCloseBrackets) {
            throw GameParserException(context.getString(R.string.user_error_missing_brackets))
        }
        return if (code.isNotEmpty()) {
            //TODO execute this in background
            val codeLines = code.split("\n") as ArrayList
            getCommands(codeLines)
        } else {
            arrayListOf()
        }
    }

    override fun colorAndAddSpacing(s: Editable): Editable {
        val pattern = Pattern.compile(reservedWordsPattern)
        val matcher = pattern.matcher(s)
        while (matcher.find()) {
            s.setSpan(ForegroundColorSpan(ContextCompat.getColor(context, R.color.loop_text_color)),
                    matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
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
        if (codeLines.firstOrNull()?.trim().isNullOrEmpty()) {
            throw GameParserException(context.getString(R.string.user_error_command_field_empty))
        }

        val list = ArrayList<Command>()

        while (codeLines.isNotEmpty()) {
            val command = codeLines.firstOrNull()

            if (command != null) {
                if (command.isEmpty()) {
                    codeLines.removeAt(0)
                    continue
                }
                if (command.contains(MOVE_PREFIX)) {
                    val action = when (command.trim()) {
                        MOVE_UP -> Action(ACTION_UP)
                        MOVE_DOWN -> Action(ACTION_DOWN)
                        MOVE_RIGHT -> Action(ACTION_RIGHT)
                        MOVE_LEFT -> Action(ACTION_LEFT)
                        else -> {
                            throw if (!command.contains("(") && !command.contains(")")) {
                                GameParserException(context.getString(R.string.user_error_missing_brackets_command, command))
                            } else {
                                GameParserException(context.getString(R.string.user_error_command_dont_exists, command))
                            }

                        }
                    }
                    list.add(action)
                    codeLines.removeAt(0)
                } else if (command.contains(RESERVED_LOOP)) {
                    var bracketsOpened = 0
                    val whileCodeLines = ArrayList<String>()

                    val conditionRepeat = getCondition(command).toIntOrNull() ?: 0

                    codeLines.removeAt(0)
                    while (codeLines.isNotEmpty()) {
                        val line = codeLines.firstOrNull()
                        if (line != null) {
                            if (line.contains(RESERVED_LOOP) || line.contains(RESERVED_CONDITION_IF)
                                    || line.contains(RESERVED_CONDITION_ELSE)) {
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
                    list.add(Loop(conditionRepeat, getCommands(whileCodeLines)))
                } else if (command.contains(RESERVED_CONDITION_IF)) {
                    var bracketsOpened = 0
                    val trueCodeLines = ArrayList<String>()


                    val conditionColor = when (getCondition(command)) {
                        CONDITION_GREEN_LEAF -> TYPE_LEAF_GREEN
                        CONDITION_BROWN_LEAF -> TYPE_LEAF_BROWN
                        else -> {
                            throw if (command.contains("(") && command.contains(")")) {
                                GameParserException(context.getString(R.string.user_error_missing_brackets_condition))
                            } else {
                                GameParserException(context.getString(R.string.user_error_command_dont_exists, command))
                            }
                        }
                    }

                    codeLines.removeAt(0)
                    while (codeLines.isNotEmpty()) {
                        val line = codeLines.firstOrNull()
                        if (line != null) {
                            if (line.contains(RESERVED_LOOP) || line.contains(RESERVED_CONDITION_IF)
                                    || line.contains(RESERVED_CONDITION_ELSE)) {
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
                                if (line.contains(RESERVED_LOOP) || line.contains(RESERVED_CONDITION_IF)
                                        || line.contains(RESERVED_CONDITION_ELSE)) {
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
                    list.add(IfCondition(ColorCondition(conditionColor, Condition.TYPE_TRUE), getCommands(trueCodeLines), getCommands(falseCodeLines)))
                } else {
                    throw GameParserException(context.getString(R.string.user_error_cannot_recognize_command, command))
                }
            } else {
                break
            }
        }
        return list
    }

    override fun getIntroCodeLoop(): String {
        return "$RESERVED_LOOP (4){\n    moveRight()\n}"
    }

    override fun getIntroCodeLoopIf(): String {
        return "$RESERVED_LOOP (6){\n   $RESERVED_CONDITION_IF ($CONDITION_GREEN_LEAF)" +
                "{\n        $MOVE_RIGHT\n    } else { \n        $MOVE_DOWN\n}\n"
    }

    companion object {
        const val RESERVED_LOOP = "repeat"
    }
}

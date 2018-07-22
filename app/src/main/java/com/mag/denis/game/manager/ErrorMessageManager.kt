package com.mag.denis.game.manager

import android.support.annotation.StringRes
import com.mag.denis.game.R
import javax.inject.Singleton


@Singleton
class ErrorMessageManager() {

    @StringRes fun parseErrorMessage(e: Throwable): Int {
        return when (e) {
            is IllegalStateException -> {
                when (e.message) {
                    ERROR_PROBLEM_CONDITION -> R.string.error_parser_problem_condition
                    ERROR_MISSING_BRACKETS -> R.string.error_missing_brackets
                    else -> R.string.error_general
                }
            }
            else -> {
                R.string.error_general
            }
        }
    }

    companion object {
        const val ERROR_PROBLEM_CONDITION = "error_problem_condition"
        const val ERROR_GENERAL = "error_general"
        const val ERROR_MISSING_BRACKETS = "error_missing_brackets"
    }

}

package com.mag.denis.game.ui.main.view.pseudo.dialog

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mag.denis.game.R
import com.mag.denis.game.ui.main.view.pseudo.interactionview.AbsPseudoView
import com.mag.denis.game.ui.main.view.pseudo.interactionview.PseudoKotlinView
import com.mag.denis.game.ui.main.dialog.BaseDialogFragment
import com.mag.denis.game.ui.main.dialog.DialogUtils
import kotlinx.android.synthetic.main.dialog_help.*
import java.util.regex.Pattern

class HelpKotlinDialog : BaseDialogFragment() {

    override var widthRatio = 0.8
    override var heightRatio = 0.8

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.dialog_help, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvActions.text = getColoredCode(SpannableString("${getString(R.string.help_actions_title)}\n\n${AbsPseudoView.MOVE_UP}\n${AbsPseudoView.MOVE_DOWN}\n${AbsPseudoView.MOVE_RIGHT}\n${AbsPseudoView.MOVE_LEFT}"))
        tvContition.text = getColoredCode(SpannableString("${AbsPseudoView.RESERVED_CONDITION_IF} (${AbsPseudoView.CONDITION_GREEN_LEAF}){\n    ...\n}\n\n${getString(R.string.help_conditions)}\n${AbsPseudoView.CONDITION_GREEN_LEAF}\n${AbsPseudoView.CONDITION_GREEN_LEAF}"))
        tvLoop.text = getColoredCode(SpannableString("${PseudoKotlinView.RESERVED_LOOP} (${1}){\n    ...\n}"))
    }

    private fun getColoredCode(s: SpannableString): SpannableString {
        val pattern = Pattern.compile("${PseudoKotlinView.RESERVED_LOOP}|${AbsPseudoView.RESERVED_CONDITION_IF}|${AbsPseudoView.RESERVED_CONDITION_ELSE}")
        val matcher = pattern.matcher(s)
        while (matcher.find()) {
            s.setSpan(ForegroundColorSpan(ContextCompat.getColor(context!!, R.color.loop_text_color)), matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        val pattern2 = Pattern.compile("${getString(R.string.help_actions_title)}|${getString(R.string.help_conditions)}")
        val matcher2 = pattern2.matcher(s)
        while (matcher2.find()) {
            s.setSpan(ForegroundColorSpan(ContextCompat.getColor(context!!, R.color.backgroundBlueLight)), matcher2.start(), matcher2.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        return s
    }

    companion object {

        fun show(fragmentManager: FragmentManager): HelpKotlinDialog {
            val dialog = HelpKotlinDialog()
            DialogUtils.showDialg(fragmentManager, dialog)
            return dialog
        }
    }
}

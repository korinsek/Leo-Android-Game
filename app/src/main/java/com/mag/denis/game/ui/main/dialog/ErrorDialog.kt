package com.mag.denis.game.ui.main.dialog

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mag.denis.game.R
import kotlinx.android.synthetic.main.dialog_message.*

class ErrorDialog : BaseDialogFragment() {

    override var widthRatio = 0.5
    override var heightRatio = 0.5

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.dialog_error, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments!!
        val message = args.getString(MESSAGE_STR_ERROR)
        constraintDialog.setOnClickListener { dismiss() }
        tvMessage.text = message
    }

    companion object {
        const val MESSAGE_STR_ERROR = "com.mag.denis.game.ui.main.dialog.messageId"

        fun show(fragmentManager: FragmentManager, message: String): ErrorDialog {
            val args = Bundle(1)
            args.putString(MESSAGE_STR_ERROR, message)

            val dialog = ErrorDialog()
            dialog.arguments = args
            DialogUtils.showDialg(fragmentManager, dialog)
            return dialog
        }
    }
}

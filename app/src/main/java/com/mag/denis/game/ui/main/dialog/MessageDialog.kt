package com.mag.denis.game.ui.main.dialog

import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mag.denis.game.R
import kotlinx.android.synthetic.main.dialog_message.*

class MessageDialog : BaseDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.dialog_message, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments!!
        val messageID = args.getInt(MESSAGE_STR_ID)
        if (messageID == 0) {
            throw IllegalStateException("Message not defined")
        }

        constraintDialog.setOnClickListener { dismiss() }
        tvMessage.setText(messageID)
    }

    companion object {
        const val MESSAGE_STR_ID = "com.mag.denis.game.ui.main.dialog.messageId"

        fun show(fragmentManager: FragmentManager, @StringRes message: Int): MessageDialog {
            val args = Bundle(1)
            args.putInt(MESSAGE_STR_ID, message)

            val dialog = MessageDialog()
            dialog.arguments = args
            DialogUtils.showDialg(fragmentManager, dialog)
            return dialog
        }
    }
}

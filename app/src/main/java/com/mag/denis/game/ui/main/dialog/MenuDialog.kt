package com.mag.denis.game.ui.main.dialog

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mag.denis.game.R

class MenuDialog : BaseDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.dialog_menu, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //TODO set UI data to elements
    }

    companion object {

        fun show(fragmentManager: FragmentManager): MenuDialog {
            val dialog = MenuDialog()
            DialogUtils.showDialg(fragmentManager, dialog)
            return dialog
        }
    }
}

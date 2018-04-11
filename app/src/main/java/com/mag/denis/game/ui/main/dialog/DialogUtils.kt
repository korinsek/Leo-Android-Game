package com.mag.denis.game.ui.main.dialog


import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager

object DialogUtils {

    private val DIALOG_TAG = "com.mag.denis.game.dialog"

    fun showDialg(fragmentManager: FragmentManager, dialog: DialogFragment) {
        dialog.show(fragmentManager, DIALOG_TAG)
    }

}

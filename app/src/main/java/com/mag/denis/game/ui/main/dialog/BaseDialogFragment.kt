package com.mag.denis.game.ui.main.dialog

import android.support.v7.app.AppCompatDialogFragment

abstract class BaseDialogFragment : AppCompatDialogFragment() {

    open var widthRatio = 0.4
    open var heightRatio = 0.4

    override fun onResume() {
        val params = dialog.window.attributes

        params.width = (resources.displayMetrics.widthPixels * widthRatio).toInt()
        params.height = (resources.displayMetrics.heightPixels * heightRatio).toInt()

        dialog.window.attributes = params
        super.onResume()
    }

    public fun closeDialog() {
        dismiss()
    }
}

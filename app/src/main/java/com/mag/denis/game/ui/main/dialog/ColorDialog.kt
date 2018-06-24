package com.mag.denis.game.ui.main.dialog

import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mag.denis.game.R
import com.mag.denis.game.ui.main.objects.FloorSet
import kotlinx.android.synthetic.main.dialog_color.*

class ColorDialog : BaseDialogFragment() {

    private lateinit var callback: ColorCallback

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.dialog_color, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ivLeafGreen.setOnClickListener {
            callback.onColorTypeSelect(FloorSet.TYPE_LEAF_GREEN, R.drawable.ic_leaf_green)
            dismiss()
        }

        ivLeafBrown.setOnClickListener {
            callback.onColorTypeSelect(FloorSet.TYPE_LEAF_BROWN, R.drawable.ic_leaf_brown)
            dismiss()
        }
    }

    fun setColorListener(callback: ColorCallback) {
        this.callback = callback
    }

    companion object {

        fun show(fragmentManager: FragmentManager): ColorDialog {
            val dialog = ColorDialog()
            DialogUtils.showDialg(fragmentManager, dialog)
            return dialog
        }
    }

    interface ColorCallback {
        fun onColorTypeSelect(colorType: Int, @DrawableRes drawableSelected: Int)
    }
}

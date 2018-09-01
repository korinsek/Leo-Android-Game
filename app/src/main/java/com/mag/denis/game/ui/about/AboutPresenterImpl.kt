package com.mag.denis.game.ui.about

import com.mag.denis.game.ui.ActivityScope
import javax.inject.Inject

@ActivityScope
class AboutPresenterImpl @Inject constructor(private val view: AboutView) : AboutPresenter {

    override fun onBackClicked() {
        view.closeView()
    }
}

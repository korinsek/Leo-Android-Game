package com.mag.denis.game.ui.main.view

import com.mag.denis.game.ui.ViewScope
import dagger.Subcomponent

@Subcomponent
@ViewScope
interface GameViewSubComponent {

    fun inject(layout: GameView)

    @Subcomponent.Builder
    interface Builder {
        fun build(): GameViewSubComponent
    }
}

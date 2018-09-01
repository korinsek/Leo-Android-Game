package com.mag.denis.game.ui

import android.content.Context
import android.preference.PreferenceManager
import com.mag.denis.game.manager.LanguageManager
import com.mag.denis.game.utils.LanguageUtils
import dagger.android.support.DaggerAppCompatActivity


abstract class BaseActivity : DaggerAppCompatActivity() {

    override fun attachBaseContext(base: Context) {
        val selectedLanguage = PreferenceManager.getDefaultSharedPreferences(base)
                .getString(LanguageManager.LANG_PREFERENCES_ID, LanguageManager.LANG_CODE_EN)
        super.attachBaseContext(LanguageUtils.changeLang(base, selectedLanguage))
    }
}

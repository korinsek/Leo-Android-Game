package com.mag.denis.game.manager

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LanguageManager @Inject constructor(private val sharedPreferences: SharedPreferences) {
    
    fun setLang(language: String) {
        sharedPreferences.edit().putString(LANG_PREFERENCES_ID, language).apply()
    }

    fun getLang(): String {
        return sharedPreferences.getString(LANG_PREFERENCES_ID, LANG_CODE_EN)
    }

    companion object {
        const val LANG_PREFERENCES_ID = "com.mag.denis.game.manager.lang"
        
        const val LANG_CODE_EN = "en"
        const val LANG_CODE_SL = "sl"
    }
}

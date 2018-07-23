package com.mag.denis.game.utils

import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import java.util.*

object LanguageUtils {

    fun changeLang(context: Context, lang: String): ContextWrapper {
        val rs = context.resources
        val config = rs.configuration

        @Suppress("DEPRECATION")
        val sysLocale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.locales.get(0)
        } else {
            config.locale
        }

        if (lang.isNotEmpty() && sysLocale.language != lang) {
            val locale = Locale(lang)
            Locale.setDefault(locale)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                config.setLocale(locale)
            } else {
                @Suppress("DEPRECATION")
                config.locale = locale
            }

            return ContextWrapper(context.createConfigurationContext(config))
        }

        return ContextWrapper(context)
    }
}

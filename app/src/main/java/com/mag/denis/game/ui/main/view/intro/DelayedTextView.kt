package com.mag.denis.game.ui.main.view.intro

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatTextView
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import com.mag.denis.game.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

class DelayedTextView : AppCompatTextView {

    private var index: Int = 0
    private var delay: Long = 150
    private var reservedWord: String = ""
    private var textUpdateDisposable: Disposable? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    fun animateText(text: CharSequence, reservedWords: String, callback: TextTypedCallback) {
        reservedWord = reservedWords
        index = 1

        textUpdateDisposable?.dispose()
        textUpdateDisposable = Observable.interval(delay, TimeUnit.MILLISECONDS)
                .map {
                    colorText(SpannableString(text.subSequence(0, index++)))
                }.observeOn(AndroidSchedulers.mainThread()).subscribe({
                    setText(it)
                    if (index > text.length) {
                        textUpdateDisposable?.dispose()
                        callback.onTextTypingEnd()
                    }
                }, {
                })
    }

    private fun colorText(s: SpannableString): SpannableString {
        val pattern = Pattern.compile(reservedWord)
        val matcher = pattern.matcher(s)
        while (matcher.find()) {
            s.setSpan(ForegroundColorSpan(ContextCompat.getColor(context, R.color.loop_text_color)), matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        return s
    }


    fun setCharacterDelay(millis: Long) {
        delay = millis
    }

    interface TextTypedCallback {
        fun onTextTypingEnd()
    }
}

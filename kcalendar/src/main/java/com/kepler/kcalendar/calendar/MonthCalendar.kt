package com.kepler.kcalendar.calendar

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet

/**
 * @author kepler
 * @date 2022/3/31
 */
class MonthCalendar(
    context: Context,
    attrs: AttributeSet? = null,
) : BaseCalendar(context, attrs) {
    init {
        background = ColorDrawable(Color.CYAN)
    }
}
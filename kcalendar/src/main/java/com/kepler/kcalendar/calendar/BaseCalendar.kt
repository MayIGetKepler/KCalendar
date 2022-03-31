package com.kepler.kcalendar.calendar

import android.content.Context
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager

/**
 * @author kepler
 * @date 2022/3/31
 */
abstract class BaseCalendar(
    context: Context,
    attrs: AttributeSet? = null,
) : ViewPager(context, attrs),ICalendarView {
}
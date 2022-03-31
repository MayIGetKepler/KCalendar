package com.kepler.kcalendar.utils

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue

/**
 * @author kepler
 * @date 2022/3/31
 */
object ViewUtil {
    fun dp2px(context: Context?, dp: Float): Float {
        val metrics = context?.resources?.displayMetrics ?: Resources.getSystem().displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics)
    }
}
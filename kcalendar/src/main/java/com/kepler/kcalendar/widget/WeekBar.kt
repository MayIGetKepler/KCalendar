package com.kepler.kcalendar.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import kotlin.math.abs

/**
 * @author kepler
 * @date 2022/3/31
 */
class WeekBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.textViewStyle
) :
    AppCompatTextView(context, attrs, defStyleAttr) {

    companion object {
        private val dayOfWeek = arrayOf("日", "一", "二", "三", "四", "五", "六")
    }

    init {
        paint.textAlign = Paint.Align.CENTER
        paint.textAlign = Paint.Align.CENTER
    }

    override fun onDraw(canvas: Canvas) {
        //基线到中线的距离=(Descent+Ascent)/2-Descent = (Ascent-Descent)/2
        val fontMetrics = paint.fontMetrics
        val baseLineOffset = (abs(fontMetrics.ascent) - fontMetrics.descent) / 2
        val centerY = (height - paddingTop - paddingBottom).shr(1) + paddingTop
        val baseLine = centerY + baseLineOffset
        val blockWidth = (width - paddingStart - paddingEnd) * 1f / dayOfWeek.size
        dayOfWeek.forEachIndexed { index, it ->
            canvas.drawText(it, paddingStart + blockWidth * (index + 0.5f), baseLine, paint)
        }
    }
}
package com.kepler.kcalendar.widget

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.NestedScrollingParent3
import com.kepler.kcalendar.R
import com.kepler.kcalendar.calendar.MonthCalendar
import com.kepler.kcalendar.calendar.WeekCalendar
import com.kepler.kcalendar.utils.ViewUtil
import kotlin.math.abs
import kotlin.math.min

/**
 * @author kepler
 * @date 2022/3/31
 */
class KCalendar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), NestedScrollingParent3 {

    private val mMonthCalendar: MonthCalendar = MonthCalendar(context, attrs)
    private val mWeekCalendar: WeekCalendar = WeekCalendar(context, attrs)
    private val mLines = 5

    private var mLineHeight = 0
    private var monthCalendarHeight = ViewUtil.dp2px(context, 300f).toInt()
        set(value) {
            field = value
            mLineHeight = value / mLines
        }
    private val weekCalendarHeight: Int
        get() = monthCalendarHeight / mLines

    //展开高度 >= monthCalendarHeight
    private val mExpandedHeight
        get() = monthCalendarHeight

    //收缩高度 <= weekCalendarHeight
    private val mCollapsedHeight
        get() = weekCalendarHeight

    private lateinit var childView: View

    init {
        resolveAttrs(attrs)
        addView(mMonthCalendar, LayoutParams(LayoutParams.MATCH_PARENT, monthCalendarHeight))
        addView(
            mWeekCalendar,
            LayoutParams(LayoutParams.MATCH_PARENT, weekCalendarHeight)
        )
        mWeekCalendar.visibility = GONE
    }

    private fun resolveAttrs(attrs: AttributeSet?) {
        attrs ?: return
        val typedValue = context.obtainStyledAttributes(attrs, R.styleable.KCalendar)
        monthCalendarHeight = typedValue.getDimensionPixelSize(
            R.styleable.KCalendar_calendarHeight,
            monthCalendarHeight
        )
        typedValue.recycle()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (childCount > 3) {
            throw RuntimeException("只允许添加一个子View")
        }
        childView = getChildAt(childCount - 1)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val calendarLeft = paddingLeft
        val calendarTop = paddingTop
        val calendarRight = measuredWidth - paddingRight
        val monthCalendarBottom = paddingTop + monthCalendarHeight
        val weekCalendarBottom = paddingTop + weekCalendarHeight
        mMonthCalendar.layout(
            calendarLeft,
            calendarTop,
            calendarRight,
            monthCalendarBottom
        )
        mWeekCalendar.layout(
            calendarLeft,
            calendarTop,
            calendarRight,
            weekCalendarBottom
        )
        childView.layout(
            paddingLeft,
            monthCalendarBottom,
            measuredWidth - paddingRight,
            monthCalendarBottom + childView.measuredHeight
        )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }

    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
        return true
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {
    }

    override fun onStopNestedScroll(target: View, type: Int) {
    }

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray
    ) {

    }

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        val monthCalendarY = mMonthCalendar.y
        //向上滑动
        if (dy > 0) {
            //topLimit < 0
            val topLimit = (mCollapsedHeight - monthCalendarHeight).toFloat()
            //未滑到最顶部
            if (monthCalendarY > topLimit) {
                //此时monthCalendarY <= 0
                val gap = monthCalendarY - topLimit
                val consumedY = min(gap, dy.toFloat())
                mMonthCalendar.y = (monthCalendarY - consumedY).coerceAtLeast(topLimit)
                childView.y = (childView.y - consumedY).coerceAtLeast(mCollapsedHeight.toFloat())
                consumed[1] = consumedY.toInt()
            }
        }
        //向下滑动
        else if (dy < 0) {
            //topLimit >= 0
            val topLimit = (mExpandedHeight - monthCalendarHeight).toFloat()
            //未滑动到底，topLimit = 0
            if (monthCalendarY < topLimit) {
                val gap = topLimit - monthCalendarY
                val consumedY = min(gap, abs(dy).toFloat())
                mMonthCalendar.y = monthCalendarY + consumedY
                childView.y = childView.y + consumedY
                consumed[1] = -consumedY.toInt()
            }
        }
    }

}
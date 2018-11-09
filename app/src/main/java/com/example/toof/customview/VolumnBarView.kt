package com.example.toof.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class VolumnBarView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val mDefaultBarWidth = resources.getDimensionPixelSize(R.dimen.volumn_bar_default_width)
    private val mDefaultBarHeight = resources.getDimensionPixelSize(R.dimen.volumn_bar_default_height)
    private val mVerticalLinePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mCirclePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mLowLinePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mHighLinePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mVolumeLevelsCount: Int? = null
    private var mCurrentVolumeLevel: Int? = null

    init {
        mVerticalLinePaint.color = resources.getColor(R.color.white)
        mCirclePaint.color = resources.getColor(R.color.colorPrimary)
        mLowLinePaint.color = resources.getColor(R.color.low_white)
        mLowLinePaint.strokeWidth = 8f
        mHighLinePaint.color = resources.getColor(R.color.colorPrimary)
        mHighLinePaint.strokeWidth = 8f
    }

    override fun onDraw(canvas: Canvas) {
        drawBar(canvas)
        drawLowLine(canvas)
        drawLine(canvas)
        drawCircle(canvas)

    }

    private fun drawBar(canvas: Canvas) {
        val rect = RectF(0.0f, 0.0f, width.toFloat(), height.toFloat())
        canvas.drawRoundRect(rect, 35f, 35f, mVerticalLinePaint)
    }

    private fun drawLowLine(canvas: Canvas) {
        canvas.drawLine(
            width.toFloat() / 2.0f,
            (height - width / 2).toFloat() - 35 / 2f,
            width.toFloat() / 2.0f,
            (height - (height - width / 2).toFloat()) + 35 / 2f,
            mLowLinePaint
        )
    }

    private fun drawCircle(canvas: Canvas) {
        val circleX = width.toFloat() / 2.0f
        val circleY = calculateCircleY()
        val radius = 20.0f
        canvas.drawCircle(circleX, circleY, radius, mCirclePaint)
    }

    private fun calculateCircleY(): Float {
        val volumeLevelsCount = mVolumeLevelsCount
        val currentVolumeLevel = mCurrentVolumeLevel

        return if (volumeLevelsCount != null && currentVolumeLevel != null) {
            (height - ((height - width) / volumeLevelsCount * currentVolumeLevel + width / 2)).toFloat()
        } else {
            0.0f
        }
    }

    private fun drawLine(canvas: Canvas) {
        invalidate()
        canvas.drawLine(
            width.toFloat() / 2.0f,
            (height - width / 2).toFloat() - 35 / 2f,
            width.toFloat() / 2.0f,
            calculateLineY(),
            mHighLinePaint
        )
    }

    private fun calculateLineY(): Float {
        invalidate()
        val volumeLevelsCount = mVolumeLevelsCount
        val currentVolumeLevel = mCurrentVolumeLevel

        return if (volumeLevelsCount != null && currentVolumeLevel != null) {
            (height - ((height - width) / volumeLevelsCount * currentVolumeLevel + width.toFloat() / 2.0f))
        } else {
            0.0f
        }
    }

    fun setVolumeLevel(volumnLevel: Int) {
        mCurrentVolumeLevel = volumnLevel
    }

    fun calibrateVolumeLevels(volumeLevelsCount: Int, currentVolumeLevel: Int) {
        mVolumeLevelsCount = volumeLevelsCount
        mCurrentVolumeLevel = currentVolumeLevel
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)

        val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = View.MeasureSpec.getSize(heightMeasureSpec)

        val width = when (widthMode) {
            View.MeasureSpec.EXACTLY -> widthSize
            View.MeasureSpec.AT_MOST -> mDefaultBarWidth
            View.MeasureSpec.UNSPECIFIED -> mDefaultBarWidth
            else -> mDefaultBarWidth
        }

        val height = when (heightMode) {
            View.MeasureSpec.EXACTLY -> heightSize
            View.MeasureSpec.AT_MOST -> mDefaultBarHeight
            View.MeasureSpec.UNSPECIFIED -> mDefaultBarHeight
            else -> mDefaultBarHeight
        }

        setMeasuredDimension(width, height)
    }
}
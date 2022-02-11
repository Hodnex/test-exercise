package com.hodnex.testphotoplan.widgets

import android.content.Context
import android.graphics.Outline
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.FrameLayout
import com.hodnex.testphotoplan.R

class OutlinedFrameLayout : FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        parseAttrs(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        parseAttrs(attrs)
    }

    private var cornerRadius: Int = 0

    private var rightOutline: Int = 0
    private var leftOutline: Int = 0
    private var topOutline: Int = 0
    private var bottomOutline: Int = 0

    private var horizontalOutline: Int = 0
        set(value) {
            field = value
            rightOutline = value
            leftOutline = value
        }

    private var verticalOutline: Int = 0
        set(value) {
            field = value
            topOutline = value
            bottomOutline = value
        }

    private fun createOutlineProvider(): ViewOutlineProvider {
        return object : ViewOutlineProvider() {
            override fun getOutline(view: View?, outline: Outline?) {
                outline?.setRoundRect(
                    leftOutline,
                    topOutline,
                    width - rightOutline,
                    height - bottomOutline,
                    cornerRadius.toFloat()
                )
            }
        }
    }

    private fun parseAttrs(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.OutlinedFrameLayout)
        bottomOutline =
            typedArray.getDimensionPixelSize(R.styleable.OutlinedFrameLayout_ofl_outlineBottom, 0)
        topOutline =
            typedArray.getDimensionPixelSize(R.styleable.OutlinedFrameLayout_ofl_outlineTop, 0)
        leftOutline =
            typedArray.getDimensionPixelSize(R.styleable.OutlinedFrameLayout_ofl_outlineLeft, 0)
        rightOutline =
            typedArray.getDimensionPixelSize(R.styleable.OutlinedFrameLayout_ofl_outlineRight, 0)
        if (leftOutline == rightOutline) {
            horizontalOutline =
                typedArray.getDimensionPixelSize(R.styleable.OutlinedFrameLayout_ofl_outlineHorizontal, leftOutline)
        }
        if (topOutline == bottomOutline) {
            verticalOutline =
                typedArray.getDimensionPixelSize(R.styleable.OutlinedFrameLayout_ofl_outlineVertical, topOutline)
        }
        cornerRadius =
            typedArray.getDimensionPixelSize(R.styleable.OutlinedFrameLayout_ofl_cornerRadius, 0)
        typedArray.recycle()
        outlineProvider = createOutlineProvider()
    }
}
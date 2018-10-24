package com.example.chintansoni.myapplication.view.customview

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet

class SquareImageView(context: Context, attributeSet: AttributeSet) : AppCompatImageView(context, attributeSet) {

    init {
        adjustViewBounds = true
        scaleType = ScaleType.CENTER_CROP
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}
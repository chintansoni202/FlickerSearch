package com.example.chintansoni.myapplication.view.customview

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout

class SquareLinearLayout(context: Context, attributeSet: AttributeSet) : LinearLayout(context, attributeSet) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }

}
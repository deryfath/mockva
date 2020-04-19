package com.project.daksatest.extension

import android.widget.Button
import androidx.core.content.ContextCompat
import com.project.daksatest.R

fun Button.registerIndicatorActive(){
    isEnabled = true
    background = ContextCompat.getDrawable(context, R.drawable.circle_green)
    setTextColor(ContextCompat.getColor(context, R.color.white))
}

fun Button.registerIndicatorInactive(){
    isEnabled = true
    background = ContextCompat.getDrawable(context, R.drawable.circle_white)
    setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
}
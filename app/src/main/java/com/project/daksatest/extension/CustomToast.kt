package com.project.daksatest.extension

import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import com.project.daksatest.R


fun Activity.createErrorToast(message:String, gravity:Int = Gravity.TOP, duration:Int = Toast.LENGTH_SHORT) {
    val toast = Toast(this)
    val inflater: LayoutInflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val layout = inflater.inflate(R.layout.dialog_error_state, this.findViewById(R.id.containerErrorState))
    layout.findViewById<TextView>(R.id.tvMessage).text = message
    toast.setGravity(gravity, 0, 0)
    toast.duration = duration
    toast.view = layout
    toast.show()
}

fun Activity.createSuccessToast(message:String, gravity:Int = Gravity.TOP, duration:Int = Toast.LENGTH_SHORT){
    val toast = Toast(this)
    val inflater: LayoutInflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val layout = inflater.inflate(R.layout.dialog_success_state, this.findViewById(R.id.containerErrorState))
    layout.findViewById<TextView>(R.id.tvMessage).text = message
    toast.setGravity(gravity, 0, 0)
    toast.duration = duration
    toast.view = layout
    toast.show()
}
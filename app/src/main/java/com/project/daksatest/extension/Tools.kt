package com.project.daksatest.extension

import android.animation.ObjectAnimator
import android.app.Activity
import android.app.ProgressDialog
import android.content.*
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.LinearInterpolator
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.project.daksatest.R
import com.project.daksatest.state.LocalStateChange
import org.greenrobot.eventbus.EventBus


object Tools {


    fun setSpinnerLangEvent(spinner:Spinner,pref:SharedPreferences, context: Context, act: Activity){

        var firstEventConsumed = false
        val arrayLang = context.resources.getStringArray(R.array.country_arrays)


        if(pref.get(GlobalVariable.LocalHelper.toDescription())==GlobalVariable.InLang.toDescription()){
            spinner.setSelection(1)
        }else{
            spinner.setSelection(0)
        }

        val arrayAdapter = ArrayAdapter<String>(context,R.layout.spinner_item_lang, arrayLang)

        spinner.adapter = arrayAdapter

        spinner.setOnTouchListener(View.OnTouchListener { view, motionEvent ->
            firstEventConsumed = true
            false
        })

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {


            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                println("POSITION : $position")

                if(position==0){
                    LocaleHelper.setLocale(context,GlobalVariable.DefaultLang.toDescription())


                }else{
                    LocaleHelper.setLocale(context,GlobalVariable.InLang.toDescription())

                }

                if (firstEventConsumed) {

                    if(position==0){
                        EventBus.getDefault().post(LocalStateChange(GlobalVariable.DefaultLang.toDescription()))

                    }else{
                        EventBus.getDefault().post(LocalStateChange(GlobalVariable.InLang.toDescription()))

                    }

                    act.recreate();


                }

            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // your code here
            }

        }
    }

    fun getStandardSpinnerItemView() : Int {
        return R.layout.item_dropdown_general
    }

    //Email Validation pattern
    val regEx = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b"

    fun createRotateAnimator(target: View, from: Float, to: Float): ObjectAnimator {
        val animator = ObjectAnimator.ofFloat(target, "rotation", from, to)
        animator.duration = 100
        animator.interpolator = LinearInterpolator()
        return animator
    }

    fun isValidEmail(email: String?): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun progressBar(act: Activity, message:String):ProgressDialog{
        val dialog = ProgressDialog(act)
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        dialog.setMessage(message)
        dialog.isIndeterminate = true
        dialog.setCanceledOnTouchOutside(false)
        dialog.cancel()
        return dialog
    }

    fun hideStatusBar(act: Activity) {

        act.requestWindowFeature(Window.FEATURE_NO_TITLE);
        act.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    fun tranparentStatusBar(act: Activity) {

        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false,act)
            act.window.statusBarColor = Color.TRANSPARENT
        }
    }

    private fun setWindowFlag(bits: Int, on: Boolean, act: Activity) {
        val win = act.window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }

    fun dateFormat(context: Context,dateString : String):String{

        val dateNew = dateString.split("-")
        var date = dateNew[2]
        var month = dateNew[1]
        var year = dateNew[0]
        var monthNew = ""

//        debug("MONTH : $dateNew")

        val monthArray = context.resources.getStringArray(R.array.month_array)
        monthArray.forEachIndexed { index, s ->

            if(month.toInt()==index){
                monthNew = s
            }

        }

        return date+" "+monthNew+" "+year

    }

    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
        val fragmentTransaction = beginTransaction()
        fragmentTransaction.func()
        fragmentTransaction.commit()
    }


    fun addFragment(fragment: Fragment, fragmentManager:FragmentManager, frameId: Int, tag: String? = null) {
        fragmentManager.inTransaction { add(frameId, fragment, tag) }
    }

    fun callFragment(fragment: Fragment, fragmentManager:FragmentManager, frameId: Int, tag: String? = null, addToBackStack: Boolean = false) {
        fragmentManager.inTransaction{
            if (addToBackStack) {
                addToBackStack(null)
            }
            replace(frameId, fragment, tag)
        }
    }



}
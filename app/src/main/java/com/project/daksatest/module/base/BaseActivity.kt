package com.project.daksatest.module.base

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.project.daksatest.R
import com.project.daksatest.extension.ViewLoadingDotsBounce
import com.project.daksatest.extension.createErrorToast

abstract class BaseActivity : AppCompatActivity(), BaseView {

    protected var progressDialog: Dialog? = null
    protected var animationProgress: ViewLoadingDotsBounce? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        progressDialog = Dialog(this, android.R.style.Theme_Light)
        progressDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progressDialog?.setContentView(R.layout.dialog_loading)

        animationProgress = progressDialog?.findViewById(R.id.progress_animation)

        val window = progressDialog?.window
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
        window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun showLoading() {
        progressDialog!!.show()
    }

    override fun hideLoading() {
        if(progressDialog != null && progressDialog!!.isShowing){
            progressDialog?.dismiss()
            animationProgress?.onLayoutReach = false
        }
    }

    override fun showError(message: String) {
        createErrorToast(message)
    }
}


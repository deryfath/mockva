package com.project.daksatest.module.splash

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.project.daksatest.App
import com.project.daksatest.R
import com.project.daksatest.extension.*
import com.project.daksatest.module.MainActivity
import com.project.daksatest.module.auth.login.LoginActivity
import javax.inject.Inject

class SplashActivity : Activity() {

    @Inject
    lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Tools.tranparentStatusBar(this)

        setContentView(R.layout.activity_splash)

        App.component.inject(this)

        if(pref.get(GlobalVariable.LocalHelper.toDescription())==null){
            pref.save(GlobalVariable.LocalHelper.toDescription(), GlobalVariable.DefaultLang.toDescription())

        }

        if (pref.get(GlobalVariable.SessionId.toDescription()) == null) {
            goto(LoginActivity::class.java)
        } else {
            goto(MainActivity::class.java)
        }

    }

    fun <T : Activity> goto(nextClass: Class<T>) {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val intent = Intent(this, nextClass)
            startActivity(intent)
            finish()
        }, 3000)
    }
}

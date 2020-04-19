package com.project.daksatest.module.auth.login

import com.project.daksatest.model.LoginResponse
import com.project.daksatest.mvp.View

interface LoginView:View {

    fun onLoginSuccess(data : LoginResponse)
    fun onLoginFailed(message : String)


}
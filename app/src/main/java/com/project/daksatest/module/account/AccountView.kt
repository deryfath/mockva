package com.project.daksatest.module.account

import com.project.daksatest.mvp.View

interface AccountView:View {
    fun onLogoutSuccess(data : Any)
    fun onLogoutFailed(message : String)
}
package com.project.daksatest.module.main

import com.project.daksatest.model.AccountDetailResponse
import com.project.daksatest.mvp.View

interface HomeView:View {
    fun onGetDataDetailAccountSuccess(data : AccountDetailResponse)
    fun onGetDataDetailAccountFailed(message : String)
}
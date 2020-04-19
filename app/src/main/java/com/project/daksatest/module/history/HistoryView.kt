package com.project.daksatest.module.history

import com.project.daksatest.model.DataTransaction
import com.project.daksatest.mvp.View

interface HistoryView:View {
    fun onLoadHistorySuccess(data : List<DataTransaction>)
    fun onLoadHistoryFailed(message : String)
}
package com.project.daksatest.module.transfer

import com.project.daksatest.model.TransferConfirmResponse
import com.project.daksatest.model.TransferInquiryResponse
import com.project.daksatest.mvp.View

interface TransferView:View {

    fun onLoadTransferInquirySuccess(data : TransferInquiryResponse)
    fun onLoadTransferInquiryFailed(message : String)

    fun onLoadTransferConfirmSuccess(data : TransferConfirmResponse)
    fun onLoadTransferConfirmFailed(message : String)

}
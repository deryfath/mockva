package com.project.daksatest.model

data class TransferInquiryRequest(
    val accountDstId: String?,
    val accountSrcId: String,
    val amount: Int
)
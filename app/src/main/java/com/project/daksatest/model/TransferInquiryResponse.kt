package com.project.daksatest.model

data class TransferInquiryResponse(
    val accountDstId: String,
    val accountDstName: String,
    val accountSrcId: String,
    val accountSrcName: String,
    val amount: Int,
    val inquiryId: String
)
package com.project.daksatest.model

data class TransferConfirmRequest(
    val accountDstId: String?,
    val accountSrcId: String,
    val amount: Int,
    val inquiryId: String
)
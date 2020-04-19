package com.project.daksatest.model

data class TransferConfirmResponse(
    val accountDstId: String,
    val accountSrcId: String,
    val amount: Int,
    val clientRef: String,
    val transactionTimestamp: String
)
package com.project.daksatest.model

data class TransactionHistoryResponse(
    val `data`: List<DataTransaction>,
    val rowCount: Int
)

data class DataTransaction(
    val accountDstId: String,
    val accountSrcId: String,
    val amount: Double,
    val clientRef: String,
    val id: String,
    val transactionTimestamp: String
)
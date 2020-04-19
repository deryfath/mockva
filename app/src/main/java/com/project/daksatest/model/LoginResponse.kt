package com.project.daksatest.model

data class LoginResponse(
    val accountId: String,
    val id: String,
    val lastLoginTimestamp: String,
    val sessionStatus: String
)
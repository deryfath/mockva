package com.project.daksatest.model


data class ResponseData<T>(
        var status: Int = 0,
        var msg: String = "",
        var data: T
)
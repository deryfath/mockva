package com.project.daksatest.service

import com.project.daksatest.model.*
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {

    @GET("/mockva-rest/rest/account/detail")
    fun getAccountDetail(@Header("_sessionId") sessionId:String?, @Query("id") id: String?): Observable<AccountDetailResponse>

    @POST("/mockva-rest/rest/auth/login")
    fun login(@Body body:RequestLogin): Observable<LoginResponse>

    @POST("/mockva-rest/rest/account/transaction/transferInquiry")
    fun transferInquiry(@Header("_sessionId") sessionId:String?, @Body body:TransferInquiryRequest): Observable<TransferInquiryResponse>

    @POST("/mockva-rest/rest/account/transaction/transfer")
    fun transferConfirm(@Header("_sessionId") sessionId:String?, @Body body:TransferConfirmRequest): Observable<TransferConfirmResponse>

    @GET("/mockva-rest/rest/account/transaction/log")
    fun getTransationHistory(@Header("_sessionId") sessionId:String?):Observable<TransactionHistoryResponse>

    @DELETE("/mockva-rest/rest/auth/logout")
    fun logout(@Header("_sessionId") sessionId:String?):Observable<Any>

}
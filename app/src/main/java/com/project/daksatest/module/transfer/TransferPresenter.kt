package com.project.daksatest.module.transfer

import android.content.SharedPreferences
import com.project.daksatest.dagger.qualifier.Authorized
import com.project.daksatest.extension.GlobalVariable
import com.project.daksatest.extension.get
import com.project.daksatest.model.TransferConfirmRequest
import com.project.daksatest.model.TransferInquiryRequest
import com.project.daksatest.module.history.HistoryView
import com.project.daksatest.mvp.Presenter
import com.project.daksatest.service.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.http.Body
import javax.inject.Inject

class TransferPresenter @Inject constructor(
    @Authorized val api: ApiService,
    val retrofit: Retrofit,
    val pref: SharedPreferences
): Presenter<TransferView> {
    var transferInquiryDisposable = Disposables.empty()
    var transferConfirmDisposable = Disposables.empty()
    private var view : TransferView? = null

    override fun onAttach(view: TransferView) {
        this.view = view
    }

    override fun onDetach() {
        view = null
    }

    fun transferInquiry(body: TransferInquiryRequest){

        transferInquiryDisposable.dispose()
        val sessionId = pref.get(GlobalVariable.SessionId.toDescription())

        transferInquiryDisposable = api.transferInquiry(sessionId,body)
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                    res ->

                view?.onLoadTransferInquirySuccess(res)
            }, {
                    err ->
                if (err is HttpException) {
//                    val body = retrofit.errorConverter<Response<Throwable>>(err)
                    view?.onLoadTransferInquiryFailed( err.code().toString())
                } else {
                    view?.onLoadTransferInquiryFailed(err.localizedMessage)
                }
            })
    }


    fun transferConfirm(body: TransferConfirmRequest){

        transferConfirmDisposable.dispose()
        val sessionId = pref.get(GlobalVariable.SessionId.toDescription())

        transferConfirmDisposable = api.transferConfirm(sessionId,body)
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                    res ->

                view?.onLoadTransferConfirmSuccess(res)
            }, {
                    err ->
                if (err is HttpException) {
//                    val body = retrofit.errorConverter<Response<Throwable>>(err)
                    view?.onLoadTransferConfirmFailed( err.code().toString())
                } else {
                    view?.onLoadTransferConfirmFailed(err.localizedMessage)
                }
            })
    }
}
package com.project.daksatest.module.main

import android.content.Intent
import android.content.SharedPreferences
import com.project.daksatest.dagger.qualifier.Authorized
import com.project.daksatest.extension.GlobalVariable
import com.project.daksatest.extension.clear
import com.project.daksatest.extension.errorConverter
import com.project.daksatest.extension.get
import com.project.daksatest.mvp.Presenter
import com.project.daksatest.service.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class HomePresenter  @Inject constructor(
    @Authorized val api: ApiService,
    val retrofit: Retrofit,
    val pref: SharedPreferences
): Presenter<HomeView> {

    private var view : HomeView? = null

    var getAccountDetailDisposable = Disposables.empty()

    override fun onAttach(view: HomeView) {
        this.view = view
    }

    override fun onDetach() {
        view = null
    }

    fun getAccountDetail(){

        getAccountDetailDisposable.dispose()
        val accountId = pref.get(GlobalVariable.AccountId.toDescription())
        val sessionId = pref.get(GlobalVariable.SessionId.toDescription())
        getAccountDetailDisposable = api.getAccountDetail(sessionId,accountId)
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                    res ->

                view?.onGetDataDetailAccountSuccess(res)
            }, {
                    err ->
                if (err is HttpException) {
//                    val body = retrofit.errorConverter<Response<Throwable>>(err)
                    view?.onGetDataDetailAccountFailed( err.code().toString())
                } else {
                    println("ERROR NON EXCEPTION ${err.stackTrace}")
                    view?.onGetDataDetailAccountFailed(err.localizedMessage)
                }
            })
    }

}
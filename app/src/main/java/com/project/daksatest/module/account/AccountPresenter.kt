package com.project.daksatest.module.account

import android.content.SharedPreferences
import com.project.daksatest.dagger.qualifier.Authorized
import com.project.daksatest.extension.GlobalVariable
import com.project.daksatest.extension.get
import com.project.daksatest.mvp.Presenter
import com.project.daksatest.service.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import retrofit2.Retrofit
import javax.inject.Inject

class AccountPresenter @Inject constructor(
    @Authorized val api: ApiService,
    val retrofit: Retrofit,
    val pref: SharedPreferences
): Presenter<AccountView> {

    private var view : AccountView? = null

    var logoutDisposable = Disposables.empty()

    override fun onAttach(view: AccountView) {
        this.view = view
    }

    override fun onDetach() {
        view = null
    }

    fun logout(){

        logoutDisposable.dispose()
        val sessionId = pref.get(GlobalVariable.SessionId.toDescription())

        logoutDisposable = api.logout(sessionId)
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                    res ->

                view?.onLogoutSuccess(res)
            }, {
                    err ->
                if (err is HttpException) {
//                    val body = retrofit.errorConverter<Response<Throwable>>(err)
                    view?.onLogoutFailed( err.code().toString())
                } else {
                    view?.onLogoutFailed(err.localizedMessage)
                }
            })
    }
}
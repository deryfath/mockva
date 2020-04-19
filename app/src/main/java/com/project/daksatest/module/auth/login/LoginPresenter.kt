package com.project.daksatest.module.auth.login

import android.content.SharedPreferences
import com.project.daksatest.dagger.qualifier.Authorized
import com.project.daksatest.extension.errorConverter
import com.project.daksatest.model.RequestLogin
import com.project.daksatest.mvp.Presenter
import com.project.daksatest.service.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class LoginPresenter  @Inject constructor(
    @Authorized val api: ApiService,
    val retrofit: Retrofit,
    val pref: SharedPreferences
): Presenter<LoginView> {

    private var view : LoginView? = null

    var loginDisposable = Disposables.empty()

    override fun onAttach(view: LoginView) {
        this.view = view
    }

    override fun onDetach() {
        view = null
    }

    fun doLogin(request:RequestLogin){

        loginDisposable.dispose()
        loginDisposable = api.login(request)
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                    res ->

                view?.onLoginSuccess(res)
            }, {
                    err ->
                if (err is HttpException) {
//                    val body = retrofit.errorConverter<Response<Throwable>>(err)
                    view?.onLoginFailed("Error: ${err.code()}")
                } else {
                    view?.onLoginFailed(err.localizedMessage)
                }
            })
    }
}
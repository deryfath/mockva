package com.project.daksatest.activity.main

import com.project.daksatest.dagger.qualifier.Authorized
import com.project.daksatest.mvp.Presenter
import com.project.daksatest.service.ApiService
import io.reactivex.disposables.Disposables
import retrofit2.Retrofit
import javax.inject.Inject


class MainPresenter @Inject constructor(
    @Authorized val api: ApiService,
    val retrofit: Retrofit
): Presenter<MainView> {

    private var view : MainView? = null

    var productListDisposables = Disposables.empty()


    override fun onAttach(view: MainView) {
        this.view = view
    }

    override fun onDetach() {
        view = null
    }

//    fun loadProductList(){
//
//        productListDisposables.dispose()
//        productListDisposables = api.getProducts()
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    res ->
//
//                    view?.onLoadSuccess(res.data)
//
//                }, {
//                    err ->
//                    if (err is HttpException) {
//                        val body = retrofit.errorConverter<Response<Throwable>>(err)
//                        view?.onLoadFailed("Error: ${body}")
//                    } else {
//                        view?.onLoadFailed("Error : ${err.localizedMessage}")
//                    }
//                })
//    }



}
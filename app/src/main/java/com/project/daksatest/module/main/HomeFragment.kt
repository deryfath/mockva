package com.project.daksatest.module.main

import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.project.daksatest.App
import com.project.daksatest.R
import com.project.daksatest.extension.GlobalVariable
import com.project.daksatest.extension.clear
import com.project.daksatest.model.AccountDetailResponse
import com.project.daksatest.module.MainActivity
import com.project.daksatest.module.auth.login.LoginActivity
import com.project.daksatest.module.splash.SplashActivity
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class HomeFragment :Fragment(),HomeView {

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    @Inject
    lateinit var pref: SharedPreferences

    @Inject
    lateinit var presenter: HomePresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        App.component.inject(this)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.onAttach(this)
        presenter.getAccountDetail()
    }

    override fun onAttach() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onGetDataDetailAccountSuccess(data: AccountDetailResponse) {

        tv_account_number.text = data.id
        tv_name.text = data.name
        tv_balance.text = data.balance.toString()
    }

    override fun onGetDataDetailAccountFailed(message: String) {
        println("ERROR $message")

        val builder = activity?.let { AlertDialog.Builder(it) }
        builder?.setTitle("Notifikasi")
        builder?.setMessage("Waktu login anda telah berakhir, silakan login untuk melanjutkan")
        builder?.setPositiveButton("OK",
            DialogInterface.OnClickListener { dialogInterface, i ->
                pref.clear()
                startActivity(Intent(activity,SplashActivity::class.java))
                activity?.finish()
            })
        builder?.show()

    }

    override fun lifecycle(): Observable<ActivityEvent> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <T> bindTolifeCycle(): LifecycleTransformer<T> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
package com.project.daksatest.module.account

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.project.daksatest.App
import com.project.daksatest.R
import com.project.daksatest.extension.clear
import com.project.daksatest.module.MainActivity
import com.project.daksatest.module.splash.SplashActivity
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_account.*
import javax.inject.Inject

class AccountFragment:Fragment(),AccountView {

    companion object {
        fun newInstance(): AccountFragment {
            return AccountFragment()
        }
    }

    @Inject
    lateinit var pref: SharedPreferences

    @Inject
    lateinit var presenter: AccountPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_account, container, false)
        App.component.inject(this)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.onAttach(this)

        btn_logout.setOnClickListener {
            presenter.logout()
        }

    }

    override fun onAttach() {
    }

    override fun onLogoutSuccess(data: Any) {
        pref.clear()
        startActivity(Intent(activity, SplashActivity::class.java))
        activity?.finish()
    }

    override fun onLogoutFailed(message: String) {
        println("ERROR $message")
        pref.clear()
        startActivity(Intent(activity, SplashActivity::class.java))
        activity?.finish()
    }

    override fun lifecycle(): Observable<ActivityEvent> {
        TODO("Not yet implemented")
    }

    override fun <T> bindTolifeCycle(): LifecycleTransformer<T> {
        TODO("Not yet implemented")
    }
}
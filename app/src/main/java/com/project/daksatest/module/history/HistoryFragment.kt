package com.project.daksatest.module.history

import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.daksatest.App
import com.project.daksatest.R
import com.project.daksatest.extension.clear
import com.project.daksatest.model.DataTransaction
import com.project.daksatest.module.splash.SplashActivity
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_history.*
import javax.inject.Inject

class HistoryFragment:Fragment(),HistoryView {

    companion object {
        fun newInstance(): HistoryFragment {
            return HistoryFragment()
        }
    }

    private lateinit var adapter: HistoryAdapter

    @Inject
    lateinit var pref: SharedPreferences

    @Inject
    lateinit var presenter: HistoryPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        App.component.inject(this)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.onAttach(this)
        presenter.getHistoryTransaction()
    }

    override fun onAttach() {
    }

    override fun onLoadHistorySuccess(data: List<DataTransaction>) {
        rv_history.layoutManager = LinearLayoutManager(context)
        adapter= HistoryAdapter(data)
        rv_history.adapter=adapter
    }

    override fun onLoadHistoryFailed(message: String) {
        println("ERROR $message")

        val builder = activity?.let { AlertDialog.Builder(it) }
        builder?.setTitle("Notifikasi")
        builder?.setMessage("Waktu login anda telah berakhir, silakan login untuk melanjutkan")
        builder?.setPositiveButton("OK",
            DialogInterface.OnClickListener { dialogInterface, i ->
                pref.clear()
                startActivity(Intent(activity, SplashActivity::class.java))
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
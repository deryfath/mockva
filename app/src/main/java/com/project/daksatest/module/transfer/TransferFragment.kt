package com.project.daksatest.module.transfer

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.project.daksatest.App
import com.project.daksatest.R
import com.project.daksatest.extension.GlobalVariable
import com.project.daksatest.extension.clear
import com.project.daksatest.extension.get
import com.project.daksatest.model.TransferConfirmRequest
import com.project.daksatest.model.TransferConfirmResponse
import com.project.daksatest.model.TransferInquiryRequest
import com.project.daksatest.model.TransferInquiryResponse
import com.project.daksatest.module.splash.SplashActivity
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_booking.*
import javax.inject.Inject


class TransferFragment :Fragment(),TransferView {

    companion object {
        fun newInstance(): TransferFragment {
            return TransferFragment()
        }
    }

    private var inquiryId = ""
    lateinit var dialogSuccessTransfer : Dialog

    @Inject
    lateinit var pref: SharedPreferences

    @Inject
    lateinit var presenter: TransferPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_booking, container, false)
        App.component.inject(this)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.onAttach(this)
        flipper.displayedChild = 0

        btn_transfer.setOnClickListener {
            val request = TransferInquiryRequest(pref.get(GlobalVariable.AccountId.toDescription()),ed_account_destination.text.toString(),ed_amount.text.toString().toInt())

            presenter.transferInquiry(request)

        }

        btn_confirm.setOnClickListener {
            val request = TransferConfirmRequest(pref.get(GlobalVariable.AccountId.toDescription()),ed_account_destination.text.toString(),ed_amount.text.toString().toInt(),inquiryId)
            presenter.transferConfirm(request)
        }

    }

    override fun onAttach() {
    }

    override fun onLoadTransferInquirySuccess(data: TransferInquiryResponse) {
        flipper.displayedChild = 1
        tv_accountSource.text = data.accountSrcId
        tv_accountSourceName.text = data.accountSrcName
        tv_accountDestination.text = data.accountDstId
        tv_accountDestinationName.text = data.accountDstName
        tv_amount.text = data.amount.toString()
        inquiryId = data.inquiryId
    }

    override fun onLoadTransferInquiryFailed(message: String) {
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

    override fun onLoadTransferConfirmSuccess(data: TransferConfirmResponse) {
        dialogSuccessTransfer = Dialog(requireContext())
        dialogSuccessTransfer.requestWindowFeature(Window.FEATURE_NO_TITLE) // before
        dialogSuccessTransfer.setContentView(R.layout.dialog_success_transfer)
        dialogSuccessTransfer.setCancelable(true)
        dialogSuccessTransfer.setCanceledOnTouchOutside(true)

        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialogSuccessTransfer.getWindow()!!.getAttributes())
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT

        dialogSuccessTransfer.getWindow()!!.setAttributes(lp)
        dialogSuccessTransfer.findViewById<TextView>(R.id.tv_accountSource).text = data.accountDstId
        dialogSuccessTransfer.findViewById<TextView>(R.id.tv_accountSourceName).text = tv_accountSourceName.text
        dialogSuccessTransfer.findViewById<TextView>(R.id.tv_accountDestination).text = data.accountDstId
        dialogSuccessTransfer.findViewById<TextView>(R.id.tv_accountDestinationName).text = tv_accountDestinationName.text
        dialogSuccessTransfer.findViewById<TextView>(R.id.tv_amount).text = tv_amount.text
        dialogSuccessTransfer.findViewById<TextView>(R.id.tv_reference).text = data.clientRef
        dialogSuccessTransfer.findViewById<TextView>(R.id.tv_transactionTimestamp).text = data.transactionTimestamp
        dialogSuccessTransfer.findViewById<TextView>(R.id.tv_transactionStatus).text = "SUCCESS"
        dialogSuccessTransfer.show()

        flipper.displayedChild = 0
        ed_amount.text.clear()
        ed_account_destination.text.clear()
    }

    override fun onLoadTransferConfirmFailed(message: String) {
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
        TODO("Not yet implemented")
    }

    override fun <T> bindTolifeCycle(): LifecycleTransformer<T> {
        TODO("Not yet implemented")
    }
}
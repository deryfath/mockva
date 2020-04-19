package com.project.daksatest.module.auth.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.text.Editable
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import com.project.daksatest.App
import com.project.daksatest.R
import com.project.daksatest.extension.*
import com.project.daksatest.model.LoginResponse
import com.project.daksatest.model.RequestLogin
import com.project.daksatest.module.MainActivity
import com.project.daksatest.module.base.BaseActivity
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*
import javax.inject.Inject

class LoginActivity : BaseActivity(),LoginView {

    @Inject
    lateinit var pref: SharedPreferences

    var  showPassword = false

    @Inject
    lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Tools.tranparentStatusBar(this)

        setContentView(R.layout.activity_login)

        App.component.inject(this)

        onAttach()
    }

    override fun onLoginSuccess(data: LoginResponse) {
        println("DATA $data")
        pref.save(GlobalVariable.SessionId.toDescription(),data.id)
        pref.save(GlobalVariable.AccountId.toDescription(),data.accountId)
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            startActivity(Intent(this,MainActivity::class.java))
            setResult(Activity.RESULT_OK)
            finish()
            super.hideLoading()

        }, 2000)

    }

    override fun onLoginFailed(message: String) {
        println("ERROR $message")
        super.hideLoading()
        this.createErrorToast("ERROR "+message)
    }

    override fun onAttach() {

        presenter.onAttach(this)

        image_eye.setOnClickListener {
            showPassword = !showPassword
            if(showPassword){
                image_eye.setImageResource(R.drawable.eye)
                ed_password.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }
            else {
                image_eye.setImageResource(R.drawable.eye_off)
                ed_password.transformationMethod = PasswordTransformationMethod.getInstance()
            }
            if(ed_password.text != null)
                ed_password.setSelection(ed_password.text!!.length)
        }

        btn_login.setOnClickListener {

            if(validateLogin(ed_email.text!!,ed_password.text!!)==true){


                super.showLoading()
                //call presenter
                val requestLogin = RequestLogin(ed_email.text.toString(),ed_password.text.toString())
                presenter.doLogin(requestLogin)


            }

        }
    }

    override fun onDetach() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun lifecycle(): Observable<ActivityEvent> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <T> bindTolifeCycle(): LifecycleTransformer<T> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun validateLogin(email: Editable, password: Editable): Boolean {
        val errorMessage = when {
            email.isBlank() -> "${getString(R.string.email_address)} tidak boleh kosong"
//            Tools.isValidEmail(email.toString()).not() -> "format email salah"
            password.isBlank() -> "${getString(R.string.password)} tidak boleh kosong"
            else -> ""
        }

        if (errorMessage.isNotEmpty()) {
            this.createErrorToast(errorMessage)
            return false
        }
        return true
    }


    override fun attachBaseContext(newBase: Context) {
        App.component.inject(this)

        val language = pref.get(GlobalVariable.LocalHelper.toDescription())
        super.attachBaseContext(MyContextWrapper.wrap(newBase, Locale(language!!)))
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}

package com.kotlin.demo.ui

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import cn.pedant.SweetAlert.SweetAlertDialog
import com.kotlin.demo.R
import com.kotlin.demo.bean.LoginResponse
import com.kotlin.demo.bean.RegisterResponse
import com.kotlin.demo.presenter.LoginPresenter
import com.kotlin.demo.presenter.LoginPresenterImp
import com.kotlin.demo.view.LoginView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.regex.Pattern

class MainActivity : AppCompatActivity(), View.OnClickListener, LoginView {
    var loginPresenter: LoginPresenter? = null
    var dialog: SweetAlertDialog? = null

    override fun loginFail(str: String?) {
        dialog?.changeAlertType(SweetAlertDialog.ERROR_TYPE)
        dialog?.titleText = str
    }

    override fun loginSuccess(str: LoginResponse) {
        dialog?.changeAlertType(SweetAlertDialog.SUCCESS_TYPE)
        dialog?.titleText = str.msg
    }

    override fun registerFail(str: String?) {
        dialog?.changeAlertType(SweetAlertDialog.ERROR_TYPE)
        dialog?.titleText = str
    }

    override fun registerSuccess(str: RegisterResponse) {
        dialog?.changeAlertType(SweetAlertDialog.SUCCESS_TYPE)
        dialog?.titleText = str.msg
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.login ->
                if (checkContent(true)) {
                    dialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
                            .setTitleText("正在登录...")
                    dialog?.setCancelable(false)
                    dialog?.show()
                    loginPresenter?.login(username.text.toString(), password.text.toString())
                }

            R.id.register ->
                if (checkContent(false)) {
                    dialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
                    dialog?.titleText = "正在注册..."
                    dialog?.setCancelable(false)
                    dialog?.show()
                    loginPresenter?.register(username.text.toString(), password.text.toString(), email.text.toString())
                }
        // else -> Toast.makeText(this, "text", Toast.LENGTH_LONG).show()
        }
    }

    private fun checkContent(login: Boolean): Boolean {
        username.error = null
        password.error = null
        email.error = null

        var cancel = false
        var focusView: View? = null

        if (!login) {

            if (TextUtils.isEmpty(email.text)) {
                email.error = "请输入邮箱"
                focusView = email
                cancel = true
            }

            /*if (isEmail(email.text.toString())) {
                email.error = "请输入正确的邮箱"
                focusView = email
                cancel = true
            }*/
        }
        if (TextUtils.isEmpty(password.text)) {
            password.error = "请输入密码"
            focusView = password
            cancel = true
        }
        if (TextUtils.isEmpty(username.text)) {
            username.error = "请输入用户名"
            focusView = username
            cancel = true
        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            if (focusView != null) {
                focusView.requestFocus()
            }
        } else {
            return true
        }
        return false

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loginPresenter = LoginPresenterImp(this)
        login.setOnClickListener(this)
        register.setOnClickListener(this)
    }

    /**
     * 判断email地址
     * @param email
     * @return
     */
    fun isEmail(email: String?): Boolean {
        if (email == null) {
            return false
        }
        val regex = "\\w+(\\.\\w)*@\\w+(\\.\\w{2,3}){1,3}"
        val pattern = Pattern.compile(regex)
        return pattern.matcher(email).matches()
    }

}

package com.kotlin.demo.presenter

import com.kotlin.demo.bean.LoginResponse
import com.kotlin.demo.bean.RegisterResponse
import com.kotlin.demo.model.LoginModel
import com.kotlin.demo.model.LoginModelImpl
import com.kotlin.demo.view.LoginView

/**
 * Created by Mustang on 2019/2/14.
 */
class LoginPresenterImp(val loginView: LoginView) :LoginPresenter, LoginPresenter.loginListener, LoginPresenter.registerListener {

    var loginModel: LoginModel

    init {
        loginModel = LoginModelImpl()
    }
    override fun login(username: String, password: String) {
        loginModel.login(this,username,password)
    }

    override fun register(username: String, password: String, email: String) {
        loginModel.register(this,username,password,email)
    }

    override fun registerSuccess(str: RegisterResponse) {
        loginView.registerSuccess(str)
    }

    override fun registerFail(str: String?) {
        loginView.registerFail(str)
    }

    override fun loginSuccess(str: LoginResponse) {
        loginView.loginSuccess(str)
    }

    override fun loginFail(str: String?) {
        loginView.loginFail(str)
    }


}
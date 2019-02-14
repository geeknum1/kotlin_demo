package com.kotlin.demo.model

import com.kotlin.demo.presenter.LoginPresenter

/**
 * Created by Mustang on 2019/2/14.
 */
interface LoginModel {
    //login
    fun login(loginListener: LoginPresenter.loginListener, username: String, password: String)

    //register
    fun register(registerListener: LoginPresenter.registerListener, username: String, password: String, email: String)
}
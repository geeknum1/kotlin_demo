package com.kotlin.demo.presenter

import com.kotlin.demo.bean.LoginResponse
import com.kotlin.demo.bean.RegisterResponse

/**
 * Created by Mustang on 2019/2/14.
 */
interface LoginPresenter {
    fun login(username: String, password: String)
    fun register(username: String, password: String, email: String)
    interface loginListener {
        fun loginSuccess(str: LoginResponse)
        fun loginFail(str: String?)
    }

    interface registerListener {
        fun registerSuccess(str: RegisterResponse)
        fun registerFail(str: String?)
    }

}
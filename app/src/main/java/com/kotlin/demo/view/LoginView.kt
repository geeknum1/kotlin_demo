package com.kotlin.demo.view

import com.kotlin.demo.bean.LoginResponse
import com.kotlin.demo.bean.RegisterResponse

/**
 * Created by Mustang on 2019/2/14.
 */

interface LoginView {
    fun loginFail(str: String?)
    fun loginSuccess(str: LoginResponse)
    fun registerFail(str: String?)
    fun registerSuccess(str: RegisterResponse)
}
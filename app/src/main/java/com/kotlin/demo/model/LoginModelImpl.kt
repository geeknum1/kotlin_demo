package com.kotlin.demo.model

import com.kotlin.demo.presenter.LoginPresenter
import com.kotlin.demo.util.RetrofitUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Mustang on 2019/2/14.
 */
class LoginModelImpl : LoginModel {
    var onloginListener: LoginPresenter.loginListener? = null
    var onregisterListener: LoginPresenter.registerListener? = null

    override fun login(loginListener: LoginPresenter.loginListener, username: String, password: String) {
        if (onloginListener == null) {
            onloginListener = loginListener
        }
        RetrofitUtil
                .retrofitService
                .userLogin(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    when (result.retCode) {
                        200 -> onloginListener?.loginSuccess(result)
                        else -> onloginListener?.loginFail(result.msg)
                    }

                }, { error ->
                    onloginListener?.loginFail(error.message)

                }

                )

    }

    override fun register(registerListener: LoginPresenter.registerListener, username: String, password: String, email: String) {
        if (onregisterListener == null) {
            onregisterListener = registerListener
        }
        RetrofitUtil
                .retrofitService
                .userRegister(username, password, email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            result ->
                            when (result.retCode) {
                                200 ->
                                    onregisterListener?.registerSuccess(result)
                                else ->
                                    onregisterListener?.registerFail(result.msg)
                            }
                        },
                        {
                            error ->
                            onregisterListener?.registerFail(error.message)
                        })
    }
}
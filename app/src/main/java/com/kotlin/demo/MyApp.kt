package com.kotlin.demo

import android.app.Application

/**
 * Created by Mustang on 2019/2/14.
 */
class MyApp : Application() {

    companion object {
       private  var instance: Application? = null
        fun instance() = instance!!
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
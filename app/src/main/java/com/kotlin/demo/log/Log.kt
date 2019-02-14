package com.kotlin.demo.log

import android.util.Log

/**
 * Created by Mustang on 2019/2/14.
 */
fun loge(tag: String, content: String?) {
    Log.e(tag, content ?: tag)
}


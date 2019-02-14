package com.kotlin.demo.util

import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.kotlin.demo.BuildConfig
import com.kotlin.demo.MyApp
import com.kotlin.demo.api.RetrofitService
import com.kotlin.demo.constant.Constant
import com.kotlin.demo.log.loge
import com.kotlin.demo.ui.MainActivity
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Mustang on 2019/2/14.
 */
class RetrofitUtil {
    companion object {
        /**
         * 创建Retrofit
         */
        fun create(url: String): Retrofit {
            //日志显示级别
            val level: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BODY
            //新建log拦截器
            val loggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                message -> loge("RetrofitUtils", "OkHttp: " + message)
            })
            loggingInterceptor.level = level


            // okHttpClientBuilder
            val okHttpClientBuilder = OkHttpClient().newBuilder()

            okHttpClientBuilder.connectTimeout(60, TimeUnit.SECONDS)
            okHttpClientBuilder.readTimeout(10, TimeUnit.SECONDS)
            //OkHttp进行添加拦截器loggingInterceptor
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
            okHttpClientBuilder.addInterceptor(provideBeautyLoggingInterceptor())
            okHttpClientBuilder.addInterceptor(ChuckInterceptor(MyApp.instance()))

            return Retrofit.Builder()
                    .baseUrl(url)
                    .client(okHttpClientBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
        }

        fun provideBeautyLoggingInterceptor(): LoggingInterceptor = with(LoggingInterceptor.Builder()) {
            loggable(BuildConfig.DEBUG)
            setLevel(Level.BASIC)
            log(Platform.INFO)
            request("Request")
            response("Response")
            addHeader("Version", BuildConfig.VERSION_NAME)
            build()
        }

        val retrofitService: RetrofitService = RetrofitUtil.getService(Constant.REQUEST_BASE_URL, RetrofitService::class.java)

        /**
         * 获取ServiceApi
         */
        fun <T> getService(url: String, service: Class<T>): T {
            return create(url).create(service)
        }
    }


}
package com.example.root.mymvvmdemo

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class MyApplication : Application() {


    override fun onCreate() {
        super.onCreate()

        instance = this
        MyApplication.context = applicationContext
    }


    companion object {
        @get:Synchronized
        var instance: MyApplication? = null
            private set

        var context: Context? = null


        fun getAppContext(): Context {

            return MyApplication.context!!
        }


    }


}
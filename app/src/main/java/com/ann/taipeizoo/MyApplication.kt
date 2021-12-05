package com.ann.taipeizoo

import android.app.Application
import com.ann.taipeizoo.network.OkHttpManager
import kotlin.properties.Delegates

class MyApplication : Application() {
    companion object {
        var instance: MyApplication by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        OkHttpManager.Builder()
            .build()
    }
}

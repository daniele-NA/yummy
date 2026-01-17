package me.app.yummy.boot

import android.app.Application
import android.content.Context
import me.app.yummy.di.initKoin
import org.koin.android.ext.koin.androidContext

// == EntryPoint for Android App == //
class YummyApp : Application() {

    companion object{@JvmStatic internal lateinit var appContext: Context}

    // == Initialization of Koin Dependency Injection == //
    override fun onCreate() {
        super.onCreate()
        appContext=this
        initKoin {
            androidContext(this@YummyApp)
        }
    }

}
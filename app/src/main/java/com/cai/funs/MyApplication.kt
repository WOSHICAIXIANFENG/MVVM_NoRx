package com.cai.funs

import android.app.Application
import com.cai.funs.di.myModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

  override fun onCreate() {
    super.onCreate()

    // start koin
    startKoin {
      // Android context
      androidContext(this@MyApplication)
      // modules
      modules(myModule)
    }
  }
}
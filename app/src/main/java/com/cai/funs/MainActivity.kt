package com.cai.funs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.cai.funs.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main_activity)

    if (savedInstanceState == null) {
      supportFragmentManager.beginTransaction()
        .replace(R.id.container, MainFragment.newInstance())
        .commitNow()
    }
  }

  override fun onPause() {
    super.onPause()
    Log.d("MainActivity", "onPause()")
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    Log.d("MainActivity", "onSaveInstanceState()")
  }

  override fun onStop() {
    super.onStop()
    Log.d("MainActivity", "onStop()")
  }

  override fun onDestroy() {
    super.onDestroy()
    Log.d("MainActivity", "onDestroy()")
  }
}

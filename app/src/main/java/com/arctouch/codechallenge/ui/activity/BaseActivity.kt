package com.arctouch.codechallenge.ui.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

  protected fun configToolbar(toolbar: Toolbar, title: String, showDisplayHomeEnable: Boolean = false) {
    toolbar.title = title
    setSupportActionBar(toolbar)
    if (showDisplayHomeEnable) {
      supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
  }
}

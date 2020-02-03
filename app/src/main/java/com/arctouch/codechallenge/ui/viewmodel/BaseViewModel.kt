package com.arctouch.codechallenge.ui.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

  private val disposable = CompositeDisposable()

  override fun onCleared() {
    super.onCleared()
    disposable.clear()
  }
}

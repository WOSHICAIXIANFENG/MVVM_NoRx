package com.cai.funs.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cai.funs.interactor.MuseumCallBack
import com.cai.funs.interactor.MuseumInteractor
import org.koin.core.KoinComponent
import org.koin.core.inject

class MainViewModel : ViewModel(), KoinComponent {
  private val interactor: MuseumInteractor by inject()

  private val _museums = MutableLiveData<List<Museum>>().apply { value = emptyList() }
  val museums: LiveData<List<Museum>> = _museums

  private val _showEmptyTip = MutableLiveData<Boolean>()
  val showEmptyTip = _showEmptyTip

  private val _showErrorTip = MutableLiveData<Boolean>()
  val showErrorTip = _showErrorTip

  private val _showLoading = MutableLiveData<Boolean>()
  val showLoading = _showLoading

  // If you want the data only be loaded once, you can put loadMuseums() here, when the screen rotated, the service will not be called
  init {
    loadMuseums()
  }

  override fun onCleared() {
    super.onCleared()
  }

  fun loadMuseums() {
    // loadMockData()
    loadData()
  }

  private fun loadData() {
    _showLoading.value = true

    interactor.retrieveMuseums(object : MuseumCallBack{
      override fun onFail() {
        _showLoading.value = false
        _showErrorTip.value = true
      }

      override fun onSuccess(museums: List<Museum>) {
        _showLoading.value = false
        _showErrorTip.value = false

        if (museums.isEmpty())
          _showEmptyTip.value = true
        else {
          _showEmptyTip.value = false
          _museums.value = museums
        }
      }
    })
  }

  private fun loadMockData() {
    _museums.value = listOf(
      Museum(
        1,
        "Test 1",
        "http://museos.cultura.pe/sites/default/files/styles/museos_portada/public/museos/imagen/rnm_1493925752.jpg"
      ),
      Museum(
        2,
        "Test 2",
        "http://museos.cultura.pe/sites/default/files/styles/museos_portada/public/museos/imagen/rnm_1493925752.jpg"
      )
    )
  }
}

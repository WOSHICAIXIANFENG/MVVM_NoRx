package com.cai.funs.interactor

import com.cai.funs.model.MuseumData
import com.cai.funs.network.MuseumEntity
import com.cai.funs.network.MuseumResponse
import com.cai.funs.network.MuseumService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface MuseumCallBack {
  fun onSuccess(museums: List<MuseumData>)
  fun onFail()
}

interface MuseumInteractor {
  fun retrieveMuseums(callBack: MuseumCallBack)
}

class MuseumInteractorImpl(val museumService: MuseumService) : MuseumInteractor {

  override fun retrieveMuseums(callBack: MuseumCallBack) {
    // need enqueue fun to do async request
    museumService.museums().enqueue(object : Callback<MuseumResponse> {
      override fun onResponse(call: Call<MuseumResponse>, response: Response<MuseumResponse>) {
        if (response.isSuccessful) {
          response.body()?.let {
            callBack.onSuccess(it.data?.map { it.toClientModel() } ?: emptyList<MuseumData>())
          }
        } else {
          callBack.onFail()
        }
      }

      override fun onFailure(call: Call<MuseumResponse>, t: Throwable) {
        callBack.onFail()
      }
    })
  }

  private fun MuseumEntity.toClientModel(): MuseumData = MuseumData(
    id = this.id,
    name = this.name,
    imgUrl = this.img
  )
}
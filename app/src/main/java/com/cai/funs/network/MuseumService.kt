package com.cai.funs.network

import retrofit2.Call
import retrofit2.http.GET

interface MuseumService {
  @GET("/api/museums/")
  fun museums(): Call<MuseumResponse>
}
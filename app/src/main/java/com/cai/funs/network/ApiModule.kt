package com.cai.funs.network

import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
  single(createdAtStart = true) { get<Retrofit>().create(MuseumService::class.java) }
}
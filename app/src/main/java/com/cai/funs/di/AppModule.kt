package com.cai.funs.di

import com.cai.funs.interactor.MuseumInteractor
import com.cai.funs.interactor.MuseumInteractorImpl
import com.cai.funs.network.MuseumService
import com.cai.funs.network.apiModule
import com.cai.funs.network.networkModule
import org.koin.dsl.module

val myModule = module {
  factory<MuseumInteractor> { MuseumInteractorImpl(get<MuseumService>()) }

} + networkModule + apiModule
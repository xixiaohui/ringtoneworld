package com.xxh.ringtone.world.di.module

import com.xxh.ringtone.world.data.viewmodel.PlaylistViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val AppModule = module {

    viewModel { PlaylistViewModel(get(), get(), get()) }


}
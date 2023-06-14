package com.example.myapplication

import com.example.myapplication.MainActivity.Companion.BASE_URL
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val appModule = module {

    single {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RetrofitCall::class.java)
    }

    single {
        MoviesRepo(get())
    }

    viewModel {
        MoviesViewModel(get())
    }

}
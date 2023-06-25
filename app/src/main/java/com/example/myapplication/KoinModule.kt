package com.example.myapplication

import androidx.room.Room
import com.example.myapplication.MainActivity.Companion.BASE_URL
import com.example.myapplication.roomdatabase.MovieResultsDao
import com.example.myapplication.roomdatabase.NowShowingDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.scope.get
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

//    single {
//        Room.databaseBuilder(
//            androidContext(),
//            NowShowingDatabase::class.java,
//            "movie_results",
//        ).build()
//    }
//
//    single{
//        get<NowShowingDatabase>().movieResultDao()
//    }

    single {
        MoviesRepo(get(),androidContext())
    }

    viewModel {
        MoviesViewModel(get())
    }

}
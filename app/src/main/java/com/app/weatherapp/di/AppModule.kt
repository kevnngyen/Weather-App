package com.app.weatherapp.di

import com.app.weatherapp.data.Api
import com.app.weatherapp.data.ProductRepo
import com.app.weatherapp.data.ProductRepoImpl
import com.app.weatherapp.presentation.ProductViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {


    // Provide Retrofit
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(Api.BASE_URL)  // BASE_URL from your Api object
            .addConverterFactory(GsonConverterFactory.create())  // You can change the converter if needed
            .build()
    }

    // Provide Api instance
    single<Api> {
        get<Retrofit>().create(Api::class.java)
    }

    // Provide ProductRepo
    single<ProductRepo> { ProductRepoImpl(get()) }

    // Provide ViewModel
    viewModel { ProductViewModel(get()) }


}
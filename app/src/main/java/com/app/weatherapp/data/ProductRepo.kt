package com.app.weatherapp.data

import com.app.weatherapp.data.model.product
import kotlinx.coroutines.flow.Flow


interface ProductRepo {

    suspend fun getProductList(city: String): Flow<Result<product>>


}
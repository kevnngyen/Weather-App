package com.app.weatherapp.data

import android.util.Log
import com.app.weatherapp.data.model.product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

// a flow is a sequence of things that can happen

class ProductRepoImpl (private val api: Api) : ProductRepo {


    override suspend fun getProductList(city : String): Flow<Result<product>> {
        return flow {
            // Getting the products from the API
            val productsFromApi = try {
                val apiData = api.getProductsList(city)  // Call the API
                // Log the weather data
                Log.d("WeatherRepo", "Weather data: $apiData")  // Log the result
                apiData  // Return the result from the try block
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading products from IO"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading productions from Http"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading productions from Exception"))
                return@flow
            }

            // Emit the success result
            emit(Result.Success(productsFromApi))

        }

    }

}
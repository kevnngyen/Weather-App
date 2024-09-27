package com.app.weatherapp.data

import androidx.core.app.NotificationCompat.MessagingStyle.Message
import kotlin.Result

/*
* this class gets the result of our api call
* we want to get the result and that will be generic T
* in case it succeeds we pass the product
* in case if we fail we pass an error
*
*/

sealed class Result<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T> (data: T?): com.app.weatherapp.data.Result<T>(data) //case Success
    class Error<T>(data: T? = null, message: String): com.app.weatherapp.data.Result<T>(data,message) //Case error

}
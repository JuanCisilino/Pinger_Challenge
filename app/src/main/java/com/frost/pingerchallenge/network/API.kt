package com.frost.pingerchallenge.network

import com.frost.pingerchallenge.data.Endpoints
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import retrofit2.http.GET

interface API {

    @GET(Endpoints.APACHE_LOG)
    suspend fun downloadApacheLogStream(): ResponseBody

}
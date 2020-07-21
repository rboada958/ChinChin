package com.chinchinapp.chinchin.app.network.api

import com.chinchinapp.chinchin.app.models.CryptoResponse
import retrofit2.http.GET

interface CryptoApi {

    @GET("/exchange-api/v1/public/asset-service/product/get-products")
    suspend fun getCrypto() : CryptoResponse

}
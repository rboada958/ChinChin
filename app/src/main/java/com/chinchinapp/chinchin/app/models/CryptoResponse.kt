package com.chinchinapp.chinchin.app.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CryptoResponse(

    @Json(name = "code")
    val code: String? = null,

    @Json(name = "data")
    val data: List<DataItem?>? = null,

    @Json(name = "success")
    val success: Boolean? = null,

    @Json(name = "message")
    val message: String? = null,

    @Json(name = "messageDetail")
    val messageDetail: String? = null
)

package com.chinchinapp.chinchin.app.models

import android.graphics.drawable.Drawable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CryptosItem(

    @Json(name = "id")
    val uid: String? = null,

    @Json(name = "name")
    val name: String? = null,

    @Json(name = "icon")
    val icon: Drawable? = null
)
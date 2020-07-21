package com.chinchinapp.chinchin.app.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DataItem(

    @Json(name = "st")
    val st: String? = null,

    @Json(name = "b")
    val B: String? = null,

    @Json(name = "c")
    val C: Double? = null,

    @Json(name = "qv")
    val qv: Double? = null,

    @Json(name = "h")
    val H: Double? = null,

    @Json(name = "i")
    val I: Double? = null,

    @Json(name = "l")
    val L: Double? = null,

    @Json(name = "an")
    val an: String? = null,

    @Json(name = "o")
    val O: Double? = null,

    @Json(name = "qa")
    val qa: String? = null,

    @Json(name = "cs")
    val cs: Long? = null,

    @Json(name = "q")
    val Q: String? = null,

    @Json(name = "as")
    val ass: Double? = null,

    @Json(name = "s")
    val S: String? = null,

    @Json(name = "v")
    val V: Double? = null,

    @Json(name = "etf")
    val etf: Boolean? = null,

    @Json(name = "y")
    val Y: Double? = null,

    @Json(name = "qn")
    val qn: String? = null,

    @Json(name = "pm")
    val pm: String? = null,

    @Json(name = "pn")
    val pn: String? = null,

    @Json(name = "ba")
    val ba: String? = null,

    @Json(name = "ts")
    val ts: Double? = null
)
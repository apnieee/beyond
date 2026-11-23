package com.apni.beyond.data.model

import com.google.gson.annotations.SerializedName

data class ApodResponse(
    val date: String?,
    val explanation: String?,
    val title: String?,
    val url: String?,

    @SerializedName("hdurl")
    val hdUrl: String?,

    @SerializedName("media_type")
    val mediaType: String?
)

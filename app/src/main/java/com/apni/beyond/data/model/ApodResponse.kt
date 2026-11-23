package com.apni.beyond.data.model

import com.google.gson.annotations.SerializedName

data class ApodResponse(
    val date: String?,
    val title: String?,

    @SerializedName("post_id")
    val postId: Int?,

    val permalink: String?,

    @SerializedName("media_type")
    val mediaType: String?,

    val explanation: String?,
    val url: String?,

    @SerializedName("hdurl")
    val hdUrl: String?
)
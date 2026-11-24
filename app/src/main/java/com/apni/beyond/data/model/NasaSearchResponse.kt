package com.apni.beyond.data.model

import com.google.gson.annotations.SerializedName

data class NasaSearchResponse(
    val collection: NasaCollection?
)

data class NasaCollection(
    val items: List<NasaItem>?
)

data class NasaItem(
    val data: List<NasaItemData>?,
    val links: List<NasaItemLink>?
)

data class NasaItemData(
    val title: String?,
    val description: String?,

    @SerializedName("date_created")
    val dateCreated: String?,

    val nasa_id: String?
)

data class NasaItemLink(
    val href: String?,
    val rel: String?,
    val render: String?
)
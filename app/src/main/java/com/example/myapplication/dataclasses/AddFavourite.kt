package com.example.myapplication.dataclasses

import com.google.gson.annotations.SerializedName

data class AddFavourite(

    @SerializedName("media_type" ) var mediaType : String?  = null,
    @SerializedName("media_id"   ) var mediaId   : Int?     = null,
    @SerializedName("favorite"   ) var favorite  : Boolean? = null

)

data class FavoriteResponse(

    @SerializedName("success"        ) var success       : Boolean? = null,
    @SerializedName("status_code"    ) var statusCode    : Int?     = null,
    @SerializedName("status_message" ) var statusMessage : String?  = null

)

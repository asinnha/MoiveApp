package com.example.myapplication.dataclasses

import com.google.gson.annotations.SerializedName

data class CreateRequestToken(
    @SerializedName("success"       ) var success      : Boolean? = null,
    @SerializedName("expires_at"    ) var expiresAt    : String?  = null,
    @SerializedName("request_token" ) var requestToken : String?  = null
)

data class LoginRequestBody(
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String,
    @SerializedName("request_token") val requestToken: String
)

data class SessionRequestBody(
    @SerializedName("request_token") val requestToken: String
)

data class SessionResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("session_id") val sessionId: String
)
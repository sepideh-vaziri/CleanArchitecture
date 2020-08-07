package com.architecture.data.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class AuthorizationEntity : Serializable {

    companion object {
        private const val GRANT_TYPE = "grant_type"
        private const val GRANT_TYPE_PASSWORD = "code"
        private const val GRANT_TYPE_REFRESH = "refresh_token"

        private const val CLIENT_ID = "client_id"
        private const val CLIENT_ID_VALUE = "test_client_id"

        private const val CLIENT_SECRET = "client_secret"
        private const val CLIENT_SECRET_VALUE = "some_secret"

        private const val USERNAME = "phoneNumber"
        private const val PASSWORD = "code"

        private const val REFRESH_TOKEN = "refresh_token"
    }

    @SerializedName(GRANT_TYPE)
    private var mGrantType: String? = null
    @SerializedName(CLIENT_ID)
    private var mClientId: String? = null
    @SerializedName(CLIENT_SECRET)
    private var mClientSecret: String? = null
    @SerializedName(USERNAME)
    private var mUserName: String? = null
    @SerializedName(PASSWORD)
    private var mPassword: String? = null
    @SerializedName(REFRESH_TOKEN)
    private var mRefreshToken: String? = null


    constructor(userName: String, password: String) {
        mGrantType = GRANT_TYPE_PASSWORD
        mClientId = CLIENT_ID_VALUE
        mClientSecret = CLIENT_SECRET_VALUE
        mUserName = userName
        mPassword = password
    }

    constructor(refreshToken: String) {
        mGrantType = GRANT_TYPE_REFRESH
        mClientId = CLIENT_ID_VALUE
        mClientSecret = CLIENT_SECRET_VALUE
        mRefreshToken = refreshToken
    }
}
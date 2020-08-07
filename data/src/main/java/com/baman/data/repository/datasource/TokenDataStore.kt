package com.baman.data.repository.datasource

import android.content.SharedPreferences
import com.baman.data.entity.server.ServerResponseEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenDataStore @Inject constructor(sharedPreferences: SharedPreferences) {

    companion object {

        private const val TOKEN = "access_token"
        private const val TOKEN_TYPE = "token_type"
        private const val REFRESH_TOKEN = "refresh_token"

    }

    private val mSharedPreferences = sharedPreferences

    //**********************************************************************************************
    /**
     * The method set login info on sharedPreferences to future usage
     * @param tokenEntity
     */
    internal fun saveTokenInfo(serverResponseEntity: ServerResponseEntity<String>) {
        if (serverResponseEntity.data == null) {
            return
        }

        val editor = mSharedPreferences.edit()

        editor.putString(TOKEN, serverResponseEntity.data)
        editor.putString(TOKEN_TYPE, "Bearer")
        editor.apply()
    }

    /**
     * The method set login info on sharedPreferences to future usage
     * @param tokenEntity
     */
    internal fun saveAuthenticateInfo(token: String?) {
        if (token == null) {
            return
        }

        val editor = mSharedPreferences.edit()

        editor.putString(TOKEN, token)
        editor.putString(TOKEN_TYPE, "Bearer")
        editor.apply()

    }

    //Get methods **********************************************************************************
    /**
     * The method return the user token
     * @return String: User token
     */
    internal fun getToken(): String? {
        return mSharedPreferences.getString(TOKEN, null)
    }

    /**
     * The method return the user token category
     * @return String: TokenEntity category
     */
    internal fun getTokenType(): String? {
        return "Bearer"
//        return mSharedPreferences.getString(TOKEN_TYPE, null)
    }

    /**
     * The method return the refresh token
     * @return String: Refresh token
     */
    internal fun getRefreshToken(): String? {
        return mSharedPreferences.getString(REFRESH_TOKEN, "")
    }

    /**
     * The method checks the token exists and has been expired or not
     */
    internal fun hasValidToken(): Boolean {
        return mSharedPreferences.contains(TOKEN)
//        return mSharedPreferences.contains(TOKEN) &&
//                (mSharedPreferences.getLong(TOKEN_EXPIRE, 0) > System.currentTimeMillis())
    }

    //Remove login info ****************************************************************************
    /**
     * The method remove login info from the sharedPreferences
     */
    internal fun removeLoginInfo() {

        val editor = mSharedPreferences.edit()
        editor.remove(TOKEN)
        editor.remove(TOKEN_TYPE)
        editor.remove(REFRESH_TOKEN)
        editor.remove(TOKEN)
        editor.apply()
    }

}
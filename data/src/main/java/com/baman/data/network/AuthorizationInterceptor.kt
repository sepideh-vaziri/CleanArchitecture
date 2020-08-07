package com.baman.data.network

import android.content.Context
import android.content.Intent
import com.baman.data.BuildConfig
import com.baman.data.entity.AuthorizationEntity
import com.baman.data.network.di.ApiServiceModule
import com.baman.data.repository.datasource.TokenDataStore
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * This class adds authorization header to a request and if request gets 401
 * sends refresh token request to the server and gets new token.
 */
class AuthorizationInterceptor @Inject constructor(
    context: Context,
    tokenDataStore: TokenDataStore
) : Authenticator {

    companion object {
        private val TAG: String = AuthorizationInterceptor::class.java.simpleName

        const val AUTHORIZATION: String = "Authorization"

        //Use application id for this string
        const val BROADCAST_UNAUTHORIZED = "com.baman.example.broadcast.unauthorized.401"
    }

    private val mContext: Context = context

    private val mTokenRepository = tokenDataStore

    /**
     * AuthorizationInterceptor method adds authorization header to a request.
     * @param chain
     * @return
     * @throws IOException
     */
    @Throws(IOException::class)
    fun interceptor(chain: okhttp3.Interceptor.Chain): Response {

        val originalRequest = chain.request()

        synchronized(mTokenRepository) {

            if (!mTokenRepository.hasValidToken()) {

                if (!refreshToken()) {

                    val intent = Intent(BROADCAST_UNAUTHORIZED)
                    mContext.sendBroadcast(intent)

                    return chain.proceed(originalRequest)

                }

            }

        }

        val token = mTokenRepository.getTokenType() + " " + mTokenRepository.getToken()

        val request = originalRequest.newBuilder()
                .header(AUTHORIZATION, token)
                .method(originalRequest.method(), originalRequest.body())
                .build()

        return chain.proceed(request)

    }

    /**
     * Authenticate method calls if response code is 401 and send refresh token request to the server for getting
     * new token.
     * @param route
     * @param response
     * @return
     * @throws IOException
     */
    @Throws(IOException::class)
    override fun authenticate(route: Route?, response: Response): Request? {

        if (response.request().header(AUTHORIZATION) != null && response.request().header(AUTHORIZATION)!!.isNotEmpty()) {

            synchronized(mTokenRepository) {

                val token = mTokenRepository.getTokenType() + " " + mTokenRepository.getToken()

                if (token == response.request().header(AUTHORIZATION)) {

                    Timber.tag(TAG).d("response= ${response.code()} token= $token")
                    if (!refreshToken()) {

                        val intent = Intent(BROADCAST_UNAUTHORIZED)
                        mContext.sendBroadcast(intent)

                        return null

                    }

                }

            }

            val token = mTokenRepository.getTokenType() + " " + mTokenRepository.getToken()

            return response.request().newBuilder()
                    .header(AUTHORIZATION, token)
                    .build()

        }
        else {
            val intent = Intent(BROADCAST_UNAUTHORIZED)
            mContext.sendBroadcast(intent)
        }

        return null
    }

    //Todo
    @Throws(IOException::class)
    private fun refreshToken(): Boolean {

        val apiService = getApiService()

        val tokenCall = apiService.refreshToken(AuthorizationEntity(mTokenRepository.getRefreshToken()!!))
        val refreshTokenResponse = tokenCall.execute()

        if (refreshTokenResponse.isSuccessful) {

            val serverResponseEntity = refreshTokenResponse.body()

            return if (serverResponseEntity?.data != null) {
                mTokenRepository.saveTokenInfo(serverResponseEntity)

                true
            }
            else {
                Timber.tag(TAG).e("authenticator: refresh token failed. TokenEntity object is failed.")

                false
            }

        }
        else {
            Timber.tag(TAG).e("authenticator: refresh token failed. response code is: %s", refreshTokenResponse.code())
            Timber.tag(TAG).e("authenticator: message is: %s", refreshTokenResponse.message())

            return false
        }

    }

    private fun getApiService() : ApiService {
        return Retrofit.Builder()
            .baseUrl(ServerUrl.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(createOkHttpClient())
            .build()
            .create(ApiService::class.java)
    }

    private fun createOkHttpClient() : OkHttpClient {

        val httpClientBuilder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        if (BuildConfig.DEBUG) {
            httpClientBuilder.addInterceptor(loggingInterceptor)
        }

        httpClientBuilder.readTimeout(ApiServiceModule.REQUEST_TIME_OUT, TimeUnit.SECONDS)
        httpClientBuilder.connectTimeout(ApiServiceModule.REQUEST_TIME_OUT, TimeUnit.SECONDS)

        return httpClientBuilder.build()
    }

}
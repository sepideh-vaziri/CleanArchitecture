package com.baman.example.view.activity.base

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.baman.data.network.AuthorizationInterceptor
import com.baman.data.network.NetworkStateReceiver
import com.baman.example.R
import com.baman.example.base.addFragment
import com.baman.example.base.replaceFragment
import retrofit2.HttpException
import timber.log.Timber


/**
 * BaseActivity contains some base code.
 */
abstract class BaseActivity : AppCompatActivity(),
    NetworkStateReceiver.NetworkStateReceiverListener {


    companion object {
        private const val TAG = "BaseActivity"

        private const val HTTP_ERROR_BAD_REQUEST = 400
        private const val HTTP_ERROR_UNAUTHORIZED = 401
        private const val HTTP_ERROR_FORBIDDEN = 403
        private const val HTTP_ERROR_NOT_FOUND = 404
        private const val HTTP_ERROR_CONFLICT = 409
        private const val HTTP_ERROR_INTERNAL_SERVER_ERROR = 500
        private const val HTTP_ERROR_BAD_GATEWAY = 502
        private const val HTTP_ERROR_SERVICE_UNAVAILABLE = 503

    }

    private lateinit var mHolderContent: FrameLayout
    private lateinit var mTextViewNetworkErrorMessage: TextView

    private lateinit var mNetworkStateReceiver: NetworkStateReceiver      // Receiver that detects network state changes

    private lateinit var mNetworkStateCallback: NetworkStateCallback

    private var mIsShowNetworkAvailabilityMessage = true

    //Lifecycle ************************************************************************************
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.activity_base)

        //Find view
        mHolderContent = findViewById(R.id.layout_content)
        mTextViewNetworkErrorMessage = findViewById(R.id.text_no_network_available)

    }

    override fun onResume() {
        super.onResume()

        //Register broad cast receiver for get un authorization
        val intentFilter = IntentFilter()
        intentFilter.addAction(AuthorizationInterceptor.BROADCAST_UNAUTHORIZED)
        registerReceiver(mUnAuthorizedBroadcast, intentFilter)

        //Register for get network state
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            mNetworkStateReceiver = NetworkStateReceiver(this)
            mNetworkStateReceiver.addListener(this)
        } else {
            mNetworkStateCallback =
                NetworkStateCallback(
                    this::networkAvailable,
                    this::networkUnavailable
                )
        }

        registerNetworkBroadcastReceiver(this)
    }

    override fun onPause() {
        unregisterReceiver(mUnAuthorizedBroadcast)

        unregisterNetworkBroadcastReceiver(this)

        super.onPause()
    }

    //Set content view *****************************************************************************
    override fun setContentView(layoutResID: Int) {
        mHolderContent.removeAllViews()

        layoutInflater.inflate(layoutResID, mHolderContent, true)
    }

    override fun setContentView(view: View) {
        mHolderContent.removeAllViews()

        mHolderContent.addView(view)
    }

    //**********************************************************************************************
    open fun getParentView(): ViewGroup {
        return mHolderContent
    }

    //**********************************************************************************************
    private val mUnAuthorizedBroadcast = object: BroadcastReceiver() {

        override fun onReceive(p0: Context?, p1: Intent?) {
            logOut()
        }

    }

    //Handle network error *************************************************************************
    /**
     * This method based on [HttpException] code (HTTP status codes) show a message to the user.
     * @param throwable: Server exception
     */
    protected fun showNetworkErrorMessage(throwable: Throwable) {

        if (throwable is HttpException) {
            //Todo complete
            when (throwable.code()) {
                HTTP_ERROR_BAD_REQUEST -> {
                }

                HTTP_ERROR_UNAUTHORIZED -> {
                }

                HTTP_ERROR_FORBIDDEN -> {
                }

                HTTP_ERROR_NOT_FOUND -> {
                }

                HTTP_ERROR_CONFLICT -> {
                }

                HTTP_ERROR_INTERNAL_SERVER_ERROR -> {
                }

                HTTP_ERROR_BAD_GATEWAY -> {
                }

                HTTP_ERROR_SERVICE_UNAVAILABLE -> {
                }

                else -> {
                }

            }

        }

    }

    //**********************************************************************************************
    override fun networkAvailable() {
        runOnUiThread {
            mTextViewNetworkErrorMessage.visibility = View.INVISIBLE
        }
    }

    override fun networkUnavailable() {
        if (mIsShowNetworkAvailabilityMessage) {

            runOnUiThread {
                mTextViewNetworkErrorMessage.visibility = View.VISIBLE
            }

        }
    }

    //**********************************************************************************************
    /**
     * Register the NetworkStateReceiver with your activity
     * @param currentContext
     */
    private fun registerNetworkBroadcastReceiver(currentContext: Context) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            currentContext.registerReceiver(
                mNetworkStateReceiver,
                IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
            )
        } else {
            val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            if (!isNetworkAvailable(currentContext)) {
                networkUnavailable()
            }

            val networkRequest: NetworkRequest = NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .build()

            connectivityManager.registerNetworkCallback(networkRequest, mNetworkStateCallback)
        }

    }

    /**
     * Unregister the NetworkStateReceiver with your activity
     * @param currentContext
     */
    private fun unregisterNetworkBroadcastReceiver(currentContext: Context) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            currentContext.unregisterReceiver(mNetworkStateReceiver)
        } else {
            val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            connectivityManager.unregisterNetworkCallback(mNetworkStateCallback)
        }

    }

    //Network callback listener ********************************************************************
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private class NetworkStateCallback(networkAvailable: () -> Unit, networkUnavailable: () -> Unit) :
        ConnectivityManager.NetworkCallback() {

        private val mNetworkAvailable = networkAvailable
        private val mNetworkUnavailable = networkUnavailable

        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            mNetworkAvailable()
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            mNetworkUnavailable()
        }

    }

    //Log out **************************************************************************************
    /**
     * This method removes all user data and token
     */
    internal fun logOut() {
        //Todo complete
        Timber.tag(TAG).d("logOut: ")
    }

    //**********************************************************************************************
    /**
     * Checking if the network is available
     */
    private fun isNetworkAvailable(context: Context): Boolean {

        var result = false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            connectivityManager.run {
                getNetworkCapabilities(activeNetwork)?.run {
                    result = when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                        else -> false
                    }
                }
            }

        }
        else {
            connectivityManager.run {
                activeNetworkInfo?.run {
                    if (type == ConnectivityManager.TYPE_WIFI) {
                        result = true
                    } else if (type == ConnectivityManager.TYPE_MOBILE) {
                        result = true
                    }
                }
            }
        }

        return result
    }

    //**********************************************************************************************
    /**
     * Determine that network not available message show in this page or not.
     */
    protected fun setIsShowNetworkAvailabilityMessage(isShowNetworkAvailabilityMessage: Boolean) {
        mIsShowNetworkAvailabilityMessage = isShowNetworkAvailabilityMessage
    }

    //**********************************************************************************************
    protected fun addFragment(
        @IdRes containerViewId: Int, fragment: Fragment,
        addToBackStack: Boolean = false
    ) {
        supportFragmentManager.addFragment(containerViewId, fragment, addToBackStack)
    }

    //**********************************************************************************************
    internal fun replaceFragment(
        @IdRes containerViewId: Int,
        fragment: Fragment,
        addToBackStack: Boolean = false
    ) {
        supportFragmentManager.replaceFragment(containerViewId, fragment, addToBackStack)
    }

    //**********************************************************************************************
    internal fun hideKeyboard() {
        val imm: InputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view: View? = currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(this)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}
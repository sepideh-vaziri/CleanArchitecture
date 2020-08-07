package com.baman.data.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import timber.log.Timber

/**
 * NetworkStateReceiver defines a BroadcastReceiver which allows us to register for system (i.e. network status) or application events.
 * All registered receivers for an event are notified by the Android runtime once this event happens.
 * Source: http://stackoverflow.com/questions/6169059/android-event-for-internet-connectivity-state-change
 */
class NetworkStateReceiver (context: Context) : BroadcastReceiver() {

    companion object {
        private const val TAG = "NetworkStateReceiver"
    }

    /**
     * Inner Interface (i.e. to encapsulate behavior in a generic and re-usable way) which handles connection state changes for classes which registered this receiver (Outer class NetworkStateReceiver)
     * This interface implements the 'Strategy Pattern', where an execution strategy is evaluated and applied internally at runtime
     */
    interface NetworkStateReceiverListener {
        /**
         * When the connection state is changed and there is a connection, this method is called
         */
        fun networkAvailable()

        /**
         * Connection state is changed and there is not a connection, this method is called
         */
        fun networkUnavailable()
    }

    private var mListenerList: MutableList<NetworkStateReceiverListener> = ArrayList()

    private var mIsConnected: Boolean? = null

    init {
        mIsConnected = isConnected(context)
    }

    /**
     * Called when the BroadcastReceiver is receiving an Intent broadcast (event for which the broadcast receiver has registered occurs).
     * During this time you can use the other methods on BroadcastReceiver to view/modify the current result values.
     * NOTE: When it runs on the main thread you should never perform long-running operations in it (there is a timeout of 10 seconds that the system allows before considering the receiver to be blocked and a candidate to be killed).
     * NOTE: You cannot launch a popup dialog in your implementation of onReceive().
     * @param context   Object to access additional information or to start services or activities
     * @param intent    Object with action used to register your receiver. This object contains additional information (e.g. extras)
     */
    override fun onReceive(context: Context, intent: Intent?) {
        Timber.tag(TAG).i("Intent broadcast received")

        if (intent == null || intent.extras == null) return

        if (isConnected(context)) {
            mIsConnected = true
        }
        else if (intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, java.lang.Boolean.FALSE)) {    //Boolean that indicates whether there is a complete lack of connectivity
            mIsConnected = false
        }

        notifyStateToAll()
    }

    /**
     * Check network state and
     * @param context
     */
    private fun isConnected(context: Context): Boolean {
        var result = false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

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
                    }
                    else if (type == ConnectivityManager.TYPE_MOBILE) {
                        result = true
                    }
                }
            }
        }

        return result
    }

    /**
     * Notify the state to all needed methods
     */
    private fun notifyStateToAll() {
        Timber.tag(TAG).i("Notifying state to %s listener(s)", mListenerList.size)

        for (eachNetworkStateReceiverListener in mListenerList) {
            notifyState(eachNetworkStateReceiverListener)
        }

    }

    /**
     * Notify the network state, triggering interface functions based on the current state
     * @param networkStateReceiverListener  Object which implements the NetworkStateReceiverListener interface
     */
    private fun notifyState(networkStateReceiverListener: NetworkStateReceiverListener?) {
        if (mIsConnected == null || networkStateReceiverListener == null) return

        if (mIsConnected == true) {
            // Triggering function on the interface towards network availability
            networkStateReceiverListener.networkAvailable()
        }
        else {
            // Triggering function on the interface towards network being unavailable
            networkStateReceiverListener.networkUnavailable()
        }

    }

    /**
     * Adds a listener to the list so that it will receive connection state change updates
     * @param networkStateReceiverListener     Object which implements the NetworkStateReceiverListener interface
     */
    fun addListener(networkStateReceiverListener: NetworkStateReceiverListener) {
        mListenerList.add(networkStateReceiverListener)
        notifyState(networkStateReceiverListener)
    }

    /**
     * Removes listener (when no longer necessary) from the list so that it will no longer receive connection state change updates
     * @param networkStateReceiverListener     Object which implements the NetworkStateReceiverListener interface
     */
    fun removeListener(networkStateReceiverListener: NetworkStateReceiverListener) {
        mListenerList.remove(networkStateReceiverListener)
    }

}
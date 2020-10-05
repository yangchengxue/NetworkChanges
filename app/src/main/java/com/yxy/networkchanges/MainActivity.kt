package com.yxy.networkchanges

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.yxy.library.NetworkMonitorManager
import com.yxy.library.enums.NetworkState
import com.yxy.library.interfaces.NetworkMonitor
import com.yxy.library.util.NetworkStateUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        NetworkMonitorManager.getInstance().register(this)

        btn_action_1?.setOnClickListener {
            val hasNetworkCapability: Boolean = NetworkStateUtils.hasNetworkCapability(applicationContext)
            Toast.makeText(applicationContext, if (hasNetworkCapability) "当前有网络" else "当前无网络", Toast.LENGTH_SHORT).show()
        }
        btn_action_2?.setOnClickListener {
            val networkState: NetworkState = NetworkStateUtils.getNetworkState(applicationContext)
            Toast.makeText(applicationContext, "当前网络类型：$networkState", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        NetworkMonitorManager.getInstance().unregister(this)
        super.onDestroy()
    }

    @NetworkMonitor
    fun onNetWorkStateChange(networkState: NetworkState) {
        Log.i(TAG, "onNetWorkStateChange  networkState = $networkState")
        when (networkState) {
            NetworkState.NONE -> {
                Toast.makeText(applicationContext, "暂无网络", Toast.LENGTH_SHORT).show()
                tv_network_state?.text = "当前网络类型：暂无网络"
            }
            NetworkState.WIFI -> {
                Toast.makeText(applicationContext, "WIFI网络", Toast.LENGTH_SHORT).show()
                tv_network_state?.text = "当前网络类型：WIFI网络"
            }
            NetworkState.CELLULAR -> {
                Toast.makeText(applicationContext, "蜂窝网络", Toast.LENGTH_SHORT).show()
                tv_network_state?.text = "当前网络类型：蜂窝网络"
            }
        }
    }

    @NetworkMonitor(monitorFilter = [NetworkState.WIFI, NetworkState.CELLULAR])
    fun onNetWorkStateChangeWIFI(networkState: NetworkState) {
        Log.i(TAG, "onNetWorkStateChangeWIFI  networkState = $networkState")
    }

    @NetworkMonitor(monitorFilter = [NetworkState.CELLULAR])
    fun onNetWorkStateChangeCellular(networkState: NetworkState) {
        Log.i(TAG, "onNetWorkStateChangeCellular  networkState = $networkState")
    }

    @NetworkMonitor(monitorFilter = [NetworkState.NONE])
    fun onNetWorkStateChangeNONE(networkState: NetworkState) {
        Log.i(TAG, "onNetWorkStateChangeNONE  networkState = $networkState")
    }
}

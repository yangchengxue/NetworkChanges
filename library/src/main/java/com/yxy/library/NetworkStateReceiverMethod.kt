package com.yxy.library

import com.yxy.library.enums.NetworkState
import java.lang.reflect.Method

internal class NetworkStateReceiverMethod(
    var any: Any? = null,
    var method: Method? = null,
    var monitorFilter: Array<NetworkState>? = null
)
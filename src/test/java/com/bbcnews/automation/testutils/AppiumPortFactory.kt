package com.bbcnews.automation.testutils

import java.net.ServerSocket

object AppiumPortFactory {
    @Throws(Exception::class)
    fun create(): Int {
        val socket = ServerSocket(0)
        socket.reuseAddress = true
        val port = socket.localPort
        socket.close()
        return port
    }



}
package com.bbcnews.automation.testutils

import io.appium.java_client.service.local.AppiumDriverLocalService
import io.appium.java_client.service.local.AppiumServiceBuilder

import java.io.File

object AppiumManager {


    lateinit var appiumService: AppiumDriverLocalService
    lateinit var appiumServiceUrl: String


    /**
     * start appium with modified arguments : appium port, chrome port, and bootstap port as user pass port number
     * @param  port
     */

    @Throws(Exception::class)
    fun startAppium(port: Int) {
        appiumService = AppiumDriverLocalService
                .buildService(AppiumServiceBuilder()
                        .usingDriverExecutable(File("/usr/local/bin/node"))
                        .withAppiumJS(
                                File(
                                        "/usr/local/bin/appium"))
                        .withIPAddress("127.0.0.1").usingPort(port))
        appiumService.start()
    }

//    fun AppiumURL(): String {
//        return this.appiumServiceUrl = this.appiumService.url.toString()
//
//    }

    fun stopappium() {
        println("Stop appium service")
        appiumService.stop()

    }


    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val appiumManager = AppiumManager
        appiumManager.startAppium(4735)
        appiumManager.stopappium()

    }


}
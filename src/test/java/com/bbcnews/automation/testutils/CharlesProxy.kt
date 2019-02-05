package com.bbcnews.automation.testutils

import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.IOException

import org.testng.Assert.assertEquals

class CharlesProxy {

    var charlesconfig = "/charles/CharlesConfig.xml"
    var charlesRecord = "curl -v -x http://localhost:8888 http://control.charles/recording/start"
    var charlesSessionDownload = "curl -o session.chls -x http://localhost:8888 http://control.charles/session/download"
    var charlesStop = "curl -v -x http://localhost:8888 http://control.charles/recording/stop"
    var startCharles = "Charles -config"
    var stopCharles = "killall Charles"

    var rt = Runtime.getRuntime()


    fun startCharles() {

        try {
            //Process pr = rt.exec("charles -config /Users/ramakh02/Desktop/AppiumBBCNewsAndroid/charles/NewCharlesConfig.xml");
            val pr = rt.exec("charles")
        } catch (e: IOException) {

        }

    }


    fun stopCharles() {
        try {
            val pr1 = rt.exec("killall charles")
        } catch (e: IOException) {

        }

    }

    fun converttoCSV() {
        try {
            val pr6 = rt.exec("Charles convert session.chls session.csv")
            Thread.sleep(2000)
        } catch (e: IOException) {
        } catch (e: InterruptedException) {
        }

    }

    @Throws(InterruptedException::class)
    fun downloadCharlesSession() {
        try {
            val pr4 = rt.exec("curl -o session.chls -x http://localhost:8888 http://control.charles/session/download")
            Thread.sleep(2000)
        } catch (e: IOException) {
        }

    }

    fun startcharlesSession() {
        try {
            val pr2 = rt.exec("curl -v -x http://localhost:8888 http://control.charles/recording/start")
            Thread.sleep(2000)
        } catch (e: Exception) {
        }

    }

    fun stopCharlesSession() {
        try {
            //Process p5 = Runtime.getRuntime().exec(charlesStop);
            val pr5 = rt.exec("curl -v -x http://localhost:8888 http://control.charles/recording/stop")
        } catch (e: IOException) {
        }

    }





}
package com.bbcnews.automation.testutils;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

import java.io.File;

public class AppiumManager {


    public static AppiumDriverLocalService appiumService;
    public static String appiumServiceUrl;


    /**
     * start appium with modified arguments : appium port, chrome port, and bootstap port as user pass port number
     * @param  port
     */

    public static void startAppium(int port)throws Exception
    {
        appiumService=AppiumDriverLocalService
                .buildService(new AppiumServiceBuilder()
                        .usingDriverExecutable(new File("/usr/local/bin/node"))
                        .withAppiumJS(
                                new File(
                                        "/usr/local/bin/appium"))
                        .withIPAddress("127.0.0.1").usingPort(port));
        appiumService.start();
    }

    public static String AppiumURL()
    {
        return appiumServiceUrl = appiumService.getUrl().toString();

    }

    public static void stopappium()
    {
        System.out.println("Stop appium service");
        appiumService.stop();

    }


    public static void main(String args[]) throws Exception {
        AppiumManager appiumManager = new AppiumManager();
        appiumManager.startAppium(4735);
        appiumManager.stopappium();

    }


}

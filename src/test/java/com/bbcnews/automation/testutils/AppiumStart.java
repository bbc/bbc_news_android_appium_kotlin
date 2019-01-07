package com.bbcnews.automation.testutils;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;

public class AppiumStart {

    public AppiumDriverLocalService appiumService;
    public String appiumServiceUrl;
    public AppiumDriverLocalService service;


    public void startAppium(int port) throws Exception
    {

        String command = "appium --session-override -p " + port ;
        System.out.println(command);
        String output = runCommand(command); //run command on terminal
        System.out.println(output);
        try {
            appiumServiceUrl = appiumService.getUrl().toString();
            System.out.println("appiumServiceUrl is " + appiumServiceUrl);
        }catch (NullPointerException e)
        {

        }

    }

    public String runCommand(String command) throws InterruptedException, IOException
    {
        Process p = Runtime.getRuntime().exec(command);

        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));

        String line="";
        String allLine="";
        while((line=r.readLine()) != null){
            allLine=allLine+""+line+"\n";
            if(line.contains("started on"))
                break;
        }
        return allLine;
    }

    public void stopAppium() throws Exception
    {
        try {
            appiumService.stop();
        }catch (NullPointerException e)
        {

        }
    }

//    public static void main(String args[]) throws Exception {
//        AppiumStart appiumStart = new AppiumStart();
//        appiumStart.startAppium(4725);
//        appiumStart.stopAppium();
//    }
}

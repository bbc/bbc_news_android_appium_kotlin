package com.bbcnews.automation.base;

import com.bbcnews.automation.pageobjects.BasePageObject;
import com.bbcnews.automation.pageobjects.MyNewsPageObject;
import com.bbcnews.automation.pageobjects.PopularPageObject;
import com.bbcnews.automation.testutils.AppiumManager;
import com.bbcnews.automation.testutils.AppiumPortFactory;
import com.bbcnews.automation.testutils.AppiumStart;
import com.bbcnews.automation.testutils.DeviceDetails;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Base {

    public  AppiumDriver<MobileElement> appiumDriver;
    public  DesiredCapabilities capabilities;
    AppiumStart appiumStart = new AppiumStart();

    public String DeviceosName;
    public String Deviceid;
    public String Devicename;

    public File file;
    public int appiumport;

    String Deviceos_Name ;
    String Device_id;
    String Device_Name;
    String App_Path;
    String Appium_Port;

    private static Date curDate = new Date();
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private static String DateToStr = format.format(curDate);
    public  static String workingDirectory =  System.getProperty("user.dir");
    private  static String screenshotpath = workingDirectory +"/Screenshots/"+ File.separator+DateToStr;

    public  void getAttachedDeviceDetails()
    {

        try {

            getDeviceDetailsinfo();
            DeviceDetails.populateDevices_IDs();
            DeviceDetails.populateDevices_OS();
            DeviceDetails.populateDevices_Names();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void getDeviceDetailsinfo() {

        for (int i = 0; i < DeviceDetails.deviceOS.size(); i++) {
            try {
                DeviceosName = DeviceDetails.deviceOS.get(i);
                Deviceid = DeviceDetails.deviceID.get(i);
                Devicename = DeviceDetails.deviceName.get(i);
                appiumport = AppiumPortFactory.create();
                System.out.println("The Device OS is " + DeviceosName);
                System.out.println("The Device ID is " + Deviceid);
                System.out.println("The Device port is " + Devicename);
                System.out.println("The Device Name is " + appiumport);
            } catch (Exception e) {
                e.printStackTrace();

            }

        }

    }


    public void readDeviceDetailsCommandPrompt() {

        try {
            Deviceos_Name = System.getProperty("DeviceOS");
            Device_id = System.getProperty("DeviceID");
            Device_Name = System.getProperty("DeviceName");
            App_Path = System.getProperty("AppPath");
            Appium_Port = System.getProperty("AppiumPort");
            System.out.println("Passed The Device OS is " + Deviceos_Name);
            System.out.println("Passed The Device ID is " + Device_id);
            System.out.println("Passed The Device Name is " + Device_Name);
            System.out.println("Passed The Appium port is " + Appium_Port);
            System.out.println("Passed The Application path  is " + App_Path);
        } catch (Exception e) {
            e.printStackTrace();

        }

    }



    public void setUP(AppiumDriver<MobileElement> appiumDriver) {
        try {

            //commented out to start appium server, as this taken care by hive, to run locally un-comment below line of code
            //appiumStart.startAppium(Integer.parseInt(Appium_Port));
            AppiumManager.startAppium(Integer.parseInt(Appium_Port));
            String appium_url = "http://127.0.0.1:"+Appium_Port+"/wd/hub";
            System.out.println("Appium Server Address : - " + appium_url);
            capabilities = new DesiredCapabilities();
            capabilities.setCapability(MobileCapabilityType.UDID, Device_id);
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "bbcnews");
            capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
            capabilities.setCapability("appiumversion", "1.8.1");
            capabilities.setCapability("app",App_Path); //"/Users/ramakh02/Desktop/tools/APK/BBCNews-5.5.0.35.apk");
            //it's not mandatory to pass OS version of the device
            // capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,Deviceos_Name);
            capabilities.setCapability("appPackage", "bbc.mobile.news.uk.internal" );
            capabilities.setCapability("appActivity","bbc.mobile.news.v3.app.TopLevelActivity");
            // capabilities.setCapability(MobileCapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, true);
            // capabilities.setCapability("autoAcceptAlerts", true);
            appiumDriver = new AppiumDriver<MobileElement>(new URL(appium_url),capabilities);
        }catch (Exception e) {
        }
    }

    public  void settingAppiumCapabilities() {
        try {

            AppiumManager.startAppium(appiumport);
           // appiumStart.startAppium(Integer.parseInt(appiumPort));
            String appiul_url = AppiumManager.AppiumURL();
            System.out.println("Appium Service Address : - " + appiul_url);
            capabilities = new DesiredCapabilities();
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, Deviceid);
            capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
            capabilities.setCapability(MobileCapabilityType.APPIUM_VERSION, "1.8.1");
            capabilities.setCapability(MobileCapabilityType.APP, "/Users/ramakh02/Desktop/tools/APK/BBCNews-5.5.0.35.apk");
            capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, DeviceosName);
            capabilities.setCapability("appPackage", "bbc.mobile.news.uk.internal");
            capabilities.setCapability("appActivity", "bbc.mobile.news.v3.app.TopLevelActivity");
            capabilities.setCapability(MobileCapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, true);
            capabilities.setCapability("autoAcceptAlerts", true);
            capabilities.setCapability("--session-override", true);
            appiumDriver = new AppiumDriver<MobileElement>(new URL(appiul_url), capabilities);
        } catch (Exception e) {
        }
    }


    public void stoppingAppiumCapabilities()
    {
        AppiumManager.stopappium();
    }




//    public static void main(String args[])
//    {
//        Base base = new Base();
//        base.getAttachedDeviceDetails();
//        base.getDeviceDetailsinfo();
//        base.settingAppiumCapabilities();
//        base.stoppingAppiumCapabilities();
//    }
}

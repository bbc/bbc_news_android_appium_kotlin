package com.bbcnews.automation.scripts;

import com.aventstack.extentreports.ExtentTest;
import com.bbcnews.automation.base.CharlesProxy;
import com.bbcnews.automation.commonfunctions.CommonFunctions;
import com.bbcnews.automation.pageobjects.*;
import com.bbcnews.automation.testutils.AppiumStart;
import com.bbcnews.automation.testutils.ExtenttestReport;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobileCapabilityType;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BBCNewsStatsTest {

        private AndroidDriver<MobileElement> androidDriver;
        private DesiredCapabilities capabilities = new DesiredCapabilities();
        private String Deviceos_Name ;
        private String Device_id;
        private String Device_Name;
        private String App_Path;
        private String Appium_Port;
        public File file;
        ExtentTest test;

        private HomePageObject homePageObject;
        private MyNewsPageObject myNewsPageObject;
        private BasePageObject basePageObject;
        private VidoePageObject vidoePageObject;
        private PopularPageObject popularPageObject;

        ExtenttestReport extenttestReport = new ExtenttestReport();
        AppiumStart appiumStart = new AppiumStart();
        CharlesProxy charlesProxy = new CharlesProxy();

        private static Date curDate = new Date();
        private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        private static String DateToStr = format.format(curDate);
        public static String workingDirectory = System.getProperty("user.dir");
        private static String screenshotpath = workingDirectory + "/Screenshots/";

        @BeforeTest
        public void RunTest() throws Exception {

        try {

            readDeviceDetailsCommandPrompt();
            charlesProxy.startCharles();
            charlesProxy.charlesSessionStart();
            setUP();
            LaunchBBCNews();
        } catch (Exception e) {
            e.printStackTrace();
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

        public void setUP() {
        try
        {
            //  appiumStart.startAppium(Integer.parseInt(Appium_Port));
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
            capabilities.setCapability("--session-override",true);
            androidDriver = new AndroidDriver<MobileElement>(new URL(appium_url),capabilities);
        } catch (Exception e) {
        }
    }

        public void LaunchBBCNews() {
        try {
            homePageObject = new HomePageObject();
            PageFactory.initElements(new AppiumFieldDecorator(androidDriver), homePageObject);

            myNewsPageObject = new MyNewsPageObject();
            PageFactory.initElements(new AppiumFieldDecorator(androidDriver), myNewsPageObject);

            basePageObject = new BasePageObject();
            PageFactory.initElements(new AppiumFieldDecorator(androidDriver), basePageObject);

            vidoePageObject = new VidoePageObject();
            PageFactory.initElements(new AppiumFieldDecorator(androidDriver), vidoePageObject);

            popularPageObject = new PopularPageObject();
            PageFactory.initElements(new AppiumFieldDecorator(androidDriver), popularPageObject);

            CommonFunctions.emptyFolder(screenshotpath);

            extenttestReport.createrReportHive("SmokeTest", Deviceos_Name, Device_Name, Device_id);

            androidDriver.context("NATIVE_APP");
            file = new File(screenshotpath);
            String screenshot = file.getAbsolutePath();
            System.out.println("The ScreenShot Path is " + screenshot);

            ScreenOrientation orientation = androidDriver.getOrientation();
            if(orientation.equals(ScreenOrientation.LANDSCAPE))
            {
                androidDriver.rotate(ScreenOrientation.PORTRAIT);
            }


        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(priority = 1, description = "launching the app and start the Charles ")
    @Story("Home")
    @Severity(SeverityLevel.CRITICAL)
    public void testOpenNewsApp() throws Exception {
        try {


            extenttestReport.tapButton(androidDriver,basePageObject.okbutton,false);
            extenttestReport.tapButton(androidDriver,basePageObject.nothanksbutton,false);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test(priority = 2, description = "Test to navigate around app for generate stats")
    @Story("Home")
    @Severity(SeverityLevel.CRITICAL)
    public void testCheckHomePage(){
        try {
            extenttestReport.startTest("HomePage", "Checking the HomePage", "Smoke");
            extenttestReport.tapButton(androidDriver,basePageObject.mynews,false);
            extenttestReport.tapButton(androidDriver,basePageObject.popular,false);
            extenttestReport.tapButton(androidDriver,basePageObject.video,false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test(priority = 3, description = "Test to stop the charles recording ")
    public void testStopcharlesRecord() throws Exception {

        try {
            charlesProxy.stopCharlesSession();
            Thread.sleep(8000);
        }catch (AssertionError e)
        {
            throw e;
        }
    }


    @Test(priority = 4, description = "Test to download the charles recording session")
    public void testDownloadcharlesRecord() throws Exception {

        try {
            charlesProxy.downloadCharlesSession();
            Thread.sleep(8000);
        }catch (AssertionError e)
        {
            throw e;
        }
    }

    @Test(priority = 5, description = "Test to convert the charles session to CSV format")
    public void testConvertCharlesSessiontoCSV() throws Exception {

        try {
            charlesProxy.converttoCSV();
            Thread.sleep(8000);
            charlesProxy.stopCharles();
        }catch (AssertionError e)
        {
            throw e;
        }
    }

    @AfterMethod
    public void getResult(ITestResult result)
    {
        try {
            extenttestReport.getTestResult(androidDriver,result);
        }catch (IOException e)
        {}

    }
    @AfterTest
    public void tearDown()
    {
        extenttestReport.publishReport();
        androidDriver.closeApp();
        androidDriver.quit();

    }


}



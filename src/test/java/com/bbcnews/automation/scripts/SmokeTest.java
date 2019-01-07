package com.bbcnews.automation.scripts;

import com.bbcnews.automation.base.Base;
import com.bbcnews.automation.commonfunctions.CommonFunctions;
import com.bbcnews.automation.pageobjects.*;
import com.bbcnews.automation.testutils.*;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobileCapabilityType;
import io.qameta.allure.Description;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Copyright 2018 BBC Inc.
 * All rights reserved.
 */

/*
 * @author  harish ramakrishna
 * @version 12/10/18
 */
@Listeners(com.bbcnews.automation.testutils.Listeners.TestListeners.class)
public class SmokeTest {

    public AndroidDriver<MobileElement> androidDriver;
    public DesiredCapabilities capabilities;
    private String DeviceosName;
    private String Deviceid;
    private String Devicename;
    public int appiumport;
    public File file;

    String Deviceos_Name ;
    String Device_id;
    String Device_Name;
    String App_Path;
    String Appium_Port;


    HomePageObject homePageObject;
    MyNewsPageObject myNewsPageObject;
    BasePageObject basePageObject;
    VidoePageObject vidoePageObject;
    PopularPageObject popularPageObject;
    CommonFunctions commonFunctions = new CommonFunctions();

    Base baseclass = new Base();
    AppiumStart appiumStart = new AppiumStart();
    ExtenttestReport extenttestReport = new ExtenttestReport();

//    private static Date curDate = new Date();
//    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//    private static String DateToStr = format.format(curDate);

    public  static String workingDirectory =  System.getProperty("user.dir");
    private  static String screenshotpath = "/Screenshots/";
    //  private boolean true;


    @BeforeTest
    public void RunTest() throws Exception{

        try {

            readDeviceDetailsCommandPrompt();
            //baseclass.readDeviceDetailsCommandPrompt();
           // baseclass.setUP(androidDriver);
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
        try {

            //commented out to start appium server, as this taken care by hive, to run locally un-comment below line of code
            //appiumStart.startAppium(Integer.parseInt(Appium_Port));
          //  AppiumManager.startAppium(Integer.parseInt(Appium_Port));
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
        }catch (Exception e) {
        }
    }



    public  void LaunchBBCNews()
    {
        try {
            homePageObject = new HomePageObject();
            PageFactory.initElements(new AppiumFieldDecorator(androidDriver), homePageObject);

            myNewsPageObject= new MyNewsPageObject();
            PageFactory.initElements(new AppiumFieldDecorator(androidDriver),myNewsPageObject);

            basePageObject = new BasePageObject();
            PageFactory.initElements(new AppiumFieldDecorator(androidDriver),basePageObject);

            vidoePageObject = new VidoePageObject();
            PageFactory.initElements(new AppiumFieldDecorator(androidDriver),vidoePageObject);

            popularPageObject = new PopularPageObject();
            PageFactory.initElements(new AppiumFieldDecorator(androidDriver),popularPageObject);

            File currentDirFile = new File("Screenshots");
            String helper = currentDirFile.getAbsolutePath();

            CommonFunctions.emptyFolder(helper);

            androidDriver.context("NATIVE_APP");
//            file = new File(screenshotpath);
//            String screenshot = file.getAbsolutePath();
//            System.out.println("The ScreenShot Path is " + screenshot);

            CommonFunctions.createReport("Functional");
        }catch (NullPointerException e)
        {

        } catch (Exception e) {

        }
    }


    @Test(priority = 1)
   // @Description("Test to check the Home page ")
    public void testHomepageTopStories() throws Exception {
        try {
            CommonFunctions.clickButton(androidDriver,basePageObject.okbutton,false);//,file.getAbsolutePath());
            CommonFunctions.clickButton(androidDriver,basePageObject.nothanksbutton,false);//,file.getAbsolutePath());
            CommonFunctions.startTest(androidDriver,"Open BBC News App","HomePage",false,"HomePage");//,file.getAbsolutePath(),"HomePage");
            CommonFunctions.linksDisplayed(androidDriver, basePageObject.mynews);
            CommonFunctions.linksDisplayed(androidDriver, basePageObject.topstories);
            CommonFunctions.linksDisplayed(androidDriver, basePageObject.popular);
            CommonFunctions.linksDisplayed(androidDriver, basePageObject.video);
            CommonFunctions.linksDisplayed(androidDriver, basePageObject.search);
            CommonFunctions.linksDisplayed(androidDriver, basePageObject.menubutton);
            CommonFunctions.endTest();
        }catch (Exception e){}

    }


    @Test(priority = 3)
   // @Description("Test to check the MyNews page ")
    public void testMyNewsPage() throws Exception {
        try {
            CommonFunctions.startTest(androidDriver, "My News Page", "MyNews", false, "MyNews");//file.getAbsolutePath(),"MyNews");
            CommonFunctions.clickButton(androidDriver, basePageObject.mynews, false);//,file.getAbsolutePath());
            CommonFunctions.linksDisplayed(androidDriver, myNewsPageObject.mynews_summary);
            CommonFunctions.linksDisplayed(androidDriver, myNewsPageObject.mynewstitle);
            CommonFunctions.linksDisplayed(androidDriver, myNewsPageObject.addnews_button);
            Assert.assertEquals(myNewsPageObject.mynewstitle_text, myNewsPageObject.mynewstitle.getText(), "Text matched");
            Assert.assertEquals(myNewsPageObject.mynewssummary_text, myNewsPageObject.mynews_summary.getText(), "Text matched");
            CommonFunctions.endTest();
        }catch (Exception e)
        {
        }catch (AssertionError e)
        {
            throw e;
        }
    }

    @Test(priority = 4)
   // @Description("Test to check the adding the topics to MyNews page ")
    public void testAddingTopicstoMyNewsPage() throws Exception {
    try {
        CommonFunctions.startTest(androidDriver, "Adding Topics to MyNews Page", "MyNews", false, "AddingTopics");//,file.getAbsolutePath(),"AddingTopics");
        CommonFunctions.clickButton(androidDriver, myNewsPageObject.mynews_startButton, false);//,file.getAbsolutePath());
        CommonFunctions.scrolltoElement(androidDriver, myNewsPageObject.Walestopic, "Wales");
        CommonFunctions.clickButton(androidDriver, myNewsPageObject.Walestopic, false);//,file.getAbsolutePath());
        CommonFunctions.scrolltoElement(androidDriver, myNewsPageObject.Asiatopic, "Asia");
        CommonFunctions.clickButton(androidDriver, myNewsPageObject.Asiatopic, false);//,file.getAbsolutePath());
        CommonFunctions.endTest();
    }catch (Exception e)
        {

        }
    }

    @Test(priority = 5)
    //@Description("Test to check the adding the topics are displayed in MyNews page ")
    public void testCheckAddedTopics() throws Exception {
        try {
            CommonFunctions.startTest(androidDriver, "Checking Added Topics on MyTopics Page", "MyNews", false, "MyTopics");//,file.getAbsolutePath(),"MyTopics");
            CommonFunctions.clickButton(androidDriver, myNewsPageObject.mytopics, false);//,file.getAbsolutePath());
            CommonFunctions.linksDisplayed(androidDriver, myNewsPageObject.Walestopic);
            CommonFunctions.linksDisplayed(androidDriver, myNewsPageObject.Asiatopic);
            CommonFunctions.endTest();
            CommonFunctions.navigateBack(androidDriver);
        }catch(Exception e)
        { }


    }

    @Test(priority = 6)
   // @Description("Test to check the popular page  ")
    public void testPopularPage() throws Exception {
        try {
            CommonFunctions.navigateBack(androidDriver);
            CommonFunctions.startTest(androidDriver, "Checking Popular Page", "Popular",false,"Popularr");// file.getAbsolutePath(), "Popularr");
            CommonFunctions.clickButton(androidDriver, popularPageObject.popular, false);//,file.getAbsolutePath());
            CommonFunctions.linksDisplayed(androidDriver, popularPageObject.mostread);
            CommonFunctions.endTest();
        }catch (Exception e) { }


    }

    @Test(priority = 7)
   // @Description("Test to check that most watched in Popular page ")
    public void testcheckMostWatched() throws Exception
    {
        try {
            CommonFunctions.startTest(androidDriver, "Checking Most Watched ", "Popular", false,"MostWatched");//file.getAbsolutePath(), "MostWatched");
            CommonFunctions.scrolltoElement(androidDriver, popularPageObject.popular_mostwatched,"Most Watched");
            CommonFunctions.endTest();
        }catch (Exception e)
        {

        }
    }

    @Test(priority = 8)
    //@Description("Test to check the Video page ")
    public void testVideopage() throws Exception {
        try
        {
        CommonFunctions.startTest(androidDriver, "Checking the Video Page", "Video", false, "video");//file.getAbsolutePath(),"video");
        CommonFunctions.clickButton(androidDriver, basePageObject.video, false);
        CommonFunctions.linksDisplayed(androidDriver, vidoePageObject.livebbchannel);//,file.getAbsolutePath());
        CommonFunctions.clickButton(androidDriver, vidoePageObject.bbcnewsChannel, false);//,file.getAbsolutePath());
        CommonFunctions.linksDisplayed(androidDriver, vidoePageObject.livebbchannel);
        CommonFunctions.linksDisplayed(androidDriver, basePageObject.navigate_back);
        CommonFunctions.linksDisplayed(androidDriver, basePageObject.sharestory);
        CommonFunctions.clickButton(androidDriver, vidoePageObject.smp_placeholder_play_button, false);//,file.getAbsolutePath());
        CommonFunctions.endTest();
        CommonFunctions.navigateBack(androidDriver);

    }catch (Exception e)
    {

    }

    }

    @Test(priority = 9)
    //@Description("Test to check the menu Items ")
    public void testcheckMenuItems() throws Exception
    {

            CommonFunctions.startTest(androidDriver, "Checking Menu Items ", "MenuItems", false,"Menu");//file.getAbsolutePath(), "Menu");
            CommonFunctions.clickButton(androidDriver,basePageObject.menubutton,true);//,file.getAbsolutePath());
            CommonFunctions.linksDisplayed(androidDriver,basePageObject.settings);
            CommonFunctions.linksDisplayed(androidDriver,basePageObject.InternalSettings);
            CommonFunctions.linksDisplayed(androidDriver,basePageObject.OtherBBCapps);
            CommonFunctions.linksDisplayed(androidDriver,basePageObject.Appinfo);
            CommonFunctions.endTest();
            CommonFunctions.linksDisplayed(androidDriver,vidoePageObject.smp_exit_fullscreen_button);
            CommonFunctions.linksDisplayed(androidDriver,vidoePageObject.smp_exit_fullscreen_button);
            CommonFunctions.navigateBack(androidDriver);

    }

    @AfterMethod
    public void showResults(ITestResult result)
    {
       // ITestContext context=null;
        CommonFunctions.getTestResult(androidDriver,result);
        //commonFunctions.onFinish(context);
    }

    @AfterTest
    public void tearDown()
    {
        try {

            CommonFunctions.publishReport();
            androidDriver.closeApp();
            androidDriver.quit();
            //commenting out stopping appium code, as this is taken care by HIVE itself
            //un-comment to run locally
           // AppiumManager.stopappium();
        } //catch (NullPointerException e) {}
          catch (Exception e){}

    }
}

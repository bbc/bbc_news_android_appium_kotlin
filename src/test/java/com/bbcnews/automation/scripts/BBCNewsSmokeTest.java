package com.bbcnews.automation.scripts;

import com.aventstack.extentreports.ExtentTest;

import com.bbcnews.automation.commonfunctions.CommonFunctions;
import com.bbcnews.automation.pageobjects.*;
import com.bbcnews.automation.testutils.*;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobileCapabilityType;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

@Listeners(com.bbcnews.automation.testutils.Listeners.TestListeners.class)
public class BBCNewsSmokeTest
{
    private AndroidDriver<MobileElement> androidDriver;
    private DesiredCapabilities capabilities = new DesiredCapabilities();
    private String Deviceos_Name ;
    private String Device_id;
    private String Device_Name;
    private String App_Path;
    private String Appium_Port;
    public File file;
    ExtentTest test;

    private  HomePageObject homePageObject;
    private  MyNewsPageObject myNewsPageObject;
    private BasePageObject basePageObject;
    private VidoePageObject vidoePageObject;
    private PopularPageObject popularPageObject;

    ExtenttestReport extenttestReport = new ExtenttestReport();
    AppiumStart appiumStart = new AppiumStart();



    private static Date curDate = new Date();
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private static String DateToStr = format.format(curDate);
    public static String workingDirectory = System.getProperty("user.dir");
    private static String screenshotpath = workingDirectory + "/Screenshots/";



    @BeforeTest
    public void RunTest() throws Exception {

        try {

            readDeviceDetailsCommandPrompt();
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


    @Test(priority = 1, description = "launching the app ")
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


    @Test(priority = 2, description = "Check the links on the Home page after app launched")
    @Story("Home")
    @Severity(SeverityLevel.CRITICAL)
    public void testCheckHomePage(){
        try {
            extenttestReport.startTest("HomePage", "Checking the HomePage", "Smoke");
            Assert.assertTrue(basePageObject.topstories.isSelected());
            extenttestReport.elementDisplayed(androidDriver,basePageObject.topstories);
            extenttestReport.elementDisplayed(androidDriver,basePageObject.mynews);
            extenttestReport.elementDisplayed(androidDriver,basePageObject.popular);
            extenttestReport.elementDisplayed(androidDriver,basePageObject.video);
            extenttestReport.elementDisplayed(androidDriver,basePageObject.menubutton);
            extenttestReport.elementDisplayed(androidDriver,basePageObject.search);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test(priority = 3, description = "Test to check the  popular page")
    @Story("Popular")
    @Severity(SeverityLevel.CRITICAL)
    public void testPopularPage() throws Exception {

        try {
            extenttestReport.startTest("PopularPage", "Checking the Popular", "Smoke");
            extenttestReport.tapButton(androidDriver,basePageObject.popular,false);//,file.getAbsolutePath());
            Assert.assertTrue(basePageObject.popular.isSelected());
            extenttestReport.elementDisplayed(androidDriver,popularPageObject.mostread);
        }catch (AssertionError e)
        {
            throw e;
        }
    }

    @Test(priority = 4, description = "checking that most watched displayed in popular page")
    @Story("Popular")
    @Severity(SeverityLevel.CRITICAL)
    // @Description("Test to check that most watched in Popular page ")
    public void testcheckMostWatched()
    {
        try {
            extenttestReport.startTest("PopularPage", "Checking most watched displayed the Popular", "Smoke");
            extenttestReport.scrolltoElement(androidDriver, popularPageObject.popular_mostwatched);
        }catch (Exception e)
        {

        }
    }


    @Test(priority = 5, description = "Test to check the Mynews page")
    @Story("MyNews")
    @Severity(SeverityLevel.CRITICAL)
    public void testMyNewsPage(){
        try {
            extenttestReport.startTest("MyNews", "Checking the MyNews", "Smoke");
            extenttestReport.tapButton(androidDriver,basePageObject.mynews,false);//,file.getAbsolutePath());
            Assert.assertTrue(basePageObject.mynews.isSelected());
            extenttestReport.linksDisplayed(androidDriver, myNewsPageObject.mynews_summary);
            extenttestReport.linksDisplayed(androidDriver, myNewsPageObject.mynewstitle);
            extenttestReport.linksDisplayed(androidDriver, myNewsPageObject.addnews_button);
            Assert.assertEquals(myNewsPageObject.mynewstitle_text, myNewsPageObject.mynewstitle.getText(), "Text matched");
            Assert.assertEquals(myNewsPageObject.mynewssummary_text, myNewsPageObject.mynews_summary.getText(), "Text matched");

        }catch (Exception e) {
        }
        catch (AssertionError e)
        {
            throw e;
        }
    }


    @Test(priority = 6, description = "Test to check the adding the topics to MyNews page")
    @Story("MyNews")
    @Severity(SeverityLevel.CRITICAL)
    public void testAddingTopicstoMyNewsPage() throws Exception {
        try {
            extenttestReport.startTest("MyNews", "Adding topics to MyNews", "Smoke");
            extenttestReport.tapButton(androidDriver,myNewsPageObject.mynews_startButton,false);
            extenttestReport.scrolltoElement(androidDriver, myNewsPageObject.Walestopic);
            extenttestReport.tapButton(androidDriver,myNewsPageObject.Walestopic,false);
            extenttestReport.scrolltoElement(androidDriver, myNewsPageObject.Asiatopic);
            extenttestReport.tapButton(androidDriver,myNewsPageObject.Asiatopic,false);

        }catch (Exception e)
        {

        }
    }

    @Test(priority = 7, description = "Test to check whether selected topics displayed under MyTopics page")
    @Story("MyTopics")
    @Severity(SeverityLevel.CRITICAL)
    //@Description("Test to check the adding the topics are displayed in MyNews page ")
    public void testCheckAddedTopicsUnderMyTopics() throws Exception {
        try {
            extenttestReport.startTest("MyTopics", "Checking Added topics in MyTopics", "Smoke");
            extenttestReport.tapButton(androidDriver,myNewsPageObject.mytopics,false);
            extenttestReport.elementDisplayed(androidDriver,myNewsPageObject.Walestopic);
            extenttestReport.elementDisplayed(androidDriver,myNewsPageObject.Asiatopic);
            extenttestReport.navigateBack(androidDriver);
        }catch(Exception e)
        { }


    }

    @Test(priority = 8, description = "Test to check whether added topics displayed under MyNews page")
    @Story("MyNews")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test to check whether added topics displayed under MyNews page")
    public void testCheckAddedTopicsUnderMyNews() throws Exception {
        try {
            extenttestReport.startTest("MyNews", "Checking Added topics in MyNews", "Smoke");
            extenttestReport.elementDisplayed(androidDriver,myNewsPageObject.Walestopic);
            extenttestReport.elementDisplayed(androidDriver,myNewsPageObject.Asiatopic);

        }catch(Exception e)
        { }


    }

    @Test(priority = 9, description = "Test to Check the Menu Options ")
    @Story("Menu")
    @Severity(SeverityLevel.CRITICAL)
    public void testMenuPage() throws Exception {

        try {
            extenttestReport.startTest("Menu", "Checking the Menu Items", "Smoke");
            extenttestReport.tapButton(androidDriver,basePageObject.menubutton,false);//,file.getAbsolutePath());
            extenttestReport.elementDisplayed(androidDriver,basePageObject.Appinfo);
            extenttestReport.elementDisplayed(androidDriver,basePageObject.OtherBBCapps);
            extenttestReport.elementDisplayed(androidDriver,basePageObject.InternalSettings);
            extenttestReport.elementDisplayed(androidDriver,basePageObject.settings);
            extenttestReport.navigateBack(androidDriver);
        }catch (AssertionError e)
        {
            throw e;
        }
    }

    @Test(priority = 10, description = "Test to check the Video page")
    @Story("VideoPage")
    @Severity(SeverityLevel.CRITICAL)
    public void testVideoPage() throws Exception {

        try {
            extenttestReport.startTest("Videopgae", "Checking the Video", "Smoke");
            extenttestReport.tapButton(androidDriver,basePageObject.video,false);//,file.getAbsolutePath());
            Assert.assertTrue(basePageObject.video.isSelected());
            extenttestReport.elementDisplayed(androidDriver, vidoePageObject.livebbchannel);//,file.getAbsolutePath());
            extenttestReport.tapButton(androidDriver, vidoePageObject.bbcnewsChannel, false);//,file.getAbsolutePath());
          //  extenttestReport.elementDisplayed(androidDriver, vidoePageObject.live_media_item_caption);
            extenttestReport.elementDisplayed(androidDriver, basePageObject.navigate_back);
            extenttestReport.elementDisplayed(androidDriver, basePageObject.sharestory);
            extenttestReport.tapButton(androidDriver, vidoePageObject.smp_placeholder_play_button, false);//,file.getAbsolutePath());
            extenttestReport.sleepmethod(3000);
            extenttestReport.navigateBack(androidDriver);
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

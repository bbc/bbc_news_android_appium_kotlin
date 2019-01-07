package com.bbcnews.automation.scripts;

import com.bbcnews.automation.base.Base;
import com.bbcnews.automation.commonfunctions.CommonFunctions;
import com.bbcnews.automation.pageobjects.*;
import com.bbcnews.automation.testutils.AppiumManager;
import com.bbcnews.automation.testutils.ExtenttestReport;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BBCNewsSanityChecks {

    public AndroidDriver<MobileElement> androidDriver;
    public DesiredCapabilities capabilities;

    String Deviceos_Name ;
    String Device_id;
    String Device_Name;
    String App_Path;
    String Appium_Port;

    public File file;


    HomePageObject homePageObject;
    MyNewsPageObject myNewsPageObject;
    BasePageObject basePageObject;
    VidoePageObject vidoePageObject;
    PopularPageObject popularPageObject;

    private static Date curDate = new Date();
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private static String DateToStr = format.format(curDate);
    public  static String workingDirectory =  System.getProperty("user.dir");
    private  static String screenshotpath = workingDirectory +"/Screenshots/"+ File.separator+DateToStr;
    //  private boolean true;


    @BeforeTest
    public void RunTest() throws Exception{

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

            CommonFunctions.emptyFolder(screenshotpath);

            androidDriver.context("NATIVE_APP");
            file = new File(screenshotpath);
            String screenshot = file.getAbsolutePath();
            System.out.println("The ScreenShot Path is " + screenshot);

            CommonFunctions.createReport("Functional");
        }catch (NullPointerException e)
        {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test(priority = 1)
    public void testVideopage() throws Exception
    {
        CommonFunctions.clickButton(androidDriver,basePageObject.okbutton,false);//,file.getAbsolutePath());
        CommonFunctions.clickButton(androidDriver,basePageObject.nothanksbutton,false);//,file.getAbsolutePath());
        CommonFunctions.startTest(androidDriver,"Checking the Video Page","Video",false,"video");//file.getAbsolutePath(),"video");
        CommonFunctions.clickButton(androidDriver,basePageObject.video,true);//,file.getAbsolutePath());
        CommonFunctions.clickButton(androidDriver,vidoePageObject.bbcnewsChannel,true);//,file.getAbsolutePath());
        CommonFunctions.linksDisplayed(androidDriver,vidoePageObject.live_media_item_caption);
        CommonFunctions.linksDisplayed(androidDriver,basePageObject.navigate_back);
        CommonFunctions.linksDisplayed(androidDriver,basePageObject.sharestory);
        CommonFunctions.clickButton(androidDriver,vidoePageObject.smp_placeholder_play_button,true);//,file.getAbsolutePath());
       // CommonFunctions.clickButton(androidDriver,vidoePageObject.smp_fullscreen_button,true);//, file.getAbsolutePath());
       // Thread.sleep(2000);
        CommonFunctions.clickButton(androidDriver,vidoePageObject.transportcontrol,false);//,file.getAbsolutePath());
        CommonFunctions.clickButton(androidDriver,vidoePageObject.transportcontrol,false);//,file.getAbsolutePath());
        CommonFunctions.linksDisplayed(androidDriver,vidoePageObject.smp_pause_button);
        CommonFunctions.linksDisplayed(androidDriver,vidoePageObject.smp_fullscreen_button);
        CommonFunctions.linksDisplayed(androidDriver,vidoePageObject.smp_live_icon);
        CommonFunctions.linksDisplayed(androidDriver,vidoePageObject.smp_volume_button);
        CommonFunctions.linksDisplayed(androidDriver,vidoePageObject.smp_seek_bar);
        CommonFunctions.endTest();

    }

    @Test(priority = 2)
    public void testPopularPage() {
        try {

            CommonFunctions.navigateBack(androidDriver);
            CommonFunctions.startTest(androidDriver, "Checking Popular Page", "Popular",false, "Popularr");//file.getAbsolutePath(), "Popularr");
            CommonFunctions.clickButton(androidDriver, popularPageObject.popular, true);//,file.getAbsolutePath());
            CommonFunctions.linksDisplayed(androidDriver, popularPageObject.mostread);
            CommonFunctions.endTest();
        }catch (Exception e) { }

    }


    @Test(priority = 3)
    public void testCheckMostReadPopular()
    {
        try {
            CommonFunctions.startTest(androidDriver, "Checking Most Read Popular ", "Most Read",false, "Popularr");//file.getAbsolutePath(), "Popularr");
            CommonFunctions.clickButton(androidDriver,popularPageObject.mostRead_article,false);
            CommonFunctions.linksDisplayed(androidDriver,popularPageObject.headline_title);
           //CommonFunctions.linksDisplayed(androidDriver,popularPageObject.headline_author_title);
            CommonFunctions.linksDisplayed(androidDriver,popularPageObject.image_item_badge);
            CommonFunctions.linksDisplayed(androidDriver,popularPageObject.headline_info);
            CommonFunctions.linksDisplayed(androidDriver,popularPageObject.headline_link);
            CommonFunctions.endTest();

        }catch (Exception e){}
    }

    @Test(priority = 4)
    public void testcheckMostWatched()
    {
        try {
            CommonFunctions.navigateBack(androidDriver);
            CommonFunctions.startTest(androidDriver, "Checking Most Watched ", "Popular", true,"MostWatched");//file.getAbsolutePath(), "MostWatched");
            CommonFunctions.scrolltoElement(androidDriver, popularPageObject.popular_mostwatched,"Most Watched");
            CommonFunctions.endTest();
        }catch (Exception e) { }
    }



    @Test(priority = 5)
    public void testCheckMostWatchedArticleInPopular()
    {
        try {
            CommonFunctions.startTest(androidDriver, "Checking Most Watched Popular ", "Most Read",false, "Popularr");//file.getAbsolutePath(), "Popularr");
            CommonFunctions.clickButton(androidDriver,popularPageObject.mostwatched_article,false);
            CommonFunctions.linksDisplayed(androidDriver,popularPageObject.videoTitleTopic);
            CommonFunctions.linksDisplayed(androidDriver,popularPageObject.videoTitleTimestamp);
            CommonFunctions.linksDisplayed(androidDriver,popularPageObject.videoTitleHeadline);
            CommonFunctions.linksDisplayed(androidDriver,popularPageObject.videoSummary);
            CommonFunctions.endTest();
            CommonFunctions.navigateBack(androidDriver);
        }catch (Exception e){}
    }

    @Test(priority = 6)
    public void testcheckMenuItems()
    {
        try {
            CommonFunctions.startTest(androidDriver, "Checking Menu Items ", "MenuItems", false,"Menu");//file.getAbsolutePath(), "Menu");
            CommonFunctions.clickButton(androidDriver,basePageObject.menubutton,true);//,file.getAbsolutePath());
            CommonFunctions.linksDisplayed(androidDriver,basePageObject.settings);
            CommonFunctions.linksDisplayed(androidDriver,basePageObject.InternalSettings);
            CommonFunctions.linksDisplayed(androidDriver,basePageObject.OtherBBCapps);
            CommonFunctions.linksDisplayed(androidDriver,basePageObject.Appinfo);
            CommonFunctions.navigateBack(androidDriver);
            CommonFunctions.endTest();

        }catch (Exception e) { }
    }

    @Test(priority = 7)
    public void testSearchStories()
    {
        try
        {
            CommonFunctions.startTest(androidDriver,"Checking Search Topics","Search",false,"Search");//,file.getAbsolutePath(),"Search");
            CommonFunctions.clickButton(androidDriver,basePageObject.searchbutton,true);//,file.getAbsolutePath());
            CommonFunctions.enterText(androidDriver,basePageObject.searchfield,"India");
            String searchTopics_text = CommonFunctions.getText(androidDriver,basePageObject.searchheading);

            Assert.assertEquals(searchTopics_text,"Topics (3)","matched");
            String searchRelatedheading_text = CommonFunctions.getText(androidDriver,basePageObject.searchheading2);

            Assert.assertEquals(searchRelatedheading_text,"Articles related to \"India\"");

            CommonFunctions.clickButton(androidDriver,basePageObject.cancelSearch,true);//,file.getAbsolutePath());

            String searchTopics_text1 = CommonFunctions.getText(androidDriver,basePageObject.searchheading);
            Assert.assertEquals(searchTopics_text1,"In The News Now","matched");

            String searchRelatedheading_text1 = CommonFunctions.getText(androidDriver,basePageObject.searchheading2);
            Assert.assertEquals(searchRelatedheading_text1,"More Topics","matched");

            //Assert.assertEquals(basePageObject.searchheading4.getText(),"My Topics","matched");
            basePageObject.backButton.click();
            CommonFunctions.endTest();

        }catch (Exception e){}
    }

    @AfterMethod
    public void getResult(ITestResult result)
    {
        CommonFunctions.getTestResult(androidDriver,result);


    }

    @AfterClass
    public void tearDown()
    {

        CommonFunctions.publishReport();
        androidDriver.closeApp();
        androidDriver.quit();
        //AppiumManager.stopappium();

    }
}

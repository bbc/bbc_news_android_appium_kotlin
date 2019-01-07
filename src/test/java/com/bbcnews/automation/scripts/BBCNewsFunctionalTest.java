package com.bbcnews.automation.scripts;

import com.bbcnews.automation.commonfunctions.CommonFunctions;
import com.bbcnews.automation.pageobjects.*;
import com.bbcnews.automation.testutils.AppiumManager;
import com.bbcnews.automation.testutils.AppiumPortFactory;
import com.bbcnews.automation.testutils.DeviceDetails;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

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

public class BBCNewsFunctionalTest {

    public AndroidDriver<MobileElement> androidDriver;
    public DesiredCapabilities capabilities;
    private String DeviceosName;
    private String Deviceid;
    private String Devicename;
    public int appiumport;
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


    @BeforeClass
    public void RunTest() throws Exception{

        try {

            getDeviceDetails();
            DeviceDetails.populateDevices_IDs();
            DeviceDetails.populateDevices_OS();
            DeviceDetails.populateDevices_Names();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getDeviceDetails() {

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

@Test(dependsOnMethods = {"getDeviceDetails"})
    public void setUP() {
        try {

            AppiumManager.startAppium(appiumport);
            String appiul_url = AppiumManager.AppiumURL();
            System.out.println("Appium Service Address : - " + appiul_url);
            capabilities = new DesiredCapabilities();
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, Deviceid);
            capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
            capabilities.setCapability(MobileCapabilityType.APPIUM_VERSION, "1.8.1");
            capabilities.setCapability(MobileCapabilityType.APP,"/Users/ramakh02/Desktop/tools/APK/BBCNews-5.5.0.35.apk");
            capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,DeviceosName);
            capabilities.setCapability("appPackage", "bbc.mobile.news.uk.internal" );
            capabilities.setCapability("appActivity","bbc.mobile.news.v3.app.TopLevelActivity");
            capabilities.setCapability(MobileCapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, true);
            capabilities.setCapability("autoAcceptAlerts", true);
            androidDriver = new AndroidDriver<MobileElement>(new URL(appiul_url),capabilities);
        }catch (Exception e) {
        }
    }


    @Test(dependsOnMethods = {"setUP"})
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


    @Test(dependsOnMethods = {"LaunchBBCNews"})
    public void openBBCNewsApp() {
        try {
            CommonFunctions.clickButton(androidDriver,basePageObject.okbutton,false);//,file.getAbsolutePath());
            CommonFunctions.clickButton(androidDriver,basePageObject.nothanksbutton,false);//,file.getAbsolutePath());
            CommonFunctions.startTest(androidDriver,"Open BBC News App","HomePage",true,"HomePage");//file.getAbsolutePath(),"HomePage");
            CommonFunctions.linksDisplayed(androidDriver, basePageObject.mynews);
            CommonFunctions.linksDisplayed(androidDriver, basePageObject.topstories);
            CommonFunctions.linksDisplayed(androidDriver, basePageObject.popular);
            CommonFunctions.linksDisplayed(androidDriver, basePageObject.video);
            CommonFunctions.linksDisplayed(androidDriver, basePageObject.search);
            CommonFunctions.linksDisplayed(androidDriver, basePageObject.menubutton);
            CommonFunctions.endTest();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
}

    @Test(dependsOnMethods = {"openBBCNewsApp"})
    public void testVideoofthedayDisplayed() throws Exception {
        CommonFunctions.startTest(androidDriver,"Scroll to a Video of the day","HomePage",false,"VideoOftheDay");//file.getAbsolutePath(),"VideoOftheDay");
        CommonFunctions.scrolltoElement(androidDriver,homePageObject.videooftheday_button,"VideoOftheDay");
        CommonFunctions.linksDisplayed(androidDriver,homePageObject.watchvideo);
        CommonFunctions.linksDisplayed(androidDriver,homePageObject.promocounter);
        CommonFunctions.clickButton(androidDriver,homePageObject.videooftheday_button,true);//,file.getAbsolutePath());
        CommonFunctions.scrolltoEndofStories(androidDriver,homePageObject.checkback_later,file.getAbsolutePath(),homePageObject.newstream_progress);
        CommonFunctions.navigateBack(androidDriver);
        CommonFunctions.endTest();
    }

    @Test(dependsOnMethods = {"testVideoofthedayDisplayed"})
    public void testStoriesdisplayed() throws Exception {
        CommonFunctions.startTest(androidDriver,"Scroll to a Stories of the day","HomePage",false,"StoriesOftheDay");//file.getAbsolutePath(),"StoriesOftheDay");
        CommonFunctions.scrolltoElement(androidDriver,homePageObject.stories_button,"StoriesOfDay");
        CommonFunctions.linksDisplayed(androidDriver,homePageObject.watchvideo);
        CommonFunctions.linksDisplayed(androidDriver,homePageObject.promocounter);
        CommonFunctions.clickButton(androidDriver,homePageObject.stories_button,true);//,file.getAbsolutePath());
        CommonFunctions.scrolltoEndofStories(androidDriver,homePageObject.checkback_later,file.getAbsolutePath(),homePageObject.newstream_progress);
      //  homePageObject.closewindow.click();
        CommonFunctions.navigateBack(androidDriver);
        CommonFunctions.endTest();

    }


    @Test(dependsOnMethods = {"testStoriesdisplayed"})
    public void testToCheckPoliticsTopic()
    {
        CommonFunctions.startTest(androidDriver,"Scroll to a Political Topic","HomePage",false,"Politics");//file.getAbsolutePath(),"Politics");
        CommonFunctions.scrolltoElement(androidDriver,homePageObject.top_stories_Politics,"TopStories");
        CommonFunctions.clickButton(androidDriver,homePageObject.top_stories_Politics,true);//,file.getAbsolutePath());
        CommonFunctions.navigateBack(androidDriver);
        CommonFunctions.endTest();
    }

    @Test(dependsOnMethods = {"testToCheckPoliticsTopic"})
    public void testMyNews() throws Exception {
        CommonFunctions.startTest(androidDriver,"My News Page","MyNews",false,"MyNews");//file.getAbsolutePath(),"MyNews");
        CommonFunctions.clickButton(androidDriver,basePageObject.mynews,true);//,file.getAbsolutePath());
        CommonFunctions.linksDisplayed(androidDriver,myNewsPageObject.mynews_summary);
        CommonFunctions.linksDisplayed(androidDriver,myNewsPageObject.mynewstitle);
        CommonFunctions.linksDisplayed(androidDriver,myNewsPageObject.addnews_button);
        Assert.assertEquals(myNewsPageObject.mynewstitle_text,myNewsPageObject.mynewstitle.getText(),"Text Mesaaged");
        Assert.assertEquals(myNewsPageObject.mynewssummary_text,myNewsPageObject.mynews_summary.getText(),"Text Mesaaged");
        CommonFunctions.endTest();
    }

    @Test(dependsOnMethods = {"testMyNews"})
    public void testAddingTopicsPage() throws Exception {
        CommonFunctions.startTest(androidDriver,"Checking EditMyTopics Page","MyNews",false,"EditTopics");//file.getAbsolutePath(),"EditTopics");
        CommonFunctions.clickButton(androidDriver,myNewsPageObject.mynews_startButton,true);//,file.getAbsolutePath());
        CommonFunctions.elementIsSelected(androidDriver,myNewsPageObject.addtopics);
        CommonFunctions.linksDisplayed(androidDriver,myNewsPageObject.mytopics);
        CommonFunctions.linksDisplayed(androidDriver,myNewsPageObject.location_button);
        CommonFunctions.linksDisplayed(androidDriver,myNewsPageObject.editMyTopics);
        CommonFunctions.linksDisplayed(androidDriver,myNewsPageObject.localnews);
        CommonFunctions.clickButton(androidDriver,myNewsPageObject.mytopics,true);//,file.getAbsolutePath());
        CommonFunctions.elementIsSelected(androidDriver,myNewsPageObject.mytopics);
        Assert.assertEquals(myNewsPageObject.mytopic_emptyview_text,myNewsPageObject.mytopic_emptyview.getText(),"Text Mesaaged");
        CommonFunctions.endTest();
    }


    @Test(dependsOnMethods = {"testAddingTopicsPage"})
    public void testAddingTopicstoMyNewsPage() throws Exception {

        CommonFunctions.startTest(androidDriver,"Adding Topics to MyNews Page","MyNews",false,"AddingTopics");//file.getAbsolutePath(),"AddingTopics");
        CommonFunctions.clickButton(androidDriver,myNewsPageObject.addtopics,true);//,file.getAbsolutePath());
        CommonFunctions.scrolltoElement(androidDriver,myNewsPageObject.Walestopic,"Wales");
        CommonFunctions.clickButton(androidDriver,myNewsPageObject.Walestopic,false);//,file.getAbsolutePath());
        CommonFunctions.scrolltoElement(androidDriver,myNewsPageObject.Asiatopic,"Asia");
        CommonFunctions.clickButton(androidDriver,myNewsPageObject.Asiatopic,false);//,file.getAbsolutePath());
        CommonFunctions.scrolltoElement(androidDriver,myNewsPageObject.Europeantopic,"Europe");
        CommonFunctions.clickButton(androidDriver,myNewsPageObject.Europeantopic,false);//,file.getAbsolutePath());
        CommonFunctions.endTest();

    }

    @Test(dependsOnMethods = {"testAddingTopicstoMyNewsPage"})
    public void testCheckAddedTopics() throws Exception {
        CommonFunctions.startTest(androidDriver,"Checking Added Topics on MyTopics Page","MyNews",false,"MyTopics");//file.getAbsolutePath(),"MyTopics");
        CommonFunctions.clickButton(androidDriver,myNewsPageObject.mytopics,true);//,file.getAbsolutePath());
        CommonFunctions.linksDisplayed(androidDriver,myNewsPageObject.Walestopic);
        CommonFunctions.linksDisplayed(androidDriver,myNewsPageObject.Europeantopic);
        CommonFunctions.linksDisplayed(androidDriver,myNewsPageObject.Asiatopic);
        CommonFunctions.navigateBack(androidDriver);
        CommonFunctions.endTest();
    }

    @Test(dependsOnMethods = {"testCheckAddedTopics"})
    public void testSelectedAddedTopics() throws Exception {
        CommonFunctions.startTest(androidDriver,"Selecting Added Topics from MyNews Page","MyNews",false,"MyNewsTopics");//file.getAbsolutePath(),"MyNewsTopics");
        CommonFunctions.linksDisplayed(androidDriver,myNewsPageObject.Walestopic);
        CommonFunctions.linksDisplayed(androidDriver,myNewsPageObject.Europeantopic);
        CommonFunctions.linksDisplayed(androidDriver,myNewsPageObject.Asiatopic);
        CommonFunctions.clickButton(androidDriver,myNewsPageObject.Walestopic,true);//,file.getAbsolutePath());
        CommonFunctions.navigateBack(androidDriver);
        CommonFunctions.clickButton(androidDriver,myNewsPageObject.Europeantopic,true);//,file.getAbsolutePath());
        CommonFunctions.navigateBack(androidDriver);
        CommonFunctions.clickButton(androidDriver,myNewsPageObject.Asiatopic,true);//,file.getAbsolutePath());
        CommonFunctions.navigateBack(androidDriver);
        CommonFunctions.endTest();
    }

    @Test(dependsOnMethods = {"testSelectedAddedTopics"})
    public void testVideopage() throws Exception
    {
        CommonFunctions.startTest(androidDriver,"Checking the Video Page","Video",false,"video");//file.getAbsolutePath(),"video");
        CommonFunctions.clickButton(androidDriver,basePageObject.video,true);//,file.getAbsolutePath());
        CommonFunctions.clickButton(androidDriver,vidoePageObject.bbcnewsChannel,true);//,file.getAbsolutePath());
        CommonFunctions.linksDisplayed(androidDriver,vidoePageObject.livebbchannel);
        CommonFunctions.linksDisplayed(androidDriver,basePageObject.navigate_back);
        CommonFunctions.linksDisplayed(androidDriver,basePageObject.sharestory);
        CommonFunctions.clickButton(androidDriver,vidoePageObject.smp_placeholder_play_button,true);//,file.getAbsolutePath());
        CommonFunctions.clickButton(androidDriver,vidoePageObject.smp_fullscreen_button,true);//, file.getAbsolutePath());
        Thread.sleep(2000);
        CommonFunctions.clickButton(androidDriver,vidoePageObject.transportcontrol,false);//,file.getAbsolutePath());
        CommonFunctions.clickButton(androidDriver,vidoePageObject.transportcontrol,false);//,file.getAbsolutePath());
        CommonFunctions.linksDisplayed(androidDriver,vidoePageObject.smp_pause_button);
        CommonFunctions.linksDisplayed(androidDriver,vidoePageObject.smp_exit_fullscreen_button);
        CommonFunctions.linksDisplayed(androidDriver,vidoePageObject.smp_live_icon);
        CommonFunctions.linksDisplayed(androidDriver,vidoePageObject.smp_volume_button);
        CommonFunctions.linksDisplayed(androidDriver,vidoePageObject.smp_seek_bar);
        CommonFunctions.endTest();
        CommonFunctions.navigateBack(androidDriver);
    }

    @Test(dependsOnMethods = {"testVideopage"})
    public void testPopularPage() {
        try {
            CommonFunctions.navigateBack(androidDriver);
            CommonFunctions.startTest(androidDriver, "Checking Popular Page", "Popular",false, "Popularr");//file.getAbsolutePath(), "Popularr");
            CommonFunctions.clickButton(androidDriver, popularPageObject.popular, true);//,file.getAbsolutePath());
            CommonFunctions.linksDisplayed(androidDriver, popularPageObject.mostread);
            CommonFunctions.endTest();
        }catch (Exception e) { }

    }

    @Test(dependsOnMethods = {"testPopularPage"})
    public void testcheckMostWatched()
    {
        try {
            CommonFunctions.startTest(androidDriver, "Checking Most Watched ", "Popular", true,"MostWatched");//file.getAbsolutePath(), "MostWatched");
            CommonFunctions.scrolltoElement(androidDriver, popularPageObject.popular_mostwatched,"Most Watched");
            CommonFunctions.endTest();
        }catch (Exception e) { }
    }

    @Test(dependsOnMethods = {"testcheckMostWatched"})
    public void testcheckMenuItems()
    {
        try {
            CommonFunctions.startTest(androidDriver, "Checking Menu Items ", "MenuItems", false,"Menu");//file.getAbsolutePath(), "Menu");
            CommonFunctions.clickButton(androidDriver,basePageObject.menubutton,true);//,file.getAbsolutePath());
            CommonFunctions.linksDisplayed(androidDriver,basePageObject.settings);
            CommonFunctions.linksDisplayed(androidDriver,basePageObject.InternalSettings);
            CommonFunctions.linksDisplayed(androidDriver,basePageObject.OtherBBCapps);
            CommonFunctions.linksDisplayed(androidDriver,basePageObject.Appinfo);
            CommonFunctions.endTest();
            CommonFunctions.navigateBack(androidDriver);
        }catch (Exception e) { }
    }

    @Test(dependsOnMethods = {"testcheckMenuItems"})
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
            Assert.assertEquals(searchRelatedheading_text,"Articles related to \"india\"");
            CommonFunctions.clickButton(androidDriver,basePageObject.cancelSearch,true);//,file.getAbsolutePath());
            Assert.assertEquals(searchTopics_text,"In The News Now","matched");
            Assert.assertEquals(searchTopics_text,"More Topics","matched");
            Assert.assertEquals(basePageObject.searchheading4.getText(),"My Topics","matched");
            basePageObject.backButton.click();
            CommonFunctions.endTest();

        }catch (Exception e){}
    }

    @AfterClass
    public void tearDown()
    {

            CommonFunctions.publishReport();
            androidDriver.quit();
            AppiumManager.stopappium();

    }
}
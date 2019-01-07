package com.bbcnews.automation.scripts

import com.aventstack.extentreports.ExtentTest
import com.bbcnews.automation.commonfunctions.CommonFunctionKotlin

import com.bbcnews.automation.commonfunctions.CommonFunctions
import com.bbcnews.automation.pageobjects.*
import com.bbcnews.automation.testutils.*
import io.appium.java_client.MobileElement
import io.appium.java_client.android.AndroidDriver

import io.appium.java_client.pagefactory.AppiumFieldDecorator
import io.appium.java_client.remote.MobileCapabilityType
import io.qameta.allure.Description
import io.qameta.allure.Severity
import io.qameta.allure.SeverityLevel
import io.qameta.allure.Story
import org.openqa.selenium.ScreenOrientation
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.support.PageFactory
import org.testng.Assert
import org.testng.ITestResult
import org.testng.annotations.*

import java.io.File
import java.io.IOException
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date

class BBCNewsSmokeTestKotlin : CommonFunctionKotlin()
{


    private var capabilities = DesiredCapabilities()
    private var Deviceos_Name: String? = null
    private var Device_id: String? = null
    private var Device_Name: String? = null
    private var App_Path: String? = null
    private var Appium_Port: String? = null
    private lateinit var file: File

    internal var commonFunctionKotlin = CommonFunctionKotlin()



    private lateinit var homePageObject: HomePageObject
    private lateinit var androidDriver: AndroidDriver<MobileElement>
    private lateinit var myNewsPageObject: MyNewsPageObject
    public lateinit var basePageObject: BasePageObject
    private lateinit var vidoePageObject: VidoePageObject
    private lateinit var popularPageObject: PopularPageObject
    public lateinit var basepageobjectkotlin: BBCNewsSmokeTestKotlin

    //private lateinit var commonFunctionKotlin: CommonFunctionKotlin
    internal var appiumStart = AppiumStart()

    private val curDate = Date()
    private val format = SimpleDateFormat("yyyy-MM-dd")
    private val DateToStr = format.format(curDate)
   // private var workingDirectory = System.getProperty("user.dir")
    private val screenshotpath = "$workingDirectory/Screenshots/"

    @BeforeTest
    fun RunTest() {

        try {

            readDeviceDetailsCommandPrompt()
            setUP()
            LaunchBBCNews()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun readDeviceDetailsCommandPrompt() {

        try {
            Deviceos_Name = System.getProperty("DeviceOS")
            Device_id = System.getProperty("DeviceID")
            Device_Name = System.getProperty("DeviceName")
            App_Path = System.getProperty("AppPath")
            Appium_Port = System.getProperty("AppiumPort")
            println("Passed The Device OS is $Deviceos_Name")
            println("Passed The Device ID is $Device_id")
            println("Passed The Device Name is $Device_Name")
            println("Passed The Appium port is $Appium_Port")
            println("Passed The Application path  is $App_Path")
        } catch (e: Exception) {
            e.printStackTrace()

        }

    }

    fun setUP() {
        try {
            //  appiumStart.startAppium(Integer.parseInt(Appium_Port));
            val appium_url = "http://127.0.0.1:$Appium_Port/wd/hub"
            println("Appium Server Address : - $appium_url")
            capabilities = DesiredCapabilities()
            capabilities.setCapability(MobileCapabilityType.UDID, Device_id)
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "bbcnews")
            capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2")
            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android")
            capabilities.setCapability("appiumversion", "1.8.1")
            capabilities.setCapability("app", App_Path) //"/Users/ramakh02/Desktop/tools/APK/BBCNews-5.5.0.35.apk");
            //it's not mandatory to pass OS version of the device
            // capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,Deviceos_Name);
            capabilities.setCapability("appPackage", "bbc.mobile.news.uk.internal")
            capabilities.setCapability("appActivity", "bbc.mobile.news.v3.app.TopLevelActivity")
            // capabilities.setCapability(MobileCapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, true);
            // capabilities.setCapability("autoAcceptAlerts", true);
            capabilities.setCapability("--session-override", true)
            androidDriver = AndroidDriver(URL(appium_url), capabilities)
        } catch (e: Exception) {
        }
    }

    fun LaunchBBCNews() {
        try {
            homePageObject = HomePageObject()
            PageFactory.initElements(AppiumFieldDecorator(androidDriver), homePageObject)

            myNewsPageObject = MyNewsPageObject()
            PageFactory.initElements(AppiumFieldDecorator(androidDriver), myNewsPageObject)

            basePageObject = BasePageObject()
            PageFactory.initElements(AppiumFieldDecorator(androidDriver), basePageObject)

            vidoePageObject = VidoePageObject()
            PageFactory.initElements(AppiumFieldDecorator(androidDriver), vidoePageObject)

            popularPageObject = PopularPageObject()
            PageFactory.initElements(AppiumFieldDecorator(androidDriver), popularPageObject)

            commonFunctionKotlin.emptyFolder(screenshotpath)

            // commonFunctionKotlin.startReport("SmokeTest");
            commonFunctionKotlin.createrReportHive("SmokeTest", Deviceos_Name.toString(), Device_Name.toString(), Device_id.toString())
            //createrReportHive("SmokeTest", Deviceos_Name, Device_Name, Device_id)

            androidDriver.context("NATIVE_APP")
            file = File(screenshotpath)
            val screenshot = file.getAbsolutePath()
            println("The ScreenShot Path is $screenshot")

            val orientation = androidDriver.getOrientation()
            if (orientation != ScreenOrientation.LANDSCAPE) {
                androidDriver.rotate(ScreenOrientation.PORTRAIT)
            } else {

            }


        } catch (e: NullPointerException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Test(priority = 1, description = "Launching the app")
    fun testOpenNewsApp() {
        try {

            commonFunctionKotlin.tapButton(androidDriver, basePageObject.okbutton, false)
            commonFunctionKotlin.tapButton(androidDriver, basePageObject.nothanksbutton, false)

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    @Test(priority = 2, description = "Check the links on the Home page after app launched")
    fun testCheckHomePage() {
        try {
            commonFunctionKotlin.startTest("HomePage", "Checking the HomePage", "Smoke")
            Assert.assertTrue(basePageObject.topstories.isSelected)
            commonFunctionKotlin.elementDisplayed(androidDriver, basePageObject.topstories)
            commonFunctionKotlin.IselementSelected(androidDriver,basePageObject.topstories)
            commonFunctionKotlin.elementDisplayed(androidDriver, basePageObject.mynews)
            commonFunctionKotlin.elementDisplayed(androidDriver, basePageObject.popular)
            commonFunctionKotlin.elementDisplayed(androidDriver, basePageObject.video)
            commonFunctionKotlin.elementDisplayed(androidDriver, basePageObject.menubutton)
            commonFunctionKotlin.elementDisplayed(androidDriver, basePageObject.search)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    @Test(priority = 3, description = "Test to check the  popular page")
    fun testPopularPage() {

        try {
            commonFunctionKotlin.startTest("PopularPage", "Checking the Popular", "Smoke")
            commonFunctionKotlin.tapButton(androidDriver, basePageObject.popular, false)//,file.getAbsolutePath());
            commonFunctionKotlin.IselementSelected(androidDriver,basePageObject.popular)
            Assert.assertTrue(basePageObject.popular.isSelected)
            commonFunctionKotlin.elementDisplayed(androidDriver, popularPageObject.mostread)
        } catch (e: AssertionError) {
            throw e
        }

    }

    @Test(priority = 4, description = "checking that most watched displayed in popular page")
    @Story("Popular")
    @Severity(SeverityLevel.CRITICAL)
    fun testcheckMostWatched() {
        try {
            commonFunctionKotlin.startTest("PopularPage", "Checking most watched displayed the Popular", "Smoke")
            commonFunctionKotlin.scrolltoElement(androidDriver,popularPageObject.popular_mostwatched)
        } catch (e: Exception) {

        }
    }


    @Test(priority = 5, description = "Test to check the Mynews page")
    @Story("MyNews")
    @Severity(SeverityLevel.CRITICAL)
    fun testMyNewsPage() {
        try {
            commonFunctionKotlin.startTest("MyNews", "Checking the MyNews", "Smoke")
            commonFunctionKotlin.tapButton(androidDriver, basePageObject.mynews, false)//,file.getAbsolutePath());
            commonFunctionKotlin.IselementSelected(androidDriver,basePageObject.topstories)
            Assert.assertTrue(basePageObject.mynews.isSelected)
            commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.mynews_summary)
            commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.mynewstitle)
            commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.addnews_button)
           // Assert.assertEquals(myNewsPageObject.mynewstitle_text, myNewsPageObject.mynewstitle.text, "Text matched")
            //Assert.assertEquals(myNewsPageObject.mynewssummary_text, myNewsPageObject.mynews_summary.text, "Text matched")

        } catch (e: Exception) {
        } catch (e: AssertionError) {
            throw e
        }
    }

    @Test(priority = 6, description = "Test to check the adding the topics to MyNews page")
    @Story("MyNews")
    @Severity(SeverityLevel.CRITICAL)
    @Throws(Exception::class)
    fun testAddingTopicstoMyNewsPage() {
        try {
            commonFunctionKotlin.startTest("MyNews", "Adding topics to MyNews", "Smoke")
            commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.mynews_startButton, false)
            commonFunctionKotlin.scrolltoElement(androidDriver, myNewsPageObject.Walestopic)
            commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.Walestopic, false)
            commonFunctionKotlin.scrolltoElement(androidDriver, myNewsPageObject.Asiatopic)
            commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.Asiatopic, false)

        } catch (e: Exception) {

        }

    }
    @Test(priority = 7, description = "Test to check whether selected topics displayed under MyTopics page")
    @Story("MyTopics")
    @Severity(SeverityLevel.CRITICAL)
    @Throws(Exception::class)
    //@Description("Test to check the adding the topics are displayed in MyNews page ")
    fun testCheckAddedTopicsUnderMyTopics() {
        try {
            commonFunctionKotlin.startTest("MyTopics", "Checking Added topics in MyTopics", "Smoke")
            commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.mytopics, false)
            commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.Walestopic)
            commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.Asiatopic)
            commonFunctionKotlin.navigateBack(androidDriver)
        } catch (e: Exception) {
        }
    }

    @Test(priority = 8, description = "Test to check whether added topics displayed under MyNews page")
    @Story("MyNews")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test to check whether added topics displayed under MyNews page")
    @Throws(Exception::class)
    fun testCheckAddedTopicsUnderMyNews() {
        try {
            commonFunctionKotlin.startTest("MyNews", "Checking Added topics in MyNews", "Smoke")
            commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.Walestopic)
            commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.Asiatopic)

        } catch (e: Exception) {
        }
    }

    @Test(priority = 9, description = "Test to Check the Menu Options ")
    @Story("Menu")
    @Severity(SeverityLevel.CRITICAL)
    @Throws(Exception::class)
    fun testMenuPage() {

        try {
            commonFunctionKotlin.startTest("Menu", "Checking the Menu Items", "Smoke")
            commonFunctionKotlin.tapButton(androidDriver, basePageObject.menubutton, false)//,file.getAbsolutePath());
            commonFunctionKotlin.elementDisplayed(androidDriver, basePageObject.Appinfo)
            commonFunctionKotlin.elementDisplayed(androidDriver, basePageObject.OtherBBCapps)
            commonFunctionKotlin.elementDisplayed(androidDriver, basePageObject.InternalSettings)
            commonFunctionKotlin.elementDisplayed(androidDriver, basePageObject.settings)
            commonFunctionKotlin.navigateBack(androidDriver)
        } catch (e: AssertionError) {
            throw e
        }

    }

    @Test(priority = 10, description = "Test to check the Video page")
    @Story("VideoPage")
    @Severity(SeverityLevel.CRITICAL)
    @Throws(Exception::class)
    fun testVideoPage() {

        try {
            commonFunctionKotlin.startTest("Videopgae", "Checking the Video", "Smoke")
            commonFunctionKotlin.tapButton(androidDriver, basePageObject.video, false)//,file.getAbsolutePath());
            Assert.assertTrue(basePageObject.video.isSelected)
            commonFunctionKotlin.elementDisplayed(androidDriver, vidoePageObject.livebbchannel)//,file.getAbsolutePath());
            commonFunctionKotlin.tapButton(androidDriver, vidoePageObject.bbcnewsChannel, false)//,file.getAbsolutePath());
            //  extenttestReport.elementDisplayed(androidDriver, vidoePageObject.live_media_item_caption);
            commonFunctionKotlin.elementDisplayed(androidDriver, basePageObject.navigate_back)
            commonFunctionKotlin.elementDisplayed(androidDriver, basePageObject.sharestory)
            commonFunctionKotlin.tapButton(androidDriver, vidoePageObject.smp_placeholder_play_button, false)//,file.getAbsolutePath());
            commonFunctionKotlin.sleepmethod(3000)
            commonFunctionKotlin.navigateBack(androidDriver)
        } catch (e: AssertionError) {
            throw e
        }
    }

    @AfterMethod
    fun getResult(result: ITestResult)
     {
        try {
            commonFunctionKotlin.getTestResult(androidDriver, result)
        } catch (e: IOException) {
        }

    }

    @AfterTest
    fun tearDown() {
        commonFunctionKotlin.publishReport()
        androidDriver.closeApp()
        androidDriver.quit()

    }
}
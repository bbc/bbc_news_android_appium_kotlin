package com.bbcnews.automation.scripts

import com.aventstack.extentreports.ExtentTest
import com.bbcnews.automation.commonfunctions.CommonFunctionKotlin


import com.bbcnews.automation.pageobjects.*
import io.appium.java_client.MobileElement
import io.appium.java_client.android.AndroidDriver

import io.appium.java_client.pagefactory.AppiumFieldDecorator
import io.appium.java_client.remote.MobileCapabilityType
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


    var capabilities = DesiredCapabilities()
    var DeviceOsName: String? = null
    var Deviceid: String? = null
    var DeviceName: String? = null
    var AppPath: String? = null
    var AppiumPort: String? = null
    lateinit var file: File
    var commonFunctionKotlin = CommonFunctionKotlin()
    private lateinit var homePageObject: HomePageObject
    private lateinit var androidDriver: AndroidDriver<MobileElement>
    private lateinit var myNewsPageObject: MyNewsPageObject
    //private lateinit var basePageObject: BasePageObject
    private lateinit var vidoePageObject: VidoePageObject
    private lateinit var popularPageObject: PopularPageObject
    private  lateinit var basePageObjectModel: BasePageObject


//    private val curDate = Date()
//    private val format = SimpleDateFormat("yyyy-MM-dd")
//    private val DateToStr = format.format(curDate)
    private val screenshotpath = "$workingDirectory/Screenshots/"

    @BeforeTest
    fun RunTest() {

        try {

            readDeviceDetailsCommandPrompt()
            setUP()
            launchBBCNews()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     *  gets the details of the device, app path , appium port which are passed through command prompt
     */

    private fun readDeviceDetailsCommandPrompt() {
        try {
            DeviceOsName = System.getProperty("DeviceOS")
            Deviceid = System.getProperty("DeviceID")
            DeviceName = System.getProperty("DeviceName")
            AppPath = System.getProperty("AppPath")
            AppiumPort = System.getProperty("AppiumPort")
            println("Passed The Device OS is $DeviceOsName")
            println("Passed The Device ID is $Deviceid")
            println("Passed The Device Name is $DeviceName")
            println("Passed The Appium port is $AppiumPort")
            println("Passed The Application path  is $AppPath")
        } catch (e: Exception) {
            e.printStackTrace()

        }

    }

    /**
     *
     * setup the desired capabilities based on the parameter set
     */

    private fun setUP() {
        try {
            //  appiumStart.startAppium(Integer.parseInt(Appium_Port));
            val appiumurl = "http://127.0.0.1:$AppiumPort/wd/hub"
            println("Appium Server Address : - $appiumurl")
            capabilities = DesiredCapabilities()
            capabilities.setCapability(MobileCapabilityType.UDID, Deviceid)
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "bbcnews")
            capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2")
            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android")
            capabilities.setCapability("appiumversion", "1.8.1")
            capabilities.setCapability("app", AppPath) //"/Users/ramakh02/Desktop/tools/APK/BBCNews-5.5.0.35.apk");
            //it's not mandatory to pass OS version of the device
            // capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,Deviceos_Name);
            capabilities.setCapability("appPackage", "bbc.mobile.news.uk.internal")
            capabilities.setCapability("appActivity", "bbc.mobile.news.v3.app.TopLevelActivity")
            // capabilities.setCapability(MobileCapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, true);
            // capabilities.setCapability("autoAcceptAlerts", true);
            capabilities.setCapability("--session-override", true)
            androidDriver = AndroidDriver(URL(appiumurl), capabilities)
        } catch (e: Exception) {
        }
    }

    /**
     *
     * function to initialise the page objects for Home page, Video page, popular page
     */
   private fun launchBBCNews() {
        try {
            homePageObject = HomePageObject()
            PageFactory.initElements(AppiumFieldDecorator(androidDriver), homePageObject)

            myNewsPageObject = MyNewsPageObject()
            PageFactory.initElements(AppiumFieldDecorator(androidDriver), myNewsPageObject)

            basePageObjectModel = BasePageObject()
            PageFactory.initElements(AppiumFieldDecorator(androidDriver), basePageObjectModel)

            vidoePageObject = VidoePageObject()
            PageFactory.initElements(AppiumFieldDecorator(androidDriver), vidoePageObject)

            popularPageObject = PopularPageObject()
            PageFactory.initElements(AppiumFieldDecorator(androidDriver), popularPageObject)


            commonFunctionKotlin.emptyFolder(screenshotpath)

            // commonFunctionKotlin.startReport("SmokeTest");
            commonFunctionKotlin.createrReportHive("SmokeTest", DeviceOsName.toString(), DeviceName.toString(), Deviceid.toString())
            //createrReportHive("SmokeTest", Deviceos_Name, Device_Name, Device_id)

            androidDriver.context("NATIVE_APP")
            file = File(screenshotpath)
            val screenshot = file.absolutePath
            println("The ScreenShot Path is $screenshot")

            val orientation = androidDriver.orientation
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

    /**
     * launches the app and ignores the pop up message
     */
    @Test(priority = 1, description = "Launching the app")
    fun testOpenNewsApp() {
        try {

            commonFunctionKotlin.tapButton(androidDriver, basePageObjectModel.okbutton, false)
            commonFunctionKotlin.tapButton(androidDriver, basePageObjectModel.nothanksbutton, false)

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * after app launches, checks the top stories page and assertion
     */

    @Test(priority = 2, description = "Check the links on the Home page after app launched")
    fun testCheckHomePage() {
        try {
            commonFunctionKotlin.startTest("HomePage", "Checking the HomePage", "Smoke")
            Assert.assertTrue(basePageObjectModel.topstories.isSelected)
            commonFunctionKotlin.elementDisplayed(androidDriver, basePageObjectModel.topstories)
            commonFunctionKotlin.iselementSelected(basePageObjectModel.topstories)
            commonFunctionKotlin.elementDisplayed(androidDriver, basePageObjectModel.mynews)
            commonFunctionKotlin.elementDisplayed(androidDriver, basePageObjectModel.popular)
            commonFunctionKotlin.elementDisplayed(androidDriver, basePageObjectModel.video)
            commonFunctionKotlin.elementDisplayed(androidDriver, basePageObjectModel.menubutton)
            commonFunctionKotlin.elementDisplayed(androidDriver, basePageObjectModel.search)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * checks the mynews page by allowing the location services
     */

    @Test(priority = 3, description = "checking the location services based topics ")
    fun testAllowLocationBasedTopic()
    {
        commonFunctionKotlin.startTest("MyNews", "Location Based Topics in MyNews", "Smoke")
        commonFunctionKotlin.tapButton(androidDriver,basePageObjectModel.mynews,false)
        commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.mynews_startButton, false)
        commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.allow_location,false);
        commonFunctionKotlin.tapButton(androidDriver,myNewsPageObject.allowlocation_premission,false);
        commonFunctionKotlin.navigateBack(androidDriver);
    }



    /**
     * checks the popular page most read
     */
    @Test(priority = 4, description = "Test to check the  popular page")
    fun testPopularPage() {

        try {
            commonFunctionKotlin.startTest("PopularPage", "Checking the Popular", "Smoke")
            commonFunctionKotlin.tapButton(androidDriver, basePageObjectModel.popular, false)//,file.getAbsolutePath());
            commonFunctionKotlin.iselementSelected(basePageObjectModel.popular)
            Assert.assertTrue(basePageObjectModel.popular.isSelected)
            commonFunctionKotlin.elementDisplayed(androidDriver, popularPageObject.mostread)
        } catch (e: AssertionError) {
            throw e
        }

    }

    /**
     * checks the popular most watched
     */

    @Test(priority = 5, description = "checking that most watched displayed in popular page")
    fun testcheckMostWatched() {
        try {
            commonFunctionKotlin.startTest("PopularPage", "Checking most watched displayed the Popular", "Smoke")
            commonFunctionKotlin.scrolltoElement(androidDriver,popularPageObject.popular_mostwatched)
        } catch (e: Exception) {

        }
    }

    /**
     * check the MyNews Page
     *
     */

    @Test(priority = 6, description = "Test to check the Mynews page")
    @Story("MyNews")
    @Severity(SeverityLevel.CRITICAL)
    fun testMyNewsPage() {
        try {
            commonFunctionKotlin.startTest("MyNews", "Checking the MyNews", "Smoke")
            commonFunctionKotlin.tapButton(androidDriver, basePageObjectModel.mynews, false)
            commonFunctionKotlin.iselementSelected(basePageObjectModel.topstories)
            Assert.assertTrue(basePageObjectModel.mynews.isSelected)
            commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.mynews_summary)
            commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.mynewstitle)
            commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.addnews_button)
            Assert.assertEquals(myNewsPageObject.mynewstitle_text, myNewsPageObject.mynewstitle.text, "Text matched")
            Assert.assertEquals(myNewsPageObject.mynewssummary_text, myNewsPageObject.mynews_summary.text, "Text matched")

        } catch (e: Exception) {
        } catch (e: AssertionError) {
            throw e
        }
    }

    /**
     * Adding the topics to MyNews
     */
    @Test(priority = 7, description = "Test to check the adding the topics to MyNews page")
    @Story("MyNews")
    @Severity(SeverityLevel.CRITICAL)
    fun testAddingTopicstoMyNewsPage() {
        try {
            commonFunctionKotlin.startTest("MyNews", "Adding topics to MyNews", "Smoke")
            commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.mynews_startButton, false)
            commonFunctionKotlin.tapButton(androidDriver,myNewsPageObject.addtopics,false);
            Assert.assertEquals("London",myNewsPageObject.localnews_displayed.getText());
            commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.localnews_displayed);
            commonFunctionKotlin.scrolltoElement(androidDriver, myNewsPageObject.Walestopic)
            commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.Walestopic, false)
            commonFunctionKotlin.scrolltoElement(androidDriver, myNewsPageObject.Asiatopic)
            commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.Asiatopic, false)

        } catch (e: Exception) {

        }

    }
    @Test(priority = 8, description = "Test to check whether selected topics displayed under MyTopics page")
    @Story("MyTopics")
    @Severity(SeverityLevel.CRITICAL)
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

    @Test(priority = 9, description = "Test to check whether added topics displayed under MyNews page")
    @Story("MyNews")
    @Severity(SeverityLevel.CRITICAL)
    fun testCheckAddedTopicsUnderMyNews() {
        try {
            commonFunctionKotlin.startTest("MyNews", "Checking Added topics in MyNews", "Smoke")
            commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.Walestopic)
            commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.Asiatopic)

        } catch (e: Exception) {
        }
    }

    /**
     * Open the Menu items and assert whether links are displayed properly
     */
    @Test(priority = 10, description = "Test to Check the Menu Options ")
    @Story("Menu")
    @Severity(SeverityLevel.CRITICAL)
    fun testMenuPage() {

        try {
            commonFunctionKotlin.startTest("Menu", "Checking the Menu Items", "Smoke")
            commonFunctionKotlin.tapButton(androidDriver, basePageObjectModel.menubutton, false)
            commonFunctionKotlin.elementDisplayed(androidDriver, basePageObjectModel.Appinfo)
            commonFunctionKotlin.elementDisplayed(androidDriver, basePageObjectModel.OtherBBCapps)
            commonFunctionKotlin.elementDisplayed(androidDriver, basePageObjectModel.InternalSettings)
            commonFunctionKotlin.elementDisplayed(androidDriver, basePageObjectModel.settings)
            commonFunctionKotlin.navigateBack(androidDriver)
        } catch (e: AssertionError) {
            throw e
        }

    }

    /**
     * check the live video on video page
     */

    @Test(priority = 11, description = "Test to check the Video page")
    @Story("VideoPage")
    @Severity(SeverityLevel.CRITICAL)
    fun testVideoPage() {

        try {
            commonFunctionKotlin.startTest("Videopgae", "Checking the Video", "Smoke")
            commonFunctionKotlin.tapButton(androidDriver, basePageObjectModel.video, false)
            Assert.assertTrue(basePageObjectModel.video.isSelected)
            commonFunctionKotlin.elementDisplayed(androidDriver, vidoePageObject.livebbchannel)
            commonFunctionKotlin.tapButton(androidDriver, vidoePageObject.bbcnewsChannel, false)
            commonFunctionKotlin.elementDisplayed(androidDriver, vidoePageObject.live_media_item_caption);
            commonFunctionKotlin.elementDisplayed(androidDriver, basePageObjectModel.navigate_back)
            commonFunctionKotlin.elementDisplayed(androidDriver, basePageObjectModel.sharestory)
            commonFunctionKotlin.tapButton(androidDriver, vidoePageObject.smp_placeholder_play_button, false)
            commonFunctionKotlin.sleepmethod(3000)
            commonFunctionKotlin.navigateBack(androidDriver)

        } catch (e: AssertionError) {
            throw e
        }
    }


    @Test(priority = 12, description = "Test to check the Search and Search Result")
    fun testSearchTopics()
    {
        try {
        commonFunctionKotlin.startTest("Search", "Checking the Search Topics", "Smoke")
        commonFunctionKotlin.tapButton(androidDriver, basePageObjectModel.searchbutton, false)
        commonFunctionKotlin.enterText(basePageObjectModel.searchfield, basePageObjectModel.searchtext)
        commonFunctionKotlin.sleepmethod(1000)
        Assert.assertEquals(basePageObjectModel.searchtext, basePageObjectModel.searchkeyword.text, "Text matched")
        commonFunctionKotlin.tapButton(androidDriver, basePageObjectModel.searchkeyword, false)
        val title = commonFunctionKotlin.getText(basePageObjectModel.headlinetitle)
        Assert.assertEquals(basePageObjectModel.searchtext, title)
            commonFunctionKotlin.navigateBack(androidDriver)
        } catch (e: Exception) {
            e.printStackTrace()
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
        androidDriver.removeApp("bbc.mobile.news.uk.internal")
        androidDriver.quit()

    }
}
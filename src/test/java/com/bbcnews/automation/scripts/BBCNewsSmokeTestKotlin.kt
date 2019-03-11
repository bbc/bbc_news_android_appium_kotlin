package com.bbcnews.automation.scripts

import com.bbcnews.automation.commonfunctions.CommonFunctionKotlin
import com.bbcnews.automation.pageobjects.*


import com.bbcnews.automation.testutils.Testutility
import io.appium.java_client.MobileElement
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.pagefactory.AppiumFieldDecorator
import io.appium.java_client.remote.MobileCapabilityType
import io.qameta.allure.Severity
import io.qameta.allure.SeverityLevel
import io.qameta.allure.Story
import org.openqa.selenium.By
import org.openqa.selenium.ScreenOrientation
import org.openqa.selenium.StaleElementReferenceException
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.support.PageFactory
import org.testng.Assert
import org.testng.ITestResult
import org.testng.annotations.*
import java.io.File
import java.io.IOException
import java.net.URL
import java.time.Duration

class BBCNewsSmokeTestKotlin //: CommonFunctionKotlin()
{
   private var capabilities = DesiredCapabilities()
   private var deviceOsName: String? = null
   private var deviceid: String? = null
   private  var deviceName: String? = null
   private  var appPath: String? = null
   private var appiumPort: String? = null
   private  lateinit var file: File
    private var commonFunctionKotlin = CommonFunctionKotlin()
    private var testutility = Testutility()
    private lateinit var homePageObject: HomePageObject
    private lateinit var androidDriver: AndroidDriver<MobileElement>
    private lateinit var myNewsPageObject: MyNewsPageObject
    private lateinit var vidoePageObject: VideoPageObjects
    private lateinit var popularPageObject: PopularPageObjects
    private lateinit var basePageObjectModel: BasePageObject
    private lateinit var commonpageobjects: CommonPageObjects
    private lateinit var myTopicsPageObject: MyTopicsPageObject


    var workingDirectory = System.getProperty("user.dir")
    private val screenshotpath = "$workingDirectory/Screenshots/"

    @BeforeTest
    fun runTest() {

        try {

            readDeviceDetailsCommandPrompt()
            setUP()
            /**
             *  checking the connection , since on Hive, sometime device wifi might be in turned OFF
             */
            commonFunctionKotlin.checkConnection(androidDriver)
            /**
             *  setting the view mode to Portrait , since on Hive, sometime device might be in Landscape mode
             */
            val orientation = androidDriver.orientation
            if (orientation == ScreenOrientation.LANDSCAPE) {
                androidDriver.rotate(ScreenOrientation.PORTRAIT)
            }

            initialiseobjects()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     *  gets the details of the device, app path , appium port which are passed through command prompt
     */

    private fun readDeviceDetailsCommandPrompt() {
        try {
          //  deviceOsName = System.getProperty("DeviceOS")
            deviceid = System.getProperty("DeviceID")
            deviceName = System.getProperty("DeviceName")
            appPath = System.getProperty("AppPath")
            appiumPort = System.getProperty("AppiumPort")
           // println("Passed The Device OS is $deviceOsName")
            println("Passed The Device ID is $deviceid")
            println("Passed The Device Name is $deviceName")
            println("Passed The Appium port is $appiumPort")
            println("Passed The Application path  is $appPath")
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
            val appiumurl = "http://127.0.0.1:$appiumPort/wd/hub"
            println("Appium Server Address : - $appiumurl")
            capabilities = DesiredCapabilities()
            capabilities.setCapability(MobileCapabilityType.UDID, deviceid)
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "bbcnews")
            capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2")
            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android")
            capabilities.setCapability("appiumversion", "1.8.1")
            capabilities.setCapability("app", appPath) //"/Users/ramakh02/Desktop/tools/APK/BBCNews-5.5.0.35.apk");
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
   private fun initialiseobjects() {
        try {
            homePageObject = HomePageObject()
            PageFactory.initElements(AppiumFieldDecorator(androidDriver), homePageObject)

            myNewsPageObject = MyNewsPageObject()
            PageFactory.initElements(AppiumFieldDecorator(androidDriver), myNewsPageObject)

            basePageObjectModel = BasePageObject()
            PageFactory.initElements(AppiumFieldDecorator(androidDriver), basePageObjectModel)

            vidoePageObject = VideoPageObjects()
            PageFactory.initElements(AppiumFieldDecorator(androidDriver), vidoePageObject)

            popularPageObject = PopularPageObjects()
            PageFactory.initElements(AppiumFieldDecorator(androidDriver), popularPageObject)

            myTopicsPageObject = MyTopicsPageObject()
            PageFactory.initElements(AppiumFieldDecorator(androidDriver), myTopicsPageObject)


            testutility.emptyFolder(screenshotpath)

            // commonFunctionKotlin.startReport("SmokeTest");
            commonFunctionKotlin.createrReportHive("SmokeTest", deviceName.toString(), deviceid.toString())
            //createrReportHive("SmokeTest", Deviceos_Name, Device_Name, Device_id)

            androidDriver.context("NATIVE_APP")
            file = File(screenshotpath)
            val screenshot = file.absolutePath
            println("The ScreenShot Path is $screenshot")




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
            try {
                if (androidDriver.findElement(By.id("bbc.mobile.news.uk.internal:id/error_retry")).isDisplayed) {
                    androidDriver.findElement(By.id("bbc.mobile.news.uk.internal:id/error_retry")).click()

                }
            } catch (e: org.openqa.selenium.NoSuchElementException) {
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


//    @Test(priority = 2, description = "takes the screenshot of the topstories, mynews, popular,video and menu page")
//    @Throws(IOException::class)
//    fun testtakeScreenshotsofPages() {
//        commonFunctionKotlin.tapButton(androidDriver, basePageObjectModel.topstories, false)
//        testutility.AshotScreenshot(androidDriver, "Before", "topstories")
//        commonFunctionKotlin.tapButton(androidDriver, basePageObjectModel.mynews, false)
//        testutility.AshotScreenshot(androidDriver, "Before", "mynews")
//        commonFunctionKotlin.tapButton(androidDriver, basePageObjectModel.popular, false)
//        testutility.AshotScreenshot(androidDriver, "Before", "popular")
//        commonFunctionKotlin.tapButton(androidDriver, basePageObjectModel.video, false)
//        testutility.AshotScreenshot(androidDriver, "Before", "video")
//        commonFunctionKotlin.tapButton(androidDriver, basePageObjectModel.menubutton, false)
//        testutility.AshotScreenshot(androidDriver, "Before", "menu")
//        commonFunctionKotlin.navigateBack(androidDriver)
//    }

    /**
     * after app launches, checks the top stories page and assertion
     */

    @Test(priority = 2, description = "Check the links on the Home page after app launched")
    @Story("Home")
    @Severity(SeverityLevel.CRITICAL)
    fun testCheckHomePage() {
        try {
            androidDriver.runAppInBackground(Duration.ofSeconds(30))
            commonFunctionKotlin.startTest("HomePage", "Checking the HomePage", "Smoke")
            commonFunctionKotlin.tapButton(androidDriver, basePageObjectModel.topstories, false)
            Assert.assertTrue(basePageObjectModel.topstories.isSelected)
            commonFunctionKotlin.elementDisplayed(androidDriver, basePageObjectModel.item_layout_name)
            commonFunctionKotlin.elementDisplayed(androidDriver, basePageObjectModel.item_layout_home_section)
            commonFunctionKotlin.elementDisplayed(androidDriver, basePageObjectModel.item_layout_last_updated)
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

    @Test(priority = 3, description = "Test to check the Mynews page")
    @Story("MyNews")
    @Severity(SeverityLevel.CRITICAL)
    @Throws(Exception::class)
    fun testAllowLocation() {
        try {
            commonFunctionKotlin.startTest("MyNews", "Checking the MyNews", "Smoke")
            commonFunctionKotlin.tapButton(androidDriver, basePageObjectModel.mynews, false)//,file.getAbsolutePath());
            commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.mynews_startButton, false)
            commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.allow_location, false)
            commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.allowlocation_premission, false)
            commonFunctionKotlin.navigateBack(androidDriver)
        } catch (e: AssertionError) {
            e.printStackTrace()
        }

    }



    /**
     * checks the popular page most read
     */
    @Test(priority = 4, description = "Test to check the  popular page")
    @Story("Popular")
    @Severity(SeverityLevel.CRITICAL)
    @Throws(Exception::class)
    fun testPopularPage() {
        try {
            commonFunctionKotlin.startTest("PopularPage", "Checking the Popular", "Smoke")
            commonFunctionKotlin.tapButton(androidDriver, basePageObjectModel.popular, false)//,file.getAbsolutePath());
            Assert.assertTrue(basePageObjectModel.popular.isSelected)
            commonFunctionKotlin.elementDisplayed(androidDriver, popularPageObject.mostread)
            Assert.assertEquals("Most Read", popularPageObject.mostread.text, "Text Matched")
        } catch (e: AssertionError) {
            e.printStackTrace()
        }

    }

    /**
     * checks the popular most watched
     */

    @Test(priority = 5, description = "checking that most watched displayed in popular page")
    @Story("Popular")
    @Severity(SeverityLevel.CRITICAL)
    fun testcheckMostWatched() {
        try {
            commonFunctionKotlin.startTest("PopularPage", "Checking most watched displayed the Popular", "Smoke")
            commonFunctionKotlin.scrolltoElement(androidDriver, popularPageObject.popularmostwatched)
            Assert.assertEquals("Most Watched", popularPageObject.popularmostwatched.text, "Text Matched")
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    /**
     * check the MyNews Page
     *
     */

    @Test(priority = 6, description = "Test to check the Mynews page")
    @Story("MyNews")
    @Severity(SeverityLevel.CRITICAL)
    @Throws(Exception::class)
    fun testMyNewsPage() {
        try {
            commonFunctionKotlin.startTest("MyNews", "Checking the MyNews", "Smoke")
            commonFunctionKotlin.tapButton(androidDriver, basePageObjectModel.mynews, false)//,file.getAbsolutePath());
            Assert.assertTrue(basePageObjectModel.mynews.isSelected)
            commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.mynews_summary)
            commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.mynewstitle)
            commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.addnews_button)
            Assert.assertEquals(myNewsPageObject.mynewstitle_text, myNewsPageObject.mynewstitle.text, "Text matched")
            Assert.assertEquals(myNewsPageObject.mynewssummary_text, myNewsPageObject.mynews_summary.text, "Text matched")
        } catch (e: AssertionError) {
            e.printStackTrace()
        }

    }

    /**
     * Adding the topics to MyNews
     */
    @Test(priority = 7, description = "Test to check the adding the topics to MyNews page")
    @Severity(SeverityLevel.CRITICAL)
    @Throws(Exception::class)
    fun testAddingTopicstoMyNewsPage() {
        try {
            commonFunctionKotlin.startTest("MyNews", "Adding topics to MyNews", "Smoke")
            commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.mynews_startButton, false)
            commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.addtopics, false)

            Assert.assertEquals("Manchester", myNewsPageObject.localnews_displayed.text)
            commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.localnews_displayed)

            commonFunctionKotlin.scrolltoElement(androidDriver, myTopicsPageObject.englandtopic)
            commonFunctionKotlin.tapButton(androidDriver, myTopicsPageObject.englandtopic, false)
            commonFunctionKotlin.textpresent(androidDriver, "England", "added to")

            commonFunctionKotlin.scrolltoElement(androidDriver, myTopicsPageObject.africatopic)
            commonFunctionKotlin.tapButton(androidDriver, myTopicsPageObject.africatopic, false)
            commonFunctionKotlin.textpresent(androidDriver, "Africa", "added to")

        } catch (e: StaleElementReferenceException) {
            e.printStackTrace()
        }

    }



    @Test(priority = 8, description = "Test to check whether selected topics displayed under MyTopics page")
    @Story("MyTopics")
    @Severity(SeverityLevel.CRITICAL)
    fun testCheckAddedTopicsUnderMyTopics() {
        try {
            commonFunctionKotlin.startTest("MyTopics", "Checking Added topics in MyTopics", "Smoke")
            commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.mytopics, false)
            commonFunctionKotlin.elementDisplayed(androidDriver, myTopicsPageObject.Englandtopic)
            commonFunctionKotlin.elementDisplayed(androidDriver, myTopicsPageObject.Africatopic)
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
            commonFunctionKotlin.elementDisplayed(androidDriver, myTopicsPageObject.Englandtopic)
            commonFunctionKotlin.elementDisplayed(androidDriver, myTopicsPageObject.Africatopic)

        } catch (e: Exception) {
        }

    }

    /**
     * Open the Menu items and assert whether links are displayed properly
     */
    @Test(priority = 10, description = "Test to Check the Menu Options ")
    @Story("Menu")
    @Severity(SeverityLevel.CRITICAL)
    @Throws(Exception::class, AssertionError::class)
    fun testMenuPage() {
        commonFunctionKotlin.startTest("Menu", "Checking the Menu Items", "Smoke")
        commonFunctionKotlin.tapButton(androidDriver, basePageObjectModel.menubutton, false)//,file.getAbsolutePath());
        commonFunctionKotlin.elementDisplayed(androidDriver, basePageObjectModel.Appinfo)
        commonFunctionKotlin.elementDisplayed(androidDriver, basePageObjectModel.OtherBBCapps)
        commonFunctionKotlin.elementDisplayed(androidDriver, basePageObjectModel.InternalSettings)
        commonFunctionKotlin.elementDisplayed(androidDriver, basePageObjectModel.settings)
        commonFunctionKotlin.navigateBack(androidDriver)
    }

    /**
     * check the live video on video page
     */

    @Test(priority = 11, description = "Test to check the Video page and selecting the live video for playback and asserting the playback controls")
    @Story("VideoPage")
    @Severity(SeverityLevel.CRITICAL)
    @Throws(Exception::class, AssertionError::class)
    fun testVideoPage() {
        commonFunctionKotlin.startTest("Videopgae", "Checking the Video", "Smoke")
        commonFunctionKotlin.tapButton(androidDriver, basePageObjectModel.video, false)
        // commonFunctionKotlin.AshotScreenshot(androidDriver,"After","VideoPage");
        Assert.assertTrue(basePageObjectModel.video.isSelected())
        // commonFunctionKotlin.elementDisplayed(androidDriver, vidoePageObject.livebbchannel);
        commonFunctionKotlin.tapButton(androidDriver, vidoePageObject.bbcnewsChannel, false)//,file.getAbsolutePath());
        commonFunctionKotlin.elementDisplayed(androidDriver, vidoePageObject.live_media_item_caption)
        try {
            if (!basePageObjectModel.sharestory.isDisplayed) {
                commonFunctionKotlin.verticalSwipe(androidDriver, "Up")
                commonFunctionKotlin.elementDisplayed(androidDriver, basePageObjectModel.sharestory)
            }
        } catch (e: NoSuchElementException) {
        }

        commonFunctionKotlin.tapButton(androidDriver, vidoePageObject.smp_placeholder_play_button, false)

    }


    /**
     * check the live video  seeking
     */
    @Test(priority = 12, description = "Test to check whether you can scrub the Live Video and Live Text shouldn't be displayed")
    @Story("VideoPage")
    @Severity(SeverityLevel.CRITICAL)
    @Throws(AssertionError::class, Exception::class)
    fun testCheckLiveVideoSeeking() {

        commonFunctionKotlin.startTest("VideopageSeeking", "Test to whether you can scrub the Live Video ", "Smoke")

        commonFunctionKotlin.tapButton(androidDriver, vidoePageObject.transportcontrol, false)
        commonFunctionKotlin.tapButton(androidDriver, vidoePageObject.transportcontrol, false)
        commonFunctionKotlin.elementDisplayed(androidDriver, vidoePageObject.smpliveicon)
        commonFunctionKotlin.elementDisplayed(androidDriver, vidoePageObject.smp_volume_button)
        commonFunctionKotlin.elementDisplayed(androidDriver, vidoePageObject.smp_seek_bar)
        commonFunctionKotlin.tapButton(androidDriver, vidoePageObject.bbcnewsChannel, false)
        commonFunctionKotlin.tapButton(androidDriver, vidoePageObject.smp_placeholder_play_button, false)
        try {
            commonFunctionKotlin.videoplaybackseeking(androidDriver, vidoePageObject.smp_seek_bar, 0.30)
            commonFunctionKotlin.isElementPresent(androidDriver, By.id("bbc.mobile.news.uk.internal:id/smp_live_icon"))
        } catch (e: Exception) {
        }

        commonFunctionKotlin.navigateBack(androidDriver)
    }

    /**
     * check to search for a topic
     */
    @Test(priority = 13, description = "Test to check for search results")
    fun testSearchStories() {
        try {
            commonFunctionKotlin.startTest("Search", "Checking for Search Topics", "Smoke")
            commonFunctionKotlin.tapButton(androidDriver, basePageObjectModel.searchbutton, false)
            commonFunctionKotlin.enterText( basePageObjectModel.searchfield, basePageObjectModel.searchtext)
            commonFunctionKotlin.sleepmethod(1000)
            Assert.assertEquals(basePageObjectModel.searchtext, basePageObjectModel.searchkeyword.text, "Text Matched")
            commonFunctionKotlin.tapButton(androidDriver, basePageObjectModel.searchkeyword, false)
            val title = commonFunctionKotlin.getText(basePageObjectModel.headlinetitle)
            Assert.assertEquals(basePageObjectModel.searchtext, title)
            commonFunctionKotlin.tapButton(androidDriver, basePageObjectModel.backButton, false)
            commonFunctionKotlin.navigateBack(androidDriver)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

//    @Test(priority = 14, description = "takes the screenshot of the topstories, mynews, popular,video and menu page")
//    @Throws(IOException::class)
//    fun testtakescreenshotafter()
//    {
//        commonFunctionKotlin.tapButton(androidDriver, basePageObjectModel.topstories, false)
//        testutility.AshotScreenshot(androidDriver, "After", "topstories")
//        commonFunctionKotlin.tapButton(androidDriver, basePageObjectModel.mynews, false)
//        testutility.AshotScreenshot(androidDriver, "After", "mynews")
//        commonFunctionKotlin.tapButton(androidDriver, basePageObjectModel.popular, false)
//        testutility.AshotScreenshot(androidDriver, "After", "popular")
//        commonFunctionKotlin.tapButton(androidDriver, basePageObjectModel.video, false)
//        testutility.AshotScreenshot(androidDriver, "After", "video")
//        commonFunctionKotlin.tapButton(androidDriver, basePageObjectModel.menubutton, false)
//        testutility.AshotScreenshot(androidDriver, "After", "menu")
//        commonFunctionKotlin.navigateBack(androidDriver)
//    }
//
//


//    @Test(priority = 15, description = "Compares the images")
//    @Throws(IOException::class)
//    fun testcomparetheimages()
//    {
//        commonFunctionKotlin.startTest("CompraeImage", "Compares the HomePage", "Smoke")
//         testutility.comparetwoimages()
//
//    }

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
        testutility.emptyFolder("./Screenshots/Before")
        testutility.emptyFolder("./Screenshots/After")
        androidDriver.closeApp()
        androidDriver.removeApp("bbc.mobile.news.uk.internal")
        androidDriver.quit()

    }
}
package com.bbcnews.automation.scripts

import com.aventstack.extentreports.ExtentTest
import com.bbcnews.automation.testutils.CharlesProxy
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
import org.openqa.selenium.ScreenOrientation
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.support.PageFactory
import org.testng.ITestResult
import org.testng.annotations.AfterMethod
import org.testng.annotations.AfterTest
import org.testng.annotations.BeforeTest
import org.testng.annotations.Test

import java.io.File
import java.io.IOException
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date

class BBCNewsStatsTest {

    private var capabilities = DesiredCapabilities()
    private var deviceosName: String? = null
    private var deviceid: String? = null
    private var deviceName: String? = null
    private var appPath: String? = null
    private var appiumPort: String? = null
    private lateinit var file: File

    private var commonFunctionKotlin = CommonFunctionKotlin()
    private var testutility = Testutility()

    private lateinit var homePageObject: HomePageObject
    private lateinit var androidDriver: AndroidDriver<MobileElement>
    private lateinit var myNewsPageObject: MyNewsPageObject
    //private lateinit var basePageObject: BasePageObject
    private lateinit var vidoePageObject: VideoPageObjects
    private lateinit var popularPageObject: PopularPageObjects
    private lateinit var basePageObjectModel: BasePageObject
    private lateinit var commonpageobjects: CommonPageObjects

    internal var charlesProxy = CharlesProxy()
    var statsTestData = StatsTestData()


    @BeforeTest
    @Throws(Exception::class)
    fun RunTest() {

        try {

            readDeviceDetailsCommandPrompt()
            charlesProxy.startCharles()
            setUP()
            initialiseobjects()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun readDeviceDetailsCommandPrompt() {

        try {
            deviceosName = System.getProperty("DeviceOS")
            deviceid = System.getProperty("DeviceID")
            deviceName = System.getProperty("DeviceName")
            appPath = System.getProperty("AppPath")
            appiumPort = System.getProperty("AppiumPort")
            println("Passed The Device OS is $deviceosName")
            println("Passed The Device ID is $deviceid")
            println("Passed The Device Name is $deviceName")
            println("Passed The Appium port is $appiumPort")
            println("Passed The Application path  is $appPath")
        } catch (e: Exception) {
            e.printStackTrace()

        }

    }

    private  fun setUP() {
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
            capabilities.setCapability("ignoreUnimportantViews",true)
            androidDriver = AndroidDriver(URL(appiumurl), capabilities)
        } catch (e: Exception) {
        }
    }

    private fun initialiseobjects() = try {
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

        testutility.emptyFolder(screenshotpath)


        commonFunctionKotlin.createrReportHive("Regression", deviceName.toString(), deviceid.toString())


        androidDriver.context("NATIVE_APP")
        file = File(screenshotpath)
        val screenshot = file.absolutePath
        println("The ScreenShot Path is $screenshot")

        val orientation = androidDriver.orientation
        if (orientation != ScreenOrientation.LANDSCAPE) {
            androidDriver.rotate(ScreenOrientation.PORTRAIT)
        }
        else { }
    } catch (e: NullPointerException) {
        e.printStackTrace()
    } catch (e: Exception) {
        e.printStackTrace()
    }

    @Test(priority = 1, description = "launching the app and start the Charles ")
    @Story("Home")
    @Severity(SeverityLevel.CRITICAL)
    @Throws(Exception::class)
    fun testOpenNewsApp() {
        try {


            commonFunctionKotlin.tapButton(androidDriver, basePageObjectModel.okbutton, false)
            commonFunctionKotlin.tapButton(androidDriver, basePageObjectModel.nothanksbutton, false)
            commonFunctionKotlin.tapButton(androidDriver, basePageObjectModel.menubutton, false)
            commonFunctionKotlin.tapButton(androidDriver, basePageObjectModel.internalsettings, false)
            commonFunctionKotlin.tapButton(androidDriver, basePageObjectModel.cpscontent, false)
            commonFunctionKotlin.tapButton(androidDriver, basePageObjectModel.trevortest, false)
            commonFunctionKotlin.tapButton(androidDriver, basePageObjectModel.navigate_back, false)
            commonFunctionKotlin.tapButton(androidDriver, basePageObjectModel.reloadButton, false)
            charlesProxy.startcharlesSession()

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    @Test(priority = 2, description = "Test to navigate around app for generate stats")
    @Story("Home")
    @Severity(SeverityLevel.CRITICAL)
    fun testCheckHomePage() {
        try {
            //  androidDriver.runAppInBackground(Duration.ofSeconds(15));

            //  androidDriver.currentActivity();
            commonFunctionKotlin.startTest("HomePage", "Checking the HomePage", "Smoke")
            commonFunctionKotlin.tapButton(androidDriver, basePageObjectModel.topstories, false)
            commonFunctionKotlin.tapButton(androidDriver,basePageObjectModel.mynews, false)
            commonFunctionKotlin.tapButton(androidDriver, basePageObjectModel.popular, false)
            commonFunctionKotlin.tapButton(androidDriver, basePageObjectModel.video, false)
            commonFunctionKotlin.tapButton(androidDriver, basePageObjectModel.searchbutton, false)

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    @Test(priority = 3, description = "Test to stop the charles recording ")
    @Throws(Exception::class)
    fun testStopcharlesRecord() {

        try {
            charlesProxy.stopCharlesSession()
            Thread.sleep(2000)
        } catch (e: AssertionError) {
            throw e
        }

    }


    @Test(priority = 4, description = "Test to download the charles recording session")
    @Throws(Exception::class)
    fun testDownloadcharlesRecord() {

        try {
            charlesProxy.downloadCharlesSession()
            Thread.sleep(2000)
        } catch (e: AssertionError) {
            throw e
        }

    }

    @Test(priority = 5, description = "Test to convert the charles session to CSV format")
    @Throws(Exception::class)
    fun testConvertCharlesSessiontoCSV() {

        try {
            charlesProxy.converttoCSV()
            Thread.sleep(2000)
            charlesProxy.stopCharles()
        } catch (e: AssertionError) {
            throw e
        }

    }


    @Test(priority = 6, description = "Test to check the compared the downloaded stats")
    fun testBBVNewsBasicStats() {
        commonFunctionKotlin.startTest("BasicStats", "Test to check the compared the downloaded stats", "Stat's")
        try {
            statsTestData.comapre_StatsData(statsTestData.csvFile, statsTestData.BBCnewsBasicstats)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }


    @Test(priority = 7, description = "Test to check the TopStores Page downloaded stats")
    fun testCheckTopStoresStats() {
        commonFunctionKotlin.startTest("TopStores", "Test to check the TopStores Page downloaded stats", "TopStoriesStat's")
        try {
            statsTestData.comapre_StatsData(statsTestData.csvFile, statsTestData.topstores)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    @Test(priority = 8, description = "Test to check the MyNews Page downloaded stats")
    fun testCheckMyNewsStats() {
        commonFunctionKotlin.startTest("MyNews", "Test to check the MyNews Page downloaded stats", "MyNews Stat's")
        try {
            statsTestData.comapre_StatsData(statsTestData.csvFile, statsTestData.mynews)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    @Test(priority = 9, description = "Test to check the Popular Page downloaded stats")
    fun testCheckPopularPageStats() {
        commonFunctionKotlin.startTest("Popular", "Test to check the Popular Page downloaded stats", "Popular Stat's")
        try {
            statsTestData.comapre_StatsData(statsTestData.csvFile, statsTestData.popularpage)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    @Test(priority = 10, description = "Test to check the Video Page downloaded stats")
    fun testCheckVideoPageStats() {
        commonFunctionKotlin.startTest("Video", "Test to check the Video Page downloaded stats", "Video Stat's")
        try {
            statsTestData.comapre_StatsData(statsTestData.csvFile, statsTestData.videopage)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    @Test(priority = 11, description = "Test to check the Search Page downloaded stats")
    fun testCheckPopularStats() {
        commonFunctionKotlin.startTest("Search", "Test to check the Search Page downloaded stats", "Search Stat's")
        try {
            statsTestData.comapre_StatsData(statsTestData.csvFile, statsTestData.searchstats)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }


    @AfterMethod
    fun getResult(result: ITestResult) {
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

    companion object {

        private val curDate = Date()
        private val format = SimpleDateFormat("yyyy-MM-dd")
        private val DateToStr = format.format(curDate)
        var workingDirectory = System.getProperty("user.dir")
        private val screenshotpath = "$workingDirectory/Screenshots/"
    }


}



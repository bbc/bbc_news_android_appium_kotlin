package com.bbcnews.automation.scripts

import com.aventstack.extentreports.ExtentTest
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
import org.testng.Assert
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

class BBCNewsHindiSmokeTest {



    private var capabilities = DesiredCapabilities()

    private var deviceid: String? = null
    private var deviceName: String? = null
    private var AppPath: String? = null
    private var AppiumPort: String? = null
    private lateinit var file: File
    internal var test: ExtentTest? = null

    private lateinit var homePageObject: HomePageObject
    private lateinit var androidDriver: AndroidDriver<MobileElement>
    private lateinit var bbcNewsHindiPageObject: BBCNewsHindiPageObject

    private var commonFunctionKotlin = CommonFunctionKotlin()
    private var testutility = Testutility()


    @BeforeTest
    @Throws(Exception::class)
    fun runTest() {

        try {

            readDeviceDetailsCommandPrompt()
            setUP()
            commonFunctionKotlin.checkConnection(androidDriver)
            launchBBCNews()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

   private  fun readDeviceDetailsCommandPrompt() = try {
        // Deviceos_Name = System.getProperty("DeviceOS");
        deviceid = System.getProperty("DeviceID")
        deviceName = System.getProperty("DeviceName")
        AppPath = System.getProperty("AppPath")
        AppiumPort = System.getProperty("AppiumPort")
        // System.out.println("Passed The Device OS is " + Deviceos_Name);
        System.out.println("Passed The Device ID is $deviceid")
        System.out.println("Passed The Device Name is $deviceName")
        System.out.println("Passed The Appium port is $AppiumPort")
        System.out.println("Passed The Application path  is $AppPath")
    } catch (e: Exception) {
        e.printStackTrace()

    }


   private fun setUP() {
        try {

            val appium_url = "http://127.0.0.1:$AppiumPort/wd/hub"
            System.out.println("Appium Server Address : - $appium_url")
            capabilities = DesiredCapabilities()
            capabilities.setCapability(MobileCapabilityType.UDID, deviceid)
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "bbcnews")
            capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2")
            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android")
            capabilities.setCapability("appiumversion", "1.8.1")
            capabilities.setCapability("app", AppPath) //"/Users/ramakh02/Desktop/tools/APK/BBCNews-5.5.0.35.apk");
            capabilities.setCapability("appPackage", "uk.co.bbc.hindi")
            capabilities.setCapability("appActivity", "bbc.mobile.news.v3.app.TopLevelActivity")
            capabilities.setCapability(MobileCapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, true);
            capabilities.setCapability("autoAcceptAlerts", true);
            capabilities.setCapability("--session-override", true)
            androidDriver = AndroidDriver<MobileElement>(URL(appium_url), capabilities)
        } catch (e: Exception) {
        }

    }

   private fun launchBBCNews() {
        try {
            homePageObject = HomePageObject()
            PageFactory.initElements(AppiumFieldDecorator(androidDriver), homePageObject)

            bbcNewsHindiPageObject = BBCNewsHindiPageObject()
            PageFactory.initElements(AppiumFieldDecorator(androidDriver), bbcNewsHindiPageObject)

            testutility.emptyFolder(screenshotpath)

            commonFunctionKotlin.createrReportHive("Regression", deviceName.toString(), deviceid.toString())

            androidDriver.context("NATIVE_APP")
            file = File(screenshotpath)
            val screenshot = file.absolutePath
            System.out.println("The ScreenShot Path is $screenshot")

            val orientation = androidDriver.orientation
            if (orientation == ScreenOrientation.LANDSCAPE) {
                androidDriver.rotate(ScreenOrientation.PORTRAIT)
            }


        } catch (e: NullPointerException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    @Test(priority = 1, description = "launching the app ")
    @Story("Home")
    @Severity(SeverityLevel.CRITICAL)
    @Throws(Exception::class)
    fun testOpenNewsApp() {
        try {

            Assert.assertEquals("ओके", bbcNewsHindiPageObject.bbchindi_okbutton.text, "Text Matched")
            Assert.assertEquals("बीबीसी न्यूज़ आपको नोटिफ़िकेशंस भेजना चाहता है. आप कभी भी सेटिंग्स में जाकर बदलाव कर सकते हैं.", bbcNewsHindiPageObject.bbchindi_message.text)
            commonFunctionKotlin.tapButton(androidDriver, bbcNewsHindiPageObject.bbchindi_okbutton, false)
            commonFunctionKotlin.tapButton(androidDriver, bbcNewsHindiPageObject.nothanksbutton, false)

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    @Test(priority = 2, description = "Check the links on the Home page after app launched")
    @Story("Home")
    @Severity(SeverityLevel.CRITICAL)
    fun testCheckHomePage() = try {
        commonFunctionKotlin.startTest("HomePage", "Checking the HomePage", "Smoke")

        commonFunctionKotlin.getElements(androidDriver, "uk.co.bbc.hindi:id/item_layout_name")
        commonFunctionKotlin.getElements(androidDriver, "uk.co.bbc.hindi:id/item_layout_last_updated")

        System.out.println("The Title is " + bbcNewsHindiPageObject.item_layout_name.text)
        System.out.println("The Last Update is " + bbcNewsHindiPageObject.item_layout_last_updated.text)

        commonFunctionKotlin.tapButton(androidDriver, bbcNewsHindiPageObject.bbchindi_homepage, false)
        Assert.assertTrue(bbcNewsHindiPageObject.bbchindi_homepage.isSelected)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.bbchindi_homepage)

        commonFunctionKotlin.tapButton(androidDriver, bbcNewsHindiPageObject.bbchindi_india, false)
        Assert.assertTrue(bbcNewsHindiPageObject.bbchindi_india.isSelected)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.bbchindi_india)

        commonFunctionKotlin.tapButton(androidDriver, bbcNewsHindiPageObject.bbchindi_international, false)
        Assert.assertTrue(bbcNewsHindiPageObject.bbchindi_international.isSelected)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.bbchindi_international)

        commonFunctionKotlin.tapButton(androidDriver, bbcNewsHindiPageObject.bbchindi_entertainment, false)
        Assert.assertTrue(bbcNewsHindiPageObject.bbchindi_entertainment.isSelected)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.bbchindi_entertainment)

        commonFunctionKotlin.tapButton(androidDriver, bbcNewsHindiPageObject.bbchindi_sports, false)
        Assert.assertTrue(bbcNewsHindiPageObject.bbchindi_sports.isSelected)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.bbchindi_sports)

        commonFunctionKotlin.tapButton(androidDriver, bbcNewsHindiPageObject.bbchindi_radio, false)
        Assert.assertTrue(bbcNewsHindiPageObject.bbchindi_radio.isSelected)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.bbchindi_radio)

        commonFunctionKotlin.tapButton(androidDriver, bbcNewsHindiPageObject.bbchindi_sciencetechnology, false)
        Assert.assertTrue(bbcNewsHindiPageObject.bbchindi_sciencetechnology.isSelected)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.bbchindi_sciencetechnology)

        commonFunctionKotlin.tapButton(androidDriver, bbcNewsHindiPageObject.bbchindi_lookat, false)
        Assert.assertTrue(bbcNewsHindiPageObject.bbchindi_lookat.isSelected)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.bbchindi_lookat)

        commonFunctionKotlin.tapButton(androidDriver, bbcNewsHindiPageObject.bbchindi_thephotos, false)
        Assert.assertTrue(bbcNewsHindiPageObject.bbchindi_thephotos.isSelected)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.bbchindi_thephotos)

        commonFunctionKotlin.tapButton(androidDriver, bbcNewsHindiPageObject.bbchindi_social, false)
        Assert.assertTrue(bbcNewsHindiPageObject.bbchindi_social.isSelected)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.bbchindi_social)

    } catch (e: Exception) {
        e.printStackTrace()
    }


    @Test(priority = 3, description = "Checking the MoreOptions Menu")
    @Story("MoreOptions")
    @Severity(SeverityLevel.CRITICAL)
    @Throws(Exception::class)
    fun testMenuItems() {
        try {
            commonFunctionKotlin.startTest("MoreOptions", "Checking the MoreOptions Menu", "Smoke")
            commonFunctionKotlin.tapButton(androidDriver, bbcNewsHindiPageObject.bbc_moreoptions, false)
            commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.bbchindi_help)
            //  commonFunctionKotlin.elementDisplayed(androidDriver,bbcNewsHindiPageObject.bbchindi_Internalsettings);
            commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.bbchindi_settings)
            commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.bbchindi_pleasecontact)
            commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.bbchindi_OtherBBCapplications)
            commonFunctionKotlin.navigateBack(androidDriver)


        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    @Test(priority = 4, description = "Checking the More Settings Options Menu")
    @Story("MoreOptions")
    @Severity(SeverityLevel.CRITICAL)
    @Throws(Exception::class)
    fun testMoreSettingsOptions() {
        try {
            commonFunctionKotlin.startTest("MoreOptions", "Checking the More Settings Options Menu", "Smoke")
            commonFunctionKotlin.tapButton(androidDriver, bbcNewsHindiPageObject.bbchindi_Moresettings, false)
            // commonFunctionKotlin.elementDisplayed(androidDriver,bbcNewsHindiPageObject.bbchindi_localnews);
            commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.bbchindi_topics)


        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    @Test(priority = 5, description = "Checking the More Settings Options Topics")
    @Story("MoreOptions-Topics")
    @Severity(SeverityLevel.CRITICAL)
    @Throws(Exception::class)
    fun testMoreSettingsOptions_Topics() {
        try {
            commonFunctionKotlin.startTest("MoreOptionsTopics", "Checking the More Settings Options Topics", "Smoke")
            //  commonFunctionKotlin.tapButton(androidDriver,bbcNewsHindiPageObject.bbchindi_topics_collapsegroup,false);
            commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.hindihomepage)
            commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.hindibharath)
            commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.hindienrairnment)
            commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.hindiinternatonal)
            commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.hindilookat)
            commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.hindiphotos)
            commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.hindiscience)
            commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.hindiphotos)
            commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.hindisocial)
        } catch (e: Exception) {
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
        private val screenshotpath = workingDirectory + "/Screenshots/"
    }
}
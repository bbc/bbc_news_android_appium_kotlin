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
import org.openqa.selenium.By
import org.openqa.selenium.ScreenOrientation
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.support.PageFactory
import org.testng.Assert
import org.testng.Assert.assertTrue
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

        deviceid = System.getProperty("DeviceID")
        deviceName = System.getProperty("DeviceName")
        AppPath = System.getProperty("AppPath")
        AppiumPort = System.getProperty("AppiumPort")
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
            capabilities.setCapability("app", AppPath)
            capabilities.setCapability("appPackage", "uk.co.bbc.hindi.internal")
            capabilities.setCapability("appActivity", "bbc.mobile.news.v3.app.TopLevelActivity")
            capabilities.setCapability(MobileCapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, true)
            capabilities.setCapability("autoAcceptAlerts", true)
            capabilities.setCapability("--session-override", true)
            androidDriver = AndroidDriver(URL(appium_url), capabilities)
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
   fun testCheckHomePage() = try {
       commonFunctionKotlin.startTest("HomePage", "Checking the HomePage", "Smoke");
       commonFunctionKotlin.tapButton(androidDriver,bbcNewsHindiPageObject.bbchindi_homepage,false)
       assertTrue(bbcNewsHindiPageObject.bbchindi_homepage.isSelected)
       commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.mainitem_layout_name)
       commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.mainitem_layout_last_updated)

       commonFunctionKotlin.tapButton(androidDriver, bbcNewsHindiPageObject.article, false)
       if(!bbcNewsHindiPageObject.frontpage.isDisplayed)
       {
           System.out.println("Scrolling up")
           commonFunctionKotlin.verticalSwipe(androidDriver, "Up")
       }

       commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.imageitembadge)
       commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.headlinetitle)
       commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.headlineinfo)
       commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.headlineauthorname)
       commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.headlineauthortitle)
       commonFunctionKotlin.navigateBack(androidDriver)

   } catch (e:Exception ) {
   }

    @Test(priority = 3, description = "checking the india page")
    @Throws(Exception::class)
    fun testIndiaPage() {
        commonFunctionKotlin.startTest("IndiaPage", "Checking the IndiaPage", "Smoke")
        commonFunctionKotlin.tapButton(androidDriver, bbcNewsHindiPageObject.bbchindi_india, false)
        assertTrue(bbcNewsHindiPageObject.bbchindi_india.isSelected)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.mainitem_layout_name)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.mainitem_layout_last_updated)

        commonFunctionKotlin.tapButton(androidDriver, bbcNewsHindiPageObject.article, false)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.imageitembadge)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.headlinetitle)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.headlineinfo)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.headlineauthorname)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.headlineauthortitle)

        commonFunctionKotlin.scrolltoElement(androidDriver, bbcNewsHindiPageObject.relatedtopics)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.relatedarticles)

        commonFunctionKotlin.navigateBack(androidDriver)
    }


    @Test(priority = 3, description = "checking the international page")
    @Throws(Exception::class)
    fun testInternationalPage() {
        commonFunctionKotlin.startTest("InternationalaPage", "Checking the InternationalPage", "Smoke")
        commonFunctionKotlin.tapButton(androidDriver, bbcNewsHindiPageObject.bbchindi_international, false)
        assertTrue(bbcNewsHindiPageObject.bbchindi_international.isSelected)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.mainitem_layout_name)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.mainitem_layout_last_updated)

        commonFunctionKotlin.tapButton(androidDriver, bbcNewsHindiPageObject.article, false)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.headlinetitle)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.headlineinfo)

        commonFunctionKotlin.navigateBack(androidDriver)
    }


    @Test(priority = 4, description = "checking the Entertainment page")
    @Throws(Exception::class)
    fun testEntertainmentPage() {
        commonFunctionKotlin.startTest("EntertainmentPage", "Checking the EntertainmentPage", "Smoke")
        commonFunctionKotlin.tapButton(androidDriver, bbcNewsHindiPageObject.bbchindi_entertainment, false)
        assertTrue(bbcNewsHindiPageObject.bbchindi_entertainment.isSelected)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.mainitem_layout_name)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.mainitem_layout_last_updated)

        commonFunctionKotlin.tapButton(androidDriver, bbcNewsHindiPageObject.article, false)
        //commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.imageitembadge);
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.headlinetitle)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.headlineinfo)


        commonFunctionKotlin.scrolltoElement(androidDriver, bbcNewsHindiPageObject.relatedtopics)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.relatedarticles)

        commonFunctionKotlin.navigateBack(androidDriver)
    }

    @Test(priority = 5, description = "checking the Sports page")
    @Throws(Exception::class)
    fun testSportsPage() {
        commonFunctionKotlin.startTest("Sports", "Checking the Sports", "Smoke")
        commonFunctionKotlin.tapButton(androidDriver, bbcNewsHindiPageObject.bbchindi_sports, false)
        assertTrue(bbcNewsHindiPageObject.bbchindi_sports.isSelected)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.mainitem_layout_name)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.mainitem_layout_last_updated)

        commonFunctionKotlin.tapButton(androidDriver, bbcNewsHindiPageObject.article, false)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.imageitembadge)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.headlinetitle)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.headlineinfo)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.imageitemcaption)


        commonFunctionKotlin.scrolltoElement(androidDriver, bbcNewsHindiPageObject.relatedtopics)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.relatedarticles)

        commonFunctionKotlin.navigateBack(androidDriver)
    }


    @Test(priority = 6, description = "checking the Radio page")
    @Throws(Exception::class)
    fun testRadioPage() {
        commonFunctionKotlin.startTest("Radio", "Checking the Radio", "Smoke")
        commonFunctionKotlin.tapButton(androidDriver, bbcNewsHindiPageObject.bbchindi_radio, false)
        assertTrue(bbcNewsHindiPageObject.bbchindi_radio.isSelected)

        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.headlinetitle)
        Assert.assertEquals("सुनिए", bbcNewsHindiPageObject.headlinetitle.text)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.headlineinfo)
        Assert.assertEquals("12 अप्रै 2018", bbcNewsHindiPageObject.headlineinfo.text)

        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.radiopagetext)
        Assert.assertEquals("नमस्कार भारत  (06.30IST - 07.00IST)", bbcNewsHindiPageObject.radiopagetext.text)

        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.radiopagetextdaily)
        Assert.assertEquals("दिनभर (19.30IST - 20.00IST)", bbcNewsHindiPageObject.radiopagetextdaily.text)

    }

    @Test(priority = 7, description = "checking the Science&Technology page")
    @Throws(Exception::class)
    fun testScienceTechnologyPage() {
        commonFunctionKotlin.startTest("Science&Technology", "Checking the Science&Technology", "Smoke")
        commonFunctionKotlin.tapButton(androidDriver, bbcNewsHindiPageObject.bbchindi_sciencetechnology, false)
        assertTrue(bbcNewsHindiPageObject.bbchindi_sciencetechnology.isSelected)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.mainitem_layout_name)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.mainitem_layout_last_updated)

        commonFunctionKotlin.tapButton(androidDriver, bbcNewsHindiPageObject.article, false)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.headlinetitle)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.headlineinfo)

        commonFunctionKotlin.scrolltoElement(androidDriver, bbcNewsHindiPageObject.relatedtopics)

        commonFunctionKotlin.navigateBack(androidDriver)

    }


    @Test(priority = 8, description = "checking the Science&Technology page")
    @Throws(Exception::class)
    fun testLookAtPage() {
        commonFunctionKotlin.startTest("LookAt", "Checking the Science&LookAt Page", "Smoke")
        commonFunctionKotlin.tapButton(androidDriver, bbcNewsHindiPageObject.bbchindi_lookat, false)
        assertTrue(bbcNewsHindiPageObject.bbchindi_lookat.isSelected)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.mainitem_layout_name)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.mainitem_layout_last_updated)


        commonFunctionKotlin.tapButton(androidDriver, bbcNewsHindiPageObject.article, false)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.playbutton)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.mediaitemcaption)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.headlinetitle)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.headlineinfo)

        commonFunctionKotlin.tapButton(androidDriver, bbcNewsHindiPageObject.playbutton, false)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.volumebutton)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.Fullscreenbutton)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.seekbar)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.pausebutton)

        commonFunctionKotlin.navigateBack(androidDriver)

    }

    @Test(priority = 9, description = "checking the Pictures  page")
    @Throws(Exception::class)
    fun testPicturesPage() {
        commonFunctionKotlin.startTest("Pictures", "Checking the Pictures Page", "Smoke")
        commonFunctionKotlin.tapButton(androidDriver, bbcNewsHindiPageObject.bbchindi_thephotos, false)
        assertTrue(bbcNewsHindiPageObject.bbchindi_thephotos.isSelected)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.mainitem_layout_name)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.mainitem_layout_last_updated)


        commonFunctionKotlin.tapButton(androidDriver, bbcNewsHindiPageObject.article, false)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.picturestitle)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.picturessubtitle)

        for (i in 0..6) {
            val images = androidDriver.findElement(By.xpath("//android.widget.ImageView[@index='$i']"))
            images.isDisplayed
        }

        commonFunctionKotlin.navigateBack(androidDriver)

    }


    @Test(priority = 10, description = "checking the Social  page")
    @Throws(Exception::class)
    fun testSocialPage() {
        commonFunctionKotlin.startTest("Social", "Checking the Social Page", "Smoke")
        commonFunctionKotlin.tapButton(androidDriver, bbcNewsHindiPageObject.bbchindi_social, false)
        assertTrue(bbcNewsHindiPageObject.bbchindi_social.isSelected)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.mainitem_layout_name)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.mainitem_layout_last_updated)

        commonFunctionKotlin.tapButton(androidDriver, bbcNewsHindiPageObject.article, false)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.headlinetitle)
        commonFunctionKotlin.elementDisplayed(androidDriver, bbcNewsHindiPageObject.headlineinfo)

        commonFunctionKotlin.navigateBack(androidDriver)

    }


    @Test(priority = 11, description = "Checking the MoreOptions Menu")
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

    @Test(priority = 12, description = "Checking the More Settings Options Menu")
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


    @Test(priority = 13, description = "Checking the More Settings Options Topics")
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
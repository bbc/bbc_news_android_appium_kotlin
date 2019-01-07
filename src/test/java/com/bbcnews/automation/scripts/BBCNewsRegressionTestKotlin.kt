package com.bbcnews.automation.scripts

import com.bbcnews.automation.commonfunctions.CommonFunctionKotlin
import com.bbcnews.automation.commonfunctions.CommonFunctions
import com.bbcnews.automation.pageobjects.*
import com.bbcnews.automation.testutils.AppiumStart
import io.appium.java_client.MobileElement
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.pagefactory.AppiumFieldDecorator
import io.appium.java_client.remote.MobileCapabilityType
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
import java.util.*

class BBCNewsRegressionTestKotlin
{

    private var capabilities = DesiredCapabilities()
    private var Deviceos_Name: String? = null
    private var Device_id: String? = null
    private var Device_Name: String? = null
    private var App_Path: String? = null
    private var Appium_Port: String? = null
    private lateinit var file: File

    internal var commonFunctionKotlin = CommonFunctionKotlin()

    //abstract  var homePageObject: HomePageObject
    // abstract  var myNewsPageObject: MyNewsPageObject
    //abstract  var basePageObject: BasePageObject
    //abstract  var vidoePageObject: VidoePageObject
    // abstract  var popularPageObject: PopularPageObject

    private lateinit var homePageObject: HomePageObject
    private lateinit var androidDriver: AndroidDriver<MobileElement>
    private lateinit var myNewsPageObject: MyNewsPageObject
    public lateinit var basePageObject: BasePageObject
    private lateinit var vidoePageObject: VidoePageObject
    private lateinit var popularPageObject: PopularPageObject
    public lateinit var basepageobjectkotlin: BBCNewsSmokeTestKotlin

    //private lateinit var commonFunctionKotlin: CommonFunctionKotlin
    internal var appiumStart = AppiumStart()
    var workingDirectory = System.getProperty("user.dir")
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

    fun LaunchBBCNews() = try {
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
        commonFunctionKotlin.createrReportHive("Regression", Deviceos_Name.toString(), Device_Name.toString(), Device_id.toString())
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


    @Test(priority = 1, description = "Launching the app")
    fun testOpenNewsApp() {
        try {

            commonFunctionKotlin.tapButton(androidDriver, basePageObject.okbutton, false)
            commonFunctionKotlin.tapButton(androidDriver, basePageObject.nothanksbutton, false)

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    @Test(priority = 2, description = "Test to check whether all links present on Home Page")
    fun openBBCNewsApp() {
        try {
            commonFunctionKotlin.startTest("HomePage", "Checking the HomePage", "Regression")
            commonFunctionKotlin.IselementSelected(androidDriver,basePageObject.topstories)
            commonFunctionKotlin.elementDisplayed(androidDriver, basePageObject.topstories)
            commonFunctionKotlin.elementDisplayed(androidDriver, basePageObject.mynews)
            commonFunctionKotlin.elementDisplayed(androidDriver, basePageObject.popular)
            commonFunctionKotlin.elementDisplayed(androidDriver, basePageObject.video)
            commonFunctionKotlin.elementDisplayed(androidDriver, basePageObject.search)
            commonFunctionKotlin.elementDisplayed(androidDriver, basePageObject.menubutton)

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    @Test(priority = 3, description = "Test to check Video of the day displayed and swipe through all the videos")
    @Throws(Exception::class)
    fun testVideoofthedayDisplayed() {
        commonFunctionKotlin.startTest("HomePage", "Checking the VideoOfTheDay", "Regression")
        commonFunctionKotlin.scrolltoElement(androidDriver, homePageObject.videoOftheDay_watch)
        //        CommonFunctions.linksDisplayed(androidDriver,homePageObject.watchvideo);
        //        CommonFunctions.linksDisplayed(androidDriver,homePageObject.promocounter);
        commonFunctionKotlin.tapButton(androidDriver, homePageObject.videooftheday_button, false)//,file.getAbsolutePath());
        commonFunctionKotlin.scrolltoEndofStories(androidDriver, homePageObject.newstream_progress, file.absolutePath, homePageObject.checkback_later)
        commonFunctionKotlin.navigateBack(androidDriver)

    }


    @Test(priority = 4, description = "Test to scroll to Politics topic on home page and tapp on the politics topic")
    fun testToCheckPoliticsTopic() {
       // CommonFunctions.startTest(androidDriver, "Scroll to a Political Topic", "HomePage", false, "Politics")//file.getAbsolutePath(),"Politics");
        commonFunctionKotlin.startTest("HomePage", "Scrolling to a Political Topic", "Regression")
        commonFunctionKotlin.scrolltoElement(androidDriver, homePageObject.top_stories_Politics)
        commonFunctionKotlin.tapButton(androidDriver, homePageObject.top_stories_Politics, false)//,file.getAbsolutePath());
        commonFunctionKotlin.navigateBack(androidDriver)
    }

    @Test(priority = 5, description = "Test to check MyNews page and asserting whether all links displayed")
    @Throws(Exception::class)
    fun testMyNews() {
        var mynewstitle_text = "Add Topics to create your own personal news feed"

        var mynewssummary_text = "All the latest stories from your topics will appear here."

        commonFunctionKotlin.startTest("MyNews", "Test to check MyNews page", "Regression")
        commonFunctionKotlin.tapButton(androidDriver, basePageObject.mynews, false)//,file.getAbsolutePath());
        commonFunctionKotlin.IselementSelected(androidDriver,basePageObject.mynews)
        commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.mynews_summary)
        commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.mynewstitle)
        commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.addnews_button)
        Assert.assertEquals(mynewstitle_text, myNewsPageObject.mynewstitle.text, "Text Mesaaged")
        Assert.assertEquals(mynewssummary_text, myNewsPageObject.mynews_summary.text, "Text Mesaaged")
        CommonFunctions.endTest()
    }


    @Test(priority = 6, description = "Test to check on My News Add Topic screen and asserting all links are displayed")
    @Throws(Exception::class)
    fun testAddingTopicsPage() {
        var mytopic_emptyview_text = "Your added topics will be displayed here"
        commonFunctionKotlin.startTest("MyNews", "Checking EditMyTopics Page", "Regression")
        commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.mynews_startButton, false)//,file.getAbsolutePath());
        commonFunctionKotlin.IselementSelected(androidDriver, myNewsPageObject.addtopics)
        commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.mytopics)
        commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.location_button)
        commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.editMyTopics)
        commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.localnews)
        commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.mytopics,false)//,file.getAbsolutePath());
        commonFunctionKotlin.IselementSelected(androidDriver, myNewsPageObject.mytopics)
        Assert.assertEquals(mytopic_emptyview_text, myNewsPageObject.mytopic_emptyview.text, "Text Mesaaged")
        CommonFunctions.endTest()
    }


    @Test(priority = 7, description = "Test to check adding Topics under MyNews")
    @Throws(Exception::class)
    fun testAddingTopicstoMyNewsPage() {

        commonFunctionKotlin.startTest("Mynews","Check Adding Topics MyNews","Regression")
        commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.addtopics, false)//,file.getAbsolutePath());
        commonFunctionKotlin.scrolltoElement(androidDriver, myNewsPageObject.Walestopic)
        commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.Walestopic, false)//,file.getAbsolutePath());
        commonFunctionKotlin.scrolltoElement(androidDriver, myNewsPageObject.Asiatopic)
        commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.Asiatopic, false)//,file.getAbsolutePath());
        commonFunctionKotlin.scrolltoElement(androidDriver, myNewsPageObject.Europeantopic)
        commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.Europeantopic, false)//,file.getAbsolutePath());

    }


    @Test(priority = 8, description = "Test to check whether added topics are displayed under Added Topics in MyNews")
    @Throws(Exception::class)
    fun testCheckAddedTopics() {
        //CommonFunctions.startTest(androidDriver, "Checking Added Topics on MyTopics Page", "MyNews", false, "MyTopics")//file.getAbsolutePath(),"MyTopics");
        commonFunctionKotlin.startTest("MyNews","Selected Topics under MyTopcis Page","Regression")
        commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.mytopics, false)//,file.getAbsolutePath());
        commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.Walestopic)
        commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.Europeantopic)
        commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.Asiatopic)
        commonFunctionKotlin.navigateBack(androidDriver)

    }


    @Test(priority = 9, description = "Test to select each of the added topics , which displayed under MyNews ")
    @Throws(Exception::class)
    fun testSelectedAddedTopics() {
        commonFunctionKotlin.startTest("MyNews","Selecting added topics from MyNews","Regression")
        commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.Walestopic)
        commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.Europeantopic)
        commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.Asiatopic)
        commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.Walestopic, false)//,file.getAbsolutePath());
        commonFunctionKotlin.elementDisplayed(androidDriver,myNewsPageObject.mynews_managetopics)
        commonFunctionKotlin.elementDisplayed(androidDriver,myNewsPageObject.Walestopic)
        commonFunctionKotlin.navigateBack(androidDriver)
        commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.Europeantopic, false)//,file.getAbsolutePath());
        commonFunctionKotlin.elementDisplayed(androidDriver,myNewsPageObject.mynews_managetopics)
        commonFunctionKotlin.elementDisplayed(androidDriver,myNewsPageObject.Europeantopic)
        commonFunctionKotlin.navigateBack(androidDriver)
        commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.Asiatopic, false)//,file.getAbsolutePath());
        commonFunctionKotlin.elementDisplayed(androidDriver,myNewsPageObject.mynews_managetopics)
        commonFunctionKotlin.elementDisplayed(androidDriver,myNewsPageObject.Asiatopic)
        commonFunctionKotlin.navigateBack(androidDriver)
    }

    @Test(priority = 10, description = "Test to play a Live video from Vide page and asserting on whether playback controls are displayed")
    @Throws(Exception::class)
    fun testVideopage() {
        commonFunctionKotlin.startTest("Video","Playing a Live Video","Regression")
        commonFunctionKotlin.tapButton(androidDriver, basePageObject.video, false)//,file.getAbsolutePath());
        commonFunctionKotlin.IselementSelected(androidDriver,basePageObject.video)
        commonFunctionKotlin.tapButton(androidDriver, vidoePageObject.bbcnewsChannel, false)//,file.getAbsolutePath());
        commonFunctionKotlin.elementDisplayed(androidDriver, vidoePageObject.live_media_item_caption)
        commonFunctionKotlin.elementDisplayed(androidDriver, basePageObject.navigate_back)
        commonFunctionKotlin.elementDisplayed(androidDriver, basePageObject.sharestory)
        commonFunctionKotlin.tapButton(androidDriver, vidoePageObject.smp_placeholder_play_button, false)//,file.getAbsolutePath());
        commonFunctionKotlin.tapButton(androidDriver,vidoePageObject.smp_fullscreen_button,false)
        Thread.sleep(2000)
        if(vidoePageObject.playbutton.isDisplayed)
        {
            commonFunctionKotlin.tapButton(androidDriver,vidoePageObject.playbutton,false)
        }
        commonFunctionKotlin.tapButton(androidDriver, vidoePageObject.transportcontrol, false)//,file.getAbsolutePath());
        commonFunctionKotlin.tapButton(androidDriver, vidoePageObject.transportcontrol, false)//,file.getAbsolutePath());
        commonFunctionKotlin.elementDisplayed(androidDriver, vidoePageObject.smp_pause_button)
        commonFunctionKotlin.elementDisplayed(androidDriver, vidoePageObject.smp_exit_fullscreen_button)
        commonFunctionKotlin.elementDisplayed(androidDriver, vidoePageObject.smp_live_icon)
        commonFunctionKotlin.elementDisplayed(androidDriver, vidoePageObject.smp_volume_button)
        commonFunctionKotlin.elementDisplayed(androidDriver, vidoePageObject.smp_seek_bar)
        commonFunctionKotlin.navigateBack(androidDriver)
    }


    @Test(priority = 11, description = "Test to check the Popular page and also to check Most Read Displayed")
    fun testPopularPage() {
        try {
            commonFunctionKotlin.startTest("Popular", "Checking Popular Page", "Regression")
            commonFunctionKotlin.tapButton(androidDriver, popularPageObject.popular, false)//,file.getAbsolutePath());
            commonFunctionKotlin.IselementSelected(androidDriver,popularPageObject.popular)
            commonFunctionKotlin.elementDisplayed(androidDriver, popularPageObject.mostread)

        } catch (e: Exception) {
        }
    }

    @Test(priority = 12, description = "Test to select an Article from MostRead-Popular Page")
    fun testCheckMostReadPopular() {
        try {
            commonFunctionKotlin.startTest("Popular", "Checking Most Read Article ", "Regression")
            commonFunctionKotlin.tapButton(androidDriver, popularPageObject.mostRead_article, false)
            commonFunctionKotlin.elementDisplayed(androidDriver, popularPageObject.headline_title)
            //CommonFunctions.linksDisplayed(androidDriver,popularPageObject.headline_author_title);
            commonFunctionKotlin.elementDisplayed(androidDriver, popularPageObject.image_item_badge)
            commonFunctionKotlin.elementDisplayed(androidDriver,popularPageObject.mostpopular_text)
            commonFunctionKotlin.elementDisplayed(androidDriver, popularPageObject.headline_info)
            commonFunctionKotlin.elementDisplayed(androidDriver, popularPageObject.headline_link)

        } catch (e: Exception) {
        }
    }

    @Test(priority = 13, description = "Test to check whether the Most Watched heading displayed in Popular Page")
    fun testcheckMostWatched() {
        try {
            commonFunctionKotlin.navigateBack(androidDriver)
            commonFunctionKotlin.startTest("Popular", "Checking Most Watched ", "Regression");
            commonFunctionKotlin.scrolltoElement(androidDriver, popularPageObject.popular_mostwatched)

        } catch (e: Exception) {
        }
    }


    @Test(priority = 14, description = "Test to check for search results")
    fun testSearchStories() {
        try {
            commonFunctionKotlin.startTest("Search", "Checking Search Topics", "Regression");
            commonFunctionKotlin.tapButton(androidDriver, basePageObject.searchbutton, false)
            commonFunctionKotlin.enterText(androidDriver, basePageObject.searchfield, "India")
            val searchTopics_text = CommonFunctions.getText(androidDriver, basePageObject.searchheading)

            Assert.assertEquals(searchTopics_text, "Topics (3)", "matched")
            val searchRelatedheading_text = CommonFunctions.getText(androidDriver, basePageObject.searchheading2)

            Assert.assertEquals(searchRelatedheading_text, "Articles related to \"India\"")

            commonFunctionKotlin.tapButton(androidDriver, basePageObject.cancelSearch, false)

            val searchTopics_text1 = CommonFunctions.getText(androidDriver, basePageObject.searchheading)
            Assert.assertEquals(searchTopics_text1, "In The News Now", "matched")

            val searchRelatedheading_text1 = CommonFunctions.getText(androidDriver, basePageObject.searchheading2)
            Assert.assertEquals(searchRelatedheading_text1, "More Topics", "matched")

            //Assert.assertEquals(basePageObject.searchheading4.getText(),"My Topics","matched");
           commonFunctionKotlin.tapButton(androidDriver,basePageObject.backButton,false)


        } catch (e: Exception) {
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
package com.bbcnews.automation.scripts

import com.bbcnews.automation.commonfunctions.CommonFunctionKotlin
import com.bbcnews.automation.pageobjects.*
import io.appium.java_client.MobileElement
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.pagefactory.AppiumFieldDecorator
import io.appium.java_client.remote.MobileCapabilityType
import org.openqa.selenium.ScreenOrientation
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.support.PageFactory
import org.testng.ITestResult
import java.io.File
import java.io.IOException
import java.net.URL
import java.util.*
import io.qameta.allure.Story
import com.bbcnews.automation.pageobjects.MyNewsPageObject
import com.bbcnews.automation.testutils.Testutility
import org.openqa.selenium.By
import org.openqa.selenium.StaleElementReferenceException
import org.testng.Assert.assertEquals
import org.testng.Assert
import java.lang.System.*
import io.appium.java_client.android.StartsActivity
import org.testng.annotations.*
import java.time.Duration


class BBCNewsRegressionTestKotlin
{
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
    private lateinit var basePageObject: BasePageObject
    private lateinit var vidoePageObject: VidoePageObject
    private lateinit var popularPageObject: PopularPageObject

    private var workingDirectory = getProperty("user.dir")
    private val screenshotpath = "$workingDirectory/Screenshots/"

    @BeforeTest
    fun runTest() {

        try {

            readDeviceDetailsCommandPrompt()
            setUP()
            initialiseobjects()
        } catch (e: Exception) {
            e.printStackTrace() }
    }

   private fun readDeviceDetailsCommandPrompt() {

        try {
            deviceosName = getProperty("DeviceOS")
            deviceid = getProperty("DeviceID")
            deviceName = getProperty("DeviceName")
            appPath = getProperty("AppPath")
            appiumPort = getProperty("AppiumPort")
            println("Passed The Device OS is $deviceosName")
            println("Passed The Device ID is $deviceid")
            println("Passed The Device Name is $deviceName")
            println("Passed The Appium port is $appiumPort")
            println("Passed The Application path  is $appPath")
        } catch (e: Exception) {
            e.printStackTrace() }
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

        basePageObject = BasePageObject()
        PageFactory.initElements(AppiumFieldDecorator(androidDriver), basePageObject)

        vidoePageObject = VidoePageObject()
        PageFactory.initElements(AppiumFieldDecorator(androidDriver), vidoePageObject)

        popularPageObject = PopularPageObject()
        PageFactory.initElements(AppiumFieldDecorator(androidDriver), popularPageObject)

        testutility.emptyFolder(screenshotpath)


        commonFunctionKotlin.createrReportHive("Regression", deviceosName.toString(), deviceName.toString(), deviceid.toString())


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
    fun testTopStories() {
        try {
            commonFunctionKotlin.startTest("Checking the HomePage", "Checking the HomePage", "HomePage")
            commonFunctionKotlin.iselementSelected(basePageObject.topstories)
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
        commonFunctionKotlin.startTest("Videos Of the Day", "Checking the VideoOfTheDay", "HomePage")
        commonFunctionKotlin.sleepmethod(1000)
        commonFunctionKotlin.scrolltoElement(androidDriver, homePageObject.videoOftheDay_watch)
        commonFunctionKotlin.elementDisplayed(androidDriver, homePageObject.watchvideo)
        commonFunctionKotlin.elementDisplayed(androidDriver, homePageObject.promocounter)
        assertEquals("WATCH", homePageObject.watchvideo.text)
        assertEquals("7", homePageObject.promocounter.text)
        commonFunctionKotlin.tapButton(androidDriver, homePageObject.videooftheday_button, false)
        commonFunctionKotlin.scrolltoEndofStories(androidDriver, homePageObject.newstream_progress,vidoePageObject.videsoftheday ,homePageObject.checkback_later)
        commonFunctionKotlin.navigateBack(androidDriver)

    }


    @Test(priority = 4, description = "Test to scroll to a topic on home page and select a particular topic and add to MyNews")
    fun testToCheckTopicsTopStores() {
        try {
            commonFunctionKotlin.startTest("Scrolling to topics TopStories", "Scroll to a Topics on Home Page", "HomePage")
            commonFunctionKotlin.scrolltoElement(androidDriver, homePageObject.realitychecktopics)
            commonFunctionKotlin.tapButton(androidDriver, homePageObject.realitychecktopics, false)
            commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.mynews_addtopics, false)
            // Assert.assertEquals(basePageObject.alert_text_business,basePageObject.alert_text.getText());
            commonFunctionKotlin.navigateBack(androidDriver)
            commonFunctionKotlin.scrolltoElement(androidDriver, homePageObject.healthtopic)
            commonFunctionKotlin.tapButton(androidDriver, homePageObject.healthtopic, false)
            commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.mynews_addtopics, false)
            // Assert.assertEquals(basePageObject.alert_text_uk,basePageObject.alert_text.getText());
            commonFunctionKotlin.navigateBack(androidDriver)
        } catch (e: NullPointerException) {
        }

    }

    @Test(priority = 5, description = "Test To Check the topics added from top stories are displayed under MyNews")
    fun testMyNews_TopStoriesTopics() {
        try {
            commonFunctionKotlin.startTest("Removing Topics Added from TopStories", "Test to check Topics on MyNews page", "TopStories")
            commonFunctionKotlin.tapButton(androidDriver, basePageObject.mynews, false)
            commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.editMyNews, false)
            try {
                commonFunctionKotlin.elementDisplayed(androidDriver, homePageObject.healthtopic)
                commonFunctionKotlin.elementDisplayed(androidDriver, homePageObject.realitychecktopics)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.removetopics, false)
            commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.removetopics, false)
            commonFunctionKotlin.tapButton(androidDriver, basePageObject.backButton, false)
        } catch (e: NullPointerException) {

        }

    }


    @Test(priority = 6, description = "Test to check the Mynews page")
    @Story("MyNews")
    @Throws(Exception::class)
    fun testAllowLocation() {
        try {
            commonFunctionKotlin.startTest("Checking MyNewsPage", "Checking the MyNews", "Smoke")
            commonFunctionKotlin.tapButton(androidDriver, basePageObject.mynews, false)//,file.getAbsolutePath());
            commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.mynews_startButton, false)
            commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.allow_location, false)
            commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.allowlocation_premission, false)
            commonFunctionKotlin.navigateBack(androidDriver)
        } catch (e: AssertionError) {
            e.printStackTrace()
        }

    }

    @Test(priority = 7, description = "Test to check MyNews page and asserting whether all links displayed")
    @Throws(Exception::class)
    fun testMyNews() {
        try {
            commonFunctionKotlin.startTest("Checking Elements on MyNews Page", "Test to check MyNews page", "MyNews")
            commonFunctionKotlin.tapButton(androidDriver, basePageObject.mynews, false)
            commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.mynews_summary)
            commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.mynewstitle)
            commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.addnews_button)
            assertEquals(myNewsPageObject.mynewstitle_text, myNewsPageObject.mynewstitle.text, "Text Mesaaged")
            assertEquals(myNewsPageObject.mynewssummary_text, myNewsPageObject.mynews_summary.text, "Text Mesaaged")
        } catch (e: NullPointerException) {
        }

    }


    @Test(priority = 8, description = "Test to check on My News Add Topic screen and asserting all links are displayed")
    @Throws(Exception::class)
    fun testAddingTopicsPage() {
        try {
            commonFunctionKotlin.startTest("Checking Elements on Edit Mynews Page", "Test to check Edit MyNews page", "MyNews")
            commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.mynews_startButton, false)
            commonFunctionKotlin.iselementSelected(myNewsPageObject.addtopics)
            commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.mytopics)
            //commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.location_button)
            commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.editMyTopics)
            commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.localnews)
            commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.mytopics, false)//,file.getAbsolutePath());
            commonFunctionKotlin.iselementSelected(myNewsPageObject.mytopics)
            assertEquals(myNewsPageObject.mytopic_emptyview_text, myNewsPageObject.mytopic_emptyview.text, "Text Mesaaged")
        } catch (e: NullPointerException) {
        }

    }


    // @Test(dependsOnMethods = {"testAddingTopicsPage"})
    @Test(priority = 9, description = "Test to add Topics under MyNews")
    @Throws(Exception::class)
    fun testAddingTopicstoMyNewsPage() {
        try {
            commonFunctionKotlin.startTest("Selecting Topics", "Test to check added Topics MyNews page", "MyNews")
            commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.addtopics, false)
            myNewsPageObject.addtopics.click()
            assertEquals("London", myNewsPageObject.localnews_displayed.text)
            commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.localnews_displayed)
            commonFunctionKotlin.scrolltoElement(androidDriver, myNewsPageObject.Walestopic)
            commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.Walestopic, false)
            commonFunctionKotlin.scrolltoElement(androidDriver, myNewsPageObject.Asiatopic)
            commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.Asiatopic, false)
            commonFunctionKotlin.scrolltoElement(androidDriver, myNewsPageObject.Europeantopic)
            commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.Europeantopic, false)
            commonFunctionKotlin.scrolltoElement(androidDriver,myNewsPageObject.mortgagestopic)
            commonFunctionKotlin.tapButton(androidDriver,myNewsPageObject.mortgagestopic,false)
            commonFunctionKotlin.scrolltoElement(androidDriver,myNewsPageObject.youtubetopic)
            commonFunctionKotlin.tapButton(androidDriver,myNewsPageObject.youtubetopic,false)
        } catch (e: NullPointerException) {
        }

    }

    //@Test(dependsOnMethods = {"testAddingTopicstoMyNewsPage"})
    @Test(priority = 10, description = "Test to check whether selected topics are displayed under Added Topics in MyNews")
    @Throws(Exception::class)
    fun testCheckAddedTopics() {
        try {
            commonFunctionKotlin.startTest("My Topics page", "Test to check added Topics MyNews page", "MyNews")
            commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.mytopics, false)
            commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.Walestopic)
            commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.Europeantopic)
            commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.Asiatopic)
            commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.mortgagestopic)
            commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.youtubetopic)

        } catch (e: NullPointerException) {
        }

    }


    @Test(priority = 11, description = "Test to display the Ordering of the Topics")
    @Throws(Exception::class)
    fun testCheckOrderingofTopicsAdded()
    {
    try {
        commonFunctionKotlin.startTest("My Topics Ordering", "Test to display the Ordering of the Topics", "MyNews")
        commonFunctionKotlin.readRecyclerView(androidDriver, "Topics Before Re-Ordering :- ")

        out.println("The Text at get(0) is " + androidDriver.findElement(By.xpath("//android.widget.RelativeLayout[@index='0']/android.widget.TextView[@index='1']")).text)
        Assert.assertEquals("Wales", androidDriver.findElement(By.xpath("//android.widget.RelativeLayout[@index='0']/android.widget.TextView[@index='1']")).text, "Test Matched")
        commonFunctionKotlin.navigateBack(androidDriver)

    }catch (e:NullPointerException)
    {
    }
}

    //@Test(dependsOnMethods = {"testCheckAddedTopics"})
    @Test(priority = 12, description = "Test to select each of the topics displayed under MyNews ")
    @Throws(StaleElementReferenceException::class)
    fun testSelectedAddedTopics(){
            commonFunctionKotlin.startTest("Checking Added Topics on Mynews page", "Selecting Added Topics", "MyNews")
            commonFunctionKotlin.sleepmethod(2000)
            commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.Walestopic)
            commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.Europeantopic)
            commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.Asiatopic)
            commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.Walestopic, false)
            commonFunctionKotlin.navigateBack(androidDriver)
            commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.Europeantopic, false)
            commonFunctionKotlin.navigateBack(androidDriver)
            commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.Asiatopic, false)

    }

    @Test(priority = 13, description = "Test to select An Article from the Asia Topics under MyNews ")
    @Throws(Exception::class)
    fun testSelectArticleAsiaTopic() {
        try {
            commonFunctionKotlin.startTest("Selecting a Article from Asia Topics", "Test to select An Article from the Asia Topics under MyNews", "MyNews")
            androidDriver.findElement(By.xpath("//android.widget.FrameLayout[@index='1']/android.widget.RelativeLayout[@index='0']/android.widget.ImageView[@index='0']")).click()
            commonFunctionKotlin.sleepmethod(900)
            for (i in 0 until myNewsPageObject.articlepageelements.size) {
                commonFunctionKotlin.isElementPresent(androidDriver, By.id(myNewsPageObject.articlepageelements[i]))
            }
            commonFunctionKotlin.navigateBack(androidDriver)

        } catch (e: StaleElementReferenceException) {
        }
    }

    @Test(priority = 14, description = "Test to select An Video Article from the Asia Topics under MyNews ")
    @Throws(Exception::class)
    fun testSelectVideoArticleAsiaTopic() {
        try {
            commonFunctionKotlin.startTest("Select a Video Article from Asia Topic", "Test to select An Video Article from the Asia Topics under MyNews", "MyNews")
            commonFunctionKotlin.scrolltoElement(androidDriver, myNewsPageObject.mynewsrecyclerview)
            androidDriver.findElement(By.xpath("//android.support.v7.widget.RecyclerView[@index='3']/android.widget.FrameLayout[@index='0']/android.widget.RelativeLayout[@index='0']")).click()
            commonFunctionKotlin.sleepmethod(500)
            for (i in 0 until vidoePageObject.videowallelements.size) {
                commonFunctionKotlin.isElementPresent(androidDriver, By.id(vidoePageObject.videowallelements[i]))
            }
            //extenttestReport.elementFoundAndClicked(By.id("bbc.mobile.news.uk.internal:id/smp_placeholder_play_button"));
            androidDriver.findElementByAccessibilityId("Play").click()
            commonFunctionKotlin.sleepmethod(2000)
            for (i in 0 until vidoePageObject.playbackcontrols.size) {
                commonFunctionKotlin.isElementPresent(androidDriver, By.id(vidoePageObject.playbackcontrols[i]))
            }
            commonFunctionKotlin.navigateBack(androidDriver)
            commonFunctionKotlin.tapButton(androidDriver, basePageObject.backButton, false)
        } catch (e: StaleElementReferenceException) {
        }

    }


    @Test(priority = 15, description = "Test to Re-Ordering of the Topics")
    @Throws(Exception::class)
    fun testCheckReOrderingofTopicsAdded() {
        try {
            commonFunctionKotlin.startTest("Re-Arrangeing Topics - MtTopics", "Test to Re Ordering of the Topics", "MyNews")
            commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.editMyNews, false)

            val wales = androidDriver.findElementsById("bbc.mobile.news.uk.internal:id/grab_handle")[0]
            val youtubetopic = androidDriver.findElementsById("bbc.mobile.news.uk.internal:id/grab_handle")[4]

            commonFunctionKotlin.elementdragdrop(androidDriver, wales, youtubetopic)

            commonFunctionKotlin.readRecyclerView(androidDriver, "Topics After  Re-Ordering :- ")

            out.println("The Text at get(3) is " + androidDriver.findElement(By.xpath("//android.support.v7.widget.RecyclerView[@index='1']/android.widget.RelativeLayout[@index='3']/android.widget.TextView[@index='1']")).text)
            Assert.assertEquals("Wales", androidDriver.findElement(By.xpath("//android.support.v7.widget.RecyclerView[@index='1']/android.widget.RelativeLayout[@index='3']/android.widget.TextView[@index='1']")).text, "Test Matched")

            out.println("The Text at get(0) is " + androidDriver.findElement(By.xpath("//android.support.v7.widget.RecyclerView[@index='1']/android.widget.RelativeLayout[@index='0']/android.widget.TextView[@index='1']")).text)
            Assert.assertNotEquals("Wales", androidDriver.findElement(By.xpath("//android.support.v7.widget.RecyclerView[@index='1']/android.widget.RelativeLayout[@index='0']/android.widget.TextView[@index='1']")).text, "Test Matched")


            commonFunctionKotlin.navigateBack(androidDriver)

        } catch (e: NullPointerException) {
        }

    }

    @Test(priority = 16, description = "Test to check whether the Menu Options are displayed")
    @Throws(Exception::class)
    fun testcheckMenuItems() {
        try {
            commonFunctionKotlin.startTest("MenuItems", "Checking Menu Items ", "MenuItems")
            commonFunctionKotlin.tapButton(androidDriver, basePageObject.menubutton, false)
            commonFunctionKotlin.elementDisplayed(androidDriver, basePageObject.settings)
            commonFunctionKotlin.elementDisplayed(androidDriver, basePageObject.InternalSettings)
            commonFunctionKotlin.elementDisplayed(androidDriver, basePageObject.OtherBBCapps)
            commonFunctionKotlin.elementDisplayed(androidDriver, basePageObject.Appinfo)
            commonFunctionKotlin.navigateBack(androidDriver)
        } catch (e: NullPointerException) {
        }

    }


    @Test(priority = 17, description = "Test to play a Live video from Vide page and asserting on whether playback controls are displayed")
    @Throws(Exception::class)
    fun testVideopage() {
        try {
            commonFunctionKotlin.startTest("Video", "Checking the Video Page", "Video")
            commonFunctionKotlin.tapButton(androidDriver, basePageObject.video, false)
            commonFunctionKotlin.tapButton(androidDriver, vidoePageObject.bbcnewsChannel, false)
            commonFunctionKotlin.elementDisplayed(androidDriver, vidoePageObject.live_media_item_caption)
            commonFunctionKotlin.elementDisplayed(androidDriver, basePageObject.navigate_back)
            commonFunctionKotlin.elementDisplayed(androidDriver, basePageObject.sharestory)
           // WebDriverWait(androidDriver, 10).until<MobileElement>(ExpectedConditions.presenceOfElementLocated(vidoePageObject.smp_placeholder_play_button))
            commonFunctionKotlin.tapButton(androidDriver, vidoePageObject.smp_placeholder_play_button, false)
            commonFunctionKotlin.sleepmethod(2000)
            commonFunctionKotlin.tapButton(androidDriver, vidoePageObject.smp_fullscreen_button, false)
            commonFunctionKotlin.sleepmethod(2000)
            // if (vidoePageObject.playbutton != null && vidoePageObject.playbutton.isEnabled())
            try {
                if (vidoePageObject.playbutton.isDisplayed) {
                    commonFunctionKotlin.tapButton(androidDriver, vidoePageObject.playbutton, false)
                }
            } catch (e: NoSuchElementException) {
            }

            commonFunctionKotlin.tapButton(androidDriver, vidoePageObject.transportcontrol, false)
            commonFunctionKotlin.tapButton(androidDriver, vidoePageObject.transportcontrol, false)
            commonFunctionKotlin.elementDisplayed(androidDriver, vidoePageObject.smp_pause_button)
            commonFunctionKotlin.elementDisplayed(androidDriver, vidoePageObject.smp_exit_fullscreen_button)
            commonFunctionKotlin.elementDisplayed(androidDriver, vidoePageObject.smpliveicon)
            commonFunctionKotlin.elementDisplayed(androidDriver, vidoePageObject.smp_volume_button)
            commonFunctionKotlin.elementDisplayed(androidDriver, vidoePageObject.smp_seek_bar)
        } catch (e: NullPointerException) {
        }

    }


    @Test(priority = 18, description = "Test to check whether video plays in Landspace mode")
    @Throws(Exception::class)
    fun playingLandspace() {
        commonFunctionKotlin.startTest("Video", "Checking the Video in Landscape Mode", "Video")
        androidDriver.rotate(ScreenOrientation.LANDSCAPE)
        commonFunctionKotlin.tapButton(androidDriver, vidoePageObject.transportcontrol, false)
        commonFunctionKotlin.tapButton(androidDriver, vidoePageObject.transportcontrol, false)
        // extenttestReport.elementDisplayed(androidDriver, vidoePageObject.smp_pause_button);
        commonFunctionKotlin.elementDisplayed(androidDriver, vidoePageObject.smp_exit_fullscreen_button)
        commonFunctionKotlin.elementDisplayed(androidDriver, vidoePageObject.smpliveicon)
        commonFunctionKotlin.elementDisplayed(androidDriver, vidoePageObject.smp_volume_button)
        commonFunctionKotlin.elementDisplayed(androidDriver, vidoePageObject.smp_seek_bar)
    }

    @Test(priority = 19, description = "Test to scrub the video playback ")
    fun scrubbingvideoplayback()
    {
        androidDriver.rotate(ScreenOrientation.PORTRAIT)
        commonFunctionKotlin.livevideoseeking(androidDriver, vidoePageObject.smp_seek_bar,.30)
        commonFunctionKotlin.isElementPresent(androidDriver,By.id("bbc.mobile.news.uk.internal:id/smp_seek_bar"))
        commonFunctionKotlin.tapButton(androidDriver, vidoePageObject.smp_exit_fullscreen_button, false)
        commonFunctionKotlin.navigateBack(androidDriver)
    }

    @Test(priority = 20, description = "Test to check for search results")
    fun testSearchStories() = try {
        commonFunctionKotlin.startTest("Search", "Checking Search Topics", "Search")
        commonFunctionKotlin.tapButton(androidDriver, basePageObject.searchbutton, false)
        commonFunctionKotlin.enterText(basePageObject.searchfield, "India")
        commonFunctionKotlin.sleepmethod(1000)
        val searchTopicstext = commonFunctionKotlin.getText(basePageObject.searchheading)
        assertEquals(searchTopicstext, "Topics (5)", "matched")
        commonFunctionKotlin.sleepmethod(1000)
        val searchRelatedheadingtext = commonFunctionKotlin.getText(basePageObject.searchheading2)
        assertEquals(searchRelatedheadingtext, "Articles related to \"India\"")

        commonFunctionKotlin.tapButton(androidDriver, basePageObject.cancelSearch, false)
        val searchTopicstext1 = commonFunctionKotlin.getText(basePageObject.searchheading)
        assertEquals(searchTopicstext1, "In The News Now", "matched")

        commonFunctionKotlin.sleepmethod(1000)
        val searchRelatedheadingtext2 = commonFunctionKotlin.getText(basePageObject.searchheading2)
        assertEquals(searchRelatedheadingtext2, "More Topics", "matched")

        //Assert.assertEquals(basePageObject.searchheading4.getText(),"My Topics","matched");
        commonFunctionKotlin.tapButton(androidDriver, basePageObject.backButton, false)

    } catch (e: NullPointerException) {
        e.printStackTrace()
    }

    @Test(priority = 21, description = "Test to search for a Topic and navigate to topic page")
    fun testselectsearchresult() {
        try {
            commonFunctionKotlin.startTest("Search", "Test to search for a Topic and navigate to topic page", "Search")
            commonFunctionKotlin.tapButton(androidDriver, basePageObject.searchbutton, false)
            basePageObject.searchfield.clear()
            commonFunctionKotlin.enterText(basePageObject.searchfield, basePageObject.searchtext)
            commonFunctionKotlin.sleepmethod(3000)
            assertEquals(basePageObject.searchtext, basePageObject.searchkeyword.text, "Text Matched")
            commonFunctionKotlin.tapButton(androidDriver, basePageObject.searchkeyword, false)
            val title = commonFunctionKotlin.getText(basePageObject.headlinetitle)
            assertEquals(basePageObject.searchtext, title)
            commonFunctionKotlin.tapButton(androidDriver, basePageObject.backButton, false)
            commonFunctionKotlin.navigateBack(androidDriver)
            commonFunctionKotlin.tapButton(androidDriver, basePageObject.backButton, false)

        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
    }

    @Test(priority = 22, description = "Test Checking an Topic and adding to MyNews")
    @Throws(Exception::class, AssertionError::class)
    fun testSearchTopic() {
        try {
            commonFunctionKotlin.startTest("Search and Adding Topic to Mynews", "Test Checking an Topic and adding to MyNews", "Search")
            commonFunctionKotlin.tapButton(androidDriver, basePageObject.searchbutton, false)
            basePageObject.searchfield.clear()
            commonFunctionKotlin.enterText( basePageObject.searchfield, "India")
            commonFunctionKotlin.sleepmethod(1000)
            commonFunctionKotlin.tapButton(androidDriver, basePageObject.article, false)

            Assert.assertEquals("The women who fought to ban alcohol", basePageObject.articlelayout_name.text)
            Assert.assertEquals("4th Jan", basePageObject.articlellast_updated.text)
            commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.mynews_addtopics, false)
        } catch (e: AssertionError) {
            throw e
        }

    }


    @Test(priority = 23, description = "Test Checking an Article page without an Item Image Badge")
    @Throws(Exception::class, AssertionError::class)
    fun testArtictleItemWithoutItemBadge() {
        try {
            commonFunctionKotlin.startTest("Article without ItemImage Badge", "Test Checking an Article page without an Item Image Badge", "Search")
            commonFunctionKotlin.scrolltoElement(androidDriver, basePageObject.artictleitem_withoutitembadge)
            commonFunctionKotlin.tapButton(androidDriver, basePageObject.artictleitem_withoutitembadge, false)

            var i = 0
            while (i < basePageObject.articledetailpagelinks.size && i < basePageObject.articleitemwithimagebadge.size) {
                commonFunctionKotlin.isElementPresent(androidDriver, By.id(basePageObject.articledetailpagelinks[i]))
                Assert.assertEquals(basePageObject.articleitemwithoutimagebadge[i], androidDriver.findElement(By.id(basePageObject.articledetailpagelinks[i])).text, "Test matched")
                i++
            }

            commonFunctionKotlin.navigateBack(androidDriver)

        } catch (e: AssertionError) {
            throw e
        }

    }


    @Test(priority = 24, description = "Test Checking an Article page with an Item Image Badge")
    @Throws(Exception::class)
    fun testArtictleItemWithItemBadge() {
        try {

            commonFunctionKotlin.startTest("Article with ItemImage Badge", "Checking an Article page without an Item Image Badge", "Search")
            commonFunctionKotlin.scrolltoElement(androidDriver, basePageObject.artictleitem_withitembadge)
            commonFunctionKotlin.tapButton(androidDriver, basePageObject.artictleitem_withitembadge, false)
            commonFunctionKotlin.elementDisplayed(androidDriver, basePageObject.article_imagebade)
            Assert.assertEquals("EPA", basePageObject.article_imagebade.text, "Text Matched")
            var i = 0
            while (i < basePageObject.articledetailpagelinks.size && i < basePageObject.articleitemwithimagebadge.size) {
                commonFunctionKotlin.isElementPresent(androidDriver, By.id(basePageObject.articledetailpagelinks[i]))
                Assert.assertEquals(basePageObject.articleitemwithimagebadge[i], androidDriver.findElement(By.id(basePageObject.articledetailpagelinks[i])).text, "Test matched")
                i++
            }

            commonFunctionKotlin.navigateBack(androidDriver)
            commonFunctionKotlin.tapButton(androidDriver, basePageObject.backButton, false)
            commonFunctionKotlin.tapButton(androidDriver, basePageObject.cancelSearch, false)
        } catch (e: AssertionError) {
            throw e
        }

    }



    @Test(priority = 25, description = "Test to search for an particular article")
    @Throws(Exception::class)
    fun testsearcharticle() {
        try {
            commonFunctionKotlin.startTest("ArticlePage", "Test to search for an particular article", "Article")
            commonFunctionKotlin.tapButton(androidDriver, basePageObject.searchbutton, false)
            basePageObject.searchfield.clear()
            commonFunctionKotlin.enterText(basePageObject.searchfield, "A rape victim's two-year wait for justice")
            commonFunctionKotlin.tapButton(androidDriver,basePageObject.articlesearch, false)
            commonFunctionKotlin.sleepmethod(2000)

            var i = 0
            while (i < basePageObject.articlepagedetail.size && i < basePageObject.articlepagedetailelements.size) {
                commonFunctionKotlin.isElementPresent(androidDriver, By.id(basePageObject.articlepagedetailelements[i]))
                assertEquals(basePageObject.articlepagedetail[i], androidDriver.findElement(By.id(basePageObject.articlepagedetailelements[i])).text, "Test matched")
                i++
            }
            commonFunctionKotlin.tapButton(androidDriver,basePageObject.navigate_back,false)
            commonFunctionKotlin.tapButton(androidDriver,basePageObject.backButton,false)


        } catch (e: AssertionError) {
            throw e
        }
    }


    @Test(priority = 26, description = "Test to search for an particular video article")
    @Throws(Exception::class)
    fun testondemandvideoplyback() {
        try {

            commonFunctionKotlin.startTest("Playing a Particular OnDemand Video", "Test to search ana play a on-demand video", "OnDemandVideo")
            commonFunctionKotlin.tapButton(androidDriver, basePageObject.search, false)
            commonFunctionKotlin.enterText( basePageObject.searchfield, "Egypt court imposes jail")
            commonFunctionKotlin.waitForScreenToLoad(androidDriver, vidoePageObject.videoarticlesearch, 3)
            // androidDriver.hideKeyboard();
            // extenttestReport.scrolltoElement(androidDriver, vidoePageObject.videoarticlesearch);
            commonFunctionKotlin.tapButton(androidDriver, vidoePageObject.videoarticlesearch, false)
            //extenttestReport.waitForScreenToLoad(androidDriver, vidoePageObject.smp_placeholder_play_button, 3);
            var i = 0
            while (i < vidoePageObject.videodetailpage.size && i < vidoePageObject.videdetailpagetext.size) {

                commonFunctionKotlin.isElementPresent(androidDriver, By.id(vidoePageObject.videodetailpage[i]))
                assertEquals(vidoePageObject.videdetailpagetext[i], androidDriver.findElement(By.id(vidoePageObject.videodetailpage[i])).text)
                i++
            }
        } catch (e: AssertionError) {
            throw e

        }

    }

    @Test(priority = 27, description = "Test to seek forward while video playing")
    @Throws(InterruptedException::class)
    fun testseekingvideoforward() {
        commonFunctionKotlin.startTest("Seeking Video Forward", "Test to search ana play a on-demand video", "OnDemandVideo")
        commonFunctionKotlin.tapButton(androidDriver, vidoePageObject.smp_placeholder_play_button, false)
        commonFunctionKotlin.videoplaybackseeking(androidDriver, vidoePageObject.smp_seek_bar, .50, "forward")
    }


    @Test(priority = 28, description = "Test to seek forward videoplayback")
    @Throws(InterruptedException::class)
    fun testseekingvideobackward() {
        commonFunctionKotlin.startTest("Seeking Video Backward", "Test to search ana play a on-demand video", "OnDemandVideo")
        commonFunctionKotlin.videoplaybackseeking(androidDriver, vidoePageObject.smp_seek_bar, .30, "backward")

    }


    @Test(priority = 29, description = "Test to check Related Stories and Topics of an Article")
    @Throws(Exception::class)
    fun testRelatedStoriesTopics() {
        commonFunctionKotlin.startTest("Checking for RelatedS Story/Topics", "Test to check Related Stories and Topics of an Article", "OnDemandVideo")
        commonFunctionKotlin.scrolltoElement(androidDriver, basePageObject.relatedstories)
        commonFunctionKotlin.scrolltoElement(androidDriver, basePageObject.relatedtopics)
        commonFunctionKotlin.navigateBack(androidDriver)
        //  extenttestReport.tapButton(androidDriver,basePageObject.navigate_back,false);
        commonFunctionKotlin.tapButton(androidDriver, basePageObject.backButton, false)
    }


    @Test(priority = 30, description = "Test to check the T&C , PrivacyPolicy from Menu options")
    fun testcheckTermsPrivacyPolicy() {
        commonFunctionKotlin.startTest("Checking T&C Privacy Policy", "Checking T&C and privacy Policy", "Menu")
        commonFunctionKotlin.tapButton(androidDriver, basePageObject.menubutton, false)
        commonFunctionKotlin.tapButton(androidDriver, basePageObject.menuappinfo, false)
        commonFunctionKotlin.tapButton(androidDriver, basePageObject.termsconditions, false)
        commonFunctionKotlin.navigateBack(androidDriver)
        commonFunctionKotlin.tapButton(androidDriver, basePageObject.privacypolicy, false)
        commonFunctionKotlin.navigateBack(androidDriver)
        commonFunctionKotlin.tapButton(androidDriver, basePageObject.backButton, false)
    }


    @Test(priority = 31, description = "Playing a video from Video page")
    fun testTopStroesvideo() {
        // extenttestReport.navigateBack(androidDriver);
        commonFunctionKotlin.startTest("Playing a OnDemand Video", "Test to play a video from Video page", "OnDemandVideo")
        commonFunctionKotlin.tapButton(androidDriver, basePageObject.video, false)
        commonFunctionKotlin.tapButton(androidDriver, vidoePageObject.popularvideo, false)
        commonFunctionKotlin.tapButton(androidDriver,vidoePageObject.smp_placeholder_play_button,false)
        vidoePageObject.transportcontrol.click()
        vidoePageObject.transportcontrol.click()
        for (i in 0 until vidoePageObject.popularvideoelements.size) {
            commonFunctionKotlin.isElementPresent(androidDriver, By.id(vidoePageObject.popularvideoelements[i]))
        }

        commonFunctionKotlin.navigateBack(androidDriver)
    }

    @Test(priority = 32, description = "App Backgrounding")
    fun testAppbackground() {
        commonFunctionKotlin.startTest("App Background ", "Test to check backgrouding the app and reopen and checking same page opens", "App Background")
        basePageObject.popular.click()
        androidDriver.runAppInBackground(Duration.ofSeconds(40))
        (androidDriver as StartsActivity).currentActivity()
        try {
            Assert.assertTrue(basePageObject.popular.isSelected)
        } catch (e: AssertionError) {
            throw e
        }
    }


    @Test(priority = 33, description = "Test to check the Popular page and also to check Most Read Displayed")
    @Throws(Exception::class)
    fun testPopularPage() {

        commonFunctionKotlin.startTest("PopularPage", "Checking Popular Page", "Popular")
        commonFunctionKotlin.tapButton(androidDriver, popularPageObject.popular, false)
        commonFunctionKotlin.elementDisplayed(androidDriver, popularPageObject.mostread)

    }

    @Test(priority = 34, description = "Test to select one Article from Most Read  Article from Popular Page")
    @Throws(Exception::class)
    fun testCheckMostReadPopular() {
        try {
            commonFunctionKotlin.startTest("PopularPage", "Checking Most Read Popular", "Popular")
            commonFunctionKotlin.tapButton(androidDriver, popularPageObject.mostRead_article, false)

            var i = 0
            while (i < popularPageObject.mostreadpopularlinks.size) {
                commonFunctionKotlin.isElementPresent(androidDriver, By.id(popularPageObject.mostreadpopularlinks[i]))
                i++
            }

        } catch (e: NullPointerException) {
        }

    }


    @Test(priority = 35, description = "Test to check whether the Most Watched heading displayed in Popular Page")
    fun testcheckMostWatched() {
        try {
            commonFunctionKotlin.navigateBack(androidDriver)
            commonFunctionKotlin.startTest("PopularPage", "Checking Most Watched", "Popular")
            commonFunctionKotlin.scrolltoElement(androidDriver, popularPageObject.popular_mostwatched)
            commonFunctionKotlin.elementDisplayed(androidDriver,popularPageObject.popular_mostwatched)

        } catch (e: NullPointerException) {
        }
    }

    @Test(priority = 36, description = "Test to check whether the Most Watched heading displayed in Popular Page")
    @Throws(Exception::class)
    fun testcheckMostWatchedArticle() {
        try {

            commonFunctionKotlin.startTest("Checking Most Watched Article ", "Checking Most Watched", "Popular")
            commonFunctionKotlin.scrolltoElement(androidDriver, popularPageObject.mostwatchedartcilevideo)
            System.out.println("The Element selected is  :- " + popularPageObject.mostwatchedartcilevideo.text)
            commonFunctionKotlin.tapButton(androidDriver,popularPageObject.mostwatchedartcilevideo, false)
            if (!popularPageObject.mostpopular.isDisplayed) {
                commonFunctionKotlin.verticalSwipe(androidDriver, "Up")
            }
            var i=0
            while(i< vidoePageObject.videowallelements.size)
            {
                commonFunctionKotlin.isElementPresent(androidDriver,By.id(vidoePageObject.videowallelements[i]))
                i++
            }

            commonFunctionKotlin.elementDisplayed(androidDriver, vidoePageObject.smp_placeholder_play_button)
            commonFunctionKotlin.navigateBack(androidDriver)


        } catch (e: NullPointerException) {
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
        androidDriver.removeApp("bbc.mobile.news.uk.internal")
        androidDriver.closeApp()
        androidDriver.quit()

    }
}
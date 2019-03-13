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
import com.bbcnews.automation.pageobjects.VideoPageObjects
import com.bbcnews.automation.pageobjects.PopularPageObjects
import com.bbcnews.automation.testutils.Testutility
import org.openqa.selenium.By
import org.openqa.selenium.StaleElementReferenceException
import org.testng.Assert
import java.lang.System.*
import io.appium.java_client.android.StartsActivity
import io.appium.java_client.android.connection.ConnectionStateBuilder
import org.testng.Assert.*
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
    private lateinit var vidoePageObject: VideoPageObjects
    private lateinit var popularPageObject: PopularPageObjects
    private lateinit var myTopicsPageObject: MyTopicsPageObject

    private var workingDirectory = getProperty("user.dir")
    private val screenshotpath = "$workingDirectory/Screenshots/"

    @BeforeTest
    fun runTest() {

        try {

            readDeviceDetailsCommandPrompt()
            setUP()
            commonFunctionKotlin.checkConnection(androidDriver)
            /**
             *  setting the view mode to Portrait , since on Hive sometime device might be in Landscape mode
             */
            val orientation = androidDriver.orientation
            if (orientation == ScreenOrientation.LANDSCAPE) {
                androidDriver.rotate(ScreenOrientation.PORTRAIT)
            }
            initialiseobjects()
        } catch (e: Exception) {
            e.printStackTrace() }
    }

   private fun readDeviceDetailsCommandPrompt() {

        try {
           // deviceosName = getProperty("DeviceOS")
            deviceid = getProperty("DeviceID")
            deviceName = getProperty("DeviceName")
            appPath = getProperty("AppPath")
            appiumPort = getProperty("AppiumPort")
           // println("Passed The Device OS is $deviceosName")
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
            capabilities.setCapability("app", appPath)
            capabilities.setCapability("appPackage", "bbc.mobile.news.uk.internal")
            capabilities.setCapability("appActivity", "bbc.mobile.news.v3.app.TopLevelActivity")
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

        vidoePageObject = VideoPageObjects()
        PageFactory.initElements(AppiumFieldDecorator(androidDriver), vidoePageObject)

        popularPageObject = PopularPageObjects()
        PageFactory.initElements(AppiumFieldDecorator(androidDriver), popularPageObject)

        myTopicsPageObject = MyTopicsPageObject()
        PageFactory.initElements(AppiumFieldDecorator(androidDriver), myTopicsPageObject)

        testutility.emptyFolder(screenshotpath)


        commonFunctionKotlin.createrReportHive("Regression", deviceName.toString(), deviceid.toString())


        androidDriver.context("NATIVE_APP")
        file = File(screenshotpath)
        val screenshot = file.absolutePath
        println("The ScreenShot Path is $screenshot")

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
            try {
                if (commonFunctionKotlin.isElementPresent(androidDriver, By.id("bbc.mobile.news.uk.internal:id/error_retry")))
                    //    !androidDriver.findElement(By.id("bbc.mobile.news.uk.internal:id/error_retry")).isDisplayed)
                {
                    androidDriver.findElement(By.id("bbc.mobile.news.uk.internal:id/error_retry")).click()
                    //wait.until(extenttestReport.elementFoundAndClicked(By.id("bbc.mobile.news.uk.internal:id/error_retry")));
                }
            } catch (e:NoSuchElementException) {
            }


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


    /**
     * commented out as VideoOfTheDay isn't displayed  on Home page
     */

//    @Test(priority = 3, description = "Test to check Video of the day displayed and swipe through all the videos")
//    @Throws(Exception::class)
//    fun testVideoofthedayDisplayed() {
//        try {
//            commonFunctionKotlin.startTest("VideoOftheDay", "Scroll to a Video of the day", "HomePage")
//            commonFunctionKotlin.sleepmethod(1000)
//            commonFunctionKotlin.scrolltoElement(androidDriver, homePageObject.videoOftheDay_watch)
//            commonFunctionKotlin.elementDisplayed(androidDriver, homePageObject.videooftheday_watchtext)
//            commonFunctionKotlin.elementDisplayed(androidDriver, homePageObject.promocounter)
//            commonFunctionKotlin.elementDisplayed(androidDriver, homePageObject.vodeoofthedaypromosummary)
//            commonFunctionKotlin.elementDisplayed(androidDriver, homePageObject.vodeoofthedaytitle)
//            Assert.assertEquals("Videos of the day", homePageObject.vodeoofthedaytitle.getText())
//            Assert.assertEquals("WATCH", homePageObject.videooftheday_watchtext.getText())
//            Assert.assertEquals("7", homePageObject.promocounter.getText())
//            Assert.assertEquals("Swipe through the latest news videos", homePageObject.vodeoofthedaypromosummary.getText())
//            commonFunctionKotlin.tapButton(androidDriver, homePageObject.videooftheday_button, false)
//            commonFunctionKotlin.scrolltoEndofStories(androidDriver, homePageObject.newstream_progress, vidoePageObject.videsofthedayRelease, homePageObject.checkback_later)
//            commonFunctionKotlin.navigateBack(androidDriver)
//        } catch (e: AssertionError) {
//            throw e
//        }
//
//    }


    @Test(priority = 3, description = "Test to scroll to a topic on home page and select a particular topic and add to MyNews")
    @Throws(Exception::class)
    fun testToCheckTopicsTopStores() {
        try {
            commonFunctionKotlin.startTest("Scrolling to topics", "Scroll to a Topics on Home Page", "HomePage")

            //scrolls to Reality Check topics on Top Stories page
            commonFunctionKotlin.scrolltoElement(androidDriver, homePageObject.educationstopics)
            commonFunctionKotlin.tapButton(androidDriver, homePageObject.educationstopics, false)
            if (!commonFunctionKotlin.isElementPresent(androidDriver, By.id("bbc.mobile.news.uk.internal:id/menu_follow"))) {
                System.out.println("Scrolling up")
                commonFunctionKotlin.verticalSwipe(androidDriver, "Up")
            }
            commonFunctionKotlin.elementDisplayed(androidDriver, homePageObject.family_educationTopic)
            System.out.println("Topics is :-" + homePageObject.family_educationTopic.text)

            for (i in 0 until basePageObject.topicspageelemnets.size) {
                commonFunctionKotlin.isElementPresent(androidDriver, By.id(basePageObject.topicspageelemnets[i]))
            }
            commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.mynews_addtopics, false)
            commonFunctionKotlin.textpresent(androidDriver, "Family & Education", "added to")
            commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.manageyourtopics)
            commonFunctionKotlin.tapButton(androidDriver, basePageObject.backButton, false)


            //scrolls to health topics on Top Stories page
            commonFunctionKotlin.scrolltoElement(androidDriver, homePageObject.technologytopic)
            commonFunctionKotlin.tapButton(androidDriver, homePageObject.technologytopic, false)
            if (!commonFunctionKotlin.isElementPresent(androidDriver, By.id("bbc.mobile.news.uk.internal:id/menu_follow"))) {
                System.out.println("Scrolling up")
                commonFunctionKotlin.verticalSwipe(androidDriver, "Up")
            }
            System.out.println("The Topic is " + homePageObject.technologytopic.text)
            commonFunctionKotlin.elementDisplayed(androidDriver, homePageObject.technologytopic)
            System.out.println("Topics is :-" + homePageObject.technologytopic.text)
            for (i in 0 until basePageObject.topicspageelemnets.size) {
                commonFunctionKotlin.isElementPresent(androidDriver, By.id(basePageObject.topicspageelemnets[i]))
            }
            commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.mynews_addtopics, false)
            commonFunctionKotlin.textpresent(androidDriver, "Technology", "added to")
            commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.manageyourtopics)
            commonFunctionKotlin.tapButton(androidDriver, basePageObject.backButton, false)

        } catch (e: Exception) {
        }

    }

    @Test(priority = 4, description = "Test To Check the topics added from top stories are displayed under MyNews")
    @Throws(Exception::class)
    fun testMyNewsTopStoriesTopics() {
        try {
            commonFunctionKotlin.startTest("Removing Added Topics", "Test to check Topics on MyNews page", "MyNews")
            commonFunctionKotlin.tapButton(androidDriver, basePageObject.mynews, false)
            commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.editMyNews, false)
            try {
                commonFunctionKotlin.elementDisplayed(androidDriver, homePageObject.technologytopic)
                commonFunctionKotlin.elementDisplayed(androidDriver, homePageObject.family_educationTopic)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.removetopics, false)
           // commonFunctionKotlin.textpresent(androidDriver, "Family & Education", "removed from")
            commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.removetopics, false)
           // commonFunctionKotlin.textpresent(androidDriver, "Technology", "removed from")
           // commonFunctionKotlin.navigateBack(androidDriver)
            commonFunctionKotlin.tapButton(androidDriver, basePageObject.backButton, false)

        } catch (e: StaleElementReferenceException) {

        }

    }


    @Test(priority = 5, description = "Test for Checking whether Location service works")
    @Story("MyNews")
    @Throws(Exception::class)
    fun testAllowLocation() {
        try {
            commonFunctionKotlin.startTest("Allowing Location Service ", "Checking whether Location service works ", "MyNews")
            commonFunctionKotlin.tapButton(androidDriver, basePageObject.mynews, false)//,file.getAbsolutePath());
            commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.mynews_startButton, false)
            commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.allow_location, false)
            commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.allowlocation_premission, false)
            commonFunctionKotlin.navigateBack(androidDriver)
        } catch (e: AssertionError) {
            e.printStackTrace()
        }

    }


    @Test(priority = 6, description = "Test to check MyNews page and asserting whether all links displayed")
    @Throws(Exception::class)
    fun testMyNews() = try {
        commonFunctionKotlin.startTest("Checking Elements on MyNews Page", "Test to check MyNews page", "MyNews")
        commonFunctionKotlin.tapButton(androidDriver, basePageObject.mynews, false)
        commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.mynews_summary)
        commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.mynewstitle)
        commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.addnews_button)
        assertEquals(myNewsPageObject.mynewstitle_text, myNewsPageObject.mynewstitle.text, "Text Mesaaged")
        assertEquals(myNewsPageObject.mynewssummary_text, myNewsPageObject.mynews_summary.text, "Text Mesaaged")
    } catch (e: NullPointerException) {
    }

    // @Test(dependsOnMethods = {"testMyNews"})
    @Test(priority = 7, description = "Test to check on My News Add Topic screen and asserting all links are displayed")
    @Throws(Exception::class)
    fun testAddingTopicsPage() {
        try {
            commonFunctionKotlin.startTest("Checking Elements on Edit Mynews Page", "Test to check Edit MyNews page", "MyNews")
            commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.mynews_startButton, false)
            commonFunctionKotlin.elementIsSelected(myNewsPageObject.addtopics)
            commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.mytopics)
            //commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.location_button);
            commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.editMyTopics)
            commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.localnews)
            commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.mytopics, false)//,file.getAbsolutePath());
            commonFunctionKotlin.elementIsSelected(myNewsPageObject.mytopics)
            assertEquals(myNewsPageObject.mytopic_emptyview_text, myNewsPageObject.mytopic_emptyview.text, "Text Mesaaged")
        } catch (e: NullPointerException) {
        }

    }


    // @Test(dependsOnMethods = {"testAddingTopicsPage"})
    @Test(priority = 8, description = "Test to add Topics under MyNews")
    @Throws(Exception::class)
    fun testAddingTopicstoMyNewsPage() {
        try {
            commonFunctionKotlin.startTest("Adding Topics ", "Test to check added Topics to MyNews page", "MyNews")
            commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.addtopics, false)

            Assert.assertEquals("Manchester", myNewsPageObject.localnews_displayed.getText())
            commonFunctionKotlin.elementDisplayed(androidDriver, myNewsPageObject.localnews_displayed)

            commonFunctionKotlin.scrolltoElement(androidDriver, myTopicsPageObject.englandtopic)
            commonFunctionKotlin.tapButton(androidDriver, myTopicsPageObject.englandtopic, false)
            commonFunctionKotlin.textpresent(androidDriver, "England", "added to")

            commonFunctionKotlin.scrolltoElement(androidDriver, myTopicsPageObject.africatopic)
            commonFunctionKotlin.tapButton(androidDriver, myTopicsPageObject.africatopic, false)

            commonFunctionKotlin.scrolltoElement(androidDriver, myTopicsPageObject.europeuniontopic)
            commonFunctionKotlin.tapButton(androidDriver, myTopicsPageObject.europeuniontopic, false)

            commonFunctionKotlin.scrolltoElement(androidDriver, myTopicsPageObject.mortgagestopic)
            commonFunctionKotlin.tapButton(androidDriver, myTopicsPageObject.mortgagestopic, false)
            commonFunctionKotlin.textpresent(androidDriver, "Mortgages", "added to")

            commonFunctionKotlin.scrolltoElement(androidDriver, myTopicsPageObject.youtubetopic)
            commonFunctionKotlin.tapButton(androidDriver, myTopicsPageObject.youtubetopic, false)
            commonFunctionKotlin.textpresent(androidDriver, "YouTube", "added to")
        } catch (e: NullPointerException) {
        }

    }

    @Test(priority = 9, description = "Test to check whether selected topics are displayed under Added Topics in MyNews")
    @Throws(Exception::class)
    fun testCheckAddedTopics() {
        try {
            commonFunctionKotlin.startTest("My Topics page", "Test to check added Topics MyNews page", "MyNews")
            commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.mytopics, false)
            commonFunctionKotlin.elementDisplayed(androidDriver, myTopicsPageObject.Englandtopic)
            commonFunctionKotlin.elementDisplayed(androidDriver, myTopicsPageObject.Africatopic)
            commonFunctionKotlin.elementDisplayed(androidDriver, myTopicsPageObject.Europeantopic)
            commonFunctionKotlin.elementDisplayed(androidDriver, myTopicsPageObject.Mortgagestopic)
            commonFunctionKotlin.elementDisplayed(androidDriver, myTopicsPageObject.Youtubetopic)
        } catch (e: NullPointerException) {
        }

    }


    @Test(priority = 10, description = "Test to display the Ordering of the Topics")
    @Throws(Exception::class)
    fun testCheckOrderingofTopicsAdded() = try {
        commonFunctionKotlin.startTest("My Topics Ordering", "Test to display the Ordering of the Topics", "MyNews")
        commonFunctionKotlin.readRecyclerView(androidDriver, "Topics Before Re-Ordering :- ")
        System.out.println("The Text at get(0) is " + androidDriver.findElement(By.xpath("//android.support.v7.widget.RecyclerView[@index='1']/android.widget.RelativeLayout[@index='0']/android.widget.TextView[@index='1']")).text)
        System.out.println("The Text at get(1) is " + androidDriver.findElement(By.xpath("//android.support.v7.widget.RecyclerView[@index='1']/android.widget.RelativeLayout[@index='1']/android.widget.TextView[@index='1']")).text)
        System.out.println("The Text at get(2) is " + androidDriver.findElement(By.xpath("//android.support.v7.widget.RecyclerView[@index='1']/android.widget.RelativeLayout[@index='2']/android.widget.TextView[@index='1']")).text)
        System.out.println("The Text at get(3) is " + androidDriver.findElement(By.xpath("//android.support.v7.widget.RecyclerView[@index='1']/android.widget.RelativeLayout[@index='3']/android.widget.TextView[@index='1']")).text)
        System.out.println("The Text at get(4) is " + androidDriver.findElement(By.xpath("//android.support.v7.widget.RecyclerView[@index='1']/android.widget.RelativeLayout[@index='4']/android.widget.TextView[@index='1']")).text)
        assertEquals("England", androidDriver.findElement(By.xpath("//android.widget.RelativeLayout[@index='0']/android.widget.TextView[@index='1']")).text, "Test Matched")
        commonFunctionKotlin.navigateBack(androidDriver)

    } catch (e: NullPointerException) {
    }

    @Test(priority = 11, description = "Test to select each of the topics displayed under MyNews ")
    @Throws(Exception::class)
    fun testSelectedAddedTopics() {
        try {
            commonFunctionKotlin.startTest("Checking Added Topics on Mynews page", "Selecting Added Topics", "MyNews")
            commonFunctionKotlin.sleepmethod(1000)
            commonFunctionKotlin.elementDisplayed(androidDriver, myTopicsPageObject.Englandtopic)
            commonFunctionKotlin.elementDisplayed(androidDriver, myTopicsPageObject.Europeantopic)
            commonFunctionKotlin.elementDisplayed(androidDriver, myTopicsPageObject.Africatopic)
            commonFunctionKotlin.tapButton(androidDriver, myTopicsPageObject.Africatopic, false)
            for (i in 0 until basePageObject.topicspageelemnets.size) {
                commonFunctionKotlin.isElementPresent(androidDriver, By.id(basePageObject.topicspageelemnets[i]))
            }
            commonFunctionKotlin.navigateBack(androidDriver)
            commonFunctionKotlin.tapButton(androidDriver, myTopicsPageObject.Europeantopic, false)
            for (i in 0 until basePageObject.topicspageelemnets.size) {
                commonFunctionKotlin.isElementPresent(androidDriver, By.id(basePageObject.topicspageelemnets[i]))
            }
            commonFunctionKotlin.navigateBack(androidDriver)
            commonFunctionKotlin.tapButton(androidDriver, myTopicsPageObject.Englandtopic, false)
            for (i in 0 until basePageObject.topicspageelemnets.size) {
                commonFunctionKotlin.isElementPresent(androidDriver, By.id(basePageObject.topicspageelemnets[i]))
            }
            commonFunctionKotlin.navigateBack(androidDriver)

        } catch (e: StaleElementReferenceException) {
        }

    }



    @Test(priority = 12, description = "Test to re-arrange topics from top to bottom")
    @Throws(Exception::class)
    fun testCheckReOrderingofTopicsAdded() = try {
        commonFunctionKotlin.startTest("Re Arrange Topics from Top-to-Bottom", "Test to re-arrange topics from top to bottom", "MyNews")
        commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.editMyNews, false)

        val england = androidDriver.findElementsById("bbc.mobile.news.uk.internal:id/grab_handle")[0]
        val youtubetopic = androidDriver.findElementsById("bbc.mobile.news.uk.internal:id/grab_handle")[4]

        commonFunctionKotlin.elementdragdrop(androidDriver, england, youtubetopic)

        commonFunctionKotlin.readRecyclerView(androidDriver, "Topics After  Re-Ordering :- ")

        System.out.println("The Text at get(3) is " + androidDriver.findElement(By.xpath("//android.support.v7.widget.RecyclerView[@index='1']/android.widget.RelativeLayout[@index='3']/android.widget.TextView[@index='1']")).text)
        assertEquals("England", androidDriver.findElement(By.xpath("//android.support.v7.widget.RecyclerView[@index='1']/android.widget.RelativeLayout[@index='3']/android.widget.TextView[@index='1']")).text, "Test Matched")

        System.out.println("The Text at get(0) is " + androidDriver.findElement(By.xpath("//android.support.v7.widget.RecyclerView[@index='1']/android.widget.RelativeLayout[@index='0']/android.widget.TextView[@index='1']")).text)
        assertNotEquals("England", androidDriver.findElement(By.xpath("//android.support.v7.widget.RecyclerView[@index='1']/android.widget.RelativeLayout[@index='0']/android.widget.TextView[@index='1']")).text, "Test Matched")
        assertEquals("Africa", androidDriver.findElement(By.xpath("//android.support.v7.widget.RecyclerView[@index='1']/android.widget.RelativeLayout[@index='0']/android.widget.TextView[@index='1']")).text, "Test Matched")

        commonFunctionKotlin.navigateBack(androidDriver)

    } catch (e: NullPointerException) {
    }



    @Test(priority = 13, description = "Test to check whether the Menu Options are displayed")
    @Throws(Exception::class)
    fun testcheckMenuItems() {
        try {
            commonFunctionKotlin.startTest("Checking the MenuItems", "Checking Menu Items ", "Menu")
            commonFunctionKotlin.tapButton(androidDriver, basePageObject.menubutton, false)
            commonFunctionKotlin.elementDisplayed(androidDriver, basePageObject.settings)
            commonFunctionKotlin.elementDisplayed(androidDriver, basePageObject.InternalSettings)
            commonFunctionKotlin.elementDisplayed(androidDriver, basePageObject.OtherBBCapps)
            commonFunctionKotlin.elementDisplayed(androidDriver, basePageObject.Appinfo)
            commonFunctionKotlin.navigateBack(androidDriver)
        } catch (e: NullPointerException) {
        }

    }


    @Test(priority = 14, description = "Test to play a Live video from Vide page and asserting on whether playback controls are displayed")
    @Throws(Exception::class)
    fun testVideopage() {
        try {
            commonFunctionKotlin.startTest("Playing a Live Video", "Checking the Video Page", "Live Video")
            commonFunctionKotlin.tapButton(androidDriver, basePageObject.video, false)
            commonFunctionKotlin.tapButton(androidDriver, vidoePageObject.bbcnewsChannel, false)
            commonFunctionKotlin.elementDisplayed(androidDriver, vidoePageObject.live_media_item_caption)
            commonFunctionKotlin.elementDisplayed(androidDriver, basePageObject.navigate_back)
            commonFunctionKotlin.elementDisplayed(androidDriver, basePageObject.sharestory)
            commonFunctionKotlin.tapButton(androidDriver, vidoePageObject.smp_placeholder_play_button, false)
            commonFunctionKotlin.sleepmethod(1400)
            commonFunctionKotlin.tapButton(androidDriver, vidoePageObject.smp_fullscreen_button, false)
            commonFunctionKotlin.sleepmethod(1400)
            try {
                if (commonFunctionKotlin.isElementPresent(androidDriver, By.id("bbc.mobile.news.uk.internal:id/smp_play_button"))) {
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

    @Test(priority = 15, description = "Test to check whether video plays in Landspace mode")
    @Throws(Exception::class)
    fun playingLandspace() {
        commonFunctionKotlin.startTest("Checking Live Video in Landscape", "Checking the Video in Landscape Mode", "Live Video")
        androidDriver.rotate(ScreenOrientation.LANDSCAPE)
        commonFunctionKotlin.tapButton(androidDriver, vidoePageObject.transportcontrol, false)
        commonFunctionKotlin.tapButton(androidDriver, vidoePageObject.transportcontrol, false)
        commonFunctionKotlin.elementDisplayed(androidDriver, vidoePageObject.smp_pause_button)
        commonFunctionKotlin.elementDisplayed(androidDriver, vidoePageObject.smp_exit_fullscreen_button)
        commonFunctionKotlin.elementDisplayed(androidDriver, vidoePageObject.smpliveicon)
        commonFunctionKotlin.elementDisplayed(androidDriver, vidoePageObject.smp_volume_button)
        commonFunctionKotlin.elementDisplayed(androidDriver, vidoePageObject.smp_seek_bar)

    }


    @Test(priority = 16, description = "Test to scrub thise video playback ")
    @Throws(InterruptedException::class)
    fun scrubbingvideoplayback() {
        androidDriver.rotate(ScreenOrientation.PORTRAIT)
        commonFunctionKotlin.startTest("Checking Live Video Scrubbing", "Checking the Live Video in Portrait Mode and seeking", "Live Video")
        commonFunctionKotlin.seeking(androidDriver, vidoePageObject.smp_seek_bar, .30, "forward")
        commonFunctionKotlin.isElementPresent(androidDriver, By.id("bbc.mobile.news.uk.internal:id/smp_seek_bar"))
        commonFunctionKotlin.tapButton(androidDriver, vidoePageObject.smp_exit_fullscreen_button, false)
        commonFunctionKotlin.navigateBack(androidDriver)
    }

    @Test(priority = 17, description = "Test to check for search results")
    fun testSearchStories() {
        try {
            commonFunctionKotlin.startTest("Search for an Topics", "Checking Search Topics", "Search")
            commonFunctionKotlin.tapButton(androidDriver, basePageObject.searchbutton, false)
            commonFunctionKotlin.enterText(basePageObject.searchfield, "India")
            commonFunctionKotlin.sleepmethod(2000)
            val searchTopics_text = commonFunctionKotlin.getText( basePageObject.searchheading)
                    //androidDriver.findElement(By.xpath("android.widget.TextView[@text='Topics (5)']")).text
                    //commonFunctionKotlin.getText( basePageObject.searchheading)
            assertEquals(searchTopics_text, "Topics (5)", "matched")
            commonFunctionKotlin.sleepmethod(1000)
            val searchRelatedheading_text = commonFunctionKotlin.getText( basePageObject.searchheading2)
            assertEquals(searchRelatedheading_text, "Articles related to \"India\"")

            commonFunctionKotlin.tapButton(androidDriver, basePageObject.cancelSearch, false)
            val searchTopics_text1 = commonFunctionKotlin.getText( basePageObject.searchheading)
            assertEquals(searchTopics_text1, "In The News Now", "matched")

            commonFunctionKotlin.sleepmethod(1000)
            val searchRelatedheading_text2 = commonFunctionKotlin.getText( basePageObject.searchheading2)
            assertEquals(searchRelatedheading_text2, "More Topics", "matched")

            //Assert.assertEquals(basePageObject.searchheading4.getText(),"My Topics","matched");
            commonFunctionKotlin.tapButton(androidDriver, basePageObject.backButton, false)

        } catch (e: AssertionError) {
            throw e
        }

    }

    @Test(priority = 18, description = "Test to search for a Topic and navigate to topic page")
    fun testSelectSearchResult() {
        try {
            commonFunctionKotlin.startTest("Navigate to Topic Detail Page", "Test to search for a Topic and navigate to topic page", "Search")
            commonFunctionKotlin.tapButton(androidDriver, basePageObject.searchbutton, false)
            basePageObject.searchfield.clear()
            commonFunctionKotlin.enterText(basePageObject.searchfield, basePageObject.searchtext)
            commonFunctionKotlin.sleepmethod(700)
            assertEquals(basePageObject.searchtext, basePageObject.searchkeyword.text, "Text Matched")
            commonFunctionKotlin.tapButton(androidDriver, basePageObject.searchkeyword, false)
            val title = commonFunctionKotlin.getText(basePageObject.headlinetitle)
            assertEquals(basePageObject.searchtext, title)
            commonFunctionKotlin.tapButton(androidDriver, basePageObject.backButton, false)
            commonFunctionKotlin.navigateBack(androidDriver)
            commonFunctionKotlin.tapButton(androidDriver, basePageObject.backButton, false)
            // }

        } catch (e: AssertionError) {
            throw e
        }

    }


    @Test(priority = 19, description = "Test Checking an Topic and adding to MyNews")
    @Throws(Exception::class, AssertionError::class)
    fun testSearchTopic() {
        try {
            commonFunctionKotlin.startTest("Search and Adding Topic to Mynews", "Test Checking an Topic and adding to MyNews", "Search")
            commonFunctionKotlin.tapButton(androidDriver, basePageObject.searchbutton, false)
           // basePageObject.searchfield.clear()
            commonFunctionKotlin.enterText( basePageObject.searchfield, "India")
            commonFunctionKotlin.sleepmethod(700)
            commonFunctionKotlin.tapButton(androidDriver, basePageObject.article, false)

            assertEquals("The women who fought to ban alcohol", basePageObject.articlelayout_name.text)
            assertEquals("4th Jan", basePageObject.articlellast_updated.text)
            commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.mynews_addtopics, false)
        } catch (e: AssertionError) {
            throw e
        }

    }

    @Test(priority = 20, description = "Test Checking an Article page without an Item Image Badge")
    @Throws(Exception::class, AssertionError::class)
    fun testArtictleItemWithoutItemBadge() {
        try {
            commonFunctionKotlin.startTest("Article without ItemImage Badge", "Test Checking an Article page without an Item Image Badge", "Search")
            commonFunctionKotlin.scrolltoElement(androidDriver, basePageObject.artictleitem_withoutitembadge)
            commonFunctionKotlin.tapButton(androidDriver, basePageObject.artictleitem_withoutitembadge, false)

            var i = 0
            while (i < basePageObject.articledetailpagelinks.size && i < basePageObject.articleitemwithimagebadge.size) {
                commonFunctionKotlin.isElementPresent(androidDriver, By.id(basePageObject.articledetailpagelinks[i]))
                assertEquals(basePageObject.articleitemwithoutimagebadge[i], androidDriver.findElement(By.id(basePageObject.articledetailpagelinks[i])).text, "Test matched")
                i++
            }

            commonFunctionKotlin.navigateBack(androidDriver)

        } catch (e: AssertionError) {
            throw e
        }

    }

    @Test(priority = 21, description = "Test Checking an Article page with an Item Image Badge")
    @Throws(Exception::class)
    fun testArtictleItemWithItemBadge() {
        try {

            commonFunctionKotlin.startTest("Article with ItemImage Badge", "Checking an Article page without an Item Image Badge", "Search")
            commonFunctionKotlin.scrolltoElement(androidDriver, basePageObject.artictleitem_withitembadge)
            commonFunctionKotlin.tapButton(androidDriver, basePageObject.artictleitem_withitembadge, false)
            commonFunctionKotlin.elementDisplayed(androidDriver, basePageObject.article_imagebade)
            assertEquals("EPA", basePageObject.article_imagebade.text, "Text Matched")
            var i = 0
            while (i < basePageObject.articledetailpagelinks.size && i < basePageObject.articleitemwithimagebadge.size) {
                commonFunctionKotlin.isElementPresent(androidDriver, By.id(basePageObject.articledetailpagelinks[i]))
                assertEquals(basePageObject.articleitemwithimagebadge[i], androidDriver.findElement(By.id(basePageObject.articledetailpagelinks[i])).text, "Test matched")
                i++
            }

            commonFunctionKotlin.navigateBack(androidDriver)
            commonFunctionKotlin.tapButton(androidDriver, basePageObject.backButton, false)
            commonFunctionKotlin.tapButton(androidDriver, basePageObject.cancelSearch, false)
        } catch (e: AssertionError) {
            throw e
        }

    }


    @Test(priority = 22, description = "Test to search for an particular article")
    @Throws(Exception::class)
    fun testSearchArticle() {
        try {
            commonFunctionKotlin.startTest("Searching a Particular article", "Test to search for an particular article", "Search")
            commonFunctionKotlin.tapButton(androidDriver, basePageObject.searchbutton, false)
            // basePageObject.searchfield.clear();
            commonFunctionKotlin.enterText(basePageObject.searchfield, "A rape victim's two-year wait for justice")
            //androidDriver.hideKeyboard();
            commonFunctionKotlin.tapButton(androidDriver, basePageObject.articlesearch, false)
            commonFunctionKotlin.sleepmethod(500)

            var i = 0
            while (i < basePageObject.articlepagedetail.size && i < basePageObject.articlepagedetailelements.size) {
                commonFunctionKotlin.isElementPresent(androidDriver, By.id(basePageObject.articlepagedetailelements[i]))
                assertEquals(basePageObject.articlepagedetail[i], androidDriver.findElement(By.id(basePageObject.articlepagedetailelements[i])).text, "Test matched")
                i++
            }
            commonFunctionKotlin.tapButton(androidDriver, basePageObject.navigate_back, false)
            commonFunctionKotlin.tapButton(androidDriver, basePageObject.backButton, false)

        } catch (e: AssertionError) {
            throw e
        }

    }

    @Test(priority = 23, description = "Test to search for an particular video article")
    @Throws(Exception::class)
    fun testondemandvideoplyback() {
        try {

            commonFunctionKotlin.startTest("Playing a Particular OnDemand Video", "Test to search ana play a on-demand video", "OnDemandVideo")
            commonFunctionKotlin.tapButton(androidDriver, basePageObject.search, false)
            commonFunctionKotlin.enterText(basePageObject.searchfield, "egypt court imposes jail")
            commonFunctionKotlin.waitForScreenToLoad(androidDriver, vidoePageObject.videoarticlesearch, 3)

            assertEquals("Articles related to \"egypt court imposes jail\"", basePageObject.searchheading.getText())//androidDriver.findElement(By.id("bbc.mobile.news.uk.internal:id/heading")).getText());
            commonFunctionKotlin.elementDisplayed(androidDriver, androidDriver.findElement(By.id("bbc.mobile.news.uk.internal:id/content_card_title")))
            commonFunctionKotlin.elementDisplayed(androidDriver, androidDriver.findElement(By.id("bbc.mobile.news.uk.internal:id/content_card_last_updated")))
            val videotitle = vidoePageObject.videoarticlesearch.getText()
            val videolastupdated = androidDriver.findElement(By.id("bbc.mobile.news.uk.internal:id/content_card_last_updated")).getText()

            commonFunctionKotlin.tapButton(androidDriver, vidoePageObject.videoarticlesearch, false)
            assertEquals(videotitle, androidDriver.findElement(By.id("bbc.mobile.news.uk.internal:id/headline_title")).getText())
            assertEquals("31 Dec 2018", androidDriver.findElement(By.id("bbc.mobile.news.uk.internal:id/headline_info")).getText())

            var i = 0
            while (i < vidoePageObject.videodetailpage.size && i < vidoePageObject.videdetailpagetext.size) {

                commonFunctionKotlin.isElementPresent(androidDriver, By.id(vidoePageObject.videodetailpage[i]))
                assertEquals(vidoePageObject.videdetailpagetext[i], androidDriver.findElement(By.id(vidoePageObject.videodetailpage[i])).getText())
                i++
            }
        } catch (e: AssertionError) {
            throw e

        }

    }

    @Test(priority = 24, description = "Test to seek forward videoplayback")
    @Throws(InterruptedException::class)
    fun testseekingvideoforward() {
        commonFunctionKotlin.startTest("Seeking Video Forward", "Test to search ana play a on-demand video", "OnDemandVideo")
        commonFunctionKotlin.tapButton(androidDriver, vidoePageObject.smp_placeholder_play_button, false)
        commonFunctionKotlin.seeking(androidDriver, vidoePageObject.smp_seek_bar, .50, "forward")
        vidoePageObject.elapsedtime_forward = vidoePageObject.smpelapsedtime.text
    }

    @Test(priority = 25, description = "Test to seek forward videoplayback")
    @Throws(InterruptedException::class)
    fun testseekingvideobackward() {
        commonFunctionKotlin.startTest("Seeking Video Backward", "Test to search ana play a on-demand video", "OnDemandVideo")
        commonFunctionKotlin.seeking(androidDriver, vidoePageObject.smp_seek_bar, .30, "backward")
        vidoePageObject.elapsedtime_backward = vidoePageObject.smpelapsedtime.text
        assertNotEquals(vidoePageObject.elapsedtime_forward, vidoePageObject.elapsedtime_backward)

    }

    @Test(priority = 26, description = "Test to check Related Stories and Topics of an Article")
    @Throws(Exception::class)
    fun testRelatedStoriesArticle() {
        commonFunctionKotlin.startTest("Checking for Related Story Article", "Test to check Related Stories of an Article", "Related Stories/Topics")
        commonFunctionKotlin.scrolltoElement(androidDriver, popularPageObject.relatedstorieArticle)
        commonFunctionKotlin.elementDisplayed(androidDriver, basePageObject.relatedstories)
        commonFunctionKotlin.tapButton(androidDriver, popularPageObject.relatedstorieArticle, false)
        for (i in 0 until popularPageObject.mostreadpopularlinks.size) {
            commonFunctionKotlin.isElementPresent(androidDriver, By.id(popularPageObject.mostreadpopularlinks[i]))
        }
        if (!basePageObject.sharestory.isDisplayed) {
            commonFunctionKotlin.verticalSwipe(androidDriver, "Up")
        }
        commonFunctionKotlin.tapButton(androidDriver, basePageObject.navigate_back, false)
    }

    @Test(priority = 27, description = "Test to check Related Topics of an Article")
    @Throws(Exception::class)
    fun testRelatedTopicArticle() {

        commonFunctionKotlin.startTest("Checking for Related Topic Article", "Test to check Related Topics of an Article", "Related Stories/Topics")
        commonFunctionKotlin.scrolltoElement(androidDriver, popularPageObject.relatedtopicsArticle)
        commonFunctionKotlin.elementDisplayed(androidDriver, basePageObject.relatedtopics)
        commonFunctionKotlin.tapButton(androidDriver, popularPageObject.relatedtopicsArticle, false)
        assertEquals("Egypt", androidDriver.findElement(By.id("bbc.mobile.news.uk.internal:id/title")).text)
        assertEquals("Add topic", androidDriver.findElement(By.id("bbc.mobile.news.uk.internal:id/menu_follow")).getAttribute("contentDescription"))
        for (i in 0 until basePageObject.topicspageelemnets.size) {
            commonFunctionKotlin.isElementPresent(androidDriver, By.id(basePageObject.topicspageelemnets[i]))
        }
        commonFunctionKotlin.tapButton(androidDriver, basePageObject.backButton, false)
        commonFunctionKotlin.navigateBack(androidDriver)
        commonFunctionKotlin.tapButton(androidDriver, basePageObject.backButton, false)

    }

    @Test(priority = 28, description = "Test to check the T&C , PrivacyPolicy from Menu options")
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


    @Test(priority = 29, description = "Playing a video from Video page")
    @Throws(Exception::class)
    fun testTopStoriesvideo() {

        commonFunctionKotlin.startTest("Playing a OnDemand Video", "Test to play a video from Video page", "OnDemandVideo")
        commonFunctionKotlin.tapButton(androidDriver, basePageObject.video, false)
        //commented out as Top Stories link isn't displayed
        //commonFunctionKotlin.elementDisplayed(androidDriver, vidoePageObject.topstories)
//        commonFunctionKotlin.scrolltoElement(androidDriver, vidoePageObject.topstoriesvideo)
//        commonFunctionKotlin.elementDisplayed(androidDriver, vidoePageObject.topstoriesvideoplaytime)
//        commonFunctionKotlin.elementDisplayed(androidDriver, vidoePageObject.topstoriesvideocontent_card_title)
//        commonFunctionKotlin.elementDisplayed(androidDriver, vidoePageObject.topstoriesvideocontent_card_link)
//        commonFunctionKotlin.elementDisplayed(androidDriver, vidoePageObject.topstoriesvideocontent_card_info)

        commonFunctionKotlin.tapButton(androidDriver, vidoePageObject.topstoriesvideo, false)
        commonFunctionKotlin.tapButton(androidDriver, vidoePageObject.smp_placeholder_play_button, false)
        // androidDriver.findElementByAccessibilityId("Play").click();
        vidoePageObject.transportcontrol.click()
        vidoePageObject.transportcontrol.click()
        for (i in 0 until popularPageObject.popularvideoelements.size) {
            commonFunctionKotlin.isElementPresent(androidDriver, By.id(popularPageObject.popularvideoelements[i]))
        }
        commonFunctionKotlin.navigateBack(androidDriver)
    }


    @Test(priority = 30, description = "App Backgrounding")
    fun testAppbackground() {
        commonFunctionKotlin.startTest("App Background ", "Test to check backgrouding the app and reopen and checking same page opens", "App Background")
        basePageObject.popular.click()
        androidDriver.runAppInBackground(Duration.ofSeconds(30))
        (androidDriver as StartsActivity).currentActivity()
        try {
            assertTrue(basePageObject.popular.isSelected)
        } catch (e: AssertionError) {
            throw e
        }

    }

    @Test(priority = 31, description = "Test to check the Popular page and also to check Most Read Displayed")
    @Throws(Exception::class)
    fun testPopularPage() {
        try {

            commonFunctionKotlin.startTest("Checking PopularPage", "Checking Popular Page", "Popular")
            commonFunctionKotlin.tapButton(androidDriver, popularPageObject.popular, false)
            commonFunctionKotlin.elementDisplayed(androidDriver, popularPageObject.mostread)

        } catch (e: AssertionError) {
        }

    }

    @Test(priority = 32, description = "Test to select one Article from Most Read  Article from Popular Page")
    @Throws(Exception::class)
    fun testCheckMostReadPopular() {
        try {
            commonFunctionKotlin.startTest("Checking Article from Most Read Section", "Checking Most Read Popular", "Popular")
            commonFunctionKotlin.elementDisplayed(androidDriver, popularPageObject.mostread)
            commonFunctionKotlin.tapButton(androidDriver, popularPageObject.mostRead_article, false)

        } catch (e: NullPointerException) {
        }

    }


    @Test(priority = 33, description = "Test to check whether the Most Watched heading displayed in Popular Page")
    @Throws(Exception::class)
    fun testcheckMostWatched() {
        try {
            commonFunctionKotlin.navigateBack(androidDriver)
            commonFunctionKotlin.startTest("Checking Most Popular Section", "Checking Most Watched", "Popular")
            commonFunctionKotlin.scrolltoElement(androidDriver, popularPageObject.popularmostwatched)
            commonFunctionKotlin.elementDisplayed(androidDriver, popularPageObject.popularmostwatched)

        } catch (e: NullPointerException) {
        }

    }


    @Test(priority = 34, description = "Test to check whether the Most Watched heading displayed in Popular Page")
    @Throws(Exception::class)
    fun testcheckMostWatchedArticle() {
        try {

            commonFunctionKotlin.startTest("Checking Most Watched Article ", "Checking Most Watched", "Popular")
            commonFunctionKotlin.scrolltoElement(androidDriver, popularPageObject.mostwatchedartcilevideo)
            System.out.println("The Element selected is  :- " + popularPageObject.mostwatchedartcilevideo.text)
            popularPageObject.mostwatchedartcilevideo.click()
            if (!popularPageObject.mostpopular.isDisplayed()) {
                commonFunctionKotlin.verticalSwipe(androidDriver, "Up")
            }
            for (i in 0 until vidoePageObject.videowallelements.size) {
                commonFunctionKotlin.isElementPresent(androidDriver, By.id(vidoePageObject.videowallelements[i]))
            }
            commonFunctionKotlin.navigateBack(androidDriver)


        } catch (e: NullPointerException) {
        }

    }

    @Test(priority = 35, description = "Test re-arrange topics from bottom to top")
    fun testArrangeTopicsFromBottomToTop() {
        commonFunctionKotlin.startTest("Re Arrange Topics from Bottom-to-Top", "Test re-arrange topics from bottom to top", "MyNews")
        commonFunctionKotlin.tapButton(androidDriver, basePageObject.mynews, false)
        commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.editMyNews, false)

        val india = androidDriver.findElementsById("bbc.mobile.news.uk.internal:id/grab_handle")[5]
        val europe = androidDriver.findElementsById("bbc.mobile.news.uk.internal:id/grab_handle")[0]

        commonFunctionKotlin.elementdragdrop(androidDriver, india, europe)
        commonFunctionKotlin.readRecyclerView(androidDriver, "Topics After  Re-Ordering :- ")

        assertNotEquals("Rape in India", androidDriver.findElement(By.xpath("//android.support.v7.widget.RecyclerView[@index='1']/android.widget.RelativeLayout[@index='5']/android.widget.TextView[@index='1']")).text, "Test Didn't Matched")
        assertEquals("Rape in India", androidDriver.findElement(By.xpath("//android.support.v7.widget.RecyclerView[@index='1']/android.widget.RelativeLayout[@index='1']/android.widget.TextView[@index='1']")).text, "Test Matched")
        // Assert.assertEquals("European Union",androidDriver.findElement(By.xpath("//android.support.v7.widget.RecyclerView[@index='1']/android.widget.RelativeLayout[@index='2']/android.widget.TextView[@index='1']")).getText(), "Test Matched");
        assertEquals("YouTube", androidDriver.findElement(By.xpath("//android.support.v7.widget.RecyclerView[@index='1']/android.widget.RelativeLayout[@index='5']/android.widget.TextView[@index='1']")).text, "Test Matched")
        commonFunctionKotlin.tapButton(androidDriver,myNewsPageObject.removetopics,false)
        commonFunctionKotlin.tapButton(androidDriver,myNewsPageObject.removetopics,false)
        commonFunctionKotlin.navigateBack(androidDriver)
    }


    @Test(priority = 36, description = "Test to check the offline scenario of the app")
    fun testcheckofflinescenario() {
        commonFunctionKotlin.startTest("Going OffLine", "Checking apps offline scenario", "Offline")
        val state = androidDriver.setConnection(ConnectionStateBuilder()
                .withWiFiDisabled()
                .build())
        androidDriver.setConnection(state)
//        commonFunctionKotlin.waitForScreenToLoad(androidDriver,basePageObject.topstories,10);
//        commonFunctionKotlin.tapButton(androidDriver,basePageObject.topstories,false);
//        commonFunctionKotlin.scrolltoElement(androidDriver, homePageObject.videoOftheDay_watch);
//        commonFunctionKotlin.tapButton(androidDriver, homePageObject.videooftheday_button, false);
//        Assert.assertEquals("You're not connected to the internet.", myNewsPageObject.snackbar.getText(), "Text Matched");
    }

    @Test(priority = 37, description = "Test to play a  video, while device offline")
    @Throws(Exception::class)
    fun testplayingvideooffline() {
        try {
            commonFunctionKotlin.startTest("VideoPlayback-Offline", "Checking the Video while device offline", "Offline")
            commonFunctionKotlin.tapButton(androidDriver, basePageObject.video, false)
            commonFunctionKotlin.tapButton(androidDriver, vidoePageObject.bbcnewsChannel, false)
            commonFunctionKotlin.elementDisplayed(androidDriver, vidoePageObject.live_media_item_caption)
            commonFunctionKotlin.elementDisplayed(androidDriver, basePageObject.navigate_back)
            commonFunctionKotlin.elementDisplayed(androidDriver, basePageObject.sharestory)
            commonFunctionKotlin.tapButton(androidDriver, vidoePageObject.smp_placeholder_play_button, false)
            commonFunctionKotlin.elementDisplayed(androidDriver, basePageObject.smperrormessage)
            commonFunctionKotlin.elementDisplayed(androidDriver, basePageObject.smperrorokbutton)
            commonFunctionKotlin.elementDisplayed(androidDriver, basePageObject.smpretrybuton)
            val state = androidDriver.setConnection(ConnectionStateBuilder()
                    .withWiFiEnabled()
                    .build())
            androidDriver.setConnection(state)
            System.out.println("The Connection state is " + state.isWiFiEnabled)
            commonFunctionKotlin.sleepmethod(1000)
            assertTrue(state.isWiFiEnabled)
            commonFunctionKotlin.navigateBack(androidDriver)
            commonFunctionKotlin.tapButton(androidDriver, basePageObject.mynews, false)
            commonFunctionKotlin.tapButton(androidDriver, basePageObject.popular, false)

        } catch (e: Exception) {
        }

    }


    @Test(priority = 38, description = "Test to play a  video, while device online")
    @Throws(Exception::class)
    fun testPlayingVideoOnLine() {
        try {

            commonFunctionKotlin.startTest("VideoPlayback-Online", "Checking the Video while device Online", "Offline")
            commonFunctionKotlin.tapButton(androidDriver, basePageObject.video, false)
            commonFunctionKotlin.tapButton(androidDriver, vidoePageObject.bbcnewsChannel, false)
            commonFunctionKotlin.tapButton(androidDriver, vidoePageObject.smp_placeholder_play_button, false)
            commonFunctionKotlin.tapButton(androidDriver,basePageObject.navigate_back,false)

        } catch (e: Exception) {

        }

    }


    @Test(priority = 39, description = "Test to select An Article from the England Topics under MyNews ")
    @Throws(Exception::class)
    fun testSelectArticleTopic() {
        try {
            commonFunctionKotlin.startTest("Selecting a Article from Africa Topics", "Test to select An Article from the Africa Topics under MyNews", "MyNews")
            commonFunctionKotlin.tapButton(androidDriver,basePageObject.mynews,false)
            commonFunctionKotlin.tapButton(androidDriver,myTopicsPageObject.Englandtopic,false)
            commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.topicarticle, false)
            commonFunctionKotlin.elementDisplayed(androidDriver, myTopicsPageObject.Englandtopic)
            commonFunctionKotlin.navigateBack(androidDriver)

        } catch (e: StaleElementReferenceException) {
        }

    }

    @Test(priority = 40, description = "Test to select An Video Article from the England Topics under MyNews ")
    @Throws(Exception::class)
    fun testSelectVideoArticleTopic() {
        try {
            commonFunctionKotlin.startTest("Select a Video Article from Africa Topic", "Test to select An Video Article from the Africa Topics under MyNews", "MyNews")
            commonFunctionKotlin.scrolltoElement(androidDriver, myNewsPageObject.mynewsrecyclerview)
            commonFunctionKotlin.tapButton(androidDriver, myNewsPageObject.topicvideoarticle, false)
            commonFunctionKotlin.elementDisplayed(androidDriver, myTopicsPageObject.Englandtopic)
            for (i in 0 until vidoePageObject.videowallelements.size) {
                commonFunctionKotlin.isElementPresent(androidDriver, By.id(vidoePageObject.videowallelements[i]))
            }
            commonFunctionKotlin.tapButton(androidDriver, vidoePageObject.smp_placeholder_play_button, false)
            commonFunctionKotlin.sleepmethod(1300)
            for (i in 0 until vidoePageObject.playbackcontrols.size) {
                commonFunctionKotlin.isElementPresent(androidDriver, By.id(vidoePageObject.playbackcontrols[i]))
            }
            commonFunctionKotlin.navigateBack(androidDriver)
            commonFunctionKotlin.tapButton(androidDriver, basePageObject.backButton, false)
        } catch (e: StaleElementReferenceException) {
        }

    }

    /**
     * commented out as Video of the day isn't displayed
     */

//    @Test(priority = 42, description = "Test to check the offline scenario of the app")
//    fun testcheckOnlineinescenario() {
//        commonFunctionKotlin.startTest("VideOfTheDay - Online", "Checking apps offline scenario", "Offline")
//        commonFunctionKotlin.tapButton(androidDriver, basePageObject.topstories, false)
//        commonFunctionKotlin.scrolltoElement(androidDriver, homePageObject.videoOftheDay_watch)
//        commonFunctionKotlin.tapButton(androidDriver, homePageObject.videooftheday_button, false)
//        //extenttestReport.isElementPresent(androidDriver,By.id("bbc.mobile.news.uk:id/snackbar_text"));
//        commonFunctionKotlin.navigateBack(androidDriver)
//
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
        androidDriver.closeApp()
        androidDriver.removeApp("bbc.mobile.news.uk.internal")
        androidDriver.quit()

    }
}





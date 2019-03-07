package com.bbcnews.automation.commonfunctions


import com.aventstack.extentreports.ExtentReports
import com.aventstack.extentreports.ExtentTest
import com.aventstack.extentreports.Status
import com.aventstack.extentreports.markuputils.ExtentColor
import com.aventstack.extentreports.markuputils.MarkupHelper
import com.aventstack.extentreports.reporter.ExtentHtmlReporter
import com.aventstack.extentreports.reporter.configuration.ChartLocation
import com.aventstack.extentreports.reporter.configuration.Theme
import com.bbcnews.automation.testutils.PlatformTouchAction
import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.android.connection.ConnectionStateBuilder
import io.appium.java_client.android.nativekey.AndroidKey
import io.appium.java_client.android.nativekey.KeyEvent
import io.appium.java_client.functions.ExpectedCondition
import io.appium.java_client.touch.WaitOptions
import io.appium.java_client.touch.offset.ElementOption
import io.appium.java_client.touch.offset.PointOption
import org.apache.commons.io.FileUtils
import org.openqa.selenium.*
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.testng.Assert
import org.testng.Assert.assertEquals
import org.testng.ITestResult
import ru.yandex.qatools.ashot.AShot

import java.awt.*
import java.awt.image.PixelGrabber
import java.io.*
import java.nio.file.Paths

import java.nio.file.Files
import java.text.SimpleDateFormat
import java.time.Duration
import java.util.ArrayList
import java.util.Date

import javax.imageio.ImageIO



open class CommonFunctionKotlin {


    private lateinit var extent: ExtentReports
    private lateinit var htmlReporter: ExtentHtmlReporter
    var test: ExtentTest? = null
    var result: ITestResult? = null

    var appiumDriver: AppiumDriver<MobileElement>? = null


    var config_file = "extent-config.xml"
    var workingDirectory = System.getProperty("user.dir")
    private val curDate = Date()
    private val format = SimpleDateFormat("yyyy-MM-dd")
    private val DateToStr = format.format(curDate)
    var absoluteFilePath = "$workingDirectory/Results"
    var screenshotpathfolder = "$workingDirectory/Screenshots"

    @Throws(Exception::class)
    fun startReport(reportname: String) {

        val Deviceos_Name = System.getProperty("DeviceOS")
        val Device_id = System.getProperty("DeviceID")
        val Device_Name = System.getProperty("DeviceName")


        val curDate = Date()
        println(curDate.toString())


        println("absoluteFilePath is $absoluteFilePath")
        Thread.sleep(4000)

        val reportfolder = extentResultFolder(absoluteFilePath)
        println("reportfolder is $reportfolder")
        Thread.sleep(4000)

        //htmlReporter = new ExtentHtmlReporter(reportfolder+File.separator+reportname+device_name+dateName+".html");
        htmlReporter = ExtentHtmlReporter("$reportfolder$reportname$Device_Name.html")
        extent = ExtentReports()
        extent.attachReporter(htmlReporter)

        htmlReporter.setAppendExisting(true)


        extent.setSystemInfo("Device ID", Device_id)
        extent.setSystemInfo("Firmware version", Deviceos_Name)
        extent.setSystemInfo("Device Name ", Device_Name)
        extent.setSystemInfo("Run Started on", curDate.toString())


        htmlReporter.config().chartVisibilityOnOpen = true
        htmlReporter.config().documentTitle = "BBC News Android Report "
        htmlReporter.config().reportName = "Test Report"
        htmlReporter.config().testViewChartLocation = ChartLocation.TOP
        htmlReporter.config().theme = Theme.STANDARD
        htmlReporter.config().timeStampFormat = "EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'"


    }

    open fun startTest(testName: String, testDecsription: String, category: String) = try {
        test = extent.createTest(testName, testDecsription)
        test?.assignCategory(category)
    } catch (e: Exception) {

    }

    open fun publishReport() {
        extent.flush()


    }




    /**
     * Appium Android default method
     * even though the AndroidKeycode is deprected in latest appium and java client
     *
     * @param, androidDriver, only works with Android
     */

    fun navigateBack(androiddriver: AndroidDriver<MobileElement>) {
        //androiddriver.pressKeyCode(AndroidKeyCode.BACK);
        androiddriver.pressKey(KeyEvent(AndroidKey.BACK))

    }

    /**
     * Function on click on any button or link on the app
     *
     * @param, driverType, element Type
     * boolean to take screenshot ( true = takes screenshot and attached to testReport, fail= wont take screenshot)
     * Screenshot path
     */
    fun tapButton(appiumDriver: AppiumDriver<MobileElement>, element: MobileElement, takescreenshot: Boolean) {
        try {
            waitForScreenToLoad(appiumDriver, element, 3)
            element.click()
            Thread.sleep(2000)
            if (takescreenshot) {
                val screenshotpath = getScreenshot(appiumDriver, element.text)
                println("Taken Screenshotpath is $screenshotpath")
                test?.log(Status.INFO, "Screenshot Attached:-" + test?.addScreenCaptureFromPath(screenshotpath))

            } else {

            }
        } catch (e: Exception) {
        }

    }


//    fun tapButtons(appiumDriver: AppiumDriver<MobileElement>, element: MobileElement?, takescreenshot: Boolean) {
//        try {
//            waitForScreenToLoads(appiumDriver, element, 3)
//            element?.click()
//            Thread.sleep(2000)
//            if (takescreenshot) {
//                val screenshotpath = getScreenshot(appiumDriver, element?.text.toString())
//                println("Taken Screenshotpath is $screenshotpath")
//                test?.log(Status.INFO, "Screenshot Attached:-" + test?.addScreenCaptureFromPath(screenshotpath))
//
//            } else {
//
//            }
//        } catch (e: Exception) {
//        }
//
//    }

    /**
     * Function to wait until the screen is fully loaded
     * @param, drivertype , element and seconds to wait for page to load
     */

    open fun waitForScreenToLoad(driver: AppiumDriver<MobileElement>, element: MobileElement?, seconds: Int) {

        val wait = WebDriverWait(driver, seconds.toLong())
        wait.until<WebElement>(ExpectedConditions.visibilityOf(element))

    }


//    open fun waitForScreenToLoads(driver: AppiumDriver<MobileElement>, element: MobileElement?, seconds: Int) {
//
//        val wait = WebDriverWait(driver, seconds.toLong())
//        wait.until<WebElement>(ExpectedConditions.visibilityOf(element))
//
//    }

    /**
     * @param, drivertype, screenshot path, screenshot name
     * attaches the screenshot to the test report
     */

    private fun getScreenshot(appiumdriver: AppiumDriver<MobileElement>, screenshotName: String): String {
        val subDirectory = "Screenshots"
        val screenshotPaths: String

        try {
            val dateName = SimpleDateFormat("dd-M-yyyy hh:mm").format(Date())
            val ts = appiumdriver as TakesScreenshot
            val source = ts.getScreenshotAs(OutputType.FILE)

            screenshotPaths = extentResultFolder(subDirectory).toString()
            val file = File(screenshotPaths)
            println("the ScreenShot  Folder is :- " + file.absolutePath)

            val dest = file.absolutePath + File.separator + screenshotName + "_" + dateName + ".png"
            println("Screenshot path name:------$dest")
            val destination = File(dest)
            FileUtils.copyFile(source, destination)
            println("ScreenShot Taken")
            return dest
        } catch (e: Exception) {
            println("Exception While Taking screenshot" + e.message)
            return e.message.toString()
        }

    }

    /**
     * Function to create a folder with the project path
     * @param, Directory path
     */
    open fun extentResultFolder(path: String): String? {
        var strManyDirectories: String? = null
        try {
            //  String strDirectoy = path;
            strManyDirectories = path

            // Create one directory
            val
            // Create multiple directories
                    success = File(strManyDirectories).mkdirs()
            if (success) {
                println("Directories: "
                        + strManyDirectories + " created")
            }

        } catch (e: Exception) {//Catch exception if any
            System.err.println("Error: " + e.message)
        }

        return strManyDirectories
    }


    /**
     * Function to scroll to an element, if the list if very big
     *
     * @param, driverType, element to be scrolled, screenshot
     */
    fun scrolltoElement(appiumDriver: AppiumDriver<MobileElement>, element: MobileElement) {
        for (i in 0..20) {
            try {
                element.isDisplayed
                //element.click()
                break
            } catch (e: Exception) {
                verticalSwipe(appiumDriver,"Down")


            }

        }
    }

    /**
     * Function to seek vertical on the app.
     * Startx remains constant
     * StartY and EndY are the two main parameters to swipe vertically
     * @param, driverType
     */

    open fun verticalSwipe(driver: AppiumDriver<MobileElement>,swipingdirection:String) {
        val dimension = driver.manage().window().size
        val height = dimension.getHeight()
        val width = dimension.getWidth()
        val startX = width / 2
        val startY = (height * 0.75).toInt()
        val endY = (height * 0.35).toInt()

//        val action = TouchAction(performsTouchActions = driver)
//        action.press(PointOption.point(startX, startY))
//                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
//                .moveTo(PointOption.point(startX, endY)).release().perform()
        if(swipingdirection.equals("Down")) {
            PlatformTouchAction(driver).press(PointOption.point(startX, startY))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                    .moveTo(PointOption.point(startX, endY)).release().perform()
        }else if(swipingdirection.equals("Up"))
        {
            PlatformTouchAction(driver).press(PointOption.point(startX, endY))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                    .moveTo(PointOption.point(startX, startY)).release().perform()
        }
    }


    /**
     * wait method
     */
    fun sleepmethod(seconds: Long) {
        try {
            Thread.sleep(seconds)
        } catch (e: Exception) {
        }

    }

    /**
     * Function to seek the video, you need pass the percentage of seeking
     * @param appiumdriver
     * @param element
     * @param d
     * @throws InterruptedException
     */

    fun livevideoseeking(appiumdriver: AppiumDriver<MobileElement>, element: MobileElement, d: Double) {
        val startx = element.location.getX()
        val endx = element.size.width
        val yaxis = element.location.getY()
        val moveToXDirectionAt = (endx * d).toInt()

        PlatformTouchAction(appiumdriver).press(PointOption.point(startx, yaxis)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000))).moveTo(PointOption.point(moveToXDirectionAt, yaxis)).release().perform()


    }


    /**
    Function to enter the text into a textfeld
    @param, driverType, element and string that's need to be entered
     */

    fun entersearchtext(element: MobileElement, searchkey: String) {
        element.sendKeys(searchkey)
    }


    /**
     * Function to check whether an Element is present or not
     */

    fun isElementPresent(appiumDriver: AppiumDriver<MobileElement>, locatorKey: By): Boolean {
        try {
            appiumDriver.findElement(locatorKey)
            test?.log(Status.PASS, appiumDriver.findElement(locatorKey).getText() + "Element Present")
            return true

        } catch (e: NoSuchElementException) {
            test?.log(Status.INFO, appiumDriver.findElement(locatorKey).getText() + "Element Not Present")
            return false
        }
    }


    /**
     * Function to check whether an element is displayed , return true if present else fail
     * If true, then the element text will be attached the report name. If element text not present, it uses the
     * element attribute
     *
     * @param, drivertype, element name
     */

    @Throws(Exception::class)
    fun elementDisplayed(appiumDriver: AppiumDriver<MobileElement>, element: MobileElement) {

        try {
            waitForScreenToLoad(appiumDriver, element, 3)
            Assert.assertTrue(element.isDisplayed)
            if (element.isDisplayed) {
                if (element.text.isEmpty()) {
                    test?.log(Status.PASS, element.getAttribute("contentDescription") + "  Displayed")
                } else {
                    test?.log(Status.PASS, element.text + "  Displayed")
                }

            } else {
                test?.log(Status.FAIL, element.text + "  isn't Displayed")
            }

        } catch (e: AssertionError) {
            e.printStackTrace()
            org.testng.Assert.fail()
        }

    }

    fun getTestResult(appiumDriver: AppiumDriver<MobileElement>, result: ITestResult) {
        try {
            when {
                result.status == ITestResult.FAILURE -> {

                    test?.fail(MarkupHelper.createLabel(result.name + " Test Case is FAILED", ExtentColor.RED))
                    test?.fail(result.throwable)
                    try {
                        val screenshotPath = getScreenshot(appiumDriver, result.name)
                        test?.log(Status.FAIL, "Failed" + test?.addScreenCaptureFromPath(screenshotPath))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }
                result.status == ITestResult.SUCCESS -> test?.pass(MarkupHelper.createLabel(result.name + " Test Case is PASSED", ExtentColor.GREEN))
                result.status == ITestResult.SKIP -> {
                    test?.skip(MarkupHelper.createLabel(result.name + " Test Case is SKIPPED", ExtentColor.YELLOW))
                    test?.skip(result.throwable)

                }
            }
        } catch (e: NullPointerException) {

        }

    }


    /**
     * function to create a ExtentReport
     * @reportname
     * @deviceOS, @deviceName, @deviceId
     *
     */


    @Throws(Exception::class)
    fun createrReportHive(reportname: String, deviceName: String, deviceId: String) {

        val curDate = Date()
        println(curDate.toString())

        // val dateName = SimpleDateFormat("hh:mm").format(Date())

        val reportfolder = extentResultFolder(absoluteFilePath)
        println("reportfolder is $reportfolder")

        val subDirectory = "Results"
        val resultsPaths: String
        resultsPaths = extentResultFolder(subDirectory).toString()
        val file = File(resultsPaths)
        println("the Result path Folder is :- " + file.absolutePath)


        htmlReporter = ExtentHtmlReporter(file.absolutePath + File.separator + reportname + ".html")//"_"+deviceName+"_"+deviceOS+ ".html");
        extent = ExtentReports()
        extent.attachReporter(htmlReporter)
        htmlReporter.setAppendExisting(false)
        extent.setSystemInfo("Device ID", deviceId)
       // extent.setSystemInfo("Firmware version", deviceOS)
        extent.setSystemInfo("Device Name ", deviceName)
        extent.setSystemInfo("Run Started on", curDate.toString())


        htmlReporter.config().chartVisibilityOnOpen = true
        htmlReporter.config().documentTitle = "BBC News Android Report "
        htmlReporter.config().reportName = "Automation Test Report"
        htmlReporter.config().testViewChartLocation = ChartLocation.TOP
        htmlReporter.config().theme = Theme.STANDARD
        htmlReporter.config().timeStampFormat = "EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'"


    }


    /**
     * Function to scroll on TopStories - Videos and Stories carousel
     * @param, driverType, element and path for screenshot to be taken
     */

    fun scrolltoEndofStories(appiumDriver: AppiumDriver<MobileElement>, element: MobileElement,
                             elements: Array<String>, element2: MobileElement
    ) {
       // val flag = false
        for (i in 0..20) {
            try {
                waitForScreenToLoad(appiumDriver, element, 5)
                Thread.sleep(800)
                val element_title = element.getText()
                test?.log(Status.INFO, element_title)
                for (j in elements.indices) {
                    isElementPresent(appiumDriver, By.id(elements[j]))
                    test?.log(Status.PASS, elements[j])
                }
                Thread.sleep(800)
                element2.isDisplayed()
                //element.click();
                break
            } catch (e: Exception) {

                horizontalSwipe(appiumDriver)

            }

        }
    }

    /**
     * Function to seek horizontal on the app.
     * Yaxis remains horizontal
     * StartXaxis and endXaxis are the two main parameters to swipe vertically
     * @param, driverType
     */
    private fun horizontalSwipe(driver: AppiumDriver<MobileElement>) {
        val dimension = driver.manage().window().size
        val height = dimension.getHeight()
        val width = dimension.getWidth()
        val startXaxis = (width * 0.90).toInt()
        val yaxis = (height * 0.20).toInt()
        val endXaxis = (width * 0.10).toInt()

//         val action = TouchAction(driver)
//         action.press(PointOption.point(startXaxis, Yaxis))
//                 .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
//                 .moveTo(PointOption.point(endXaxis, Yaxis)).release().perform()

        PlatformTouchAction(driver).press(PointOption.point(startXaxis, yaxis))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                .moveTo(PointOption.point(endXaxis, yaxis)).release().perform()


    }


    /**
     * Function to check, whether given element is selected or not
     * @param, drivertype, element name
     */
    fun iselementSelected(element: MobileElement): Boolean {
        return if (element.isSelected) {
            test?.log(Status.INFO, "Element selected")
            true

        } else {
            test?.log(Status.INFO, "Element not selected")
            false

        }
    }

    /**
     * Function to enter the text into a textfeld
     * @param, element and string that's need to be entered
     */

    fun enterText(element: MobileElement, searchkey: String) {
        try {
            element.sendKeys(searchkey)
        } catch (e: Exception) {
        }

    }

    /**
     * function to get the element text
     * @param, element
     */

    fun getText(element: MobileElement): String {
        return element.text
    }


    /**
     * Returns all png images from a directory in an array.
     *
     * @param directory                 the directory to start with
     * @param descendIntoSubDirectories should we include sub directories?
     * @return an ArrayList<String> containing all the files or nul if none are found..
     * @throws IOException
    </String> */
    @Throws(IOException::class)
    fun getAllImages(directory: File, descendIntoSubDirectories: Boolean): ArrayList<String>
    {
        val resultList = ArrayList<String>(256)
        val f = directory.listFiles()
        for (file in f!!) {
            if (file != null && file.name.toLowerCase().endsWith(".png"))
            {
                resultList.add(file.canonicalPath)
            }
            if (descendIntoSubDirectories && file!!.isDirectory)
            {
                val tmp = getAllImages(file, true)
                if (true)
                {
                    resultList.addAll(tmp)
                }
            }
        }
      if (resultList.size > 0)
         return  resultList
        else
          return null!!

    }


    /**
     * Function which compare the two images bi pixels and by dimension
     *
     * @throws IOException
     */

//    @Throws(IOException::class)
//    fun comparetwoimages() {
//        val expected = File("./Screenshots/Before")
//        val actual = File("./Screenshots/After")
//
//     //   var expectedresults = //ArrayList<String>()
//        var expectedresults = getAllImages(expected, false)
//        val expectedimages = expectedresults.toTypedArray()
//
//   //     var actualresults: ArrayList<String> = ArrayList()
//        var actualresults = getAllImages(actual, false)
//        val actualimages = actualresults.toArray(arrayOfNulls<String>(actualresults.size))
//        var i = 0
//        while (i < expectedimages.size && i < actualimages.size) {
//            println("Expected Image :=" + expectedimages[i])
//            println("Actual Image :=" + actualimages[i])
//
//            compareImage(File(expectedimages[i]), File(actualimages[i]))
//            processImage(expectedimages[i], actualimages[i])
//            i++
//
//
//        }
//    }


        /**
         * Function to compare two images and display the diffrence
         * @param fileA
         * @param fileB
         * @return
         */

//         fun compareImage(fileA: File, fileB: File): Float {
//
//            var percentage = 0f
//            try {
//                // take buffer data from both image files //
//                val biA = ImageIO.read(fileA)
//                val dbA = biA.getData().getDataBuffer()
//                val sizeA = dbA.getSize()
//                val biB = ImageIO.read(fileB)
//                val dbB = biB.getData().getDataBuffer()
//                val sizeB = dbB.getSize()
//                var count = 0
//                // compare data-buffer objects //
//                if (sizeA == sizeB) {
//
//                    for (i in 0 until sizeA) {
//
//                        if (dbA.getElem(i) === dbB.getElem(i)) {
//                            count = count + 1
//                        }
//
//                    }
//                    percentage = (count * 100 / sizeA).toFloat()
//                    println("Image Difference Percentage --> :- $percentage")
//                    test?.log(Status.PASS,"Image Difference Percentage --> :- " + percentage);
//
//                } else {
//                    println("Both the images are not of same size")
//                    // test.log(Status.FAIL,"Both the images are not of same size");
//                }
//
//            } catch (e: Exception) {
//                println("Failed to compare image files ...")
//            }
//
//            return percentage
//        }


        /**
         * Function to compare the images by pixel
         * @param expected
         * @param actual
         */
        private fun processImage(expected: String?, actual: String?) {
            val imagesrc = Toolkit.getDefaultToolkit().getImage(expected)
            val imagecom = Toolkit.getDefaultToolkit().getImage(actual)

            try {
                val grab1 = PixelGrabber(imagesrc, 0, 0, -1, -1, false)
                val grab2 = PixelGrabber(imagecom, 0, 0, -1, -1, false)

                var data1: IntArray? = null

                if (grab1.grabPixels()) {
                //    val width = grab1.getWidth()
                 //   val height = grab1.getHeight()
                    //data1 = IntArray(width * height)
                    data1 = grab1.getPixels() as IntArray?
                }

                var data2: IntArray? = null

                if (grab2.grabPixels()) {
                //    val width = grab2.getWidth()
                  //  val height = grab2.getHeight()
                    //data2 = IntArray(width * height)
                    data2 = grab2.getPixels() as IntArray?
                }

                println("Pixels equal: " + java.util.Arrays.equals(data1, data2))
                test?.log(Status.INFO, "Pixels equal: " + java.util.Arrays.equals(data1, data2))

            } catch (e1: InterruptedException) {
                e1.printStackTrace()
            }

        }


        /**
         * Function to take screenshot of page using the Ashot API
         * @param driver
         * @param folder
         * @param imagename
         * @throws IOException
         */
        @Throws(IOException::class)
        fun AshotScreenshot(driver: AndroidDriver<MobileElement>, folder: String, imagename: String) {
            val dateName = SimpleDateFormat("dd-M-yyyy hh:mm").format(Date())
            val directory = "Screenshots"
            val screenshotPaths = extentResultFolder(directory)

            extentResultFolder(folder)

            File(screenshotPaths)
            // success = (new File(strManyDirectories)).mkdirs();

            val myScreenshot = AShot().takeScreenshot(driver)
            val screenshotFolder = Paths.get(directory, folder)
            if (Files.notExists(screenshotFolder))
                Files.createDirectory(screenshotFolder)
            // To save the screenshot in desired location


            ImageIO.write(myScreenshot.getImage(), "PNG",
                     File(screenshotFolder.toString()+File.separator+imagename+dateName+".png"))


        }


    /**
     * Function to seek forward on the video/audio playing
     * @param, driverType, Element type
     * double the seeking position ex(.30) means 30% seek
     */

    @Throws(InterruptedException::class)
    fun videoplaybackseeking(driver: AppiumDriver<MobileElement>, element: MobileElement, d: Double, seekingtype: String) {
        val startX = element.location.getX()
        println("Startx :$startX")

        val endX = element.size.getWidth()
        println("Endx  :$endX")

        val yAxis = element.location.getY()
        println("Yaxis  :$yAxis")

        val moveToXDirectionAt = (endX * d).toInt()
        println("Moving seek bar at $moveToXDirectionAt In X direction.")
        Thread.sleep(3000)

        if (seekingtype.equals("forward", ignoreCase = true))
        {
            PlatformTouchAction(driver).press(PointOption.point(startX, yAxis))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                    .moveTo(PointOption.point(moveToXDirectionAt, yAxis)).release().perform()

        } else if (seekingtype.equals("backward", ignoreCase = true))
        {

            PlatformTouchAction(driver).press(PointOption.point(endX, yAxis))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                    .moveTo(PointOption.point(moveToXDirectionAt, yAxis)).release().perform()
        }

    }


    /**
     *function to drag and drop an element
     * @param androidDriver
     * @param elementfrom
     * @param elementto
     */

    fun elementdragdrop(androiddriver: AndroidDriver<MobileElement>, elementfrom:MobileElement, elementto:MobileElement)
    {
        PlatformTouchAction(androiddriver).longPress(ElementOption.element(elementfrom)).
                moveTo(ElementOption.element(elementto)).release().perform()
    }

//    public void elementdragdrop(AndroidDriver<MobileElement> androidDriver, MobileElement elementfrom, MobileElement elementto) {
//        TouchAction action = new TouchAction((MobileDriver) androidDriver);
//        action.longPress(ElementOption.element(elementfrom)).
//                moveTo(ElementOption.element(elementto)).release().perform();


    /**
     * function to read the text from a recyclerview
     * @param androidDriver
     */
    fun readRecyclerView(androidDriver: AndroidDriver<MobileElement>, text: String) {

        val elements = androidDriver.findElementByClassName("android.support.v7.widget.RecyclerView").findElements(By.id("bbc.mobile.news.uk.internal:id/text"))
        for (element in elements) {
            //System.out.println("Topics After  Re-Ordering :- "+element.getText());
            test?.log(Status.INFO, text + element.getText())
        }
    }



//    @Throws(InterruptedException::class, IOException::class)
//    fun comapreStatsData(csv: String, statsdata: Array<String>)
//    {
//        var br: BufferedReader? = null
//        var line= ""
//        val cvsSplitBy = ","
//        var country: Array<String>? = null
//        var staturl: Array<String>? = null
//
//        try {
//
//            br = BufferedReader(FileReader(csv))
//            line = br.readLine()
//            while (line  != null)
//            {
//                // use comma as separator
//                country = line.split(cvsSplitBy.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
//                staturl = country[0].split("&".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
//                println("Stat's URL " + country[0])
//                for (i in staturl.indices) {
//
//                    //System.out.println("The New Generated Stats " + staturl[i]);
//                    for (j in statsdata.indices) {
//                        if (staturl[i].equals(statsdata[j], ignoreCase = true)) {
//
//                            assertEquals(staturl[i], statsdata[j], "Stat's Matched")
//                            val matchedstats = staturl[i]
//                            println("The New Generated Stats $matchedstats")//list.add(staturl[i].toString()));
//                        }
//                    }
//
//                }
//
//            }
//
//        } catch (e: FileNotFoundException) {
//            e.printStackTrace()
//
//        } finally {
//            if (br != null) {
//                try {
//                    br.close()
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                }
//
//            }
//
//
//        }
//
//    }


    /**
     * function to check whether WiFi Connection enabled or not.
     * If not, enables the WiFi Connection
     * @param androidDriver
     */

    fun checkConnection(androidDriver: AndroidDriver<MobileElement>) {
        try {
            val state = androidDriver.setConnection(ConnectionStateBuilder()
                    .withWiFiEnabled()
                    .build())
            System.out.println("The WiFi Status is " + state.isWiFiEnabled())
            if (!state.isWiFiEnabled()) {
                androidDriver.setConnection(state)
            }
        } catch (e: NullPointerException) {

        }

    }

    /**
     *
     */
    @Throws(Exception::class)
    fun textpresent(appiumDriver: AppiumDriver<MobileElement>, text: String, text1: String)
    {
        val textpresent = appiumDriver.findElement(By.xpath("//android.widget.TextView[@text='$text $text1 My News']"))
        test?.log(Status.PASS, "Element Present" + textpresent.text)

    }

    /**
     * Function to seek the video, you need pass the percentage of seeking
     * @param driver
     * @param element
     * @param d
     * @throws InterruptedException
     */


    @Throws(InterruptedException::class)
    fun videoplaybackseeking(driver: AppiumDriver<MobileElement>, element: MobileElement, d: Double) {
        val startX = element.getLocation().getX()
        System.out.println("Startx :$startX")

        val endX = element.getSize().getWidth()
        System.out.println("Endx  :$endX")

        val yAxis = element.getLocation().getY()
        System.out.println("Yaxis  :$yAxis")

        val moveToXDirectionAt = (endX * d).toInt()
        System.out.println("Moving seek bar at $moveToXDirectionAt In X direction.")
        Thread.sleep(3000)

        PlatformTouchAction(driver).press(PointOption.point(startX, yAxis))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                .moveTo(PointOption.point(moveToXDirectionAt, yAxis)).release().perform()
    }


    /**
     * Function to seek forward on the video/audio playing
     * @param, driverType, Element type
     * double the seeking position ex(.30) means 30% seek
     */

    @Throws(InterruptedException::class)
    fun seeking(driver: AppiumDriver<MobileElement>, element: MobileElement, d: Double, seekingtype: String) {
        val startX = element.getLocation().getX()
        val endX = element.getSize().getWidth()
        val yAxis = element.getLocation().getY()
        val moveToXDirectionAt = (endX * d).toInt()

        if (seekingtype.equals("forward"))
        {

            PlatformTouchAction(driver).press(PointOption.point(startX, yAxis))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                    .moveTo(PointOption.point(moveToXDirectionAt, yAxis)).release().perform()

        } else if (seekingtype.equals("backward"))
        {

            PlatformTouchAction(driver).press(PointOption.point(endX, yAxis))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                    .moveTo(PointOption.point(moveToXDirectionAt, yAxis)).release().perform()
        }

    }



    /**
     * Function to check, whether given element is selected or not
     * @param, drivertype, element name
     */
    fun elementIsSelected( element: MobileElement): Boolean {
        if (element.isSelected()) {
            test?.log(Status.INFO, "Eelement selected")
            return true

        } else {
            test?.log(Status.INFO, "Eelement not selected")
            return false

        }
    }


    /**
     *
     * @param appiumDriver
     * @param elementID
     */
    fun getElements(appiumDriver: AppiumDriver<MobileElement>, elementID: String) {
        var elements: List<MobileElement>
        elements = appiumDriver.findElements(By.id(elementID))
        System.out.println("The elements counts are" + elements.size)
        for (i in 0 until elements.size) {
            System.out.println("The Text are " + elements[i].getText())
        }
    }

//    /**
//     *
//     * @param locator
//     * @return
//     */
//
//    fun elementFoundAndClicked(locator: By): ExpectedCondition<Boolean> {
//        return object : ExpectedCondition<Boolean> {
//            @Override
//            fun apply(driver: AppiumDriver): Boolean {
//                val el = driver.findElement(locator)
//                el.click()
//                return true
//            }
//        }
//    }


}





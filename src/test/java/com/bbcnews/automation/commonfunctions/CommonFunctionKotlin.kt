package com.bbcnews.automation.commonfunctions

import com.aventstack.extentreports.ExtentReporter
import com.aventstack.extentreports.ExtentReports
import com.aventstack.extentreports.ExtentTest
import com.aventstack.extentreports.Status
import com.aventstack.extentreports.markuputils.ExtentColor
import com.aventstack.extentreports.markuputils.MarkupHelper
import com.aventstack.extentreports.reporter.ExtentHtmlReporter
import com.aventstack.extentreports.reporter.configuration.ChartLocation
import com.aventstack.extentreports.reporter.configuration.Theme
import com.bbcnews.automation.commonfunctions.CommonFunctions
import com.bbcnews.automation.testutils.DeviceDetails
import com.bbcnews.automation.testutils.PlatformTouchAction
import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import io.appium.java_client.PerformsTouchActions
import io.appium.java_client.TouchAction
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.android.nativekey.AndroidKey
import io.appium.java_client.android.nativekey.KeyEvent
import io.appium.java_client.touch.WaitOptions
import io.appium.java_client.touch.offset.PointOption
import org.apache.commons.io.FileUtils
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.WebElement

import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.testng.*


import java.io.File

import java.io.IOException
import java.text.SimpleDateFormat
import java.time.Duration
import java.util.*

 open class CommonFunctionKotlin {

    // internal var extent = ExtentReports()
    public lateinit var extent: ExtentReports
    public lateinit var htmlReporter: ExtentHtmlReporter
    //abstract var htmlReporter: ExtentHtmlReporter
    public lateinit var test: ExtentTest
     public  var result: ITestResult? = null


     var appiumDriver: AppiumDriver<MobileElement>? = null

    var filename = "BBCNewsApp"
    var config_file = "extent-config.xml"
    var workingDirectory = System.getProperty("user.dir")
    private val curDate = Date()
    private val format = SimpleDateFormat("yyyy-MM-dd")
    private val DateToStr = format.format(curDate)
    var absoluteFilePath = "$workingDirectory/Results"
    var screenshotpathfolder = "$workingDirectory/Screenshots"

    public lateinit var testCases: List<Map<String, Any>>


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

    fun startTest(testName: String, testDecsription: String, category: String) = try {
        test = extent.createTest(testName, testDecsription)
        test.assignCategory(category)
    } catch (e: Exception) {

    }

    fun publishReport() {
        extent.flush()


    }

     /**
      * Function to empty the  result and screenshot folder
      * @param folder name
      */
     fun emptyFolder(filepath: String) {
         val file = File(filepath)
         val myFiles: Array<String>?
         if (file.isDirectory) {
             myFiles = file.list()
             for (i in myFiles!!.indices) {
                 val myFile = File(file, myFiles[i])
                 myFile.delete()
             }
         }
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
            if (takescreenshot == true) {
                val screenshotpath = getScreenshot(appiumDriver, element.text)
                println("Taken Screenshotpath is $screenshotpath")
                test.log(Status.INFO, "Screenshot Attached:-" + test.addScreenCaptureFromPath(screenshotpath))

            } else {

            }
        } catch (e: Exception) {
        }

    }

    /**
     * Function to wait until the screen is fully loaded
     * @param, drivertype , element and seconds to wait for page to load
     */

    fun waitForScreenToLoad(driver: AppiumDriver<MobileElement>, element: MobileElement, seconds: Int) {

        val wait = WebDriverWait(driver, seconds.toLong())
        wait.until<WebElement>(ExpectedConditions.visibilityOf(element))

    }

    /**
     * @param, drivertype, screenshot path, screenshot name
     * attaches the screenshot to the test report
     */
    @Throws(IOException::class)
    fun getScreenshot(appiumdriver: AppiumDriver<MobileElement>, screenshotName: String): String {
        val SubDirectory = "Screenshots"
        val ScreenshotPaths: String

        try {
            val dateName = SimpleDateFormat("dd-M-yyyy hh:mm").format(Date())
            val ts = appiumdriver as TakesScreenshot
            val source = ts.getScreenshotAs(OutputType.FILE)

            ScreenshotPaths = extentResultFolder(SubDirectory).toString()
            val file = File(ScreenshotPaths)
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
     fun extentResultFolder(path: String): String? {
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
                element.click()
                break
            } catch (e: Exception) {
                verticalSwipe(appiumDriver)


            }

        }
    }

    /**
     * Function to seek vertical on the app.
     * Startx remains constant
     * StartY and EndY are the two main parameters to swipe vertically
     * @param, driverType
     */

    fun verticalSwipe(driver: AppiumDriver<MobileElement>) {
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

        PlatformTouchAction(driver as AppiumDriver<MobileElement>).
                press(PointOption.point(startX, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                .moveTo(PointOption.point(startX, endY)).release().perform()
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
                if (element.text.length <= 0) {
                    test.log(Status.PASS, element.getAttribute("contentDescription") + "  Displayed")
                } else {
                    test.log(Status.PASS, element.text + "  Displayed")
                }

            } else {
                test.log(Status.FAIL, element.text + "  isn't Displayed")
            }

        } catch (e: AssertionError) {
            e.printStackTrace()
            org.testng.Assert.fail()
        }

    }

     fun getTestResult(appiumDriver: AppiumDriver<MobileElement>,result: ITestResult)
     {
         try {
             if (result.status == ITestResult.FAILURE) {

                 test.fail(MarkupHelper.createLabel(result.name + " Test Case is FAILED", ExtentColor.RED))
                 test.fail(result.throwable)
                 try {
                     val screenshotPath = getScreenshot(appiumDriver, result.name)
                     test.log(Status.FAIL, "Failed" + test.addScreenCaptureFromPath(screenshotPath))
                 } catch (e: Exception) {
                     e.printStackTrace()
                 }

             } else if (result.status == ITestResult.SUCCESS) {
                 test.pass(MarkupHelper.createLabel(result.name + " Test Case is PASSED", ExtentColor.GREEN))

             } else if (result.status == ITestResult.SKIP) {
                 test.skip(MarkupHelper.createLabel(result.name + " Test Case is SKIPPED", ExtentColor.YELLOW))
                 test.skip(result!!.throwable)

             }
         } catch (e: NullPointerException) {

         }

     }

     @Throws(IOException::class)
     fun getTestResults(appiumDriver: AppiumDriver<MobileElement>, result: ITestResult) {
         try {
             if (result.status == ITestResult.FAILURE) {

                 test.fail(MarkupHelper.createLabel(result.name + " Test Case is FAILED", ExtentColor.RED))
                 test.fail(result.throwable)
                 try {
                     val screenshotPath = getScreenshot(appiumDriver, result.name)
                     test.log(Status.FAIL, "Failed" + test.addScreenCaptureFromPath(screenshotPath))
                 } catch (e: Exception) {
                     e.printStackTrace()
                 }

             } else if (result.status == ITestResult.SUCCESS) {
                 test.pass(MarkupHelper.createLabel(result.name + " Test Case is PASSED", ExtentColor.GREEN))

             } else if (result.status == ITestResult.SKIP) {
                 test.skip(MarkupHelper.createLabel(result.name + " Test Case is SKIPPED", ExtentColor.YELLOW))
                 test.skip(result.throwable)

             }
         } catch (e: NullPointerException) {

         }

     }


     @Throws(Exception::class)
     fun createrReportHive(reportname: String, deviceOS: String, deviceName: String, deviceId: String) {

         val curDate = Date()
         println(curDate.toString())

         val dateName = SimpleDateFormat("hh:mm").format(Date())

         val reportfolder = extentResultFolder(absoluteFilePath)
         println("reportfolder is $reportfolder")

         val SubDirectory = "Results"
         val ResultsPaths: String
         ResultsPaths = extentResultFolder(SubDirectory).toString()
         val file = File(ResultsPaths)
         println("the Result path Folder is :- " + file.absolutePath)


         htmlReporter = ExtentHtmlReporter(file.absolutePath + File.separator + reportname + ".html")//"_"+deviceName+"_"+deviceOS+ ".html");
         extent = ExtentReports()
         extent.attachReporter(htmlReporter)
         htmlReporter.setAppendExisting(false)
         extent.setSystemInfo("Device ID", deviceId)
         extent.setSystemInfo("Firmware version", deviceOS)
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

     fun scrolltoEndofStories(appiumDriver: AppiumDriver<MobileElement>, element: MobileElement, path: String, element2: MobileElement) {
         val flag = false
         for (i in 0..20) {
             try {
                 waitForScreenToLoad(appiumDriver, element, 5)
                 Thread.sleep(800)
                 val element_title = element.text
                 println(element_title)
                 test.log(Status.INFO,element_title)
                 //                logger.log(LogStatus.INFO, "Snapshot below: "
                 //                        + logger.addScreenCapture(capture_ScreenShots(appiumDriver, path, element_title)));
                 //logger.log(LogStatus.INFO, "Snapshot below: "
                 //      + logger.addScreenCapture(capture_ScreenShots(appiumDriver,element_title)));
                 Thread.sleep(800)
                 element2.isDisplayed
                 test.log(Status.INFO,element2.text)
                 //element.click();
                 break
             } catch (e: Exception) {

                 CommonFunctions.horizontalSwipe(appiumDriver)

             }

         }
     }


     /**
      * Function to check, whether given element is selected or not
      * @param, drivertype, element name
      */
     fun IselementSelected(appiumDriver: AppiumDriver<MobileElement>, element: MobileElement): Boolean {
         if (element.isSelected) {
             test.log(Status.INFO, "Element selected")
             return true

         } else {
             test.log(Status.INFO, "Element not selected")
             return false

         }
     }

     /**
      * Function to enter the text into a textfeld
      * @param, driverType, element and string that's need to be entered
      */

     fun enterText(appiumDriver: AppiumDriver<MobileElement>, element: MobileElement, searchkey: String) {
         try {
             element.sendKeys(searchkey)
         } catch (e: Exception) {
         }

     }

     /**
      * function to get the element text
      * @param, drivertype and element
      */

     fun getText(appiumDriver: AppiumDriver<MobileElement>, element: MobileElement): String {
         return element.text
     }

}
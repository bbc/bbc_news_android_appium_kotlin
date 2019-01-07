package com.bbcnews.automation.testutils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.bbcnews.automation.commonfunctions.CommonFunctions;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.*;


import java.io.File;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

import static com.bbcnews.automation.commonfunctions.CommonFunctions.extentResultFolder;


public class ExtenttestReport {

    public ExtentHtmlReporter htmlReporter;
    ExtentReports extent;
    ExtentTest test;
    public AppiumDriver<MobileElement> appiumDriver;

    public String filename = "BBCNewsApp";
    public String config_file = "extent-config.xml";
    public String workingDirectory = System.getProperty("user.dir");
    private Date curDate = new Date();
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private String DateToStr = format.format(curDate);
    public String absoluteFilePath = workingDirectory + "/Results";
    public String screenshotpathfolder = workingDirectory + "/Screenshots";

    List<Map<String, Object>> testCases = new ArrayList<Map<String, Object>>();

    public void startReport(String reportname) throws Exception {
        String device_name = DeviceDetails.populateDevices_Names();
        String device_OS_Name = DeviceDetails.populateDevices_OS();
        String device_ID_Name = DeviceDetails.populateDevices_IDs();
        Date curDate = new Date();
        System.out.println(curDate.toString());

        String dateName = new SimpleDateFormat("hh:mm").format(new Date());

        System.out.println("absoluteFilePath is " + absoluteFilePath);
        Thread.sleep(4000);

        String reportfolder = extentResultFolder(absoluteFilePath);
        System.out.println("reportfolder is " + reportfolder);
        Thread.sleep(4000);

        //htmlReporter = new ExtentHtmlReporter(reportfolder+File.separator+reportname+device_name+dateName+".html");
        htmlReporter = new ExtentHtmlReporter(reportfolder + reportname + device_name + ".html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        htmlReporter.setAppendExisting(true);


        extent.setSystemInfo("Device ID", device_ID_Name);
        extent.setSystemInfo("Firmware version", device_OS_Name);
        extent.setSystemInfo("Device Name ", device_name);
        extent.setSystemInfo("Run Started on", curDate.toString());


        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setDocumentTitle("BBC News Android Report ");
        htmlReporter.config().setReportName("Test Report");
        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");


    }


    public void createrReportHive(String reportname, String deviceOS, String deviceName, String deviceId) throws Exception {

        Date curDate = new Date();
        System.out.println(curDate.toString());

        String dateName = new SimpleDateFormat("hh:mm").format(new Date());

        String reportfolder = extentResultFolder(absoluteFilePath);
        System.out.println("reportfolder is " + reportfolder);

        String SubDirectory = "Results";
        String ResultsPaths;
        ResultsPaths = extentResultFolder(SubDirectory);
        File file = new File(ResultsPaths);
        System.out.println("the Result path Folder is :- " + file.getAbsolutePath());


        htmlReporter = new ExtentHtmlReporter(file.getAbsolutePath() + File.separator + reportname+".html");//"_"+deviceName+"_"+deviceOS+ ".html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        htmlReporter.setAppendExisting(false);
        extent.setSystemInfo("Device ID", deviceId);
        extent.setSystemInfo("Firmware version", deviceOS);
        extent.setSystemInfo("Device Name ", deviceName);
        extent.setSystemInfo("Run Started on", curDate.toString());


        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setDocumentTitle("BBC News Android Report ");
        htmlReporter.config().setReportName("Automation Test Report");
        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");


    }

    public void startTest(String testName, String testDecsription, String category) {
        try {
            test = extent.createTest(testName, testDecsription);
            test.assignCategory(category);
        } catch (Exception e) {

        }


    }

    public void publishReport() {
        extent.flush();


    }


    public void getTestResult(AppiumDriver<MobileElement> appiumDriver,ITestResult result) throws IOException{
        try {
            if (result.getStatus() == ITestResult.FAILURE) {

                test.fail(MarkupHelper.createLabel(result.getName() + " Test Case is FAILED", ExtentColor.RED));
                test.fail(result.getThrowable());
                try {
                    String screenshotPath = getScreenshot(appiumDriver, result.getName());
                    test.log(Status.FAIL,"Failed"+test.addScreenCaptureFromPath(screenshotPath));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (result.getStatus() == ITestResult.SUCCESS) {
                test.pass(MarkupHelper.createLabel(result.getName() + " Test Case is PASSED", ExtentColor.GREEN));

            } else if (result.getStatus() == ITestResult.SKIP) {
                test.skip(MarkupHelper.createLabel(result.getName() + " Test Case is SKIPPED", ExtentColor.YELLOW));
                test.skip(result.getThrowable());

            }
        } catch (NullPointerException e) {

        }

    }

    /**
     * Appium Android default method
     * even though the AndroidKeycode is deprected in latest appium and java client
     *
     * @param, androidDriver, only works with Android
     */

    public void navigateBack(AndroidDriver<MobileElement> androiddriver) {
        //androiddriver.pressKeyCode(AndroidKeyCode.BACK);
        androiddriver.pressKey(new KeyEvent(AndroidKey.BACK));

    }

    public void clickButton(AppiumDriver<MobileElement> appiumDriver, MobileElement element, boolean takescreenshot) throws Exception {
        try {
            waitForScreenToLoad(appiumDriver, element, 3);
            element.click();
            if (takescreenshot = true) {
                String screenshotpath = getScreenshot(appiumDriver, element.getText());
                System.out.println("Taken Screenshotpath is " + screenshotpath);
                test.log(Status.INFO, "Screenshot Attached:-" +
                        test.addScreenCaptureFromPath(screenshotpath));
                // + MediaEntityBuilder.createScreenCaptureFromPath(screenshotpath).build());
//                test.log(Status.INFO, "Snapshot below: "
//                        + test.addScreencastFromPath(capture_ScreenShots(appiumDriver, path, element.getText())));

            } else {

            }
        } catch (NullPointerException e) {

        }

    }


    /**
            wait method
     */
    public  void sleepmethod(long seconds)
    {
        try
        {
        Thread.sleep(seconds);}catch (Exception e){}
    }




    /**
     * Function on click on any button or link on the app
     *
     * @param, driverType, element Type
     * boolean to take screenshot ( true = takes screenshot and attached to testReport, fail= wont take screenshot)
     * Screenshot path
     */
    public void tapButton(AppiumDriver<MobileElement> appiumDriver, MobileElement element, boolean takescreenshot) {
        try {
            waitForScreenToLoad(appiumDriver, element, 3);
            element.click();
            Thread.sleep(3000);
            if (takescreenshot == true) {
                String screenshotpath = getScreenshot(appiumDriver, element.getText());
                System.out.println("Taken Screenshotpath is " + screenshotpath);
                test.log(Status.INFO, "Screenshot Attached:-" +
                        test.addScreenCaptureFromPath(screenshotpath));

            } else {

            }
        } catch (Exception e) {
        }

    }

    /**
     * Function to check whether an element is displayed , return true if present else fail
     * If true, then the element text will be attached the report name. If element text not present, it uses the
     * element attribute
     *
     * @param, drivertype, element name
     */

    public void elementDisplayed(AppiumDriver<MobileElement> appiumDriver, MobileElement element) throws Exception {

        try {
            waitForScreenToLoad(appiumDriver, element, 3);
            Assert.assertTrue(element.isDisplayed());
            if (element.isDisplayed()) {
                if (element.getText().length() <= 0) {
                    test.log(Status.PASS, element.getAttribute("contentDescription") + "  Displayed");
                } else {
                    test.log(Status.PASS, element.getText() + "  Displayed");
                }

            } else {
                test.log(Status.FAIL, element.getText() + "  isn't Displayed");
            }

        } catch (AssertionError e) {
            e.printStackTrace();
            org.testng.Assert.fail();
        }
    }


    public void linksDisplayed(AppiumDriver<MobileElement> driver, MobileElement element) throws Exception {

        try {
            // waitForelementToLoad(driver,element,1);
            if (element.isDisplayed()) {
                if (element.getText().length() <= 0) {
                    test.log(Status.PASS, element.getAttribute("contentDescription") + "  Displayed");
                } else {
                    test.log(Status.PASS, element.getText() + "  Displayed");
                }

            } else {
                test.log(Status.FAIL, element.getText() + "  isn't Displayed");
            }

        } catch (Exception e) {

        }
    }

    /**
     Function to wait until the screen is fully loaded
     @param, drivertype , element and seconds to wait for page to load
     */

    public static void waitForScreenToLoad(AppiumDriver<MobileElement> driver, MobileElement element, int seconds) {

        WebDriverWait wait = new WebDriverWait(driver, seconds);
        wait.until(ExpectedConditions.visibilityOf(element));

    }


    /**
     * @param, drivertype, screenshot path, screenshot name
     * attaches the screenshot to the test report
     */

    public static String capture_ScreenShots(AppiumDriver<MobileElement> driver, String screenshot_path, String screenshot_name) {

        try {
            String dateName = new SimpleDateFormat("dd-M-yyyy hh:mm").format(new Date());
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            String dest = screenshot_path + File.separator + screenshot_name + "_" + dateName + ".png";
            System.out.println("Screenshot path name:------" + dest);
            File destination = new File(dest);
            FileUtils.copyFile(source, destination);
            System.out.println("ScreenShot Taken");
            return dest;
        } catch (Exception e) {
            System.out.println("Exception While Taking screenshot" + e.getMessage());
            return e.getMessage();
        }

    }


    /**
     * @param, drivertype, screenshot path, screenshot name
     * attaches the screenshot to the test report
     */
    public String getScreenshot(AppiumDriver<MobileElement> appiumdriver, String screenshotName) throws IOException {
        String SubDirectory = "Screenshots";
        String ScreenshotPaths;

        try {
            String dateName = new SimpleDateFormat("dd-M-yyyy hh:mm").format(new Date());
            TakesScreenshot ts = (TakesScreenshot) appiumdriver;
            File source = ts.getScreenshotAs(OutputType.FILE);

            ScreenshotPaths = extentResultFolder(SubDirectory);
            File file = new File(ScreenshotPaths);
            System.out.println("the ScreenShot  Folder is :- " + file.getAbsolutePath());

            String dest = file.getAbsolutePath() + File.separator + screenshotName + "_" + dateName + ".png";
            System.out.println("Screenshot path name:------" + dest);
            File destination = new File(dest);
            FileUtils.copyFile(source, destination);
            System.out.println("ScreenShot Taken");
            return dest;
        } catch (Exception e) {
            System.out.println("Exception While Taking screenshot" + e.getMessage());
            return e.getMessage();
        }

    }


    /**
     Function to create a folder with the project path
     @param, Directory path
     */
    public static String extentResultFolder(String path)
    {
        String strManyDirectories=null;
        try{
            //  String strDirectoy = path;
            strManyDirectories= path;

            // Create one directory
            boolean
                    // Create multiple directories
                    success = (new File(strManyDirectories)).mkdirs();
            if (success) {
                System.out.println("Directories: "
                        + strManyDirectories + " created");
            }

        }catch (Exception e){//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
        return strManyDirectories;
    }

    /**
     * Function to scroll to an element, if the list if very big
     *
     * @param, driverType, element to be scrolled, screenshot
     */
    public static void scrolltoElement(AppiumDriver<MobileElement> appiumDriver, MobileElement element) {
        boolean flag = false;
        String element_title = null;
        for (int i = 0; i <= 20; i++) {
            try {
                element_title = element.getText();
                element.isDisplayed();
//                logger.log(LogStatus.INFO, "Snapshot below: "
//                        + logger.addScreenCapture(capture_ScreenShots(appiumDriver,Screenshotname)));
                element.click();
                break;
            } catch (Exception e) {
                CommonFunctions.verticalSwipe(appiumDriver);


            }

        }
    }



    /**
     Function to seek vertical on the app.
     Startx remains constant
     StartY and EndY are the two main parameters to swipe vertically
     @param, driverType

     */

    public static void verticalSwipe(AppiumDriver<MobileElement> driver)
    {
        org.openqa.selenium.Dimension dimension = driver.manage().window().getSize();
        int height = dimension.getHeight();
        int width = dimension.getWidth();
        int startX = width/2;
        int startY = (int)(height*0.75);
        int endY = (int)(height*0.35);

        TouchAction action = new TouchAction(driver);
        action.press(PointOption.point(startX, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                .moveTo(PointOption.point(startX, endY)).release().perform();
    }

}

//  MediaEntityModelProvider screenshot  = MediaEntityBuilder.createScreenCaptureFromPath(capture_ScreenShots(appiumDriver, path, element.getText())).build();
// test.log(Status.INFO,"HomePage",screenshot);
//  test.log(Status.INFO,"Screenshot",
//        MediaEntityBuilder.createScreenCaptureFromPath(capture_ScreenShots(appiumDriver, path, element.getText())).build());
package com.bbcnews.automation.commonfunctions;



import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

import javax.imageio.ImageIO;


import com.bbcnews.automation.testutils.DeviceDetails;
import com.tesults.tesults.Results;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.testng.ITestContext;
import org.testng.ITestResult;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;



/**
 * Copyright 2018 BBC Inc.
 * All rights reserved.
 */

/**
 * @author  harish ramakrishna
 * @version 12/10/18
 */

public class CommonFunctions {

    WebDriverWait wait;
    public static ExtentReports extent;
    public static ExtentTest logger;
    public  final long TIME_OUT_IN_SECONDS = 30L;

    public static String device_OS_Name;
    public static String device_ID_Name;
    public static String device_name;



    public static String screenshot_path="BBCNewsScreenshots";
    public static String screenshot_name= "harish";
    public static String path = screenshot_path+File.separator+screenshot_name;
    public static File file;
    public static AppiumDriver<WebElement> driver;

    public  static String filename = "BBCNewsApp";
    public  static String config_file = "extent-config.xml";
    public  static String workingDirectory =  System.getProperty("user.dir");
    private static Date curDate = new Date();
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private static String DateToStr = format.format(curDate);
    private  static String absoluteFilePath = workingDirectory +"/Results/";//+File.separator+DateToStr;
    public  static String screenshotpath = workingDirectory +"/Screenshots/";
    public  static String config_file_dir = workingDirectory + File.separator + config_file;

    public static String SubDirectory =  "Screenshot";
    public static String ScreenshotPaths;

    static  List<Map<String,Object>>  testCases = new ArrayList<Map<String, Object>>();


    /**
         @parm reportName
         Creates a test report after test exceution done
     */
    public static void createReport(String reportName) throws Exception {

        device_name = DeviceDetails.populateDevices_Names();
        device_OS_Name = DeviceDetails.populateDevices_OS();
        device_ID_Name = DeviceDetails.populateDevices_IDs();

//        Date curDate = new Date();
//        System.out.println(curDate.toString());
//
//        String dateName = new  SimpleDateFormat("hh:mm").format(new Date());

        String SubDirectory =  "Results";
        String ResultsPaths;
        ResultsPaths = CommonFunctions.extentResultFolder(SubDirectory);
        File file = new File(ResultsPaths);
        System.out.println("the Result path Folder is :- "+ file.getAbsolutePath());

        try {
           // extent = new ExtentReports(absoluteFilePath+File.separator+reportName+device_name+dateName+".html",true, DisplayOrder.OLDEST_FIRST);
            extent = new ExtentReports(file.getAbsolutePath()+File.separator+reportName+device_name+".html",true, DisplayOrder.OLDEST_FIRST);


            HashMap<String, String> sysInfo = new HashMap<String, String>();
            sysInfo.put("Device ID", device_ID_Name);
            sysInfo.put("Firmware version", device_OS_Name);
            sysInfo.put("Device Name ", device_name);
            sysInfo.put("Run Started on", curDate.toString());
            extent.addSystemInfo(sysInfo);
            extent.loadConfig(new File(config_file_dir));
        } catch (NoClassDefFoundError e) {
        }catch (NullPointerException e)
        {

        }

    }

    /**
      @param, drivertype, screenshot path, screenshot name
      attaches the screenshot to the test report
     */

    public static String capture_ScreenShots(AppiumDriver<MobileElement> driver, String screenshot_name) {

        try {

             String SubDirectory =  "Screenshots";
             String ScreenshotPaths;

           // String dateName = new  SimpleDateFormat("dd-M-yyyy hh:mm:ss").format(new Date());
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            ScreenshotPaths = extentResultFolder(SubDirectory);
            file = new File(ScreenshotPaths);
            System.out.println("the ScreenShot  Folder is :- "+ file.getAbsolutePath());
            //   String dest = screenshot_path + File.separator + screenshot_name+"_"+dateName+".png";
            String dest = file.getAbsolutePath()+File.separator+screenshot_name+".png";
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
             starts the test, this test will be attached to the Extent TestReport
             @param, driverype,Testname, testype (categories the testype in report)
             path of the screenshot, screenshot name
         */

    public static void startTest(AppiumDriver<MobileElement> driver,String testName, String testType,boolean takescreenshot,String screenshotName)
    {
        try
        {
        logger = extent.startTest(testName);
       // logger.addScreenCapture(path);
        logger.assignCategory(testType);
        logger.getRunStatus();
        logger.getTest();
            if(takescreenshot=true) {
                logger.log(LogStatus.INFO, "Snapshot below: "
                        + logger.addScreenCapture(capture_ScreenShots(driver,screenshotName)));
            }else {}
        }catch(NullPointerException e)
        {
            e.printStackTrace();
        }

    }

/**
    Function to compare the screenshots taken
    @param, drivertype, imagename which needs to be compared
 */

    public static void compare(AppiumDriver<MobileElement> driver,String imageName) throws IOException, InterruptedException
    {
        try
        {
            String Ashot_Screenpath = System.getProperty("user.dir")+File.separator+"Ashotscreenshots_Ref";
            String Ashot_Screenshot_Ref = Ashot_Screenpath+File.separator+imageName+".png";

            String Ashot_Screenshot_Actual = System.getProperty("user.dir")+File.separator+"BBCNewsAppscreenshots_After";
            String Ashot_Screenshot_Actuals = Ashot_Screenshot_Actual+File.separator+imageName+".png";

            System.out.println("The BBC News Image Reference is " + Ashot_Screenshot_Ref);
            BufferedImage expectedImage = ImageIO.read(new File(Ashot_Screenshot_Ref));

            BufferedImage actula_image = ImageIO.read(new File(Ashot_Screenshot_Actuals));
            System.out.println("TThe BBC News Actual Image Reference is " + actula_image);

            ImageDiffer imagediffer = new ImageDiffer();
            ImageDiff diff = imagediffer.makeDiff(expectedImage, actula_image);
            //	assertTrue(diff.hasDiff(),"Images are Same");
        }catch(Exception e)
        {

        }

		/*if(diff.hasDiff()==false)
		{
			logger.log(LogStatus.PASS, "Images are same");
		}
		else
		{
			logger.log(LogStatus.PASS, "Images aren't same");
		}*/
    }


    public static void takeScreenshot(AppiumDriver<MobileElement> driver,String screenshotname) {

        try {
            // now copy the screenshot to desired location using copyFile
            Path screenshotFolder;
            screenshotFolder = Paths.get(System.getProperty("user.dir"), "/Ashotscreenshots");

            if (Files.notExists(screenshotFolder))
                Files.createDirectory(screenshotFolder);

            String fileName = screenshotname + "_" + System.nanoTime();

            Path screenshotPath = Paths.get(screenshotFolder.toString(), fileName + ".png");

            Screenshot screenshot = new AShot()
                    .takeScreenshot(driver);

            ImageIO.write(screenshot.getImage(), "PNG", new File(screenshotPath.toString()));

            // return screenshotPath.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public static void Ashot_Screenshot(AndroidDriver<MobileElement> driver,String fullpagescrn) throws IOException
    {

        Screenshot myScreenshot = new AShot().takeScreenshot(driver);
        Path screenshotFolder;
        screenshotFolder = Paths.get(System.getProperty("user.dir"), "/BBCNewsAppscreenshots_After/");
        if (Files.notExists(screenshotFolder))
            Files.createDirectory(screenshotFolder);
        // To save the screenshot in desired location
        ImageIO.write(myScreenshot.getImage(), "PNG",
                new File(screenshotFolder+File.separator+fullpagescrn+".png"));
    }

    /**
          Function to check whether an element is displayed , return true if present else fail
          If true, then the element text will be attached the report name. If element text not present, it uses the
          element attribute
          @param, drivertype, element name
     */

    public static void linksDisplayed(AppiumDriver<MobileElement> driver, MobileElement element) throws Exception
    {

        try
        {
            waitForScreenToLoad(driver,element,2);
            Assert.assertTrue(element.isDisplayed());
            if(element.isDisplayed())
            {
                if(element.getText().length() <= 0)
                {
                    logger.log(LogStatus.PASS, element.getAttribute("contentDescription")+"  Displayed");
                }else
                {
                    logger.log(LogStatus.PASS, element.getText() + "  Displayed");
                }

            }
            else
            {
                logger.log(LogStatus.FAIL, element.getText() + "  isn't Displayed");
            }

        }catch (AssertionError e)
        {
            e.printStackTrace();
            org.testng.Assert.fail();
        }
    }

    /**
        Function to check, whether given element is selected or not
        @param, drivertype, element name
     */
    public static boolean elementIsSelected(AppiumDriver<MobileElement> appiumDriver, MobileElement element)
    {
        if(element.isSelected())
        {
            logger.log(LogStatus.INFO,"Eelement selected");
            return true;

        } else {
            logger.log(LogStatus.INFO,"Eelement not selected");
            return false;

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

    public static void articleType(AppiumDriver<MobileElement> appiumDriver, MobileElement element,String articleType)
    {
        if(articleType.equalsIgnoreCase("Media"))
        {
            element.isDisplayed();
        }else {

        }

    }

    /**
        Function to end the ExtentTest
        after each test execution, endtest method is called
     */

    public static void endTest()
    {
        try { extent.endTest(logger);
        }catch (Exception e)
        {

        }
    }

    /**
           Function to create a folder with the project path
           @param, Directory path
     */
    public static String ResultFolder(AppiumDriver<MobileElement> appiumDriver,String path)
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
        Function to generate the ExtentReport, after all the test execution done.
        This Method called to publish the report in an HTML format
     */

    public void GenerateReport() throws Exception{
        try
        {
            extent.endTest(logger);
            extent.flush();
        }catch(NullPointerException e)
        {
            e.printStackTrace();
        }
    }

    /**
            Function to check whether element present are not
            @param, drivertype and locaorykey to identify the element.
     */

    public boolean isElementPresent(AndroidDriver<WebElement> driver, By locatorKey) {
        try {
            driver.findElement(locatorKey);
            return true;
        } catch (NoSuchElementException e) {
            // e.printStackTrace();
            return false;
        }
        // return false;

    }




    public static String capture(AndroidDriver<WebElement> driver,String screenShotName) throws IOException
    {

      //  String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot ts = (TakesScreenshot)driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String dest = System.getProperty("user.dir") +"/ErrorScreenshots/"+screenShotName+".png";
        File destination = new File(dest);
        FileUtils.copyFile(source, destination);
        return dest;
    }

    /**
        Function which is used to scroll until a text is present
        This is a alternate method to TouchAction press/swipe
        This function only works for Android
        @param, driverType, text to be searched
        @return Webelement
     */

    public static WebElement scrollToAnElementByText(AppiumDriver<MobileElement> driver, String text)
    {

        return  driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector())" +
                ".scrollIntoView(new UiSelector().text(\"" + text + "\"))"));


    }

    /**
           Function to select the element after scrolling
           @param, drivertype and topic to be searched
     */

    public static void gettopicselected(AppiumDriver<MobileElement> driver,String topic)
    {

        WebElement topicsare = scrollToAnElementByText(driver, topic);

    }

    public static WebElement scroll(AndroidDriver<WebElement> driver)
    {

        return driver.findElement(MobileBy.AndroidUIAutomator(
                "new UiScrollable(new UiSelector().resourceId(\"com.android.vending:id/tab_recycler_view\")).getChildByText("
                        + "new UiSelector().className(\"android.widget.TextView\"), \"Most Watched\")"));

    }

    /**
        Function on click on any button or link on the app
        @param, driverType, element Type
        boolean to take screenshot ( true = takes screenshot and attached to testReport, fail= wont take screenshot)
       Screenshot path
     */
    public static void clickButton(AppiumDriver<MobileElement> appiumDriver, MobileElement element,boolean takescreenshot)
    {
        try
        {
            //waitForelementToLoad(appiumDriver,element,5);
            element.click();
            Thread.sleep(1000);
            if(takescreenshot=true) {
                logger.log(LogStatus.INFO, "Snapshot below: "
                        + logger.addScreenCapture(capture_ScreenShots(appiumDriver,element.getText())));

            }else {

            }
        }catch (Exception e)
        {
        }

    }

    /**
        Function similar to waitforPagetoLoad
        @param, driverType Element to be waited and seconds
     */

    public static void waitForelementToLoad(AppiumDriver<MobileElement> driver, MobileElement element, int seconds) {

        WebDriverWait wait = new WebDriverWait(driver, seconds);
       // wait.until(visibilityOf(element));

    }

    /**
          Function to Publishreport an HTNL report with testname, testype
          and sceenshots after the test execution done
     */

    public static void publishReport()
    {
        try {
            extent.flush();
            //extent.close();
        }catch (Exception e)
        { }

    }

    /**
        Function to seek forward on the video/audio playing
        @param, driverType, Element type
        double the seeking position ex(.30) means 30% seek
     */

    public static void seeking(AppiumDriver<MobileElement> driver, MobileElement element, double d) throws InterruptedException
    {
        int startX = element.getLocation().getX();
        System.out.println("Startx :" + startX);

        int endX = element.getSize().getWidth();
        System.out.println("Endx  :" + endX);

        int yAxis = element.getLocation().getY();
        System.out.println("Yaxis  :" + yAxis);

        int moveToXDirectionAt = (int) (endX * d);
        System.out.println("Moving seek bar at " + moveToXDirectionAt + " In X direction.");
        Thread.sleep(3000);

        TouchAction action = new TouchAction(driver);
        action.press(PointOption.point(startX, yAxis))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000)))
                .moveTo(PointOption.point(moveToXDirectionAt, yAxis)).release().perform();
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

    /**
            Function to seek horizontal on the app.
            Yaxis remains horizontal
            StartXaxis and endXaxis are the two main parameters to swipe vertically
            @param, driverType

         */
    public static void horizontalSwipe(AppiumDriver<MobileElement> driver)
    {
        org.openqa.selenium.Dimension dimension = driver.manage().window().getSize();
        int height = dimension.getHeight();
        int width = dimension.getWidth();
        int startXaxis = (int)(width*0.90);
        int Yaxis = (int)(height*0.20);
        int endXaxis = (int)(width*0.10);

        TouchAction action = new TouchAction(driver);
        action.press(PointOption.point(startXaxis, Yaxis))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                .moveTo(PointOption.point(endXaxis, Yaxis)).release().perform();
    }

    public static void checkElement(AppiumDriver<MobileElement> driver,String text, MobileElement element)
    {
        boolean present;
        try {
            driver.findElementsByAccessibilityId(text);
            present = true;
            element.click();
        } catch (NoSuchElementException e) {
            present = false;
        }
    }


    public static void isSelected(AppiumDriver<MobileElement> driver, MobileElement element) {
        if (element.isSelected()) {
            logger.log(LogStatus.PASS,"element selected");
        } else {
            logger.log(LogStatus.PASS,"element not selected");

        }
    }

    /**
            Appium Android default method
            even though the AndroidKeycode is deprected in latest appium and java client
            @param, androidDriver, only works with Android
     */

    public static void navigateBack(AndroidDriver<MobileElement> androiddriver)
    {
        //androiddriver.pressKeyCode(AndroidKeyCode.BACK);
        androiddriver.pressKey(new KeyEvent(AndroidKey.BACK));

    }

    /**
           Function to scroll to an element, if the list if very big
           @param, driverType, element to be scrolled, screenshot
     */
    public static void scrolltoElement(AppiumDriver<MobileElement> appiumDriver, MobileElement element, String Screenshotname)
    {
        boolean flag = false;
        String element_title=null;
        for(int i=0;i<=20;i++)
        {
            try
            {
                element_title = element.getText();
                element.isDisplayed();
                logger.log(LogStatus.INFO, "Snapshot below: "
                        + logger.addScreenCapture(capture_ScreenShots(appiumDriver,Screenshotname)));
               // element.click();
                break;
            }catch(Exception e)
            {
                CommonFunctions.verticalSwipe(appiumDriver);


            }

        }
//        flag = element2.isDisplayed();
//        if(flag)
//        {
//
//            logger.log(LogStatus.PASS,"Element Found");
//        }else
//        {
//            logger.log(LogStatus.FAIL, "Element not Found");
//        }
    }



    /**
      Function to empty the  folder
     */
    public static void emptyFolder(String filepath)
    {
        File file = new File(filepath);
        String[] myFiles;
        if (file.isDirectory()) {
            myFiles = file.list();
            for (int i = 0; i < myFiles.length; i++) {
                File myFile = new File(file, myFiles[i]);
                myFile.delete();
            }
        }
    }

    /**
        Function to scroll on TopStories - Videos and Stories carousel
        @param, driverType, element and path for screenshot to be taken

     */

    public static void scrolltoEndofStories(AppiumDriver<MobileElement> appiumDriver, MobileElement element,String path,MobileElement element2) {
        boolean flag = false;
        for (int i = 0; i <= 20; i++) {
            try {
                waitForScreenToLoad(appiumDriver,element,5);
                Thread.sleep(800);
                String element_title=element.getText();
                System.out.println(element_title);
//                logger.log(LogStatus.INFO, "Snapshot below: "
//                        + logger.addScreenCapture(capture_ScreenShots(appiumDriver, path, element_title)));
                //logger.log(LogStatus.INFO, "Snapshot below: "
                  //      + logger.addScreenCapture(capture_ScreenShots(appiumDriver,element_title)));
                Thread.sleep(800);
                element2.isDisplayed();

                //element.click();
                break;
            } catch (Exception e) {

                CommonFunctions.horizontalSwipe(appiumDriver);

            }

        }
    }

    /**
            Function to enter the text into a textfeld
            @param, driverType, element and string that's need to be entered
     */

    public static void enterText(AppiumDriver<MobileElement> appiumDriver, MobileElement element, String searchkey)
    {
        try
        {
            element.sendKeys(searchkey);
        }catch (Exception e){}
    }

    /**
        function to get the element text
        @param, drivertype and element
     */

    public static String getText(AppiumDriver<MobileElement> appiumDriver,MobileElement element)
    {
        return element.getText();
    }

   /* public static void AshotScreenshot(AndroidDriver<MobileElement> driver,String fullpagescrn) throws IOException
    {

        Screenshot myScreenshot = new AShot().takeScreenshot(driver);
        Path screenshotFolder;
        screenshotFolder = Paths.get(System.getProperty("user.dir"), "/BBCNewsAppscreenshots_After/");
        if (Files.notExists(screenshotFolder))
            Files.createDirectory(screenshotFolder);
        // To save the screenshot in desired location
        ImageIO.write(myScreenshot.getImage(), "PNG",
                new File(screenshotFolder+File.separator+fullpagescrn+".png"));
    }*/


    public static void getTestResult(AppiumDriver<MobileElement> appiumDriver,ITestResult result) {
        try {
            if (result.getStatus() == ITestResult.FAILURE) {
                logger.log(LogStatus.FAIL, result.getName() +"      Test Case is FAILED"+
                        logger.getTest()+
                        logger.getRunStatus());
                logger.log(LogStatus.FAIL, result.getThrowable());
                //, ExtentColor.RED));

            } else if (result.getStatus() == ITestResult.SUCCESS) {
                logger.log(LogStatus.PASS,result.getName() + "      Test Case is PASSED"+
                        logger.getTest()+
                        logger.getRunStatus());
                //, ExtentColor.GREEN));
            } else if(result.getStatus() == ITestResult.SKIP){
                logger.log(LogStatus.SKIP, result.getName()+"       Test Case is Skipped"+
                        logger.getTest() +
                        logger.getRunStatus());
                        //MarkupHelper.createLabel(result.getName() + " Test Case is SKIPPED", ExtentColor.YELLOW));
                logger.log(LogStatus.SKIP, result.getThrowable());
            }
        }catch (NullPointerException e)
        {

        }


    }

}













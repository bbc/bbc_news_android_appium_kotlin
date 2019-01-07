package com.bbcnews.automation.testutils;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.model.Log;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class createExtentReport {

    public  static String filename = "BBCNewsApp";
    public  static String config_file = "extent-config.xml";
    public  static String workingDirectory =  System.getProperty("user.dir");
    public  static String absoluteFilePath = workingDirectory +"/Results/" +filename;
    public  static String screenshotpath = workingDirectory +"/Screenshots/";
    public  static String config_file_dir = workingDirectory + File.separator + config_file;
    public  static File file;

    public static ExtentReports extent;
    public static ExtentTest logger;

    public static CommandPrompt cmd = new CommandPrompt();
    public static DeviceDetails devicedetails = new DeviceDetails();

    public static String device_OS_Name;
    public static String device_ID_Name;
    public static String device_name;
    public static AndroidDriver<WebElement> driver;


    public static void getAndroid_DeviceDetails() throws Exception {

        for (int i = 0; i < DeviceDetails.deviceOS.size(); i++) {
            try {
                device_OS_Name = DeviceDetails.deviceOS.get(i);
                device_ID_Name = DeviceDetails.deviceID.get(i);
                device_name = DeviceDetails.deviceName.get(i);

                System.out.println("The Device OS is " + device_OS_Name);
                System.out.println("The Device ID is " + device_ID_Name);
                System.out.println("The Device Name is " + device_name);

            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    public static void generatereport()
    {
        try {
            file = new File(absoluteFilePath);
            if (file.createNewFile()) {
                //  Log.d("FileName", "Filecreated");
                System.out.println("File is created!");
            } else {
                // Log.d("FileName", "File already created");
                System.out.println("File is already existed!");
            }
        }catch (IOException e)
        {

        }
    }


    public static String capture(AndroidDriver<WebElement> driver,String screenShotName) throws IOException
    {

        String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot ts = (TakesScreenshot)driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        //String destfolder = commonfunction.ResultFolder(screenshot_path);
        //String dest = destfolder+File.separator+screenShotName+".png";
        //System.getProperty("user.dir")+File.separatorChar+screenShotName+".png";
        String dest = screenshotpath+screenShotName+dateName+".png";
        File destination = new File(dest);
        FileUtils.copyFile(source, destination);
        return dest;
    }


    public static void createReport() throws Exception {

        device_name = DeviceDetails.populateDevices_Names();
        device_OS_Name = DeviceDetails.populateDevices_OS();
        device_ID_Name = DeviceDetails.populateDevices_IDs();

        Date curDate = new Date();
        System.out.println(curDate.toString());

        try {
            extent = new ExtentReports(absoluteFilePath +"_" +device_name +".html"  , true, DisplayOrder.NEWEST_FIRST);


            HashMap<String, String> sysInfo = new HashMap<String, String>();
            sysInfo.put("Device ID", device_ID_Name);
            sysInfo.put("Firmware version", device_OS_Name);
            sysInfo.put("Device Name ", device_name);
            sysInfo.put("Run Started on", curDate.toString());
            extent.addSystemInfo(sysInfo);
            extent.loadConfig(new File(config_file_dir));
            // extent.loadConfig(new File(System.getProperty("user.dir")+File.separator+"extent-config.xml"));
            //"/Users/ramakh02/Desktop/Appium/BBCNews/xmlextent-config.xml"));

        } catch (NoClassDefFoundError e) {
        }

    }



    public static void createReport_Parallel(String device_name ,String device_OS_Name, String device_ID_Name) throws Exception {


        try {
            extent = new ExtentReports(absoluteFilePath +"_" +device_name +".html"  , true, DisplayOrder.NEWEST_FIRST);


            HashMap<String, String> sysInfo = new HashMap<String, String>();
            sysInfo.put("Device ID", device_ID_Name);
            sysInfo.put("Firmware version", device_OS_Name);
            sysInfo.put("Device Name ", device_name);
            //sysInfo.put("Appium Port", appiumport);
            extent.addSystemInfo(sysInfo);
            extent.loadConfig(new File(config_file_dir));
            // extent.loadConfig(new File(System.getProperty("user.dir")+File.separator+"extent-config.xml"));
            //"/Users/ramakh02/Desktop/Appium/BBCNews/xmlextent-config.xml"));

        } catch (NoClassDefFoundError e) {
        }

    }


    public static String capture_ScreenShot(AppiumDriver<MobileElement> driver, String screenshot_path, String screenshot_name) {

        try {
            String dateName = new  SimpleDateFormat("dd-M-yyyy hh:mm:ss").format(new Date());
            //SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
            // String
            // report_path=absoluteFilePath;//"//Users//ramakh01//Downloads//RadioApp_Automation//RadioApp//Appium_Test//test-output//AVTestHarness.html";
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            String dest = screenshot_path + File.separator + screenshot_name+"_"+dateName+".png";
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

    public static void startTest(AppiumDriver<MobileElement> driver, String path,String testname) throws IOException, InterruptedException
    {
        try {
            //String ScreenshotPaths = capture(driver, testname);
            //System.out.print("The Screenshot name path is "+ScreenshotPaths);
            logger = extent.startTest(testname);
            logger.log(LogStatus.PASS, testname + "passed");
            Thread.sleep(2000);
            logger.log(LogStatus.INFO, "Snapshot below: "
                    + logger.addScreenCapture(capture_ScreenShot(driver, path, testname)));

//            logger.log(LogStatus.INFO,"Snapshot below: "+
//    				logger.addScreenCapture(capture(driver, testname)));
        }catch (Exception e)
        {

        }

    }

    public static void endTest()
    {
        try {
            extent.endTest(logger);
        }catch (Exception e)
        {

        }
    }

    public static void publishReport()
    {
        try {
            extent.flush();
            //extent.close();
        }catch (Exception e)
        {

        }
    }

//    public static void main(String args[]) throws Exception
//    {
//
//           // getAndroid_DeviceDetails();
//            createReport();
//            startTest("Hello");
//            endTest();
//            publishReport();
//
//
//    }
}

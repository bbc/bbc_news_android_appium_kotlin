package com.bbcnews.automation.testutils.ExtentReports;

import com.bbcnews.automation.commonfunctions.CommonFunctions;
import com.relevantcodes.extentreports.ExtentReports;
import com.bbcnews.automation.testutils.DeviceDetails;
import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ExtentManager {

    private static String filename = "BBCNewsAndroidApp";
    private static String config_file = "extent-config.xml";
    public static ExtentReports extentReports;
    public static ExtentTest extentTest;
    private static String workingDirectory= System.getProperty("user.dir");

    private static Date curDate = new Date();
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private static String DateToStr = format.format(curDate);
    private  static String absoluteFilePath = workingDirectory +"/Results/"+File.separator+DateToStr;
    private static String config_file_dir = workingDirectory + File.separator + config_file;



    private static String device_OS_Name;
    private static String device_ID_Name;
    private static String device_name;

    public static void createExtentreport() throws  Exception{
        try {
            device_name = DeviceDetails.populateDevices_Names();
            device_OS_Name = DeviceDetails.populateDevices_OS();
            device_ID_Name = DeviceDetails.populateDevices_IDs();

            //Set HTML reporting file location
            String workingDir = System.getProperty("user.dir");
           // extent = new ExtentReports(workingDir + "/ExtentReports/ExtentReportResults.html" , true)
            extentReports = new ExtentReports(absoluteFilePath+File.separator+device_name +".html"  , true, DisplayOrder.NEWEST_FIRST);
            HashMap<String, String> sysInfo = new HashMap<String, String>();
            sysInfo.put("Device ID", device_ID_Name);
            sysInfo.put("Firmware version", device_OS_Name);
            sysInfo.put("Device Name ", device_name);
            Date curDate = new Date();
            sysInfo.put("Run Started on", curDate.toString());
            extentReports.addSystemInfo(sysInfo);
            extentReports.loadConfig(new File(config_file_dir));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void startExtentTest(AppiumDriver<MobileElement> driver,String testName, String testType,String path,String screenshotname)
    {
        try {
            extentReports.startTest(testName);
            extentTest.assignCategory(testType);
            extentTest.getRunStatus();
            extentTest.log(LogStatus.PASS, testName + "passed");
            extentTest.log(LogStatus.INFO,"ScreenShot Below"+extentTest.addScreenCapture(CommonFunctions.capture_ScreenShots(driver,screenshotname)));
        }catch (NullPointerException e)
        {

        }
    }

    public static void stopExtentTest()
    {
        try {
            extentReports.endTest(extentTest);
        }catch (NullPointerException e)
        {

        }
    }

    public static void publishExtentReport()
    {
        try {
            extentReports.flush();
        }catch (NullPointerException e)
        {

        }

    }
}

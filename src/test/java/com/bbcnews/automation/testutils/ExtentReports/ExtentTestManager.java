//package com.bbcnews.automation.testutils.ExtentReports;
//
//import com.bbcnews.automation.testutils.DeviceDetails;
//import com.relevantcodes.extentreports.ExtentReports;
//import com.relevantcodes.extentreports.ExtentTest;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class ExtentTestManager {
//
//    static Map extentTestMap = new HashMap();
//    public static ExtentTest logger;
//    public static ExtentReports extent = ExtentManager.getReporter(DeviceDetails.device_name);
//
//    public static synchronized ExtentTest getTest() {
//        return (ExtentTest)extentTestMap.get((int) (long) (Thread.currentThread().getId()));
//    }
//
//    public static synchronized void endTest() {
//        extent.endTest((ExtentTest)extentTestMap.get((int) (long) (Thread.currentThread().getId())));
//        extent.flush();
//    }
//
//    public static synchronized ExtentTest startTest(String testName, String testType) {
//        ExtentTest test = extent.startTest(testName);
//        extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);
//        logger.assignCategory(testType);
//        return test;
//    }
//}

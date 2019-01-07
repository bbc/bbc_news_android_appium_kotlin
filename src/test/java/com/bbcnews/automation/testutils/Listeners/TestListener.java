package com.bbcnews.automation.testutils.Listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.bbcnews.automation.testutils.DeviceDetails;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestListener implements ITestListener {

    public ExtentHtmlReporter htmlReporter;
    ExtentReports extent;
    ExtentTest test;

    public   String filename = "BBCNewsApp";
    public   String config_file = "extent-config.xml";
    public   String workingDirectory =  System.getProperty("user.dir");
    private Date curDate = new Date();
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private  String DateToStr = format.format(curDate);
    private  String absoluteFilePath = workingDirectory +"/Results/"+ File.separator+DateToStr;
    public   String screenshotpath = workingDirectory +"/Screenshots/";
    public   String config_file_dir = workingDirectory + File.separator + config_file;


    public void startReport() throws Exception
    {
        String device_name = DeviceDetails.populateDevices_Names();
        String device_OS_Name = DeviceDetails.populateDevices_OS();
        String device_ID_Name = DeviceDetails.populateDevices_IDs();
        Date curDate = new Date();
        System.out.println(curDate.toString());

        String dateName = new  SimpleDateFormat("hh:mm").format(new Date());

        htmlReporter = new ExtentHtmlReporter(absoluteFilePath+File.separator+"Harish"+device_name+dateName+".html");
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

    public void startTest(String testName, String testDecsription,String category)
    {
        try {
            test = extent.createTest(testName, testDecsription);
            test.assignCategory(category);
        }catch(Exception e)
        {

        }


    }

    public void publishReport()
    {
        extent.flush();


    }


    @Override
    public void onTestStart(ITestResult result) {

        System.out.println("on test method " +  getTestMethodName(result) + " start");

    }

    @Override
    public void onTestSuccess(ITestResult result) {
        if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " PASSED ", ExtentColor.GREEN));
        }

    }

    @Override
    public void onTestFailure(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " FAILED ", ExtentColor.RED));
            test.fail(result.getThrowable());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        if(result.getStatus() == ITestResult.SKIP)
        {
            test.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " SKIPPED ", ExtentColor.ORANGE));
            test.skip(result.getThrowable());
        }

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println("test failed but within success % " + getTestMethodName(result));
    }


    @Override
    public void onStart(ITestContext context) {
        System.out.println("on start of test " + context.getName());
    }


    @Override
    public void onFinish(ITestContext context) {
        System.out.println("on finish of test " + context.getName());
    }


    public  String getTestMethodName(ITestResult result){

        return result.getMethod().getConstructorOrMethod().getName();
    }

}

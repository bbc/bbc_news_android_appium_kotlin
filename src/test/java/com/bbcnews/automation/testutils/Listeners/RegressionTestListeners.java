package com.bbcnews.automation.testutils.Listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.tesults.tesults.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegressionTestListeners implements ITestListener {

    // A list to hold your test cases.
    List<Map<String,Object>> testCases = new ArrayList<Map<String, Object>>();
    ITestResult result;

    @Override
    public void onTestStart(ITestResult result) {

        if (result.getStatus() == ITestResult.STARTED) {
            System.out.println("on test method " + getTestMethodName(result) + " start");
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        System.out.println("I'M a  test method " + getTestMethodName(result) + " success");
        Map<String, Object> testCase = new HashMap<String, Object>();
        testCase.put("name", getTestMethodName(result));
        testCase.put("suite", "BBCNewsSuite");
        testCase.put("result", "pass");

        String Deviceos_Name = System.getProperty("DeviceOS");
        String Device_Name = System.getProperty("DeviceName");

        List<String> files = new ArrayList<String>();
        testCase.put("_DeviceName", Device_Name);
        testCase.put("_DeviceOS", Deviceos_Name);
        testCase.put("files",files);
        testCases.add(testCase);

    }

    @Override
    public void onTestFailure(ITestResult result) {
        String originalMessage=null;
        if (result.getStatus() == ITestResult.FAILURE)
        {
            Throwable throwable = result.getThrowable();
             originalMessage = throwable.getMessage();
        }
       // System.out.println("I'M a  test method " + getTestMethodName(result) + " failure");
        System.out.println("The name of the testcase failed is :"+getTestMethodName(result));
        Map<String, Object> testCase = new HashMap<String, Object>();
        testCase.put("name", getTestMethodName(result));//getTestMethodName(result));
        testCase.put("suite", "BBCNewsSuite");
        testCase.put("result", "fail");
       // testCase.put("reason",originalMessage);
        testCases.add(testCase);

    }



    @Override
    public void onTestSkipped(ITestResult result) {

       // System.out.println("I'M a  test method " + getTestMethodName(result) + " skipped");
        System.out.println("The name of the testcase Skipped is :"+getTestMethodName(result));
        Map<String, Object> testCase = new HashMap<String, Object>();
        testCase.put("name", getTestMethodName(result));//getTestMethodName(result));
        testCase.put("suite", "BBCNewsSuite");
        testCase.put("result", "unknown");
        testCases.add(testCase);

    }



    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

        System.out.println("test failed but within success % " + getTestMethodName(result));

    }



    @Override
    public void onStart(ITestContext context) {

        System.out.println("I'M a  start of test " + context.getName());

    }



    @Override
    public void onFinish(ITestContext context) {



        System.out.println("on finish of test " + context.getName());
        // Map<String, Object> to hold your test results data.
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("target", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6IjE2MjRkNDA1LTZhNTEtNDVkYS1iNmQyLWM5OTA3NWI4MDBlNi0xNTUxOTcwNDE3MjYwIiwiZXhwIjo0MTAyNDQ0ODAwMDAwLCJ2ZXIiOiIwIiwic2VzIjoiOTk5NWRiODQtYjdkMS00MjdmLWIyMzgtMDg1ZGIxNGIxZTdkIiwidHlwZSI6InQifQ.jyNIIoWPBAI9R9lQ_a_frh9JqQlDCbDKISAQeKy1N78");

        Map<String, Object> results = new HashMap<String, Object>();
        results.put("cases", testCases);
        data.put("results", results);

        // Upload
        Map<String, Object> response = Results.upload(data);
        System.out.println("success: " + response.get("success"));
        System.out.println("message: " + response.get("message"));
        System.out.println("errors: " + response.get("errors"));
        System.out.println("warnings: " + response.get("warnings"));





    }



    private static String getTestMethodName(ITestResult result) {

        return result.getMethod().getConstructorOrMethod().getName();

    }



}


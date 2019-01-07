package com.bbcnews.automation.testutils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DeviceDetails {


    public static List<String> deviceID = new ArrayList<String>();
    public static List<String> deviceOS = new ArrayList<String>();
    public static List<String> deviceName = new ArrayList<String>();

    public static String osVersion;
    public static Process process;
    public static String output;

    public static String sdkPath = "/Users/ramakh02/Library/Android/sdk/platform-tools/";
    public static String adbPath = sdkPath + File.separator + "./adb";
    public static String[] commandDevices = new String[]{adbPath, "devices"};
    public static String clearlogs = sdkPath + File.separator + "adb logcat " + "-c";
    public static CommandPrompt cmd = new CommandPrompt();


    public static String device_OS_Name = null;
    public static String device_ID_Name = null;
    public static String device_name = null;

    public static String device_names;
    public static String deviceID1;

    public static void getAllDetails() {


        for (int i = 0; i < deviceOS.size(); i++) {
            try {
                device_OS_Name = deviceOS.get(i);
                device_ID_Name = deviceID.get(i);
                device_name = deviceName.get(i);

                System.out.println("The Device OS is " + device_OS_Name);
                System.out.println("The Device ID is " + device_ID_Name);
                System.out.println("The Device Name is " + device_name);



            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }

        }

    }


    public static String populateDevices_IDs() throws Exception {
        process = new ProcessBuilder(commandDevices).start();

        output = cmd.runCommand(sdkPath + "./adb devices");
        String[] lines = output.split("\n");

        for (int i = 1; i < lines.length; i++) {
            lines[i] = lines[i].replaceAll("\\s+", "");

            if (lines[i].contains("device")) {
                lines[i] = lines[i].replaceAll("device", "");
                deviceID1 = lines[i];

                // System.out.println("Following device is connected");
//                 System.out.println("deviceID  " + deviceID1);
                deviceID.add(deviceID1);

            }

        }
        return deviceID1;
    }

    public static String  populateDevices_OS() throws Exception {
        process = new ProcessBuilder(commandDevices).start();
        output = cmd.runCommand(sdkPath + "./adb devices");
        String[] lines = output.split("\n");

        for (int i = 1; i < lines.length; i++) {
            lines[i] = lines[i].replaceAll("\\s+", "");

            if (lines[i].contains("device")) {
                lines[i] = lines[i].replaceAll("device", "");
                String deviceID1 = lines[i];
                // String model = cmd.runCommand(sdkPath + "./adb -s " +
                // deviceID1 + " shell getprop ro.product.model")
                // .replaceAll("\\s+", "");
                // String brand = cmd.runCommand(sdkPath + "./adb -s " +
                // deviceID1 + " shell getprop ro.product.brand")
                // .replaceAll("\\s+", "");
                osVersion = cmd
                        .runCommand(sdkPath + "./adb -s " + deviceID1 + " shell getprop ro.build.version.release")
                        .replaceAll("\\s+", "");
                // String devicenames = brand + " " + model;

                // System.out.println("Following device OS");
                // // System.out.println(deviceID+" "+osVersion+"\n");
//                 System.out.println("OS Version***** :" + osVersion);
                deviceOS.add(osVersion);

            }

        }
        return osVersion;
    }

    public static String populateDevices_Names() throws Exception {
        process = new ProcessBuilder(commandDevices).start();
        output = cmd.runCommand(sdkPath + "./adb devices");
        String[] lines = output.split("\n");

        for (int i = 1; i < lines.length; i++) {
            lines[i] = lines[i].replaceAll("\\s+", "");

            if (lines[i].contains("device")) {
                lines[i] = lines[i].replaceAll("device", "");
                String deviceID1 = lines[i];
                String model = cmd.runCommand(sdkPath + "./adb -s " + deviceID1 + " shell getprop ro.product.model")
                        .replaceAll("\\s+", "");
                String brand = cmd.runCommand(sdkPath + "./adb -s " + deviceID1 + " shell getprop ro.product.brand")
                        .replaceAll("\\s+", "");
                osVersion = cmd
                        .runCommand(sdkPath + "./adb -s " + deviceID1 + " shell getprop ro.build.version.release")
                        .replaceAll("\\s+", "");
                device_names = brand + " " + model;


                //System.out.println("Device Name ***** :" + device_names);
                deviceName.add(device_names);

            }

        }
        return device_names;
    }

    public static void main(String args[]) throws  Exception
    {
        getAllDetails();

        String deviceID = populateDevices_IDs();
        String deviceName = populateDevices_Names();
        String deviceOS = populateDevices_OS();

        System.out.println("deviceID ******  :- " + deviceID);
        System.out.println("deviceName ******  :- " + deviceName);
        System.out.println("deviceOS ******  :- " + deviceOS);
    }
}

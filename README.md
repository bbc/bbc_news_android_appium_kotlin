# bbc_news_android_appium_kotlin
Automation for BBC News Android app using Appium ,Kotlin and TestNG 

## What you need

Java JDK (with JAVA_HOME and PATH configured)

IDE 

Android SDK (for Android execution | with ANDROID_HOME and PATH configured)

Android AVD created (or Genymotion)

Appium installed through npm

## Android
Configurations
To execute the examples over the Android platform you'll need:

## Android SDK
Updated Build Tools, Platform Tools and, at least, one System Image (Android Version)

Configure Android Path on your environment variables

ANDROID_HOME: root android sdk directory

PATH: ANDROID_HOME + the following paths = platform-tools, tools, tools/bin

And Android Virtual Device

Inspect elements on Android

You can use the uiautomatorviewer to inspect elements on Android devices. or you can use Appium Desktop

# Appium
Try to always have Appium and libraries updated.

# Executing Scripts :
To run scripts , run the BBCNewsSmokeTest.xml file .

First you'll need to start appium. By typing appium -p portnumber (to run on a specify port. By default appium runs on port 4734) 

./gradlew test -DAppiumPort="4567" -DDeviceID="04157df4ec26b91a" -DDeviceOS="7.0" -DDeviceName="HUAWEI" -DAppPath="/Users/ramakh02/Desktop/tools/APK/BBCNews-5.5.0.47.apk" -PSmokeTest

**Note:**  
Change the device ID, OS version, device Name  to the devices connected in your system , pass the Appiumport, pass the AppPath location in you system i before you run it .

# Report
After execution a ExtentReport will be created under Result folder with TestName

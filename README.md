# bbc_news_android_appium_kotlin
Automation for BBC News Android app using Appium ,Kotlin , TestNG and PageObjectModel

## What you need

Java JDK (with JAVA_HOME and PATH configured)

IDE 

Android SDK (for Android execution | with ANDROID_HOME and PATH configured)

Android AVD created (or Genymotion)

## Appium needs node.js,install this too!

brew install node
npm install npm@latest -g
npm init  ( tap enter to all )

## Appium installed through npm

npm install -g appium

npm install wd

npm install appium-doctor -g

If you want a specific version of Appium use this:

npm install -g appium@x.x.x ( e.g. npm install -g appium@1.5.3)

run appium-doctor from terminal
You should get all greens from the report:

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

First you'll need to start appium. By typing appium -p portnumber (to run on a specify port. By default appium runs on port 4723) 

./gradlew test -DAppiumPort="Appium portNumber " -DDeviceID="DeviceID" -DDeviceOS="OS" -DDeviceName="DeviceName" -DAppPath="AppPath" -PSmokeTest

**Note:**  
Change the device ID, OS version, device Name  to the devices connected in your system , pass the Appiumport, pass the AppPath location in you system i before you run it .

# Report
After execution a ExtentReport will be created under Result folder with TestName

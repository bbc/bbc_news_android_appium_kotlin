package com.bbcnews.automation.testutils

import io.appium.java_client.PerformsTouchActions
import  io.appium.java_client.TouchAction

class PlatformTouchAction(performsActions: PerformsTouchActions):
        TouchAction<PlatformTouchAction>(performsActions)
package com.bbcnews.automation.pageobjects

import io.appium.java_client.MobileElement
import io.appium.java_client.pagefactory.AndroidBy
import io.appium.java_client.pagefactory.AndroidFindAll
import io.appium.java_client.pagefactory.AndroidFindBy

public class HomePageObjectKotlin
{
    fun HomePageObjectKotlin()
    {

    }

    @AndroidFindBy(xpath = "//android.widget.Button[@text='OK']")
    var okbutton: MobileElement? = null

    @AndroidFindBy(xpath = "//android.widget.Button[@text='GO TO SETTINGS']")
    var settingsButton: MobileElement? = null

    @AndroidFindBy(accessibility = "Back")
    var backButton: MobileElement? = null

    @AndroidFindBy(xpath = "//android.widget.Button[@text='NO, THANKS.']")
    var nothanksbutton: MobileElement? = null

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Top Stories']")
    var topstories: MobileElement? = null

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='My News']")
    var mynews: MobileElement? = null

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Popular']")
    var popular: MobileElement? = null

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Video']")
    var video: MobileElement? = null

    @AndroidFindBy(accessibility = "Search")
    var searchbutton: MobileElement? = null

    @AndroidFindAll(AndroidBy(id = "bbc.mobile.news.uk.internal:id/action_search"), AndroidBy(accessibility = "Search"))
    var search: MobileElement? = null

    @AndroidFindBy(accessibility = "Show navigation menu drawer")
    var menubutton: MobileElement? = null

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Politics']")
    var top_stories_Politics: MobileElement? = null

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='UK Politics']")
    var UKPolitics_topheading: MobileElement? = null

    @AndroidFindBy(accessibility = "Button:Videos of the day")
    var videooftheday_button: MobileElement? = null

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='WATCH']")
    var videoOftheDay_watch: MobileElement? = null

    @AndroidFindBy(accessibility = "Button: Stories")
    var stories_button: MobileElement? = null

    @AndroidFindBy(id = "bbc.mobile.news.uk.internal:id/newstream_promo_counter")
    var promocounter: MobileElement? = null

    // @AndroidFindBy(xpath = "android.widget.TextView[@text='WATCH']")
    @AndroidFindBy(id = "bbc.mobile.news.uk.internal:id/newstream_promo_watch_label")
    var watchvideo: MobileElement? = null

    @AndroidFindBy(accessibility = "Exit")
    var closewindow: MobileElement? = null

    @AndroidFindBy(accessibility = "Share")
    var sharebutton: MobileElement? = null

    @AndroidFindBy(accessibility = "play video content")
    var playvideocontent: MobileElement? = null

    @AndroidFindBy(xpath = "//android.widget.TextView[@index='0']")
    var storiescontainer: MobileElement? = null

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Check back later']")
    var checkback_later: MobileElement? = null

    @AndroidFindBy(id = "bbc.mobile.news.uk.internal:id/newstream_progress")
    var newstream_progress: MobileElement? = null

    @AndroidFindBy(xpath = "//android.widget.FrameLayout[1]/bbc.mobile.news.uk.internal:id/main_view[0]/android.widget.ImageView[@index='0']")
    var topstories_atricle: MobileElement? = null
}
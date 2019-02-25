package com.bbcnews.automation.pageobjects;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AndroidFindAll;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class HomePageObject
{

    public HomePageObject()
    {

    }

    @AndroidFindBy(xpath="//android.widget.Button[@text='OK']")
    public MobileElement okbutton;

    @AndroidFindBy(xpath="//android.widget.Button[@text='GO TO SETTINGS']")
    public MobileElement settingsButton;

    @AndroidFindBy(accessibility = "Back")
    public MobileElement backButton;

    @AndroidFindBy(xpath="//android.widget.Button[@text='NO, THANKS.']")
    public MobileElement nothanksbutton;

    @AndroidFindBy(xpath="//android.widget.TextView[@text='Top Stories']")
    public MobileElement topstories;

    @AndroidFindBy(xpath="//android.widget.TextView[@text='My News']")
    public MobileElement mynews;

    @AndroidFindBy(xpath="//android.widget.TextView[@text='Popular']")
    public MobileElement popular;

    @AndroidFindBy(xpath="//android.widget.TextView[@text='Video']")
    public MobileElement video;

    @AndroidFindBy(accessibility = "Search")
    public MobileElement searchbutton;

    @AndroidFindAll(
            {
                    @AndroidBy(id = "bbc.mobile.news.uk.internal:id/action_search"),
                    @AndroidBy(id = "bbc.mobile.news.uk:id/action_search"),
                    @AndroidBy(accessibility = "Search")
            }
    )
    public MobileElement search;

    @AndroidFindBy(accessibility="Show navigation menu drawer")
    public MobileElement menubutton;

    @AndroidFindBy(xpath="//android.widget.TextView[@text='Technology']")
    public MobileElement technologytopic;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Education']")
    public MobileElement educationstopics;

    @AndroidFindBy(xpath="//android.widget.TextView[@text='Family & Education']")
    public  MobileElement family_educationTopic;

    @AndroidFindBy(xpath="//android.widget.TextView[@text='Politics']")
    public MobileElement top_stories_Politics;

    @AndroidFindBy(xpath="//android.widget.TextView[@text='UK Politics']")
    public MobileElement UKPolitics_topheading;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Business']")
    public MobileElement Business_topics;

    @AndroidFindBy(accessibility = "Button:Videos of the day")
    public MobileElement videooftheday_button;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='WATCH']")
    public MobileElement videoOftheDay_watch;

    @AndroidFindBy(accessibility = "Button: Stories")
    public MobileElement stories_button;

   // @AndroidFindBy(id = "bbc.mobile.news.uk.internal:id/newstream_promo_counter")
    @AndroidFindAll(
            {
                    @AndroidBy(id = "bbc.mobile.news.uk.internal:id/newstream_promo_counter"),
                    @AndroidBy(id = "bbc.mobile.news.uk:id/newstream_promo_counter")
            }
    )
    public MobileElement promocounter;


   // @AndroidFindBy(id="bbc.mobile.news.uk.internal:id/newstream_promo_watch_label")
    @AndroidFindAll(
            {
                    @AndroidBy(id="bbc.mobile.news.uk.internal:id/newstream_promo_watch_label"),
                    @AndroidBy(id="bbc.mobile.news.uk:id/newstream_promo_watch_label")
            }
    )
    public MobileElement watchvideo;


    @AndroidFindAll(
            {
                    @AndroidBy(id="bbc.mobile.news.uk:id/newstream_promo_title"),
                    @AndroidBy(id="bbc.mobile.news.uk.internal:id/newstream_promo_title")
            }
    )
    public MobileElement vodeoofthedaytitle;

    @AndroidFindAll(
            {
                    @AndroidBy(id="bbc.mobile.news.uk.internal:id/newstream_promo_summary"),
                    @AndroidBy(id="bbc.mobile.news.uk:id/newstream_promo_summary")
            }
    )
    public MobileElement vodeoofthedaypromosummary;

    @AndroidFindAll(
            {
                    @AndroidBy(id="bbc.mobile.news.uk.internal:id/newstream_promo_watch_label"),
                    @AndroidBy(id="bbc.mobile.news.uk:id/newstream_promo_watch_label")
            }
    )
    public MobileElement videooftheday_watchtext;


    @AndroidFindBy(accessibility = "Exit")
    public MobileElement closewindow;

    @AndroidFindBy(accessibility = "Share")
    public MobileElement sharebutton;

    @AndroidFindBy(accessibility = "play video content")
    public MobileElement playvideocontent;

    @AndroidFindBy(xpath = "//android.widget.TextView[@index='0']")
    public MobileElement storiescontainer;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Check back later']")
    public MobileElement checkback_later;

   // @AndroidFindBy(id="bbc.mobile.news.uk.internal:id/newstream_progress")
    @AndroidFindAll(
            {
                    @AndroidBy(id="bbc.mobile.news.uk.internal:id/newstream_progress"),
                    @AndroidBy(id="bbc.mobile.news.uk:id/newstream_progress")
            }
    )
    public MobileElement newstream_progress;

  //  @AndroidFindBy(xpath = "//android.widget.FrameLayout[1]/bbc.mobile.news.uk.internal:id/main_view[0]/android.widget.ImageView[@index='0']")
    @AndroidFindAll(
            {
                    @AndroidBy(xpath = "//android.widget.FrameLayout[1]/bbc.mobile.news.uk.internal:id/main_view[0]/android.widget.ImageView[@index='0']"),
                    @AndroidBy(xpath = "//android.widget.FrameLayout[1]/bbc.mobile.news.uk:id/main_view[0]/android.widget.ImageView[@index='0']")
            }
    )


    public MobileElement topstories_atricle;



}

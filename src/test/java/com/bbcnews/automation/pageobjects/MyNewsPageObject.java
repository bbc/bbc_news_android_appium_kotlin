package com.bbcnews.automation.pageobjects;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class MyNewsPageObject {

    public MyNewsPageObject()
    {

    }



    @AndroidFindBy(id = "bbc.mobile.news.uk.internal:id/title")
    public MobileElement mynewstitle;

    @AndroidFindBy(accessibility = "OK, let's get started Button:")
    public MobileElement mynews_startButton;

    @AndroidFindBy(id = "bbc.mobile.news.uk.internal:id/summary")
    public MobileElement mynews_summary;

    @AndroidFindBy(id = "bbc.mobile.news.uk.internal:id/icon")
    public MobileElement addnews_button;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='My Topics']")
    public MobileElement mytopics;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Add Topics']")
    public MobileElement addtopics;

    @AndroidFindBy(accessibility = "Topic heading,Local news")
    public MobileElement localnews;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Allow location']")
    public MobileElement allow_location;

    @AndroidFindBy(id="com.android.packageinstaller:id/permission_allow_button")
    public MobileElement allowlocation_premission;

    @AndroidFindBy(id="com.android.packageinstaller:id/permission_deny_button")
    public MobileElement allowlocation_permission_deny;

    @AndroidFindBy(id = "bbc.mobile.news.uk.internal:id/request_permission")
    public MobileElement location_button;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Edit My News']")
    public MobileElement editMyTopics;

    @AndroidFindBy(id="bbc.mobile.news.uk.internal:id/emptyTextView")
    public MobileElement mytopic_emptyview;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Wales']")
    public MobileElement Walestopic;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Asia']")
    public MobileElement Asiatopic;

    @AndroidFindBy(accessibility = "Back")
    public MobileElement backbutton;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='European Union']")
    public MobileElement Europeantopic;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Mortgages']")
    //@AndroidFindBy(accessibility = "Button: Add Mortgages to My News")
    public MobileElement mortgagestopic;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='YouTube']")
    //@AndroidFindBy(accessibility = "Button: Add YouTube to My News")
    public MobileElement youtubetopic;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Education']")
    //@AndroidFindBy(accessibility = "Button: Add Education to My News")
    public MobileElement educationtopic;


    @AndroidFindBy(accessibility = "Manage your topics")
    public MobileElement mynews_managetopics;

    public  String mynewstitle_text="Add Topics to create your own personal news feed";

    public  String mynewssummary_text="All the latest stories from your topics will appear here.";

    public  String mytopic_emptyview_text="Your added topics will be displayed here";

    @AndroidFindBy(accessibility = "Add topic")
    public  MobileElement mynews_addtopics;

    @AndroidFindBy(accessibility="Edit My News")
    public MobileElement editMyNews;

    @AndroidFindBy(accessibility = "Remove topic")
    public MobileElement removetopics;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Manchester']")
    public MobileElement localnews_displayed;

    public String articlepageelements[] =
            {       "bbc.mobile.news.uk.internal:id/image_item_badge",
                    "bbc.mobile.news.uk.internal:id/headline_info",
                    "bbc.mobile.news.uk.internal:id/headline_title",
                    "bbc.mobile.news.uk.internal:id/image_item",
                    "bbc.mobile.news.uk.internal:id/headline_link",
            };

    public String articlepageview[] =
            {       "bbc.mobile.news.uk.internal:id/item_layout_name",
                    "bbc.mobile.news.uk.internal:id/item_layout_last_updated",
                    "bbc.mobile.news.uk.internal:id/item_layout_home_section"
            };

    @AndroidFindBy(xpath = "//android.support.v7.widget.RecyclerView[@index='3']")
    public MobileElement mynewsrecyclerview;



}


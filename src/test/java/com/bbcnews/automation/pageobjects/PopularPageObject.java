package com.bbcnews.automation.pageobjects;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;


public class PopularPageObject {

    public PopularPageObject()
    {

    }

    @AndroidFindBy(xpath="//android.widget.Button[@text='OK']")
    public MobileElement okbutton;

    @AndroidFindBy(xpath="//android.widget.Button[@text='NO, THANKS.']")
    public MobileElement nothanksbutton;

    @AndroidFindBy(xpath="//android.widget.TextView[@text='Popular']")
    public MobileElement popular;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Most Read']")
    public MobileElement mostread;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Most Watched']")
    public MobileElement popular_mostwatched;

   // @AndroidFindBy(xpath = "//android.widget.FrameLayout[@index='1']/android.view.ViewGroup[@index='0']/android.widget.ImageView[contains(@resource-id,'bbc.mobile.news.uk.internal:id/content_card_image') and @index='0']")
    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@resource-id,'bbc.mobile.news.uk.internal:id/content_card_ordered_badge') and @text='1']")
    public MobileElement populararticle;

    @AndroidFindBy(id="bbc.mobile.news.uk.internal:id/image_item_caption")
    public MobileElement image_item_caption;

    @AndroidFindBy(id="bbc.mobile.news.uk.internal:id/image_item_badge")
    public MobileElement image_item_badge;

    @AndroidFindBy(id="bbc.mobile.news.uk.internal:id/headline_title")
    public MobileElement headline_title;

    @AndroidFindBy(id="bbc.mobile.news.uk.internal:id/headline_author_name")
    public MobileElement headline_author_name;

    @AndroidFindBy(id="bbc.mobile.news.uk.internal:id/headline_author_title")
    public MobileElement headline_author_title;

    @AndroidFindBy(id="bbc.mobile.news.uk.internal:id/headline_info")
    public MobileElement headline_info;

    @AndroidFindBy(id="bbc.mobile.news.uk.internal:id/headline_link")
    public MobileElement headline_link;

    public String popularlinks[] =
            {       "bbc.mobile.news.uk.internal:id/image_item_badge",
                    "bbc.mobile.news.uk.internal:id/headline_info",
                    "bbc.mobile.news.uk.internal:id/headline_title",
                    "bbc.mobile.news.uk.internal:id/headline_info",
                    "bbc.mobile.news.uk.internal:id/headline_link"
            };


    //@AndroidFindBy(xpath = "//android.widget.FrameLayout[2]/android.view.ViewGroup[0]/android.widget.ImageView[@index='0']")
    @AndroidFindBy(xpath = "//android.widget.TextView[@text='3' and @index='1']")
    public MobileElement mostRead_article;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Most Popular']")
    public MobileElement mostpopular;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='3' and @index='1']")
    public MobileElement mostwatched_article;

    @AndroidFindBy(id="bbc.mobile.news.uk.internal:id/videoTitleHeadline")
    public MobileElement videoTitleHeadline;

    @AndroidFindBy(id="bbc.mobile.news.uk.internal:id/videoTitleTimestamp")
    public MobileElement videoTitleTimestamp;

    @AndroidFindBy(id="bbc.mobile.news.uk.internal:id/videoTitleTopic")
    public MobileElement videoTitleTopic;

    @AndroidFindBy(id="bbc.mobile.news.uk.internal:id/videoSummary")
    public MobileElement videoSummary;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Most Popular']")
    public MobileElement mostpopular_text;



}

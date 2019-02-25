package com.bbcnews.automation.pageobjects;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AndroidFindAll;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class PopularPageObjects {


    public PopularPageObjects()
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

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Most Watched' and @index='0']")
    public MobileElement popularmostwatched;

    // @AndroidFindBy(xpath = "//android.widget.FrameLayout[@index='1']/android.view.ViewGroup[@index='0']/android.widget.ImageView[contains(@resource-id,'bbc.mobile.news.uk.internal:id/content_card_image') and @index='0']")
    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@resource-id,'bbc.mobile.news.uk.internal:id/content_card_ordered_badge') and @text='1']")
    public MobileElement populararticle;

   // @AndroidFindBy(id="bbc.mobile.news.uk.internal:id/image_item_caption")
    @AndroidFindAll(
            {
                    @AndroidBy(id="bbc.mobile.news.uk.internal:id/image_item_caption"),
                    @AndroidBy(id="bbc.mobile.news.uk:id/image_item_caption")
            }
    )
    public MobileElement image_item_caption;

    //@AndroidFindBy(id="bbc.mobile.news.uk.internal:id/image_item_badge")
    @AndroidFindAll(
            {
                    @AndroidBy(id="bbc.mobile.news.uk.internal:id/image_item_badge"),
                    @AndroidBy(id="bbc.mobile.news.uk:id/image_item_badge")
            }
    )
    public MobileElement image_item_badge;

    //@AndroidFindBy(id="bbc.mobile.news.uk.internal:id/headline_title")
    @AndroidFindAll(
            {
                    @AndroidBy(id="bbc.mobile.news.uk.internal:id/headline_title"),
                    @AndroidBy(id="bbc.mobile.news.uk:id/headline_title")
            }
    )
    public MobileElement headline_title;

    //@AndroidFindBy(id="bbc.mobile.news.uk.internal:id/headline_author_name")
    @AndroidFindAll(
            {
                    @AndroidBy(id="bbc.mobile.news.uk.internal:id/headline_author_name"),
                    @AndroidBy(id="bbc.mobile.news.uk:id/headline_author_name")
            }
    )
    public MobileElement headline_author_name;

   // @AndroidFindBy(id="bbc.mobile.news.uk.internal:id/headline_author_title")
    @AndroidFindAll(
            {
                    @AndroidBy(id="bbc.mobile.news.uk.internal:id/headline_author_title"),
                    @AndroidBy(id="bbc.mobile.news.uk:id/headline_author_title")
            }
    )
    public MobileElement headline_author_title;

   // @AndroidFindBy(id="bbc.mobile.news.uk.internal:id/headline_info")
    @AndroidFindAll(
            {
                    @AndroidBy(id="bbc.mobile.news.uk.internal:id/headline_info"),
                    @AndroidBy(id="bbc.mobile.news.uk:id/headline_info")
            }
    )
    public MobileElement headline_info;

    //@AndroidFindBy(id="bbc.mobile.news.uk.internal:id/headline_link")
    @AndroidFindAll(
            {
                    @AndroidBy(id="bbc.mobile.news.uk.internal:id/videoTitleHeadline"),
                    @AndroidBy(id="bbc.mobile.news.uk:id/videoTitleHeadline")
            }
    )
    public MobileElement headline_link;

    public String mostreadpopularlinks[] =
            {       //"bbc.mobile.news.uk.internal:id/image_item_badge",
                    "bbc.mobile.news.uk.internal:id/headline_info",
                    "bbc.mobile.news.uk.internal:id/headline_title",
                    //"bbc.mobile.news.uk.internal:id/headline_author_title",
                    "bbc.mobile.news.uk.internal:id/headline_link",
                    // "bbc.mobile.news.uk.internal:id/image_item_caption",
                    //  "bbc.mobile.news.uk.internal:id/headline_author_name"
                    "bbc.mobile.news.uk.internal:id/image_item"
            };


    public String mostreadpopularlinks_Release[] =
            {
                    "bbc.mobile.news.uk:id/headline_info",
                    "bbc.mobile.news.uk:id/headline_title",
                    "bbc.mobile.news.uk:id/headline_link",
                    // "bbc.mobile.news.uk:id/image_item_caption",
                    //  "bbc.mobile.news.uk:id/headline_author_name"
                    "bbc.mobile.news.uk:id/image_item"
            };


    //@AndroidFindBy(xpath = "//android.widget.FrameLayout[2]/android.view.ViewGroup[0]/android.widget.ImageView[@index='0']")
    @AndroidFindBy(xpath = "//android.widget.TextView[@text='1' and @index='1']")
    public MobileElement mostRead_article;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Most Popular']")
    public MobileElement mostpopular;

    @AndroidFindBy(xpath = "//android.widget.FrameLayout[@index='3']/android.widget.TextView[@text='1' and @index='1']")
    public MobileElement mostwatched_article;

   // @AndroidFindBy(id="bbc.mobile.news.uk.internal:id/videoTitleHeadline")
    @AndroidFindAll(
            {
                    @AndroidBy(id="bbc.mobile.news.uk.internal:id/videoTitleHeadline"),
                    @AndroidBy(id="bbc.mobile.news.uk:id/videoTitleHeadline")
            }
    )
    public MobileElement videoTitleHeadline;

   // @AndroidFindBy(id="bbc.mobile.news.uk.internal:id/videoTitleTimestamp")
    @AndroidFindAll(
            {
                    @AndroidBy(id="bbc.mobile.news.uk.internal:id/videoTitleTimestamp"),
                    @AndroidBy(id="bbc.mobile.news.uk:id/videoTitleTimestamp")
            }
    )
    public MobileElement videoTitleTimestamp;

    //@AndroidFindBy(id="bbc.mobile.news.uk.internal:id/videoTitleTopic")
    @AndroidFindAll(
            {
                    @AndroidBy(id="bbc.mobile.news.uk.internal:id/videoTitleTopic"),
                    @AndroidBy(id="bbc.mobile.news.uk:id/videoTitleTopic")
            }
    )
    public MobileElement videoTitleTopic;

    //@AndroidFindBy(id="bbc.mobile.news.uk.internal:id/videoSummary")
    @AndroidFindAll(
            {
                    @AndroidBy(id="bbc.mobile.news.uk.internal:id/videoSummary"),
                    @AndroidBy(id="bbc.mobile.news.uk:id/videoSummary")
            }
    )
    public MobileElement videoSummary;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Most Popular']")
    public MobileElement mostpopular_text;




    public String popularvideoelements [] = {
            "bbc.mobile.news.uk.internal:id/smp_pause_button",
            // "bbc.mobile.news.uk.internal:id/videoTitleHeadline",
            "bbc.mobile.news.uk.internal:id/videoTitleTimestamp",
            "bbc.mobile.news.uk.internal:id/videoTitleTopic",
            "bbc.mobile.news.uk.internal:id/videoSummary"
    };

    public String popularvideoelements_Release [] = {
            "bbc.mobile.news.uk:id/smp_pause_button",
            "bbc.mobile.news.uk:id/videoTitleHeadline",
            "bbc.mobile.news.uk:id/videoTitleTimestamp",
            "bbc.mobile.news.uk:id/videoTitleTopic",
            "bbc.mobile.news.uk:id/videoSummary"
    };



    //@AndroidFindBy(xpath="//android.support.v7.widget.RecyclerView[@index='0']/android.widget.FrameLayout[@index='3']/android.widget.RelativeLayout[@index='0']/android.widget.FrameLayout[@index=0]/android.widget.TextView[@text='2' and index='1']")
    @AndroidFindBy(xpath="//android.support.v7.widget.RecyclerView[@index='0']/android.widget.FrameLayout[@index='3']/android.widget.RelativeLayout[@index='0']/android.widget.FrameLayout[@index=0]/android.widget.TextView[@text='2' and index='1']")
    public MobileElement mostwatchedvideoarticle;







    @AndroidFindBy(xpath="//*[@content-desc='Media length' or @text='2']")
    public MobileElement mostwatchedartcilevideo;


    @AndroidFindBy(xpath="//android.widget.RelativeLayout[@index='0']/android.widget.TextView[@index='2']")
    public MobileElement item_layout_name;

    @AndroidFindBy(xpath="//android.widget.RelativeLayout[@index='0']/android.widget.TextView[@index='1']")
    public MobileElement item_layout_position;

    //@AndroidFindBy(xpath="//android.widget.FrameLayout[@index='2']/android.widget.RelativeLayout[@index='0']/android.widget.LinearLayout[@index='3']/android.widget.LinearLayout[@index='0']/android.widget.TextView[@index='0']")
    @AndroidFindBy(xpath="//android.widget.LinearLayout[@index='0']/android.widget.TextView[@index='0']")
    public MobileElement item_layout_last_updated;

    //@AndroidFindBy(xpath="//android.widget.FrameLayout[@index='2']/android.widget.RelativeLayout[@index='0']/android.widget.LinearLayout[@index='3']/android.widget.LinearLayout[@index='0']/android.widget.TextView[@index='2']")
    @AndroidFindBy(xpath="//android.widget.LinearLayout[@index='0']/android.widget.TextView[@index='2']")
    public MobileElement item_layout_home_section;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Egypt tightens control over internet use']")
    public MobileElement relatedstorieArticle;

    @AndroidFindBy(xpath="//android.widget.FrameLayout[@index='5']/android.view.ViewGroup[@index='0']/android.widget.TextView[@index='1']")
    public MobileElement content_card_title;

    @AndroidFindBy(xpath = "//android.widget.FrameLayout[@index='5']/android.view.ViewGroup[@index='0']/android.widget.TextView[@index='2']")
    public  MobileElement content_card_info;

    @AndroidFindBy(xpath="//android.widget.FrameLayout[@index='5']/android.view.ViewGroup[@index='0']/android.widget.TextView[@index='3']")
    public MobileElement content_card_link;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Egypt']")
    public  MobileElement relatedtopicsArticle;
}

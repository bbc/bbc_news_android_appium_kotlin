package com.bbcnews.automation.pageobjects;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.By;

public class VideoPageObjects {


    public VideoPageObjects()
    {

    }


    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Video']")
    public MobileElement video;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='BBC News Channel']")
    public MobileElement bbcnewsChannel;

    //@AndroidFindBy(accessibility = "The BBC News Channel")
    //@AndroidFindBy(id="bbc.mobile.news.uk.internal:id/content_card_title")
    @AndroidFindBy(id="bbc.mobile.news.uk.internal:id/content_card_title")
    public MobileElement livebbchannel;

    @AndroidFindBy(id="bbc.mobile.news.uk.internal:id/media_item_caption")
    public MobileElement live_media_item_caption;

    @AndroidFindBy(accessibility = "Play")
    public MobileElement smp_placeholder_play_button;

    @AndroidFindBy(id = "bbc.mobile.news.uk.internal:id/smp_pause_button")
    public MobileElement smp_pause_button;

    @AndroidFindBy(accessibility = "volume")
    public MobileElement smp_volume_button;

    @AndroidFindBy(accessibility = "live content")
    public MobileElement smp_live_icon;

    @AndroidFindBy(accessibility = "live content")
    public MobileElement smpliveicon;

    @AndroidFindBy(accessibility = "Fullscreen")
    public MobileElement smp_fullscreen_button;

    @AndroidFindBy(accessibility="exit fullscreen")
    public MobileElement smp_exit_fullscreen_button;

    @AndroidFindBy(id = "bbc.mobile.news.uk.internal:id/smp_seek_bar")
    public MobileElement smp_seek_bar;

    // @AndroidFindBy(id ="bbc.mobile.news.uk.internal:id/smp_hide_transport_panel_button")
    @AndroidFindBy(id="bbc.mobile.news.uk.internal:id/smp_playout_window_inset")
    public MobileElement transportcontrol;

    @AndroidFindBy(id="bbc.mobile.news.uk.internal:id/smp_play_button")
    public MobileElement playbutton;


    public String videsoftheday[] = {
            "bbc.mobile.news.uk.internal:id/newstream_duration",
            "bbc.mobile.news.uk.internal:id/newstream_title",
            "bbc.mobile.news.uk.internal:id/newstream_summary"
    };

    @AndroidFindBy(accessibility = "play video content")
    public MobileElement videooftheday_play;

    @AndroidFindBy(id="bbc.mobile.news.uk.internal:id/newstream_duration")
    public  MobileElement videooftheday_duration;

    @AndroidFindBy(id="bbc.mobile.news.uk.internal:id/newstream_title")
    public  MobileElement videooftheday_title;

    @AndroidFindBy(id="bbc.mobile.news.uk.internal:id/newstream_summary")
    public MobileElement videooftheday_summary;


    public String videodetailpage[] = {
            "bbc.mobile.news.uk.internal:id/media_item_caption",
            "bbc.mobile.news.uk.internal:id/headline_title",
            "bbc.mobile.news.uk.internal:id/headline_info",
            "bbc.mobile.news.uk.internal:id/headline_link"
    };

    public String videdetailpagetext[] = {
            "In Egypt, fake news becomes weapon of choice to crush dissent",
            "Amal Fathy: Egypt court imposes jail term over harassment video",
            "31 Dec 2018",
            "Middle East"

    };


    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Amal Fathy: Egypt court imposes jail term over harassment video' and @index='1']")
    public MobileElement videoarticlesearch;


    public String playbackcontrols[] = {
            "bbc.mobile.news.uk.internal:id/smp_pause_button",
            "bbc.mobile.news.uk.internal:id/smp_seek_bar",
            "bbc.mobile.news.uk.internal:id/smp_fullscreen_button",
            "bbc.mobile.news.uk.internal:id/smp_duration",
            "bbc.mobile.news.uk.internal:id/smp_elapsed"
    };


    public String videowallelements[] = {
            "bbc.mobile.news.uk.internal:id/smp_placeholder_play_button",
            // "bbc.mobile.news.uk.internal:id/videoTitleHeadline",
            "bbc.mobile.news.uk.internal:id/videoTitleTimestamp",
            "bbc.mobile.news.uk.internal:id/videoTitleTopic",
            "bbc.mobile.news.uk.internal:id/videoSummary"
    };

    @AndroidFindBy(xpath="//android.widget.TextView[@text='Top Stories']")
    public MobileElement topstories;

    @AndroidFindBy(xpath="//android.widget.FrameLayout[@index='2']")///android.view.ViewGroup[@index='0']/android.widget.ImageView[@index='0']")
    //@AndroidFindBy(xpath="//android.widget.FrameLayout[@index='2']")
    //android.view.ViewGroup[@index='0']/android.widget.ImageView[@index='0']")
    public MobileElement topstoriesvideo;

    @AndroidFindBy(xpath="//android.widget.FrameLayout[@index='2']/android.widget.RelativeLayout[@index='0']/android.widget.TextView[@index='1']")
    public MobileElement topstoriesvideoplaytime;

    @AndroidFindBy(xpath="//android.widget.FrameLayout[@index='2']/android.widget.RelativeLayout[@index='0']/android.widget.TextView[@index='2']")
    public MobileElement topstoriesvideolayoutname;

    @AndroidFindBy(id = "bbc.mobile.news.uk.internal:id/smp_elapsed")
    public MobileElement smpelapsedtime;

    public  String elapsedtime_forward;

    public String elapsedtime_backward;
}

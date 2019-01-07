package com.bbcnews.automation.pageobjects;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class VidoePageObject {

    public VidoePageObject()
    {

    }

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Video']")
    public MobileElement video;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='BBC News Channel']")
    public MobileElement bbcnewsChannel;

    //@AndroidFindBy(accessibility = "The BBC News Channel")
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



}

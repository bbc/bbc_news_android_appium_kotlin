package com.bbcnews.automation.pageobjects;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AndroidFindAll;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class BBCNewsHindiPageObject {


    public BBCNewsHindiPageObject()
    {

    }

    @AndroidFindBy(id="android:id/button2")
    public MobileElement nothanksbutton;

    //@AndroidFindBy(xpath="//android.widget.FrameLayout[@index='0']/android.widget.RelativeLayout[@index='0']/android.widget.LinearLayout[@index='1']/android.widget.TextView[@index='0']")
    @AndroidFindBy(xpath="//android.widget.FrameLayout[@index='0']/android.widget.RelativeLayout[@index='0']/android.widget.LinearLayout[@index='1']/android.widget.TextView[@index='0']")
    public MobileElement mainitem_layout_name;

    @AndroidFindBy(xpath="//android.widget.FrameLayout[@index='0']/android.widget.RelativeLayout[@index='0']/android.widget.LinearLayout[@index='1']/android.widget.LinearLayout[@index='1']/android.widget.LinearLayout[@index='0']/android.widget.TextView[@index='0']")
    public MobileElement mainitem_layout_last_updated;


    @AndroidFindBy(xpath="//android.widget.FrameLayout[@index='1']/android.widget.RelativeLayout[@index='0']/android.widget.TextView[@index='1']")
    public MobileElement articleitemlayoutname;

    @AndroidFindBy(xpath="//android.widget.FrameLayout[@index='1']/android.widget.RelativeLayout[@index='0']/android.widget.LinearLayout[@index='2']/android.widget.LinearLayout[@index='0']/android.widget.TextView[@index='0']")
    public MobileElement articleitemlayoutlastupdated;

    //BBC News Hindi Common Page Objects

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='होम पेज']")
    public MobileElement bbchindi_homepage;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='भारत']")
    public MobileElement bbchindi_india;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='अंतरराष्ट्रीय']")
    public MobileElement bbchindi_international;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='मनोरंजन']")
    public MobileElement bbchindi_entertainment;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='खेल']")
    public MobileElement bbchindi_sports;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='रेडियो']")
    public MobileElement bbchindi_radio;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='विज्ञान-टेक्नॉलॉजी']")
    public MobileElement bbchindi_sciencetechnology;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='देखिए']")
    public MobileElement bbchindi_lookat;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='तस्वीरें']")
    public MobileElement bbchindi_thephotos;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='सोशल']")
    public MobileElement bbchindi_social;

    @AndroidFindBy(accessibility = "More options")
    public MobileElement bbc_moreoptions;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='सेटिंग्स']")
    public MobileElement bbchindi_settings;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='मदद']")
    public MobileElement bbchindi_help;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='संपर्क करें']")
    public MobileElement bbchindi_pleasecontact;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='बीबीसी के दूसरे ऐप्स']")
    public MobileElement bbchindi_OtherBBCapplications;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Internal Settings']")
    public MobileElement bbchindi_Internalsettings;

    @AndroidFindBy(xpath = "//android.widget.LinearLayout[@index='0']/android.view.ViewGroup[@index='0']/android.widget.ImageButton[@index='0']")
    public MobileElement bbchindi_Moresettings;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='स्थानीय समाचार']")
    public MobileElement bbchindi_localnews;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='टॉपिक्स']")
    public MobileElement bbchindi_topics;

    @AndroidFindBy(accessibility = "Button: टॉपिक्स , collapse group")
    public MobileElement bbchindi_topics_collapsegroup;

    @AndroidFindBy(accessibility = "Button: स्थानीय समाचार , collapse group")
    public MobileElement bbchindi_localnews_collapsegroup;

    @AndroidFindBy(accessibility = "Button: होम पेज ")
    public MobileElement hindihomepage;

    @AndroidFindBy(accessibility = "Button: भारत ")
    public MobileElement hindibharath;

    @AndroidFindBy(accessibility = "Button: अंतरराष्ट्रीय ")
    public MobileElement hindiinternatonal;

    @AndroidFindBy(accessibility = "Button: मनोरंजन ")
    public MobileElement hindienrairnment;

    @AndroidFindBy(accessibility = "Button: खेल ")
    public MobileElement hindisports;

    @AndroidFindBy(accessibility = "Button: विज्ञान-टेक्नॉलॉजी ")
    public MobileElement hindiscience;

    @AndroidFindBy(accessibility = "Button: सोशल ")
    public MobileElement hindisocial;

    @AndroidFindBy(accessibility = "Button: देखिए ")
    public MobileElement hindilookat;

    @AndroidFindBy(accessibility = "Button: तस्वीरें ")
    public MobileElement hindiphotos;

    @AndroidFindAll({
            @AndroidBy(id="uk.co.bbc.hindi.internal:id/image_item_caption"),
            @AndroidBy(id="uk.co.bbc.hindi:id/image_item_caption")
    })
    public MobileElement imageitemcaption;

    @AndroidFindAll({
            @AndroidBy(id="uk.co.bbc.hindi.internal:id/headline_title"),
            @AndroidBy(id="uk.co.bbc.hindi:id/headline_title")
    })
    public MobileElement headlinetitle;

    @AndroidFindAll({
            @AndroidBy(id="uk.co.bbc.hindi.internal:id/headline_info"),
            @AndroidBy(id="uk.co.bbc.hindi:id/headline_info")
    })
    public MobileElement headlineinfo;

    @AndroidFindAll({
            @AndroidBy(id="uk.co.bbc.hindi.internal:id/headline_author_name"),
            @AndroidBy(id="uk.co.bbc.hindi:id/headline_author_name")
    })
    public MobileElement headlineauthorname;

    @AndroidFindAll({
            @AndroidBy(id="uk.co.bbc.hindi.internal:id/headline_author_title"),
            @AndroidBy(id="uk.co.bbc.hindi:id/headline_author_title")
    })
    public MobileElement headlineauthortitle;

    @AndroidFindAll({
            @AndroidBy(xpath = "//android.widget.Button[@index='1']"),
            @AndroidBy(xpath = "//android.widget.Button[@text='ओके']")
    })
    public MobileElement bbchindi_okbutton;

    @AndroidFindAll({
            @AndroidBy(id = "uk.co.bbc.hindi.internal:id/image_item_badge"),
            @AndroidBy(id = "uk.co.bbc.hindi:id/image_item_badge")
    })
    public MobileElement imageitembadge;

    @AndroidFindAll({
            @AndroidBy(id = "uk.co.bbc.hindi.internal:id/media_item_caption"),
            @AndroidBy(id = "uk.co.bbc.hindi:id/media_item_caption")
    })
    public MobileElement mediaitemcaption;

    @AndroidFindAll({
            @AndroidBy(id="uk.co.bbc.hindi.internal:id/smp_seek_bar"),
            @AndroidBy(id="uk.co.bbc.hindi:id/smp_seek_bar")
    })
    public MobileElement seekbar;

    @AndroidFindAll({
            @AndroidBy(id="uk.co.bbc.hindi.internal:id/smp_pause_button"),
            @AndroidBy(id="uk.co.bbc.hindi:id/smp_pause_button")
    })
    public MobileElement pausebutton;


    @AndroidFindAll({
            @AndroidBy(id="uk.co.bbc.hindi.internal:id/title"),
            @AndroidBy(id="uk.co.bbc.hindi:id/title")
    })
    public MobileElement picturestitle;

    @AndroidFindAll({
            @AndroidBy(id="uk.co.bbc.hindi.internal:id/subtitle"),
            @AndroidBy(id="uk.co.bbc.hindi:id/subtitle")
    })
    public MobileElement picturessubtitle;


    @AndroidFindBy(xpath = "//android.widget.TextView[@index='2']")
    public MobileElement radiopagetext;

    @AndroidFindBy(xpath = "//android.widget.TextView[@index='3']")
    public MobileElement radiopagetextdaily;

    @AndroidFindBy(id="android:id/message")
    public MobileElement bbchindi_message;

    @AndroidFindBy(accessibility = "बैक")
    public MobileElement backbutton;

    @AndroidFindBy(xpath="//android.widget.TextView[@text='हेमिल्टन जीता तो इतिहास रच देगी टीम इंडिया']")
    public MobileElement sportsarticle;

    @AndroidFindBy(xpath="//android.widget.FrameLayout[@index='2']")
    public MobileElement article;

    @AndroidFindBy(xpath="//android.widget.TextView[@text='संबंधित टॉपिक']")
    public MobileElement relatedtopics;

    @AndroidFindBy(xpath="//android.widget.TextView[@text='संबंधित स्टोरीज़']")
    public MobileElement relatedarticles;

    @AndroidFindBy(accessibility = "Play")
    public MobileElement playbutton;

    @AndroidFindBy(accessibility = "volume")
    public MobileElement volumebutton;

    @AndroidFindBy(accessibility = "Fullscreen")
    public MobileElement Fullscreenbutton;

    @AndroidFindBy(xpath="//android.widget.TextView[@text='Front page']")
    public MobileElement frontpage;
}

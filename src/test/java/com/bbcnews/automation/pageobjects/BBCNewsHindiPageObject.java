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
    public MobileElement item_layout_name;

    @AndroidFindBy(xpath="//android.widget.FrameLayout[@index='0']/android.widget.RelativeLayout[@index='0']/android.widget.LinearLayout[@index='1']/android.widget.LinearLayout[@index='1']/android.widget.LinearLayout[@index='0']/android.widget.TextView[@index='0']")
    public MobileElement item_layout_last_updated;

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
            @AndroidBy(xpath = "//android.widget.Button[@index=1]"),
            @AndroidBy(xpath = "//android.widget.Button[@text='ओके']")
    })
    public MobileElement bbchindi_okbutton;

    @AndroidFindBy(id="android:id/message")
    public MobileElement bbchindi_message;
}

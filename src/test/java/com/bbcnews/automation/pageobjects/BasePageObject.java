package com.bbcnews.automation.pageobjects;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AndroidFindAll;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class BasePageObject {

    AppiumDriver<MobileElement> appiumDriver;

    public BasePageObject()
    {

    }

    @AndroidFindBy(accessibility = "Navigate up")
    public MobileElement navigate_back;

    //@AndroidFindBy(xpath = "//android.widget.Button[@resource-id='android:id/button1' and @text='OK']")
    @AndroidFindBy(xpath="//android.widget.Button[@text='OK']")
    public MobileElement okbutton;

    @AndroidFindBy(xpath="//android.widget.Button[@text='GO TO SETTINGS']")
    public MobileElement settingsButton;

    @AndroidFindBy(accessibility = "Back")
    public MobileElement backButton;

    // @AndroidFindBy(xpath = "//android.widget.Button[@resource-id='android:id/button2' and @text='NO, THANKS.']")
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

    @AndroidFindBy(accessibility = "Share story")
    public MobileElement sharestory;


    @AndroidFindAll(
            {
                    @AndroidBy(id = "bbc.mobile.news.uk.internal:id/action_search"),
                    @AndroidBy(accessibility = "Search")
            }
    )
    public MobileElement search;

    @AndroidFindBy(id="bbc.mobile.news.uk.internal:id/item_layout_name")
    public MobileElement item_layout_name;

    @AndroidFindBy(id="bbc.mobile.news.uk.internal:id/item_layout_last_updated")
    public MobileElement item_layout_last_updated;

    @AndroidFindBy(id="bbc.mobile.news.uk.internal:id/item_layout_home_section")
    public MobileElement item_layout_home_section;

    @AndroidFindBy(accessibility="Show navigation menu drawer")
    public MobileElement menubutton;

    @AndroidFindBy(xpath = "//android.widget.CheckedTextView[@text='Settings']")
    public MobileElement settings;

    @AndroidFindBy(xpath = "//android.widget.CheckedTextView[@text='App info']")
    public MobileElement Appinfo;

    @AndroidFindBy(xpath = "//android.widget.CheckedTextView[@text='Other BBC apps']")
    public MobileElement OtherBBCapps;

    @AndroidFindBy(xpath = "//android.widget.CheckedTextView[@text='Internal Settings']")
    public MobileElement InternalSettings;

    @AndroidFindBy(xpath = "//android.widget.EditText[@text='Search topics and articles']")
    public MobileElement searchfield;

    // @AndroidFindBy(xpath="//android.widget.TextView[contains(@resource-id,'bbc.mobile.news.uk.internal:id/chip_item') and @index='0']")
    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Brexit' and @index='0']")
    public MobileElement searchkeyword;

    @AndroidFindBy(id="bbc.mobile.news.uk.internal:id/title")
    public MobileElement headlinetitle;

    //  @AndroidFindBy(id = "bbc.mobile.news.uk.internal:id/heading")
    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@resource-id,'bbc.mobile.news.uk.internal:id/heading') and @index='0']")
    //@AndroidFindBy(xpath = "//android.widget.TextView[contains(@resource-id,'bbc.mobile.news.uk.internal:id/content_card_ordered_badge') and @text='1']")
    public MobileElement searchheading;

    @AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='bbc.mobile.news.uk.internal:id/heading' and @index='2']")
    public MobileElement searchheading2;

    @AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='bbc.mobile.news.uk.internal:id/heading' and @index='4']")
    public MobileElement searchheading4;

    @AndroidFindBy(xpath = "//bbc.mobile.news.uk.internal:id/chip_item[@index='1']")
    public MobileElement searchsuggest1;
    public static String searchsuggest1_text="India";

    @AndroidFindBy(xpath = "//bbc.mobile.news.uk.internal:id/chip_item[@index='2']")
    public MobileElement searchsuggest2;
    public static String searchsuggest2_text="India-Pakistan independence";

    @AndroidFindBy(xpath = "//bbc.mobile.news.uk.internal:id/chip_item[@index='3']")
    public MobileElement searchsuggest3;
    public static String searchsuggest3_text="Rape in India";

    @AndroidFindBy(accessibility = "Cancel search")
    public MobileElement cancelSearch;

    public  String SubDirectory =  "Screenshots";

    public  String ScreenshotPaths;

    public  String searchtext ="Brexit";


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

    @AndroidFindBy(xpath = "//android.widget.Button[@index=1]")
    public MobileElement bbchindi_okbutton;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='UK Politics added to My News']")
    //   @AndroidFindBy(id="bbc.mobile.news.uk.internal:id/snackbar_text")
    public MobileElement alert_text;

    //@AndroidFindBy(id="bbc.mobile.news.uk.internal:id/snackbar_action")
    @AndroidFindBy(xpath = "//android.widget.Button[@text='UNDO' and @index='1']")
    public MobileElement undo_button;

    public String alert_text_uk = "UK Politics added to My News";

    public String alert_text_business = "Business added to My News";


}

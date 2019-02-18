package com.bbcnews.automation.pageobjects;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AndroidFindAll;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BasePageObject {

    AppiumDriver<MobileElement> appiumDriver;

//    public BasePageObject(AppiumDriver<MobileElement> appiumDriver)
//    {
//        this.appiumDriver=appiumDriver;
//
//    }

    public BasePageObject() {

    }

    @AndroidFindBy(accessibility = "Navigate up")
    public MobileElement navigate_back;

    //@AndroidFindBy(xpath = "//android.widget.Button[@resource-id='android:id/button1' and @text='OK']")
    // @AndroidFindBy(xpath = "//android.widget.Button[@text='OK']")
    @AndroidFindBy(id="android:id/button1")
    public MobileElement okbutton;

    @AndroidFindBy(xpath = "//android.widget.Button[@text='GO TO SETTINGS']")
    public MobileElement settingsButton;

    @AndroidFindBy(accessibility = "Back")
    public MobileElement backButton;

    // @AndroidFindBy(xpath = "//android.widget.Button[@resource-id='android:id/button2' and @text='NO, THANKS.']")
    //@AndroidFindBy(xpath = "//android.widget.Button[@text='NO, THANKS.' and index='0']")
    @AndroidFindBy(id="android:id/button2")
    public MobileElement nothanksbutton;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Top Stories']")
    public MobileElement topstories;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='My News']")
    public MobileElement mynews;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Popular']")
    public MobileElement popular;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Video']")
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

    public String[] topicspageelemnets =
            {
                    "bbc.mobile.news.uk.internal:id/item_image",
                    "bbc.mobile.news.uk.internal:id/item_layout_name",
                    "bbc.mobile.news.uk.internal:id/item_layout_last_updated",
                    "bbc.mobile.news.uk.internal:id/item_layout_home_section"
            };

    @AndroidFindBy(id = "bbc.mobile.news.uk.internal:id/item_layout_name")
    public MobileElement item_layout_name;

    @AndroidFindBy(id = "bbc.mobile.news.uk.internal:id/item_layout_last_updated")
    public MobileElement item_layout_last_updated;

    @AndroidFindBy(id = "bbc.mobile.news.uk.internal:id/item_layout_home_section")
    public MobileElement item_layout_home_section;

    @AndroidFindBy(accessibility = "Show navigation menu drawer")
    public MobileElement menubutton;

    @AndroidFindBy(xpath = "//android.widget.CheckedTextView[@text='Settings']")
    public MobileElement settings;

    @AndroidFindBy(xpath = "//android.widget.CheckedTextView[@text='App info']")
    public MobileElement Appinfo;

    @AndroidFindBy(xpath = "//android.widget.CheckedTextView[@text='Other BBC apps']")
    public MobileElement OtherBBCapps;

    @AndroidFindBy(xpath = "//android.widget.CheckedTextView[@text='Internal Settings']")
    public MobileElement InternalSettings;

    public String menuoptions[] = {"//android.widget.CheckedTextView[@text='Settings']",
            "//android.widget.CheckedTextView[@text='App info']",
            "//android.widget.CheckedTextView[@text='Other BBC apps']",
            "//android.widget.CheckedTextView[@text='Internal Settings']"};

    @AndroidFindBy(xpath = "//android.widget.EditText[@text='Search topics and articles']")
    public MobileElement searchfield;

    // @AndroidFindBy(xpath="//android.widget.TextView[contains(@resource-id,'bbc.mobile.news.uk.internal:id/chip_item') and @index='0']")
    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Brexit' and @index='0']")
    public MobileElement searchkeyword;

    @AndroidFindBy(id = "bbc.mobile.news.uk.internal:id/title")
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
    public static String searchsuggest1_text = "India";

    @AndroidFindBy(xpath = "//bbc.mobile.news.uk.internal:id/chip_item[@index='2']")
    public MobileElement searchsuggest2;
    public static String searchsuggest2_text = "India-Pakistan independence";

    @AndroidFindBy(xpath = "//bbc.mobile.news.uk.internal:id/chip_item[@index='3']")
    public MobileElement searchsuggest3;
    public static String searchsuggest3_text = "Rape in India";

    @AndroidFindBy(accessibility = "Cancel search")
    public MobileElement cancelSearch;

    public  String SubDirectory = "Screenshots";

    public  String ScreenshotPaths;

    public  String searchtext = "Brexit";


    @AndroidFindBy(xpath = "//android.widget.TextView[@text='UK Politics added to My News']")
    //   @AndroidFindBy(id="bbc.mobile.news.uk.internal:id/snackbar_text")
    public MobileElement alert_text;

    //@AndroidFindBy(id="bbc.mobile.news.uk.internal:id/snackbar_action")
    @AndroidFindBy(xpath = "//android.widget.Button[@text='UNDO' and @index='1']")
    public MobileElement undo_button;

    public String alert_text_uk = "UK Politics added to My News";

    public String alert_text_business = "Business added to My News";

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Rape in India']")
    //@AndroidFindBy(xpath = "//android.widget.TextView[contains(@resource-id,'bbc.mobile.news.uk.internal:id/chip_item') and @index='2']")
    public MobileElement article;

    // @AndroidFindBy(xpath = "//android.widget.TextView[@text='India man held for rape of British woman in Goa' and index='1']")
    // @AndroidFindBy(xpath = "//android.widget.FrameLayout[2]/android.view.ViewGroup[0]/android.widget.ImageView[contains(@resource-id,'bbc.mobile.news.uk.internal:id/content_card_image') and @index='0']")
    @AndroidFindBy(xpath = "//android.widget.FrameLayout[@index='3']/android.view.ViewGroup[@index='0']/android.widget.ImageView[@index='0']")
    public MobileElement articlesearch;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='How social media helped catch rape suspect' and @index='1']")
    public MobileElement artictleitem_withitembadge;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='India bishop accused of rape arrested' and @index='1']")
    public MobileElement artictleitem_withoutitembadge;

    @AndroidFindBy(xpath="//android.widget.FrameLayout[6]/bbc.mobile.news.uk.internal:id/main_view[0]/android.widget.ImageView[@index='0']")
    public  MobileElement articleitem_image;

    //@AndroidFindBy(xpath = "//android.widget.FrameLayout[0]/android.widget.LinearLayout[1/android.widget.TextView[@inde='0']")
    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@resource-id,'bbc.mobile.news.uk.internal:id/item_layout_name') and @index='0']")
    public MobileElement articlelayout_name;

    // @AndroidFindBy(xpath = "//android.widget.FrameLayout[0]/android.widget.LinearLayout[1/android.widget.LinearLayout[0]/android.widget.TextView[@index='0']")
    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@resource-id,'bbc.mobile.news.uk.internal:id/item_layout_last_updated') and @index='0']")
    public MobileElement articlellast_updated;

    //@AndroidFindBy(xpath = "//android.widget.FrameLayout[0]/android.widget.LinearLayout[1/android.widget.LinearLayout[0]/android.widget.TextView[@index='2']")
    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@resource-id,'bbc.mobile.news.uk.internal:id/item_layout_home_section') and @index='0']")
    public MobileElement articlelhome_section;




    public String articledetailpagelinks[] =
            {
                    "bbc.mobile.news.uk.internal:id/image_item_caption",
                    "bbc.mobile.news.uk.internal:id/headline_title",
                    "bbc.mobile.news.uk.internal:id/headline_info",
                    "bbc.mobile.news.uk.internal:id/headline_link"};

    public String articleitemwithimagebadge [] =
            {

                    "Hundreds of people poured onto the streets demanding justice for the girl",
                    "How India viral messages helped catch rape suspect",
                    "10 Jul 2018",
                    "India"
            };

    public String articleitemwithoutimagebadge [] =
            {
                    "Bishop Franco Mulakkal denies wrongoing",
                    "India bishop accused of rape arrested in Kerala",
                    "21 Sep 2018",
                    "India"
            };

    public String articlepagedetail [] =
            {
                    "India man held for rape of British woman in Goa",
                    "21 Dec 2018",
                    "India"
            };

    public String articlepagedetailelements[] =
            {       "bbc.mobile.news.uk.internal:id/headline_title",
                    "bbc.mobile.news.uk.internal:id/headline_info",
                    "bbc.mobile.news.uk.internal:id/headline_link"};



    @AndroidFindBy(id = "bbc.mobile.news.uk.internal:id/image_item_badge")
    public MobileElement article_imagebade;

    @AndroidFindBy(xpath="//android.widget.TextView[@text='Related stories']")
    public MobileElement relatedstories;

    @AndroidFindBy(xpath="//android.widget.TextView[@text='Related topics']")
    public MobileElement relatedtopics;

    @AndroidFindBy(id="bbc.mobile.news.uk.internal:id/smp_error_message")
    public MobileElement smperrormessage;

    @AndroidFindBy(id="bbc.mobile.news.uk.internal:id/smp_error_button")
    public MobileElement smperrorokbutton;

    @AndroidFindBy(id="bbc.mobile.news.uk.internal:id/smp_retry_button")
    public MobileElement smpretrybuton;

    @AndroidFindBy(xpath="//android.widget.ListView[@index='0']")
    public MobileElement appinfolistview;

    @AndroidFindBy(xpath = "//android.widget.CheckedTextView[@text='App info']")
    public MobileElement menuappinfo;


    //@FindBy(xpath="//*[contains(text(), 'Terms of Use')]")
    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Terms of use']")
    public MobileElement termsconditions;

    //@FindBy(xpath="//*[contains(text(), 'Privacy policy')]")
    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Privacy policy']")
    public MobileElement privacypolicy;

    @FindBy(xpath="//*[contains(text(), 'A few rules for us and you')]")
    public WebElement termofustext;

    @FindBy(xpath="//*[contains(text(), 'Keeping your info safe & sound')]")
    public WebElement privacypolicytext;

    @FindBy(xpath="//*[contains(text(), 'Your Information & Privacy')]")
    public WebElement getPrivacypolicytext;

    @AndroidFindBy(xpath = "//android.widget.CheckedTextView[@text='Internal Settings']")
    public MobileElement internalsettings;

    @AndroidFindBy(xpath="//android.widget.TextView[@text='CPS Content']")
    public MobileElement cpscontent;

    @AndroidFindBy(xpath="//android.widget.CheckedTextView[@text='Trevor TEST (Direct)' and @index='4']")
    public MobileElement trevortest;

    //@AndroidFindBy(xpath = "//android.widget.Button[@text='RELOAD' and @index='1']")
    @AndroidFindBy(id="bbc.mobile.news.uk.internal:id/snackbar_action")
    public MobileElement reloadButton;

    @AndroidFindBy(xpath = "//android.widget.CheckedTextView[@text='Settings']")
    public MobileElement menusettings;








}

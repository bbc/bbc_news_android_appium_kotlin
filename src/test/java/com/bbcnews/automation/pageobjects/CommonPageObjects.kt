package com.bbcnews.automation.pageobjects

import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import io.appium.java_client.pagefactory.AndroidBy
import io.appium.java_client.pagefactory.AndroidFindAll
import io.appium.java_client.pagefactory.AndroidFindBy
import org.openqa.selenium.support.FindBy

class CommonPageObjects
{

    internal var appiumDriver: AppiumDriver<MobileElement>? = null

    fun CommonPageObjects()
    {

    }

    @AndroidFindBy(accessibility = "Navigate up")
    var navigate_back: MobileElement? = null

    //@AndroidFindBy(xpath = "//android.widget.Button[@resource-id='android:id/button1' and @text='OK']")
    @AndroidFindBy(xpath = "//android.widget.Button[@text='OK']")
    var okbutton: MobileElement? = null

    @AndroidFindBy(xpath = "//android.widget.Button[@text='GO TO SETTINGS']")
    var settingsButton: MobileElement? = null

    @AndroidFindBy(accessibility = "Back")
    var backButton: MobileElement? = null

    // @AndroidFindBy(xpath = "//android.widget.Button[@resource-id='android:id/button2' and @text='NO, THANKS.']")
    @AndroidFindBy(xpath = "//android.widget.Button[@text='NO, THANKS.']")
    var nothanksbutton: MobileElement? = null

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Top Stories']")
    var topstories: MobileElement? = null

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='My News']")
    var mynews: MobileElement? = null

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Popular']")
    var popular: MobileElement? = null

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Video']")
    var video: MobileElement? = null

    @AndroidFindBy(accessibility = "Search")
    var searchbutton: MobileElement? = null

    @AndroidFindBy(accessibility = "Share story")
    var sharestory: MobileElement? = null


    @AndroidFindAll(AndroidBy(id = "bbc.mobile.news.uk.internal:id/action_search"), AndroidBy(accessibility = "Search"))
    var search: MobileElement? = null

    @AndroidFindBy(id = "bbc.mobile.news.uk.internal:id/item_layout_name")
    var item_layout_name: MobileElement? = null

    @AndroidFindBy(id = "bbc.mobile.news.uk.internal:id/item_layout_last_updated")
    var item_layout_last_updated: MobileElement? = null

    @AndroidFindBy(id = "bbc.mobile.news.uk.internal:id/item_layout_home_section")
    var item_layout_home_section: MobileElement? = null

    @AndroidFindBy(accessibility = "Show navigation menu drawer")
    var menubutton: MobileElement? = null

    @AndroidFindBy(xpath = "//android.widget.CheckedTextView[@text='Settings']")
    var settings: MobileElement? = null

    @AndroidFindBy(xpath = "//android.widget.CheckedTextView[@text='App info']")
    var Appinfo: MobileElement? = null

    @AndroidFindBy(xpath = "//android.widget.CheckedTextView[@text='Other BBC apps']")
    var OtherBBCapps: MobileElement? = null

    @AndroidFindBy(xpath = "//android.widget.CheckedTextView[@text='Internal Settings']")
    var InternalSettings: MobileElement? = null

    @AndroidFindBy(xpath = "//android.widget.EditText[@text='Search topics and articles']")
    var searchfield: MobileElement? = null

    // @AndroidFindBy(xpath="//android.widget.TextView[contains(@resource-id,'bbc.mobile.news.uk.internal:id/chip_item') and @index='0']")
    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Brexit' and @index='0']")
    var searchkeyword: MobileElement? = null

    @AndroidFindBy(id = "bbc.mobile.news.uk.internal:id/title")
    var headlinetitle: MobileElement? = null

    //  @AndroidFindBy(id = "bbc.mobile.news.uk.internal:id/heading")
    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@resource-id,'bbc.mobile.news.uk.internal:id/heading') and @index='0']")
    //@AndroidFindBy(xpath = "//android.widget.TextView[contains(@resource-id,'bbc.mobile.news.uk.internal:id/content_card_ordered_badge') and @text='1']")
    var searchheading: MobileElement? = null

    @AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='bbc.mobile.news.uk.internal:id/heading' and @index='2']")
    var searchheading2: MobileElement? = null

    @AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='bbc.mobile.news.uk.internal:id/heading' and @index='4']")
    var searchheading4: MobileElement? = null

    @AndroidFindBy(xpath = "//bbc.mobile.news.uk.internal:id/chip_item[@index='1']")
    var searchsuggest1: MobileElement? = null
    var searchsuggest1_text = "India"

    @AndroidFindBy(xpath = "//bbc.mobile.news.uk.internal:id/chip_item[@index='2']")
    var searchsuggest2: MobileElement? = null
    var searchsuggest2_text = "India-Pakistan independence"

    @AndroidFindBy(xpath = "//bbc.mobile.news.uk.internal:id/chip_item[@index='3']")
    var searchsuggest3: MobileElement? = null
    var searchsuggest3_text = "Rape in India"

    @AndroidFindBy(accessibility = "Cancel search")
    var cancelSearch: MobileElement? = null

    var SubDirectory = "Screenshots"

    var ScreenshotPaths: String? = null

    var searchtext = "Brexit"


    //BBC News Hindi Common Page Objects

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='होम पेज']")
    var bbchindi_homepage: MobileElement? = null

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='भारत']")
    var bbchindi_india: MobileElement? = null

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='अंतरराष्ट्रीय']")
    var bbchindi_international: MobileElement? = null

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='मनोरंजन']")
    var bbchindi_entertainment: MobileElement? = null

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='खेल']")
    var bbchindi_sports: MobileElement? = null

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='रेडियो']")
    var bbchindi_radio: MobileElement? = null

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='विज्ञान-टेक्नॉलॉजी']")
    var bbchindi_sciencetechnology: MobileElement? = null

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='देखिए']")
    var bbchindi_lookat: MobileElement? = null

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='तस्वीरें']")
    var bbchindi_thephotos: MobileElement? = null

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='सोशल']")
    var bbchindi_social: MobileElement? = null

    @AndroidFindBy(accessibility = "More options")
    var bbc_moreoptions: MobileElement? = null

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='सेटिंग्स']")
    var bbchindi_settings: MobileElement? = null

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='मदद']")
    var bbchindi_help: MobileElement? = null

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='संपर्क करें']")
    var bbchindi_pleasecontact: MobileElement? = null

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='बीबीसी के दूसरे ऐप्स']")
    var bbchindi_OtherBBCapplications: MobileElement? = null

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Internal Settings']")
    var bbchindi_Internalsettings: MobileElement? = null

    @AndroidFindBy(xpath = "//android.widget.LinearLayout[@index='0']/android.view.ViewGroup[@index='0']/android.widget.ImageButton[@index='0']")
    var bbchindi_Moresettings: MobileElement? = null

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='स्थानीय समाचार']")
    var bbchindi_localnews: MobileElement? = null

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='टॉपिक्स']")
    var bbchindi_topics: MobileElement? = null

    @AndroidFindBy(accessibility = "Button: टॉपिक्स , collapse group")
    var bbchindi_topics_collapsegroup: MobileElement? = null

    @AndroidFindBy(accessibility = "Button: स्थानीय समाचार , collapse group")
    var bbchindi_localnews_collapsegroup: MobileElement? = null

    @AndroidFindBy(accessibility = "Button: होम पेज ")
    var hindihomepage: MobileElement? = null

    @AndroidFindBy(accessibility = "Button: भारत ")
    var hindibharath: MobileElement? = null

    @AndroidFindBy(accessibility = "Button: अंतरराष्ट्रीय ")
    var hindiinternatonal: MobileElement? = null

    @AndroidFindBy(accessibility = "Button: मनोरंजन ")
    var hindienrairnment: MobileElement? = null

    @AndroidFindBy(accessibility = "Button: खेल ")
    var hindisports: MobileElement? = null

    @AndroidFindBy(accessibility = "Button: विज्ञान-टेक्नॉलॉजी ")
    var hindiscience: MobileElement? = null

    @AndroidFindBy(accessibility = "Button: सोशल ")
    var hindisocial: MobileElement? = null

    @AndroidFindBy(accessibility = "Button: देखिए ")
    var hindilookat: MobileElement? = null

    @AndroidFindBy(accessibility = "Button: तस्वीरें ")
    var hindiphotos: MobileElement? = null

    @AndroidFindBy(xpath = "//android.widget.Button[@index=1]")
    var bbchindi_okbutton: MobileElement? = null

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='UK Politics added to My News']")
    //   @AndroidFindBy(id="bbc.mobile.news.uk.internal:id/snackbar_text")
    var alert_text: MobileElement? = null

    //@AndroidFindBy(id="bbc.mobile.news.uk.internal:id/snackbar_action")
    @AndroidFindBy(xpath = "//android.widget.Button[@text='UNDO' and @index='1']")
    var undo_button: MobileElement? = null

    var alert_text_uk = "UK Politics added to My News"

    var alert_text_business = "Business added to My News"




}
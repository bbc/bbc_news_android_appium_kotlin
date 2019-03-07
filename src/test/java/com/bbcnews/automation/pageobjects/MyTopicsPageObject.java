package com.bbcnews.automation.pageobjects;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class MyTopicsPageObject {


    public MyTopicsPageObject()
    {

    }

    @AndroidFindBy(accessibility = "Button: Add England to My News")
    public MobileElement englandtopic;

    @AndroidFindBy(accessibility = "Button: Add Africa to My News")
    public MobileElement africatopic;

    @AndroidFindBy(accessibility = "Button: Add European Union to My News")
    public MobileElement europeuniontopic;

    @AndroidFindBy(accessibility = "Button: Add Mortgages to My News")
    public MobileElement mortgagestopic;

    @AndroidFindBy(accessibility = "Button: Add YouTube to My News")
    public MobileElement youtubetopic;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Allow location']")
    public MobileElement allow_location;

    @AndroidFindBy(id="com.android.packageinstaller:id/permission_allow_button")
    public MobileElement allowlocation_premission;

    @AndroidFindBy(id="com.android.packageinstaller:id/permission_deny_button")
    public MobileElement allowlocation_permission_deny;

    @AndroidFindBy(id = "bbc.mobile.news.uk.internal:id/request_permission")
    public MobileElement location_button;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Africa']")
    public MobileElement Africatopic;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='England']")
    public MobileElement Englandtopic;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Wales']")
    public MobileElement Walestopic;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='European Union']")
    public MobileElement Europeantopic;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Mortgages']")
    public MobileElement Mortgagestopic;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='YouTube']")
    public MobileElement Youtubetopic;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Education']")
    public MobileElement educationtopic;



}

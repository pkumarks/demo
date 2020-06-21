package pageObjectDemo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class FormPage {
	
	public FormPage(AppiumDriver driver) {
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	@AndroidFindBy(id="com.androidsample.generalstore:id/nameField")
	public WebElement nameField;
	
	@AndroidFindBy(id="com.androidsample.generalstore:id/radioFemale")
	public WebElement radioBtn;
	
	@AndroidFindBy(id="android:id/text1")
	public WebElement dropdown;
	
	@AndroidFindBy(xpath="//*[@text='Argentina']")
	//We can use this way by creating methods so that we can add more logs in to TC
	private WebElement country;
	
	public WebElement getCountry() {
		System.out.println("Selecting Country");
		return country;
	}
	
	@AndroidFindBy(id="com.androidsample.generalstore:id/btnLetsShop")
	public WebElement ShopBtn;

}

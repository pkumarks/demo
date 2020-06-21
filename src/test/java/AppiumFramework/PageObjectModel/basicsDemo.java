package AppiumFramework.PageObjectModel;

import java.io.IOException;

import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import pageObjectDemo.HomePageDemo;
import pageObjectDemo.PreferencesDemo;

public class basicsDemo extends Base {
	@Test(dataProvider="InputData",dataProviderClass=TestData.class)
	//public void apiDemo() throws IOException, InterruptedException {
public void apiDemo(String input) throws IOException, InterruptedException {
		service = startServer();//Starting server
		AndroidDriver<AndroidElement> driver = Capabilities("apiDemo");
		HomePageDemo h = new HomePageDemo(driver);
		h.Preferences.click();
		PreferencesDemo p = new PreferencesDemo(driver);
		p.Dependencies.click();
		p.cb.click();
		p.settings.click();
		p.text.sendKeys(input);
		
		p.buttons.get(1).click();
		
		service.stop();
	 }
	
	

}

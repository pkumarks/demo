package AppiumFramework.PageObjectModel;

import static io.appium.java_client.touch.LongPressOptions.longPressOptions;
import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static java.time.Duration.ofSeconds;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import pageObjectDemo.CheckOutPage;
import pageObjectDemo.FormPage;
import pageObjectDemo.Products;

public class TC_5 extends Base {

	@Test
	public void test() throws IOException, InterruptedException {
		service = startServer();// Starting server
		AndroidDriver<AndroidElement> driver = Capabilities("GeneralStoreApp");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		FormPage form = new FormPage(driver);
		form.nameField.sendKeys("hello");
		driver.hideKeyboard();
		form.radioBtn.click();
		form.dropdown.click();
		Utilities u = new Utilities(driver);
		u.scrollToText("Argentina");
		form.getCountry().click();
		form.ShopBtn.click();

		Products p = new Products(driver);
		p.item.get(0).click();
		p.item.get(0).click();
		p.cart.click();

		Thread.sleep(4000);
		int count = driver.findElements(By.id("com.androidsample.generalstore:id/productPrice")).size();
		// int count = cp.productList.size();
		double sum = 0;
		CheckOutPage cp = new CheckOutPage(driver);
		for (int i = 0; i < count; i++) {
			String amount1 = cp.productList.get(i).getText();
			double amount = getAmount(amount1);
			sum = sum + amount;
		}

		System.out.println(sum + " sum of products");

		String totalAmount = cp.totalAmount.getText();
		totalAmount = totalAmount.substring(1);
		double totalAmountvalue = Double.parseDouble(totalAmount);
		System.out.println(totalAmountvalue + " Total value of products");

		Assert.assertEquals(sum, totalAmountvalue);

		WebElement checkbox = driver.findElement(By.className("android.widget.CheckBox"));
		TouchAction t = new TouchAction(driver);
		t.tap(tapOptions().withElement(element(checkbox))).perform();

		WebElement tc = driver.findElement(By.xpath("//*[@text='Please read our terms of conditions']"));
		t.longPress(longPressOptions().withElement(element(tc)).withDuration(ofSeconds(2))).release().perform();
		driver.findElement(By.id("android:id/button1")).click();
		driver.findElement(By.id("com.androidsample.generalstore:id/btnProceed")).click();
		service.stop();

	}

	public static double getAmount(String value) {
		value = value.substring(1);
		double amount1value = Double.parseDouble(value);
		return amount1value;

	}

	@BeforeTest
	public void killAllNodes() throws IOException, InterruptedException {
		Runtime.getRuntime().exec("taskkill /F /IM node.exe");
		Thread.sleep(3000);
	}

}

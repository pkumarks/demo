package AppiumFramework.PageObjectModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;

public class Base {
	public static AppiumDriverLocalService service;
	public static AndroidDriver<AndroidElement> driver;

	// To start server
	public AppiumDriverLocalService startServer() {
		boolean flag = checkIfServerIsRunning(4723);
		if (!flag) {
			service = AppiumDriverLocalService.buildDefaultService();
			service.start();
		}
		return service;
	}

	public static boolean checkIfServerIsRunning(int port) {
		boolean isServerRunning = false;
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(port);
			serverSocket.close();
		} catch (IOException e) {
			isServerRunning = true;
		} finally {
			serverSocket = null;
		}
		return isServerRunning;
	}

	// To start emulator with out manual interaction

	public static void startEmulator() throws IOException, InterruptedException {
		Runtime.getRuntime().exec(System.getProperty("user.dir") + "\\src\\main\\java\\resources\\startEmulator.bat");
		Thread.sleep(6000);
	}

	public static AndroidDriver<AndroidElement> Capabilities(String appName) throws IOException, InterruptedException {

		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")
				+ "\\src\\main\\java\\AppiumFramework\\PageObjectModel\\global.properties");
		Properties prop = new Properties();
		prop.load(fis);
		String TestApp = (String) prop.get(appName);
		File appDir = new File("src");
		File app = new File(appDir, TestApp);

		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
		//String device = (String) prop.get("device");
		String device=System.getProperty("deviceName"); //to call device from mvn test command line globally

		if (device.contains("Emulator")) {
			startEmulator();
		}

		cap.setCapability(MobileCapabilityType.DEVICE_NAME, device);
		
		cap.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
		/*
		 * String udid = (String) prop.get("udid");
		 * cap.setCapability(MobileCapabilityType.UDID, udid);
		 */
		String appPackage = (String) prop.get("appPackage");
		cap.setCapability("appPackage", appPackage);
		String appActivity = (String) prop.get("appActivity");
		cap.setCapability("appActivity", appActivity);

		URL url = new URL("http://127.0.0.1:4723/wd/hub");
		AndroidDriver<AndroidElement> driver = new AndroidDriver<AndroidElement>(url, cap);
		//driver.pressKey(new KeyEvent(AndroidKey.HOME));
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;

	}
	
	public static void getScreenshot(String s) throws IOException {
		File srcfile  = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(srcfile, new File(System.getProperty("user.dir")+"\\"+s+".png"));
	}

}

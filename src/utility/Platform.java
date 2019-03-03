package utility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Assert;
import org.testng.Reporter;

public class Platform {

	public static WebDriver driver;
	private static String USER_DIRECTORY = System.getProperty("user.dir");

	public static void initializeDriver(String webBrowser) {

		switch (webBrowser) {
		case "chrome":
			// Set path of chromedriver
			System.setProperty("webdriver.chrome.driver", USER_DIRECTORY + "/chromedriver");

			ChromeOptions cOptions = new ChromeOptions(); // options to disable chrome info-bars
			cOptions.addArguments("disable-infobars");

			driver = new ChromeDriver(cOptions); // initialize driver
			break;
		case "firefox":
			// Set path of geckodriver
			System.setProperty("webdriver.gecko.driver", USER_DIRECTORY + "/geckodriver");

			FirefoxOptions fOptions = new FirefoxOptions(); // initialize FirefoxOptions
			fOptions.addArguments("marionette");

			driver = new FirefoxDriver(); // initialize driver
			break;
		case "opera":
			// Set path of operadriver
			System.setProperty("webdriver.opera.driver", USER_DIRECTORY + "/operadriver/operadriver");
			driver = new OperaDriver(); // initialize driver
			break;
		case "safari":
			driver = new SafariDriver(); // initialize driver
			break;
		default:
			Assert.fail(
					"Driver Initializing fail: Please enter one of the webBrowser value: safari, chrome, opera, firefox, edge");
		}
	}

	public static WebDriver getDriver() {
		if (driver != null) {
			return driver;
		} else {
			System.out.println("Driver not initialised");
			Reporter.log("Driver not initialised");
			return driver;
		}
	}

}

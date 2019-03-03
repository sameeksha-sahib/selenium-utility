package testScripts;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import utility.Platform;

public class BaseClass {

	public WebDriver driver;
	public WebDriverWait wait; 

	
	@BeforeSuite
	@Parameters(value = {"webBrowser", "webURL"})
	public void setUp(String webBrowser, String webURL) throws Exception {
		
		// initialize driver
		Platform.initializeDriver(webBrowser);
		driver = Platform.getDriver();
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 10);
		 
		 
		// Maximize browser and go to required URL
		driver.manage().window().maximize();
		driver.get(webURL);

	}
	
	@AfterSuite
	public void tearDown() throws Exception {
		driver.quit();
	}
}

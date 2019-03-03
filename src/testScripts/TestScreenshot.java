package testScripts;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import utility.Screenshot;

public class TestScreenshot extends BaseClass {

	@Test(priority = 0)
	public void testPass() throws Exception {
		System.out.println("In testPass");
		
		WebElement searchTextBox = driver.findElement(By.cssSelector(".gLFyf.gsfi"));
		
		File before = Screenshot.getScreenshotOfElement(driver, searchTextBox, "Search_Text_Box_Before");
		
		searchTextBox.sendKeys("Passed Test");
		
		File after = Screenshot.getScreenshotOfElement(driver, searchTextBox, "Search_Text_Box_After");
		
		Assert.assertFalse(Screenshot.isImageSame(before, after), "Send Key failed");
	}
		
	@Test (priority = 2)
	public void testFail() {
		System.out.println("In testFail");
		
		WebElement searchTextBox = driver.findElement(By.cssSelector(".gLFyf.gsfi"));
		searchTextBox.clear();
		searchTextBox.sendKeys("Failed Test");
		
		Assert.fail();
	}
	
	@Test(dependsOnMethods = { "testFail" })
	public void testSkip() {
		System.out.println("In testSkip");
	}
}

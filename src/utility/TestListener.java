package utility;

import java.io.File;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

public class TestListener implements ITestListener {

	@Override // Get screenshot before start of test
	public void onTestStart(ITestResult result) {
		try {
			Screenshot.getScreenshot(Platform.getDriver(), result.getName() + "_START");
		} catch (Exception e) {
			Reporter.log("listener onTestStart fail");
			e.printStackTrace();
		}

	}

	@Override // Log screenshot at test success
	public void onTestSuccess(ITestResult result) {
		File screenshot;
		try {
			screenshot = Screenshot.getScreenshot(Platform.getDriver(), result.getName() + "_SUCCESS");
			Reporter.log("Result status: " + result.getStatus());
			Screenshot.logScreenshot(screenshot);
		} catch (Exception e) {
			Reporter.log("listener onTestSuccess fail");
			e.printStackTrace();
		}

	}

	@Override // Log screenshot at test failure
	public void onTestFailure(ITestResult result) {
		File screenshot;
		try {
			screenshot = Screenshot.getScreenshot(Platform.getDriver(), result.getName() + "_FAILURE");
			Reporter.log("Result status: " + result.getStatus());
			Screenshot.logScreenshot(screenshot);
		} catch (Exception e) {
			Reporter.log("listener onTestFailure fail");
			e.printStackTrace();
		}

	}

	@Override // Log screenshot at test skip
	public void onTestSkipped(ITestResult result) {
		File screenshot;
		try {
			screenshot = Screenshot.getScreenshot(Platform.getDriver(), result.getName() + "_SKIP");
			Reporter.log("Result status: " + result.getStatus());
			Screenshot.logScreenshot(screenshot);
		} catch (Exception e) {
			Reporter.log("listener onTestSkipped fail");
			e.printStackTrace();
		}

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
	}

	@Override // Print browser name in console on start
	public void onStart(ITestContext context) {
		WebDriver driver = Platform.getDriver();
		Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
		System.out.println("browser: " + caps.getBrowserName());
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub

	}

}

package gestures;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

//import io.appium.java_client.MobileDriver;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.offset.PointOption;

public class AndroidGesture {

	// Method to perform Tap gesture on given element
	@SuppressWarnings("rawtypes")
	public static void tap(AndroidDriver driver, Point frameLoc, WebElement element) {
		// Get element location and size
		Point elementLoc = element.getLocation();
		Dimension elementSize = element.getSize();

		// Get element center
		Point center = getPosition(driver, elementLoc, elementSize, frameLoc, "CENTER");

		driver.context("NATIVE_APP");

		// Perform tap gesture
		TouchAction action = new TouchAction(driver);
		action.tap(PointOption.point(center)).perform();

		driver.context("CHROMIUM");

	}

	// Method to perform Double Tap gesture on given element
	@SuppressWarnings("rawtypes")
	public static void doubleTap(AndroidDriver driver, Point frameLoc, WebElement element) {
		// Get element location and size
		Point elementLoc = element.getLocation();
		Dimension elementSize = element.getSize();

		// Get element center
		Point center = getPosition(driver, elementLoc, elementSize, frameLoc, "CENTER");

		driver.context("NATIVE_APP");

		// Perform tap gesture
		TouchAction action = new TouchAction(driver);
		action.tap(PointOption.point(center)).waitAction().tap(PointOption.point(center)).perform();

		driver.context("CHROMIUM");
	}

	// Method to perform Swipe gesture on given element
	@SuppressWarnings("rawtypes")
	public static void swipe(AndroidDriver driver, Point frameLoc, WebElement element, String direction) {
		// Get element location and size
		Point elementLoc = element.getLocation();
		Dimension elementSize = element.getSize();

		// Get element center as start location
		Point start = getPosition(driver, elementLoc, elementSize, frameLoc, "CENTER");

		Point end = null;
		TouchAction action = new TouchAction(driver);

		// Based on direction, get left or right as end location
		switch (direction) {
		case "LEFT":
			end = getPosition(driver, elementLoc, elementSize, frameLoc, "LEFT");
			break;
		case "RIGHT":
			end = getPosition(driver, elementLoc, elementSize, frameLoc, "RIGHT");
			break;
		default:
			Assert.fail("Inavlid Direction: Please mention either LEFT or RIGHT");
		}

		// perform swipe gesture
		driver.context("NATIVE_APP");
		action.press(PointOption.point(start)).moveTo(PointOption.point(end)).release().perform();
		driver.context("CHROMIUM");
	}

	// Method to perform Swipe gesture on screen
	@SuppressWarnings("rawtypes")
	public static void swipe(AndroidDriver driver, String direction) {
		// Switch to default content
		driver.switchTo().defaultContent();

		// Get DPR
		JavascriptExecutor js = (JavascriptExecutor) driver;
		long dpr = (long) js.executeScript("return (window.devicePixelRatio)");

		// Get screen inner size
		long screenInnerWidth = (long) js.executeScript("return (window.innerWidth)");
		long screenInnerHeight = (long) js.executeScript("return (window.innerHeight)");

		// Calculate left and right mid of screen
		int y = (int) ((screenInnerHeight / 2) * dpr);
		int rightX = (int) ((screenInnerWidth - 20) * dpr);
		int leftX = (int) (20 * dpr);

		driver.context("NATIVE_APP");

		TouchAction action = new TouchAction(driver);

		switch (direction) {
		case "LEFT":
			action.press(PointOption.point(rightX, y)).moveTo(PointOption.point(leftX, y)).release().perform();
			break;
		case "RIGHT":
			action.press(PointOption.point(leftX, y)).moveTo(PointOption.point(rightX, y)).release().perform();
			break;
		default:
			Assert.fail("Inavlid Direction: Please mention either LEFT or RIGHT");
		}
		driver.context("CHROMIUM");
	}

	// Method to perform Zoom Out gesture on given element
	@SuppressWarnings("rawtypes")
	public static void zoomOut(AndroidDriver driver, WebElement element, Point frameLoc) {
		// Get element location and size
		Point elementLoc = element.getLocation();
		Dimension elementSize = element.getSize();

		// Get center, left and right location
		Point center = getPosition(driver, elementLoc, elementSize, frameLoc, "CENTER");
		Point left = getPosition(driver, elementLoc, elementSize, frameLoc, "LEFT");
		Point right = getPosition(driver, elementLoc, elementSize, frameLoc, "RIGHT");

		driver.context("NATIVE_APP");

		// Create two TouchActions to swipe to left and right direction from center
		TouchAction action1 = new TouchAction(driver);
		TouchAction action2 = new TouchAction(driver);
		MultiTouchAction multiTouchAction = new MultiTouchAction(driver);

		action1.press(PointOption.point(center.getX() + 5, center.getY())).moveTo(PointOption.point(right)).release();
		action2.press(PointOption.point(center.getX() - 5, center.getY())).moveTo(PointOption.point(left)).release();

		// Perform zoom out gesture by performing simultaneous swipe to left and right
		// from center
		multiTouchAction.add(action1).add(action2).perform();

		driver.context("CHROMIUM");

	}

	// Method to perform Zoom In gesture on given element
	@SuppressWarnings("rawtypes")
	public static void zoomIn(AndroidDriver driver, WebElement element, Point frameLoc) {
		// Get element location and size
		Point elementLoc = element.getLocation();
		Dimension elementSize = element.getSize();

		// Get center, left and right location
		Point center = getPosition(driver, elementLoc, elementSize, frameLoc, "CENTER");
		Point left = getPosition(driver, elementLoc, elementSize, frameLoc, "LEFT");
		Point right = getPosition(driver, elementLoc, elementSize, frameLoc, "RIGHT");

		driver.context("NATIVE_APP");

		// Create two TouchActions to swipe from left and right direction to center
		TouchAction action1 = new TouchAction(driver);
		TouchAction action2 = new TouchAction(driver);
		MultiTouchAction multiTouchAction = new MultiTouchAction(driver);

		action1.press(PointOption.point(right)).moveTo(PointOption.point(center.getX() + 5, center.getY())).release();
		action2.press(PointOption.point(left)).moveTo(PointOption.point(center.getX() - 5, center.getY())).release();

		// Perform zoom in gesture by performing simultaneous swipe from left and right
		// to center
		multiTouchAction.add(action1).add(action2).perform();

		driver.context("CHROMIUM");

	}

	// Method to calculate Offset for convert CSS location to Physical location
	@SuppressWarnings("rawtypes")
	private static Point getOffset(AndroidDriver driver, Point frameLoc) {

		driver.switchTo().defaultContent();

		// Get screen height and screen inner height
		JavascriptExecutor js = (JavascriptExecutor) driver;
		long screenHeight = (long) js.executeScript("return (window.screen.height)");
		long screenInnerHeight = (long) js.executeScript("return (window.innerHeight)");

		// Calculate offset
		int offsetX = frameLoc.getX();
		int offsetY = (int) (screenHeight - screenInnerHeight + frameLoc.getY());

		// return offset
		return new Point(offsetX, offsetY);
	}

	// Method to calculate coordinates of required position
	@SuppressWarnings("rawtypes")
	private static Point getPosition(AndroidDriver driver, Point elementLoc, Dimension elementSize, Point frameLoc,
			String position) {
		driver.switchTo().defaultContent();

		// Calculate height of navigation bar of device
		int winH = driver.manage().window().getSize().getHeight();
		int navH = (int) 7.5 * winH / 100;

		// TODO: To know if navigation bar is hidden or not. If hidden navH should be 0
		// and not 7.5% of device height

		// Get DPR
		JavascriptExecutor js = (JavascriptExecutor) driver;
		long dpr = (long) js.executeScript("return (window.devicePixelRatio)");

		// Get screen inner size
		long screenInnerWidth = (long) js.executeScript("return (window.innerWidth)");
		long screenInnerHeight = (long) js.executeScript("return (window.innerHeight)");

		// Get offset
		Point offset = getOffset(driver, frameLoc);

		// Calculate physical location of element
		int physicalX = (int) ((elementLoc.getX() + offset.getX()) * dpr);
		int physicalY = (int) ((elementLoc.getY() + offset.getY()) * dpr - navH);

		// Calculate x coordinate of required position
		int x = 0;
		switch (position) {
		case "CENTER":
			x = (int) (physicalX + dpr * elementSize.getWidth() / 2);
			if (Math.abs(x) > screenInnerWidth)
				x = (int) (dpr * screenInnerWidth / 2);
			break;

		case "LEFT":
			x = (int) (physicalX + dpr * 10);
			if (Math.abs(x) > screenInnerWidth)
				x = (int) (dpr * 20);
			break;

		case "RIGHT":
			x = (int) (physicalX + dpr * (elementSize.getWidth() - 10));
			if (Math.abs(x) > screenInnerWidth)
				x = (int) (dpr * (screenInnerWidth - 20));
			break;
		default:
			Assert.fail("Invalid position: Please mention LEFT, RIGHT or CENTER");

		}

		// Calculate y coordinate of required position
		int y = (int) (physicalY + dpr * elementSize.getHeight() / 2);
		if (Math.abs(y) > screenInnerHeight)
			y = (int) (dpr * screenInnerHeight / 2);

		// Return required position
		return new Point(x, y);
	}
}

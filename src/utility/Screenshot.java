package utility;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

public class Screenshot {

	private static String SCREENSHOT_FOLDER_PATH = System.getProperty("user.dir") + "/Screenshots/";

	// Method to take screenshot of element
	public static File getScreenshotOfElement(WebDriver driver, WebElement element, String fileName) throws Exception {

		// Get DPR
		JavascriptExecutor js = (JavascriptExecutor) driver;
		long dpr = (long) js.executeScript("return (window.devicePixelRatio)");

		// Calculate physical size of the element to be cropped
		Dimension eleSize = element.getSize();
		int imageWidth = (int) (eleSize.getWidth() * dpr);
		int imageHeight = (int) (eleSize.getHeight() * dpr);
		Dimension physicalSize = new Dimension(imageWidth, imageHeight);

		// Calculate physical location of element to be cropped
		Point eleLoc = element.getLocation();
		int imageX = (int) (eleLoc.getX() * dpr);
		int imageY = (int) (eleLoc.getY() * dpr);
		Point physicalLoc = new Point(imageX, imageY);

		// Get entire page screenshot
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		// Return cropped screenshot with time stamp in name
		return cropImage(screenshot, physicalLoc, physicalSize, fileName);

	}

	// Method to take screenshot of element in frame
	public static File getScreenshotOfElementInFrame(WebDriver driver, WebElement frame, WebElement element,
			String fileName) throws Exception {
		// Switch to frame and get location and size of element
		driver.switchTo().frame(frame);

		Point eleLoc = element.getLocation();
		Dimension eleSize = element.getSize();

		driver.switchTo().defaultContent();

		// Get DPR of device screen
		JavascriptExecutor js = (JavascriptExecutor) driver;
		long dpr = (long) js.executeScript("return (window.devicePixelRatio)");

		// Calculate physical size of the element to be cropped
		int imageWidth = (int) (eleSize.getWidth() * dpr);
		int imageHeight = (int) (eleSize.getHeight() * dpr);
		Dimension physicalSize = new Dimension(imageWidth, imageHeight);

		// Calculate offset for location of element to be cropped
		Point framePosition = frame.getLocation();

		long offsetX = framePosition.getX() + 10;
		long offsetY = framePosition.getY() + 10;

		// Calculate physical location of the element to be cropped
		int imageX = (int) ((eleLoc.getX() + offsetX) * dpr);
		int imageY = (int) ((eleLoc.getY() + offsetY) * dpr);
		Point physicalLoc = new Point(imageX, imageY);

		// Get entire page screenshot
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		// Return cropped screenshot with time stamp in name
		return cropImage(screenshot, physicalLoc, physicalSize, fileName);

	}

	// Method to crop image
	private static File cropImage(File image, Point location, Dimension size, String fileName) throws Exception {

		BufferedImage fullImage = ImageIO.read(image);

		// Crop the screenshot to given size and location
		BufferedImage croppedImg = fullImage.getSubimage(location.getX(), location.getY(), size.getWidth(),
				size.getHeight());
		ImageIO.write(croppedImg, "png", image);

		// Save screenshot with time stamp in name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

		File croppedImage = new File(SCREENSHOT_FOLDER_PATH + fileName + "_" + timeStamp + ".png");
		FileUtils.copyFile(image, croppedImage);

		return croppedImage;
	}

	// Method to get screenshot of page
	public static File getScreenshot(WebDriver driver, String fileName) throws Exception {

		// Get entire page screenshot
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		// Save screenshot with time stamp in name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

		File screenshotName = new File(SCREENSHOT_FOLDER_PATH + fileName + "_" + timeStamp + ".png");
		FileUtils.copyFile(screenshot, screenshotName);

		return screenshotName;
	}

	// Method to compare image
	public static boolean isImageSame(File image1, File image2) throws Exception {

		// Get data of image1
		BufferedImage bufferImage1 = ImageIO.read(image1);
		DataBuffer dataImage1 = bufferImage1.getData().getDataBuffer();
		int sizefileInput = dataImage1.getSize();

		// Get data of image2
		BufferedImage bufferImage2 = ImageIO.read(image2);
		DataBuffer dataImage2 = bufferImage2.getData().getDataBuffer();
		int sizefileOutPut = dataImage2.getSize();

		// Compare data of image1 and image2
		boolean matchFlag = true;
		if (sizefileInput == sizefileOutPut) {
			for (int i = 0; i < sizefileInput; i++) {
				if (dataImage1.getElem(i) != dataImage2.getElem(i)) {
					return matchFlag = false;
				}
			}
		} else {
			return matchFlag = false;
		}
		return matchFlag;
	}

	public static void logScreenshot(File screenshot) throws Exception {
		// Get path of screenshot and log it to TestNG report
		String filePath = screenshot.toString();
		Reporter.log("<a href='" + filePath + "'> <img src='" + filePath + "' height='100' width='100'/> </a>");
	}
}

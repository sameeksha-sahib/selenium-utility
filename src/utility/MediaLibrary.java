package utility;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

public class MediaLibrary {

	public static void playMedia(WebDriver driver, String media, int index) throws Exception {

		// Focus browser to handle promise rejection
		focusBrowser(driver);

		// Wait for buffering if media is video
		if (media.equals("video")) {
			waitForVideoBuffering(driver, index);
		}

		JavascriptExecutor js = (JavascriptExecutor) driver;

		// Get current time before playing media
		long mediaTimePre = (long) js
				.executeScript("return document.getElementsByTagName('" + media + "')[" + index + "].currentTime");

		// Get state of media
		boolean isPaused = (boolean) js
				.executeScript("return document.getElementsByTagName('" + media + "')[" + index + "].paused");

		// Play media if media is not already playing
		if (isPaused) {
			js.executeScript("document.getElementsByTagName('" + media + "')[" + index
					+ "].play().then(() => { console.log('play') })");
		} else {
			throw new Exception("In playMedia(): Media already playing");
		}

		// Wait for media to be ready to load all frames
		waitForMediaToBeReady(driver, index, media);

		// Wait for 1000 ms
		Thread.sleep(1000);

		// Get current time after playing media
		double mediaTimePost = (double) ((JavascriptExecutor) driver)
				.executeScript("return document.getElementsByTagName('" + media + "')[" + index + "].currentTime");

		// Assert that media is playing
		Assert.assertTrue(mediaTimePost > mediaTimePre);
	}

	public static void pauseMedia(WebDriver driver, String media, int index) throws Exception {

		// Focus browser to handle promise rejection
		focusBrowser(driver);

		JavascriptExecutor js = (JavascriptExecutor) driver;

		// Get state of media
		boolean isPaused = (boolean) js
				.executeScript("return document.getElementsByTagName('" + media + "')[" + index + "].paused");

		// Pause media if not already paused
		if (!isPaused) {
			js.executeScript("document.getElementsByTagName('" + media + "')[" + index + "].pause()");
		} else {
			throw new Exception("In pauseMedia(): Media already paused");
		}

		// Get current time after pause
		double mediaTimePre = (double) js
				.executeScript("return document.getElementsByTagName('" + media + "')[" + index + "].currentTime");

		// Wait for 1000 ms
		Thread.sleep(1000);

		// Again Get current time
		double mediaTimePost = (double) ((JavascriptExecutor) driver)
				.executeScript("return document.getElementsByTagName('" + media + "')[" + index + "].currentTime");

		// Assert if media is paused
		Assert.assertTrue(mediaTimePost == mediaTimePre);
	}

	private static void focusBrowser(WebDriver driver) {
		// Click on 0,0 to handle promise rejection thrown by browser on play()/pause()
		// I can't find any other work around
		// Please help if you find anything else
		Actions tap = new Actions(driver);
		tap.moveByOffset(0, 0).click().build().perform();
	}

	private static void waitForVideoBuffering(WebDriver driver, int index) throws Exception {

		JavascriptExecutor js = (JavascriptExecutor) driver;

		long readyState = 0;
		long startTime = System.currentTimeMillis();
		while (System.currentTimeMillis() - startTime < 20000) {
			readyState = (long) js
					.executeScript("return document.getElementsByTagName('video')[" + index + "].readyState");
			if (readyState > 0)
				return;
		}

		throw new Exception("In waitForVideoBuffering: Time Out (20 seconds) for video buffering state");
	}

	private static void waitForMediaToBeReady(WebDriver driver, int index, String media) throws Exception {
		JavascriptExecutor js = (JavascriptExecutor) driver;

		long readyState = 0;
		long startTime = System.currentTimeMillis();
		while (System.currentTimeMillis() - startTime < 20000) {
			readyState = (long) js
					.executeScript("return document.getElementsByTagName('" + media + "')[" + index + "].readyState");
			if (readyState > 2)
				return;
		}

		throw new Exception("In waitForMediaToBeReady: Time Out (20 seconds) for media ready state");
	}

}

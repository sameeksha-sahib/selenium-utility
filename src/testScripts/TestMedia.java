package testScripts;

import org.testng.annotations.Test;

import utility.MediaLibrary;

public class TestMedia extends BaseClass{

	@Test (priority = 0)
	public void testVideo() throws Exception {
		MediaLibrary.playMedia(driver, "video", 0);
		
		Thread.sleep(500);
		
		MediaLibrary.pauseMedia(driver, "video", 0);
	}
	
	@Test (priority = 1)
	public void testAudio() throws Exception {
		
		driver.get("http://www.russianlessons.net/audio/audio-test.php");
		
		MediaLibrary.playMedia(driver, "audio", 1);
		
		Thread.sleep(500);
		
		MediaLibrary.pauseMedia(driver, "audio", 1);
	}
}

#Sample selenium-utility project:

This is a java based sample project with examples to show how to integrate Screenshot with TestNG report, Excel IO, Media library, Android Gesture and coordinate mapper to map CSS coordinates to physical coordinates of android device.

Setup:
1. Do initial setup for selenium: https://www.guru99.com/installing-selenium-webdriver.html
2. Download source zip, extract it and open in eclipse.
3. Update the project through Maven update (Right click on project in eclipse --> Maven --> Update project)
4. If error of java binding occurs, right click project in eclipse --> properties --> Java compiler --> Check 'Use compliance from execution environment' option
5. Download chrome driver: http://chromedriver.chromium.org/downloads and keep the .exe in the project folder.
6. Download Gecko driver: https://github.com/mozilla/geckodriver/releases and keep the .exe in the project folder.
7. Download Opera driver: https://github.com/operasoftware/operachromiumdriver/releases and keep the opera driver folder in the project folder 

Note: On windows folder path contains "\" and on Mac folder path contains "/".
Therefore, change slashes according to our machine in files in folder utility: ExcelExportAndFileIO.java, Screenshot.java
Platform.java

8. Run .xml files as TestNG and see test output.

Happy testing :)

Some helpful links: 
Safari Bug: https://github.com/SeleniumHQ/selenium/issues/6637 Safari issue for getLocation()
Property readyState for media elements: https://developer.mozilla.org/en-US/docs/Web/API/HTMLMediaElement/readyState

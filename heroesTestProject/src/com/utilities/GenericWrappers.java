package com.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestResult;
import org.openqa.selenium.io.FileHandler;

import java.util.Date;
import java.util.Set;

import io.github.bonigarcia.wdm.WebDriverManager;

//Here we will store re-usable Functions/Methods
public class GenericWrappers extends BaseClass {

	/********* Chrome browser launch ****************/
	public void launchChromeBrowser() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}

	/********* Edge browser launch ****************/
	public void launchEdgeBrowser() {
		WebDriverManager.edgedriver().setup();
		driver = new EdgeDriver();
	}
	
	/***** Validate the web element is enabled or not? *****/
	public void ValidateWebelementIsEnabledOrNot(By locator) {
		WebElement ele = driver.findElement(locator);
		if (ele.isEnabled()) {
			System.out.println("The webelement is Enabled state***");
		} else {
			System.out.println("The webelement is NOT in Enabled state***");
		}
	}
	
	/** sendkeys by any locators for Editbox/Textbox *********/
	public void sendKeyByAnyLocator(By locator, String testdata) {
		WebElement ele = driver.findElement(locator);
		if (ele.isDisplayed()) {
			if (ele.isEnabled()) {
				ele.clear();
				ele.sendKeys(testdata);
			} else {
				System.out.println("element is disable state, please check the locator***");
			}
		} else {
			System.out.println("element is not displayed, please check the locator***");
		}
	}

	/** verify input box is clear *********/
	public void isInputBoxClear(By locator) {
		WebElement ele = driver.findElement(locator);
		if (ele.isDisplayed()) {
			if (ele.isEnabled()) {
				if(ele.getText() == "") {
					System.out.println("The input box is cleared***");
				};
			} else {
				System.out.println("element is disable state, please check the locator***");
			}
		} else {
			System.out.println("element is not displayed, please check the locator***");
		}
	}
	/************ click on any webelement *************/
	public void clickByAnylocator(By locator) {
		WebElement ele = driver.findElement(locator);
		if (ele.isDisplayed() && ele.isEnabled()) {
			ele.click();
		} else {
			System.out.println("The given locator is not displayed on DOM or not in enabled state****");
		}
	}

	/******** Implicit wait *****************/
	public void implicitWait(int TimeInMillySeconds) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TimeInMillySeconds));
	}

	/********* Read Data from Properties file *********************/

	// to get the test data from Property file
	public void loadTestData(String path) {
		file = new File(path);
		fi = null;
		try {
			fi = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			prop.load(fi);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getData(String key) {
		String keyvlaue = null;
		try {
			keyvlaue = prop.getProperty(key);
		} catch (Exception e) {
			System.out.println("Error description: " + e.getStackTrace());
		}
		return keyvlaue;
	}

	/**********
	 * Verify the web element has present or not on DOM (Document Object Model)
	 ***************/
	public void findTheWebelementByAnyLocator(By locator) {
		// Find the given locator, if the locator has present on Screen then the size of
		// locator is ONE '1'
		// else size of locator is ZERO '0'
		if (driver.findElements(locator).size() > 0) {
			System.out.println("The Given locator is present on DOM****"+locator);
		} else {
			System.out.println("The Given locator is NOT present on DOM, please check again****"+locator);
		}

	}
	
	/*** navigational methods************/
	public void refreshScreen() {
		driver.navigate().refresh();
		implicitWait(5);
	}
	
	public void navigateToPopupWindow() {
		String mainWindowName = driver.getWindowHandle();
		System.out.println("mainWindowName:" + mainWindowName);

		Set<String> allWindowNames = driver.getWindowHandles();// 4
		System.out.println("allWindowNames:" + allWindowNames);

		// Close the child window (popups)
		// for (int i = 0; i < array.length; i++) { }
		for (String string : allWindowNames) {
			// validate the window name is parent window /Child window?
			if (!mainWindowName.equals(string)) {
				// switch to child window
				driver.switchTo().window(string);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public void dragNDrop(By from, By to) {
		Actions action = new Actions (driver);
		WebElement dragFrom = driver.findElement(from);
		WebElement dragTo = driver.findElement(to);
		action.dragAndDrop(dragFrom,dragTo).build().perform();
	}
	
	public String timestamp() {
		Date d = new Date();
		DateFormat df = new SimpleDateFormat("ddMMMyyy_HHmmss");
		String timeTamp = df.format(d);
		return timeTamp;
	}
	
	public void takeScreenshot() throws Exception {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String screenshotPath = ".//Screenshots//";
		FileHandler.copy(scrFile, new File(screenshotPath + "Test" + timestamp() + ".PNG"));
		System.out.println("Screenshot taken*** ");
	}

	public void takeScreenshot(ITestResult res) throws Exception {

		String projectDir = System.getProperty("user.dir");
		// code to change to work on mac system
		// screenshotPath = projectDir + "\\Screenshots\\";

		String screenshotPath = projectDir + "//Screenshots//";
		String className = res.getTestClass().getName().trim();//
		String methodName = res.getName().trim();//
		// STATUS_PackageName.ClassName_MethodName_Timestamp.PNG
		if (res.getStatus() == ITestResult.SUCCESS) {
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileHandler.copy(scrFile, new File(screenshotPath + "PASS_" + className + "_" + methodName + "_" + timestamp() + ".PNG"));
		}
		if (res.getStatus() == ITestResult.FAILURE) {
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileHandler.copy(scrFile,
					new File(screenshotPath + "FAIL_" + className + "_" + methodName + "_" + timestamp() + ".PNG"));
		}

	}
}
	
	
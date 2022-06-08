package testSuites;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.utilities.GenericWrappers;

public class ts_01HeroAdd extends GenericWrappers{
	
	@Test
	public void test_init() throws IOException {	
		launchChromeBrowser();
		driver.get("https://stackblitz.com/edit/angular-ljbece-9taez9");
		try {
			Thread.sleep(40000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebElement iframe = driver.findElement(By.className("Preview2-module-iframe-USYsD"));
		driver.switchTo().frame(iframe);
	}
	
	@Test
	public void tc_002() throws IOException {	
		sendKeyByAnyLocator(By.xpath("/html/body/app-root/app-heroes/div/input"), "Test Hero");
		clickByAnylocator(By.xpath("/html/body/app-root/app-heroes/div/button[1]"));
		implicitWait(1000);
		assert driver.findElement(By.xpath("//span[.='Test Hero']")).isDisplayed();
	}
	
	@Test
	public void tc_003() throws IOException {	
		sendKeyByAnyLocator(By.xpath("/html/body/app-root/app-heroes/div/input"), "");
		clickByAnylocator(By.xpath("/html/body/app-root/app-heroes/div/button[1]"));
		implicitWait(1000);
		Boolean isPresent = driver.findElements(By.xpath("//span[.='']")).size() > 0;
		assert isPresent == false;
		driver.switchTo().defaultContent();
		driver.close();
	}
}
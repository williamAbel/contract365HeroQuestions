package testSuites;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import com.utilities.GenericWrappers;

public class ts_02HeroSearch extends GenericWrappers{
	
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
		Boolean isPresent = driver.findElements(By.xpath("//span[.='Dr Nice']")).size() > 0;
		assert isPresent == true;
	}
	
	@Test
	public void tc_003() throws IOException {
		Boolean isPresent = driver.findElements(By.xpath("//span[.='invalid hero']")).size() > 0;
		assert isPresent == false;
		driver.switchTo().defaultContent();
		driver.quit();
	}
}
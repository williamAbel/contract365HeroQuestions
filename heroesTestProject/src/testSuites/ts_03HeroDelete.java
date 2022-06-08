package testSuites;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import com.utilities.GenericWrappers;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ts_03HeroDelete extends GenericWrappers{
	
	@Test
	public void test_init() throws IOException {	
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
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
		clickByAnylocator(By.xpath("/html/body/app-root/app-heroes/ul/li[1]/button[2]"));
		implicitWait(1000);
		Boolean isPresent = driver.findElements(By.xpath("//span[.='Dr, Nice']")).size() > 0;
		assert isPresent == false;
	}
}
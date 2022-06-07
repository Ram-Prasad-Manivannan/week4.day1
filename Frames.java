package week4.day1.assignment;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Frames 
{
 public static void main(String[] args) 
 {
		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver();
		
        driver.get("https://chercher.tech/practice/frames-example-selenium-webdriver");
		
        driver.manage().window().maximize();
		
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		driver.switchTo().frame("frame1");
		
		driver.findElement(By.xpath("//b[@id='topic']/following::input")).sendKeys("Selenium");
		
		driver.switchTo().frame("frame3");
		
		driver.findElement(By.xpath("//b[contains(text(),'Check')]/following::input")).click();
		
		driver.switchTo().defaultContent();
		driver.switchTo().frame("frame2");
		
		WebElement eleAnimalDrop = driver.findElement(By.id("animals"));
		Select selAnimal = new Select(eleAnimalDrop);
		selAnimal.selectByVisibleText("Baby Cat");
		
		driver.close();
	}

	
}


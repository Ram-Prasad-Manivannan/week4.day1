package week4.day1.assignment;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class MergeContact 
{
	public ChromeDriver setUpDriver() 
	{
		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver();
		return driver;
	}

	public void switchToNewWindow(ChromeDriver driver) 
	{
		Set<String> windowHandles = driver.getWindowHandles();
		List<String> winList = new ArrayList<String>(windowHandles);
		driver.switchTo().window(winList.get(1));
	}

	public void switchToParentWindow(ChromeDriver driver, String parentWindow) 
	{
		driver.switchTo().window(parentWindow);
	}

	public void startApp(ChromeDriver driver) 
	{
		driver.get("http://leaftaps.com/opentaps/control/login");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		
		driver.findElement(By.id("username")).sendKeys("DemoSalesManager");
		
		driver.findElement(By.id("password")).sendKeys("crmsfa");
		
		driver.findElement(By.className("decorativeSubmit")).click();
		
		driver.findElement(By.linkText("CRM/SFA")).click();
		
		driver.findElement(By.linkText("Contacts")).click();
		
		driver.findElement(By.xpath("//a[text()='Merge Contacts']")).click();
		
		String parentWindow = driver.getWindowHandle();
		
		driver.findElement(By.xpath("//input[@id='partyIdFrom']/following::img[@alt='Lookup']")).click();
		switchToNewWindow(driver);
		
		driver.findElement(By.xpath("//div[@class='x-grid3-cell-inner x-grid3-col-partyId']/a")).click();
		switchToParentWindow(driver, parentWindow);
		
		driver.findElement(By.xpath("(//input[@id='partyIdFrom']/following::img[@alt='Lookup'])[2]")).click();
		switchToNewWindow(driver);
		
		driver.findElement(By.xpath("(//div[@class='x-grid3-cell-inner x-grid3-col-partyId']/a)[2]")).click();
		switchToParentWindow(driver, parentWindow);
		
		driver.findElement(By.xpath("//a[text()='Merge']")).click();
		
		Alert alert = driver.switchTo().alert();
		alert.accept();
		System.out.println("Title : "+driver.getTitle());

		driver.close();
	}

	public static void main(String[] args) 
	{
		MergeContact merge = new MergeContact();
		merge.startApp(merge.setUpDriver());
	}
}
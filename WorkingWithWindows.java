package week4.day1.assignment;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WorkingWithWindows 
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
		getWindowCount(winList);
	}

	public void getWindowCount(List<String> winList) 
	{
		System.out.println("Number of opened windows : " + winList.size());
	}

	public void switchToParentWindow(ChromeDriver driver, String parentWindow) 
	{
		driver.close();
		driver.switchTo().window(parentWindow);
	}

	public void getPageTitle(ChromeDriver driver) 
	{
		System.out.println("Title of the page : " + driver.getTitle());
	}

	public void closeAllExceptParent(ChromeDriver driver, String parentWinTitle, String parentWindow) 
	{
		Set<String> windowHandles = driver.getWindowHandles();
		List<String> winList = new ArrayList<String>(windowHandles);
		int openWinInd = 0;
		for (int i = 0; i < winList.size(); i++) {
			driver.switchTo().window(winList.get(i));
			if (driver.getTitle().equals(parentWinTitle))
				openWinInd = i;
			if (!(driver.getTitle().equals(parentWinTitle)))
				driver.close();
		}
		System.out.println(openWinInd);
		driver.switchTo().window(winList.get(openWinInd));
	}

	public void startApp(ChromeDriver driver) throws InterruptedException 
	{
		driver.get("http://www.leafground.com/pages/Window.html");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

		String parentWindow = driver.getWindowHandle();
		String parentWinTitle = driver.getTitle();
		driver.findElement(By.id("home")).click();
		switchToNewWindow(driver);
		getPageTitle(driver);
		switchToParentWindow(driver, parentWindow);

		driver.findElement(By.xpath("//button[text()='Open Multiple Windows']")).click();
		switchToNewWindow(driver);
		getPageTitle(driver);
		driver.findElement(By.partialLinkText("Home")).click();
		switchToNewWindow(driver);
		getPageTitle(driver);

		switchToParentWindow(driver, parentWindow);
		getPageTitle(driver);

		driver.findElement(By.xpath("//button[text()='Do not close me ']")).click();
		switchToNewWindow(driver);
		getPageTitle(driver);
		closeAllExceptParent(driver, parentWinTitle, parentWindow);

		getPageTitle(driver);

		driver.findElement(By.xpath("//button[text()='Wait for 5 seconds']")).click();
		Thread.sleep(5000);
		switchToNewWindow(driver);

		driver.quit();
	}

	public static void main(String[] args) throws InterruptedException 
	{
		WorkingWithWindows win = new WorkingWithWindows();
		win.startApp(win.setUpDriver());
	}

}
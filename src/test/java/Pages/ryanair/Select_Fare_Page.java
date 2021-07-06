package Pages.ryanair;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

/**
 * @author madan mohan reddy
 *
 */
public class Select_Fare_Page 
{
	// WebDriver will helps us to perform activities on the Web page 
	
		WebDriver driver;
	
	By duration = By.xpath("//div[@class = 'duration b2']");
	
	By regular123 = By.xpath("//span[@class = 'ng-star-inserted']");


	By mr = By.xpath("//button[@class = 'dropdown__toggle b2']");

	By mr_1 = By.xpath("//div[@class = 'dropdown-item__label b2']");
	
	By first_name = By.xpath("//input[@id='formState.passengers.ADT-0.name']");
	
	By last_name = By.xpath("//input[@id='formState.passengers.ADT-0.surname']");
	
	By last_continue = By.xpath("//button[normalize-space()='Continue']");
	
	public  Select_Fare_Page (WebDriver driver)
	{
		
		this.driver = driver;
		
		
	// calling this constructor called driver
		//constructor main task is to initialize the webDriver

	}
	//Parameterizing the void functions
	
	public void duration() throws InterruptedException
	{
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,-9000)");
		
		driver.findElement(duration).click();
		
		
	}
	
	public void regular123() throws InterruptedException
	{
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,-9000)");
		
		driver.findElement(regular123).click();
		
			
	}
	public void mr() throws InterruptedException
	{
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,-9000)");
		
		driver.findElement(mr).click();
		
			
	}
	
	public void mr_1() throws InterruptedException
	{
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,-9000)");
		
		driver.findElement(mr_1).click();
			
	}
	public void first_name() throws InterruptedException
	{
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,400)");
		driver.findElement(first_name).sendKeys("madan");
		Thread.sleep(1000);

	}
	public void last_name() throws InterruptedException
	{
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,400)");
		driver.findElement(last_name).sendKeys("reddy");
		Thread.sleep(1000);

	}
	
	public void last_continue() throws InterruptedException
	{
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,400)");
		driver.findElement(last_continue).click();
		Thread.sleep(3000);

	}

}

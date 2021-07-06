/**
 * 
 */
package Pages.ryanair;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;


/**
 * @author mada nmohan reddy
 *
 */
public class Destination_Page 
{
	// WebDriver will helps us to perform activities on the Web page 
	
		WebDriver driver;
	
		By desitination = By.xpath("//input[@id='input-button__destination']");
		
		By search = By.xpath("//button[@aria-label='Search']");
		
		
		By chooseDate = By.xpath("//div[@class='input-button__input ng-star-inserted']");
		
		By choose = By.xpath("//div[@class='calendar-body__cell calendar-body__cell--weekend'][normalize-space()='24']");
		
	
	public  Destination_Page (WebDriver driver)
	{
		
		this.driver = driver;
		
		
	// calling this constructor called driver
		//constructor main task is to initialize the webDriver

	}
	//Parameterizing the void functions
		
	public void desitination() throws InterruptedException
	{
	
		driver.findElement(desitination).click();
		driver.findElement(desitination).sendKeys("London Stansted");
		
			
	}
	
	public void search() throws InterruptedException
	{
		driver.findElement(search).click();
		Thread.sleep(1000);	
	}
	
	public void choosedate() throws InterruptedException
	{
		driver.findElement(chooseDate).click();
		Thread.sleep(1000);
			
	}
	
	public void choose() throws InterruptedException
	{
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,-400)");
		
		driver.findElement(choose).click();
		
		Thread.sleep(3000);
	}
	

}

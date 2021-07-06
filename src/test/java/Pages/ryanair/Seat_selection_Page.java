package Pages.ryanair;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

/**
 * @author madan mohan reddy
 *
 */

public class Seat_selection_Page 
{
	// WebDriver will helps us to perform activities on the Web page 
	
		WebDriver driver;
		
	
	By select_seat = By.xpath("//button[@id='seat-15A']");
	
	By seat_continue = By.xpath("//button[normalize-space()='Continue']");
	
	By exception = By.xpath("//button[normalize-space()='No, thanks']");
	
	By bagselection = By.xpath("//label[@for='ry-radio-button--0']");
	
	By exception_1 = By.xpath("//button[normalize-space()='Continue']");
		
	By exceptioncont = By.xpath("//button[normalize-space()='Continue']");
	
	
	public  Seat_selection_Page (WebDriver driver)
	{
		
		this.driver = driver;
		
		
	// calling this constructor called driver
		//constructor main task is to initialize the webDriver

	}
	//Parameterizing the void functions
		
	public void select_seat() throws InterruptedException
	{
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,400)");
		driver.findElement(select_seat).click();
		Thread.sleep(1000);

	}
	public void seat_continue() throws InterruptedException
	{
		
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,400)");
		driver.findElement(seat_continue).click();
		

	}
	
	public void exception() throws InterruptedException
	{
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,400)");
		driver.findElement(exception).click();
		

	}
	
	public void bagselection() throws InterruptedException
	{
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,400)");
		driver.findElement(bagselection).click();
		

	}
	
	public void exception_1() throws InterruptedException
	{
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,400)");
		driver.findElement(exception_1).click();
		Thread.sleep(2000);

	} 
	
	
	
	public void exceptioncont() throws InterruptedException
	{
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,900)");
		driver.findElement(exceptioncont).click();
		Thread.sleep(3000);

	}
	

}

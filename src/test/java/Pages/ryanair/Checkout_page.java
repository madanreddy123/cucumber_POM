/**
 * 
 */
package Pages.ryanair;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

/**
 * @author madanmohanreddy
 *
 */
public class Checkout_page
{
	// WebDriver will helps us to perform activities on the Web page 
	
   WebDriver driver;
   
	
	By checkout = By.xpath("/html/body/app-root/ry-spinner/trip-header-wrapper/header/ry-header/div/div[3]/div[2]/div/ng-component/ry-basket-total-container/ry-basket-total/div");
	
	By checkout_1  = By.xpath("//button[normalize-space()='Check out']");
	

	public  Checkout_page (WebDriver driver)
	{
		
		this.driver = driver;
		
		
	// calling this constructor called driver
		//constructor main task is to initialize the webDriver

	}
	//Parameterizing the void functions
	
	public void checkout() throws InterruptedException
	{
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,-9000)");
		driver.findElement(checkout).click();
	
		

	}
	
	public void checkout_1() throws InterruptedException
	{
		driver.findElement(checkout_1).click();
		
		Thread.sleep(3000);
	}
	
}

/**
 * 
 */
package Pages.ryanair;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * @author madan mohan reddy
 *
 */
public class Login_page 
{
	
// WebDriver will helps us to perform activities on the Web page 
	
	WebDriver driver;
	
	    By offerdialog = By.xpath("//button[normalize-space()='Yes, I agree']");
	
	 //By offerdialog = By.xpath("/html/body/div[1]/div/div[3]/button[2]");
	   
		By one_way = By.xpath("//icon[@class='trip-type__icon']");
		
		By login = By.xpath("//span[normalize-space()='Log in']");
		

		By username = By.xpath("//input[@placeholder='email@email.com']");
		
		By password = By.xpath("//input[@placeholder='Password']");
		
		By login_button = By.xpath("//button[contains(text(),'Log in')]");
	
		public  Login_page (WebDriver driver)
		{
			
			this.driver = driver;
			
			
		// calling this constructor called driver
			//constructor main task is to initialize the webDriver

		}
		//Parameterizing the void functions
		
	
		public void offerdialog() throws InterruptedException 
		 
		 {
			 driver.findElement(offerdialog).click();
		    }
		
		public void one_way() throws InterruptedException 
		 
		 {
			 driver.findElement(one_way).click();
			 
			 Thread.sleep(1000);
			 
		    }
		
		public void login() throws InterruptedException
		{
			driver.findElement(login).click();
			
			Thread.sleep(3000);	
			
		}
		
		//Parameterizing the void functions
		public void username(String username1) throws InterruptedException

		{
			driver.findElement(username).sendKeys(username1);
			
			
			
		}

		public void password(String password1) throws InterruptedException

		{
			driver.findElement(password).sendKeys(password1);	
			
		}

		public void login_button() throws InterruptedException

		{
			driver.findElement(login_button).click();
			
		}
		 

}

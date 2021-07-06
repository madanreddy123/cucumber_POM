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
public class Passenger_Detail_Page 
{
	// WebDriver will helps us to perform activities on the Web page 
	
	 WebDriver driver;
		
	 
	By contactnumber = By.xpath("//ry-input-d[@class='contact-details__input-tel ng-untouched ng-pristine ng-invalid']//input[@name='undefined']");

	By insurance = By.xpath("//label[@for='insurance-opt-out']//div[@class='_background']");
	
	By cardnumber = By.xpath("/html/body/app-root/ng-component/ry-spinner/div/payment-form/form/div[5]/payment-methods/div/div/ry-tabs/div[2]/div/add-method-modal/form/div/div[1]/div[1]/div[2]/card-details/form/ry-input-d[1]/div/input");
		
	By expiry_date = By.xpath("//span[@class= 'dropdown__toggle-text ng-star-inserted']");
	 
	By expiry_date1= By.xpath("//div[contains(text(),'March')]");
	
	By expiry_year = By.xpath("//div[@class='wrapper']//div[2]//ry-dropdown[1]//div[1]//button[1]");
	
	By expiry_year1 = By.xpath("//div[contains(text(),'2023')]");

	
	By cvv = By.xpath("//input[@placeholder='CVV']");
	
	By card_holder_name = By.xpath("/html/body/app-root/ng-component/ry-spinner/div/payment-form/form/div[5]/payment-methods/div/div/ry-tabs/div[2]/div/add-method-modal/form/div/div[1]/div[1]/div[2]/card-details/form/ry-input-d[2]/div/input");
	
	By address_1 = By.xpath("//billing-address//ry-input-d[1]//div[1]//input[1]");
	
	By city = By.xpath("//ry-input-d[@formcontrolname='city']//input[@name='undefined']");
	

	By country = By.xpath("//icon[@class='fill-light-blue-color ng-tns-c78-3']//span[@class='_container icon-16']//*[local-name()='svg']");
	By country1 = By.xpath("//input[@class='b2 _autocomplete_input ng-tns-c78-3 _autocomplete_input--dropdown _error']");
	By postal_code = By.xpath("/html/body/app-root/ng-component/ry-spinner/div/payment-form/form/div[5]/payment-methods/div/div/ry-tabs/div[2]/div/add-method-modal/form/div/div[1]/div[2]/billing-address/address-form/form/ry-input-d[4]/div/input");
	
	By Euro = By.xpath("//button[@class='dropdown__toggle b2']");


	By Euro1 = By.xpath("//ry-dropdown-item[1]//button[1]//div[1]//div[1]");
	
	By click_now = By.xpath("//label[@for='termsAndConditions']//div[@class='_background']");

	By pay_now = By.xpath("//button[normalize-space()='Pay now']");
	
	public  Passenger_Detail_Page (WebDriver driver)
	{
		
		this.driver = driver;
		
		
	// calling this constructor called driver
		//constructor main task is to initialize the webDriver

	}
	//Parameterizing the void functions
	
	public void contactnumber() throws InterruptedException
	{
	
		driver.findElement(contactnumber).sendKeys("0899675302");
		Thread.sleep(4000);
		
	}
 
	
	
	public void insurance() throws InterruptedException
	{
		
		driver.findElement(insurance).click();
		Thread.sleep(4000);

	}
	
	public void cardnumber() throws InterruptedException
	{
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,900)");
		driver.findElement(cardnumber).sendKeys("5555555555555557");
		

	}
	
	public void expiry_date() throws InterruptedException
	{
		
		driver.findElement(expiry_date).click();
		

	}
	
	public void expiry_date1() throws InterruptedException
	{
		
		driver.findElement(expiry_date1).click();
		

	}
	
	public void expiry_year() throws InterruptedException
	{
	
		driver.findElement(expiry_year).click();

	

	}
	
	public void expiry_year1() throws InterruptedException
	{
	
		driver.findElement(expiry_year1).click();
	

	}
	
	
	public void cvv() throws InterruptedException
	{
	
		driver.findElement(cvv).sendKeys("265");
		

	}
	
	public void card_holder_name() throws InterruptedException
	{
	
		driver.findElement(card_holder_name).sendKeys("madan");
		

	}
	public void address_1() throws InterruptedException
	{
	
		driver.findElement(address_1).sendKeys("dublin 1");
	

	}
	
	public void city() throws InterruptedException
	{
	
		driver.findElement(city).sendKeys("dublin");
	

	}
	
	public void country() throws InterruptedException
	{
		driver.findElement(country).click();
		
		driver.findElement(country1).sendKeys("Ireland");
		
		driver.findElement(By.xpath("/html/body/app-root/ng-component/ry-spinner/div/payment-form/form/div[5]/payment-methods/div/div/ry-tabs/div[2]/div/add-method-modal/form/div/div[1]/div[2]/billing-address/address-form/form/ry-autocomplete/div[2]")).click();

     	
		
	}
	
	public void postal_code() throws InterruptedException
	{
	
		driver.findElement(postal_code).sendKeys("D01 XR91");
		

	}
	
	public void Euro() throws InterruptedException
	{
	
		driver.findElement(Euro).click();
		driver.findElement(Euro1).click();

	}
	
	
	
	
	public void click_now() throws InterruptedException
	{
	
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,900)");
	driver.findElement(click_now).click();

			
	}
	
	public void pay_now() throws InterruptedException
	{
	
		driver.findElement(pay_now).click();
		
		
		
	}
	
	
}

package steps;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;



import Pages.ryanair.Checkout_page;
import Pages.ryanair.Destination_Page;
import Pages.ryanair.Login_page;
import Pages.ryanair.Passenger_Detail_Page;
import Pages.ryanair.Seat_selection_Page;
import Pages.ryanair.Select_Fare_Page;
import cucumber.api.java.en.Then;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class Pages  
{
	WebDriver driver;
	
	 
	@Given("I am on Ryanair start page")
	
	public void i_am_on_Ryanair_start_page() throws InterruptedException {
	  	
			  	
	  	driver = new ChromeDriver();
	  	
	  	driver.manage().window().maximize();
	  	driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
	  	
	  	driver.get("https://www.ryanair.com/ie/en");
	  	driver.getCurrentUrl();
	  		
		
	}
	
	
	 
		@Given("I entered the {string} and {string} and click on login button")
		
		public void i_am_entering_the_username_and_password(String username, String Password) throws InterruptedException {
		  	
			Login_page login = new Login_page(driver);
			login.offerdialog();
			login.one_way();
			login.login();
			login.username(username);
			login.password(Password);
			login.login_button();
			Thread.sleep(3000);
			
		}
		
	

	@Given("I search for a flight from dublin to {string} on flightDate")
	public void i_search_for_a_flight_from_to_on(String flightTo) throws InterruptedException {
		
		
		
		Destination_Page Destination = new Destination_Page(driver);
		
		Destination.desitination();
	  
		
		Destination.search();
		
		
		Destination.choosedate();
		
		
		Destination.choose();
		 
		Destination.search();
		Thread.sleep(3000);
	}

	@Given("I pick a first offer")
	public void i_pick_a_first_offer() throws InterruptedException {
Select_Fare_Page Select_Fare = new Select_Fare_Page(driver);
		
		Select_Fare.duration();
		
		
		
		Select_Fare.regular123();
		
		Select_Fare.mr();
		
		Select_Fare.mr_1();
		
		Select_Fare.first_name();
		
		Select_Fare.last_name();
		Thread.sleep(1000);
		Select_Fare.last_continue();
		
		Thread.sleep(3000);
		

	}
	
	
	@Given("I select the seat and click on continue")
	public void i_select_the_seat_and_click_on_continue() throws InterruptedException {
Seat_selection_Page	Seat_selection = new Seat_selection_Page(driver);
		
		Seat_selection.select_seat();
		
		Seat_selection.seat_continue();
		Seat_selection.exception();
		Seat_selection.bagselection();
		Seat_selection.exception_1();
		
		Seat_selection.exceptioncont();
		
		Thread.sleep(2000);
	}


	@Given("I do a check out")
	public void i_do_a_check_out() throws InterruptedException {
		 Checkout_page Checkout = new Checkout_page(driver);
			
			Checkout.checkout();
			
			Checkout.checkout_1();
			
			Thread.sleep(3000);
	}

	@When("I am trying to enter the contact number and select the insurance")
	public void i_am_trying_to_select_the_insurance() throws InterruptedException {
		Passenger_Detail_Page Passenger_dtl = new Passenger_Detail_Page(driver);
		
		Passenger_dtl.contactnumber();

		Passenger_dtl.insurance();
	}


	@Then("I insert payment details with card number and card SecurityCode and click on pay")
	public void i_insert_payment_details_with_card_number_and_security_code() throws InterruptedException {
		Passenger_Detail_Page Passenger_dtl = new Passenger_Detail_Page(driver);

		Passenger_dtl.cardnumber();

		Passenger_dtl.expiry_date();
		
		Passenger_dtl.expiry_date1();
		
		Passenger_dtl.expiry_year();
		
		Passenger_dtl.expiry_year1();
		
		Passenger_dtl.cvv();
		

		Passenger_dtl.card_holder_name();
		
		Passenger_dtl.address_1();
		
		Passenger_dtl.city();
		
		
		Passenger_dtl.country();
		
		Passenger_dtl.postal_code();
		
		Passenger_dtl.Euro();
		
		Passenger_dtl.click_now();
		
		Passenger_dtl.pay_now();
		
		Thread.sleep(2000);
		
		driver.quit();
	}

}

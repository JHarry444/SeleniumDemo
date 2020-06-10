package com.qa.test.cucumber;

import static org.junit.Assert.assertTrue;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import com.qa.test.pages.LoginPage;
import com.qa.test.pages.RegisterPage;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class StepDefinition {

	private RemoteWebDriver driver;

	private RegisterPage registerPage;

	private LoginPage loginPage;

	@Before
	public void init() {
		ChromeOptions opts = new ChromeOptions();
		opts.setHeadless(true);
		this.driver = new ChromeDriver();
	}

	@After
	public void tearDown() {
		this.driver.quit();
	}

	@Given("^I have created an account with \"([^\"]*)\" and \"([^\"]*)\"$")
	public void i_have_created_an_account_with_and(String username, String password) throws Throwable {
		driver.get("http://thedemosite.co.uk/addauser.php");
		this.registerPage = PageFactory.initElements(driver, RegisterPage.class);
		this.registerPage.register(username, password);
	}

	@Given("^I am on the login page$")
	public void i_am_on_the_login_page() throws Throwable {
		driver.get("http://thedemosite.co.uk/login.php");
		this.loginPage = PageFactory.initElements(driver, LoginPage.class);
	}

	@When("^I enter the \"([^\"]*)\" and \"([^\"]*)\"$")
	public void i_enter_the_and(String username, String password) throws Throwable {
		this.loginPage.login(username, password);
	}

	@Then("^I verify the login \"([^\"]*)\"$")
	public void i_verify_the_login(String status) throws Throwable {
		assertTrue(this.loginPage.getStatus().contains(status));
	}

}

package com.qa.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import com.qa.test.pages.BingLandingPage;
import com.qa.test.pages.BingSearchPage;

public class SeleniumTest {

	WebDriver driver;

	@Before
	public void init() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}

	@Test
	public void test() throws InterruptedException {
		driver.get("https://www.bing.co.uk");
		WebElement searchBar = driver.findElement(By.id("sb_form_q"));
//		WebElement searchBar = driver.findElement(By.xpath("/html/body/div[2]/div/div[3]/div[2]/form/input[1]"));  using xpath instead of the id
		searchBar.sendKeys("turtles");
		System.out.println(searchBar.getAttribute("innerText"));
		searchBar.sendKeys(Keys.ENTER);
//		WebElement searchButton = driver.findElement(By.xpath("/html/body/div[3]/div/div[3]/div[2]/form/label/svg"));
//		searchButton.click();
//		WebElement searchBar2 = driver.findElement(By.xpath("/html/body/header/form/div/input[1]"));
		WebElement searchBar2 = driver.findElement(By.xpath("//*[@id=\"sb_form_q\"]"));
		assertEquals("turtles", searchBar2.getAttribute("value"));
	}

	@Test
	public void testPOM() {
		driver.get("https://www.bing.co.uk");

		final String searchTerm = "turtles";

		BingLandingPage landing = PageFactory.initElements(driver, BingLandingPage.class);
		landing.search(searchTerm);

		BingSearchPage searchPage = PageFactory.initElements(driver, BingSearchPage.class);

		assertEquals(searchTerm, searchPage.getSearchTerm());
	}

	@Test
	public void testDemoSite() {
		driver.get("http://thedemosite.co.uk/addauser.php");

		final String username = "user";

		final String password = "pass";

		WebElement userField = driver.findElement(By.xpath(
				"/html/body/table/tbody/tr/td[1]/form/div/center/table/tbody/tr/td[1]/div/center/table/tbody/tr[1]/td[2]/p/input"));
		WebElement passField = driver.findElement(By.xpath(
				"/html/body/table/tbody/tr/td[1]/form/div/center/table/tbody/tr/td[1]/div/center/table/tbody/tr[2]/td[2]/p/input"));

		userField.sendKeys(username);
		passField.sendKeys(password);

		WebElement submitUser = driver.findElement(By.xpath(
				"/html/body/table/tbody/tr/td[1]/form/div/center/table/tbody/tr/td[1]/div/center/table/tbody/tr[3]/td[2]/p/input"));
		submitUser.click();

		driver.get("http://thedemosite.co.uk/login.php");

		WebElement loginUser = driver.findElement(By.xpath(
				"/html/body/table/tbody/tr/td[1]/form/div/center/table/tbody/tr/td[1]/table/tbody/tr[1]/td[2]/p/input"));
		WebElement loginPass = driver.findElement(By.xpath(
				"/html/body/table/tbody/tr/td[1]/form/div/center/table/tbody/tr/td[1]/table/tbody/tr[2]/td[2]/p/input"));
		WebElement loginButton = driver.findElement(By.xpath(
				"/html/body/table/tbody/tr/td[1]/form/div/center/table/tbody/tr/td[1]/table/tbody/tr[3]/td[2]/p/input"));

		loginUser.sendKeys(username);
		loginPass.sendKeys(password);
		loginButton.click();

		WebElement loginStatus = driver
				.findElement(By.xpath("/html/body/table/tbody/tr/td[1]/big/blockquote/blockquote/font/center/b"));

		assertTrue(loginStatus.getText().contains("Successful Login"));
	}

	@After
	public void tearDown() {
		driver.quit();
	}
}

package com.qa.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.google.common.io.Files;
import com.qa.test.pages.BingLandingPage;
import com.qa.test.pages.BingSearchPage;

public class SeleniumTest {

	private RemoteWebDriver driver;

	private static ExtentReports report;

	private ExtentTest test;

	@BeforeClass
	@SuppressWarnings("deprecation")
	public static void setup() {
		report = new ExtentReports();
		ExtentHtmlReporter htmlReport = new ExtentHtmlReporter("test-output/html/extentReport.html");
		htmlReport.config().setAutoCreateRelativePathMedia(true);
		report.attachReporter(htmlReport);
	}

	@Before
	public void init() {
		ChromeOptions opts = new ChromeOptions();
		opts.setHeadless(true);
		driver = new EdgeDriver();
		driver.manage().window().maximize();
	}

	@Test
	public void test() throws InterruptedException, IOException {
		driver.get("https://www.bing.co.uk");
		WebElement searchBar = driver.findElement(By.id("sb_form_q"));
		this.test = report.createTest("test");
		final String searchTerm = "turtles";

//		WebElement searchBar = driver.findElement(By.xpath("/html/body/div[2]/div/div[3]/div[2]/form/input[1]"));  using xpath instead of the id
		searchBar.sendKeys(searchTerm);
		System.out.println(searchBar.getAttribute("innerText"));
		searchBar.sendKeys(Keys.ENTER);
//		WebElement searchButton = driver.findElement(By.xpath("/html/body/div[3]/div/div[3]/div[2]/form/label/svg"));
//		searchButton.click();
//		WebElement searchBar2 = driver.findElement(By.xpath("/html/body/header/form/div/input[1]"));
		WebElement searchBar2 = driver.findElement(By.xpath("//*[@id=\"sb_form_q\"]"));
		assertEquals(searchTerm, searchBar2.getAttribute("value"));

		File srcFile = driver.getScreenshotAs(OutputType.FILE);

		final String scrShotPath = "test-output/screenshots/test.png";
		File targetFile = new File(scrShotPath);
		Files.copy(srcFile, targetFile);

		if (searchTerm.equals(searchBar2.getAttribute("value"))) {
			test.pass("Correct search term found").addScreenCaptureFromPath(scrShotPath);
		} else {
			test.fail("Incorrect search term found").addScreenCaptureFromPath(scrShotPath);
			fail();
		}
	}

	@Test
	public void testPOM() {
		driver.get("https://www.bing.co.uk");
		this.test = report.createTest("testPOM");

		final String searchTerm = "turtles";

		BingLandingPage landing = PageFactory.initElements(driver, BingLandingPage.class);
		landing.search(searchTerm);

		BingSearchPage searchPage = PageFactory.initElements(driver, BingSearchPage.class);

		if (searchPage.getSearchTerm().equals(searchTerm)) {
			test.pass("Correct search term found.");
		} else {
			test.fail("Correct search term not found.");
			fail();
		}
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

	@AfterClass
	public static void tearDownClass() {
		report.flush();
	}
}

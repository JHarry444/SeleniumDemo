package com.qa.test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumTest {

	WebDriver driver;

	@Before
	public void init() {
		driver = new ChromeDriver();
	}

	@Test
	public void test() throws InterruptedException {
		driver.manage().window().maximize();
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

	@After
	public void tearDown() {
		driver.quit();
	}
}

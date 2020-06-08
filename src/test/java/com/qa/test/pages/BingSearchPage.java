package com.qa.test.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BingSearchPage {

	@FindBy(id = "sb_form_q")
	WebElement searchBar;

	public String getSearchTerm() {
		return searchBar.getAttribute("value");
	}
}

package scripts.web;

import static org.junit.Assert.fail;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import helpers.LogHelper;
import helpers.WebDriverHelper;
import pageobject.fravega.HomePage;
import utils.Constants;

public class Search {
	private WebDriverHelper browser;
	private WebDriver driver;
	private HomePage homePage;
	LogHelper logger;

	public Search(WebDriverHelper browser, WebDriver driver, LogHelper logger) {
		this.browser = browser;
		this.driver = driver;
		this.logger = logger;
	}

	public void runTest(String searchText, String subCategoryFilter, String brandFilter) throws Exception {
		// Initialize Page Object
		homePage = new HomePage();
		PageFactory.initElements(driver, homePage);

		// Check pre-conditions
		this.checkPreconditions();

		// Navigate to main page
		browser.navigateToURL(driver, Constants.FRAVEGA_MAIN_URL);

		// Close Offers window
		closeOffersBox();

		// Search word
		searchWord(searchText);

		// Set sub-category filter
		setSubCategoryFilter(subCategoryFilter);

		// Set brand filter
		setBrandFilter(brandFilter);

		// Validate search
		Thread.sleep(2000);
		validateSearch(searchText, subCategoryFilter, brandFilter);
	}

	// Check pre-conditions
	private void checkPreconditions() {
	}

	// Close Offers window
	private void closeOffersBox() {
		homePage.offersCloseButton.click();
	}

	// Search word
	private void searchWord(String searchText) {
		homePage.searchBar.sendKeys(searchText);
		homePage.searchButton.click();
	}

	// Set sub-category filter
	private void setSubCategoryFilter(String subCategoryFilter) {
		Boolean found = false;
		if (subCategoryFilter != null && !subCategoryFilter.isEmpty()) {
			for (int i = 0; i < homePage.categoriesFilter.size(); i++) {
				WebElement categoryElement = homePage.categoriesFilter.get(i);
				if (categoryElement.getText().toUpperCase().contains(subCategoryFilter.toUpperCase())) {
					categoryElement.click();
					found = true;
					break;
				}
			}
			if (!found) {
				fail("SubCategory filter '" + subCategoryFilter + "' not found.");
			}
		}
	}

	// Set brand filter
	private void setBrandFilter(String brandFilter) {
		Boolean found = false;
		if (brandFilter != null && !brandFilter.isEmpty()) {
			for (int i = 0; i < homePage.brandsFilter.size(); i++) {
				WebElement brandElement = homePage.brandsFilter.get(i);
				if (brandElement.getText().toUpperCase().contains(brandFilter.toUpperCase())) {
					brandElement.click();
					found = true;
					break;
				}
			}
			if (!found) {
				fail("Brand filter '" + brandFilter + "' not found.");
			}
		}
	}

	// Validate search
	private void validateSearch(String searchText, String subCategoryFilter, String brandFilter) {
		int resultsNumber = Integer.parseInt(homePage.resultsNumber.getText());
		int totalProductsFound = 0;
		int pages = 1;
		int actualPage = 1;

		// Get paginator
		if (homePage.resultsPaginator.size() > 0) {
			pages = homePage.resultsPaginator.size();
		}

		// Validate breadcrumb
		List<WebElement> tags = homePage.breadcrumb.findElements(By.tagName("li"));
		if (subCategoryFilter != null && !subCategoryFilter.isEmpty()) {
			Boolean found = false;
			for (int i = 0; i < tags.size(); i++) {
				if (tags.get(i).getText().trim().toUpperCase().equals(subCategoryFilter.trim().toUpperCase())) {
					found = true;
					break;
				}
			}
			if (!found) {
				fail("Word '" + subCategoryFilter + "' not found in breadcrumb tags for searched word '" + searchText + "'");
			}
		}

		// Validate product titles for all results
		for (int i = 1; i <= pages; i++) {
			if (i > 1) {
				homePage.resultsPaginator.get(actualPage).click();
				actualPage++;
			}
			for (int k = 0; k < homePage.productSearchResults.size(); k++) {
				WebElement productTitle = homePage.productSearchResults.get(k).findElement(By.tagName("h4"));
				Boolean validTitle = false;
				if (productTitle.getText().toUpperCase().contains(searchText.toUpperCase())) {
					if (brandFilter != null && !brandFilter.isEmpty()) {
						if (productTitle.getText().toUpperCase().contains(brandFilter.toUpperCase())) {
							validTitle = true;
						}
					} else {
						validTitle = true;
					}
				}
				if (validTitle) {
					totalProductsFound++;
				} else {
					fail("Wrong product title for searched word '" + searchText + "'. Expected words in title: '" + searchText + "'" + (brandFilter != null && !brandFilter.isEmpty() ? " and '" + brandFilter + "'" : "") + " - Found: '" + productTitle.getText() + "'");
				}
			}
		}

		// Validate results quantity
		if (resultsNumber != totalProductsFound) {
			fail("Wrong products quantity in results for searched word '" + searchText + "'. Expected: " + resultsNumber + " - Found: " + totalProductsFound);
		}
	}
}

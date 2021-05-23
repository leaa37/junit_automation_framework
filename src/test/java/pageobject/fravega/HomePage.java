package pageobject.fravega;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage {

	@FindBy(xpath = "//button[contains(@class, 'GeolocalizationDesktop__Button-')]")
	public WebElement offersCloseButton;

	@FindBy(xpath = "//input[contains(@class, 'InputBar__SearchInput-')]")
	public WebElement searchBar;

	@FindBy(xpath = "//button[contains(@class, 'InputBar__SearchButton-')]")
	public WebElement searchButton;

	@FindBy(xpath = "//*[@name='subcategoryAggregation']")
	public List<WebElement> categoriesFilter;

	@FindBy(xpath = "//*[@name='brandAggregation']")
	public List<WebElement> brandsFilter;

	@FindBy(xpath = "//*[@name='totalResult']/span")
	public WebElement resultsNumber;

	@FindBy(xpath = "//*[@name='breadcrumb']")
	public WebElement breadcrumb;

	@FindBy(xpath = "//li[contains(@class, 'ant-pagination-item-')]")
	public List<WebElement> resultsPaginator;

	@FindBy(xpath = "//article[contains(@class, 'ProductCard__Card-sc-')]")
	public List<WebElement> productSearchResults;
}

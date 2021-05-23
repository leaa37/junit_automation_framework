package helpers;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriverHelper {
	private static final String VERSION = "90.0.4430.24"; // Change accordingly
	private final int elementWaitSec = 10;
	LogHelper logger;
	ChromeOptions options;

	public WebDriverHelper() {
	}
	
	public WebDriver generateWebDriver() {
		logger = new LogHelper();

		// Browser configuration
		WebDriver driver;
		WebDriverManager.chromedriver().version(VERSION).setup();
		options = new ChromeOptions();
		options.addArguments("start-maximized");
		options.addArguments("enable-automation");
		options.addArguments("--no-sandbox");
		options.addArguments("--disable-infobars");
		options.addArguments("--disable-dev-shm-usage");
		options.addArguments("--disable-browser-side-navigation");
		options.addArguments("--disable-gpu");
		driver = new ChromeDriver(options);

		// Driver configuration
		driver.manage().timeouts().implicitlyWait(elementWaitSec, TimeUnit.SECONDS);

		return driver;
	}

	public void navigateToURL(WebDriver driver, String url) {
		logger.logInfo("Navigate to URL: " + url);
		driver.get(url);
	}

	public void waitForElement(WebDriver driver, WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, elementWaitSec);
		wait.until(ExpectedConditions.visibilityOf(element));
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public void doClickOnElement(WebDriver driver, WebElement element) {
		waitForElement(driver, element);
		element.click();
	}

	public void setTextOnElement(WebDriver driver, WebElement element, String text) {
		waitForElement(driver, element);
		element.sendKeys(text);
	}
	
}

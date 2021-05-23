package automation;

import java.io.File;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.WebDriver;

import helpers.LogHelper;
import helpers.RestHelper;
import helpers.WebDriverHelper;
import scripts.api.CheckBreweries;
import scripts.web.Search;

public class MainTest {
	private static WebDriver driver;
	private static WebDriverHelper browser;
	private static RestHelper restHelper;
	private static LogHelper logger;
	private static ExtentReports report;
	public static ExtentTest extentTest;

	@BeforeClass
	public static void beforeClass() {
		// Initialize report
		String extentReportFile = System.getProperty("user.dir") + "/target/report.html";
		File reportFile = new File(extentReportFile);
		if (reportFile.exists()) {
			reportFile.delete();
		}
		report = new ExtentReports(extentReportFile, false);
		logger = new LogHelper();

		logger.logInfo("Framework initialized successfully");
	}

/*
	@Before
	public void beforeTest() {
		// Initialize web driver
		initializeWebDriverHelper(); // Moved to each web test
	}
*/

	@Test // WEB test
	public void searchWordHeladera() throws Exception {
		// Initialize web driver
		initializeWebDriverHelper();

		// Search word feature. Parameters: word (required), sub-category (optional), brand (optional)
		searchWord("heladera", "heladeras", "Peabody");
	}

	@Test // WEB test
	public void searchWordMonitor() throws Exception {
		// Initialize web driver
		initializeWebDriverHelper();

		// Search word feature. Parameters: word (required), sub-category (optional), brand (optional)
		searchWord("monitor", null, "HP");
	}

	@Test // WEB test
	public void searchWordTelevision() throws Exception {
		// Initialize web driver
		initializeWebDriverHelper();

		// Search word feature. Parameters: word (required), sub-category (optional), brand (optional)
		searchWord("smart tv", "TV", "Samsung");
	}

	@Test // API test
	public void checkBreweries() throws Exception {
		// Initialize api driver
		initializeRestHelper();
	
		// Check breweries
		extentTest = report.startTest("API - Check Breweries", "Check brewerie");
		new CheckBreweries(restHelper, logger).runTest();
	}

	@Rule
	public TestWatcher watcher = new TestWatcher() {
		@Override
		protected void failed(Throwable e, Description description) {
			extentTest.log(LogStatus.FAIL, e.getMessage());
		}

		@Override
		protected void succeeded(Description description) {
			extentTest.log(LogStatus.PASS, "Test passed successfully");
		}
	};

	@After
	public void afterTest() {
		report.endTest(extentTest);
		if (driver != null) {
			driver.quit();
		}
	}

	@AfterClass
	public static void afterClass() {
		report.flush();
		logger.logInfo("Tests execution finished");
	}

	private void searchWord(String searchText, String subCategoryFilter, String setBrandFilter) throws Exception {
		extentTest = report.startTest("WEB - Search", "Search word '" + searchText + "' in Fravega page");
		logger.logInfo("Starting running test: SEARCH");
		new Search(browser, driver, logger).runTest(searchText, subCategoryFilter, setBrandFilter);
	}

	private void initializeWebDriverHelper() {
		browser = new WebDriverHelper();
		driver = browser.generateWebDriver();
	}

	private void initializeRestHelper() {
		restHelper = new RestHelper();
	}

}
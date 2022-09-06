package com.qa.util;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringWriter;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.output.WriterOutputStream;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.TestException;

import com.qa.base.TestBase;
import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.config.RedirectConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class commanUtils extends TestBase {

//	public static ExtentTest test;
//	protected static ExtentReports report;

	protected static WebDriver driver;
	WebDriverWait wait;
	StringWriter requestWriter;
	StringWriter responseWriter;

	private static final String BROWSER = System.getProperty("browser", "Chrome");

	// Browser configuration - can add more browsers and remote driver here

	public void initiazeBrowser() {
		if (BROWSER.equals("Firefox")) {
			WebDriverManager.firefoxdriver().setup(); // can also use set property method for browser executables
			driver = new FirefoxDriver();
		} else if (BROWSER.equals("Chrome")) {
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-notifications");
			driver = new ChromeDriver(options);
		} else if (BROWSER.equals("IE")) {
			WebDriverManager.iedriver().setup();
			driver = new InternetExplorerDriver();
		} else {
			throw new RuntimeException("Browser type unsupported");
		}

		// Setting implicit wait
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		// Setting WebDriverWait with max timeout value of 20 seconds
		wait = new WebDriverWait(driver, 20);

	}

	public void initiazeBrowser(String browser) {
		if (browser.equals("Firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} else if (browser.equals("Chrome")) {
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-notifications");
			driver = new ChromeDriver(options);
		} else if (browser.equals("IE")) {
			WebDriverManager.iedriver().setup();
			driver = new InternetExplorerDriver();
		} else {
			throw new RuntimeException("Browser type unsupported");
		}

		// Setting implicit wait
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		// Setting WebDriverWait with max timeout value of 20 seconds
		wait = new WebDriverWait(driver, 20);

	}

	public void navigateToURL(String URL) {
		System.out.println("Navigating to: " + URL);
		System.out.println("Thread id = " + Thread.currentThread().getId());

	}

	public String getPageTitle() {
		try {
			System.out.print(String.format("The title of the page is: %s\n\n", driver.getTitle()));
			return driver.getTitle();
		} catch (Exception e) {
			throw new TestException(String.format("Current page title is: %s", driver.getTitle()));
		}
	}

	public WebElement getElement(By selector) {
		try {
			return driver.findElement(selector);
		} catch (Exception e) {
			System.out.println(String.format("Element %s does not exist - proceeding", selector));
		}
		return null;
	}

	public void sendKeys(By selector, String value) {
		WebElement element = getElement(selector);
		clearField(element);
		try {
			element.sendKeys(value);
		} catch (Exception e) {
			throw new TestException(
					String.format("Error in sending [%s] to the following element: [%s]", value, selector.toString()));
		}
	}

	public void clearField(WebElement element) {
		try {
			element.clear();
		} catch (Exception e) {
			System.out.print(String.format("The following element could not be cleared: [%s]", element.getText()));
		}
	}

	public void click(By selector) {
		WebElement element = getElement(selector);
		waitForElementToBeClickable(selector);
		try {
			element.click();
		} catch (Exception e) {
			throw new TestException(String.format("The following element is not clickable: [%s]", selector));
		}
	}

	private void waitForElementToBeClickable(By selector) {
		// TODO Auto-generated method stub
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(selector));

	}

	public String getCssValue(By selector, String value) {
		WebElement element = getElement(selector);
		waitForElementToBeClickable(selector);
		String buttonRBGValue = null;
		try {

			buttonRBGValue = element.getCssValue(value);
		} catch (Exception e) {
			throw new TestException(
					String.format("Error in sending [%s] to the following element: [%s]", value, selector.toString()));
		}

		return buttonRBGValue;
	}

	public String getText(By selector) {
		WebElement element = getElement(selector);
		waitForElementToBeClickable(selector);
		String text = null;
		try {
			text = element.getText();
		} catch (Exception e) {
			throw new TestException(
					String.format("Error in sending [%s] to the following element: [%s]", text, selector.toString()));
		}
		return text;
	}

	public void performUploadFileAction(String path) {
		try {
			// creating object of Robot class
			Robot robot = null;
			robot = new Robot();
			// copying File path to Clipboard
			StringSelection str = new StringSelection(path);
			Clipboard clipbard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipbard.setContents(str, null);
			// press Control+V for pasting
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			// Release Control+V for pasting
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_V);
			// For pressing and releasing the Enter
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			test.log(LogStatus.INFO, "User Action not performed");
			e.printStackTrace();
		}

	}

	public Response postCallWithBody(String baseURI, String resource_Path, Map<String, String> reqHeaders,
			JSONObject formReqBodyContents) {
		Response response = null;
		RestAssured.baseURI = baseURI;
		RequestSpecification httpsRequest = RestAssured.given().log().all();
		httpsRequest.headers(reqHeaders);
		httpsRequest.body(formReqBodyContents);
		try {
			// print here the report INFO
			response = httpsRequest.post(resource_Path);
			// print the report line

		} catch (Exception e) {

			// print the report line here
		}
		/*
		 * requestWriter = new StringWriter(); PrintStream requestCaptor = new
		 * PrintStream(new WriterOutputStream(requestWriter),true); responseWriter = new
		 * StringWriter(); PrintStream responseCaptor = new PrintStream(new
		 * WriterOutputStream(responseWriter),true);
		 */

		return response;

	}

	public Response postCall(String baseURI, String resource_Path, JSONObject formReqBodyContents) {
		Response response = null;
		RestAssured.baseURI = baseURI;
		RequestSpecification httpsRequest = RestAssured.given().log().all();
		// httpsRequest.headers(reqHeaders);
		httpsRequest.body(formReqBodyContents);
		try {
			// print here the report INFO
			response = httpsRequest.post(resource_Path);
			// print the report line

		} catch (Exception e) {
			test.log(LogStatus.INFO, "Exception on post");
			test.log(LogStatus.FAIL, "API Service is DOWN");
			// print the report line here
		}

		test.log(LogStatus.INFO, "request " + baseURI);
		test.log(LogStatus.INFO, "response " + response.asString());
		return response;
	}

	public Response getCallWithParam(String baseURI, String resource_path, Map<String, String> reqHeaders,
			Map<String, String> reqParamters) {
		Response response = null;
		RestAssured.baseURI = baseURI;
		RequestSpecification httpsRequest = RestAssured.given().log().all();
		requestWriter = new StringWriter();
		PrintStream requestCaptor = new PrintStream(new WriterOutputStream(requestWriter), true);
		httpsRequest.config(new RestAssuredConfig().redirect(new RedirectConfig().followRedirects(true)))
				.filters(new RequestLoggingFilter(requestCaptor), new ResponseLoggingFilter(requestCaptor));
		if (reqHeaders == null || reqHeaders.isEmpty() || reqHeaders.size() != 0) {
			httpsRequest.headers(reqHeaders);
		}
		try {
			if (reqParamters == null || reqParamters.isEmpty() || reqParamters.size() != 0) {
				httpsRequest.queryParams(reqParamters);
			}
			response = httpsRequest.get(resource_path);
			// print the report here
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// print the failed report FAIL
		}
		test.log(LogStatus.INFO, "request " + requestWriter.toString());
		return response;
	}

	public Response getCall(String baseURI, String resource_path) {
		Response response = null;
		RestAssured.baseURI = baseURI;
		RequestSpecification httpsRequest = RestAssured.given().log().all();
		try {
			response = httpsRequest.get(resource_path);
			// print the report here
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// print the failed report FAIL
		}
		test.log(LogStatus.INFO, "request " + baseURI);
		test.log(LogStatus.INFO, "response " + response.asString());
		return response;
	}

	public Response getCallWithParam3(String baseURI, String resource_path) {
		Response response = null;
		RestAssured.baseURI = baseURI;
		RequestSpecification httpsRequest = RestAssured.given().log().all();
		/*
		 * if (reqHeaders == null || reqHeaders.isEmpty() || reqHeaders.size() != 0) {
		 * httpsRequest.headers(reqHeaders); } try { if (reqParamters == null ||
		 * reqParamters.isEmpty() || reqParamters.size() != 0) {
		 * httpsRequest.queryParams(reqParamters); } response =
		 * httpsRequest.get(resource_path); //print the report here } catch (Exception
		 * e) { // TODO Auto-generated catch block e.printStackTrace(); //print the
		 * failed report FAIL }
		 */
		httpsRequest.get(resource_path);
		return response;
	}

	public long takeSnap() {
		long number = (long) Math.floor(Math.random() * 900000000L) + 10000000L;
		try {
			FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
					new File(System.getProperty("user.dir") + "\\reports\\images\\" + number + ".jpg"));
		} catch (WebDriverException e) {
			System.out.println("The browser has been closed.");
		} catch (IOException e) {
			System.out.println("The snapshot could not be taken");
		}
		return number;
	}

}

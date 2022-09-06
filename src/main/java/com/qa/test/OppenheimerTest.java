package com.qa.test;

import java.io.IOException;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.qa.util.commanUtils;
import com.relevantcodes.extentreports.LogStatus;

import io.restassured.response.Response;

public class OppenheimerTest extends commanUtils {

	/*
	 * @BeforeMethod() public void beforeMethod(Object[] param) { test =
	 * report.startTest(param[0].toString()); }
	 */
	@BeforeMethod()

	public void beforeMethod(Object[] param, Object[] param1) {
		test = report.startTest(param[0].toString(), param[1].toString());

	}

	///////////////////////////////// ----------------dummy working--API
	///////////////////////////////// ///////////////////////////////////////
	/////////////////// UI---------///////////////////
	// @Test(dataProvider = "UseCase1")
	public void insertSingleRecord_API_Dummy(String TestCaseID, String TestCaseDescription, String testData,
			String Result) throws ParseException {

		if (TestCaseID.equalsIgnoreCase("TC1")) {
			System.out.println("I am inside Test1");
			System.out.println("TestCaseID+++++++++++" + TestCaseID);
			// Response res1 = getCall("https://dummy.restapiexample.com",
			// "/api/v1/employee/719");
			Response res1 = getCall("https://reqres.in", "/api/users/2");
			System.out.println(res1.getBody());
			int responseCode = res1.getStatusCode();
			System.out.println("+++++++++++responseCode+__________" + responseCode);

			if (responseCode == Integer.parseInt("429"))
				test.log(LogStatus.PASS, "response " + res1.statusCode());
		}
	}

	// @Test(dataProvider = "UseCase2")
	public void insertMultipleRecord_API_Dummy(String TestCaseID, String testData, String Result)
			throws ParseException {
		JSONParser parser = new JSONParser();
		JSONObject json = null;
		if (TestCaseID.equalsIgnoreCase("TC2")) {
			System.out.println("I am inside Test2");
			System.out.println("TestCaseID+++++++++++" + TestCaseID);
			String contextPath = "/context/v2/SaveHeros";
			String apiURL = envConfig.getProperty("localhostURL");
			json = (JSONObject) parser.parse("{\"name\":\"sinnga\",\"job\":\"123\"}");
			Response res1 = postCall("https://reqres.in", "/api/users", json);
			System.out.println(res1.getBody());
			int responseCode = res1.getStatusCode();
			System.out.println("+++++++++++responseCode+__________" + responseCode);
			System.out.println("+++++++++++Result+__________" + Result);

			test.log(LogStatus.PASS, "response " + res1.statusCode());
		}
	}

//	@Test(dataProvider = "DummyUI")
	public void getSearchCount(String executionFlag, String TestCaseID, String username, String pwd) {
		initiazeBrowser();
		driver.get(envConfig.getProperty("baseUrl"));
		sendKeys(By.name("q"), pwd);
		click(By.name("btnK"));
		test.log(LogStatus.PASS, "Navigated to the specified URL" + envConfig.getProperty("baseUrl"));
		List<WebElement> ele = driver.findElements(By.xpath("//*[contains(text(),\"" + username + "\")]"));
		try {
			test.log(LogStatus.PASS, "Navigated to the specified URL " + test.addScreenCapture((capture(driver))));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		test.log(LogStatus.INFO, "no of times found - " + String.valueOf(ele.size()));
		System.out.println(ele.size());
		System.out.println("sadfdf" + username);

	}

////----------------Acutal Test---------------------------------------------------------------------///////
@Test(dataProvider = "UseCase1")
	public void insertSingleRecord_API(String TestCaseID, String TestCaseDescription, String BaseURI,
			String ContextPath, String PayloadData, String ExpectedResult) throws ParseException {
		try {
			System.out.println(
					"+++++++++++++++++++++++++++++TestCaseID+++++++++++" + TestCaseID + "++++++++++++++++++++++++++++");
			JSONParser parser = new JSONParser();
			JSONObject json = null;
			System.out.println("TestCaseID+++++++++++" + TestCaseID);
			System.out.println("++++++++++++payload++++++++++" + PayloadData + "++++++++++++BaseURI++++++++" + BaseURI
					+ ContextPath);
			json = (JSONObject) parser.parse(PayloadData);
			Response res1 = postCall(BaseURI, ContextPath, json);
			System.out.println(res1.getBody());
			int responseCode = res1.getStatusCode();
			System.out.println("+++++++++++responseCode+__________" + responseCode);
			System.out.println("+++++++++++Result+__________" + ExpectedResult);
			test.log(LogStatus.PASS, "I am inside UseCase1");
			Assert.assertEquals(responseCode, Integer.parseInt(ExpectedResult));

		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

@Test(dataProvider = "UseCase2")
	public void insertMultipleRecord_API(String TestCaseID, String TestCaseDescription, String BaseURI,
			String ContextPath, String PayloadData, String ExpectedResult) throws ParseException {
		try {
			System.out.println(
					"+++++++++++++++++++++++++++++TestCaseID+++++++++++" + TestCaseID + "++++++++++++++++++++++++++++");
			JSONParser parser = new JSONParser();
			JSONObject json = null;

			System.out.println("I am inside Test2");
			System.out.println("TestCaseID+++++++++++" + TestCaseID);
			// String contextPath = "/context/v2/SaveHeros";
			// String apiURL = envConfig.getProperty("localhostURL");
			json = (JSONObject) parser.parse(PayloadData);
			Response res1 = postCall(BaseURI, ContextPath, json);
			System.out.println(res1.getBody());
			int responseCode = res1.getStatusCode();
			System.out.println("+++++++++++responseCode+__________" + responseCode);
			System.out.println("+++++++++++Result+__________" + ExpectedResult);
			test.log(LogStatus.PASS, "I am inside UseCase2");
			Assert.assertEquals(responseCode, Integer.parseInt(ExpectedResult));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test(dataProvider = "UseCase3")
	public void uploadSingleRecordIntoDB_UI1(String TestCaseID, String BrowserType, String appURL, String filePath,
			String FileName, String Result) throws InterruptedException, IOException {
		System.out.println(
				"+++++++++++++++++++++++++++++TestCaseID+++++++++++" + TestCaseID + "++++++++++++++++++++++++++++");

		try {
			initiazeBrowser(BrowserType);
			driver.get(appURL);
			// click(By.linkText("Upload a file"));
			Thread.sleep(2000); // suspending execution for specified time period
			if (filePath.contains("SingleRecord")) {
				String currentDirectory = System.getProperty("user.dir");
				System.out.println("The current working directory is " + currentDirectory);
				String getFilePath = currentDirectory + filePath + FileName;
				System.out.println("The current single record File directory path is " + getFilePath);
				performUploadFileAction(getFilePath);
				test.log(LogStatus.PASS, "Single Record File Location ::" + getFilePath);
				test.log(LogStatus.PASS, "Upload Single Record " + test.addScreenCapture((capture(driver))));
			} else {
				String currentDirectory = System.getProperty("user.dir");
				System.out.println("The current working directory is " + currentDirectory);
				String getFilePath = currentDirectory + filePath + FileName;
				System.out.println("The current Multiple record File directory path is " + getFilePath);
				performUploadFileAction(getFilePath);
				test.log(LogStatus.PASS, "Multiple Record File Location ::" + getFilePath);
				test.log(LogStatus.PASS, "Upload Multiple Record " + test.addScreenCapture((capture(driver))));
			}
		} catch (InterruptedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// @Test(dataProvider = "UseCase5")
	public void verifyButtonColor_UI(String TestCaseID, String BrowserType, String appURL, String ExpectedResult) {
		System.out.println(
				"+++++++++++++++++++++++++++++TestCaseID+++++++++++" + TestCaseID + "++++++++++++++++++++++++++++");

		try {
			System.out.println("TestCaseID+++++++++++" + TestCaseID);
			initiazeBrowser(BrowserType);
			driver.get(appURL);
			// UC5 -AC1:
			String storeButtonRBGValue = getCssValue(By.id("Dispense Now”"), "color");
			String getHexCode = Color.fromString(storeButtonRBGValue).asHex();
			// storeButtonRBGValue.getCssValue("background-color");
			test.log(LogStatus.PASS, "Navigated to the specified URL");
			test.log(LogStatus.PASS, "Navigated to the specified URL " + test.addScreenCapture((capture(driver))));
			// UC5 -AC2:
			String getButtonText = getText(By.xpath("//a[text()='Dispense Now']"));
			test.log(LogStatus.INFO, "no of times found - " + String.valueOf(getHexCode));
			Assert.assertEquals(getHexCode, Integer.parseInt(ExpectedResult));
			Assert.assertEquals(getButtonText, Integer.parseInt(ExpectedResult));
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

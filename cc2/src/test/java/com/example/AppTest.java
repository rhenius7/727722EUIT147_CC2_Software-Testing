package com.example;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.model.Log;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.util.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     * 
     * @throws InterruptedException
     * @throws IOException
     */
    WebDriver driver;
    // Extent Reports
    ExtentReports reports;
    ExtentTest test;
    // Logger
    static Logger log = Logger.getLogger(AppTest.class);

    @BeforeTest
    public void start() {
        WebDriverManager.chromedriver().setup();
        PropertyConfigurator.configure(("src/main/resources/log4j.properties"));
        driver = new ChromeDriver();
        ExtentSparkReporter ereport = new ExtentSparkReporter(
                "C:\\Users\\ASUS\\OneDrive\\Desktop\\CC2\\cc2\\ExtentReports\\cc2report.html");
        reports = new ExtentReports();
        reports.attachReporter(ereport);
    }

    @Test
    public void Test1() throws InterruptedException, IOException {
        test = reports.createTest("Test 1 Started");
        driver.get("https://www.barnesandnoble.com/");
        Thread.sleep(2000);
        log.info("Url opened");
        driver.findElement(By.linkText("All")).click();
        driver.findElement(By.linkText("Books")).click();
        Thread.sleep(2000);
        log.info("Entered Books");
        FileInputStream fs = new FileInputStream("C:\\Users\\OrangeHRM.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fs);
        XSSFSheet sheet1 = workbook.getSheet("LOGIN");
        XSSFRow row = sheet1.getRow(6);
        String name = row.getCell(0).getStringCellValue();

        WebElement search = driver
                .findElement(By.xpath("//*[@id='rhf_header_element']/nav/div/div[3]/form/div/div[2]/div/input[1]"));
        search.click();
        search.sendKeys(name);
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id='rhf_header_element']/nav/div/div[3]/form/div/span/button")).click();
        Thread.sleep(5000);
        WebElement page = driver
                .findElement(By.xpath("//*[@id='searchGrid']/div/section[1]/section[1]/div/div[1]/div[1]/h1/span"));
        String source = page.getText();
        if (source.equals("Chetan Bhagat")) {
            System.out.print("Name found");
            // Logging reports
            test.log(Status.PASS, "Name found");
            // Logging
            log.info("Chetan Bhagat found");
        } else
            System.out.print("Name not found");
        log.info("Testcase passed");

    }

    @Test
    public void Test2() throws InterruptedException {
        test = reports.createTest("Test 2 Started");
        driver.get("https://www.barnesandnoble.com/");

        Thread.sleep(2000);
        // Logging Reports
        test.log(Status.PASS, "Entered URL");
        WebElement audiobooks = driver.findElement(By.linkText("Audiobooks"));
        Thread.sleep(2000);
        Actions action = new Actions(driver);
        action.moveToElement(audiobooks).perform();
        Thread.sleep(2000);
        driver.findElement(By.linkText("Audiobooks Top 100")).click();
        test.log(Status.PASS, "Clicked AudioBooks");
        Thread.sleep(2000);
        log.info("clicked AudioBooks 100");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,300)");
        driver.findElement(By.linkText("Funny Story")).click();
        Thread.sleep(2000);
        js.executeScript("window.scrollBy(0,500)");
        log.info("window scolled");
        driver.findElement(By.xpath("//*[@id=\"commerce-zone\"]/div[2]/ul/li[2]/div/div/label/span")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"find-radio-checked\"]/div[1]/form/input[5]")).click();
        Thread.sleep(2000);
        WebElement cart = driver.findElement(By.xpath("//*[@id='add-to-bag-main']/div[1]"));
        String add = cart.getText();
        if (add.equals("Item Successfully Added To Your cart"))
            System.out.println("Item added successfully");
        else
            System.out.println("Error");
        Thread.sleep(2000);

    }

    @Test
    public void Test3() throws Exception {
        test = reports.createTest("Test 3 Started");
        driver.get("https://www.barnesandnoble.com/");
        test.log(Status.PASS, "Entere URL")
        Thread.sleep(2000);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,200)");
        Thread.sleep(3000);
        WebElement e = driver
                .findElement(By.xpath("/html/body/main/div[3]/div[3]/div/section/div/div/div/div/div/a[1]/span"));
        e.click();
        Thread.sleep(5000);
        js.executeScript("window.scrollBy(0,100)");
        driver.findElement(By.xpath("//*[@id='rewards-modal-link']"));
        WebElement check2 =
        driver.findElement(By.xpath("//*[@id=\"dialog-title\"]"));
        if (check2.getText().contains("Sign in or Create an Account"))
        log.info("Sign in first!!");
        log.info("TestCase 3 passed Successfully!");

    }

    @AfterTest
    public void finish() {
        reports.flush();
        driver.quit();
    }
}

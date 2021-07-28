package utils;

import static config.ConfigSingle.cfg;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.List;
import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeTest;


public class BaseConfigurationTest {

    public static WebDriver driver;
    public static final Logger logger = LogManager.getLogger();
    public static WebDriverWait wait;
    public static JavascriptExecutor js;
    public static Actions action;
    public static FileInputStream fis;
    public static Properties property;

    @BeforeSuite
    public void beforeSuite()
    {
        //настраиваем драйвер
        driver = DriversManager.getDriver();

        //логирование
        logger.info("Log4j2ExampleApp started.");
        logger.warn("Something to warn");
        logger.error("Something failed.");
        try {
            Files.readAllBytes(Paths.get("/file/does/not/exist"));
        } catch (IOException ioex) {
            logger.error("Error message", ioex);
        }
    }

    @BeforeTest
    public void beforeTest() throws Exception {
        //настраиваем ожидание
        wait = new WebDriverWait(driver,10);

        //настройка для работы с js
        js =  ((JavascriptExecutor) driver);
        action = new Actions(driver);

        //property
        property = new Properties();
        fis = new FileInputStream("src/test/resources/config.properties");
        property.load(fis);

        //Открываем браузер
        driver.get(cfg.getBaseUriProperties());
    }

    @BeforeMethod
    public void beforeMethod()
    {

    }


    @AfterMethod
    public void afterMethod(){

    }

    @AfterTest
    public void afterTest() throws Exception{
        try {
            //Удаляем новость
            List<WebElement> deleteButton=driver.findElements(By.xpath("//i[@class='ico icon-close']"));
            deleteButton.get(0).click();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(text(), 'Да ')]")));
            driver.findElement(By.xpath("//button[contains(text(), 'Да ')]"))
                .click();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            DriversManager.stopDriver();
        }

    }

}

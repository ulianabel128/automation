package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class BaseConfigurationTest {

    public static WebDriver driver;
    private static final Logger logger = LogManager.getLogger();

    @BeforeSuite
    public void beforeSuite()
    {
        //настраиваем драйвер
        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
        driver = new ChromeDriver();

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

    @BeforeMethod
    public void beforeMethod()
    {

    }

    @AfterMethod
    public void afterMethod(){

    }

}

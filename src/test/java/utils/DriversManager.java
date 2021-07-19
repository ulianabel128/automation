package utils;

import org.openqa.selenium.WebDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.stqa.selenium.factory.WebDriverPool;

public class DriversManager {
    public static WebDriver driver;

    public static WebDriver getDriver(){
        WebDriverManager.chromedriver().setup();
        driver = WebDriverPool.DEFAULT
                .getDriver(new ChromeOptions().addArguments("--allow-silent-push"));
        driver.manage().window().maximize();
        return driver;
    }

    public static void stopDriver() {
        WebDriverPool.DEFAULT.dismissAll();
    }
}

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class SuperadminTests {

    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;
    Actions action;
    FileInputStream fis;
    Properties property;

    private static final Logger logger = LogManager.getLogger();

    @BeforeTest
    public void beforeTest() throws Exception{
        //настраиваем драйвер
        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
        driver = new ChromeDriver();

        //настраиваем ожидание
        wait = new WebDriverWait(driver,10);

        //настройка для работы с js
        js =  ((JavascriptExecutor) driver);
        action = new Actions(driver);

        //property
        property = new Properties();
        fis = new FileInputStream("src/main/resources/config.properties");
        property.load(fis);

        //логирование
        logger.info("Log4j2ExampleApp started.");
        logger.warn("Something to warn");
        logger.error("Something failed.");
        try {
            Files.readAllBytes(Paths.get("/file/does/not/exist"));
        } catch (IOException ioex) {
            logger.error("Error message", ioex);
        }

        //Открываем браузер
        driver.get(property.getProperty("test.url"));
        driver.manage().window().maximize();
    }

    @Test
    public void creationNews() {
        //кнопка "Войти в кабинет"
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".button.lower.c-dark.c-blue.login-menu.ng-star-inserted")));
        driver.findElement(By.cssSelector(".button.lower.c-dark.c-blue.login-menu.ng-star-inserted")).click();

        //ввод логина и пароля
        driver.findElement(By.id("username")).sendKeys("16067057157");
        driver.findElement(By.id("password")).sendKeys("123");

        //кнопка "Войти"
        driver.findElement(By.name("bsubmit")).click();

        //ЛК
        wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Колташева Александра")));
        driver.findElement(By.linkText("Колташева Александра")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Личный кабинет")));
        driver.findElement(By.linkText("Личный кабинет")).click();

        //Переключение роли
        wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Мои роли")));
        driver.findElement(By.linkText("Мои роли")).click();
        driver.findElement(By.id("role1")).click();
        driver.findElement(By.cssSelector("app-account-lk-role > div > div > a")).click();

        //Переход к панели администратора
        wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Панель администратора")));
        driver.findElement(By.linkText("Панель администратора")).click();

        //Переход к созданию новости
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.linkText("Страницы")));
        {
            WebElement element = driver.findElement(By.linkText("Страницы"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element, 0, 0).perform();
        }
        driver.findElement(By.xpath("/html/body/app-root/div/app-admin-panel/main/div/div/div[2]/div/app-dashboard/p-accordion/div/p-accordiontab/div[2]/div/div/div/app-setting-element[3]/div/div/div[2]/div/a"))
                .click();

        //Создание новости
        wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Новости")));
        driver.findElement(By.cssSelector(".ui-inputtext.ui-corner-all.ui-state-default.ui-widget.ng-untouched.ng-pristine.ng-invalid"))
                .sendKeys("heading1");
        driver.findElement(By.cssSelector(".ui-dropdown-trigger.ui-state-default.ui-corner-right.ng-tns-c57-18"))
                .click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/app-root/div/app-admin-panel/main/div/div/div[2]/div/app-news-edit/app-save-element/div[2]/div/div[2]/p-dropdown/div/div[4]/div/ul/p-dropdownitem[2]/li")));
        driver.findElement(By.xpath("/html/body/app-root/div/app-admin-panel/main/div/div/div[2]/div/app-news-edit/app-save-element/div[2]/div/div[2]/p-dropdown/div/div[4]/div/ul/p-dropdownitem[2]/li"))
                .click();

        //Кнопка "Сохранить"
        {
            WebElement element = driver.findElement(By.linkText("Сохранить"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element, 0, 0).perform();
        }
        driver.findElement(By.linkText("Сохранить")).click();

        //Фильтруем по столбцу "Создано"
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/app-root/div/app-admin-panel/main/div/div/div[2]/div/app-news-list/app-list/p-table/div/div[1]/table/thead/tr[1]/th[3]")));
        action.doubleClick(driver
                .findElement(By.xpath("/html/body/app-root/div/app-admin-panel/main/div/div/div[2]/div/app-news-list/app-list/p-table/div/div[1]/table/thead/tr[1]/th[3]")))
                .perform();

        //Проверяем, что первый элемент в списке - созданная новость
        String heading = driver.findElement(By.xpath("/html/body/app-root/div/app-admin-panel/main/div/div/div[2]/div/app-news-list/app-list/p-table/div/div[1]/table/tbody/tr[1]/td[2]"))
                .getText();
        boolean ok = heading.equals("heading1");
        Assert.assertTrue(ok);
    }

    @AfterTest
    public void afterTes() throws Exception{
        try {
            //Удаляем новость
            driver.findElement(By.xpath("/html/body/app-root/div/app-admin-panel/main/div/div/div[2]/div/app-news-list/app-list/p-table/div/div[1]/table/tbody/tr[1]/td[7]/a[4]"))
                    .click();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/app-root/div/app-admin-panel/main/div/div/div[2]/div/app-news-list/app-list/p-confirmdialog/div/div/div[3]/p-footer/button[1]")));
            driver.findElement(By.xpath("/html/body/app-root/div/app-admin-panel/main/div/div/div[2]/div/app-news-list/app-list/p-confirmdialog/div/div/div[3]/p-footer/button[1]"))
                    .click();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            //Закрывем браузер
            driver.quit();
        }

    }
}

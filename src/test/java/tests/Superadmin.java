package tests;

import static config.ConfigSingle.cfg;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.*;

import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Superadmin extends BaseConfigurationTest{

    @Test
    public void creationNews() {
        //кнопка "Войти в кабинет"
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text(), 'Войти в кабинет')]")));
        driver.findElement(By.xpath("//a[contains(text(), 'Войти в кабинет')]")).click();

        //ввод логина и пароля
        driver.findElement(By.id("username")).sendKeys(property.getProperty("user.login"));
        driver.findElement(By.id("password")).sendKeys(property.getProperty("user.password"));

        //кнопка "Войти"
        driver.findElement(By.id("btn")).click();

        //ЛК
        //driver.manage().timeouts().pageLoadTimeout(Integer.parseInt(property.getProperty("page.time").trim()), TimeUnit.MILLISECONDS);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//a[contains(text(), 'Колташева Александра ')]")));
        driver.findElement(By.xpath("//a[contains(text(), 'Колташева Александра ')]")).click();
        driver.manage().timeouts().implicitlyWait(Integer.parseInt(property.getProperty("element.time").trim()), TimeUnit.MILLISECONDS);
        driver.findElement(By.xpath("//span[contains(text(), 'Личный кабинет')]")).click();

        //Переключение роли
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text(), 'Мои роли')]")));
        driver.findElement(By.xpath("//a[contains(text(), 'Мои роли')]")).click();
        driver.findElement(By.id("role1")).click();
        driver.findElement(By.xpath("//a[contains(text(), 'Перейти')]")).click();

        //Переход к панели администратора
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text(), 'Панель администратора')]")));
        driver.findElement(By.xpath("//a[contains(text(), 'Панель администратора')]")).click();

        //Переход к созданию новости
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//span[contains(text(), 'Страницы')]")));
        List<WebElement> addNew=driver.findElements(By.xpath("//a[contains(text(), 'Добавить новый')]"));
        addNew.get(2).click();

        //Создание новости
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[contains(text(), 'Новости')]")));
        driver.findElement(By.xpath("//input[@class='ui-inputtext ui-corner-all ui-state-default ui-widget ng-untouched ng-pristine ng-invalid']"))
                .sendKeys("heading1");
        driver.findElement(By.xpath("//div[@class='ng-tns-c57-17 dropdown ui-dropdown ui-widget ui-state-default ui-corner-all ui-dropdown-clearable']"))
                .click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='ui-dropdown-items-wrapper ng-tns-c57-17']")));
        driver.findElement(By.xpath("//li[starts-with(@aria-label, 'Общее')]"))
                .click();

        //Кнопка "Сохранить"
        driver.findElement(By.xpath("//a[contains(text(), 'Сохранить')]")).click();

        //Фильтруем по столбцу "Создано"
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//th[contains(text(), 'Создано')]")));
        action.doubleClick(driver
                .findElement(By.xpath("//th[contains(text(), 'Создано')]")))
                .perform();

        //Проверяем, что первый элемент в списке - созданная новость
        try {
            String heading = driver.findElement(By.xpath("//td[contains(text(), 'heading')]"))
                    .getText();
            Assert.assertTrue(heading.equals("heading1"));
        }
        catch (Exception e) {
            logger.info(e.getMessage());
        }
    }

}

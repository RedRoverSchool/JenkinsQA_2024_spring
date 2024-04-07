package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

@Ignore
public class GroupCompanionsTest extends BaseTest {
    @Test
    public void testExp86() {
        WebDriver driver = getDriver();
        driver.get("https://exp86.ru/");

        WebElement textBox = driver.findElement(By.name("q"));
        textBox.sendKeys("Hello world!");

//        How find element "Личный кабинет"
        WebElement registrationHrev = driver.findElement(By.linkText("Личный кабинет"));
        registrationHrev.click();

        WebElement text = driver.findElement(By.className("form_default_star"));
        String value = text.getText();

        Assert.assertEquals("* обязательные поля для заполнения", value);
    }

        @Test
    public void testFindElement() {

        WebDriver driver = getDriver();
        driver.get("https://exp86.ru");

        WebElement test = driver.findElement(By.xpath("//input[@type='text']"));
        test.sendKeys("Привет, мир!");

        WebElement button = driver.findElement(By.xpath("//a[@href='/user/']"));
        button.click();

        WebElement string = driver.findElement(By.className("form_default_star"));
        String result = string.getText();

        Assert.assertEquals("* обязательные поля для заполнения", result);

    }

    @Test
    public void testKg312(){
        WebDriver driver = getDriver();
        driver.get("https://312.kg/");

        WebElement buttonBlog = driver.findElement(By.linkText("Знаете ли Вы?"));
        buttonBlog.click();

        WebElement text = driver.findElement(By.xpath("/html/body/div[1]/div/div/div/section/h1"));
        String value = text.getText();

        Assert.assertEquals("Блог", value);
    }

    @Test
    public void testDZ() throws InterruptedException {
        WebDriver driver = getDriver();
        driver.get("https://exp86.ru/");

        WebElement text = driver.findElement(By.name("q"));
        text.sendKeys("Hello world");


        WebElement button = driver.findElement(By.name("Поиск"));
        button.click();


        WebElement linc = driver.findElement(By.className("notetext"));
        String result = linc.getText();

        Assert.assertEquals(result, "К сожалению, на ваш поисковый запрос ничего не найдено.");

    }
}

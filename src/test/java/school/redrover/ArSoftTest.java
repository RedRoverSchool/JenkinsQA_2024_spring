package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ArSoftTest {

    public static final String URL = "http://23.105.246.172:5000/login";
    public static final String INPUT_PASSWORD = "//input[@class='ant-input']";
    public static final String INPUT_EMAIL = "//input[@class='ant-input primaryInput  not-entered']";
    public static final String BTN_PASSWORD = "//button[@class='ant-btn ant-btn-default authButton big colorPrimary ']";
    public static final String EMAIL = "n-k-65@list.ru";
    private final By getPaswordText = By.xpath("//h2[@class='ant-typography h2_m RestorePassword__sendSuccess-text'][contains(.,'Мы отправили по адресу')]");
    private final By getErrorText = By.xpath("//div[@style='text-align: center; margin-bottom: 20px; color: rgb(255, 0, 0);']");

    WebDriver driver = new ChromeDriver();


    @Test
    public void testRemovePasword() throws InterruptedException {

        driver.get(URL);
        driver.manage().window().setSize(new Dimension(1820,1080));
        Thread.sleep(1000);

        driver.findElement(By.xpath(INPUT_EMAIL)).sendKeys("yyyyyyyyyy@mail.xx");

        driver.findElement(By.xpath(BTN_PASSWORD)).click();
        Thread.sleep(1000);

        String getError = driver.findElement(getErrorText).getText();

        Assert.assertEquals(getError,"Неправильный логин или пароль");

    }

    @Test
    public void testRega() throws InterruptedException {

        driver.get(URL);
        driver.manage().window().setSize(new Dimension(1820,1080));
        Thread.sleep(1000);

        driver.findElement(By.xpath("//h2[@class='ant-typography h2_m Login__restore-text']")).click();

        driver.findElement(By.xpath(INPUT_EMAIL)).sendKeys(EMAIL);

        driver.findElement(By.xpath(BTN_PASSWORD)).click();
        Thread.sleep(1000);

        String getPasError = driver.findElement(getPaswordText).getText();

        Assert.assertEquals(getPasError,"Мы отправили по адресу n-k-65@list.ru ссылку для восстановления доступа");

    }

    @Test
    public void testReg() {

    driver.get(URL);

    }
}

package school.redrover;

import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import java.util.ArrayList;


public class ArMobileTest extends BaseTest {

    private static final String URL = "http://23.105.246.172:5000/login";
    private static final String INPUT_PASSWORD = "//input[@class='ant-input']";
    private static final String INPUT_EMAIL = "//input[@class='ant-input primaryInput  not-entered']";
    private static final String BTN_PASSWORD = "//button[@class='ant-btn ant-btn-default authButton big colorPrimary ']";
    private static final String EMAIL = "n-k-65@list.ru";
    private final By getPaswordText = By.xpath("//h2[@class='ant-typography h2_m RestorePassword__sendSuccess-text'][contains(.,'Мы отправили по адресу')]");
    private final By getErrorText = By.xpath("//div[@style='text-align: center; margin-bottom: 20px; color: rgb(255, 0, 0);']");
    private final By newProgectNameText = By.xpath("//div[@class='Sidebar__project-name'][contains(.,'1Новый проект')]");
    private final By getPoliticaText = By.xpath("//h1[@class='page-header-title clr']");
    private final By getPoliticaUserText = By.xpath("//span[@style='font-size: 19px;'][contains(.,'Предмет пользовательского соглашения')]");
    private final By getBotText = By.xpath("//span[@dir='auto']");


    @Test
    public void testRemovePasword() throws InterruptedException {

        getDriver().get(URL);
        getDriver().manage().window().setSize(new Dimension(1920,1080));
        Thread.sleep(1000);

        getDriver().findElement(By.xpath(INPUT_EMAIL)).sendKeys("yyyyyyyyyy@mail.xx");

        getDriver().findElement(By.xpath(BTN_PASSWORD)).click();
        Thread.sleep(1000);

        String getError = getDriver().findElement(getErrorText).getText();

        Assert.assertEquals(getError,"Неправильный логин или пароль");
    }

    @Test
    public void testRega() throws InterruptedException {

        getDriver().get(URL);
        getDriver().manage().window().setSize(new Dimension(1920,1080));
        Thread.sleep(1000);

        getDriver().findElement(By.xpath("//h2[@class='ant-typography h2_m Login__restore-text']")).click();

        getDriver().findElement(By.xpath(INPUT_EMAIL)).sendKeys(EMAIL);

        getDriver().findElement(By.xpath(BTN_PASSWORD)).click();
        Thread.sleep(1000);

        String getPasError = getDriver().findElement(getPaswordText).getText();

        Assert.assertEquals(getPasError,"Мы отправили по адресу n-k-65@list.ru ссылку для восстановления доступа");
    }

    @Test
    public void testHrefPoluticUser() throws InterruptedException{

        getDriver().get(URL);

        getDriver().manage().window().setSize(new Dimension(1920,1080));
        Thread.sleep(1000);

        getDriver().findElement(By.xpath("//a[@href='https://vr-arsoft.com/user-agreement-armobail/']")).click();

        ArrayList<String> newTab = new ArrayList<>(getDriver().getWindowHandles());
        getDriver().switchTo().window(newTab.get(1));
        Thread.sleep(1000);

        String getPoliticaUser = getDriver().findElement(getPoliticaUserText).getText();

        Assert.assertEquals("Предмет пользовательского соглашения", getPoliticaUser);
        getDriver().quit();
    }

    @Test
    public void testCreateProgect() throws InterruptedException {

        getDriver().get(URL);
        getDriver().manage().window().setSize(new Dimension(1920,1080));
        Thread.sleep(1000);

        getDriver().findElement(By.xpath(INPUT_EMAIL)).sendKeys(EMAIL);
        getDriver().findElement(By.xpath(INPUT_PASSWORD)).sendKeys("qwe13567");
        getDriver().findElement(By.xpath(BTN_PASSWORD)).click();

        Thread.sleep(9000); //  сайт тормоз не убирать sleep ===


        getDriver().findElement(By.xpath("//button[@class='ant-btn ant-btn-default primaryButton big colorPrimary ']")).click();
        Thread.sleep(1000);

        getDriver().findElement(By.xpath("//input[@class='ant-input primaryInput  not-entered']")).sendKeys("1Новый проект");
        Thread.sleep(1000);

        getDriver().findElement(By.xpath("//input[@id='CreateProjectForm_city']")).sendKeys("Самара");
        Thread.sleep(1000);

        getDriver().findElement(By.xpath("//input[@id='CreateProjectForm_country']")).sendKeys("РФ");
        Thread.sleep(1000);

        getDriver().findElement(By.xpath("//textarea[@id='CreateProjectForm_street']")).sendKeys("Победы");
        Thread.sleep(1000);

        getDriver().findElement(By.xpath("//input[@id='CreateProjectForm_postalCode']")).sendKeys("444444");
        Thread.sleep(1000);

        getDriver().findElement(By.xpath("//input[@id='CreateProjectForm_code']")).sendKeys("123");
        Thread.sleep(1000);

        getDriver().findElement(By.xpath("//input[@id='CreateProjectForm_building']")).sendKeys("121");
        Thread.sleep(1000);

        getDriver().findElement(By.xpath("//input[@id='CreateProjectForm_office']")).sendKeys("117");
        Thread.sleep(1000);

        getDriver().findElement(By.xpath("//input[@id='CreateProjectForm_startDate']")).click();
        Thread.sleep(1000);

        WebElement NewData = getDriver().findElement(By.xpath("//a[@class='ant-picker-today-btn']"));
        Thread.sleep(1000);
        NewData.click();

        WebElement EndData = getDriver().findElement(By.xpath("//input[@id='CreateProjectForm_endDate']"));
        EndData.sendKeys("26-05-2024");
        Thread.sleep(1000);
        EndData.sendKeys(Keys.ENTER);

        getDriver().findElement(By.xpath("//button[@class='ant-btn ant-btn-default primaryButton big colorPrimary ']")).click();
        Thread.sleep(4000);

        getDriver().findElement(By.xpath("//div[@class='ant-typography ant-typography-ellipsis ant-typography-single-line ant-typography-ellipsis-single-line p_r']")).click();
        Thread.sleep(2000);

        getDriver().findElement(By.xpath("//a[@style='color: inherit;'][contains(.,'Настройки')]")).click();

        String newProgectName = getDriver().findElement(newProgectNameText).getText();

        Assert.assertEquals("1Новый проект", newProgectName);

        getDriver().findElement(By.xpath("//button[@class='ant-btn ant-btn-default primaryButton big colorRed ']")).click();
        Thread.sleep(2000);

        getDriver().findElement(By.xpath("//button[@class='ant-btn ant-btn-default primaryButton big colorPrimary '][contains(.,'Подтвердить')]")).click();
    }

    @Test
    public void testHrefPolitic() throws InterruptedException {

        getDriver().get(URL);
        getDriver().manage().window().setSize(new Dimension(1920,1080));
        Thread.sleep(1000);

        getDriver().findElement(By.xpath("//a[@href='https://vr-arsoft.com/personal-data-processing-policy/']")).click();
        Thread.sleep(1000);

        ArrayList<String> newTab = new ArrayList<>(getDriver().getWindowHandles());
        getDriver().switchTo().window(newTab.get(1));
        String getErr = getDriver().findElement(getPoliticaText).getText();

        Assert.assertEquals("Политика обработки персональных данных", getErr);
    }

    @Test
    public void testHrefBot() throws InterruptedException {

        getDriver().get(URL);
        getDriver().manage().window().setSize(new Dimension(1920,1080));
        Thread.sleep(3000);

        getDriver().findElement(By.xpath(INPUT_EMAIL)).sendKeys(EMAIL);

        getDriver().findElement(By.xpath(INPUT_PASSWORD)).sendKeys("qwe13567");
        getDriver().findElement(By.xpath(BTN_PASSWORD)).click();
        Thread.sleep(1000);

        getDriver().findElement(By.xpath("//a[@href='https://t.me/arsoft_support_bot']")).click();
        Thread.sleep(1000);

        ArrayList<String> newTab = new ArrayList<>(getDriver().getWindowHandles());
        getDriver().switchTo().window(newTab.get(1));
        String getBot = getDriver().findElement(getBotText).getText();

        Assert.assertEquals("AR SOFT support", getBot);
    }
}

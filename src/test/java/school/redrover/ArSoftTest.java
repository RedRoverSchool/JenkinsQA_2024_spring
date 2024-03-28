package school.redrover;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
//import org.testng.annotations.AfterMethod;
//import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class ArSoftTest {

    public static final String URL = "http://23.105.246.172:5000/login";
    public static final String INPUT_PASSWORD = "//input[@class='ant-input']";
    public static final String INPUT_EMAIL = "//input[@class='ant-input primaryInput  not-entered']";
    public static final String BTN_PASSWORD = "//button[@class='ant-btn ant-btn-default authButton big colorPrimary ']";
    public static final String EMAIL = "n-k-65@list.ru";
    private final By getPaswordText = By.xpath("//h2[@class='ant-typography h2_m RestorePassword__sendSuccess-text'][contains(.,'Мы отправили по адресу')]");
    private final By getErrorText = By.xpath("//div[@style='text-align: center; margin-bottom: 20px; color: rgb(255, 0, 0);']");
    private final By newProgectNameText = By.xpath("//div[@class='Sidebar__project-name'][contains(.,'1Новый проект')]");
    private final By getPoliticaText = By.xpath("//h1[@class='page-header-title clr']");

    WebDriver driver = new ChromeDriver();

//    @BeforeMethod
//    @AfterMethod

    @Test
    public void testRemovePasword() throws InterruptedException {

        driver.get(URL);
        driver.manage().window().setSize(new Dimension(1920,1080));
        Thread.sleep(1000);

        driver.findElement(By.xpath(INPUT_EMAIL)).sendKeys("yyyyyyyyyy@mail.xx");

        driver.findElement(By.xpath(BTN_PASSWORD)).click();
        Thread.sleep(1000);

        String getError = driver.findElement(getErrorText).getText();

        Assert.assertEquals(getError,"Неправильный логин или пароль");

        driver.quit();
    }

    @Test
    public void testRega() throws InterruptedException {

        driver.get(URL);
        driver.manage().window().setSize(new Dimension(1920,1080));
        Thread.sleep(1000);

        driver.findElement(By.xpath("//h2[@class='ant-typography h2_m Login__restore-text']")).click();

        driver.findElement(By.xpath(INPUT_EMAIL)).sendKeys(EMAIL);

        driver.findElement(By.xpath(BTN_PASSWORD)).click();
        Thread.sleep(1000);

        String getPasError = driver.findElement(getPaswordText).getText();

        Assert.assertEquals(getPasError,"Мы отправили по адресу n-k-65@list.ru ссылку для восстановления доступа");

        driver.quit();
    }

    @Test
    public void testReg() {

        driver.get(URL);

    }

    @Test
    public void testCreateProgect() throws InterruptedException {

        driver.get(URL);

        driver.manage().window().setSize(new Dimension(1920,1080));
        Thread.sleep(1000);
        driver.findElement(By.xpath(INPUT_EMAIL)).sendKeys(EMAIL);
        driver.findElement(By.xpath(INPUT_PASSWORD)).sendKeys("qwe13567");
        driver.findElement(By.xpath(BTN_PASSWORD)).click();
//  сайт тормоз не убирать sleep ================
        Thread.sleep(9000);
//=================================
        WebElement input = driver.findElement(By.xpath("//button[@class='ant-btn ant-btn-default primaryButton big colorPrimary ']"));
        input.click();
        Thread.sleep(1000);

        WebElement inp = driver.findElement(By.xpath("//input[@class='ant-input primaryInput  not-entered']"));
        inp.sendKeys("1Новый проект");
        Thread.sleep(1000);

        WebElement inp1 =driver.findElement(By.xpath("//input[@id='CreateProjectForm_city']"));
        inp1.sendKeys("Самара");
        Thread.sleep(1000);

        WebElement inp2 =driver.findElement(By.xpath("//input[@id='CreateProjectForm_country']"));
        inp2.sendKeys("РФ");
        Thread.sleep(1000);

        WebElement inp3 =driver.findElement(By.xpath("//textarea[@id='CreateProjectForm_street']"));
        inp3.sendKeys("Победы");
        Thread.sleep(1000);

        WebElement inp4 =driver.findElement(By.xpath("//input[@id='CreateProjectForm_postalCode']"));
        inp4.sendKeys("444444");
        Thread.sleep(1000);

        WebElement inp5 =driver.findElement(By.xpath("//input[@id='CreateProjectForm_code']"));
        inp5.sendKeys("123");
        Thread.sleep(1000);

        WebElement inp6 =driver.findElement(By.xpath("//input[@id='CreateProjectForm_building']"));
        inp6.sendKeys("121");
        Thread.sleep(1000);

        WebElement inp7 =driver.findElement(By.xpath("//input[@id='CreateProjectForm_office']"));
        inp7.sendKeys("117");
        Thread.sleep(1000);

        WebElement inp8 =driver.findElement(By.xpath("//input[@id='CreateProjectForm_startDate']"));
        inp8.click();
        Thread.sleep(1000);

        WebElement NewData = driver.findElement(By.xpath("//a[@class='ant-picker-today-btn']"));
        Thread.sleep(1000);
        NewData.click();

        WebElement EndData = driver.findElement(By.xpath("//input[@id='CreateProjectForm_endDate']"));
        EndData.sendKeys("26-05-2024");
        Thread.sleep(1000);

        EndData.sendKeys(Keys.ENTER);

        WebElement btn = driver.findElement(By.xpath("//button[@class='ant-btn ant-btn-default primaryButton big colorPrimary ']"));
        btn.click();
        Thread.sleep(4000);

        WebElement btnD = driver.findElement(By.xpath("//div[@class='ant-typography ant-typography-ellipsis ant-typography-single-line ant-typography-ellipsis-single-line p_r']"));
        btnD.click();
        Thread.sleep(2000);

//       ==============================кнопка отмены ===========================

//          WebElement textBoxAside = driver.findElement(By.xpath("//button[@class='Sidebar__collapsibleBtn']"));
//          textBoxAside.click();
//          Thread.sleep(3000);

        WebElement textSection = driver.findElement(By.xpath("//a[@style='color: inherit;'][contains(.,'Настройки')]"));
        textSection.click();

//        =========================== свернутый сайтбар ===============================

//           WebElement textSectionInput = driver.findElement(By.xpath("//input[@value='1Новый проект']"));
//           textSectionInput.click();
//           Thread.sleep(4000);



        String newProgectName = driver.findElement(newProgectNameText).getText();

        Assert.assertEquals("1Новый проект", newProgectName);

        WebElement btnDt = driver.findElement(By.xpath("//button[@class='ant-btn ant-btn-default primaryButton big colorRed ']"));
        btnDt.click();
        Thread.sleep(2000);



        WebElement btnDel = driver.findElement(By.xpath("//button[@class='ant-btn ant-btn-default primaryButton big colorPrimary '][contains(.,'Подтвердить')]"));
        btnDel.click();

        driver.quit();
    }

    @Test
    public void testHrefPolitic() throws InterruptedException {

        driver.get(URL);
        driver.manage().window().setSize(new Dimension(1920,1080));
        Thread.sleep(1000);
        driver.findElement(By.xpath("//a[@href='https://vr-arsoft.com/personal-data-processing-policy/']")).click();
        Thread.sleep(1000);

        ArrayList<String> newTab = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(newTab.get(1));
        String getErr = driver.findElement(getPoliticaText).getText();

        Assert.assertEquals("Политика обработки персональных данных", getErr);
        driver.quit();
    }

}

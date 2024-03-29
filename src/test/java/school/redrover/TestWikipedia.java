package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class TestWikipedia {

    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        // Установка пути к драйверу Chrome WebDriver
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\anast\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        // Инициализация экземпляра WebDriver
        driver = new ChromeDriver();
    }

    @Test
    public void testWikipediaSearch() {
        // Открыть страницу Wikipedia
        driver.get("https://en.wikipedia.org");

        // Найти поле ввода для поиска
        WebElement searchBox = driver.findElement(By.id("searchInput"));

        // Ввести запрос в поле поиска
        searchBox.sendKeys("Selenium (software)");

        // Найти кнопку отправки формы по текстовому содержимому
        WebElement submitButton = driver.findElement(By.xpath("//button[contains(@class, 'search-input')]"));

        // Нажать на кнопку отправки формы для выполнения поиска
        submitButton.click();

        // Подождать некоторое время для загрузки результатов поиска
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Проверить заголовок страницы
        String expectedTitle = "Selenium (software) - Wikipedia";
        String actualTitle = driver.getTitle();
        System.out.println("Page title is: " + actualTitle);
        // Проверка совпадения заголовка страницы с ожидаемым значением
        Assert.assertEquals(actualTitle, expectedTitle, "Page title doesn't match expected title");
    }

    @AfterClass
    public void tearDown() {
        // Закрыть браузер после выполнения всех тестов
        driver.quit();
    }
}

package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class UnderdogsGroupTest {
    @Test
    public void testDemoqaInput() {

        WebDriver driver = new ChromeDriver();

        driver.get("https://demoqa.com/");
        driver.findElement(By.id("close-fixedban")).click();
        driver.findElement(By.xpath("//div[@class='category-cards']/div[1]")).click();
        driver.findElement(By.xpath("//span[text()='Text Box']")).click();
        String name = "test";

        driver.findElement(By.id("userName")).sendKeys(name);
        driver.findElement(By.id("submit")).click();
        String result = driver.findElement(By.id("name")).getText();

        Assert.assertEquals(result, "Name:" + name);

        driver.quit();
    }

    @Test
    public void testElementsTextBox() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://demoqa.com/");
        driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div/div[1]")).click();
        driver.findElement(By.xpath("(//*[@id=\"item-0\"])[1]")).click();
        String email = "test@gmail.com";

        driver.findElement(By.id("userEmail")).sendKeys(email);
        driver.findElement(By.id("submit")).click();
        String result = driver.findElement(By.id("email")).getText();

        Assert.assertEquals(result, "Email:" + email);

        driver.quit();
    }

    @Test
    public void numberOfTheCarsPresented() {

        WebDriver driver = new ChromeDriver();
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://av.by/");
        List<String> carsManufacture = List.of("Alfa Romeo", "Dodge", "Infiniti", "Mercedes-Benz", "Skoda",
                "Audi", "Fiat", "Kia", "Mitsubishi", "Subaru","BMW","Ford","Lada (ВАЗ)","Nissan", "Tesla","Chevrolet",
                "Geely", "Land Rover", "Opel", "Toyota", "Chrysler", "Honda", "Lexus", "Peugeot","Volkswagen",
                "Citroen","Hyundai","Mazda","Renault","Volvo");
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//button[@class='button button--default button--block button--large']"))));
        driver.findElement(By.xpath("//button[@class='button button--default button--block button--large']")).click();
        ((JavascriptExecutor) driver).executeScript("javascript:window.scrollBy(0,500)");
        List<WebElement> carsModels = driver.findElements(By.xpath("//span[@class='catalog__title']"));
        System.out.println(carsModels.size());//must be 30 brands of cars that are presented on the page
        Assert.assertEquals(carsModels.size(), 30);
        for(int i=0;i < carsModels.size();i++){
            Assert.assertEquals(carsModels.get(i).getText() , carsManufacture.get(i));
        }
        driver.quit();
    }
}

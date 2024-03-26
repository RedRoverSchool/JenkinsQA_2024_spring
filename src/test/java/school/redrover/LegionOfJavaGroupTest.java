package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.openqa.selenium.By.className;

public class LegionOfJavaGroupTest {

    @Test
    public void testFerosorSearch() throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        try {
            driver.get("https://ferosor.cl");
            Thread.sleep(1000);
            WebElement textBox = driver.findElement(By.name("s"));
            textBox.sendKeys("alimento");
            driver.findElement(By.cssSelector("[type='submit']")).click();
            Thread.sleep(1000);
            driver.get("https://ferosor.cl/jardin-y-mascotas/262-alimento-para-perro-cachorro-fit-formula-saco-10-kg-130622000123.html");
            String result = String.valueOf(driver.findElement(By.className("h1")).getText());
            Assert.assertEquals(result, "Alimento para Perro cachorro FIT FORMULA Saco 10 kg");
        } finally {
            driver.quit();
        }
    }
    @Test
    public void testFerosorLogin() {
        WebDriver driver = new ChromeDriver();
        try{
            driver.get("https://ferosor.cl");
            driver.findElement(className("login")).click();
            WebElement email = driver.findElement(className("form-control"));
            email.sendKeys("test@test.com");
            WebElement password = driver.findElement(className("js-child-focus"));
            password.sendKeys("12345");
            driver.findElement(By.id("submit-login")).click();
            String result = String.valueOf(driver.findElement(By.className("page-header")).getText());
            Assert.assertEquals(result, "Su cuenta");
        }finally{
            driver.quit();
        }
    }

}

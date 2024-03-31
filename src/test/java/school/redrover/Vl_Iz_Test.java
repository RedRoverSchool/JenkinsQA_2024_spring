package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class Vl_Iz_Test {

    @Test
    public void testHelpDesk() {

        WebDriver driver = new ChromeDriver();
        driver.get("https://at-sandbox.workbench.lanit.ru/");

        driver.findElement(By.xpath( "//select[@id= 'id_queue']//option[@value= '1']")).click();

        WebElement title = driver.findElement(By.id("id_title"));
        title.sendKeys("Проблема регистрации");

        WebElement description = driver.findElement(By.id("id_body"));
        description.sendKeys("Отказывается регистрировать пользователя");

        driver.findElement(By.id("id_due_date")).click();
        driver.findElement(By.xpath("/html/body/div[2]/table/tbody/tr[6]/td[1]/a")).click();

        WebElement email = driver.findElement(By.id("id_submitter_email"));
        email.sendKeys("go@gmail.com");

        driver.findElement(By.xpath("//button[@type = 'submit']")).click();

        WebElement result = driver.findElement(By.xpath("/html/body/div/div/div/table/caption"));
        result.isDisplayed();

        driver.quit();
    }


}

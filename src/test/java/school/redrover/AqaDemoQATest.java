package school.redrover;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

public class AqaDemoQATest extends AqaGroupBaseTest {
    @Test
    public void practiceFillFormTest() {
        driver.get("https://demoqa.com/automation-practice-form");

        driver.findElement(By.id("firstName")).sendKeys("Irina");
        driver.findElement(By.id("lastName")).sendKeys("Kuperman");
        driver.findElement(By.id("userEmail")).sendKeys("ama@ama.com");
        driver.findElement(By.cssSelector("[for=\"gender-radio-2\"")).click();
        driver.findElement(By.id("userNumber")).sendKeys("1234567890");

        scrollIntoView(driver.findElement(By.id("submit"))).click();
    }

}

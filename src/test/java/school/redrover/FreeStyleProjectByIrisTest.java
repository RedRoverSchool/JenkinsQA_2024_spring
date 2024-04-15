package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class FreeStyleProjectByIrisTest extends BaseTest {

    @Test
    public void testCreateFreestyleProject() {

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));

        WebElement text = wait.until(visibilityOfElementLocated(By.xpath("//h1[contains(text(),'Welcome to Jenkins!')]")));
        assertTrue(text.isDisplayed());

        wait.until(visibilityOfElementLocated(By.xpath("//*[@id='tasks']/div[1]/span/a"))).click();
        wait.until(visibilityOfElementLocated(By.className("jenkins-input"))).sendKeys("new Freestyle project");
        wait.until(visibilityOfElementLocated(By.xpath("//button[@type = 'submit']"))).click();
        wait.until(visibilityOfElementLocated(By.xpath("//span[contains(text(),'Freestyle project')]"))).click();
        wait.until(visibilityOfElementLocated(By.xpath("//button[@type= 'submit']"))).click();
        wait.until(visibilityOfElementLocated(By.xpath("//h1[contains(text(),'Configure')]")));
        wait.until(visibilityOfElementLocated(By.xpath("//button[contains(text(),'Save')]"))).click();
        WebElement textWebElement = wait.until(visibilityOfElementLocated(By.xpath("//h1[contains(text(),'new Freestyle project')]")));

        assertEquals(textWebElement.getText(), "new Freestyle project");
        assertTrue(textWebElement.isDisplayed());
    }
}

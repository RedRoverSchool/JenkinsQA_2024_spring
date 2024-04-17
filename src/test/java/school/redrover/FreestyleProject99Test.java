package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

import static org.testng.Assert.assertTrue;

public class FreestyleProject99Test extends BaseTest {

    private static final String PROJECT_NAME = "FreestyleProject";

    private void createNewProject(String name) {

        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys("FreestyleProject");
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//*[@id=\"bottom-sticker\"]/div/button[1]")).click();
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        Actions action = new Actions(getDriver());
    }

    @Test
    public void testCreatExistingFreestyleProject() {

        createNewProject(PROJECT_NAME);

        getDriver().findElement(By.linkText("Dashboard")).click();
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys("FreestyleProject");

        assertTrue(getDriver().findElement(By.id("itemname-invalid")).isDisplayed());
    }

    @Test
    public void testAddProjectDescription() {

        createNewProject(PROJECT_NAME);

        getDriver().findElement(By.linkText("Dashboard")).click();
        getDriver().findElement(By.xpath("//*[@id=\"job_FreestyleProject\"]/td[3]/a")).click();
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.xpath("//*[@id=\"description\"]/form/div[1]/div[1]/textarea")).sendKeys("Project " +
                "Description");
        getDriver().findElement(By.xpath("//*[@id=\"description\"]/form/div[2]/button")).click();

        assertTrue(getDriver().findElement(By.xpath("//*[@id=\"description\"]/div[1]")).isDisplayed());
    }

    @Test
    public void testRenameProjectToSameName() {

        createNewProject(PROJECT_NAME);

        getDriver().findElement(By.linkText("Dashboard")).click();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        Actions action = new Actions(getDriver());
        action.moveToElement(getDriver().findElement(By.linkText(PROJECT_NAME))).perform();

        WebElement clickButton = wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//*[@id=\"job_FreestyleProject\"]/td[3]/a/button"))));
        clickButton.click();
        getDriver().findElement(By.xpath("//a[contains(., 'Rename')]")).click();
        action.doubleClick(getDriver().findElement(By.name("newName"))).perform();;
        getDriver().findElement(By.name("newName")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.name("Submit")).click();

        assertTrue(getDriver().findElement(By.xpath("//*[text()='Error']")).isDisplayed());
    }
}
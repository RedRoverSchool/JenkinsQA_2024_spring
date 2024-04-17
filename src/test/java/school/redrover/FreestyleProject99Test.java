package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject99Test extends BaseTest {

    private static final String PROJECT_NAME = "FreestyleProject";

    private void createNewProject(String name) {

        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys("FreestyleProject");
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//*[@id=\"bottom-sticker\"]/div/button[1]")).click();
    }

    @Test
    public void testCreatExistingFreestyleProject() {

        createNewProject(PROJECT_NAME);

        getDriver().findElement(By.linkText("Dashboard")).click();
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys("FreestyleProject");

        Assert.assertTrue(getDriver().findElement(By.id("itemname-invalid")).isDisplayed());
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

        Assert.assertTrue(getDriver().findElement(By.xpath("//*[@id=\"description\"]/div[1]")).isDisplayed());
    }
}
package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;



public class FreestyleProjectCreate1Test extends BaseTest {
    private final static String FREESTYLE_PROJECT_NAME = "FreeStyleFirst";

    @Test
    public void testFreestyleProjectCreate() {

        getDriver().findElement(By.cssSelector("span.task-link-wrapper ")).click();
        getDriver().findElement(By.id("name")).sendKeys(FREESTYLE_PROJECT_NAME);
        getDriver().findElement(By.cssSelector("li.hudson_model_FreeStyleProject")).click();

        WebElement button = getDriver().findElement(By.id("ok-button"));
        button.click();

        getDriver().findElement(By.cssSelector("button[name='Submit']")).click();

        String freestyleName = getDriver().findElement(By.xpath("//h1[contains(@class,'job-index-headline')]")).getText();

        Assert.assertEquals(freestyleName, FREESTYLE_PROJECT_NAME);

    }

    @Ignore
    @Test(dependsOnMethods = "testFreestyleProjectCreate")
    public void testErrorMessage() {

        getDriver().findElement(By.cssSelector("span.task-link-wrapper ")).click();
        getDriver().findElement(By.id("name")).sendKeys(FREESTYLE_PROJECT_NAME);

        boolean isDisabled = getDriver().findElement(By.id("ok-button")).isEnabled();

        getDriver().findElement(By.id("items")).click();
        String errorMessage = getDriver().findElement(By.id("itemname-invalid")).getText();

        Assert.assertEquals(errorMessage,"» A job already exists with the name ‘FreeStyleFirst’");
        Assert.assertFalse(isDisabled);

    }

    @Test(dependsOnMethods = "testFreestyleProjectCreate")
    public void testDeleteProject() {

        WebElement project = getDriver().findElement(By.id("job_" + FREESTYLE_PROJECT_NAME ));

        TestUtils.openElementDropdown(this,project);
        WebElement deleteButton = getDriver().findElement(By.xpath("//button[@href='/job/" + FREESTYLE_PROJECT_NAME +"/doDelete']"));
        deleteButton.click();

        getDriver().switchTo().activeElement();
        getDriver().findElement(By.cssSelector("button[data-id='ok']")).click();

        String welcome = getDriver().findElement(By.xpath("//h1")).getText();

        Assert.assertEquals(welcome,"Welcome to Jenkins!");

    }

}

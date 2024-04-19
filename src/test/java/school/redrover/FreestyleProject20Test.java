package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.UUID;

public class FreestyleProject20Test extends BaseTest {
    final String projectName = "Freestyle-" + UUID.randomUUID();

    public void createItem(String itemName, String item) {
        getDriver().findElement(By.linkText("New Item")).click();

        getDriver().findElement(By.id("name")).sendKeys(itemName);

        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        Assert. assertEquals(getDriver().findElement(
                By.className("hudson_model_FreeStyleProject")).getAttribute("aria-checked"), "true",
                item + "is not checked");

        getDriver().findElement(By.id("ok-button")).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.name("Submit"))).click();
        Assert.assertEquals(getDriver().getCurrentUrl(), "http://localhost:8080/job/"+itemName+"/",
                item + "is not created");
        getDriver().findElement(By.linkText("Dashboard")).click();
    }

    @Test
    public void testCreateFreestyleProject() {
        createItem(projectName,"Freestyle project");

        getDriver().findElement(By.linkText(projectName)).click();
        Assert.assertEquals(getDriver().findElement(By.cssSelector("#breadcrumbs > li:nth-child(3)")).getText(),
                projectName, "Wrong project is opened");
    }

    @Test(dependsOnMethods = "testCreateFreestyleProject")
    public void testAddDescription() {
        getDriver().findElement(By.linkText("Dashboard")).click();
        getDriver().findElement(By.linkText(projectName)).click();

        getDriver().findElement(By.linkText("Add description")).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.name("description"))).clear();
        getDriver().findElement(By.name("description")).sendKeys("Description for "+projectName);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id='description']/div[1]")).getText(),
                "Description for "+projectName);
    }
}

package school.redrover;

import org.openqa.selenium.By;
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
        getDriver().findElement(By.name("Submit")).click();
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
}

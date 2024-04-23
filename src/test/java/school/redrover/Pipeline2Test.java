package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Pipeline2Test extends BaseTest {

    private void createPipeline() {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.name("name")).sendKeys("Created Pipeline");
        getDriver().findElement(By.cssSelector("[class$='WorkflowJob']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
    }

    @Test
    public void testAddDescription() {
        createPipeline();
        getDriver().findElement(By.id("description-link")).click();

        String newDescription = "Lorem ipsum dolor sit amet, consectetur adipiscing elit";
        getDriver().findElement(By.name("description")).sendKeys(newDescription);
        getDriver().findElement(By.name("Submit")).click();
        String actualDescription = getDriver().findElement(By.cssSelector("#description>:first-child")).getText();

        Assert.assertEquals(actualDescription, newDescription);
    }
}

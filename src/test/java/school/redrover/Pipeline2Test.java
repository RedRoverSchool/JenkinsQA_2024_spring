package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Pipeline2Test extends BaseTest {

    private static final String PIPELINE_NAME = "Created Pipeline";
    private static final String DESCRIPTION = "Lorem ipsum dolor sit amet";
    private static final By CHANGE_DESCRIPTION_LOCATOR = By.id("description-link");
    private static final By DESCRIPTION_INPUT_LOCATOR = By.name("description");
    private static final By SAVE_BUTTON_LOCATOR = By.name("Submit");
    private static final By DISPLAYED_DESCRIPTION = By.cssSelector("#description>:first-child");

    private void createPipeline() {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.name("name")).sendKeys(PIPELINE_NAME);
        getDriver().findElement(By.cssSelector("[class$='WorkflowJob']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(SAVE_BUTTON_LOCATOR).click();
    }

    @Test
    public void testAddDescription() {
        createPipeline();
        getDriver().findElement(CHANGE_DESCRIPTION_LOCATOR).click();
        getDriver().findElement(DESCRIPTION_INPUT_LOCATOR).sendKeys(DESCRIPTION);
        getDriver().findElement(SAVE_BUTTON_LOCATOR).click();

        Assert.assertEquals(getDriver().findElement(DISPLAYED_DESCRIPTION).getText(), DESCRIPTION);
    }

    @Test(dependsOnMethods = "testAddDescription")
    public void testEditDescription() {
        final String addedToDescription = ", consectetur adipiscing elit.";
        final String expectedDescription = DESCRIPTION + addedToDescription;

        getDriver().findElement(By.xpath("//span[text()='" + PIPELINE_NAME + "']")).click();
        getDriver().findElement(CHANGE_DESCRIPTION_LOCATOR).click();
        getDriver().findElement(DESCRIPTION_INPUT_LOCATOR).click();
        getDriver().findElement(DESCRIPTION_INPUT_LOCATOR).sendKeys(addedToDescription);
        getDriver().findElement(SAVE_BUTTON_LOCATOR).click();

        Assert.assertEquals(getDriver().findElement(DISPLAYED_DESCRIPTION).getText(), expectedDescription);
    }

    @Test
    public void testVerifyNameInBreadcrumbs() {
        createPipeline();
        String nameInBreadcrumbs = getDriver().findElement(By.cssSelector("li > a[href^='/job/']")).getText();

        Assert.assertEquals(nameInBreadcrumbs, PIPELINE_NAME);
    }

    @Test
    public void testRenameJobViaSidebar() {
        final String newName = "New pipeline name";

        createPipeline();
        getDriver().findElement(By.cssSelector("a[href$='rename']")).click();
        getDriver().findElement(By.name("newName")).clear();
        getDriver().findElement(By.name("newName")).sendKeys(newName);
        getDriver().findElement(SAVE_BUTTON_LOCATOR).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("div > h1")).getText(), newName);
    }
}

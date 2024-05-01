package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Pipeline2Test extends BaseTest {

    private static final String PIPELINE_NAME = "Created Pipeline";
    private static final String NEW_PIPELINE_NAME = "New Pipeline name";
    private static final String DESCRIPTION = "Lorem ipsum dolor sit amet";
    private static final By CHANGE_DESCRIPTION_LOCATOR = By.id("description-link");
    private static final By DESCRIPTION_INPUT_LOCATOR = By.name("description");
    private static final By SAVE_BUTTON_LOCATOR = By.name("Submit");
    private static final By DISPLAYED_DESCRIPTION_LOCATOR = By.cssSelector("#description>:first-child");
    private static final By NAME_IN_BREADCRUMBS_LOCATOR = By.cssSelector("li > a[href^='/job/']");
    private static final By NEW_NAME_INPUT_LOCATOR = By.name("newName");
    private static final By RENAME_BUTTON_LOCATOR = By.cssSelector("a[href$='rename']");
    private static final By CHEVRON_LOCATOR = By.cssSelector("a[href^='/job'] > button");


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

        Assert.assertEquals(getDriver().findElement(DISPLAYED_DESCRIPTION_LOCATOR).getText(), DESCRIPTION);
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

        Assert.assertEquals(getDriver().findElement(DISPLAYED_DESCRIPTION_LOCATOR).getText(), expectedDescription);
    }

    @Test
    public void testVerifyNameInBreadcrumbs() {
        createPipeline();
        String nameInBreadcrumbs = getDriver().findElement(NAME_IN_BREADCRUMBS_LOCATOR).getText();

        Assert.assertEquals(nameInBreadcrumbs, PIPELINE_NAME);
    }

    @Test
    public void testRenameJobViaSidebar() {
        createPipeline();
        getDriver().findElement(RENAME_BUTTON_LOCATOR).click();
        getDriver().findElement(NEW_NAME_INPUT_LOCATOR).clear();
        getDriver().findElement(NEW_NAME_INPUT_LOCATOR).sendKeys(NEW_PIPELINE_NAME);
        getDriver().findElement(SAVE_BUTTON_LOCATOR).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("div > h1")).getText(), NEW_PIPELINE_NAME);
    }

    @Test
    public void testRenameJobViaBreadcrumbs() {
        createPipeline();

        new Actions(getDriver()).moveToElement(getDriver().findElement(NAME_IN_BREADCRUMBS_LOCATOR)).perform();

        int attempts = 0;
        while (attempts < 3) {
            try {
                getWait5().until(ExpectedConditions.visibilityOfElementLocated(CHEVRON_LOCATOR)).click();
                getDriver().findElement(By.cssSelector("[class*='dropdown'] [href$='rename']")).click();
                getDriver().findElement(NEW_NAME_INPUT_LOCATOR).clear();
                getDriver().findElement(NEW_NAME_INPUT_LOCATOR).sendKeys(NEW_PIPELINE_NAME);
                getDriver().findElement(SAVE_BUTTON_LOCATOR).click();
                Assert.assertEquals(getDriver().findElement(NAME_IN_BREADCRUMBS_LOCATOR).getText(), NEW_PIPELINE_NAME);
                break;
            } catch (Exception e) {
                attempts++;
            }
        }
    }
}

package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Pipeline2Test extends BaseTest {

    private static final String PIPELINE_NAME = "Created Pipeline";
    private static final String NEW_PIPELINE_NAME = "New Pipeline name";
    private static final By SAVE_BUTTON_LOCATOR = By.name("Submit");
    private static final By NAME_IN_BREADCRUMBS_LOCATOR = By.cssSelector("li > a[href^='/job/']");
    private static final By NEW_NAME_INPUT_LOCATOR = By.name("newName");
    private static final By CHEVRON_LOCATOR = By.cssSelector("a[href^='/job'] > button");

    private void createPipeline() {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.name("name")).sendKeys(PIPELINE_NAME);
        getDriver().findElement(By.cssSelector("[class$='WorkflowJob']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(SAVE_BUTTON_LOCATOR).click();
    }

    @Test
    public void testVerifyNameInBreadcrumbs() {
        createPipeline();
        String nameInBreadcrumbs = getDriver().findElement(NAME_IN_BREADCRUMBS_LOCATOR).getText();

        Assert.assertEquals(nameInBreadcrumbs, PIPELINE_NAME);
    }

    @Test
    public void testRenameJobViaBreadcrumbs() {
        createPipeline();

        WebElement dropdownArrow = getDriver().findElement(CHEVRON_LOCATOR);
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].dispatchEvent(new Event('mouseenter'));" +
                "arguments[0].dispatchEvent(new Event('click'));", dropdownArrow);

        getDriver().findElement(By.cssSelector("[class*='dropdown'] [href$='rename']")).click();
        getDriver().findElement(NEW_NAME_INPUT_LOCATOR).clear();
        getDriver().findElement(NEW_NAME_INPUT_LOCATOR).sendKeys(NEW_PIPELINE_NAME);
        getDriver().findElement(SAVE_BUTTON_LOCATOR).click();

        Assert.assertEquals(getDriver().findElement(NAME_IN_BREADCRUMBS_LOCATOR).getText(), NEW_PIPELINE_NAME);
    }
}

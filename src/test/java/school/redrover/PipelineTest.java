package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.model.PipelinePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;

public class PipelineTest extends BaseTest {

    private static final String PIPELINE_NAME = "FirstPipeline";
    private static final By ADD_DESCRIPTION_LOCATOR = By.id("description-link");
    private static final By DASHBOARD_PIPELINE_LOCATOR = By.cssSelector("td [href='job/" + PIPELINE_NAME + "/']");
    private static final By BUILD_HISTORY_PIPELINE_LOCATOR = By.cssSelector("td [href$='job/" + PIPELINE_NAME + "/']");
    private static final String DESCRIPTION = "Lorem ipsum dolor sit amet";

    private void createPipelineWithCreateAJob() {
        getDriver().findElement(By.linkText("Create a job")).click();
        getDriver().findElement(By.id("name")).sendKeys(PIPELINE_NAME);
        getDriver().findElement(By.cssSelector("[class$='WorkflowJob']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
    }

    private void clickOnDropdownArrow(By locator) {
        WebElement itemDropdownArrow = getDriver().findElement(locator);

        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].dispatchEvent(new Event('mouseenter'));" +
                "arguments[0].dispatchEvent(new Event('click'));", itemDropdownArrow);
    }

    @Test
    public void testPipelineDescriptionTextAreaBacklightColor() {
        String currentTextAreaBorderBacklightColor = new HomePage(getDriver())
                .resetJenkinsTheme()
                .clickLogo()
                .clickCreateAJob()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .clickChangeDescription()
                .waitAddDescriptionButtonDisappears()
                .getTextAreaBorderBacklightColor();

        Assert.assertEquals(currentTextAreaBorderBacklightColor, "rgba(11, 106, 162, 0.25)",
                "Current text area border backlight color is different");
    }

    @Test
    public void testPipelineDescriptionTextAreaBacklightDefaultColor() {
        new HomePage(getDriver())
                .resetJenkinsTheme()
                .clickLogo();

        createPipelineWithCreateAJob();
        getDriver().findElement(ADD_DESCRIPTION_LOCATOR).click();
        new Actions(getDriver()).sendKeys(Keys.TAB).perform();

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        String defaultTextAreaBorderBacklightColor = (String) js.executeScript(
                "return window.getComputedStyle(arguments[0]).getPropertyValue('--focus-input-glow');",
                getDriver().findElement(By.name("description")));

        Assert.assertEquals(defaultTextAreaBorderBacklightColor, "rgba(11,106,162,.25)");
    }

    @Test
    public void testYesButtonColorDeletingPipelineInSidebar() {
        new HomePage(getDriver())
                .resetJenkinsTheme()
                .clickLogo();

        createPipelineWithCreateAJob();
        getDriver().findElement(By.cssSelector("[data-title='Delete Pipeline']")).click();

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        String okButtonHexColor = (String) js.executeScript(
                "return window.getComputedStyle(arguments[0]).getPropertyValue('--color');",
                getDriver().findElement(By.xpath("//button[@data-id='ok']")));

        Assert.assertEquals(okButtonHexColor, "#e6001f", "The confirmation button color is not red");
    }

    @Test
    public void testDeleteViaBreadcrumbs() {
        createPipelineWithCreateAJob();
        TestUtils.goToMainPage(getDriver());

        getDriver().findElement(DASHBOARD_PIPELINE_LOCATOR).click();
        WebElement breadcrumbsItemName = getDriver().findElement(By.cssSelector("[class*='breadcrumbs']>[href*='job']"));

        new Actions(getDriver())
                .moveToElement(breadcrumbsItemName)
                .perform();
        clickOnDropdownArrow(By.cssSelector("[href^='/job'] [class$='dropdown-chevron']"));

        getDriver().findElement(By.cssSelector("[class*='dropdown'] [href$='Delete']")).click();
        getDriver().findElement(By.xpath("//button[@data-id='ok']")).click();

        List<WebElement> jobList = getDriver().findElements(DASHBOARD_PIPELINE_LOCATOR);
        Assert.assertTrue(jobList.isEmpty(), PIPELINE_NAME + " was not deleted");
    }

    @Ignore
    @Test
    public void testBuildHistoryEmptyUponPipelineRemoval() {
        createPipelineWithCreateAJob();
        TestUtils.goToMainPage(getDriver());

        getDriver().findElement(By.cssSelector("td [title='Schedule a Build for " + PIPELINE_NAME + "']")).click();
        getDriver().findElement(By.cssSelector("[href$='builds']")).click();

        new Actions(getDriver())
                .moveToElement(getDriver().findElement(BUILD_HISTORY_PIPELINE_LOCATOR))
                .perform();
        clickOnDropdownArrow(By.cssSelector("td [class$='link'] [class$='dropdown-chevron']"));

        getDriver().findElement(By.cssSelector("[href$='Delete']")).click();
        getDriver().findElement(By.xpath("//button[@data-id='ok']")).click();
        getDriver().findElement(By.cssSelector("[href$='builds']")).click();

        List<WebElement> buildHistoryTable = getDriver().findElements(BUILD_HISTORY_PIPELINE_LOCATOR);
        Assert.assertTrue(buildHistoryTable.isEmpty(), PIPELINE_NAME + " build is in Build history table");
    }

    @Test
    public void testAddDescription() {
        PipelinePage pipelinePage = new HomePage(getDriver())
                .clickCreateAJob()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .clickChangeDescription()
                .setDescription(DESCRIPTION)
                .clickSaveButton();

        Assert.assertEquals(pipelinePage.getDescriptionText(), DESCRIPTION);
    }
}

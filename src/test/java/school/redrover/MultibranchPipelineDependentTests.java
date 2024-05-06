package school.redrover;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

public class MultibranchPipelineDependentTests extends BaseTest {

    private static final String MULTI_PIPELINE_NAME = "MultibranchPipeline";
    private static final String NEW_MULTI_PIPELINE_NAME = "newMultibranchPipeline";
    private static final By MULTI_PIPELINE_ON_DASHBOARD_LOCATOR = By.cssSelector("[href='job/" + NEW_MULTI_PIPELINE_NAME + "/']");

    @Test

    public void testCreate() {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input']"))
                .sendKeys(MULTI_PIPELINE_NAME);
        getDriver().findElement(By.cssSelector("[class*='WorkflowMultiBranchProject']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
        WebElement actualMultibranchPipelineName = getDriver().findElement(By.xpath("//div[@id='breadcrumbBar']//li[3]"));

        Assert.assertEquals(actualMultibranchPipelineName.getText(), MULTI_PIPELINE_NAME);
        getDriver().findElement(By.id("jenkins-head-icon")).click();
    }

    @Test(dependsOnMethods = "testVerifyMpDisabledMessageColorOnStatusPage")
    public void testChangeFromDisableOnStatusPage() {
        getDriver().findElement(By.xpath("//span[text()='" + MULTI_PIPELINE_NAME + "']")).click();
        WebElement configureLink = getDriver().findElement(By.cssSelector(".task-link-wrapper [href$='configure']"));
        configureLink.click();
        if (getDriver().findElement(By.className("jenkins-toggle-switch__label__checked-title"))
                .isDisplayed()) {
            getDriver().findElement(By.cssSelector("[data-title*='Disabled']")).click();
        }
        getDriver().findElement(By.cssSelector("[name*='Submit']")).click();
        getDriver().findElement(By.id("jenkins-home-link")).click();

        getDriver().findElement(By.xpath("//span[text()='" + MULTI_PIPELINE_NAME + "']")).click();
        getDriver().findElement(By.xpath("//button[contains(., 'Enable')]")).click();
        List<WebElement> disabledMultiPipelineMessage = getDriver().findElements(
                By.xpath("//form[contains(., 'This Multibranch Pipeline is currently disabled')]"));

        Assert.assertEquals(disabledMultiPipelineMessage.size(), 0, "Disabled message is displayed!!!");
    }

    @Test(dependsOnMethods = "testChangeFromDisableOnStatusPage")
    public void testRenameOnTheSidebar() {
        getDriver().findElement(By.xpath("//span[text()='" + MULTI_PIPELINE_NAME + "']")).click();
        getDriver().findElement(By.cssSelector("[href $='rename']")).click();
        WebElement renameInput = getDriver().findElement(By.xpath("//input[@name='newName']"));
        renameInput.clear();
        renameInput.sendKeys(NEW_MULTI_PIPELINE_NAME);
        getDriver().findElement(By.name("Submit")).click();
        String multiPipelinePageHeading = getDriver().findElement(By.tagName("h1")).getText();

        Assert.assertEquals(multiPipelinePageHeading, NEW_MULTI_PIPELINE_NAME, "Wrong name");
    }

    @Test(dependsOnMethods = "testCreate")
    public void testVerifyMpDisabledOnStatusPage() {
        String disabledMessage = new HomePage(getDriver())
                .clickMPName(MULTI_PIPELINE_NAME)
                .clickDisableMultibranchPipeline()
                .getDisableMultibranchPipelineText();

        Assert.assertEquals(disabledMessage, "This Multibranch Pipeline is currently disabled");
    }

    @Test(dependsOnMethods = "testVerifyMpDisabledOnStatusPage")
    public void testVerifyMpDisabledMessageColorOnStatusPage() {
        getDriver().findElement(By.cssSelector("[href='job/" + MULTI_PIPELINE_NAME + "/']")).click();

        String messageColor = getDriver().findElement(By.id("enable-project")).getCssValue("color");
        Assert.assertEquals(messageColor, "rgba(254, 130, 10, 1)");
    }

    @Test(dependsOnMethods = "testRenameOnTheSidebar")
    public void testDeleteMpViaBreadcrumbs() {
        getDriver().findElement(MULTI_PIPELINE_ON_DASHBOARD_LOCATOR).click();

        WebElement dropdownArrow = getDriver().findElement(By.cssSelector("a[href^='/job'] > button"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].dispatchEvent(new Event('mouseenter'));" +
                "arguments[0].dispatchEvent(new Event('click'));", dropdownArrow);

        getDriver().findElement(By.cssSelector("[class*='dropdown'] [href$='doDelete']")).click();
        getDriver().findElement(By.cssSelector("[data-id='ok']")).click();

        List<WebElement> projectList = getDriver().findElements(MULTI_PIPELINE_ON_DASHBOARD_LOCATOR);
        Assert.assertTrue(projectList.isEmpty());
    }
}

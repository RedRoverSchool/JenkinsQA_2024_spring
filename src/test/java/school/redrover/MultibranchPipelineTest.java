package school.redrover;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import static school.redrover.runner.TestUtils.Job;

public class MultibranchPipelineTest extends BaseTest {

    private final String multiPipelineName = "MultibranchPipeline";
    private final String newMultiPipelineName = "NewMultibranchPipelineName";

    private void disableCreatedMultiPipeline(String multiPipelineName) {
        getDriver().findElement(By.xpath("//span[text()='" + multiPipelineName + "']")).click();
        WebElement configureLink = getDriver().findElement(By.cssSelector(".task-link-wrapper [href$='configure']"));
        configureLink.click();
        if (getDriver().findElement(By.className("jenkins-toggle-switch__label__checked-title"))
            .isDisplayed()) {
            getDriver().findElement(By.cssSelector("[data-title*='Disabled']")).click();
        }
        getDriver().findElement(By.cssSelector("[name*='Submit']")).click();
        getDriver().findElement(By.id("jenkins-home-link")).click();
    }

    private void createNewMultiPipeline(String multiPipelineName) {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(multiPipelineName);
        getDriver().findElement(By.cssSelector("[class*='WorkflowMultiBranchProject']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.id("jenkins-home-link")).click();
    }

    @Test
    public void testCreateMultibranchPipeline() {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input']"))
                   .sendKeys("First Multibranch Pipeline project");
        getDriver().findElement(By.xpath("//div[@id='j-add-item-type-standalone-projects']/ul/li[3]"))
                   .click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();

        WebElement actualMultibranchPipelineName = getDriver().findElement(By.xpath("//div[@id='breadcrumbBar']//li[3]"));

        Assert.assertEquals(actualMultibranchPipelineName.getText(),"First Multibranch Pipeline project");
    }

    @Test
    public void testCreateMultibranchPipelineWithEmptyName() {
        final String expectedErrorMessage = "» This field cannot be empty, please enter a valid name";

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.xpath("//div[@id='j-add-item-type-standalone-projects']/ul/li[3]"))
                   .click();
        WebElement okButton = getDriver().findElement(By.xpath("//button[@id='ok-button']"));
        WebElement actualErrorMessage = getDriver().findElement(By.xpath("//div[@id='itemname-required']"));

        Assert.assertFalse(okButton.isEnabled());
        Assert.assertTrue(actualErrorMessage.isDisplayed());
        Assert.assertEquals(actualErrorMessage.getText(),expectedErrorMessage);
    }

    @Test
    public void testRenameMultibranchPipeline() {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input']"))
                   .sendKeys("First Multibranch Pipeline project");
        getDriver().findElement(By.xpath("//div[@id='j-add-item-type-standalone-projects']/ul/li[3]"))
                   .click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
        getDriver().findElement(By.cssSelector("#breadcrumbs > li:nth-child(3")).click();
        getDriver().findElement(By.cssSelector("#tasks > div:nth-child(8) > span > a")).click();
        getDriver().findElement(By.cssSelector("input.jenkins-input.validated")).clear();
        getDriver().findElement(By.cssSelector("input.jenkins-input.validated"))
                   .sendKeys("New Multibranch Pipeline project");
        getDriver().findElement(By.xpath("//div[@id='bottom-sticker']//button")).click();

        WebElement newName = getDriver().findElement(By.xpath("//div[@id='main-panel']/h1"));

        Assert.assertEquals(newName.getText(), "Project" + " New Multibranch Pipeline project");
    }

    @Test
    public void testChangeMultiPipelineFromDisabledToEnabledOnStatusPage() {

        createNewMultiPipeline(multiPipelineName);
        disableCreatedMultiPipeline(multiPipelineName);

        getDriver().findElement(By.xpath("//span[text()='" + multiPipelineName + "']")).click();
        getDriver().findElement(By.xpath("//button[contains(., 'Enable')]")).click();
        List<WebElement> disabledMultiPipelineMessage = getDriver().findElements(
            By.xpath("//form[contains(., 'This Multibranch Pipeline is currently disabled')]"));

        Assert.assertEquals(disabledMultiPipelineMessage.size(), 0, "Disabled message is displayed!!!");
    }

    @Test
    public void testVerifyStatusToSwitchingEnableMultibranchPipeline() {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();

        getDriver().findElement(By.className("jenkins-input")).sendKeys("Muiltibranch Pipeline project");

        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_multibranch_WorkflowMultiBranchProject")).click();

        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.cssSelector("#toggle-switch-enable-disable-project > label")).click();

        WebElement footer = getDriver().findElement(By.xpath("//*[@id='footer']"));
        int deltaY = footer.getRect().y;
        new Actions(getDriver()).scrollByAmount(0, deltaY).perform();

        getDriver().findElement(By.xpath("//*[@id='bottom-sticker']/div/button[1]")).click();

        getDriver().findElement(By.xpath("//*[@id='enable-project']/button")).click();

        String foundText = getDriver().findElement(By.xpath("//*[@id='disable-project']/button")).getText();
        Assert.assertEquals(foundText, "Disable Multibranch Pipeline");
    }


    @Test
    public void testDisabledMultiPipelineTooltip() {
        WebDriverWait webDriverWait = new WebDriverWait(getDriver(), Duration.ofSeconds(2));
        final String tooltipText = "(No new builds within this Multibranch Pipeline will be executed until it is re-enabled)";

        createNewMultiPipeline(multiPipelineName);
        disableCreatedMultiPipeline(multiPipelineName);

        getDriver().findElement(By.xpath("//span[text()='" + multiPipelineName + "']")).click();
        getDriver().findElement(By.cssSelector("[href$='Pipeline/configure']")).click();
        WebElement disabledSpan = getDriver().findElement(By.cssSelector("[data-title*='Disabled']"));
        new Actions(getDriver()).moveToElement(disabledSpan).perform();
        WebElement tooltip = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className("tippy-box")));

        Assert.assertTrue(tooltip.isDisplayed());
        Assert.assertEquals(tooltip.getText(),tooltipText);
    }

    @Test
    public void testRenameMultibranchPipelineWithNameSameAsCurrent() {

        final String MULTIBRANCH_PIPELINE_NAME = "First Multibranch Pipeline project";
        final String expectedErrorMessage = "The new name is the same as the current name.";

        TestUtils.createJob(this, Job.MULTI_BRUNCH_PIPELINE, MULTIBRANCH_PIPELINE_NAME);

        getDriver().findElement(By.cssSelector("#breadcrumbs > li:nth-child(3")).click();
        getDriver().findElement(By.cssSelector("#tasks > div:nth-child(8) > span > a")).click();
        getDriver().findElement(By.xpath("//div[@id='bottom-sticker']//button")).click();

        WebElement actualErrorMessage = getDriver().findElement(By.xpath("//div[@id='main-panel']/p"));

        Assert.assertEquals(actualErrorMessage.getText(), expectedErrorMessage);
    }

    @Test
    public void testEnabledMultibranchPipelineOnConfigPage() {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("New Multibranch Pipeline");
        getDriver().findElement(By.cssSelector("[class*=MultiBranchProject]")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.cssSelector("[class*=toggle-switch__label]")).click();
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.cssSelector("[href*='Pipeline/configure']")).click();
        getDriver().findElement(By.cssSelector("[class*=toggle-switch__label]")).click();

        String statusToggle = getDriver().findElement(By.id("enable-disable-project")).getDomProperty("checked");
        Assert.assertEquals(statusToggle,"true");
    }

    @Test
    public void testRenameMultibranchPipelineOnTheSidebar() {
        createNewMultiPipeline(multiPipelineName);

        getDriver().findElement(By.xpath("//span[text()='" + multiPipelineName + "']")).click();
        getDriver().findElement(By.cssSelector("[href $='rename']")).click();
        WebElement renameInput = getDriver().findElement(By.xpath("//input[@name='newName']"));
        renameInput.clear();
        renameInput.sendKeys(newMultiPipelineName);
        getDriver().findElement(By.name("Submit")).click();
        String multiPipelinePageHeading = getDriver().findElement(By.tagName("h1")).getText();

        Assert.assertEquals(multiPipelinePageHeading, newMultiPipelineName, "Wrong name");
    }

    @Test
    public void testRenameMultibranchPipelineViaMainPageDropdownMenu() {
        createNewMultiPipeline(multiPipelineName);

        getDriver().findElement(By.id("jenkins-head-icon")).click();
        WebElement createdMultibranchPipeline = getDriver().findElement(By.xpath("//span[text()='" + multiPipelineName + "']"));
        new Actions(getDriver()).moveToElement(createdMultibranchPipeline).perform();
        WebElement dropdownChevron = getDriver().findElement(By.cssSelector("#job_" + multiPipelineName + " > td:nth-child(3) > a > button"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].dispatchEvent(new Event('mouseenter'));" +
            "arguments[0].dispatchEvent(new Event('click'));", dropdownChevron);
        getDriver().findElement(By.cssSelector("[href $='rename']")).click();

        WebElement renameInput = getDriver().findElement(By.xpath("//input[@checkdependson='newName']"));
        renameInput.clear();
        renameInput.sendKeys(newMultiPipelineName);
        getDriver().findElement(By.name("Submit")).click();

        String multiPipelinePageHeading = getDriver().findElement(By.tagName("h1")).getText();
        Assert.assertEquals(multiPipelinePageHeading, newMultiPipelineName,
            "The Multi Pipeline name is not equal to " + newMultiPipelineName);
    }
}

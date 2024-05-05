package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class PipelineProject6Test extends BaseTest {
    private static final String PIPELINE_NAME = "Pipeline";
    private static final String SUCCEED_BUILD_EXPECTED = "Finished: SUCCESS";
    private static final By BUILD_1 = By.cssSelector("[class$='-name'][href$='1/']");
    private static final By BUILD_2 = By.cssSelector("[href='/job/Pipeline/2/console']");
    private static final By CONSOLE_OUTPUT = By.cssSelector("[class$='output']");

    public Actions getActions() {
        return new Actions(getDriver());
    }

    public void createNewPipeline(String pipelineName){
        getWait5().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@href='/view/all/newJob']"))).click();
        getWait5().until(ExpectedConditions.presenceOfElementLocated(By.id("name"))).sendKeys(pipelineName);
        getDriver().findElement(By.xpath("//span[text()='Pipeline']")).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@name='Submit']"))).click();
    }

    public void goHomePage(){
        getWait5().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//li[@class='jenkins-breadcrumbs__list-item']"))).click();
    }

    private void goToConsoleOutput() {
        getDriver().findElement(By.cssSelector("[href$=console]")).click();
    }

    private void waitForPopUp() {
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[aria-describedby^='tippy']")));
    }

    @Test
    public void testFullStageViewDropDownMenu() {
        createNewPipeline(PIPELINE_NAME);
        goHomePage();

        WebElement chevron = getDriver().findElement(By.xpath("//a[@href='job/"+PIPELINE_NAME+"/']//button[@class='jenkins-menu-dropdown-chevron']"));
        JavascriptExecutor jsExecutor = (JavascriptExecutor) getDriver();

        jsExecutor.executeScript("arguments[0].dispatchEvent(new Event('mouseenter'));", chevron);
        jsExecutor.executeScript("arguments[0].dispatchEvent(new Event('click'));", chevron);

        getWait5().until(ExpectedConditions.elementToBeClickable(getDriver().findElement(
                    By.xpath("//a[contains(@href, 'workflow-stage')]")))).click();

        String expectedText = PIPELINE_NAME + " - Stage View";
        Assert.assertEquals(getWait5().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[@id='pipeline-box']/h2"))).getText(),expectedText);
    }

    @Ignore
    @Test
    public void testRunByBuildNowButton() {
        createNewPipeline(PIPELINE_NAME);

        getDriver().findElement(By.linkText("Build Now")).click();
        waitForPopUp();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(BUILD_1)).click();
        goToConsoleOutput();

        Assert.assertTrue(getDriver().findElement(CONSOLE_OUTPUT).getText().contains(SUCCEED_BUILD_EXPECTED));
    }

    @Ignore
    @Test(dependsOnMethods = "testRunByBuildNowButton")
    public void testRunBuildByTriangleButton() {
        getDriver().findElement(By.cssSelector("[title^='Schedule a Build']")).click();
        waitForPopUp();
        getDriver().findElement(By.cssSelector("[href='job/Pipeline/']")).click();

        getActions().moveToElement(getDriver().findElement(BUILD_2)).perform();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[aria-describedby='tippy-17']")));
        getDriver().findElement(BUILD_2).click();
        goToConsoleOutput();

        Assert.assertTrue(getDriver().findElement(CONSOLE_OUTPUT).getText().contains(SUCCEED_BUILD_EXPECTED));
    }
}


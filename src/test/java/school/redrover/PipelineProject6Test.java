package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class PipelineProject6Test extends BaseTest {
    private static final String PIPELINE_NAME = "Pipeline";
    private static final String SUCCEED_BUILD_EXPECTED = "Finished: SUCCESS";
    private static final String FAILED_BUILD_EXPECTED = "Finished: FAILURE";
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

    private void goToPipelinePage() {
        getDriver().findElement(By.cssSelector("[href='job/Pipeline/']")).click();
    }

    private void clickBuildNow() {
        getDriver().findElement(By.linkText("Build Now")).click();
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

    @Test
    public void testRunByBuildNowButton() {
        createNewPipeline(PIPELINE_NAME);

        clickBuildNow();
        waitForPopUp();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(BUILD_1)).click();
        goToConsoleOutput();

        Assert.assertTrue(getDriver().findElement(CONSOLE_OUTPUT).getText().contains(SUCCEED_BUILD_EXPECTED));
    }

    @Test(dependsOnMethods = "testRunByBuildNowButton")
    public void testRunBuildByTriangleButton() {
        getDriver().findElement(By.cssSelector("[title^='Schedule a Build']")).click();
        waitForPopUp();
        goToPipelinePage();

        getActions().moveToElement(getDriver().findElement(BUILD_2)).perform();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[aria-describedby='tippy-17']")));
        getDriver().findElement(BUILD_2).click();
        goToConsoleOutput();

        Assert.assertTrue(getDriver().findElement(CONSOLE_OUTPUT).getText().contains(SUCCEED_BUILD_EXPECTED));
    }

    @Test(dependsOnMethods = {"testRunByBuildNowButton", "testRunBuildByTriangleButton"})
    public void successfulBuildColor() {
        goToPipelinePage();
        String greenColor = "rgb(30, 166, 75)";
        String actualColor = getDriver().findElement(By.cssSelector("tr.build-row:nth-child(2) svg ellipse"))
                .getCssValue("stroke");

        Assert.assertEquals(actualColor, greenColor);
    }

    @Test
    public void testCreateErrorBuild() {
        createNewPipeline(PIPELINE_NAME);

        getDriver().findElement(By.cssSelector("[href$='/configure']")).click();
        Select definition = new Select(getDriver().findElement(By.cssSelector("[class$='section'] [class$='List']")));
        definition.selectByValue("1");
        Select csm = new Select(getDriver().findElement(By.cssSelector("[class$=' tr'] [class$='List']")));
        csm.selectByIndex(1);
        getDriver().findElement(By.cssSelector("[name='Submit']")).click();

        clickBuildNow();
        waitForPopUp();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(BUILD_1)).click();
        goToConsoleOutput();

        Assert.assertTrue(getDriver().findElement(CONSOLE_OUTPUT).getText().contains(FAILED_BUILD_EXPECTED));
    }
}
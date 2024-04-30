package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;

public class Pipeline1Test extends BaseTest {
    private static final String PIPELINE_NAME = "NewPipeline";

    private void createPipeline(String pipelineName) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(pipelineName);
    }

    private void chooseProjectAndClick(String projectName) {
        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//td/a[@href='job/"
                + projectName.replaceAll(" ", "%20") + "/']"))).click();
    }

    private void clickFullStageViewButton() {
        getWait5().until(ExpectedConditions.elementToBeClickable(getDriver().findElement(
                By.xpath("//a[contains(@href, 'workflow-stage')]")))).click();
    }

    private String getH1HeaderText() {
        return getWait5().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//h1"))).getText();
    }

    private String getH2HeaderText() {
        return getWait5().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//h2"))).getText();
    }

    private void sendScript(int number_of_stages) {
        String pipelineScript = "pipeline {\n" +
                "agent any\n\n" +
                "stages {\n";

        getDriver().findElement(By.className("ace_text-input")).sendKeys(pipelineScript);

        for (int i = 1; i <= number_of_stages; i++) {

            String stage = "\nstage(\'stage " + i + "\') {\n" +
                    "steps {\n" +
                    "echo \'test " + i + "\'\n";
            getDriver().findElement(By.className("ace_text-input")).sendKeys(stage);
            getDriver().findElement(By.className("ace_text-input")).sendKeys(Keys.ARROW_DOWN);
            getDriver().findElement(By.className("ace_text-input")).sendKeys(Keys.ARROW_DOWN);
        }
    }

    private String getColorOfPseudoElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        return (String) js.executeScript("return window.getComputedStyle(arguments[0], '::before').getPropertyValue('background-color');", element);
    }

    private void createPipelineProject(String pipelineProject) {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.name("name")).sendKeys(PIPELINE_NAME);
        getDriver().findElement(By.cssSelector("[class$='WorkflowJob']")).click();
        getDriver().findElement(By.id("ok-button")).click();
    }

    @Test
    public void testCreatePipeline() {
        createPipeline(PIPELINE_NAME);

        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//span[normalize-space()='NewPipeline']")).getText(),
                PIPELINE_NAME);
    }

    @Ignore
    @Test
    public void testCreatePipelineWithSameName() {
        createPipeline(PIPELINE_NAME);

        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        createPipeline(PIPELINE_NAME);

        Assert.assertEquals(
                getDriver().findElement(By.id("itemname-invalid")).getText(),
                "» A job already exists with the name ‘NewPipeline’");

    }

    @Test
    public void testVisibilityDisableButton() {
        TestUtils.createNewJob(this, TestUtils.Job.PIPELINE, "Pipeline1");
        getDriver().findElement(By.xpath("//table//a[@href='job/Pipeline1/']")).click();

        Assert.assertTrue(getDriver().findElement(By.name("Submit")).isDisplayed());

        getDriver().findElement(By.name("Submit")).click();

        String actualStatusMessage = getDriver().findElement(By.id("enable-project")).getText();

        Assert.assertTrue(actualStatusMessage.contains("This project is currently disabled"));
    }


    @Test
    public void testFullStageViewButton() {
        TestUtils.createItem(TestUtils.PIPELINE, PIPELINE_NAME, this);
        TestUtils.goToMainPage(getDriver());

        String expectedResult = PIPELINE_NAME + " - Stage View";

        chooseProjectAndClick(PIPELINE_NAME);
        clickFullStageViewButton();

        Assert.assertEquals(getH2HeaderText(), expectedResult);
    }

    @Test
    public void testFullStageViewButtonInDropDown() {
        TestUtils.createItem(TestUtils.PIPELINE, PIPELINE_NAME, this);
        TestUtils.goToMainPage(getDriver());

        String expectedResult = PIPELINE_NAME + " - Stage View";

        TestUtils.openElementDropdown(this, getDriver().findElement(
                By.cssSelector(String.format("td a[href = 'job/%s/']", TestUtils.asURL(PIPELINE_NAME)))));
        clickFullStageViewButton();

        Assert.assertEquals(getH2HeaderText(), expectedResult);
    }

    @Test(dependsOnMethods = "testVisibilityDisableButton")
    public void testPipelineNotActive() {
        final String expectedProjectName = "Pipeline1";


        String actualProjectName = getDriver().findElement(By.xpath("//tbody//td[3]//a[contains(@href, 'job/')]/span")).getText();
        Assert.assertEquals(actualProjectName, expectedProjectName);

        List<WebElement> scheduleABuildArrows = getDriver().findElements(
                By.xpath("//table//a[@title= 'Schedule a Build for " + expectedProjectName + "']"));
        Assert.assertEquals(scheduleABuildArrows.size(), 0);
    }

    @Ignore
    @Test
    public void testConsoleOutputValue() {

        int number_of_stages = 8;

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.name("name")).sendKeys(PIPELINE_NAME);
        getDriver().findElement(By.cssSelector("[class$='WorkflowJob']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        sendScript(number_of_stages);

        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/" + PIPELINE_NAME + "/build?delay=0sec']")).click();

        for (int i = 1; i <= number_of_stages; i++) {

            getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("td[class='stage-cell stage-cell-" + (i - 1) + " SUCCESS']"))).click();
            getDriver().findElement(By.cssSelector("span[class='glyphicon glyphicon-stats']")).click();

            String actualRes = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("pre[class='console-output']"))).getText();
            String expectedResult = "test " + i;

            getDriver().findElement(By.cssSelector("span[class='glyphicon glyphicon-remove']")).click();
            getWait2().until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("span[class='glyphicon glyphicon-remove']")));
            Assert.assertEquals(actualRes, expectedResult);
        }
    }

    @Test(dependsOnMethods = "testCreatePipeline")
    public void testAvgStageTimeBuildTimeIsDisplayed() {
        int number_of_stages = 1;

        chooseProjectAndClick(PIPELINE_NAME);
        getDriver().findElement(By.xpath("//*[@href='/job/NewPipeline/configure']")).click();
        sendScript(number_of_stages);
        getDriver().findElement(By.name("Submit")).click();
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@data-build-success='Build scheduled']"))).click();
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='table-box']")));

        boolean avgTime = getDriver().findElement(By.xpath("//td[@class='stage-total-0']")).isDisplayed();
        boolean buildTime = getDriver().findElement(By.xpath("//tr[@data-runid='1']//td[@data-stageid='6']")).isDisplayed();

        Assert.assertTrue(avgTime && buildTime);
    }

    @Test
    public void testCreatePipelineProject() {
        TestUtils.createItem(TestUtils.PIPELINE, PIPELINE_NAME, this);

        getH1HeaderText();

        Assert.assertEquals(getH1HeaderText(), PIPELINE_NAME);
    }

    @Test(dependsOnMethods = "testCreatePipelineProject")
    public void testColorWhenHoveringMouseOnFullStageViewButton() {

        String expectedColor = "rgba(175, 175, 207, 0.15)";

        chooseProjectAndClick(PIPELINE_NAME);

        WebElement fullStageViewButton = getDriver().findElement(
                By.xpath("//a[contains(@href, 'workflow-stage')]"));

        String backgroundColorBeforeHover = getColorOfPseudoElement(fullStageViewButton);

        Actions mouseHover = new Actions(getDriver());

        mouseHover.scrollToElement(fullStageViewButton)
                .moveToElement(fullStageViewButton)
                .pause(2000)
                .perform();

        String backgroundColorAfterHover = getColorOfPseudoElement(fullStageViewButton);

        Assert.assertTrue(!backgroundColorAfterHover.equals(backgroundColorBeforeHover)
                && backgroundColorAfterHover.equals(expectedColor));
    }

    @Test(dependsOnMethods = "testCreatePipelineProject")
    public void testBreadcrumbsOnFullStageViewPage() {

        String expectedResult = "Dashboard > " + PIPELINE_NAME + " > Full Stage View";

        chooseProjectAndClick(PIPELINE_NAME);
        clickFullStageViewButton();

        String breadcrumbs = getDriver().findElement(By.id("breadcrumbBar")).getText();

        String actualResult = breadcrumbs.replaceAll("\n", " > ");

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void testBuildAttributes() {

        int number_of_stages = 5;

        createPipelineProject(PIPELINE_NAME);

        sendScript(number_of_stages);

        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/" + PIPELINE_NAME + "/build?delay=0sec']")).click();

        WebElement box = getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.className("cell-box")));
        WebElement date = box.findElement(By.className("date"));
        WebElement time = box.findElement(By.className("time"));
        WebElement changesetBox = box.findElement(By.xpath("//div[@class='changeset-box no-changes']"));
        WebElement number = box.findElement(By.className("badge"));
        boolean result = true;
        if (date == null || !date.isDisplayed()) {
            result = false;
        }
        if (time == null || !time.isDisplayed()) {
            result = false;
        }
        if (changesetBox == null || !(changesetBox.getText().equals("No Changes"))) {
            result = false;
        }
        if (number == null || !number.isDisplayed()) {
            result = false;
        }
        Assert.assertTrue(result, "One of the elements is missing");
    }
}


package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils.*;
import school.redrover.runner.TestUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PipelineProjectTest extends BaseTest {

    private static final String PIPELINE_NAME = "NewFirstPipeline";
    private static final String RENAMED_PIPELINE = "RenamedPipeline";
    private static final String PIPELINE_DESCRIPTION = "Description added to my pipeline.";

    @Test
    public void testCreatePipeline() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        getWait5().until(ExpectedConditions.elementToBeClickable(By.cssSelector("#name"))).sendKeys(PIPELINE_NAME);
        getDriver().findElement(By.xpath("//span[text()='Pipeline']")).click();
        getDriver().findElement(By.cssSelector("#ok-button")).click();

        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@name='Submit']"))).click();

        getDriver().findElement(By.id("jenkins-head-icon")).click();

        Assert.assertTrue(getDriver().findElement(
                By.xpath("//td/a[@href='job/" + PIPELINE_NAME + "/']")).isDisplayed());
    }
    @Ignore
    @Test
    public void testSameNamePipeline() {

        final String PROJECT_NAME = "Random pipeline";

        TestUtils.createJob(this, Job.PIPELINE, PROJECT_NAME);
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        TestUtils.goToJobPageAndEnterJobName(this, PROJECT_NAME);

        getDriver().findElement(By.xpath("//*[text()='Pipeline']")).click();
        // this line duplicates click on Pipeline, because of the Jenkins bug. Sometimes warning message doesn`t appear. Second click on Pipeline makes it happen.

        String warningMessage = getDriver().findElement(By.id("itemname-invalid")).getText();

        Assert.assertEquals(warningMessage, "» A job already exists with the name ‘" + PROJECT_NAME + "’");
    }

    @Test
    public void testCreatePipelineWithEmptyName() {
        getDriver().findElement(By.xpath("//div[@class='task '][1]")).click();
        getDriver().findElement(By.xpath("//span[text()='Pipeline']")).click();

        String warningMassage = getDriver().findElement(By.id("itemname-required")).getText();
        WebElement okButton = getDriver().findElement(By.id("ok-button"));

        Assert.assertEquals(warningMassage, "» This field cannot be empty, please enter a valid name");
        Assert.assertFalse(okButton.isEnabled());
    }

    @Test
    public void testCreationOfNewPipelineProject() {

        getDriver().findElement(By.linkText("Create a job")).click();
        String newJobUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(newJobUrl.endsWith("/newJob"));

        WebElement inputElement = getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
        inputElement.sendKeys("firstPipeline");

        getDriver().findElement(By.xpath("//*[text()='Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        newJobUrl = getDriver().getCurrentUrl();

        Assert.assertTrue(newJobUrl.endsWith("job/firstPipeline/configure"));

        getDriver().findElement(By.id("jenkins-home-link")).click();
        Assert.assertTrue(getDriver().findElement(By.id("job_firstPipeline")).isDisplayed());

        WebElement jobInTableName = getDriver().findElement(By.cssSelector("a[href='job/firstPipeline/']"));
        Assert.assertEquals(jobInTableName.getText(), "firstPipeline");
    }

    @Test(dependsOnMethods = "testCreatePipeline")
    public void testAddPipelineDescription() {
        getDriver().findElement(By.xpath("//td/a[@href='job/" + PIPELINE_NAME + "/']")).click();

        getDriver().findElement(By.id("description-link")).click();
        getWait5().until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector(".jenkins-input"))).sendKeys(PIPELINE_DESCRIPTION);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        String actualDescription = getWait5().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[@id='description']/div[not(contains(@class, 'jenkins-buttons-row'))]"))).getText();

        Assert.assertTrue(actualDescription.contains(PIPELINE_DESCRIPTION));
    }

    @Test(dependsOnMethods = "testAddPipelineDescription")
    public void testEditPipelineDescription() {
        final String updatedDescription = "Description update in my pipeline.";

        getDriver().findElement(By.xpath("//td/a[@href='job/" + PIPELINE_NAME + "/']")).click();

        getDriver().findElement(By.id("description-link")).click();
        getWait2().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//textarea[@name='description']"))).sendKeys(updatedDescription);
        getDriver().findElement(By.xpath("//div/button[@name='Submit']")).click();

        Assert.assertTrue(getDriver().findElement(
                By.xpath("//div[@id='description']/div[not(contains(@class, 'jenkins-buttons-row'))]")).getText().contains(updatedDescription));
    }

    @Test
    public void testAddDescriptionPreview(){

        TestUtils.createJob(this, Job.PIPELINE, "Pipeline project");
        
        getDriver().findElement(By.xpath("//*[text()='Pipeline project']")).click();
        getDriver().findElement(By.xpath("//a[@id='description-link']")).click();
        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys("First");
        getDriver().findElement(By.xpath("//a[@class='textarea-show-preview']")).click();

        WebElement previewDescription = getDriver().findElement(By.xpath("//div[@class='textarea-preview']"));

        Assert.assertEquals(previewDescription.getText(),"First");
    }

    @Test
    public void testBreadcrumbTrailsContainsPipelineName() {

        TestUtils.createJob(this, Job.PIPELINE, "Pipeline project");

        List<WebElement> breadcrumbBarElements = List.of(
                getDriver().findElement(By.xpath("//*[@id='breadcrumbs']/li[1]")),
                getDriver().findElement(By.xpath("//*[@id='breadcrumbs']/li[2]")),
                getDriver().findElement(By.xpath("//*[@id='breadcrumbs']/li[3]/a")),
                getDriver().findElement(By.xpath("//*[@id='breadcrumbs']/li[4]")));

        for (WebElement element : breadcrumbBarElements) {
            Assert.assertTrue(element.isDisplayed(), "Pipeline project");
        }
    }

    @Test
    public void testNewPipelineProject() {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//*[@id='name']")).sendKeys("ProjectPL");
        getDriver().findElement(By.xpath("//*[@id='j-add-item-type-standalone-projects']/ul/li[2]/label")).click();
        getDriver().findElement(By.xpath("//*[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("//*[@id='bottom-sticker']/div/button[1]")).click();

        Assert.assertEquals(getDriver().findElement(
                By.xpath("//*[@id='main-panel']/div[1]/div/h1")).getText(),"ProjectPL");
    }

    @Test (dependsOnMethods = "testNewPipelineProject")
    public void testUseSearchToFindProject() {

        getDriver().findElement(By.xpath("//*[@id='search-box']")).sendKeys("ProjectPL");
        getDriver().findElement(By.xpath("//*[@id='search-box']")).sendKeys(Keys.ENTER);

        Assert.assertEquals(getDriver().findElement(
                By.xpath("//*[@id='main-panel']/div[1]/div/h1")).getText(),"ProjectPL");
    }

    @Test(dependsOnMethods = "testEditPipelineDescription")
    public void testDisablePipelineAndEnableBack() {
        getDriver().findElement(By.xpath("//td/a[@href='job/" + PIPELINE_NAME + "/']")).click();

        getDriver().findElement(By.xpath("//form[@id='disable-project']/button")).click();
        getDriver().findElement(By.id("jenkins-head-icon")).click();

        WebElement desabledGreyButton = getWait5().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//td/div/*[@title='Disabled']")));
        String pipelineStatus = desabledGreyButton.getAttribute("tooltip");

        Assert.assertEquals(pipelineStatus, "Disabled");

        getDriver().findElement(By.xpath("//td/a[@href='job/" + PIPELINE_NAME + "/']")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        getDriver().findElement(By.id("jenkins-head-icon")).click();

        WebElement greenBuildArrow = getDriver().findElement(By.xpath("//td[@class='jenkins-table__cell--tight']//a[contains(@tooltip,'Schedule')]"));
        String buildStatus = greenBuildArrow.getAttribute("tooltip");

        Assert.assertEquals(buildStatus, "Schedule a Build for " + PIPELINE_NAME);
    }

    @Test(dependsOnMethods = "testDisablePipelineAndEnableBack")
    public void testPipelineBuildSuccessFromConsole() {
        getDriver().findElement((By.xpath("//td[@class='jenkins-table__cell--tight']//a[contains(@tooltip,'Schedule')]"))).click();
        getDriver().findElement(By.xpath("//td/a[@href='job/" + PIPELINE_NAME + "/']")).click();

        getWait60().until(ExpectedConditions.attributeToBe(
                By.xpath("//a[@title='Success > Console Output']"), "tooltip", "Success > Console Output"));
        getDriver().findElement(By.xpath("//a[@title='Success > Console Output']")).click();

        WebElement consoleOutput = getDriver().findElement(By.xpath("//pre[@class='console-output']"));

        Assert.assertTrue(consoleOutput.getText().contains("Finished: SUCCESS"));
    }

    @Test(dependsOnMethods = "testPipelineBuildSuccessFromConsole")
    public void testPermalinksBuildDetails() {
        final List<String> expectedPermalinks =
                List.of("Last build (#1)", "Last stable build (#1)", "Last successful build (#1)", "Last completed build (#1)");

        getDriver().findElement(By.xpath("//td/a[@href='job/" + PIPELINE_NAME + "/']")).click();

        List<String> actualPermalinks = getDriver()
                .findElements(By.xpath("//li[@class='permalink-item']"))
                .stream()
                .map(permalink -> permalink.getText().split(",")[0].trim())
                .collect(Collectors.toList());

        Assert.assertEquals(actualPermalinks, expectedPermalinks);
    }

    @Test(dependsOnMethods = "testPermalinksBuildDetails")
    public void testGreenBuildSuccessColor() {
        final String greenHexColor = "#1ea64b";

        getDriver().findElement(By.xpath("//td/a[@href='job/" + PIPELINE_NAME + "/']")).click();

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        WebElement statusMark = getWait10().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@tooltip='Success']")));
        String actualHexColor = (String) js.executeScript(
                "return window.getComputedStyle(arguments[0]).getPropertyValue('--success');",
                statusMark);

        Assert.assertEquals(actualHexColor, greenHexColor);
    }

    @Test(dependsOnMethods = "testGreenBuildSuccessColor")
    public void testSetPipelineNumberBuildsToKeep() {
        final String maxNumberBuildsToKeep = "2";

        getDriver().findElement(By.xpath("//td/a[@href='job/" + PIPELINE_NAME + "/']")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/" + PIPELINE_NAME + "/configure']")).click();

        getDriver().findElement(By.xpath("//label[contains(text(),'Discard')]")).click();
        getDriver().findElement(By.xpath("//input[@name='_.numToKeepStr']")).sendKeys(maxNumberBuildsToKeep);
        getDriver().findElement(By.xpath("//button[@name='Apply']")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        getDriver().findElement(By.id("jenkins-head-icon")).click();

        getDriver().findElement((By.xpath("//td[@class='jenkins-table__cell--tight']//a[contains(@tooltip,'Schedule')]"))).click();
        getDriver().navigate().refresh();
        getDriver().findElement(By.xpath("//a[@href='/view/all/builds']")).click();

        List<WebElement> numberBuilds = getDriver().findElements(By.xpath("//td[contains(text(),'stable')]"));

        Assert.assertEquals(String.valueOf(numberBuilds.size()), maxNumberBuildsToKeep);
    }

    @Test(dependsOnMethods = "testSetPipelineNumberBuildsToKeep")
    public void testCheckBuildsHistoryDescendingOrder() {
        getDriver().findElement(By.xpath("//td/a[@href='job/" + PIPELINE_NAME + "/']")).click();

        List<WebElement> builds = getDriver().findElements(By.xpath("//div[@class='pane-content']//a[contains(text(),'#')]"));

        List<String> actualBuildsOrder = new ArrayList<>();
        for (WebElement element : builds) {
            actualBuildsOrder.add(element.getText());
        }

        List<String> expectedBuildOrder = new ArrayList<>(actualBuildsOrder);
        expectedBuildOrder.sort(Collections.reverseOrder());

        Assert.assertEquals(actualBuildsOrder, expectedBuildOrder, "Elements are not in descending order");
    }

    @Test(dependsOnMethods = "testCheckBuildsHistoryDescendingOrder")
    public void testSetPipelineScript() {
        getDriver().findElement(By.xpath("//td/a[@href='job/" + PIPELINE_NAME + "/']")).click();

        getDriver().findElement(By.xpath("//a[@href='/job/" + PIPELINE_NAME + "/configure']")).click();

        WebElement dropdownElement = getDriver().findElement(By.xpath("//div[@class='samples']/select"));
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByVisibleText("Hello World");
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        getDriver().findElement(By.id("jenkins-head-icon")).click();
        getDriver().findElement(By.xpath("//td/a[@href='job/" + PIPELINE_NAME + "/']")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/" + PIPELINE_NAME + "/configure']")).click();

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        WebElement scriptName = getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//span[@class='ace_string']")));
        js.executeScript("arguments[0].scrollIntoView();", scriptName);

        Assert.assertTrue(scriptName.getText().contains("Hello"));
    }

    @Test(dependsOnMethods = "testSetPipelineScript")
    public void testRenamePipelineUsingSidebar() {
        getDriver().findElement(By.xpath("//td/a[@href='job/" + PIPELINE_NAME + "/']")).click();

        getWait2().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@href='/job/" + PIPELINE_NAME + "/confirm-rename']"))).click();

        getDriver().findElement(By.xpath("//input[@checkdependson='newName']")).clear();
        getDriver().findElement(By.xpath("//input[@checkdependson='newName']")).sendKeys(RENAMED_PIPELINE);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        getDriver().findElement(By.id("jenkins-head-icon")).click();
        getDriver().findElement(By.xpath("//td/a[@href='job/" + RENAMED_PIPELINE  + "/']")).click();

        Assert.assertEquals(getWait2().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[@id='main-panel']//h1"))).getText(), RENAMED_PIPELINE);
    }

    @Test(dependsOnMethods = "testRenamePipelineUsingSidebar")
    public void testDeletePipelineUsingSidebar() {
        getDriver().findElement(By.xpath("//td/a[@href='job/" + RENAMED_PIPELINE + "/']")).click();

        getDriver().findElement(By.xpath("//a[@data-title='Delete Pipeline']")).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-id='ok']"))).click();

        Assert.assertTrue(getDriver().findElement(
                By.xpath("//div[@class='empty-state-block']")).isDisplayed(), "Welcome to Jenkins!");
        Assert.assertEquals(getDriver().findElement(
                By.xpath("//h2")).getText(), "Start building your software project");
    }
}



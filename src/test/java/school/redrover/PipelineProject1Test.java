package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PipelineProject1Test extends BaseTest {

    private static final String PIPELINE_NAME = "New First Pipeline";
    private static final String RENAMED_PIPELINE = "RenamedFirstPipeline";
    private static final String PIPELINE_DESCRIPTION = "Description added to my pipeline.";
    private static final By BUILD_TRIANGLE_BUTTON_XPATH = By.xpath("//td[@class='jenkins-table__cell--tight']//a[contains(@tooltip,'Schedule')]");
    private static final By DESCRIPTION_XPATH = By.xpath("//div[@id='description']/div[not(contains(@class, 'jenkins-buttons-row'))]");

    private void returnToHomePage() {
        getDriver().findElement(By.id("jenkins-head-icon")).click();
    }

    private void clickOnCreatedJobOnDashboardPage(String name) {
        getDriver().findElement(By.xpath("//td/a[@href='job/" + name.replaceAll(" ", "%20") + "/']")).click();
    }

    @Test
    public void testCreatePipeline() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        getWait5().until(ExpectedConditions.elementToBeClickable(By.cssSelector("#name"))).sendKeys(PIPELINE_NAME);
        getDriver().findElement(By.xpath("//span[text()='Pipeline']")).click();
        getDriver().findElement(By.cssSelector("#ok-button")).click();

        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@name='Submit']"))).click();

        returnToHomePage();

        Assert.assertTrue(getDriver().findElement(
                By.xpath("//td/a[@href='job/" + PIPELINE_NAME.replaceAll(" ", "%20") + "/']")).isDisplayed());
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

    @Test(dependsOnMethods = "testCreatePipeline")
    public void testAddPipelineDescription() {
        clickOnCreatedJobOnDashboardPage(PIPELINE_NAME);

        getDriver().findElement(By.id("description-link")).click();
        getWait5().until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector(".jenkins-input"))).sendKeys(PIPELINE_DESCRIPTION);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        String actualDescription = getWait5().until(ExpectedConditions.presenceOfElementLocated(
                DESCRIPTION_XPATH)).getText();

        Assert.assertTrue(actualDescription.contains(PIPELINE_DESCRIPTION));
    }

    @Test(dependsOnMethods = "testAddPipelineDescription")
    public void testEditPipelineDescription() {
        final String updatedDescription = "Description update in my pipeline.";

        clickOnCreatedJobOnDashboardPage(PIPELINE_NAME);

        getDriver().findElement(By.id("description-link")).click();
        getWait2().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//textarea[@name='description']"))).sendKeys(updatedDescription);
        getDriver().findElement(By.xpath("//div/button[@name='Submit']")).click();

        Assert.assertTrue(getDriver().findElement(DESCRIPTION_XPATH).getText().contains(updatedDescription));
    }

    @Test(dependsOnMethods = "testEditPipelineDescription")
    public void testDisablePipelineAndEnableBack() {
        clickOnCreatedJobOnDashboardPage(PIPELINE_NAME);

        getDriver().findElement(By.xpath("//form[@id='disable-project']/button")).click();
        returnToHomePage();

        WebElement desabledGreyButton = getWait5().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//td/div/*[@title='Disabled']")));
        String pipelineStatus = desabledGreyButton.getAttribute("tooltip");

        Assert.assertEquals(pipelineStatus, "Disabled");

        clickOnCreatedJobOnDashboardPage(PIPELINE_NAME);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        returnToHomePage();

        WebElement greenBuildArrow = getDriver().findElement(BUILD_TRIANGLE_BUTTON_XPATH);
        String buildStatus = greenBuildArrow.getAttribute("tooltip");

        Assert.assertEquals(buildStatus, "Schedule a Build for " + PIPELINE_NAME);
    }

    @Test(dependsOnMethods = "testDisablePipelineAndEnableBack")
    public void testPipelineBuildSuccessFromConsole() {
        getDriver().findElement(BUILD_TRIANGLE_BUTTON_XPATH).click();
        clickOnCreatedJobOnDashboardPage(PIPELINE_NAME);

        getWait60().until(ExpectedConditions.attributeToBe(
                By.xpath("//a[@title='Success > Console Output']"), "tooltip", "Success > Console Output"));
        getDriver().findElement(By.xpath("//a[@title='Success > Console Output']")).click();

        WebElement consoleOutput = getDriver().findElement(By.xpath("//pre[@class='console-output']"));

        Assert.assertTrue(consoleOutput.getText().contains("Finished: SUCCESS"));
    }

    @Test(dependsOnMethods = "testPipelineBuildSuccessFromConsole")
    public void testSetPipelineNumberBuildsToKeep() {
        final String maxNumberBuildsToKeep = "2";

        clickOnCreatedJobOnDashboardPage(PIPELINE_NAME);
        getDriver().findElement(By.xpath("//a[@href='/job/" + PIPELINE_NAME.replaceAll(" ", "%20") + "/configure']")).click();

        getDriver().findElement(By.xpath("//label[contains(text(),'Discard')]")).click();
        getDriver().findElement(By.xpath("//input[@name='_.numToKeepStr']")).sendKeys(maxNumberBuildsToKeep);
        getDriver().findElement(By.xpath("//button[@name='Apply']")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        returnToHomePage();

        getDriver().findElement(BUILD_TRIANGLE_BUTTON_XPATH).click();
        getDriver().navigate().refresh();
        getDriver().findElement(By.xpath("//a[@href='/view/all/builds']")).click();

        List<WebElement> numberBuilds = getDriver().findElements(By.xpath("//td[contains(text(),'stable')]"));

        Assert.assertEquals(String.valueOf(numberBuilds.size()), maxNumberBuildsToKeep);
    }

    @Test(dependsOnMethods = "testSetPipelineNumberBuildsToKeep")
    public void testCheckBuildsHistoryDescendingOrder() {
        clickOnCreatedJobOnDashboardPage(PIPELINE_NAME);

        List<WebElement> builds = getDriver().findElements(By.xpath("//div[@class='pane-content']//a[contains(text(),'#')]"));

        List<String> actualBuildsOrder = new ArrayList<>();
        for (WebElement element : builds) {
            actualBuildsOrder.add(element.getText());
        }

        List<String> expectedBuildOrder = new ArrayList<>(actualBuildsOrder);
        expectedBuildOrder.sort(Collections.reverseOrder());

        Assert.assertEquals(actualBuildsOrder, expectedBuildOrder, "Elements are not in descending order");
    }

    @Test
    public void testAddDescriptionColumnToPipelineView() {
        final String viewName = "Empoyee's view";
        final List<String> expectedPipelineViewHeader =
                List.of("S", "W", "Name" + "\n" + "  ↓", "Last Success", "Last Failure", "Last Duration", "Description");
        List<String> actualPipelineViewHeader = new ArrayList<>();

        TestUtils.createItem(TestUtils.PIPELINE, PIPELINE_NAME, this);

        returnToHomePage();

        getDriver().findElement(By.xpath("//a[@href='/newView']")).click();
        getDriver().findElement(By.id("name")).sendKeys(viewName);
        getDriver().findElement(By.xpath("//label[contains(text(),'List View')]")).click();
        getDriver().findElement(By.id("ok")).click();

        getWait5().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//label[@title='" + PIPELINE_NAME + "']"))).click();
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        WebElement addColumnButton = getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[@suffix='columns']")));
        js.executeScript("arguments[0].scrollIntoView();", addColumnButton);
        addColumnButton.click();

        getDriver().findElement(By.xpath("(//button[@class='jenkins-dropdown__item'])[last()]")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        List<WebElement> projectViewTitles = getWait5().until(ExpectedConditions.numberOfElementsToBeMoreThan(
                By.xpath("//table[@id='projectstatus']//thead//tr/th"), 7));
        for (WebElement headerTitle : projectViewTitles) {
            String header = headerTitle.getText();
            if (!header.isEmpty()) {
                actualPipelineViewHeader.add(headerTitle.getText());
            }
        }

        Assert.assertTrue(projectViewTitles.get(projectViewTitles.size() - 1).getText().contains("Description"));
        Assert.assertEquals(actualPipelineViewHeader, expectedPipelineViewHeader);
    }

    @Test(dependsOnMethods = "testCheckBuildsHistoryDescendingOrder")
    public void testRenamePipelineUsingSidebar() {
        clickOnCreatedJobOnDashboardPage(PIPELINE_NAME);

        getWait2().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@href='/job/" + PIPELINE_NAME.replaceAll(" ", "%20") + "/confirm-rename']"))).click();

        getDriver().findElement(By.xpath("//input[@checkdependson='newName']")).clear();
        getDriver().findElement(By.xpath("//input[@checkdependson='newName']")).sendKeys(RENAMED_PIPELINE);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        returnToHomePage();
        clickOnCreatedJobOnDashboardPage(RENAMED_PIPELINE);

        Assert.assertEquals(getWait2().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[@id='main-panel']//h1"))).getText(), RENAMED_PIPELINE);
    }

    @Test(dependsOnMethods = "testRenamePipelineUsingSidebar")
    public void testDeletePipelineUsingSidebar() {
        clickOnCreatedJobOnDashboardPage(RENAMED_PIPELINE);

        getDriver().findElement(By.xpath("//a[@data-title='Delete Pipeline']")).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-id='ok']"))).click();

        Assert.assertTrue(getDriver().findElement(
                By.xpath("//div[@class='empty-state-block']")).isDisplayed(), "Welcome to Jenkins!");
        Assert.assertEquals(getDriver().findElement(
                By.xpath("//h2")).getText(), "Start building your software project");
    }
}




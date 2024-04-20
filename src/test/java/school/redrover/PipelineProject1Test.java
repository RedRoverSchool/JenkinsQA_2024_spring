package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.List;

public class PipelineProject1Test extends BaseTest {

    private static final String PIPELINE_NAME = "New First Pipeline";
    private static final String PIPELINE_DESCRIPTION = "Description added to my pipeline.";
    private static final String RENAMED_PIPELINE_NAME = "RenamedFirstPipeline";
    private static final By BUILD_TRIANGLE_BUTTON = By.xpath("//td[@class='jenkins-table__cell--tight']/div/a");

    private void createPipeline(String name) {
        getDriver().findElement(By.xpath("//div[@class='task '][1]")).click();

        getDriver().findElement(By.cssSelector("#name")).sendKeys(name);
        getDriver().findElement(By.xpath("//span[text()='Pipeline']")).click();
        getDriver().findElement(By.cssSelector("#ok-button")).click();

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
    }

    private void returnToHomePage() {
        getDriver().findElement(By.cssSelector(".jenkins-breadcrumbs__list-item:nth-child(1)")).click();
    }

    private void clickOnCreatedJobOnDashboardPage(String name) {
        getDriver().findElement(By.xpath("//a[starts-with(@class,'jenkins-table__link')]")).click();
    }

    @Ignore
    @Test
    public void testCreatePipeline() {
        createPipeline(PIPELINE_NAME);
        returnToHomePage();
        clickOnCreatedJobOnDashboardPage(PIPELINE_NAME);

        String actualPipelineName = getDriver().findElement(By.xpath("//div[@id='main-panel']//h1")).getText();

        Assert.assertEquals(actualPipelineName, PIPELINE_NAME);
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

    @Ignore
    @Test
    public void testAddPipelineDescription() {
        createPipeline(PIPELINE_NAME);
        returnToHomePage();
        clickOnCreatedJobOnDashboardPage(PIPELINE_NAME);

        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.cssSelector(".jenkins-input")).sendKeys(PIPELINE_DESCRIPTION);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        String actualDescription = getDriver().findElement(By.xpath("//div[@id='description']//div[1]")).getText();

        Assert.assertTrue(actualDescription.contains(PIPELINE_DESCRIPTION));
    }

    @Ignore
    @Test
    public void testRenamePipelineFromLeftMenu() {
        createPipeline(PIPELINE_NAME);
        returnToHomePage();
        clickOnCreatedJobOnDashboardPage(PIPELINE_NAME);

        getDriver().findElement(By.xpath("//a[@href='/job/" + PIPELINE_NAME.replaceAll(" ", "%20") + "/confirm-rename']")).click();

        getDriver().findElement(By.xpath("//input[@checkdependson='newName']")).clear();
        getDriver().findElement(By.xpath("//input[@checkdependson='newName']")).sendKeys(RENAMED_PIPELINE_NAME);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        returnToHomePage();
        clickOnCreatedJobOnDashboardPage(PIPELINE_NAME);

        String changedPipelineName = getDriver().findElement(By.xpath("//div[@id='main-panel']//h1")).getText();

        Assert.assertEquals(changedPipelineName, RENAMED_PIPELINE_NAME);
    }

    @Ignore
    @Test
    public void testDeletePipelineFromLeftMenu() {
        createPipeline(PIPELINE_NAME);
        returnToHomePage();
        clickOnCreatedJobOnDashboardPage(PIPELINE_NAME);

        getDriver().findElement(By.xpath("//a[@data-title='Delete Pipeline']")).click();
        getDriver().findElement(By.xpath("//button[@data-id='ok']")).click();

        String startNewProjectMassage = getDriver().findElement(By.xpath("//h2")).getText();

        Assert.assertEquals(startNewProjectMassage, "Start building your software project");
    }

    @Ignore
    @Test
    public void testDisablePipelineAndEnableBack() {
        createPipeline(PIPELINE_NAME);
        returnToHomePage();
        clickOnCreatedJobOnDashboardPage(PIPELINE_NAME);

        getDriver().findElement(By.xpath("//form[@id='disable-project']/button")).click();
        returnToHomePage();

        WebElement desabledGreyButton = getDriver().findElement(By.xpath("//td/div/*[@tooltip='Disabled']"));
        String pipelineStatus = desabledGreyButton.getAttribute("tooltip");

        Assert.assertEquals(pipelineStatus, "Disabled");

        clickOnCreatedJobOnDashboardPage(PIPELINE_NAME);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        returnToHomePage();

        WebElement greenBuildArrow = getDriver().findElement(BUILD_TRIANGLE_BUTTON);
        String buildStatus = greenBuildArrow.getAttribute("tooltip");

        Assert.assertEquals(buildStatus, "Schedule a Build for " + PIPELINE_NAME);
    }

    @Ignore
    @Test
    public void testPipelineBuildSuccessFromConsole() {
        createPipeline(PIPELINE_NAME);
        returnToHomePage();

        getDriver().findElement(BUILD_TRIANGLE_BUTTON).click();
        clickOnCreatedJobOnDashboardPage(PIPELINE_NAME);

        getDriver().findElement(By.xpath("//a[text()='#1']")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/" + PIPELINE_NAME + "/1/console']")).click();

        WebElement consoleOutput = getDriver().findElement(By.xpath("//pre[@class='console-output']"));

        Assert.assertTrue(consoleOutput.getText().contains("Finished: SUCCESS"));
    }

    @Ignore
    @Test
    public void AddDescriptionColumnToPipelineView() {
        final List<String> expectedPipelineViewHeader = List.of("S", "W", "Name" + "\n" + "  ↓", "Last Success", "Last Failure", "Last Duration", "Description");
        List<String> actualPipelineViewHeader = new ArrayList<>();

        createPipeline(PIPELINE_NAME);
        returnToHomePage();

        getDriver().findElement(By.xpath("//a[@href='/newView']")).click();

        getDriver().findElement(By.id("name")).sendKeys("General");
        getDriver().findElement(By.xpath("//label[contains(text(),'List View')]")).click();
        getDriver().findElement(By.id("ok")).click();

        getDriver().findElement(By.xpath("//label[@title='" + PIPELINE_NAME + "']")).click();
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        WebElement addColumnButton = getDriver().findElement(By.xpath("//button[@suffix='columns']"));
        js.executeScript("arguments[0].scrollIntoView();", addColumnButton);
        addColumnButton.click();

        getDriver().findElement(By.xpath("(//button[@class='jenkins-dropdown__item'])[last()]")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        List<WebElement> projectViewTitles = getDriver().findElements(By.xpath("//table[@id='projectstatus']//thead//tr/th"));
        for (WebElement headerTitle : projectViewTitles) {
            String header = headerTitle.getText();
            if (!header.isEmpty()) {
                actualPipelineViewHeader.add(headerTitle.getText());
            }
        }

        Assert.assertTrue(projectViewTitles.get(projectViewTitles.size() - 1).getText().contains("Description"));
        Assert.assertEquals(actualPipelineViewHeader, expectedPipelineViewHeader);
    }
}




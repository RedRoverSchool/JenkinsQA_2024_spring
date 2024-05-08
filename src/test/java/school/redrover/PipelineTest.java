package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.model.PipelinePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;

public class PipelineTest extends BaseTest {

    @FindBy(xpath = "//a[contains(@href, 'workflow-stage')]")
    private WebElement fullStageViewButton;

    private static final String PIPELINE_NAME = "FirstPipeline";
    private static final By DASHBOARD_PIPELINE_LOCATOR = By.cssSelector("td [href='job/" + PIPELINE_NAME + "/']");
    private static final By BUILD_HISTORY_PIPELINE_LOCATOR = By.cssSelector("td [href$='job/" + PIPELINE_NAME + "/']");
    private static final String DESCRIPTION = "Lorem ipsum dolor sit amet";
    private static final String NEW_PIPELINE_NAME = "New Pipeline name";

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
        String defaultTextAreaBorderBacklightColor = new HomePage(getDriver())
                .resetJenkinsTheme()
                .clickLogo()
                .clickCreateAJob()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .clickChangeDescription()
                .makeDescriptionFieldNotActive()
                .getDefaultTextAreaBorderBacklightColor();

        Assert.assertEquals(defaultTextAreaBorderBacklightColor, "rgba(11,106,162,.25)");
    }

    @Test
    public void testYesButtonColorDeletingPipelineInSidebar() {
        String yesButtonHexColor = new HomePage(getDriver())
                .resetJenkinsTheme()
                .clickLogo()
                .clickCreateAJob()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .clickSidebarDeleteButton()
                .getYesButtonColorDeletingViaSidebar();

        Assert.assertEquals(yesButtonHexColor, "#e6001f", "The confirmation button color is not red");
    }

    @Test
    public void testDeleteViaBreadcrumbs() {
        boolean isPipelineDeleted = new HomePage(getDriver())
                .clickCreateAJob()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .clickSpecificPipelineName(DASHBOARD_PIPELINE_LOCATOR)
                .hoverOverBreadcrumbsName()
                .clickBreadcrumbsDropdownArrow()
                .clickBreadcrumbsDeleteButton()
                .clickYes(new HomePage(getDriver()))
                .isItemDeleted(PIPELINE_NAME);

        Assert.assertTrue(isPipelineDeleted, PIPELINE_NAME + " was not deleted");
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
        String descriptionText = new HomePage(getDriver())
                .clickCreateAJob()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .clickChangeDescription()
                .setDescription(DESCRIPTION)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(descriptionText, DESCRIPTION);
    }

    @Test(dependsOnMethods = "testAddDescription")
    public void testEditDescription() {
        final String addedToDescription = ", consectetur adipiscing elit.";
        final String expectedDescription = DESCRIPTION + addedToDescription;

        String descriptionText = new HomePage(getDriver())
                .clickCreatedPipelineName()
                .clickChangeDescription()
                .clickOnDescriptionInput()
                .setDescription(addedToDescription)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(descriptionText, expectedDescription);
    }

    @Test
    public void testRenamePipelineViaSidebar() {
        String displayedName = new HomePage(getDriver())
                .clickCreateAJob()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .clickSidebarRenameButton()
                .clearNameInputField()
                .setNewName(NEW_PIPELINE_NAME)
                .clickSaveRenameButton()
                .getHeadlineDisplayedName();

        Assert.assertEquals(displayedName, NEW_PIPELINE_NAME);
    }

    @Test
    public void testCreatePipelineProject() {
        String getH1HeaderText = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .getHeadlineDisplayedName();

             Assert.assertEquals(getH1HeaderText, PIPELINE_NAME);
    }

    @Test
    public void testFullStageViewButton() {

        final String pipelineName = "New Pipeline";
        final String expectedResult = pipelineName + " - Stage View";

        String h2HeadingText = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(pipelineName)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .chooseCreatedProject(pipelineName)
                .clickFullStageViewButton()
                .getH2HeadingText();

        Assert.assertEquals(h2HeadingText, expectedResult);
    }

    @Test
    void testVerifyThePresenceOfTheFullStageViewButtonInTheSidebar() {
        String pipelineName = "New Pipeline group_java_autoqa_rrschool";

        new HomePage(getDriver())
                .clickNewItem()
                .setItemName(pipelineName)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .chooseCreatedProject(pipelineName);

        Assert.assertTrue(new PipelinePage(getDriver()).isBtnPresentInSidebar("Full Stage View"));
    }

    @Test(dependsOnMethods = "testCreatePipelineProject")
    public void testBreadcrumbsOnFullStageViewPage() {

        final String expectedResult = "Dashboard > " + PIPELINE_NAME + " > Full Stage View";

        String actualResult = new HomePage(getDriver())
                .chooseCreatedProject(PIPELINE_NAME)
                .clickFullStageViewButton()
                .getBreadcrumbsText();

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test(dependsOnMethods = "testCreatePipelineProject")
    public void testColorWhenHoveringMouseOnFullStageViewButton() {

        final String expectedColor = "rgba(175, 175, 207, 0.15)";

        String backgroundColorBeforeHover = new HomePage(getDriver())
                .chooseCreatedProject(PIPELINE_NAME)
                .getFullStageViewButtonBackgroundColor();

        String backgroundColorAfterHover = new PipelinePage(getDriver())
                .hoverOnFullStageViewButton()
                .getFullStageViewButtonBackgroundColor();

        Assert.assertTrue(!backgroundColorAfterHover.equals(backgroundColorBeforeHover)
                && backgroundColorAfterHover.equals(expectedColor));
    }

    @Test
    public void testFullStageViewButtonInDropDown() {

        final String pipelineName = PIPELINE_NAME;
        final String expectedResult = PIPELINE_NAME + " - Stage View";

        String h2HeadingText = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(pipelineName)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .openItemDropdown(pipelineName)
                .clickFullStageViewButton()
                .getH2HeadingText();

        Assert.assertEquals(h2HeadingText, expectedResult);
    }
}

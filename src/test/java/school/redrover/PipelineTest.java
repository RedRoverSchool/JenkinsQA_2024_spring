package school.redrover;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.model.*;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import static school.redrover.runner.TestUtils.goToMainPage;
import org.testng.annotations.DataProvider;

public class PipelineTest extends BaseTest {

    private static final String PIPELINE_NAME = "FirstPipeline";

    private static final String NEW_PIPELINE_NAME = "New Pipeline name";

    private static final String DESCRIPTION = "Lorem ipsum dolor sit amet";

    private static final String SUCCEED_BUILD_EXPECTED = "Finished: SUCCESS";

    private static final List<String> NAME_PROJECTS = List.of("PPProject", "PPProject2");

    private static final String pipelineScript = "pipeline {\nagent any\n\nstages {\n";

    private static final By ADVANCED_PROJECT_OPTIONS_MENU = By.xpath("//button[@data-section-id='advanced-project-options']");

    @Test
    @Epic("New Item")
    @Story("US_00.002 Create Pipeline Project")
    @Description("Create Pipeline with valid name and verify if created project is shown on the HomePage")
    public void testCreatePipeline() {
        List<String> itemPipeline = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .getItemList();

        Assert.assertTrue(itemPipeline.contains(PIPELINE_NAME));
    }

    @Test(dependsOnMethods = "testCreatePipeline")
    @Epic("New Item")
    @Story("US_00.002 Create Pipeline Project")
    @Description("Verify that the pipeline project with the same name can`t be created")
    public void testCreatePipelineSameName() {
        String itemPipeline = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PIPELINE_NAME)
                .clickOkAnyway(new CreateNewItemPage(getDriver()))
                .getErrorMessageInvalidCharacterOrDuplicateName();

        Assert.assertEquals(itemPipeline, "» A job already exists with the name ‘" + PIPELINE_NAME + "’");
    }

    @Test
    @Epic("New Item")
    @Story("US_00.002 Create Pipeline Project")
    @Description("Create Pipeline Project with empty name")
    public void testCreatePipelineWithEmptyName() {
        String itemPipeline = new HomePage(getDriver())
                .clickNewItem()
                .setItemName("")
                .clickOkAnyway(new CreateNewItemPage(getDriver()))
                .getItemNameHintText();

        Assert.assertEquals(itemPipeline, "» This field cannot be empty, please enter a valid name");
    }

    @Test(dependsOnMethods = "testUseSearchToFindProject")
    @Epic("New Item")
    @Story("US_00.002 Create Pipeline Project")
    @Description("Find created project by its name, using 'Search' input field")
    public void testFindPipelineProject() {
        String searchResult = new HomePage(getDriver())
                .getHeader().typeSearchQueryPressEnter(PIPELINE_NAME)
                .getHeadingText();

        Assert.assertEquals(searchResult, PIPELINE_NAME);
    }

    @Test
    @Epic("New Item")
    @Story("US_00.002 Create Pipeline Project")
    @Description("Create Pipeline and find created project by its name, using 'Search' input field on Home Page")
    public void testUseSearchToFindProject() {
        String actualPipelineName = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickLogo()
                .getHeader().searchProjectByName(PIPELINE_NAME, new PipelineProjectPage(getDriver()))
                .getProjectName();

        Assert.assertEquals(actualPipelineName, PIPELINE_NAME);
    }

    @Test
    @Epic("Pipeline")
    @Story("US_02.005 Edit pipeline description")
    @Description("Verify text area border backlight color being active")
    public void testPipelineDescriptionTextAreaBacklightColor() {
        TestUtils.resetJenkinsTheme(this);

        String currentTextAreaBorderBacklightColor = new HomePage(getDriver())
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
    @Epic("Pipeline")
    @Story("US_02.005 Edit pipeline description")
    @Description("Verify text area border backlight color by default")
    public void testPipelineDescriptionTextAreaBacklightDefaultColor() {
        TestUtils.resetJenkinsTheme(this);

        String defaultTextAreaBorderBacklightColor = new HomePage(getDriver())
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
    @Epic("Pipeline")
    @Story("US_02.007 Delete pipeline")
    @Description("Verify the pop-up 'Yes' button color is red if user deletes Pipeline using sidebar")
    public void testYesButtonColorDeletingPipelineInSidebar() {
        TestUtils.resetJenkinsTheme(this);

        String yesButtonHexColor = new HomePage(getDriver())
                .clickCreateAJob()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .clickSidebarDeleteButton()
                .getYesButtonColorDeletingViaSidebar();

        Assert.assertEquals(yesButtonHexColor, "#e6001f", "The confirmation button color is not red");
    }

    @Test
    @Epic("Pipeline")
    @Story("US_02.007 Delete pipeline")
    @Description("Verify Pipeline can be deleted via breadcrumbs")
    public void testDeleteViaBreadcrumbs() {
        boolean isPipelineDeleted = new HomePage(getDriver())
                .clickCreateAJob()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .clickSpecificPipelineName(PIPELINE_NAME)
                .hoverOverBreadcrumbsName()
                .clickBreadcrumbsDropdownArrow()
                .clickBreadcrumbsDeleteButton()
                .clickYes(new HomePage(getDriver()))
                .isItemDeleted(PIPELINE_NAME);

        Assert.assertTrue(isPipelineDeleted, PIPELINE_NAME + " was not deleted");
    }

    @Test
    @Epic("Pipeline")
    @Story("US_02.007 Delete pipeline")
    @Description("Verify Pipeline builds disappeared from Build History page upon its removal")
    public void testBuildHistoryEmptyUponPipelineRemoval() {
        boolean isBuildDeleted = new HomePage(getDriver())
                .clickCreateAJob()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .scheduleBuildForItem(PIPELINE_NAME)
                .clickBuildHistory()
                .hoverOverItemName(PIPELINE_NAME)
                .clickItemDropdownArrow()
                .clickItemDeleteButton()
                .clickYes(new HomePage(getDriver()))
                .clickBuildHistory()
                .isBuildDeleted(PIPELINE_NAME);

        Assert.assertTrue(isBuildDeleted, PIPELINE_NAME + " build is in the Build history table");
    }

    @Test
    @Epic("Pipeline")
    @Story("US_02.005 Delete pipeline")
    @Description("Add a description to the pipeline")
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
    @Epic("Pipeline")
    @Story("US_02.005 Delete pipeline")
    @Description("Edit current description")
    public void testEditDescription() {
        final String addedToDescription = ", consectetur adipiscing elit.";
        final String expectedDescription = DESCRIPTION + addedToDescription;

        String descriptionText = new HomePage(getDriver())
                .clickSpecificPipelineName(PIPELINE_NAME)
                .clickChangeDescription()
                .clickOnDescriptionInput()
                .setDescription(addedToDescription)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(descriptionText, expectedDescription);
    }

    @Test
    @Epic("Pipeline")
    @Story("US_02.008 Rename pipeline")
    @Description("Rename project via sidebar")
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
                .getProjectName();

        Assert.assertEquals(displayedName, NEW_PIPELINE_NAME);
    }

    @Test
    @Epic("Pipeline")
    @Story("US_02.009 Full stage view")
    @Description("Project's name and 'Stage View' are displayed after clicking 'Full Stage View' button in the sidebar")
    public void testFullStageViewButton() {

        final String pipelineName = "New Pipeline";
        final String expectedResult = pipelineName + " - Stage View";

        String h2HeadingText = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(pipelineName)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .clickSpecificPipelineName(pipelineName)
                .clickFullStageViewButton()
                .getH2HeadingText();

        Assert.assertEquals(h2HeadingText, expectedResult);
    }

    @Test
    @Epic("Pipeline")
    @Story("US_02.009 Full stage view")
    @Description("Verify the presence of the full stage view button in the sidebar")
    void testVerifyThePresenceOfTheFullStageViewButtonInTheSidebar() {
        String pipelineName = "New Pipeline group_java_autoqa_rrschool";

        new HomePage(getDriver())
                .clickNewItem()
                .setItemName(pipelineName)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .clickSpecificPipelineName(pipelineName);

        Assert.assertTrue(new PipelineProjectPage(getDriver()).isBtnPresentInSidebar("Full Stage View"));
    }

    @Test
    @Epic("Pipeline")
    @Story("US_02.009 Full stage view")
    @Description("Verify that the breadcrumb navigation displays the correct hierarchy")
    public void testBreadcrumbsOnFullStageViewPage() {
        final String expectedResult = "Dashboard > " + PIPELINE_NAME + " > Full Stage View";

        String actualResult = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .clickSpecificPipelineName(PIPELINE_NAME)
                .clickFullStageViewButton()
                .getBreadcrumbsText();

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test(dependsOnMethods = "testBreadcrumbsOnFullStageViewPage")
    @Epic("Pipeline")
    @Story("US_02.009 Full stage view")
    @Description("Button Color Changes on Hover")
    public void testColorWhenHoveringMouseOnFullStageViewButton() {

        final String expectedColor = "rgba(175, 175, 207, 0.15)";

        String backgroundColorBeforeHover = new HomePage(getDriver())
                .clickSpecificPipelineName(PIPELINE_NAME)
                .getFullStageViewButtonBackgroundColor();

        String backgroundColorAfterHover = new PipelineProjectPage(getDriver())
                .hoverOnFullStageViewButton()
                .getFullStageViewButtonBackgroundColor();

        Assert.assertTrue(!backgroundColorAfterHover.equals(backgroundColorBeforeHover)
                && backgroundColorAfterHover.equals(expectedColor));
    }

    @Test
    @Epic("Pipeline")
    @Story("US_02.009 Full stage view")
    @Description("Verify the heading after clicking the ‘Full Stage View’ button in the dropdown menu displays")
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

    @Test
    @Epic("Pipeline")
    @Story("US_02.009 Full stage view")
    @Description("Verify the list of the last 10 builds for the pipeline is displayed")
    public void testTableWithLast10Builds() {

        final int stagesQtt = 2;
        final int buildsQtt = 13;
        final List<String> expectedBuildsList = IntStream.range(0, 10).mapToObj(i -> "#" + (buildsQtt - i)).toList();

        List<String> actualBuildsList = new HomePage(getDriver())
                .clickManageJenkins()
                .clickNodes()
                .clickBuiltInNodeName()
                .turnNodeOnIfOffline()
                .clickNewItem()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .sendScript(stagesQtt, pipelineScript)
                .clickSaveButton()
                .makeBuilds(buildsQtt)
                .clickFullStageViewButton()
                .getItemList();

        Assert.assertEquals(actualBuildsList, expectedBuildsList);
    }

    @Test
    @Epic("Pipeline")
    @Story("US_02.002 View changes")
    @Description("Verify Changes page opens by clicking 'Changes' in drop-down menu at pipeline name on Pipeline Page")
    public void testChangesPageHeading() {
        String actualPageHeading = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .hoverOverBreadcrumbsName()
                .clickBreadcrumbsDropdownArrow()
                .clickDropdownChangesButton()
                .getPageHeading();

        Assert.assertEquals(actualPageHeading, "Changes");
    }

    @Test
    @Epic("Pipeline")
    @Story("US_02.008 Rename pipeline")
    @Description("Rename project via breadcrumbs")
    public void testRenameJobViaBreadcrumbs() {
        String displayedNewName = new HomePage(getDriver())
                .clickCreateAJob()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .clickBreadcrumbsDropdownArrow()
                .clickBreadcrumbsRenameButton()
                .clearNameInputField()
                .setNewName(NEW_PIPELINE_NAME)
                .clickSaveRenameButton()
                .getProjectName();

        Assert.assertEquals(displayedNewName, NEW_PIPELINE_NAME);
    }

    @Test
    @Epic("Pipeline")
    @Story("US_02.005 Edit pipeline description")
    @Description("Use preview option to view description")
    public void testAddDescriptionPreview() {
        String previewDescription = new HomePage(getDriver())
                .clickCreateAJob()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .addDescription(DESCRIPTION)
                .clickPreview()
                .getTextareaPreviewText();

        Assert.assertEquals(previewDescription, DESCRIPTION);
    }

    @Test
    @Epic("Pipeline")
    @Story("US_02.004 Pipeline Configuration")
    @Description("Verify that a pipeline with a specified number of stages can be created via pipeline script")
    public void testStagesQtt() {
        final int stagesQtt = 5;
        final int buildsQtt = 1;

        int actualSagesQtt = new HomePage(getDriver())
                .clickManageJenkins()
                .clickNodes()
                .clickBuiltInNodeName()
                .turnNodeOnIfOffline()
                .clickNewItem()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .sendScript(stagesQtt, pipelineScript)
                .clickSaveButton()
                .makeBuilds(buildsQtt)
                .getSagesQtt();

        Assert.assertEquals(actualSagesQtt, stagesQtt);
    }

    @Test
    @Epic("Pipeline")
    @Story("US_02.006 Disable project")
    @Description("Verify 'Disable Project' button is visible")
    public void testVisibilityOfDisableButton() {
        boolean isDisableButtonDisplayed = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .isDisableButtonVisible();

        Assert.assertTrue(isDisableButtonDisplayed, "Can't find the button");
    }

    @Test
    @Epic("Pipeline")
    @Story("US_02.006 Disable project")
    @Description("Disable project")
    public void testDisableItem() {
        final String expectedWarning = "This project is currently disabled";

        String warningMessage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .clickDisableButton()
                .getWarningMessageText();

        Assert.assertTrue(warningMessage.contains(expectedWarning));
    }

    @Test(dependsOnMethods = "testDisableItem")
    @Epic("Pipeline")
    @Story("US_02.006 Disable project")
    @Description("Verify project is disabled")
    public void testPipelineNotActive() {

        Assert.assertTrue(new HomePage(getDriver()).isItemExists(PIPELINE_NAME));
        Assert.assertEquals(new HomePage(getDriver()).getBuildButtonCountForProject(PIPELINE_NAME), 0);
    }

    @Test(dependsOnMethods = {"testPipelineNotActive", "testDisableItem"})
    @Epic("Pipeline")
    @Story("US_02.006 Disable project")
    @Description("Verify disabled project can be enabled")
    public void testEnableBack() {
        String pipelineStatus = new HomePage(getDriver())
                .clickJobByName(PIPELINE_NAME, new PipelineProjectPage(getDriver()))
                .clickEnableButton()
                .clickLogo()
                .getBuildStatus();

        Assert.assertEquals(pipelineStatus, "Schedule a Build for " + PIPELINE_NAME);
    }

    @Test(dependsOnMethods = "testEnableBack")
    @Epic("Build history")
    @Story("US_08.002 Take information about a project built")
    @Description("Check permalinks after build is scheduled")
    public void testPermalinksBuildDetails() {
        final List<String> expectedPermalinkList =
                List.of("Last build (#1)", "Last stable build (#1)", "Last successful build (#1)", "Last completed build (#1)");

        List<String> actualPermalinkList = new HomePage(getDriver())
                .scheduleBuildForItem(PIPELINE_NAME)
                .waitForBuildSchedulePopUp()
                .clickJobByName(PIPELINE_NAME, new PipelineProjectPage(getDriver()))
                .getPermalinkList();

        Assert.assertEquals(actualPermalinkList, expectedPermalinkList);
    }

    @Test(dependsOnMethods = "testPermalinksBuildDetails")
    @Epic("Build history")
    @Story("US_08.002 Take information about a project built")
    @Description("Successful build is marked green")
    public void testGreenBuildSuccessColor() {
        final String greenHexColor = "#1ea64b";

        String actualHexColor = new HomePage(getDriver())
                .clickJobByName(PIPELINE_NAME, new PipelineProjectPage(getDriver()))
                .getHexColorSuccessMark();

        Assert.assertEquals(actualHexColor, greenHexColor);
    }

    @Test(dependsOnMethods = "testGreenBuildSuccessColor")
    @Epic("Build history")
    @Story("US_08.002 Take information about a project built")
    @Description("Successful build is marked green")
    public void testCheckBuildsHistoryDescendingOrder() {
        List<String> actualBuildsOrderList = new HomePage(getDriver())
                .scheduleBuildForItem(PIPELINE_NAME)
                .waitForBuildSchedulePopUp()
                .clickJobByName(PIPELINE_NAME, new PipelineProjectPage(getDriver()))
                .getBuildHistoryList();

        List<String> expectedBuildOrderList = new PipelineProjectPage(getDriver())
                .getExpectedBuildHistoryDescendingList();

        Assert.assertEquals(actualBuildsOrderList, expectedBuildOrderList, "Elements are not in descending order");
    }

    @Test(dependsOnMethods = "testCheckBuildsHistoryDescendingOrder")
    @Epic("Pipeline")
    @Story("US_02.011 Build history")
    @Description("Check numbers of builds in the Build History in descending order")
    public void testSetNumberBuildsToKeep() {
        final int maxNumberBuildsToKeep = 1;

        List<String> buildList = new HomePage(getDriver())
                .clickJobByName(PIPELINE_NAME, new PipelineProjectPage(getDriver()))
                .clickSidebarConfigureButton()
                .clickDiscardOldBuilds()
                .setNumberBuildsToKeep(maxNumberBuildsToKeep)
                .clickSaveButton()
                .clickLogo()
                .scheduleBuildForItem(PIPELINE_NAME)
                .waitForBuildSchedulePopUp()
                .clickBuildHistory()
                .getBuildsList();

        Assert.assertEquals(buildList.size(), maxNumberBuildsToKeep);
    }

    @Test(dependsOnMethods = "testSetNumberBuildsToKeep")
    @Epic("Pipeline")
    @Story("US_02.004 Pipeline Configuration")
    @Description("Set 'Hello world' pipeline script")
    public void testSetPipelineScript() {
        String echoScriptName = new HomePage(getDriver())
                .clickJobByName(PIPELINE_NAME, new PipelineProjectPage(getDriver()))
                .clickSidebarConfigureButton()
                .scrollToPipelineScript()
                .selectSamplePipelineScript("hello")
                .clickSaveButton()
                .clickSidebarConfigureButton()
                .scrollToPipelineScript()
                .getScriptText();

        Assert.assertEquals(echoScriptName, "'Hello World'");
    }

    @Test
    @Epic("Pipeline")
    @Story("US_02.007 Delete pipeline")
    @Description("Delete project via sidebar menu 'Delete Pipeline'")
    public void testDeleteSidebarMenu() {
        List<String> jobList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickLogo()
                .clickJobByName(PIPELINE_NAME, new PipelineProjectPage(getDriver()))
                .clickSidebarDeleteButton()
                .clickYes(new HomePage(getDriver()))
                .getItemList();

        Assert.assertTrue(jobList.isEmpty());
    }

    @Test
    @Epic("Pipeline")
    @Story("US_02.007 Delete pipeline")
    @Description("Delete project via dropdown menu")
    public void testDeleteDropdownMenu() {
        boolean isPipelineDeleted = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickLogo()
                .openItemDropdown(PIPELINE_NAME)
                .clickDeleteInDropdown(new DeleteDialog(getDriver()))
                .clickYes(new HomePage(getDriver()))
                .isItemDeleted(PIPELINE_NAME);

        Assert.assertTrue(isPipelineDeleted, "Pipeline was not deleted successfully.");
    }

    @Test
    public void testConsoleOutputValue() {
        int number_of_stages = 8;
        List<String> expectedConsoleOuputForAllStages = List.of("test 1", "test 2", "test 3", "test 4", "test 5", "test 6", "test 7", "test 8");
        String pipelineScript = """
                pipeline {
                agent any

                stages {
                """;

        List<String> actualConsoleOuputForAllStages = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .scrollToPipelineScript()
                .sendScript(number_of_stages, pipelineScript)
                .clickSaveButton()
                .clickBuild()
                .getConsoleOuputForAllStages(number_of_stages);

        Assert.assertEquals(actualConsoleOuputForAllStages, expectedConsoleOuputForAllStages);
    }

    @Test
    @Epic("Build history")
    @Story("US_08.002 Take information about a project built")
    @Description("Check List of builds is displayed in descending'")
    public void testBuildAttributesDescending() {
        final String PIPELINE_SCRIPT = """
                pipeline {
                agent any

                stages {
                """;

        List<String> actualOrder = new HomePage(getDriver())
                .clickManageJenkins()
                .clickNodes()
                .clickOnBuiltInNode()
                .clickBringThisNodeBackOnlineButton()
                .clickLogo()

                .clickNewItem()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .sendScript(1,PIPELINE_SCRIPT)
                .clickSaveButton()
                .makeBuilds(5)
                .waitBuildToFinish()
                .getBuildHistoryList();

        List<String> expectedOrder = new ArrayList<>(actualOrder);
        expectedOrder.sort(Collections.reverseOrder());

        Assert.assertEquals(actualOrder, expectedOrder);
    }

    @Ignore
    @Test
    public void testBuildColorGreen() {

        int number_of_stages = 1;

        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/manage']"))).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='computer']"))).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//td/a[contains(@href, 'built-in')]"))).click();

        try {
            getWait2().until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[@class='jenkins-button jenkins-button--primary ']"))).click();
            getDriver().findElement(By.id("jenkins-name-icon")).click();

        } catch (Exception e) {
            getDriver().findElement(By.id("jenkins-name-icon")).click();
        }

        TestUtils.createPipelineProject(this, PIPELINE_NAME);

        String pipelineScript = """
                pipeline {
                agent any

                stages {
                """;

        getDriver().findElement(By.className("ace_text-input")).sendKeys(pipelineScript);

        for (int i = 1; i <= number_of_stages; i++) {

            String stage = "\nstage('stage " + i + "') {\n" +
                    "steps {\n" +
                    "echo 'test " + i + "'\n";
            getDriver().findElement(By.className("ace_text-input")).sendKeys(stage);
            getDriver().findElement(By.className("ace_text-input")).sendKeys(Keys.ARROW_DOWN);
            getDriver().findElement(By.className("ace_text-input")).sendKeys(Keys.ARROW_DOWN);
        }

        getDriver().findElement(By.name("Submit")).click();

        WebElement button = getDriver().findElement(By.xpath("//a[@href='/job/" + PIPELINE_NAME + "/build?delay=0sec']"));
        for (int i = 1; i <= 2; i++) {
            button.click();
            WebElement element = getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//tr[@data-runid='" + i + "']/td[@class='stage-cell stage-cell-0 SUCCESS']/div[@class='cell-color']")));
            String backgroundColor = element.getCssValue("background-color");

            Assert.assertEquals(backgroundColor, "rgba(0, 255, 0, 0.1)");
        }
    }

    @Test
    public void testStageColumnHeader() {

        int number_of_stages = 2;
        List<String> expectedHeaderNameList = new ArrayList<>();
        for (int i = 0; i < number_of_stages; i++) {
            expectedHeaderNameList.add("stage " + (i + 1));
        }

        List<String> actualStageHeaderNameList = new HomePage(getDriver())
                .clickBuildExecutorStatusLink()
                .clickBuiltInNodeName()
                .turnNodeOnIfOffline()
                .clickNewItem()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .scrollToPipelineScript()
                .sendScript(number_of_stages, pipelineScript)
                .clickSaveButton().clickBuild()
                .waitBuildToFinish()
                .getStageHeaderNameList();

        Assert.assertEquals(actualStageHeaderNameList, expectedHeaderNameList);
    }

    @Test
    public void testButtonBackgroundColor() {
        String expectedColor = "rgba(175,175,207,.175)";

        getDriver().findElement(By.cssSelector("a[href$=\"/newJob\"]")).click();
        getDriver().findElement(By.cssSelector("#name")).sendKeys("Pipeline");
        getDriver().findElement(By.xpath("//li[contains(@class, '_Folder')]")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        String actualColor = (String) js.executeScript(
                "return window.getComputedStyle(arguments[0]).getPropertyValue('--item-background--hover');",
                getDriver().findElement(By.id("description-link")));

        Assert.assertEquals(actualColor, expectedColor);
    }

    @Test
    public void testHideDescriptionPreview() {

        TestUtils.createPipelineProject(this, PIPELINE_NAME);

        boolean descriptionPreviewIsDisplayed = new HomePage(getDriver())
                .clickSpecificPipelineName(PIPELINE_NAME)
                .clickChangeDescription()
                .setDescription(DESCRIPTION)
                .clickShowDescriptionPreview()
                .clickHideDescriptionPreview()
                .isDescriptionPreviewVisible();

        Assert.assertFalse(descriptionPreviewIsDisplayed);
    }

    @Test
    public void testFullStageViewDropDownMenu() {
        TestUtils.createPipelineProject(this, PIPELINE_NAME);

        getWait5().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//li[@class='jenkins-breadcrumbs__list-item']"))).click();

        WebElement chevron = getDriver().findElement(By.xpath("//a[@href='job/" + PIPELINE_NAME + "/']//button[@class='jenkins-menu-dropdown-chevron']"));
        JavascriptExecutor jsExecutor = (JavascriptExecutor) getDriver();

        jsExecutor.executeScript("arguments[0].dispatchEvent(new Event('mouseenter'));", chevron);
        jsExecutor.executeScript("arguments[0].dispatchEvent(new Event('click'));", chevron);

        getWait5().until(ExpectedConditions.elementToBeClickable(getDriver().findElement(
                By.xpath("//a[contains(@href, 'workflow-stage')]")))).click();

        String expectedText = PIPELINE_NAME + " - Stage View";
        Assert.assertEquals(getWait5().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[@id='pipeline-box']/h2"))).getText(), expectedText);
    }

    @Test
    public void testRunByBuildNowButton() {

        String consoleOutput = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .clickJobByName(PIPELINE_NAME,
                        new PipelineProjectPage(getDriver()))
                .clickBuild()
                .waitForBuildScheduledPopUp()
                .clickLogo()
                .clickBuildHistory()
                .clickBuild1Console()
                .getConsoleOutputMessage();

        Assert.assertTrue(consoleOutput.contains(SUCCEED_BUILD_EXPECTED));
    }

    @Test(dependsOnMethods = "testRunByBuildNowButton")
    public void testRunBuildByTriangleButton() {

        String consoleOutput = new HomePage(getDriver())
                .scheduleBuildForItem(PIPELINE_NAME)
                .waitForBuildSchedulePopUp()
                .clickBuildHistory()
                .clickBuild1Console()
                .getConsoleOutputMessage();

        Assert.assertTrue(consoleOutput.contains(SUCCEED_BUILD_EXPECTED));
    }

    @Ignore
    @Test
    public void testDisablePipelineProject() {
        TestUtils.createPipelineProject(this, PIPELINE_NAME);
        getDriver().findElement(By.cssSelector("#tasks > div:nth-child(4) > span > a")).click();

        getWait60().until(ExpectedConditions.textToBePresentInElementLocated(By.id("pipeline-box"), "Stage View\n" +
                "This Pipeline has run successfully, but does not define any stages. Please use the stage step to define some stages in this Pipeline."));

        getDriver().findElement(By.cssSelector("#disable-project > button")).click();

        Assert.assertEquals("This project is currently disabled\n" + "Enable", getDriver().findElement(By.id("enable-project")).getText());

        List<WebElement> listItems = getDriver().findElements(By.className("task"));
        String[] myArray = listItems.stream().map(WebElement::getText).toArray(String[]::new);
        for (String s : myArray) {
            if (s.equals("Build Now")) {
                throw new RuntimeException("It's possible to run this pipeline job");
            }
        }

        goToMainPage(getDriver());

        Assert.assertTrue(getDriver().findElement(By.xpath("(//*[name()='svg'][@tooltip='Disabled'])[1]")).isDisplayed());

        getDriver().findElement(By.linkText("MyNewPipeline")).click();
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.cssSelector("#tasks > div:nth-child(4) > span > a")).click();

        getWait60().until(ExpectedConditions.numberOfElementsToBe(By.className("build-row-cell"), 2));

        List<WebElement> buildList = getDriver().findElements(By.className("build-row-cell"));
        String[] bArray = buildList.stream().map(WebElement::getText).toArray(String[]::new);

        Assert.assertTrue(bArray.length >= 2);
    }

    @Test
    public void testScroll() {

        boolean isPipelineScroll = new HomePage(getDriver())
                .clickCreateAJob()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .scrollToPipelineScript()
                .isPipelineDisplayed();

        Assert.assertTrue(isPipelineScroll, "Pipline doesn't scroll");
    }

    @Test
    public void testDiscardOldBuildsByCount() {

        PipelineProjectPage pipelineProjectPage = new HomePage(getDriver())
                .clickCreateAJob()
                .setItemName(PIPELINE_NAME)
                .clickProjectType(TestUtils.ProjectType.PIPELINE)
                .clickOkAnyway(new PipelineConfigPage(getDriver()))
                .clickDiscardOldBuilds()
                .setNumberBuildsToKeep(1)
                .scrollToPipelineScript()
                .selectSamplePipelineScript("hello")
                .clickSaveButton()
                .clickBuild()
                .waitBuildToFinish()
                .clickBuild()
                .waitBuildToFinish();

        Assert.assertTrue(pipelineProjectPage.isBuildAppear(2, PIPELINE_NAME), "there is no second build");
        Assert.assertEquals(pipelineProjectPage.numberOfBuild(), 1);
    }

    @Test
    @Epic("Pipeline")
    @Story("US_02.004.03 Pipeline Configuration")
    @Description("Verify the pipeline configuration has interactive sections: General, Advanced Project Options, Pipeline")
    public void testSectionsOfSidePanelAreVisible() {

        List<String> expectedSectionsNameList = new ArrayList<>(Arrays.asList("General","Advanced Project Options","Pipeline"));

        List<String> sectionsNameList = new HomePage(getDriver())
                .clickCreateAJob()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickLogo()
                .clickSpecificPipelineName(PIPELINE_NAME)
                .clickSidebarConfigureButton()
                .getSectionsNameList();

        Assert.assertEquals(sectionsNameList,expectedSectionsNameList);
    }

    @Test
    public void testAddDisplayNameInAdvancedSection() {
         String projectsDisplayNameInHeader = new HomePage(getDriver())
                .clickCreateAJob()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickAdvancedProjectOptionsMenu()
                .clickAdvancedButton()
                .setDisplayNameDescription(DESCRIPTION)
                .clickSaveButton()
                .getProjectsDisplayNameInHeader();

        Assert.assertEquals(projectsDisplayNameInHeader, DESCRIPTION);
    }

    @Test(dependsOnMethods = "testAddDisplayNameInAdvancedSection")
    public void testEditDisplayNameInAdvancedSection() {
        final String editedDisplayNameText = " - EDITED";

        String projectsDisplayNameInHeader = new HomePage(getDriver())
                .clickSpecificPipelineName(PIPELINE_NAME)
                .clickSidebarConfigureButton()
                .clickAdvancedProjectOptionsMenu()
                .clickAdvancedButton()
                .setDisplayNameDescription(editedDisplayNameText)
                .clickSaveButton()
                .getProjectsDisplayNameInHeader();

        Assert.assertTrue(projectsDisplayNameInHeader.contains(editedDisplayNameText), "DisplayName is not edited correctly");
    }

    @Test(dependsOnMethods = {"testAddDisplayNameInAdvancedSection", "testEditDisplayNameInAdvancedSection"})
    public void testDeleteDisplayNameInAdvancedSection() {
        String projectsDisplayNameInHeader = new HomePage(getDriver())
                .clickSpecificPipelineName(PIPELINE_NAME)
                .clickSidebarConfigureButton()
                .clickAdvancedProjectOptionsMenu()
                .clickAdvancedButton()
                .clearDisplayNameDescription()
                .clickSaveButton()
                .getProjectsDisplayNameInHeader();

        Assert.assertEquals(projectsDisplayNameInHeader, PIPELINE_NAME);
    }

    @Test
    public void testVerifySectionHasTooltip() {
        String labelText = "Display Name";
        String tooltipText = "Help for feature: Display Name";

        TestUtils.createPipelineProject(this, PIPELINE_NAME);

        getDriver().findElement(By.xpath("//a[contains(@href, '" + PIPELINE_NAME + "')]")).click();
        getDriver().findElement(By.xpath("//a[contains(@href, 'configure')]")).click();

        getWait5().until(ExpectedConditions.elementToBeClickable(ADVANCED_PROJECT_OPTIONS_MENU)).click();

        WebElement advancedButton = getDriver().findElement(By.xpath("//section[@class='jenkins-section']//button[@type='button']"));

        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].dispatchEvent(new Event('click'));",
                advancedButton);

        String actualTooltip = getDriver().findElement(By.xpath("//*[contains(text(), '" + labelText + "')]//a")).getAttribute("tooltip");

        Assert.assertEquals(actualTooltip, tooltipText);
    }

    @Ignore
    @Test
    public void testChoosePipelineScript() {
        TestUtils.createPipelineProject(this, PIPELINE_NAME);

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-section-id='pipeline']"))).click();

        WebElement selectScript = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class = 'samples']//select")));
        Select simpleDropDown = new Select(selectScript);
        simpleDropDown.selectByValue("github-maven");

        WebElement uncheckCheckBox = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[text()='Use Groovy Sandbox']")));
        uncheckCheckBox.click();

        WebElement link = getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@target='blank']")));
        Assert.assertTrue(link.isDisplayed(), "Uncheck doesn't work");
    }

    @Test
    public void testSetPipelineSpeedDurabilityOverride() {
        final String selectedOptionForCheck = "Less durability, a bit faster (specialty use only)";
        final int index = 1;

        String selectedOption = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickPipelineSpeedDurabilityOverrideCheckbox()
                .selectCustomPipelineSpeedDurabilityLevel(index)
                .scrollToPipelineScript()
                .clickSaveButton()
                .clickSidebarConfigureButton()
                .getCustomPipelineSpeedDurabilityLevelText();

        Assert.assertTrue(selectedOption.contains(selectedOptionForCheck));
    }

    @Test
    public void testSetQuietPeriodBuildTriggersMoreThanZero() {
        final int numberOfSeconds = 3;

        String quietPeriodInputFieldText = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .scrollToQuietPeriodCheckbox()
                .clickQuietPeriodCheckbox()
                .setNumberOfSecondsInQuietPeriodInputField(numberOfSeconds)
                .clickSaveButton()
                .clickSidebarConfigureButton()
                .scrollToQuietPeriodCheckbox()
                .getQuietPeriodInputFieldValue();

        Assert.assertEquals(quietPeriodInputFieldText, String.valueOf(numberOfSeconds),
                "The actual numberOfSeconds differs from expected result");
    }

    @Test
    public void testSetQuietPeriodBuildTriggersLessThanZero() {
        final int numberOfSeconds = -5;
        final String errorMessage = "This value should be larger than 0";

        String validationErrorMessage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .scrollToQuietPeriodCheckbox()
                .clickQuietPeriodCheckbox()
                .setNumberOfSecondsInQuietPeriodInputField(numberOfSeconds)
                .clickNumberOfSecondsHint()
                .getQuietPeriodInputErrorText();

        Assert.assertEquals(validationErrorMessage, errorMessage);
    }

    @Test
    public void testSetDoubleQuietPeriodBuildTriggers() {
        final double numberOfSeconds = 0.3;
        final String errorMessage = "Not an integer";

        String validationErrorMessage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .scrollToQuietPeriodCheckbox()
                .clickQuietPeriodCheckbox()
                .setNumberOfSecondsInQuietPeriodInputField(numberOfSeconds)
                .clickNumberOfSecondsHint()
                .getQuietPeriodInputErrorText();

        Assert.assertEquals(validationErrorMessage, errorMessage);
    }

    @Test
    public void testVerifySectionsHaveTooltips() {
        String[] labelsText = {"Display Name", "Script"};

        TestUtils.createPipelineProject(this, PIPELINE_NAME);
        getDriver().findElement(By.xpath("//a[contains(@href, '" + PIPELINE_NAME + "')]")).click();
        getDriver().findElement(By.xpath("//a[contains(@href, 'configure')]")).click();

        getWait5().until(ExpectedConditions.elementToBeClickable(ADVANCED_PROJECT_OPTIONS_MENU)).click();
        WebElement advancedButton = getDriver().findElement(By.xpath("//section[@class='jenkins-section']//button[@type='button']"));

        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].dispatchEvent(new Event('click'));",
                advancedButton);

        for (String label : labelsText) {
            String actualTooltip = getDriver().findElement(By.xpath("//*[contains(text(), '" + label + "')]//a")).getAttribute("tooltip");

            Assert.assertEquals(actualTooltip, "Help for feature: " + label);
        }
    }

    @DataProvider(name = "tooltipTextProvider")
    public Object[][] tooltipTextProvider() {
        return new Object[][] {
                { "Discard old builds" },
                { "Pipeline speed/durability override" },
                { "Preserve stashes from completed builds" },
                { "This project is parameterized" },
                { "Throttle builds" },
                {"Build after other projects are built"},
                {"Build periodically"},
                {"GitHub hook trigger for GITScm polling"},
                {"Poll SCM"},
                {"Quiet period"},
                {"Trigger builds remotely (e.g., from scripts)"},
                {"Display Name"},
                {"Script"}
        };
    }
    @Test(dataProvider = "tooltipTextProvider")
    void testVerifyConfigurationPageHaveTooltips(String tooltipText) {
        new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickAdvancedButton();

        Assert.assertTrue(new HomePage(getDriver()).isTooltipDisplayed(tooltipText),
                "Tooltip '" + tooltipText + "' is not displayed.");
    }

    @Test
    public void testSetNumberOfBuildsThrottleBuilds() {
        final String messageDay = "Approximately 24 hours between builds";

        TestUtils.createPipelineProject(this, PIPELINE_NAME);

        getDriver().findElement(By.xpath("//a[contains(@href, '" + PIPELINE_NAME + "')]")).click();
        getDriver().findElement(By.xpath("//a[contains(@href, 'configure')]")).click();

        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].scrollIntoView({block: 'center'});",
                getDriver().findElement(By.xpath("//label[text()='Throttle builds']")));

        getDriver().findElement(By.xpath("//label[text()='Throttle builds']")).click();
        WebElement selectThrottleBuilds = getDriver().findElement(By.xpath("//select[@class='jenkins-select__input select']"));
        Select simpleDropDown = new Select(selectThrottleBuilds);
        simpleDropDown.selectByValue("day");

        WebElement dayElement = getDriver().findElement(By.xpath("//div[@class='ok']"));
        getWait5().until(ExpectedConditions.visibilityOf(dayElement));

        Assert.assertEquals(dayElement.getText(), messageDay);
    }

    @Test
    public void testBuildNowWithAnError() {
        String errorMassageConsole = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .selectDropDownDefinition(2)
                .clickSaveButton()
                .clickBuild()
                .clickLogo()
                .clickBuildHistory()
                .clickBuild1Console()
                .getConsoleOutputMessage();

        Assert.assertTrue(errorMassageConsole.contains("Finished: FAILURE"));
    }
}

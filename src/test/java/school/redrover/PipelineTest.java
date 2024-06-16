package school.redrover;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.model.*;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

@Epic("Pipeline")
public class PipelineTest extends BaseTest {

    private static final String PIPELINE_NAME = "FirstPipeline";

    private static final String NEW_PIPELINE_NAME = "New Pipeline name";

    private static final String DESCRIPTION = "Lorem ipsum dolor sit amet";

    private static final String SUCCEED_BUILD_EXPECTED = "Finished: SUCCESS";

    private static final String PIPELINE_SCRIPT = "pipeline {\nagent any\n\nstages {\n";


    @Test
    @Story("US_02.000 Create Pipeline")
    @Description("Create Pipeline with valid name and verify if created project is shown on the HomePage")
    public void testCreatePipeline() {
        List<String> itemPipeline = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .getItemList();

        Allure.step(("Expected result: Item list on dashboard contains created Pipeline"));
        Assert.assertTrue(itemPipeline.contains(PIPELINE_NAME));
    }

    @Test(dependsOnMethods = "testCreatePipeline")
    @Story("US_02.000 Create Pipeline")
    @Description("Verify that the pipeline project with the same name can`t be created")
    public void testCreatePipelineSameName() {
        String itemPipeline = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(PIPELINE_NAME)
                .clickOkAnyway(new CreateNewItemPage(getDriver()))
                .getErrorMessageInvalidCharacterOrDuplicateName();

        Allure.step(("Expected result: message '» A job already exists with the name " + PIPELINE_NAME));
        Assert.assertEquals(itemPipeline, "» A job already exists with the name ‘" + PIPELINE_NAME + "’");
    }

    @Test
    @Story("US_02.000 Create Pipeline")
    @Description("Create Pipeline Project with empty name")
    public void testCreatePipelineWithEmptyName() {
        String itemPipeline = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName("")
                .clickOkAnyway(new CreateNewItemPage(getDriver()))
                .getItemNameHintText();

        Allure.step(("Expected result: message '» This field cannot be empty, please enter a valid name'"));
        Assert.assertEquals(itemPipeline, "» This field cannot be empty, please enter a valid name");
    }

    @Test
    @Story("US_02.000 Create Pipeline")
    @Description("Create Pipeline and find it by name, using 'Search' input field on Header")
    public void testCreatePipelineAndFindProject() {
        PipelineProjectPage pipelineProjectPage =  new PipelineProjectPage(getDriver());

        TestUtils.createPipelineProject(this, PIPELINE_NAME);

        String actualPipelineName = new HomePage(getDriver())
                .getHeader().typeProjectNameToSearchInputFieldAndPressEnter(PIPELINE_NAME, pipelineProjectPage)
                .getProjectName();

        Allure.step(("Expected result: Pipeline name on project page matches the name of the created one"));
        Assert.assertEquals(actualPipelineName, PIPELINE_NAME);
    }

    @Test
    @Story("US_02.005 Edit description")
    @Description("Verify text area border backlight color being active")
    public void testColorOfPipelineDescriptionTextAreaBacklight() {
        TestUtils.resetJenkinsTheme(this);

        String currentTextAreaBorderBacklightColor = new HomePage(getDriver())
                .clickCreateAJob()
                .typeItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .clickAddDescription()
                .waitAddDescriptionButtonDisappears()
                .getColorOfTextAreaBorderBacklight();

        Assert.assertEquals(currentTextAreaBorderBacklightColor, "rgba(11, 106, 162, 0.25)",
                "Current text area border backlight color is different");
    }

    @Test
    @Story("US_02.005 Edit description")
    @Description("Verify text area border backlight color by default")
    public void testPipelineDescriptionTextAreaBacklightDefaultColor() {
        TestUtils.resetJenkinsTheme(this);

        String defaultTextAreaBorderBacklightColor = new HomePage(getDriver())
                .clickCreateAJob()
                .typeItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .clickAddDescription()
                .makeDescriptionFieldNotActive()
                .getColorOfDefaultTextAreaBorderBacklight();

        Assert.assertEquals(defaultTextAreaBorderBacklightColor, "rgba(11,106,162,.25)");
    }

    @Test
    @Story("US_02.007 Delete Pipeline")
    @Description("Verify the pop-up 'Yes' button color is red if user deletes Pipeline using sidebar")
    public void testYesButtonColorDeletingPipelineInSidebar() {
        TestUtils.resetJenkinsTheme(this);

        String yesButtonHexColor = new HomePage(getDriver())
                .clickCreateAJob()
                .typeItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .clickDeleteOnSidebar()
                .getYesButtonColorDeletingViaSidebar();

        Assert.assertEquals(yesButtonHexColor, "#e6001f", "The confirmation button color is not red");
    }

    @Test
    @Story("US_02.007 Delete Pipeline")
    @Description("Verify Pipeline can be deleted via breadcrumbs")
    public void testDeleteViaBreadcrumbs() {
        boolean isPipelineDeleted = new HomePage(getDriver())
                .clickCreateAJob()
                .typeItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .clickSpecificPipelineName(PIPELINE_NAME)
                .hoverOverBreadcrumbsName()
                .clickBreadcrumbsDropdownArrow()
                .clickDeleteOnBreadcrumbsMenu()
                .clickYes()
                .isItemDeleted(PIPELINE_NAME);

        Assert.assertTrue(isPipelineDeleted, PIPELINE_NAME + " was not deleted");
    }

    @Test
    @Story("US_02.007 Delete Pipeline")
    @Description("Verify Pipeline builds disappeared from Build History page upon its removal")
    public void testBuildHistoryEmptyUponPipelineRemoval() {
        boolean isBuildDeleted = new HomePage(getDriver())
                .clickCreateAJob()
                .typeItemName(PIPELINE_NAME)
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
    @Story("US_02.005 Edit description")
    @Description("Add a description to the Pipeline")
    public void testAddDescription() {
        String descriptionText = new HomePage(getDriver())
                .clickCreateAJob()
                .typeItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .clickAddDescription()
                .typeDescription(DESCRIPTION)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(descriptionText, DESCRIPTION);
    }

    @Test(dependsOnMethods = "testAddDescription")
    @Story("US_02.005 Edit description")
    @Description("Edit current description")
    public void testEditDescription() {
        final String addedToDescription = ", consectetur adipiscing elit.";
        final String expectedDescription = DESCRIPTION + addedToDescription;

        String descriptionText = new HomePage(getDriver())
                .clickSpecificPipelineName(PIPELINE_NAME)
                .clickAddDescription()
                .clickOnDescriptionInput()
                .typeDescription(addedToDescription)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(descriptionText, expectedDescription);
    }

    @Test
    @Story("US_02.008 Rename Pipeline")
    @Description("Rename project via sidebar")
    public void testRenamePipelineViaSidebar() {
        String displayedName = new HomePage(getDriver())
                .clickCreateAJob()
                .typeItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .clickRenameOnSidebar()
                .clearNameInputField()
                .typeNewName(NEW_PIPELINE_NAME)
                .clickRenameButton()
                .getProjectName();

        Assert.assertEquals(displayedName, NEW_PIPELINE_NAME);
    }

    @Test
    @Story("US_02.009 Full stage view")
    @Description("Project's name and 'Stage View' are displayed after clicking 'Full Stage View' button in the sidebar")
    public void testFullStageViewButton() {

        final String pipelineName = "New Pipeline";
        final String expectedResult = pipelineName + " - Stage View";

        String h2HeadingText = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(pipelineName)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .clickSpecificPipelineName(pipelineName)
                .clickFullStageViewButton()
                .getH2HeadingText();

        Assert.assertEquals(h2HeadingText, expectedResult);
    }

    @Test
    @Story("US_02.009 Full stage view")
    @Description("Verify the presence of the full stage view button in the sidebar")
    void testVerifyThePresenceOfTheFullStageViewButtonInTheSidebar() {
        String pipelineName = "New Pipeline group_java_autoqa_rrschool";

        new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(pipelineName)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .clickSpecificPipelineName(pipelineName);

        Assert.assertTrue(new PipelineProjectPage(getDriver()).isTaskPresentOnSidebar("Full Stage View"));
    }

    @Test
    @Story("US_02.009 Full stage view")
    @Description("Verify that the breadcrumb navigation displays the correct hierarchy")
    public void testBreadcrumbsOnFullStageViewPage() {
        final String expectedResult = "Dashboard > " + PIPELINE_NAME + " > Full Stage View";

        String actualResult = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .clickSpecificPipelineName(PIPELINE_NAME)
                .clickFullStageViewButton()
                .getBreadcrumbsText();

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test(dependsOnMethods = "testBreadcrumbsOnFullStageViewPage")
    @Story("US_02.009 Full stage view")
    @Description("Button Color Changes on Hover")
    public void testColorWhenHoveringMouseOnFullStageViewButton() {

        final String expectedColor = "rgba(175, 175, 207, 0.15)";

        String backgroundColorBeforeHover = new HomePage(getDriver())
                .clickSpecificPipelineName(PIPELINE_NAME)
                .getColorOfFullStageViewButtonBackground();

        String backgroundColorAfterHover = new PipelineProjectPage(getDriver())
                .hoverOnFullStageViewButton()
                .getColorOfFullStageViewButtonBackground();

        Assert.assertTrue(!backgroundColorAfterHover.equals(backgroundColorBeforeHover)
                && backgroundColorAfterHover.equals(expectedColor));
    }

    @Test
    @Story("US_02.009 Full stage view")
    @Description("Verify the heading after clicking the ‘Full Stage View’ button in the dropdown menu displays")
    public void testFullStageViewButtonInDropdown() {

        final String pipelineName = PIPELINE_NAME;
        final String expectedResult = PIPELINE_NAME + " - Stage View";

        String h2HeadingText = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(pipelineName)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .openItemDropdown(pipelineName)
                .clickFullStageViewOnDropdown()
                .getH2HeadingText();

        Assert.assertEquals(h2HeadingText, expectedResult);
    }

    @Test
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
                .typeItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .sendScript(stagesQtt, PIPELINE_SCRIPT)
                .clickSaveButton()
                .makeBuilds(buildsQtt)
                .clickFullStageViewButton()
                .getItemList();

        Assert.assertEquals(actualBuildsList, expectedBuildsList);
    }

    @Test
    @Story("US_02.002 View changes")
    @Description("Verify Changes page opens by clicking 'Changes' in drop-down menu at Pipeline name on Pipeline page")
    public void testChangesPageHeading() {
        String actualPageHeading = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .hoverOverBreadcrumbsName()
                .clickBreadcrumbsDropdownArrow()
                .clickDropdownChangesButton()
                .getPageHeading();

        Assert.assertEquals(actualPageHeading, "Changes");
    }

    @Test
    @Story("US_02.008 Rename Pipeline")
    @Description("Rename project via breadcrumbs")
    public void testRenameJobViaBreadcrumbs() {
        String displayedNewName = new HomePage(getDriver())
                .clickCreateAJob()
                .typeItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .clickBreadcrumbsDropdownArrow()
                .clickRenameOnBreadcrumbs()
                .clearNameInputField()
                .typeNewName(NEW_PIPELINE_NAME)
                .clickRenameButton()
                .getProjectName();

        Assert.assertEquals(displayedNewName, NEW_PIPELINE_NAME);
    }

    @Test
    @Story("US_02.005 Edit description")
    @Description("Use preview option to view description")
    public void testAddDescriptionPreview() {
        String previewDescription = new HomePage(getDriver())
                .clickCreateAJob()
                .typeItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .addDescription(DESCRIPTION)
                .clickPreview()
                .getTextareaPreviewText();

        Assert.assertEquals(previewDescription, DESCRIPTION);
    }

    @Test
    @Story("US_02.004 Verify the Pipeline configuration")
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
                .typeItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .sendScript(stagesQtt, PIPELINE_SCRIPT)
                .clickSaveButton()
                .makeBuilds(buildsQtt)
                .getStagesQuantity();

        Assert.assertEquals(actualSagesQtt, stagesQtt);
    }

    @Test
    @Story("US_02.006 Disable project")
    @Description("Verify 'Disable Project' button is visible")
    public void testVisibilityOfDisableButton() {
        boolean isDisableButtonDisplayed = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .isDisableButtonVisible();

        Allure.step("Expected result: 'Disable Project' button is visible");
        Assert.assertTrue(isDisableButtonDisplayed, "Can't find the button");
    }

    @Test
    @Story("US_02.006 Disable project")
    @Description("Disable Pipeline and check message about it")
    public void testDisablePipeline() {
        TestUtils.createPipelineProject(this, PIPELINE_NAME);

        String warningMessage = new HomePage(getDriver())
                .clickSpecificPipelineName(PIPELINE_NAME)
                .clickDisableButton()
                .getWarningMessageText();

        Allure.step("Expected result: The right message about disabled project status is displayed");
        Assert.assertTrue(warningMessage.contains("This project is currently disabled"));
    }

    @Test(dependsOnMethods = "testDisablePipeline")
    @Story("US_02.006 Disable project")
    @Description("Checking for a button of Schedule a Build for the Pipeline")
    public void testCheckForButtonOfScheduleABuild() {

        Allure.step("Expected result: Button of Schedule a Build for the Pipeline is displayed");
        Assert.assertFalse(new HomePage(getDriver()).isButtonOfScheduleABuildExist(PIPELINE_NAME));
    }

    @Test(dependsOnMethods = {"testCheckForButtonOfScheduleABuild", "testDisablePipeline"})
    @Story("US_02.006 Disable project")
    @Description("Verify disabled project can be enabled")
    public void testEnableBack() {
        String pipelineStatus = new HomePage(getDriver())
                .clickJobByName(PIPELINE_NAME, new PipelineProjectPage(getDriver()))
                .clickEnableButton()
                .clickLogo()
                .getBuildStatus();

        Allure.step("Expected result: Tooltip - Schedule a Build for " + PIPELINE_NAME);
        Assert.assertEquals(pipelineStatus, "Schedule a Build for " + PIPELINE_NAME);
    }

    @Test(dependsOnMethods = "testEnableBack")
    @Story("US_02.011 Take the information about a project built")
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
    @Story("US_02.011 Take the information about a project built")
    @Description("Successful build is marked green")
    public void testGreenBuildSuccessColor() {
        final String greenHexColor = "#1ea64b";

        String actualHexColor = new HomePage(getDriver())
                .clickSpecificPipelineName(PIPELINE_NAME)
                .getHexColorSuccessMark();

        Assert.assertEquals(actualHexColor, greenHexColor);
    }

    @Test(dependsOnMethods = "testGreenBuildSuccessColor")
    @Story("US_02.011 Take the information about a project built")
    @Description("Сheck builds history descending order")
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
    @Story("US_02.011 Take the information about a project built")
    @Description("Check numbers of builds in the Build History in descending order")
    public void testSetNumberBuildsToKeep() {
        final int maxNumberBuildsToKeep = 1;

        List<String> buildList = new HomePage(getDriver())
                .clickJobByName(PIPELINE_NAME, new PipelineProjectPage(getDriver()))
                .clickConfigureOnSidebar()
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
    @Story("US_02.004 Verify the Pipeline configuration")
    @Description("Set 'Hello world' pipeline script")
    public void testSetPipelineScript() {
        String echoScriptName = new HomePage(getDriver())
                .clickJobByName(PIPELINE_NAME, new PipelineProjectPage(getDriver()))
                .clickConfigureOnSidebar()
                .scrollToPipelineScript()
                .selectSamplePipelineScript("hello")
                .clickSaveButton()
                .clickConfigureOnSidebar()
                .scrollToPipelineScript()
                .getScriptText();

        Assert.assertEquals(echoScriptName, "'Hello World'");
    }

    @Test
    @Story("US_02.007 Delete Pipeline")
    @Description("Delete project via sidebar menu 'Delete Pipeline'")
    public void testDeleteSidebarMenu() {
        List<String> jobList = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickLogo()
                .clickJobByName(PIPELINE_NAME, new PipelineProjectPage(getDriver()))
                .clickDeleteOnSidebar()
                .clickYes()
                .getItemList();

        Assert.assertTrue(jobList.isEmpty());
    }

    @Test
    @Story("US_02.007 Delete Pipeline")
    @Description("Delete project via dropdown menu")
    public void testDeleteDropdownMenu() {
        boolean isPipelineDeleted = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickLogo()
                .openItemDropdown(PIPELINE_NAME)
                .clickDeleteInDropdown(new DeleteDialog(getDriver()))
                .clickYes(new HomePage(getDriver()))
                .isItemDeleted(PIPELINE_NAME);

        Assert.assertTrue(isPipelineDeleted, "Pipeline was not deleted successfully.");
    }

    @Test
    @Story("US_02.003 Build now")
    @Description("02.003.01 Verify Console Ouput For All Stages")
    public void testConsoleOutputValue() {
        int numberOfStages = 8;
        List<String> expectedConsoleOuputForAllStages = List.of(
                "test 1", "test 2", "test 3",
                "test 4", "test 5", "test 6",
                "test 7", "test 8");
        String pipelineScript = """
                pipeline {
                agent any

                stages {
                """;

        List<String> actualConsoleOuputForAllStages = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .scrollToPipelineScript()
                .sendScript(numberOfStages, pipelineScript)
                .clickSaveButton()
                .clickBuildNowOnSidebarAndWait()
                .getConsoleOutputForAllStages(numberOfStages);

        Assert.assertEquals(actualConsoleOuputForAllStages, expectedConsoleOuputForAllStages);
    }

    @Test
    @Story("US_02.011 Take the information about a project built")
    @Description("Check List of builds is displayed in descending'")
    public void testBuildAttributesDescending() {
        final String pipelineScript = """
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
                .typeItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .sendScript(1, pipelineScript)
                .clickSaveButton()
                .makeBuilds(5)
                .getBuildHistoryList();

        List<String> expectedOrder = new ArrayList<>(actualOrder);
        expectedOrder.sort(Collections.reverseOrder());

        Assert.assertEquals(actualOrder, expectedOrder);
    }

    @Test
    @Story("US_02.011 Take the information about a project built")
    @Description("Successful builds are marked with a green indicator when creating the list of builds.")
    public void testBuildColorGreen() {
        String backgroundColor = new HomePage(getDriver())
                .clickManageJenkins()
                .clickNodes()
                .clickOnBuiltInNode()
                .clickBringThisNodeBackOnlineButton()
                .clickLogo()

                .clickNewItem()
                .typeItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .sendScript(1, PIPELINE_SCRIPT)
                .clickSaveButton()
                .makeBuilds(2)
                .getColorOfCell();

        Assert.assertEquals(backgroundColor, "rgba(0, 255, 0, 0.1)");
    }

    @Test
    @Story("US_02.003 Build now")
    @Description("02.003.02 Verify column header for each stage of the build")
    public void testStageColumnHeader() {

        int numberOfStages = 2;
        List<String> expectedHeaderNameList = new ArrayList<>();
        for (int i = 0; i < numberOfStages; i++) {
            expectedHeaderNameList.add("stage " + (i + 1));
        }

        List<String> actualStageHeaderNameList = new HomePage(getDriver())
                .clickBuildExecutorStatusLink()
                .clickBuiltInNodeName()
                .turnNodeOnIfOffline()
                .clickNewItem()
                .typeItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .scrollToPipelineScript()
                .sendScript(numberOfStages, PIPELINE_SCRIPT)
                .clickSaveButton()
                .clickBuildNowOnSidebarAndWait()
                .getStageHeaderNameList();

        Assert.assertEquals(actualStageHeaderNameList, expectedHeaderNameList);
    }

    @Test
    @Story("US_02.004 Verify the Pipeline configuration")
    @Description("02.004.12 Verify that description preview can be hidden")
    public void testHideDescriptionPreview() {

        TestUtils.createPipelineProject(this, PIPELINE_NAME);

        boolean descriptionPreviewIsDisplayed = new HomePage(getDriver())
                .clickSpecificPipelineName(PIPELINE_NAME)
                .clickAddDescription()
                .typeDescription(DESCRIPTION)
                .clickShowDescriptionPreview()
                .clickHideDescriptionPreview()
                .isDescriptionPreviewVisible();

        Assert.assertFalse(descriptionPreviewIsDisplayed);
    }

    @Test
    @Story("US_02.009 Full Stage View")
    @Description("Verify the heading after clicking on the ‘Full Stage View’ on the dropdown menu")
    public void testFullStageViewOnDropdownMenu() {
        final String expectedText = PIPELINE_NAME + " - Stage View";

        TestUtils.createPipelineProject(this, PIPELINE_NAME);

        String pageHeading = new HomePage(getDriver())
                .openItemDropdown(PIPELINE_NAME)
                .clickFullStageViewOnDropdown()
                .getH2HeadingText();

        Allure.step("Expected result: Page heading - '" + expectedText + "'");
        Assert.assertEquals(pageHeading, expectedText);
    }

    @Test
    @Story("US_02.003 Build now")
    @Description("02.003.03 Verify that build is finished successfully")
    public void testRunByBuildNowButton() {

        String consoleOutput = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .clickSpecificPipelineName(PIPELINE_NAME)
                .clickBuildNowOnSidebarAndWait()
                .clickLogo()
                .clickBuildHistory()
                .clickBuild1Console()
                .getConsoleOutputMessage();

        Assert.assertTrue(consoleOutput.contains(SUCCEED_BUILD_EXPECTED));
    }

    @Test(dependsOnMethods = "testRunByBuildNowButton")
    @Story("US_02.004 Verify the Pipeline configuration")
    @Description("02.004.11 Verify that build run by schedule is finished successfully")
    public void testRunBuildByTriangleButton() {

        String consoleOutput = new HomePage(getDriver())
                .scheduleBuildForItem(PIPELINE_NAME)
                .waitForBuildSchedulePopUp()
                .clickBuildHistory()
                .clickBuild1Console()
                .getConsoleOutputMessage();

        Assert.assertTrue(consoleOutput.contains(SUCCEED_BUILD_EXPECTED));
    }

    @Test
    @Story("US_02.006 Disable project")
    @Description("Checking for a missing 'Build Now' on sidebar for disabled Pipeline")
    public void testCheckForMissingBuildNowOnSidebar() {
        TestUtils.createPipelineProject(this, PIPELINE_NAME);

        boolean isTaskPresentOnSidebar = new HomePage(getDriver())
                .clickSpecificPipelineName(PIPELINE_NAME)
                .clickDisableButton()
                .isTaskPresentOnSidebar("Build Now");

        Allure.step("Expected result: The task 'Build Now' is not present on sidebar");
        Assert.assertFalse(isTaskPresentOnSidebar);
    }

    @Test
    @Story("US_02.006 Disable project")
    @Description("Checking tooltip of status icon on Dashboard")
    public void testCheckTooltipOfStatusIcon() {
        TestUtils.createPipelineProject(this, PIPELINE_NAME);

        String statusIconTooltip = new HomePage(getDriver())
                .clickSpecificPipelineName(PIPELINE_NAME)
                .clickDisableButton()
                .clickLogo()
                .getStatusIconTooltip();

        Allure.step("Expected result: Status of last build Icon have tooltip 'Disabled'");
        Assert.assertEquals(statusIconTooltip, "Disabled");
    }

    @Test
    @Story("US_02.004 Verify the Pipeline configuration")
    @Description("02.004.13 Verify that Pipeline side bar menu item scrolls to Pipeline section")
    public void testScroll() {

        boolean isPipelineScroll = new HomePage(getDriver())
                .clickCreateAJob()
                .typeItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .scrollToPipelineScript()
                .isPipelineDisplayed();

        Assert.assertTrue(isPipelineScroll, "Pipline doesn't scroll");
    }

    @Test
    @Story("US_02.004 Verify the Pipeline configuration")
    @Description("02.004.14 Verify that Discard OldB uilds By Count option is saved")
    public void testDiscardOldBuildsByCount() {

        PipelineProjectPage pipelineProjectPage = new HomePage(getDriver())
                .clickCreateAJob()
                .typeItemName(PIPELINE_NAME)
                .clickProjectType("Pipeline")
                .clickOkAnyway(new PipelineConfigPage(getDriver()))
                .clickDiscardOldBuilds()
                .setNumberBuildsToKeep(1)
                .scrollToPipelineScript()
                .selectSamplePipelineScript("hello")
                .clickSaveButton()
                .clickBuildNowOnSidebarAndWait()
                .clickBuildNowOnSidebarAndWait();

        Assert.assertTrue(pipelineProjectPage.isBuildAppear(2, PIPELINE_NAME), "there is no second build");
        Assert.assertEquals(pipelineProjectPage.numberOfBuild(), 1);
    }

    @Test
    @Story("US_02.004 Verify the Pipeline configuration")
    @Description("Verify that Pipeline configuration has interactive sections: "
            + "General, Advanced Project Options, Pipeline")
    public void testSectionsOfSidePanelAreVisible() {

        List<String> expectedSectionsNameList = List.of("General", "Advanced Project Options", "Pipeline");

        List<String> sectionsNameList = new HomePage(getDriver())
                .clickCreateAJob()
                .typeItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickLogo()
                .clickSpecificPipelineName(PIPELINE_NAME)
                .clickConfigureOnSidebar()
                .getSectionsNameList();

        Assert.assertEquals(sectionsNameList, expectedSectionsNameList);
    }

    @Test
    public void testAddDisplayNameInAdvancedSection() {
        String projectsDisplayNameInHeader = new HomePage(getDriver())
                .clickCreateAJob()
                .typeItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickAdvancedProjectOptionsMenu()
                .clickAdvancedButton()
                .setDisplayNameDescription(DESCRIPTION)
                .clickSaveButton()
                .getProjectName();

        Assert.assertEquals(projectsDisplayNameInHeader, DESCRIPTION);
    }

    @Test(dependsOnMethods = "testAddDisplayNameInAdvancedSection")
    public void testEditDisplayNameInAdvancedSection() {
        final String editedDisplayNameText = " - EDITED";

        String projectsDisplayNameInHeader = new HomePage(getDriver())
                .clickSpecificPipelineName(PIPELINE_NAME)
                .clickConfigureOnSidebar()
                .clickAdvancedProjectOptionsMenu()
                .clickAdvancedButton()
                .setDisplayNameDescription(editedDisplayNameText)
                .clickSaveButton()
                .getProjectName();

        Assert.assertTrue(projectsDisplayNameInHeader.contains(editedDisplayNameText),
                "DisplayName is not edited correctly");
    }

    @Test(dependsOnMethods = {"testAddDisplayNameInAdvancedSection", "testEditDisplayNameInAdvancedSection"})
    public void testDeleteDisplayNameInAdvancedSection() {
        String projectsDisplayNameInHeader = new HomePage(getDriver())
                .clickSpecificPipelineName(PIPELINE_NAME)
                .clickConfigureOnSidebar()
                .clickAdvancedProjectOptionsMenu()
                .clickAdvancedButton()
                .clearDisplayNameDescription()
                .clickSaveButton()
                .getProjectName();

        Assert.assertEquals(projectsDisplayNameInHeader, PIPELINE_NAME);
    }

    @Test
    @Story("US_02.004 Verify the Pipeline configuration")
    @Description("02.004.06 Verify that Script Approval Link is Shown when Use Groovy Sandbox Checkbox is unchecked")
    public void testUncheckUseGroovySandboxCheckbox() {
        TestUtils.createPipelineProject(this, PIPELINE_NAME);

        boolean isScriptApprovalLinkShown = new HomePage(getDriver())
                .clickSpecificPipelineName(PIPELINE_NAME)
                .clickConfigureOnSidebar()
                .scrollToPipelineScript()
                .selectSamplePipelineScript("github-maven")
                .clickOnUseGroovySandboxCheckbox()
                .isScriptApprovalLinkShown();

        Assert.assertTrue(isScriptApprovalLinkShown, "Uncheck of 'Use Groovy Sandbox' checkbox doesn't work");
    }

    @Test
    @Story("US_02.004 Verify the Pipeline configuration")
    @Description("02.004.07 Verify that Custom Pipeline Speed Durability Level is saved")
    public void testSetPipelineSpeedDurabilityOverride() {
        final String selectedOptionForCheck = "Less durability, a bit faster (specialty use only)";
        final int index = 1;

        String selectedOption = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickPipelineSpeedDurabilityOverrideCheckbox()
                .selectCustomPipelineSpeedDurabilityLevel(index)
                .scrollToPipelineScript()
                .clickSaveButton()
                .clickConfigureOnSidebar()
                .getCustomPipelineSpeedDurabilityLevelText();

        Assert.assertTrue(selectedOption.contains(selectedOptionForCheck));
    }

    @Test
    @Story("US_02.004 Verify the Pipeline configuration")
    @Description("02.004.08 Verify that user is able to set Quiet Period Build Triggers more than zero")
    public void testSetQuietPeriodBuildTriggersMoreThanZero() {
        final int numberOfSeconds = 3;

        String quietPeriodInputFieldText = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .scrollToQuietPeriodCheckbox()
                .clickQuietPeriodCheckbox()
                .setNumberOfSecondsInQuietPeriodInputField(numberOfSeconds)
                .clickSaveButton()
                .clickConfigureOnSidebar()
                .scrollToQuietPeriodCheckbox()
                .getQuietPeriodInputFieldValue();

        Assert.assertEquals(quietPeriodInputFieldText, String.valueOf(numberOfSeconds),
                "The actual numberOfSeconds differs from expected result");
    }

    @Test
    @Story("US_02.004 Verify the Pipeline configuration")
    @Description("02.004.09 Verify that user is not able to set Quiet Period Build Triggers than than zero")
    public void testSetQuietPeriodBuildTriggersLessThanZero() {
        final int numberOfSeconds = -5;
        final String errorMessage = "This value should be larger than 0";

        String validationErrorMessage = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .scrollToQuietPeriodCheckbox()
                .clickQuietPeriodCheckbox()
                .setNumberOfSecondsInQuietPeriodInputField(numberOfSeconds)
                .clickNumberOfSecondsHint()
                .getQuietPeriodInputErrorText();

        Assert.assertEquals(validationErrorMessage, errorMessage);
    }

    @Test
    @Story("US_02.004 Verify the Pipeline configuration")
    @Description("02.004.10 Verify that user is not able to set double value to Quiet Period Build Triggers")
    public void testSetDoubleQuietPeriodBuildTriggers() {
        final double numberOfSeconds = 0.3;
        final String errorMessage = "Not an integer";

        String validationErrorMessage = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .scrollToQuietPeriodCheckbox()
                .clickQuietPeriodCheckbox()
                .setNumberOfSecondsInQuietPeriodInputField(numberOfSeconds)
                .clickNumberOfSecondsHint()
                .getQuietPeriodInputErrorText();

        Assert.assertEquals(validationErrorMessage, errorMessage);
    }

    @DataProvider(name = "tooltipTextProvider")
    public Object[][] tooltipTextProvider() {
        return new Object[][]{
                {"Discard old builds"},
                {"Pipeline speed/durability override"},
                {"Preserve stashes from completed builds"},
                {"This project is parameterized"},
                {"Throttle builds"},
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
    @Story("US_02.004 Verify the Pipeline configuration")
    @Description("Verify that three pipeline configure sections have toolTips")
    void testVerifyConfigureSectionsHaveTooltips(String tooltipText) {
        new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickAdvancedButton();

        Assert.assertTrue(new HomePage(getDriver()).isTooltipDisplayed(tooltipText),
                "Tooltip '" + tooltipText + "' is not displayed.");
    }


    @DataProvider
    private Object[][] dataForThrottleBuilds() {

        return new Object[][]{
                {"1", "second", "Approximately a second between builds"},
                {"2", "minute", "Approximately 30 seconds between builds"},
                {"3", "hour", "Approximately 20 minutes between builds"},
                {"4", "day", "Approximately 6 hours between builds"},
                {"6", "week", "Approximately 28 hours between builds"},
                {"20", "month", "Approximately 37 hours between builds"},
                {"50", "year", "Approximately 7 days between builds"}
        };
    }

    @Test(dataProvider = "dataForThrottleBuilds")
    @Story("US_02.004 Verify the Pipeline configuration")
    @Description("Set number of builds, time period in Throttle builds "
            + "and verify message about time between builds after")
    public void testSetParametersToThrottleBuilds(String numberOfBuilds, String timePeriod, String expectedMessage) {
        TestUtils.createPipelineProject(this, PIPELINE_NAME);

        String messageAboutTimePeriod = new HomePage(getDriver())
                .clickSpecificPipelineName(PIPELINE_NAME)
                .clickConfigureOnSidebar()
                .clickOnThrottleBuilds()
                .clearNumberOfBuilds()
                .typeNumberOfBuilds(numberOfBuilds)
                .selectTimePeriod(timePeriod)
                .getMessageAboutTimeBetweenBuilds();

        Allure.step("Expected result: Time between builds is calculated and displayed correctly");
        Assert.assertEquals(messageAboutTimePeriod, expectedMessage);
    }

    @Test
    @Story("US_02.004 Verify the Pipeline configuration")
    @Description("02.004.11 Verify that build is failed when user set to use Pipeline script from repository and don't set the repository")
    public void testBuildWithoutScriptRepository() {
        String errorMessageConsole = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .selectDropdownDefinition(2)
                .clickSaveButton()
                .clickBuildNowOnSidebarAndWait()
                .clickLogo()
                .clickBuildHistory()
                .clickBuild1Console()
                .getConsoleOutputMessage();

        Assert.assertTrue(errorMessageConsole.contains("Finished: FAILURE"));
    }
}

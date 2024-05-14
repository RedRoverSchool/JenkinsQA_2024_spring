package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.model.*;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static school.redrover.runner.TestUtils.goToMainPage;

public class PipelineTest extends BaseTest {

    private static final String PIPELINE_NAME = "FirstPipeline";
    private static final String NEW_PIPELINE_NAME = "New Pipeline name";
    private static final String DESCRIPTION = "Lorem ipsum dolor sit amet";
    private static final String SUCCEED_BUILD_EXPECTED = "Finished: SUCCESS";
    private static final String displayNameText = "This is project's Display name text for Advanced Project Options";
    List<String> nameProjects = List.of("PPProject", "PPProject2");
    private static final By SAVE_BUTTON_CONFIGURATION = By.xpath("//button[@formnovalidate='formNoValidate']");
    private static final By TOGGLE_SWITCH_ENABLE_DISABLE = By.xpath("//label[@data-title='Disabled']");
    private static final By ADVANCED_PROJECT_OPTIONS_MENU = By.xpath("//button[@data-section-id='advanced-project-options']");
    private static final By DISPLAY_NAME_TEXT_FIELD = By.xpath("//div[@class='setting-main']//input[contains(@checkurl, 'checkDisplayName')]");

    private void CreatePipelineProject() {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys("MyNewPipeline");
        getDriver().findElement(By.cssSelector("#j-add-item-type-standalone-projects > ul > li:nth-child(2)")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
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

    private void turnNodeOnIfOffline() {
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
    }

    private void createPipelineProject(String pipelineProject) {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.name("name")).sendKeys(pipelineProject);
        getDriver().findElement(By.cssSelector("[class$='WorkflowJob']")).click();
        getDriver().findElement(By.id("ok-button")).click();
    }

    private void clickConfigButton() {
        getWait5().until(ExpectedConditions.elementToBeClickable(getDriver().findElement(
                By.xpath("//a[contains(@href, 'configure')]")))).click();
    }

    public void createNewPipeline(String pipelineName) {
        getWait5().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@href='/view/all/newJob']"))).click();
        getWait5().until(ExpectedConditions.presenceOfElementLocated(By.id("name"))).sendKeys(pipelineName);
        getDriver().findElement(By.xpath("//span[text()='Pipeline']")).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@name='Submit']"))).click();
    }

    public void goHomePage() {
        getWait5().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//li[@class='jenkins-breadcrumbs__list-item']"))).click();
    }

    private Actions actions;

    private Actions getActions() {
        if (actions == null) {
            actions = new Actions(getDriver());
        }
        return actions;
    }

    public void createPipeline() {
        getDriver().findElement(By.xpath("//span[contains(text(),'Create')]")).click();
        getDriver().findElement(By.id("name")).sendKeys(PIPELINE_NAME);
        getDriver().findElement(By.xpath("//li[contains(@class,'WorkflowJob')]")).click();
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.id("ok-button")));
        getDriver().findElement(By.id("ok-button")).click();
    }

    public void navigateToConfigurePageFromDashboard() {
        getDriver().findElement(By.xpath("//a[contains(@href, '" + PIPELINE_NAME + "')]")).click();
        getDriver().findElement(By.xpath("//a[contains(@href, 'configure')]")).click();
    }

    public void clickOnAdvancedButton() {
        WebElement advancedButton = getDriver().findElement(By.xpath("//section[@class='jenkins-section']//button[@type='button']"));

        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].dispatchEvent(new Event('click'));",
                advancedButton);
    }

    public void scrollCheckBoxQuietPeriodIsVisible() {
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].scrollIntoView();",
                getDriver().findElement(By.xpath("//label[text()='Poll SCM']")));
    }

    public void scrollCheckBoxThrottleBuildsIsVisible() {
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].scrollIntoView({block: 'center'});",
                getDriver().findElement(By.xpath("//label[text()='Throttle builds']")));
    }

    @Test
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
    public void testCreatePipelineSameName() {

        String itemPipeline = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PIPELINE_NAME)
                .clickOkAnyway(new CreateNewItemPage(getDriver()))
                .getErrorMessage();

        Assert.assertEquals(itemPipeline, "» A job already exists with the name ‘" + PIPELINE_NAME + "’");
    }

    @Test
    public void testCreatePipelineWithEmptyName() {

        String itemPipeline = new HomePage(getDriver())
                .clickNewItem()
                .setItemName("")
                .clickOkAnyway(new CreateNewItemPage(getDriver()))
                .getItemNameHintText();

        Assert.assertEquals(itemPipeline, "» This field cannot be empty, please enter a valid name");
    }

    @Test(dependsOnMethods = "testCreatePipeline")
    public void testFindPipelineProject() {

        String searchResult = new HeaderBlock(getDriver())
                .enterRequestIntoSearchBox(PIPELINE_NAME)
                .makeClickToSearchBox()
                .getTitleText();

        Assert.assertEquals(searchResult, PIPELINE_NAME);
    }

    @Test
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

        Assert.assertTrue(new PipelineProjectPage(getDriver()).isBtnPresentInSidebar("Full Stage View"));
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

        String backgroundColorAfterHover = new PipelineProjectPage(getDriver())
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

    @Test
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
                .sendScript(stagesQtt)
                .clickSaveButton()
                .makeBuilds(buildsQtt)
                .clickFullStageViewButton()
                .getItemList();

        Assert.assertEquals(actualBuildsList, expectedBuildsList);
    }

    @Test
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
                .getHeadlineDisplayedName();

        Assert.assertEquals(displayedNewName, NEW_PIPELINE_NAME);
    }

    @Test
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
                .sendScript(stagesQtt)
                .clickSaveButton()
                .makeBuilds(buildsQtt)
                .getSagesQtt();

        Assert.assertEquals(actualSagesQtt, stagesQtt);
    }

    @Test
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
    public void testPipelineNotActive() {

        String actualProjectName = getDriver().findElement(By.xpath("//tbody//td[3]//a[contains(@href, 'job/')]/span")).getText();
        Assert.assertEquals(actualProjectName, PIPELINE_NAME);

        List<WebElement> scheduleABuildArrows = getDriver().findElements(
                By.xpath("//table//a[@title= 'Schedule a Build for " + PIPELINE_NAME + "']"));
        Assert.assertEquals(scheduleABuildArrows.size(), 0);
    }

    @Test(dependsOnMethods = "testPipelineNotActive")
    public void testEnableBack() {
        String pipelineStatus = new HomePage(getDriver())
                .clickJobByName(PIPELINE_NAME, new PipelineProjectPage(getDriver()))
                .clickEnableButton()
                .clickLogo()
                .getBuildStatus();

        Assert.assertEquals(pipelineStatus, "Schedule a Build for " + PIPELINE_NAME);
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

    @Test
    public void testBuildAttributesDescending() {

        int number_of_stages = 1;
        int buildsQtt = 5;

        turnNodeOnIfOffline();

        TestUtils.createItem(TestUtils.PIPELINE, PIPELINE_NAME, this);
        clickConfigButton();
        sendScript(number_of_stages);
        getDriver().findElement(By.name("Submit")).click();

        WebElement buildButton = getDriver().findElement(
                By.xpath("//a[@href='/job/" + PIPELINE_NAME + "/build?delay=0sec']"));

        for (int i = 1; i <= buildsQtt; i++) {
            buildButton.click();
            getWait10().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.xpath("//span[@class='badge']/a[@href='" + i + "']")));
        }

        List<WebElement> buildTable = getWait2().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.className("badge")));

        List<String> actualOrder = TestUtils.getTexts(buildTable);

        List<String> expectedOrder = new ArrayList<>(actualOrder);
        expectedOrder.sort(Collections.reverseOrder());

        Assert.assertEquals(actualOrder, expectedOrder);
    }

    @Test
    public void testBuildColorGreen() {

        int number_of_stages = 1;

        turnNodeOnIfOffline();

        createPipelineProject(PIPELINE_NAME);
        sendScript(number_of_stages);

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

    @Ignore
    @Test
    public void testFullStageViewPopUpWindowIsDisplayed() {
        int number_of_stages = 2;
        TestUtils.createJob(this, TestUtils.Job.PIPELINE, PIPELINE_NAME);

        sendScript(number_of_stages);

        getDriver().findElement(By.name("Submit")).click();
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@data-build-success='Build scheduled']"))).click();
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='table-box']")));
        getDriver().findElement(By.xpath("//tr[@data-runid='1']//td[@data-stageid='6']")).click();
        getDriver().findElement(By.xpath("//div[@class='btn btn-small cbwf-widget cbwf-controller-applied stage-logs']")).click();

        String actualResult = getDriver().findElement(By.xpath("//div[@class='cbwf-dialog cbwf-stage-logs-dialog']")).getText();

        Assert.assertTrue(actualResult.contains("Stage Logs (stage 1)"));
    }

    @Ignore
    @Test
    public void testStageColumnHeader() {

        int number_of_stages = 2;

        TestUtils.createItem(TestUtils.PIPELINE, PIPELINE_NAME, this);
        clickConfigButton();
        sendScript(number_of_stages);
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/" + PIPELINE_NAME + "/build?delay=0sec']")).click();

        for (int i = 1; i <= number_of_stages; i++) {
            String actualResult = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("th[class='stage-header-name-" + (i - 1) + "']"))).getText();
            String expectedResult = "stage " + i;

            Assert.assertEquals(actualResult, expectedResult);
        }
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

        TestUtils.createJob(this, TestUtils.Job.PIPELINE, "Pipeline3");

        getDriver().findElement(By.name("description")).sendKeys("Pipeline description");
        getDriver().findElement(By.cssSelector(".textarea-show-preview")).click();
        getDriver().findElement(By.cssSelector(".textarea-hide-preview")).click();

        Assert.assertFalse(getDriver().findElement(By.cssSelector(".textarea-preview")).isDisplayed());
    }

    @Test
    public void testVerifyNewPPCreatedByCreateJob() {

        getDriver().findElement(By.cssSelector("a[href='newJob']")).click();
        getDriver().findElement(By.cssSelector("div.add-item-name > input#name")).sendKeys(nameProjects.get(0));
        getDriver().findElement(By.cssSelector(".org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        getDriver().findElement(By.cssSelector("button#ok-button")).click();

        getDriver().findElement(By.cssSelector("button.jenkins-button--primary")).click();

        getDriver().findElement(By.cssSelector("li.jenkins-breadcrumbs__list-item > a[href='/']")).click();

        Assert.assertTrue(getDriver().findElement(By.cssSelector("tr#job_" + nameProjects.get(0))).isDisplayed());

    }

    @Test(dependsOnMethods = "testVerifyNewPPCreatedByCreateJob")
    public void testVerifyNewPPCreatedNewItem() {

        TestUtils.createNewItem(this, nameProjects.get(1), TestUtils.Item.PIPELINE);

        for (String nameProject : nameProjects) {
            Assert.assertTrue(getDriver().findElement(By.cssSelector("tr#job_" + nameProject)).isDisplayed());
        }
    }

    @Test
    public void testFullStageViewDropDownMenu() {
        createNewPipeline(PIPELINE_NAME);
        goHomePage();

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

    @Test(dependsOnMethods = "testCreatePipeline")
    public void testRunByBuildNowButton() {

        String consoleOutput = new HomePage(getDriver())
                .clickJobByName(PIPELINE_NAME,
                        new PipelineProjectPage(getDriver()))
                .clickBuild()
                .waitForBuildScheduledPopUp()
                .clickLogo()
                .clickBuildHistory()
                .clickBuild1Console(1)
                .getConsoleOutputMessage();

        Assert.assertTrue(consoleOutput.contains(SUCCEED_BUILD_EXPECTED));
    }

    @Test(dependsOnMethods = "testCreatePipeline")
    public void testRunBuildByTriangleButton() {

        String consoleOutput = new HomePage(getDriver())
                .scheduleBuildForItem(PIPELINE_NAME)
                .waitForBuildSchedulePopUp()
                .clickBuildHistory()
                .clickBuild1Console(1)
                .getConsoleOutputMessage();

        Assert.assertTrue(consoleOutput.contains(SUCCEED_BUILD_EXPECTED));
    }

    @Test(dependsOnMethods = "testRunBuildByTriangleButton")
    public void testPermalinksBuildDetails() {
        final List<String> expectedPermalinkList =
                List.of("Last build (#1)", "Last stable build (#1)", "Last successful build (#1)", "Last completed build (#1)");

        List<String> actualPermalinkList = new HomePage(getDriver())
                .clickJobByName(PIPELINE_NAME, new PipelineProjectPage(getDriver()))
                .getPermalinkList();

        Assert.assertEquals(actualPermalinkList, expectedPermalinkList);
    }

    @Test(dependsOnMethods = "testPermalinksBuildDetails")
    public void testGreenBuildSuccessColor() {
        final String greenHexColor = "#1ea64b";

        String actualHexColor = new HomePage(getDriver())
                .clickJobByName(PIPELINE_NAME, new PipelineProjectPage(getDriver()))
                .getHexColorSuccessMark();

        Assert.assertEquals(actualHexColor, greenHexColor);
    }


    @Test
    public void testBreadcrumbTrailsContainsPipelineName() {

        TestUtils.createJob(this, TestUtils.Job.PIPELINE, "Pipeline project");

        List<WebElement> breadcrumbBarElements = List.of(
                getDriver().findElement(By.xpath("//*[@id='breadcrumbs']/li[1]")),
                getDriver().findElement(By.xpath("//*[@id='breadcrumbs']/li[2]")),
                getDriver().findElement(By.xpath("//*[@id='breadcrumbs']/li[3]/a")),
                getDriver().findElement(By.xpath("//*[@id='breadcrumbs']/li[4]")));

        for (WebElement element : breadcrumbBarElements) {
            Assert.assertTrue(element.isDisplayed(), "Pipeline project");
        }
    }

    @Test(dependsOnMethods = "testCreatePipeline")
    public void testUseSearchToFindProject() {

        getDriver().findElement(By.xpath("//*[@id='search-box']")).sendKeys(PIPELINE_NAME);
        getDriver().findElement(By.xpath("//*[@id='search-box']")).sendKeys(Keys.ENTER);

        Assert.assertEquals(getDriver().findElement(
                By.xpath("//*[@id='main-panel']/div[1]/div/h1")).getText(), PIPELINE_NAME);
    }

    @Ignore
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

    @Ignore
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

    @Ignore
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

    @Ignore
    @Test
    public void testDisablePipelineProject() {
        CreatePipelineProject();
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
    public void testDeletePipelineSideMenu() {
        TestUtils.createJob(this, TestUtils.Job.PIPELINE, PIPELINE_NAME);

        TestUtils.goToMainPage(getDriver());
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table//a[@href='job/"
                + PIPELINE_NAME + "/']"))).click();
        getDriver().findElement(By.xpath("//a[@data-title='Delete Pipeline']")).click();
        getDriver().findElement(By.xpath("//button[@data-id='ok']")).click();

        List<WebElement> jobList = getDriver()
                .findElements(By.xpath("//table//a[@href='job/" + PIPELINE_NAME + "/']"));

        Assert.assertTrue(jobList.isEmpty());
    }

    @Ignore
    @Test
    public void testDeletePipelineDropdown() {
        TestUtils.createJob(this, TestUtils.Job.PIPELINE, PIPELINE_NAME);
        TestUtils.goToMainPage(getDriver());

        TestUtils.deleteJobViaDropdown(this, PIPELINE_NAME);

        List<WebElement> jobList = getDriver()
                .findElements(By.xpath("//table//a[@href='job/" + PIPELINE_NAME + "/']"));

        Assert.assertTrue(jobList.isEmpty());
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
    public void testAddDescriptionInConfigureMenu() {
        final String pipelineDescription = "This description was added for testing purposes";

        createPipeline();

        boolean isDescriptionVisible = new PipelineConfigPage(getDriver())
                .addDescription(pipelineDescription)
                .clickSaveButton()
                .isDescriptionVisible(pipelineDescription);

        Assert.assertTrue(isDescriptionVisible, "Something went wrong with the description");
    }

    @Test
    public void testDisableProjectInConfigureMenu() {
        final String expectedMessageForDisabledProject = "This project is currently disabled";

        createPipeline();

        String warningMessageText = new PipelineConfigPage(getDriver())
                .clickToggleSwitchEnableDisable()
                .clickSaveButton()
                .getWarningMessageText();

        Assert.assertTrue(warningMessageText.contains(expectedMessageForDisabledProject));
    }

    @Test(dependsOnMethods = "testDisableProjectInConfigureMenu")
    public void testEnableProjectInConfigureMenu() {

        navigateToConfigurePageFromDashboard();

        getDriver().findElement(TOGGLE_SWITCH_ENABLE_DISABLE).click();
        getDriver().findElement(SAVE_BUTTON_CONFIGURATION).click();

        Assert.assertTrue(
                getDriver().findElement(By.xpath("//a[@data-build-success='Build scheduled']")).isDisplayed());
    }

    @Test
    public void testDiscardOldBuildsByCount() {

        PipelineProjectPage pipelineProjectPage = new HomePage(getDriver())
                .clickCreateAJob()
                .setItemName(PIPELINE_NAME)
                .selectProjectTypeAndClickOk(TestUtils.ProjectType.PIPELINE, new PipelineConfigPage(getDriver()))
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
    public void testSectionsOfSidePanelAreVisible() {
        createPipeline();

        navigateToConfigurePageFromDashboard();

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(text(), 'Configure')]")));

        List<WebElement> sections = List.of(
                getDriver().findElement(
                        By.xpath("//div[@id='side-panel']//descendant::button[contains(@data-section-id, 'general')]")),
                getDriver().findElement(
                        By.xpath("//div[@id='side-panel']//descendant::button[contains(@data-section-id, 'advanced-project-options')]")),
                getDriver().findElement(
                        By.xpath("//div[@id='side-panel']//descendant::button[contains(@data-section-id, 'pipeline')]")));

        for (WebElement section : sections) {
            Assert.assertTrue(section.isDisplayed(),
                    "The requested section is not found in Configure side-panel");
        }
    }

    @Test
    public void testAddDisplayNameInAdvancedSection() {
        final String displayNameText = "This is project's Display name text for Advanced Project Options";

        createPipeline();

        navigateToConfigurePageFromDashboard();

        getWait5().until(ExpectedConditions.elementToBeClickable(ADVANCED_PROJECT_OPTIONS_MENU)).click();

        clickOnAdvancedButton();
        getWait10().until(ExpectedConditions.elementToBeClickable(DISPLAY_NAME_TEXT_FIELD)).sendKeys(displayNameText);
        getDriver().findElement(SAVE_BUTTON_CONFIGURATION).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//h1[@class='job-index-headline page-headline']")).getText(),
                displayNameText);
    }

    @Test(dependsOnMethods = "testAddDisplayNameInAdvancedSection")
    public void testEditDisplayNameInAdvancedSection() {
        final String editedDisplayNameText = " - EDITED";

        navigateToConfigurePageFromDashboard();

        getWait5().until(ExpectedConditions.elementToBeClickable(ADVANCED_PROJECT_OPTIONS_MENU)).click();

        clickOnAdvancedButton();
        getWait10().until(ExpectedConditions.elementToBeClickable(DISPLAY_NAME_TEXT_FIELD));
        getDriver().findElement(DISPLAY_NAME_TEXT_FIELD).sendKeys(editedDisplayNameText);
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(SAVE_BUTTON_CONFIGURATION));
        getDriver().findElement(SAVE_BUTTON_CONFIGURATION).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//h1[@class='job-index-headline page-headline']"))
                .getText().contains(editedDisplayNameText), "Your DisplayName is not edited correctly");
    }

    @Test
    public void testVerifySectionHasTooltip() {
        String labelText = "Display Name";
        String tooltipText = "Help for feature: Display Name";
        createPipeline();

        navigateToConfigurePageFromDashboard();

        getWait5().until(ExpectedConditions.elementToBeClickable(ADVANCED_PROJECT_OPTIONS_MENU)).click();
        clickOnAdvancedButton();

        String actualTooltip = getDriver().findElement(By.xpath("//*[contains(text(), '" + labelText + "')]//a")).getAttribute("tooltip");

        Assert.assertEquals(actualTooltip, tooltipText);
    }

    @Ignore
    @Test
    public void testChoosePipelineScript() {
        createPipeline();

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-section-id='pipeline']"))).click();

        WebElement selectScript = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class = 'samples']//select")));
        Select simpleDropDown = new Select(selectScript);
        simpleDropDown.selectByValue("github-maven");

        WebElement uncheckCheckBox = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[text()='Use Groovy Sandbox']")));
        uncheckCheckBox.click();

        WebElement link = getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@target='blank']")));
        Assert.assertTrue(link.isDisplayed(), "Uncheck doesn't work");
    }

    @Test(dependsOnMethods = {"testAddDisplayNameInAdvancedSection", "testEditDisplayNameInAdvancedSection"})
    public void testDeleteDisplayNameInAdvancedSection() {
        navigateToConfigurePageFromDashboard();

        getWait5().until(ExpectedConditions.elementToBeClickable(ADVANCED_PROJECT_OPTIONS_MENU)).click();

        clickOnAdvancedButton();
        getWait10().until(ExpectedConditions.elementToBeClickable(DISPLAY_NAME_TEXT_FIELD)).clear();
        getDriver().findElement(SAVE_BUTTON_CONFIGURATION).click();

        Assert.assertFalse(getDriver().findElement(By.xpath("//h1[@class='job-index-headline page-headline']"))
                .getText().contains(displayNameText));
    }

    @Test
    public void testSetQuietPeriodBuildTriggers() {
        final int numberOfSeconds = 3;

        createPipeline();
        navigateToConfigurePageFromDashboard();

        scrollCheckBoxQuietPeriodIsVisible();
        WebElement checkBoxQuietPeriod = getDriver().findElement(By.xpath("//label[text()='Quiet period']"));
        checkBoxQuietPeriod.click();

        WebElement inputField = getDriver().findElement(By.name("quiet_period"));
        inputField.clear();
        inputField.sendKeys("" + numberOfSeconds + "");
        getDriver().findElement(SAVE_BUTTON_CONFIGURATION).click();

        navigateToConfigurePageFromDashboard();
        scrollCheckBoxQuietPeriodIsVisible();

        Assert.assertTrue(getDriver().findElement(By.xpath("//input[@name='quiet_period']"))
                        .getAttribute("value").contains("" + numberOfSeconds + ""),
                "The actual numberOfSeconds differs from expected result");
    }

    @Test
    public void testVerifySectionsHaveTooltips() {
        String[] labelsText = {"Display Name", "Script"};

        createPipeline();
        navigateToConfigurePageFromDashboard();

        getWait5().until(ExpectedConditions.elementToBeClickable(ADVANCED_PROJECT_OPTIONS_MENU)).click();
        clickOnAdvancedButton();

        for (String label : labelsText) {
            String actualTooltip = getDriver().findElement(By.xpath("//*[contains(text(), '" + label + "')]//a")).getAttribute("tooltip");

            Assert.assertEquals(actualTooltip, "Help for feature: " + label);
        }
    }

    @Test
    public void testSetQuietPeriodBuildTriggersLessThanZero() {
        final int numberOfSeconds = -5;
        final String errorMessage = "This value should be larger than 0";

        createPipeline();
        navigateToConfigurePageFromDashboard();

        scrollCheckBoxQuietPeriodIsVisible();
        WebElement checkBoxQuietPeriod = getDriver().findElement(By.xpath("//label[text()='Quiet period']"));
        checkBoxQuietPeriod.click();

        WebElement inputField = getDriver().findElement(By.name("quiet_period"));
        inputField.clear();
        inputField.sendKeys("" + numberOfSeconds + "");
        getDriver().findElement(By.xpath("//div[text()='Number of seconds']")).click();

        WebElement errorElement = getDriver().findElement(By.xpath("//div[@class='form-container tr']//div[@class='error']"));
        getWait5().until(ExpectedConditions.visibilityOf(errorElement));

        Assert.assertEquals(errorElement.getText(), errorMessage);
    }

    @Test
    public void testSetDoubleQuietPeriodBuildTriggersLessThanZero() {
        final double numberOfSeconds = 0.3;
        final String errorMessage = "Not an integer";

        createPipeline();
        navigateToConfigurePageFromDashboard();

        scrollCheckBoxQuietPeriodIsVisible();
        WebElement checkBoxQuietPeriod = getDriver().findElement(By.xpath("//label[text()='Quiet period']"));
        checkBoxQuietPeriod.click();

        WebElement inputField = getDriver().findElement(By.name("quiet_period"));
        inputField.clear();
        inputField.sendKeys("" + numberOfSeconds + "");
        getDriver().findElement(By.xpath("//div[text()='Number of seconds']")).click();

        WebElement errorElement = getDriver().findElement(By.xpath("//div[@class='form-container tr']//div[@class='error']"));
        getWait5().until(ExpectedConditions.visibilityOf(errorElement));

        Assert.assertEquals(errorElement.getText(), errorMessage);
    }

    @Test
    public void testSetPipelineSpeedDurabilityOverride() {
        final String selectedOptionForCheck = "Less durability, a bit faster (specialty use only)";
        createPipeline();
        navigateToConfigurePageFromDashboard();

        getDriver().findElement(By.xpath("//label[text()='Pipeline speed/durability override']")).click();
        WebElement selectCustomPipelineSpeedDurabilityLevel = getDriver().findElement(By.xpath("//select[@class='setting-input']"));
        Select dropDown = new Select(selectCustomPipelineSpeedDurabilityLevel);
        dropDown.selectByIndex(1);
        String selectedValue = selectCustomPipelineSpeedDurabilityLevel.getText();

        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].scrollIntoView();",
                getDriver().findElement(SAVE_BUTTON_CONFIGURATION));
        getDriver().findElement(SAVE_BUTTON_CONFIGURATION).click();

        navigateToConfigurePageFromDashboard();

        Assert.assertTrue(selectedValue.contains(selectedOptionForCheck));
    }

    @Test
    public void testSetNumberOfBuildsThrottleBuilds() {
        final String messageDay = "Approximately 24 hours between builds";

        createPipeline();
        navigateToConfigurePageFromDashboard();
        scrollCheckBoxThrottleBuildsIsVisible();

        getDriver().findElement(By.xpath("//label[text()='Throttle builds']")).click();
        WebElement selectThrottleBuilds = getDriver().findElement(By.xpath("//select[@class='jenkins-select__input select']"));
        Select simpleDropDown = new Select(selectThrottleBuilds);
        simpleDropDown.selectByValue("day");

        WebElement dayElement = getDriver().findElement(By.xpath("//div[@class='ok']"));
        getWait5().until(ExpectedConditions.visibilityOf(dayElement));

        Assert.assertEquals(dayElement.getText(), messageDay);
    }
}

package school.redrover;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import school.redrover.model.DeleteDialog;
import school.redrover.model.HomePage;
import school.redrover.model.MultibranchPipelineConfigPage;
import school.redrover.model.MultibranchPipelineStatusPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;
import static school.redrover.runner.TestUtils.Job;

public class MultibranchPipelineTest extends BaseTest {

    private final static String MULTI_PIPELINE_NAME = "MultibranchPipeline";
    private final static String RENAMED_MULTI_PIPELINE = "NewMultibranchPipelineName";
    private final static String NESTED_TESTS_FOLDER_NAME = "NestedTestsFolder";
    private static final By SEARCH_RESULT_DROPDOWN = By.className("yui-ac-bd");
    private static final String FOLDER_NAME = "Folder";
    private final static List <String> PIPELINE_MENU =
            List.of("Status", "Configure", "Scan Multibranch Pipeline Log", "Multibranch Pipeline Events",
                    "Delete Multibranch Pipeline", "People", "Build History", "Rename", "Pipeline Syntax", "Credentials");
    private static final String WELCOME_PAGE_HEADING ="Welcome to Jenkins!";
    private static final By MULTI_PIPELINE_ON_DASHBOARD_LOCATOR = By.cssSelector("[href='job/" + RENAMED_MULTI_PIPELINE + "/']");

    private void createNewMultiPipeline(String multiPipelineName) {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(multiPipelineName);
        getDriver().findElement(By.cssSelector("[class*='WorkflowMultiBranchProject']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.id("jenkins-home-link")).click();
    }

    private void createFolderForNestedTests() {
        getDriver().findElement(By.linkText("Create a job")).click();
        getDriver().findElement(By.id("name")).sendKeys(NESTED_TESTS_FOLDER_NAME);
        getDriver().findElement(By.cssSelector("[class$='_Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
    }

    private void createMultibranchViaFolderSidebar() {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(MULTI_PIPELINE_NAME);
        getDriver().findElement(By.cssSelector("[class*='MultiBranch']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
    }

    @Test
    public void testCreateMultibranchPipeline() {
        String getMultibranchPipelineName = new HomePage(getDriver())
                .clickCreateAJob()
                .setItemName(MULTI_PIPELINE_NAME)
                .selectMultibranchPipelineAndClickOk()
                .clickToggle()
                .clickSaveButton()
                .getProjectNameText();

        Assert.assertEquals(getMultibranchPipelineName,MULTI_PIPELINE_NAME);
    }

    @Test
    public void testCreateMultibranchPipelineWithEmptyName() {
        final String expectedErrorMessage = "» This field cannot be empty, please enter a valid name";

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.xpath("//div[@id='j-add-item-type-standalone-projects']/ul/li[3]"))
                   .click();
        WebElement okButton = getDriver().findElement(By.xpath("//button[@id='ok-button']"));
        WebElement actualErrorMessage = getDriver().findElement(By.xpath("//div[@id='itemname-required']"));

        Assert.assertFalse(okButton.isEnabled());
        Assert.assertTrue(actualErrorMessage.isDisplayed());
        Assert.assertEquals(actualErrorMessage.getText(),expectedErrorMessage);
    }

    @Test
    public void testRenameMultibranchPipeline() {
        String newNameMultibranchPipeline = new HomePage(getDriver())
                .clickCreateAJob()
                .setItemName(MULTI_PIPELINE_NAME)
                .selectMultibranchPipelineAndClickOk()
                .clickToggle()
                .clickSaveButton()
                .clickSidebarRenameButton()
                .changeNameTo(RENAMED_MULTI_PIPELINE)
                .getProjectNameText();

        Assert.assertEquals(newNameMultibranchPipeline, RENAMED_MULTI_PIPELINE);
    }

    @Test
    public void testChangeFromDisabledToEnabledOnStatusPage() {
        MultibranchPipelineStatusPage multibranchPipelineStatusPage = new HomePage(getDriver())
            .clickCreateAJob()
            .setItemName(MULTI_PIPELINE_NAME)
            .selectMultibranchPipelineAndClickOk()
            .clickToggle()
            .clickSaveButton()
            .clickDisableEnableMultibranchPipeline();

        Assert.assertTrue(multibranchPipelineStatusPage.isMultibranchPipelineDisabledTextNotDisplayed(),"Disabled message is displayed!!!");
    }

    @Test
    public void testVerifyStatusToSwitchingEnableMultibranchPipeline() {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();

        getDriver().findElement(By.className("jenkins-input")).sendKeys("Muiltibranch Pipeline project");

        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_multibranch_WorkflowMultiBranchProject")).click();

        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.cssSelector("#toggle-switch-enable-disable-project > label")).click();

        WebElement footer = getDriver().findElement(By.xpath("//*[@id='footer']"));
        int deltaY = footer.getRect().y;
        new Actions(getDriver()).scrollByAmount(0, deltaY).perform();

        getDriver().findElement(By.xpath("//*[@id='bottom-sticker']/div/button[1]")).click();

        getDriver().findElement(By.xpath("//*[@id='enable-project']/button")).click();

        String foundText = getDriver().findElement(By.xpath("//*[@id='disable-project']/button")).getText();
        Assert.assertEquals(foundText, "Disable Multibranch Pipeline");
    }


    @Test
    public void testDisabledTooltip() {
        final String tooltipText = "(No new builds within this Multibranch Pipeline will be executed until it is re-enabled)";

        MultibranchPipelineConfigPage multibranchPipelineConfigPage = new HomePage(getDriver())
            .clickCreateAJob()
            .setItemName(MULTI_PIPELINE_NAME)
            .selectMultibranchPipelineAndClickOk()
            .clickToggle()
            .hoverOverToggle();

       Assert.assertTrue(multibranchPipelineConfigPage.isTooltipDisplayed());
       Assert.assertEquals(multibranchPipelineConfigPage.getTooltipText(),tooltipText);
    }

    @Test
    public void testRenameMultibranchPipelineWithNameSameAsCurrent() {

        final String MULTIBRANCH_PIPELINE_NAME = "First Multibranch Pipeline project";
        final String expectedErrorMessage = "The new name is the same as the current name.";

        TestUtils.createJob(this, Job.MULTI_BRUNCH_PIPELINE, MULTIBRANCH_PIPELINE_NAME);

        getDriver().findElement(By.cssSelector("#breadcrumbs > li:nth-child(3")).click();
        getDriver().findElement(By.cssSelector("#tasks > div:nth-child(8) > span > a")).click();
        getDriver().findElement(By.xpath("//div[@id='bottom-sticker']//button")).click();

        WebElement actualErrorMessage = getDriver().findElement(By.xpath("//div[@id='main-panel']/p"));

        Assert.assertEquals(actualErrorMessage.getText(), expectedErrorMessage);
    }

    @Test
    public void testEnabledMultibranchPipelineOnConfigPage() {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("New Multibranch Pipeline");
        getDriver().findElement(By.cssSelector("[class*=MultiBranchProject]")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.cssSelector("[class*=toggle-switch__label]")).click();
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.cssSelector("[href*='Pipeline/configure']")).click();
        getDriver().findElement(By.cssSelector("[class*=toggle-switch__label]")).click();

        String statusToggle = getDriver().findElement(By.id("enable-disable-project")).getDomProperty("checked");
        Assert.assertEquals(statusToggle,"true");
    }

    @Test
    public void testRenameMultibranchPipelineOnTheSidebar() {
        createNewMultiPipeline(MULTI_PIPELINE_NAME);

        getDriver().findElement(By.xpath("//span[text()='" + MULTI_PIPELINE_NAME + "']")).click();
        getDriver().findElement(By.cssSelector("[href $='rename']")).click();
        WebElement renameInput = getDriver().findElement(By.xpath("//input[@name='newName']"));
        renameInput.clear();
        renameInput.sendKeys(RENAMED_MULTI_PIPELINE);
        getDriver().findElement(By.name("Submit")).click();
        String multiPipelinePageHeading = getDriver().findElement(By.tagName("h1")).getText();

        Assert.assertEquals(multiPipelinePageHeading, RENAMED_MULTI_PIPELINE, "Wrong name");
    }

    @Test
    public void testRenameMultibranchPipelineViaMainPageDropdownMenu() {

        String multibranchPipelineName = new HomePage(getDriver())
            .clickCreateAJob()
            .setItemName(MULTI_PIPELINE_NAME)
            .selectMultibranchPipelineAndClickOk()
            .clickLogo()
            .openItemDropdown(MULTI_PIPELINE_NAME)
            .clickRenameFromDropdownMP()
            .changeNameTo(RENAMED_MULTI_PIPELINE)
            .getMultibranchPipelineName();

        Assert.assertEquals(multibranchPipelineName,RENAMED_MULTI_PIPELINE, "чтото пошло не так");
    }

    @Test
    public void testRenamedMultibranchPipelineSeenInBreadcrumbs() {
        createNewMultiPipeline(MULTI_PIPELINE_NAME);

        getDriver().findElement(By.linkText(MULTI_PIPELINE_NAME)).click();
        getDriver().findElement(By.cssSelector("[href$='rename']")).click();
        getDriver().findElement(By.name("newName")).clear();
        getDriver().findElement(By.name("newName")).sendKeys(RENAMED_MULTI_PIPELINE);
        getDriver().findElement(By.name("Submit")).click();

        String multiPipelineBreadcrumbName = getDriver().findElement(By.cssSelector("[class*='breadcrumbs'] [href^='/job']")).getText();

        Assert.assertEquals(multiPipelineBreadcrumbName, RENAMED_MULTI_PIPELINE,
                "Actual multibranch breadcrumb name is not " + RENAMED_MULTI_PIPELINE);
    }

    @Test
    public void testCreatePipelineUsingCreateAJobOnHomePage(){
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("MyPipeline");
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        String namePipelineProject = getDriver()
                .findElement(By.xpath("//a[@href='job/MyPipeline/']/span")).getText();
        Assert.assertEquals(namePipelineProject, "MyPipeline");
    }

    @Test
    public void testMultibranchSidebarTasksUponCreatingViaFolder() {
        final List<String> sidebarTasks = List.of("Status", "Configure", "Scan Multibranch Pipeline Log",
                "Multibranch Pipeline Events", "Delete Multibranch Pipeline", "People", "Build History", "Move",
                "Rename", "Pipeline Syntax", "Credentials");

        createFolderForNestedTests();
        createMultibranchViaFolderSidebar();

        List<String> actualSidebarTexts = TestUtils.getTexts(
                getDriver().findElements(By.cssSelector("[class^='task-link-wrapper']")));

        Assert.assertEquals(actualSidebarTexts.size(), 11);
        Assert.assertEquals(actualSidebarTexts, sidebarTasks);
    }

    @Test
    public void testVerifyPipelineSidebarMenu() {
        createNewMultiPipeline(MULTI_PIPELINE_NAME);

        getDriver().findElement(By.xpath("//span[text() = '"+ MULTI_PIPELINE_NAME + "']")).click();
        List<String> pipelineSideMenu = TestUtils.getTexts(getDriver()
                .findElements(By.xpath("//div[@class = 'task ']")));

        Assert.assertEquals(pipelineSideMenu.size(), 10);
        Assert.assertEquals( pipelineSideMenu, PIPELINE_MENU);
    }

    @Test
    public void testMoveInFolderViaSidebarMenu() {
        TestUtils.createNewItemAndReturnToDashboard(this, FOLDER_NAME, TestUtils.Item.FOLDER);
        TestUtils.createNewItemAndReturnToDashboard(this, MULTI_PIPELINE_NAME, TestUtils.Item.MULTI_BRANCH_PIPELINE);

        getDriver().findElement(By.xpath("//span[text()='" + MULTI_PIPELINE_NAME + "']")).click();
        getDriver().findElement(By.cssSelector("[href $='move']")).click();
        new Select(getDriver().findElement(By.name("destination"))).selectByValue("/" + FOLDER_NAME);
        getDriver().findElement(By.name("Submit")).click();

        WebElement searchInput = getDriver().findElement(By.cssSelector("input[role='searchbox']"));
        searchInput.sendKeys(MULTI_PIPELINE_NAME);

        getWait2().until(ExpectedConditions.visibilityOfElementLocated(SEARCH_RESULT_DROPDOWN));
        String actualFolderNameInSearch = getDriver().findElements(SEARCH_RESULT_DROPDOWN)
            .stream()
            .map(WebElement::getText)
            .toList().toString()
            .replace("[", "")
            .replace("]", "")
            .replace(MULTI_PIPELINE_NAME, "")
            .trim();

        Assert.assertEquals(actualFolderNameInSearch, FOLDER_NAME, MULTI_PIPELINE_NAME + "находится в другой папке");
    }

    @Test
    public void testDeleteViaDashboardDropdown(){
        String actualPageHeading = new HomePage(getDriver())
            .clickCreateAJob()
            .setItemName(MULTI_PIPELINE_NAME)
            .selectMultibranchPipelineAndClickOk()
            .clickLogo()
            .openItemDropdown(MULTI_PIPELINE_NAME)
            .clickDeleteInDropdown(new DeleteDialog(getDriver()))
            .clickYes(new HomePage(getDriver()))
            .getHeadingValue();

        Assert.assertEquals(actualPageHeading, WELCOME_PAGE_HEADING);
    }

    @Test
    public void testEnableMultibranchPipeline() {
        MultibranchPipelineConfigPage page = new HomePage(getDriver()).clickCreateAJob()
                .setItemName("TextName")
                .selectMultibranchPipelineAndClickOk()
                .clickOnToggle()
                .clickSaveButton()
                .selectConfigure()
                .clickOnToggle()
                .clickSaveButton()
                .selectConfigure();

        Assert.assertEquals(page.getStatusToggle(), "true");
    }

    @Test
    public void testDisabledMultibranchPipeline() {
        MultibranchPipelineConfigPage page = new HomePage(getDriver()).clickCreateAJob()
                .setItemName("TextName1")
                .selectMultibranchPipelineAndClickOk()
                .clickOnToggle()
                .clickSaveButton()
                .selectConfigure();

        Assert.assertEquals(page.getStatusToggle(), "false");
    }

    @Test
    public void testCreateMultibranchPipelineViaNewItem(){
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.cssSelector("#name")).sendKeys("First Multibranch Pipeline");
        getDriver().findElement(By.xpath("//span[(.='Multibranch Pipeline')]")).click();
        getDriver().findElement(By.cssSelector("#ok-button")).click();
        getDriver().findElement(By.xpath("//button[@formnovalidate='formNoValidate']")).click();

        String title = getDriver().findElement(By.xpath("//h1")).getText();
        Assert.assertEquals(title,"First Multibranch Pipeline");
    }

    @Test
    public void testCreateMultibranchPipeline2(){
        getDriver().findElement(By.xpath("//*[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("First Multibranch pipeline");
        getDriver().findElement(By.xpath("//*[contains(@class,'MultiBranch')]")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
    }
    @Test(dependsOnMethods = "testCreateMultibranchPipeline2")
    public void testRenamingMultibranchPipeline() {
        getDriver().findElement(By.xpath
            ("//*[@href='job/First%20Multibranch%20pipeline/']/span")).click();
        getDriver().findElement(By.xpath
            ("//*[contains(@href,'confirm-rename')]")).click();
        getDriver().findElement(By.xpath("//*[@name='newName']")).clear();
        getDriver().findElement(By.xpath("//*[@name='newName']"))
            .sendKeys("New project");
        getDriver().findElement(By.xpath("//*[@name='Submit']")).click();
        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), "New project");
    }

    @Test
    public void testCreateMultibranchPipeline3(){
        getDriver().findElement(By.xpath("//*[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("New Multibranch pipeline");
        getDriver().findElement(By.xpath("//*[contains(@class,'MultiBranch')]")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.xpath("//*[@name='Submit']")).click();
    }

    @Test(dependsOnMethods = "testCreateMultibranchPipeline3")
    public void testEnableMultibranchPipeline2(){
        getDriver().findElement(By.xpath
            ("//*[@href='job/New%20Multibranch%20pipeline/']/span")).click();
        getDriver().findElement(By.xpath("//*[@name='Submit']")).click();
        Assert.assertNotNull(getDriver().findElements(By.xpath(
            "//*[contains(@src, 'pipelinemultibranchproject.svg')]")));
    }

    @Test
    public void testNewMultibranchPipelineIsEmpty() {
        final String multibranchPipelineName = "FidelityNewPipeline";
        final String thisFolderIsEmptyMessage = "This folder is empty";

        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.name("name")).sendKeys(multibranchPipelineName);
        getDriver().findElement(By.xpath("//label/span[text() ='Multibranch Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        TestUtils.goToMainPage(getDriver());

        getDriver().findElement(By.linkText(multibranchPipelineName)).click();

        final String actualMultibranchPipelineName = getDriver().findElement(By.xpath("//h1")).getText();
        final String actualEmptyStateMessage = getDriver().findElement(By.xpath("//section[@class='empty-state-section']/h2")).getText();

        Assert.assertEquals(actualMultibranchPipelineName, multibranchPipelineName);
        Assert.assertEquals(actualEmptyStateMessage, thisFolderIsEmptyMessage);
    }

    @Test
    public void testCreate() {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input']"))
            .sendKeys(MULTI_PIPELINE_NAME);
        getDriver().findElement(By.cssSelector("[class*='WorkflowMultiBranchProject']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
        WebElement actualMultibranchPipelineName = getDriver().findElement(By.xpath("//div[@id='breadcrumbBar']//li[3]"));

        Assert.assertEquals(actualMultibranchPipelineName.getText(), MULTI_PIPELINE_NAME);
        getDriver().findElement(By.id("jenkins-head-icon")).click();
    }

    @Test(dependsOnMethods = "testVerifyMpDisabledMessageColorOnStatusPage")
    public void testChangeFromDisableOnStatusPage() {
        getDriver().findElement(By.xpath("//span[text()='" + MULTI_PIPELINE_NAME + "']")).click();
        WebElement configureLink = getDriver().findElement(By.cssSelector(".task-link-wrapper [href$='configure']"));
        configureLink.click();
        if (getDriver().findElement(By.className("jenkins-toggle-switch__label__checked-title"))
            .isDisplayed()) {
            getDriver().findElement(By.cssSelector("[data-title*='Disabled']")).click();
        }
        getDriver().findElement(By.cssSelector("[name*='Submit']")).click();
        getDriver().findElement(By.id("jenkins-home-link")).click();

        getDriver().findElement(By.xpath("//span[text()='" + MULTI_PIPELINE_NAME + "']")).click();
        getDriver().findElement(By.xpath("//button[contains(., 'Enable')]")).click();
        List<WebElement> disabledMultiPipelineMessage = getDriver().findElements(
            By.xpath("//form[contains(., 'This Multibranch Pipeline is currently disabled')]"));

        Assert.assertEquals(disabledMultiPipelineMessage.size(), 0, "Disabled message is displayed!!!");
    }

    @Test(dependsOnMethods = "testChangeFromDisableOnStatusPage")
    public void testRenameOnTheSidebar() {
        getDriver().findElement(By.xpath("//span[text()='" + MULTI_PIPELINE_NAME + "']")).click();
        getDriver().findElement(By.cssSelector("[href $='rename']")).click();
        WebElement renameInput = getDriver().findElement(By.xpath("//input[@name='newName']"));
        renameInput.clear();
        renameInput.sendKeys(RENAMED_MULTI_PIPELINE);
        getDriver().findElement(By.name("Submit")).click();
        String multiPipelinePageHeading = getDriver().findElement(By.tagName("h1")).getText();

        Assert.assertEquals(multiPipelinePageHeading, RENAMED_MULTI_PIPELINE, "Wrong name");
    }

    @Test(dependsOnMethods = "testCreate")
    public void testVerifyMpDisabledOnStatusPage() {
        String disabledMessage = new HomePage(getDriver())
            .clickMPName(MULTI_PIPELINE_NAME)
            .clickDisableEnableMultibranchPipeline()
            .getDisableMultibranchPipelineText();

        Assert.assertEquals(disabledMessage, "This Multibranch Pipeline is currently disabled");
    }

    @Test(dependsOnMethods = "testVerifyMpDisabledOnStatusPage")
    public void testVerifyMpDisabledMessageColorOnStatusPage() {
        String disabledMessageColor = new HomePage(getDriver())
            .clickMPName(MULTI_PIPELINE_NAME)
            .getDisableMultibranchPipelineTextColor();

        Assert.assertEquals(disabledMessageColor, "rgba(254, 130, 10, 1)");
    }

    @Test(dependsOnMethods = "testRenameOnTheSidebar")
    public void testDeleteMpViaBreadcrumbs() {
        getDriver().findElement(MULTI_PIPELINE_ON_DASHBOARD_LOCATOR).click();

        WebElement dropdownArrow = getDriver().findElement(By.cssSelector("a[href^='/job'] > button"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].dispatchEvent(new Event('mouseenter'));" +
            "arguments[0].dispatchEvent(new Event('click'));", dropdownArrow);

        getDriver().findElement(By.cssSelector("[class*='dropdown'] [href$='doDelete']")).click();
        getDriver().findElement(By.cssSelector("[data-id='ok']")).click();

        List<WebElement> projectList = getDriver().findElements(MULTI_PIPELINE_ON_DASHBOARD_LOCATOR);
        Assert.assertTrue(projectList.isEmpty());
    }

    @Ignore
    @Test
    public void testCreate2() {

        final String MULTIBRANCH_NAME = "Vika Multibranch Pipeline";

        getDriver().findElement(By.xpath("//span[contains(text(),'Create')]")).click();
        getDriver().findElement(By.id("name")).sendKeys(MULTIBRANCH_NAME);
        getDriver().findElement(By.xpath("//li[contains(@class,'WorkflowMultiBranchProject')]")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//h1[contains(text(),MULTIBRANCH_NAME)]")).isDisplayed(), MULTIBRANCH_NAME);
    }

    @Test
    public void testCreatingMultibranchPipeline(){
        String multibranchPipelinePage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(MULTI_PIPELINE_NAME)
                .selectMultibranchPipelineAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .getMultibranchPipelineNameText();

        Assert.assertEquals(multibranchPipelinePage,MULTI_PIPELINE_NAME);
    }

    @Test(dependsOnMethods = "testCreatingMultibranchPipeline")
    public void testMultibranchPipelineEnable(){
        String buttonName = new HomePage(getDriver())
                .clickJobByName(MULTI_PIPELINE_NAME,
                        new MultibranchPipelineStatusPage(getDriver()))
                .getDisableMultibranchPipelineButtonText();

        Assert.assertEquals(buttonName, "Disable Multibranch Pipeline");

    }

}

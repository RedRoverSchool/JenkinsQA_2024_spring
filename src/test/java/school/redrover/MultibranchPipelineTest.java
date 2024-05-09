package school.redrover;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
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

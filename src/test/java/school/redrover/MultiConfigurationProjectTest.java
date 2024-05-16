package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.model.*;

import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;
import java.util.Random;

public class MultiConfigurationProjectTest extends BaseTest {

    private static final String PROJECT_NAME = "MCProject";
    private final String RANDOM_PROJECT_NAME = TestUtils.randomString();

    private String generateRandomNumber(){
        Random r = new Random();
        int randomNumber = r.nextInt(100) + 1;
        return String.valueOf(randomNumber);
    }

    @Test
    public void testRenameProjectViaMainPageDropdown() {
        String addToProjectName = "New";
        TestUtils.createMultiConfigurationProject(this, PROJECT_NAME);

        String newProjectName = new HomePage(getDriver())
                .openItemDropdownWithSelenium(PROJECT_NAME)
                .selectRenameFromDropdown()
                .changeProjectNameWithoutClear(addToProjectName)
                .clickRenameButton()
                .getProjectName();

        Assert.assertEquals(newProjectName,
                "Project " + PROJECT_NAME + "New",
                "Project name has not been changed");
    }

    @Test
    public void testAddDescription() {
        final String TEXT = "❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️";

        String description = TestUtils.createMultiConfigurationProject(this, RANDOM_PROJECT_NAME)
                .clickMCPName(RANDOM_PROJECT_NAME)
                .clickAddDescriptionButton()
                .addOrEditDescription(TEXT)
                .clickSaveDescription()
                .getDescriptionText();

        Assert.assertEquals(description, TEXT);
    }

    @Test
    public void testEditDescriptionWithoutDelete() {
        final String text = "qwerty123";
        final String additionalText = "AAA";

        TestUtils.createNewItem(this, PROJECT_NAME, TestUtils.Item.MULTI_CONFIGURATION_PROJECT);

        String DescriptionText = new HomePage(getDriver())
                .clickMCPName(PROJECT_NAME)
                .clickAddDescriptionButton()
                .addOrEditDescription(text)
                .clickSaveDescription()
                .clickLogo()
                .clickMCPName(PROJECT_NAME)
                .clickAddDescriptionButton()
                .addOrEditDescription(additionalText)
                .clickSaveDescription()
                .getDescriptionText();

        Assert.assertEquals(DescriptionText, additionalText + text);
    }

    @Test
    public void testDescriptionPreview() {
        final String text = "I want to see preview";

        String previewText =
                TestUtils.createNewItem(this, PROJECT_NAME, TestUtils.Item.MULTI_CONFIGURATION_PROJECT)
                .clickMCPName(PROJECT_NAME)
                .clickAddDescriptionButton()
                .addOrEditDescription(text)
                .clickPreview()
                .getPreviewText();

        Assert.assertEquals(previewText, text);
    }

    @Test
    public void testMakeCopyMultiConfigurationProject() {
        final String newProjectName = "MCProject copy";
        List<String> projectList = TestUtils.createNewItem(this, PROJECT_NAME, TestUtils.Item.MULTI_CONFIGURATION_PROJECT)
                .clickNewItem()
                .setItemName(newProjectName)
                .setItemNameInCopyForm(PROJECT_NAME)
                .clickOkAnyway(new MultibranchPipelineConfigPage(getDriver()))
                .clickSaveButton()
                .clickLogo()
                .getItemList();

        Assert.assertTrue(projectList.contains(newProjectName));
    }

    @Test
    public void testDeleteProjectDescription() {
        final String DESCRIPTION_TEXT = "This is project description";
        TestUtils.createNewItem(this, PROJECT_NAME, TestUtils.Item.MULTI_CONFIGURATION_PROJECT);

        MultiConfigurationProjectPage multiConfigurationProjectPage = new MultiConfigurationProjectPage(getDriver());

        boolean isDescriptionDeleted = new HomePage(getDriver())
                .clickJobByName(PROJECT_NAME, multiConfigurationProjectPage)
                .clickAddDescriptionButton()
                .addOrEditDescription(DESCRIPTION_TEXT)
                .clickSaveDescription()
                .clickLogo()
                .clickJobByName(PROJECT_NAME, multiConfigurationProjectPage)
                .clickAddDescriptionButton()
                .clearDescription()
                .clickSaveDescription()
                .isDescriptionEmpty();

        Assert.assertTrue(isDescriptionDeleted);
    }

    @Test
    public void testMCPDisableByToggle() {
        Assert.assertFalse(TestUtils.createNewItem(this, PROJECT_NAME, TestUtils.Item.MULTI_CONFIGURATION_PROJECT)
                .clickMCPName(PROJECT_NAME)
                .clickConfigureButton()
                .clickToggleSwitch()
                .clickApply()
                .getStatusToggle());
    }

    @Test(dependsOnMethods = "testMCPDisableByToggle")
    public void testCheckTooltipEnablingMCP() {

        String toggleTooltipText = new HomePage(getDriver())
                .clickMCPName(PROJECT_NAME)
                .clickConfigureButton()
                .hoverOverToggleSwitch()
                .getToggleTooltipText();

        Assert.assertEquals(toggleTooltipText, "Enable or disable the current project");
    }

    @Test
    public void testYesButtonColorDeletingMCPInSidebar() {
        String expectedColorNone = "#e6001f";
        String expectedColorDark = "hsl(5, 100%, 60%)";

        String actualColor = TestUtils
                .createNewItem(this, PROJECT_NAME, TestUtils.Item.MULTI_CONFIGURATION_PROJECT)
                .clickMCPName(PROJECT_NAME)
                .clickDeleteInMenu(new DeleteDialog(getDriver()))
                .getYesButtonColorDeletingViaSidebar();

        if (getDriver().findElement(By.tagName("html")).getAttribute("data-theme").equals("none")) {
            Assert.assertEquals(expectedColorNone, actualColor);
        } else if (getDriver().findElement(By.tagName("html")).getAttribute("data-theme").equals("dark")) {
            Assert.assertEquals(expectedColorDark, actualColor);
        }
    }

    @Test
    public void testCreateProjectWithoutName() {
        final String EMPTY_NAME = "";
        final String ERROR_MESSAGE = "This field cannot be empty";

        CreateNewItemPage createNewItemPage =
                new HomePage(getDriver())
                        .clickNewItem()
                        .setItemName(EMPTY_NAME)
                        .selectMultiConfiguration();

        boolean isErrorMessageCorrect = createNewItemPage.getErrorMessageEmptyName().contains(ERROR_MESSAGE);
        boolean isCanNotPressOkButton = createNewItemPage.isOkButtonNotActive();

        Assert.assertTrue(isErrorMessageCorrect && isCanNotPressOkButton);
    }

    @Test
    public void testCreateMCProject() {
        List<String> projectNameList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectMultiConfigurationAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .getItemList();

        Assert.assertTrue(projectNameList.contains(PROJECT_NAME));
    }

    @Test(dependsOnMethods = "testCreateMCProject")
    public void testRenameMCProject() {

        HomePage homePage = new HomePage(getDriver());

        homePage
                .clickJobByName(PROJECT_NAME, new MultiConfigurationProjectPage(getDriver()))
                .clickRenameInMenu()
                .changeProjectNameWithClear(RANDOM_PROJECT_NAME)
                .clickRenameButton().clickLogo();

        Assert.assertTrue(homePage.isItemExists(RANDOM_PROJECT_NAME) && !homePage.isItemExists(PROJECT_NAME));
    }

    @Test
    public void testDeleteProjectViaDropdown() {
        TestUtils.createNewItem(this, PROJECT_NAME, TestUtils.Item.MULTI_CONFIGURATION_PROJECT);
        getDriver().findElement(By.linkText(PROJECT_NAME)).click();

        TestUtils.openElementDropdown(this, getDriver().findElement(By.linkText(PROJECT_NAME)));

        getDriver().findElement(By.cssSelector(".tippy-box [href$='Delete']")).click();
        getDriver().findElement(By.cssSelector("[data-id='ok']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.tagName("h1")).getText(),
                "Welcome to Jenkins!",
                "Project not deleted");
    }

    @Test
    public void testMoveProjectToFolderViaDropdown() {

        final String folderName = "Folder";

        TestUtils.createNewItem(this, PROJECT_NAME, TestUtils.Item.MULTI_CONFIGURATION_PROJECT);
        TestUtils.createNewItem(this, folderName, TestUtils.Item.FOLDER);

        Assert.assertTrue(new HomePage(getDriver()).openItemDropdownWithSelenium(PROJECT_NAME)
                .selectMoveFromDropdown()
                .selectFolder(folderName)
                .clickMove()
                .isProjectInsideFolder(PROJECT_NAME, folderName));
    }

    @Test
    public void testDeleteMultiConfigurationProjectFromMenu() {

        HomePage homePage = TestUtils.createMultiConfigurationProject(this, RANDOM_PROJECT_NAME);

        boolean isProjectDeleted = homePage
                .clickJobByName(RANDOM_PROJECT_NAME, new MultiConfigurationProjectPage(getDriver()))
                .clickDeleteInMenu(new DeleteDialog(getDriver()))
                .clickYes(homePage).isItemDeleted(RANDOM_PROJECT_NAME);

        Assert.assertTrue(isProjectDeleted);
    }

    @Ignore
    @Test
    public void testAddDiscardOldBuildsConfigurationsToProject(){
        final String daysToKeep = generateRandomNumber();
        final String numToKeep = generateRandomNumber();
        final String artifactDaysToKeep = generateRandomNumber();
        final String artifactNumToKeep = generateRandomNumber();

        List<String> discardOldBuildsList = TestUtils
                .createNewItem(this, PROJECT_NAME, TestUtils.Item.MULTI_CONFIGURATION_PROJECT)
                .clickMCPName(PROJECT_NAME)
                .clickConfigureButton()
                .clickDiscardOldBuilds()
                .setDaysToKeep(daysToKeep)
                .setMaxNumberOfBuildsToKeep(numToKeep)
                .clickAdvancedButton()
                .setArtifactDaysToKeepStr(artifactDaysToKeep)
                .setArtifactNumToKeepStr(artifactNumToKeep)
                .clickSaveButton()
                .clickConfigureButton()
                .clickAdvancedButton()
                .getDiscardOldBuildsListText();

        Assert.assertEquals(
                discardOldBuildsList,
                List.of(daysToKeep, numToKeep, artifactDaysToKeep, artifactNumToKeep));
    }

    @Test
    public void testSearchForCreatedProject(){

        String currentUrl = TestUtils
                .createNewItem(this, PROJECT_NAME, TestUtils.Item.MULTI_CONFIGURATION_PROJECT)
                .searchProjectByName(PROJECT_NAME, new MultiConfigurationProjectPage(getDriver()))
                .getCurrentUrl();

        Assert.assertTrue(currentUrl.contains(PROJECT_NAME));
    }

    @Test
    public void testVerifyThatDisabledIconIsDisplayedOnDashboard(){

        List<String> disabledProjectList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectMultiConfigurationAndClickOk()
                .clickBreadcrumbsProjectName(PROJECT_NAME)
                .clickDisableProject()
                .clickLogo()
                .getDisabledProjectListText();

        Assert.assertTrue(disabledProjectList.contains(PROJECT_NAME));
    }
}
package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;

public class FreestyleProject1Test extends BaseTest {

    final String FREESTYLE_PROJECT_NAME = "Freestyle project";
    final String NEW_FREESTYLE_PROJECT_NAME = "Updated name";
    private final By nameInputField = By.name("newName");
    final String PROJECT_DESCRIPTION = "Project description";
    final String PROJECT_NEW_DESCRIPTION = "Project new description";

    @Test
    public void testAddProject() {
        String projectName = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .getProjectName();

        Assert.assertEquals(projectName, FREESTYLE_PROJECT_NAME);
    }

    @Test(dependsOnMethods = "testAddProject")
    public void testAddedProjectIsDisplayedOnTheDashboardPanel() {
        List<String> itemList = new HomePage(getDriver())
                .getItemList();

        Assert.assertTrue(
                itemList.contains(FREESTYLE_PROJECT_NAME),
                "Project with '" + FREESTYLE_PROJECT_NAME + "' name is not in the list");
    }

    @Test(dependsOnMethods = "testAddProject")
    public void testOpenConfigurePageOfProject() {
        String headerText = new HomePage(getDriver())
                .clickCreatedFreestyleName()
                .clickConfigure()
                .getHeaderSidePanelText();

        Assert.assertEquals(
                headerText,
                "Configure",
                "Configure page of the project is not opened");
    }

    @Test(dependsOnMethods = {"testOpenConfigurePageOfProject", "testAddedProjectIsDisplayedOnTheDashboardPanel"})
    public void testRenameProjectFromTheBoard() {
        new Actions(getDriver()).moveToElement(getDriver().findElement(
                By.xpath("//span[text()=('" + FREESTYLE_PROJECT_NAME + "')]"))).perform();

        WebElement dropdownChevron = getDriver().findElement(
                By.xpath("//span[text()=('" + FREESTYLE_PROJECT_NAME + "')]/following-sibling::button"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].dispatchEvent(new Event('mouseenter'));" +
                "arguments[0].dispatchEvent(new Event('click'));", dropdownChevron);
        getDriver().findElement(By.partialLinkText("Rename")).click();

        getDriver().findElement(nameInputField).clear();
        getDriver().findElement(nameInputField).sendKeys(NEW_FREESTYLE_PROJECT_NAME);

        getDriver().findElement(By.name("Submit")).click();

        Assert.assertFalse(TestUtils.checkIfProjectIsOnTheBoard(getDriver(), FREESTYLE_PROJECT_NAME),
                "Old project name is on the board");

        Assert.assertTrue(TestUtils.checkIfProjectIsOnTheBoard(getDriver(), NEW_FREESTYLE_PROJECT_NAME),
                "New project name is not on the board");
    }

    @Test
    public void testEditFreestyleProjectDescription() {
        String projectDescriptionText = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .clickCreatedFreestyleName()
                .clickAddDescription()
                .setDescription(PROJECT_DESCRIPTION)
                .clickSaveButton()
                .clickAddDescription()
                .clearDescription()
                .setDescription(PROJECT_NEW_DESCRIPTION)
                .clickSaveButton()
                .getProjectDescriptionText();

        Assert.assertEquals(projectDescriptionText, PROJECT_NEW_DESCRIPTION);
    }
}


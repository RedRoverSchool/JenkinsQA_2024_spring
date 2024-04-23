package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class FreestyleProject1Test extends BaseTest {

    final String FREESTYLE_PROJECT_NAME = "Freestyle project";
    final String NEW_FREESTYLE_PROJECT_NAME = "Updated name";

    private final By nameInputField = By.name("newName");

    @Test
    public void testAddProject() {
        TestUtils.createItem(TestUtils.FREESTYLE_PROJECT, FREESTYLE_PROJECT_NAME, this);

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//h1[text()='" + FREESTYLE_PROJECT_NAME + "']")).getText(),
                FREESTYLE_PROJECT_NAME);
    }

    @Test(dependsOnMethods = "testAddProject")
    public void testAddedProjectIsDisplayedOnTheDashboardPanel() {
        Assert.assertTrue(
                TestUtils.checkIfProjectIsOnTheBoard(getDriver(), FREESTYLE_PROJECT_NAME),
                "Project with '" + FREESTYLE_PROJECT_NAME + "' name is not in the list");
    }

    @Test(dependsOnMethods = "testAddProject")
    public void testOpenConfigurePageOfProject() {
        getDriver().findElement(By.xpath("//span[text()=('Freestyle project')]")).click();

        getDriver().findElement(
                By.xpath("//*[@id='side-panel']//*[text()='Configure']//ancestor::a")).click();

        Assert.assertTrue(
                getDriver().findElement(By.xpath("//h1[text()='Configure']")).isDisplayed(),
                "Configure page of the project is not opened");
    }

    @Test(dependsOnMethods = {"testOpenConfigurePageOfProject", "testAddedProjectIsDisplayedOnTheDashboardPanel"})
    public void testRenameProjectFromTheBoard() {
        new Actions(getDriver()).moveToElement(getDriver().findElement(
                By.xpath("//span[text()=('" + FREESTYLE_PROJECT_NAME + "')]"))).perform();

        WebElement dropdownChevron =getDriver().findElement(
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
}

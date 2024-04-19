package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject2Test extends BaseTest {
    private static final By NEW_ITEM = By.xpath("//a[.='New Item']");
    private static final By ITEM_NAME_INPUT_FIELD = By.id("name");
    private static final By OK_BUTTON = By.id("ok-button");
    private static final By DASHBOARD_BUTTON = By.xpath("//a[text()='Dashboard']");
    private static final String PROJECT_NAME = "First project";
    private static final By PROJECT_ITEM = By.xpath("//a[.='" + PROJECT_NAME + "']");
    private static final String DESCRIPTION = "My first Freestyle Project";
    private static final String FOLDER_NAME = "Folder";

    private void createFreestyleProject(String name) {
        getDriver().findElement(NEW_ITEM).click();
        getDriver().findElement(ITEM_NAME_INPUT_FIELD).sendKeys(name);
        getDriver().findElement(By.xpath("//li[contains(@class, '_FreeStyleProject')]")).click();
        getDriver().findElement(OK_BUTTON).click();
        getDriver().findElement(DASHBOARD_BUTTON).click();
    }

    private void createFolder(String name) {
        getDriver().findElement(NEW_ITEM).click();
        getDriver().findElement(ITEM_NAME_INPUT_FIELD).sendKeys(name);
        getDriver().findElement(By.xpath("//label[.= 'Folder']")).click();
        getDriver().findElement(OK_BUTTON).click();
        getDriver().findElement(DASHBOARD_BUTTON).click();
    }

    @Ignore
    @Test
    public void testDescriptionAddedByUsingAddDescriptionButton() {
        createFreestyleProject(PROJECT_NAME);
        getDriver().findElement(PROJECT_ITEM).click();
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).sendKeys(DESCRIPTION);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.
                xpath("//div[@id='description']/div")).getText(), DESCRIPTION);
    }

    @Test
    public void testProjectMovedToFolder() {
        Actions action = new Actions(getDriver());
        createFreestyleProject(PROJECT_NAME);
        createFolder(FOLDER_NAME);
        getDriver().findElement(PROJECT_ITEM).click();
        getDriver().findElement(By.xpath("//a[contains(@href, '/move')]")).click();
        new Select(getDriver().findElement(By.name("destination"))).selectByValue("/Folder");
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(DASHBOARD_BUTTON).click();
        action.click(getDriver().findElement(By.xpath("//td/a[@href='job/" + FOLDER_NAME + "/']"))).perform();

        Assert.assertTrue(getDriver().getTitle().contains("Folder"));
        Assert.assertTrue(getDriver().findElement(PROJECT_ITEM).isDisplayed());
    }
}

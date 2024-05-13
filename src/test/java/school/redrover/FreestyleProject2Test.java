package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;

public class FreestyleProject2Test extends BaseTest {
    private static final By NEW_ITEM = By.xpath("//a[.='New Item']");
    private static final By ITEM_NAME_INPUT_FIELD = By.id("name");
    private static final By OK_BUTTON = By.id("ok-button");
    private static final By DASHBOARD_BUTTON = By.xpath("//a[text()='Dashboard']");
    private static final String PROJECT_NAME = "First project";
    private static final By PROJECT_ITEM_ON_PROJECTSTATUS_TABLE = By.xpath("//td/a[. = '" + PROJECT_NAME + "']");
    private static final String DESCRIPTION = "My first Freestyle Project";
    private static final String FOLDER_NAME = "Folder";

    private void createFolder(String name) {
        getDriver().findElement(NEW_ITEM).click();
        getDriver().findElement(ITEM_NAME_INPUT_FIELD).sendKeys(name);
        getDriver().findElement(By.xpath("//label[.= 'Folder']")).click();
        getDriver().findElement(OK_BUTTON).click();
        getDriver().findElement(DASHBOARD_BUTTON).click();
    }

    @Test
    public void testCreateFreestyleProject() {
        List<String> itemList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickLogo()
                .getItemList();

        Assert.assertTrue(itemList.contains(PROJECT_NAME));
    }

    @Test(dependsOnMethods = "testCreateFreestyleProject")
    public void testDescriptionAddedByUsingAddDescriptionButton() {
        getDriver().findElement(PROJECT_ITEM_ON_PROJECTSTATUS_TABLE).click();
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).sendKeys(DESCRIPTION);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertTrue(getDriver().findElement(By.
                xpath("//div[@id='description']/div")).getText().matches(DESCRIPTION));
    }

    @Test(dependsOnMethods = "testDescriptionAddedByUsingAddDescriptionButton")
    public void testProjectMovedToFolder() {
        Actions action = new Actions(getDriver());
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        getDriver().findElement(DASHBOARD_BUTTON).click();
        createFolder(FOLDER_NAME);

        WebElement projectItem = getDriver().findElement(PROJECT_ITEM_ON_PROJECTSTATUS_TABLE);
        WebElement chevron = projectItem.findElement(By.cssSelector("[class $= 'chevron']"));

        js.executeScript("arguments[0].dispatchEvent(new Event('mouseenter'));", chevron);
        js.executeScript("arguments[0].dispatchEvent(new Event('click'));", chevron);

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath("//a[contains(@href, '/move')]"))).click();

        new Select(getDriver().findElement(By.name("destination"))).selectByValue("/" + FOLDER_NAME);
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(DASHBOARD_BUTTON).click();

        action.click(getDriver().findElement(By.xpath("//td/a[@href='job/" + FOLDER_NAME + "/']"))).
                perform();

        Assert.assertTrue(getDriver().getTitle().contains("Folder"));
        Assert.assertTrue(getDriver().findElement(PROJECT_ITEM_ON_PROJECTSTATUS_TABLE).isDisplayed());
    }
}

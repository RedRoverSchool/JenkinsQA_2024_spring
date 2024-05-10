package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.model.CreateNewItemPage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class AItemTest extends BaseTest {

    private final By nameUpItem = By.xpath("//span[.='New Folder']");
    private final By renameTextField = By.xpath("//input[@name='newName']");
    private Actions actions;

    private Actions getActions() {
        if (this.actions == null) this.actions = new Actions(getDriver());
        return this.actions;
    }

    @Test
    public void testCreateItemEmptyNameNegative() {
        new HomePage(getDriver())
                .clickNewItem()
                .setItemName("New Item")
                .clearItemNameField()
                .selectMultibranchPipelineAndClickOk();

        Assert.assertFalse(new CreateNewItemPage(getDriver()).okButtonIsEnabled());
    }

    @Ignore
    @Test
    public void testCreateNewFolder() {
        TestUtils.createItem(TestUtils.FOLDER, "New Folder", this);
        TestUtils.goToMainPage(getDriver());

        Assert.assertTrue(getDriver().findElement(nameUpItem).isDisplayed());
    }

    @Ignore
    @Test
    public void testRenameFolder() {
        getActions().moveToElement(getDriver().findElement(nameUpItem)).perform();
        getDriver().findElement(By.linkText("New Folder")).click();
        getDriver().findElement(By.xpath("//a[normalize-space()='Rename']")).click();
        getDriver().findElement(renameTextField).clear();
        getDriver().findElement(renameTextField).sendKeys("New Name");
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//h1[contains(.,'New Name')]")).isDisplayed());
    }

    @Ignore
    @Test
    public void testRenameFolderShort() {
        TestUtils.renameItem(this, "New Name", "Renamed Folder");
        Assert.assertTrue(getDriver().findElement(By.xpath("//h1[contains(.,'Renamed Folder')]")).isDisplayed());
    }

    @Ignore
    @Test
    public void testCreateMulticonfigurationProjectNegative() {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.xpath("//span[text()='" + TestUtils.MULTI_CONFIGURATION_PROJECT + "']")).click();

        Assert.assertEquals(getDriver().findElement
                (By.id("itemname-required")).getText(), "» This field cannot be empty, please enter a valid name");
    }

    @Ignore
    @Test
    public void testCreateMulticonfigurationProject() {
        getDriver().findElement(By.linkText("New Item")).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("name"))).sendKeys("New Folder");
        getDriver().findElement(By.xpath("//span[text()='" + TestUtils.MULTI_CONFIGURATION_PROJECT + "']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//h1[normalize-space()='Configure']")).isDisplayed());
        TestUtils.goToMainPage(getDriver());
        Assert.assertTrue(getDriver().findElement(nameUpItem).isDisplayed());
    }

    @Ignore
    @Test
    public void testFooterRestApiLinkRGB() {
        WebElement restApi = getDriver().findElement(By.xpath("//a[normalize-space()='REST API']"));
        Assert.assertEquals(restApi.getCssValue("color"), "rgba(20, 20, 31, 1)");
    }
}
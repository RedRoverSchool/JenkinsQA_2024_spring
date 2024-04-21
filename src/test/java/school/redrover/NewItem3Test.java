package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class NewItem3Test extends BaseTest {

    private boolean isException = false;
    private final By nameUpItem = By.xpath("//span[.='New Folder']");
    private final By renameTextField = By.xpath("//input[@name='newName']");
    private Actions actions;

    private Actions getActions() {
        if (this.actions == null) this.actions = new Actions(getDriver());
        return this.actions;
    }

    @Test
    public void createItemEmptyNameNegativeTest() {
        try {
            TestUtils.createItem(TestUtils.FREESTYLE_PROJECT, "", this);
        } catch (NoSuchElementException e) {
            this.isException = true;
        }

        Assert.assertTrue(isException);
    }

    @Test
    public void createItemEmptyNameNegativeTwoTest() {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.xpath("//span[text()='" + TestUtils.MULTIBRANCH_PIPELINE + "']")).click();

        WebElement nameField = getDriver().findElement(By.id("name"));
        nameField.sendKeys("testName");
        nameField.clear();

        Assert.assertFalse(getDriver().findElement(By.id("ok-button")).isEnabled());
    }

    @Test
    public void createNewFolderTest() {
        TestUtils.createItem(TestUtils.FOLDER, "New Folder", this);
        TestUtils.goToMainPage(getDriver());

        Assert.assertTrue(getDriver().findElement(nameUpItem).isDisplayed());
    }

    @Test
    public void renameFolderTest() {
        TestUtils.createItem(TestUtils.FOLDER, "New Folder", this);
        TestUtils.goToMainPage(getDriver());

        getActions().moveToElement(getDriver().findElement(nameUpItem)).perform();
        getDriver().findElement(By.linkText("New Folder")).click();
        getDriver().findElement(By.xpath("//a[normalize-space()='Rename']")).click();
        getDriver().findElement(renameTextField).clear();
        getDriver().findElement(renameTextField).sendKeys("New Name");
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//h1[contains(.,'New Name')]")).isDisplayed());
    }

    @Test (dependsOnMethods = "createNewFolderTest")
    public void renameFolderShortTest() {
        TestUtils.renameItem(this, "New Folder", "New Name");
        Assert.assertTrue(getDriver().findElement(By.xpath("//h1[contains(.,'New Name')]")).isDisplayed());
    }
}
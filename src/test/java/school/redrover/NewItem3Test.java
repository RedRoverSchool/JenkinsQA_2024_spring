package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class NewItem3Test extends BaseTest {

    private boolean isException = false;

    @Test
    public void createItemEmptyNameNegativeTest() {
        try {
            TestUtils.createItem(TestUtils.FREESTYLE_PROJECT, "", getDriver());
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

    @Ignore
    @Test
    public void createNewFolderTest() {
        TestUtils.createItem(TestUtils.FOLDER, "New Folder", getDriver());
        TestUtils.goToMainPage(getDriver());

        Assert.assertTrue(getDriver().findElement(By.xpath("//span[.='New Folder']")).isDisplayed());
    }
}
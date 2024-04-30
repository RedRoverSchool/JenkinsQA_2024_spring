package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class NewItem18Test extends BaseTest {

    private void createNewItemPage() { getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click(); }
    private WebElement getOkButton() {
        return getDriver().findElement(By.id("ok-button"));
    }

    @Test
    public void testCreateNewItemPage() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        Assert.assertEquals(
                getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.className("h3"))).getText(),
                "Enter an item name");
    }

    @Test
    public void testEmptyNameAndType() {
        createNewItemPage();
        getDriver().findElement(By.id("name")).clear();

        Assert.assertFalse(getOkButton().isEnabled());
    }

    @Test
    public void testWithoutType() {
        createNewItemPage();
        getDriver().findElement(By.id("name")).sendKeys("ItemWithoutType");

        Assert.assertFalse(getOkButton().isEnabled());
    }

    @Test
    public void testEmptyName() {
        createNewItemPage();
        getDriver().findElement(By.id("items")).click();

        Assert.assertFalse(getOkButton().isEnabled());
    }

    @Test
    public void testHintWithoutName() {
        createNewItemPage();
        getDriver().findElement(By.id("items")).click();

        Assert.assertEquals(getDriver().findElement(By.id("itemname-required")).getText(),
                "» This field cannot be empty, please enter a valid name");
    }
}

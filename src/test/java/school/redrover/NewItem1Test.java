package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class NewItem1Test extends BaseTest {

    @Test
    public void testNewItemWithoutType() {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.cssSelector("div.add-item-name > input#name")).sendKeys("ProjectWithoutType");

        Assert.assertFalse(getDriver().findElement(By.cssSelector("div.btn-decorator > button#ok-button")).isEnabled());
    }
    @Test
    public void testNewItemWithoutNameAndType() {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();

        Assert.assertFalse(getDriver().findElement(By.cssSelector("div.btn-decorator > button#ok-button")).isEnabled());
    }
    @Test
    public void testNewItemWithoutName() {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.cssSelector("li.org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();

        Assert.assertFalse(getDriver().findElement(By.cssSelector("div.btn-decorator > button#ok-button")).isEnabled());
    }
    @Test
    public void testTextMessageNewItemUnsafeCharInName() {
        String unsafeSymbol = "#";

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.cssSelector("div.add-item-name > input#name")).sendKeys(unsafeSymbol);

        WebElement validationMessage = getDriver().findElement(By.cssSelector("div#itemname-invalid"));

        Assert.assertEquals((validationMessage).getText(), String.format("» ‘%s’ is an unsafe character", unsafeSymbol));
    }
    @Test
    public void testColorMessageNewItemUnsafeCharInName() {
        String unsafeSymbol = "#";

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.cssSelector("div.add-item-name > input#name")).sendKeys(unsafeSymbol);

        WebElement validationMessage = getDriver().findElement(By.cssSelector("div#itemname-invalid"));

        assert (Color.fromString((validationMessage).getCssValue("color")).asHex().equals("#ff0000"));
    }
    @Test
    public void testNewItemIsDisplayedOnMainPage() {
       final String nameProject = "firstProjectPipline";

        TestUtils.createNewItemAndReturnToDashboard(this, nameProject, TestUtils.Item.PIPELINE);
        Assert.assertTrue(getDriver().findElement(By.cssSelector("tr#job_" + nameProject)).isDisplayed());
    }

    @Test
    public void testDisabledOkButtonWhenItemNameIsEmpty() {
        getDriver().findElement(By.cssSelector("[href='newJob']")).click();
        getDriver().findElement(By.cssSelector("[class$=FreeStyleProject]")).click();

        boolean isOkButtonEnabled = getDriver().findElement(By.id("ok-button")).isEnabled();
        Assert.assertFalse(isOkButtonEnabled);
    }
    @Test
    public void testEmptyNameFieldHints() {
        getDriver().findElement(By.linkText("New Item")).click();

        String hintText = getDriver().findElement(By.cssSelector("[class='input-help']")).getText();
        String hintColor = getDriver().findElement(By.cssSelector("[class='input-help']")).getCssValue("color");

        getDriver().findElement(By.id("name")).sendKeys("a");
        getDriver().findElement(By.id("name")).sendKeys(Keys.BACK_SPACE);

        Assert.assertNotSame(hintText, getDriver().findElement(By.id("itemname-required")).getText());
        Assert.assertNotSame(hintColor, getDriver().findElement(By.id("itemname-required")).getCssValue("color"));
    }
}

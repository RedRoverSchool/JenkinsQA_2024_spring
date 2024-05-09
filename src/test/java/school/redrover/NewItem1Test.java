package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.CreateNewItemPage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;

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
        List<String> projectList = new HomePage(getDriver())
                .clickCreateAJob()
                .setItemName(nameProject)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .getItemList();


        Assert.assertTrue(projectList.contains(nameProject));
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
        HomePage homePage = new HomePage(getDriver());
        CreateNewItemPage createNewItemPage = new CreateNewItemPage(getDriver());

        final String testInput = "a";
        final String itemNameHintText = homePage.clickNewItem().getItemNameHintText();
        final String itemNameHintColor = createNewItemPage.getItemNameHintColor();

        createNewItemPage
                .setItemName(testInput)
                .clearItemNameField();

        Assert.assertNotSame(itemNameHintText, createNewItemPage.getItemNameHintText());
        Assert.assertNotSame(itemNameHintColor, createNewItemPage.getItemNameHintColor());
    }
}
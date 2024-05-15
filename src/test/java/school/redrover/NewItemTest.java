package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.CreateNewItemPage;
import school.redrover.model.HomePage;
import school.redrover.model.ItemPage;
import school.redrover.runner.BaseTest;

public class NewItemTest extends BaseTest {

    @Test
    public void testOpenCreateNewItemPage(){
        String newItemHeader = new HomePage(getDriver())
                .clickNewItem()
                .getPageTitle();

        String TextAboveNameField = new CreateNewItemPage(getDriver())
                .getTitleOfNameField();

        Assert.assertEquals(newItemHeader, "New Item [Jenkins]");
        Assert.assertEquals(TextAboveNameField, "Enter an item name");
    }

    @Test
    public void testCreateFreestyleProject() {
        new ItemPage(getDriver())
                .newItemClick()
                .newItemName("MyNewProject")
                .freestyleProjectClick()
                .clickButtonOK()
                .clickSaveBtn();

        Assert.assertEquals(getDriver().findElement(By.tagName("h1")).getText(), "MyNewProject");
    }

    @Test
    public void testCreateNewPipeline() {
        new ItemPage(getDriver())
                .newItemClick()
                .newItemName("NewPipeline")
                .pipelineClic()
                .clickButtonOK()
                .clickSaveBtn();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1[@class='job-index-headline page-headline']"))
                .getText(), "NewPipeline");
    }

    @Test
    public void testOkButtonUsingValidNameWithoutType() {
        boolean okButtonIsEnabled = new HomePage(getDriver())
                .clickNewItem()
                .setItemName("Test Project")
                .isOkButtonEnabled();

        Assert.assertFalse(okButtonIsEnabled);
    }

    @Test
    public void testMessageWhenCreateItemUsingSpecialCharactersInName() {
        String[] specialCharacters = {"!", "%", "&", "#", "@", "*", "$", "?", "^", "|", "/", "]", "["};

        new HomePage(getDriver())
                .clickNewItem();

        for (String specChar : specialCharacters) {
            String actualErrorMessage = new CreateNewItemPage(getDriver())
                    .clearItemNameField()
                    .setItemName("Fold" + specChar + "erdate")
                    .getErrorMessageInvalidCharacterOrDuplicateName();

            String expectMessage = "» ‘" + specChar + "’ is an unsafe character";

            Assert.assertEquals(actualErrorMessage, expectMessage, "Message is not displayed");
        }
    }

    @Test
    public void testCreateItemWithEmptyName() {
        String hintTextWhenEmptyName = "» This field cannot be empty, please enter a valid name";
        String hintColor = "rgba(255, 0, 0, 1)";

        Boolean okButtonIsEnabled = new HomePage(getDriver())
                .clickNewItem()
                .setItemName("q")
                .clearItemNameField()
                .isOkButtonEnabled();

        String validationMessage = new CreateNewItemPage(getDriver())
                .getItemNameHintText();

        String validationMessageColor = new CreateNewItemPage(getDriver())
                .getItemNameHintColor();

        Assert.assertFalse(okButtonIsEnabled);
        Assert.assertEquals(validationMessage, hintTextWhenEmptyName);
        Assert.assertEquals(validationMessageColor, hintColor);
    }
}
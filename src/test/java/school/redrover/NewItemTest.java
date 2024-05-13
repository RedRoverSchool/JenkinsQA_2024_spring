package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.CreateNewItemPage;
import school.redrover.model.HomePage;
import school.redrover.model.ItemPage;
import school.redrover.runner.BaseTest;

import java.util.List;

public class NewItemTest extends BaseTest {

    @Test
    public void testAddItem() {
        List<String> itemList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName("NewItemName")
                .selectFreestyleAndClickOk()
                .clickLogo()
                .getItemList();

        Assert.assertListContainsObject(itemList, "NewItemName", "Item not displayed");
    }

    @Test
    public void testGoToNewJobPage() {
        String pageHeading = new HomePage(getDriver())
                .clickNewItem()
                .getTitleOfNameField();

        Assert.assertEquals(pageHeading,"Enter an item name");
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
    public void testOkButtonUsingValidName() {
        boolean okButtonIsEnabled = new HomePage(getDriver())
                .clickNewItem()
                .setItemName("Test Project")
                .okButtonIsEnabled();

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
                    .getErrorMessage();

            String expectMessage = "» ‘" + specChar + "’ is an unsafe character";

            Assert.assertEquals(actualErrorMessage, expectMessage, "Message is not displayed");
        }
    }
}
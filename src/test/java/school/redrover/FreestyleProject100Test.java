package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.DeleteDialog;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;

public class FreestyleProject100Test extends BaseTest {
    final String projectName = "This is the project name";

    @Test
    public void testCreateFreestyleProject() {

        List<String> itemList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(projectName)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .getItemList();

        Assert.assertTrue(itemList.contains(projectName));
    }

    @Test(dependsOnMethods = "testCreateFreestyleProject")
    public void testDeleteUsingDropdown() {

        int beforeSize = new HomePage(getDriver()).getItemList().size();

        new HomePage(getDriver())
                .openItemDropdown(projectName)
                .clickDeleteInDropdown(new DeleteDialog(getDriver()))
                .clickYes(new HomePage(getDriver()));

        Assert.assertEquals(beforeSize - 1, new HomePage(getDriver()).getItemList().size());
    }

    @Test
    public void testRenameProjectUsingDropdown() {
        final String projectName = "This is the project to be renamed";
        TestUtils.createNewItem(this, projectName, TestUtils.Item.FREESTYLE_PROJECT);

        final String projectNewName = "Renamed project";
        TestUtils.openElementDropdown(this, TestUtils.getViewItemElement(this, projectName));
        getDriver().findElement(By.cssSelector("a[href $= '/confirm-rename']")).click();

        WebElement newName = getDriver().findElement(By.name("newName"));
        newName.clear();
        newName.sendKeys(projectNewName);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(
                getDriver().findElement(By.cssSelector("div#main-panel h1")).getText(),
                projectNewName);
    }

}

package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.FolderProjectPage;
import school.redrover.model.FreestyleProjectPage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;
import java.util.List;

public class FreestyleProject24Test extends BaseTest {

    private static final String FREESTYLE_NAME = "newFreestyleProject";

    private static final String FOLDER = "NewFolder";

    private static final String DESCRIPTION_TEXT = "This project has been added into the folder";

    private void dropDown(By xpath) {
        WebElement dropdownChevron = getDriver().findElement(xpath);
        ((JavascriptExecutor) getDriver()).executeScript(
                "arguments[0].dispatchEvent(new Event('mouseenter'));", dropdownChevron);
        ((JavascriptExecutor) getDriver()).executeScript(
                "arguments[0].dispatchEvent(new Event('click'));", dropdownChevron);
    }

   @Test
   public void testCreateFreestyleProject() {

       List<String> itemList = new HomePage(getDriver())
               .clickNewItem()
               .setItemName(FREESTYLE_NAME)
               .selectFreestyleAndClickOk()
               .clickSaveButton()
               .clickLogo()
               .getItemList();

       Assert.assertTrue(itemList.contains(FREESTYLE_NAME));
    }

    @Test(dependsOnMethods = "testCreateFreestyleProject")
    public void testAddDescription() {

        String currentFreestyleDescription = new FreestyleProjectPage(getDriver())
                .clickChangeDescription()
                .setDescription(DESCRIPTION_TEXT)
                .clickSaveButton()
                .getProjectDescriptionText();

        Assert.assertTrue(currentFreestyleDescription.matches(DESCRIPTION_TEXT));
    }

    @Test(dependsOnMethods = "testAddDescription")
    public void testFreestyleProjectMoveToFolder() {

        new HomePage(getDriver())
                .clickLogo()
                .clickNewItem()
                .setItemName(FOLDER)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .openItemDropdown(FREESTYLE_NAME)
                .chooseFolderToMove()
                .chooseFolderAndSave(FOLDER)
                .clickBreadcrumbFolder(FOLDER);

        List<String> itemListInsideFolder = new FolderProjectPage(getDriver()).getItemListInsideFolder();

        Assert.assertTrue(itemListInsideFolder.contains(FREESTYLE_NAME));
    }

    @Test(dependsOnMethods = "testFreestyleProjectMoveToFolder")
    public void testCheckFreestyleProjectViaBreadcrumb() {

        dropDown(By.xpath("(//li//button[@class='jenkins-menu-dropdown-chevron'])[1]"));

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class = 'jenkins-dropdown__item'][contains(@href, 'views')]"))).click();

        getDriver().findElement(By.xpath("(//li[@class='children'])[2]")).click();

        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class = 'jenkins-dropdown__item'][contains(@href," + FOLDER + ")]"))).click();

        getDriver().findElement(By.xpath("//td//a[@href='job/" + FREESTYLE_NAME + "/']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@class='jenkins-app-bar']")).getText(),
                FREESTYLE_NAME);
    }

    @Test(dependsOnMethods = "testCheckFreestyleProjectViaBreadcrumb")
    public void testDeleteFreestyleProjectViaDropdown() {
        getDriver().findElement(By.xpath("//tr//a[@href='job/NewFolder/']")).click();

        dropDown(By.xpath("//td//button[@class='jenkins-menu-dropdown-chevron']"));

        getDriver().findElement(By.xpath("//button[@class='jenkins-dropdown__item'][contains(@href, 'Delete')]")).click();

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-id='ok']"))).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h2")).getText(), "This folder is empty");
    }
}

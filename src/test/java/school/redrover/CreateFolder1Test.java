package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;

public class CreateFolder1Test extends BaseTest {

    private void createNewFolder(String folderName) {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.name("name")).sendKeys(folderName);
        getDriver().findElement(By.xpath("//label/span[text() ='Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();
    }

    private void openDashboard() {
        getDriver().findElement(By.id("jenkins-head-icon")).click();
    }

    @Test
    public void testNewlyCreatedFolderIsEmptyAJ() {
        final String folderName = "NewProjectFolder";
        final String thisFolderIsEmptyMessage = "This folder is empty";
        final String createAJobLinkText = "Create a job";

        createNewFolder(folderName);
        openDashboard();

        getDriver().findElement(By.linkText(folderName)).click();

        final String actualFolderName = getDriver().findElement(By.xpath("//h1")).getText();
        final String actualEmptyStateMessage = getDriver().findElement(By.xpath("//section[@class='empty-state-section']/h2")).getText();
        final WebElement newJobLink = getDriver().findElement(By.xpath("//a[@href='newJob']"));
        final String actualNewJobLinkText = newJobLink.getText();

        Assert.assertEquals(actualFolderName, folderName);
        Assert.assertEquals(actualEmptyStateMessage, thisFolderIsEmptyMessage);
        Assert.assertEquals(actualNewJobLinkText, createAJobLinkText);
        Assert.assertTrue(newJobLink.isDisplayed(), "newJobLink is NOT displayed");
    }

    @Test
    public void testRenameFolder() {
        final String folderName = "ProjectFolder";
        final String newFolderName = "NewProjectFolder";

        new HomePage(getDriver())
                 .clickNewItem()
                .setItemName(folderName)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .getItemList();

       //HomePage homePage = new HomePage(getDriver());
        List<String> itemList = new HomePage(getDriver())
                        .openItemDropdown(folderName)
                        .selectRenameFromDropdown()
                        .changeProjectName(newFolderName)
                        .clickRenameButton()
                        .clickLogo()
                        .getItemList();

        Assert.assertTrue(itemList.contains(newFolderName));
    }

    @Test
    public void testCreateFolderSpecialCharacters() {
        String[] specialCharacters = {"!", "%", "&", "#", "@", "*", "$", "?", "^", "|", "/", "]", "["};

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        WebElement nameField = getDriver().findElement(By.id("name"));

        for (String specChar: specialCharacters) {
            nameField.clear();
            nameField.sendKeys("Fold" + specChar + "erdate");

            WebElement actualMessage = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='itemname-invalid']")));

            String expectMessage = "» ‘" + specChar + "’ is an unsafe character";
            Assert.assertEquals(actualMessage.getText(), expectMessage, "Message is not displayed");
        }
    }

}

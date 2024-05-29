package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.CreateNewItemPage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

public class FolderDSTest extends BaseTest {

    private void openDashboard() {
        getDriver().findElement(By.id("jenkins-head-icon")).click();
    }

    @Test
    public void testCreateFolderDS() {
        final String folderName = "FolderDS";

        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.name("name")).sendKeys(folderName);
        getDriver().findElement(By.xpath("//label/span[text()='Folder']/ancestor::li")).click();
        getDriver().findElement(By.id("ok-button")).click();
        openDashboard();
        String actualFolderName = getDriver().findElement(By.xpath("//td/a/span")).getText();
        Assert.assertEquals(actualFolderName, folderName);

        //Assert.assertTrue(getDriver().findElement(By.linkText(folderName)).isDisplayed());
    }
    @Test
    public void testCreateFolderDSSimplePOM() {
        final String folderName = "FolderDS";

        final HomePage homePage = new HomePage(getDriver());
        homePage.getNewItemSideMenu().click();

        final CreateNewItemPage itemPage = new CreateNewItemPage(getDriver());
        itemPage.getItemNameField().sendKeys(folderName);
        itemPage.getFolderButton().click();
        itemPage.getOkButton().click();
        itemPage.clickLogo();
        String actualFolderName = homePage.getItemName();
        Assert.assertEquals(actualFolderName, folderName);
    }

    @Test
    public void testCreateFolderDSSimple1POM() {
        final String folderName = "FolderDS";

        final HomePage homePage = new HomePage(getDriver());
        homePage.ckickNewItemSideMenu();

        final CreateNewItemPage itemPage = new CreateNewItemPage(getDriver());
        itemPage.inputItemName(folderName);
        itemPage.ckickFolderButton();
        itemPage.clickOkButton();
        itemPage.clickLogo();
        String actualFolderName = homePage.getItemName();
        Assert.assertEquals(actualFolderName, folderName);
    }

    @Test
    public void testCreateFolderDSChainPOM() {
        final String folderName = "FolderDS";

        final HomePage homePage = new HomePage(getDriver());
        homePage
                .ckickNewItemSideMenuChain()
                .inputItemNameChain(folderName)
                .ckickFolderButtonChain()
                .clickOkButton();

        final CreateNewItemPage itemPage = new CreateNewItemPage(getDriver());
        itemPage.clickLogo();
        String actualFolderName = homePage.getItemName();
        Assert.assertEquals(actualFolderName, folderName);
    }
}

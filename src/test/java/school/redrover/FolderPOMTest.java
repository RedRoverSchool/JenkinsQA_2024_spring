package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.model.*;
import school.redrover.runner.BaseTest;

public class FolderPOMTest extends BaseTest {

    private final String folderName = "MyFolder";

    @Test
    public void testCreate() {
        getDriver().findElement(By.xpath("//*[text()='New Item']/ancestor::div[contains(@class,'task')]")).click();
        getDriver().findElement(By.xpath("//input[@id=\"name\"]")).sendKeys(folderName);
        getDriver().findElement(By.xpath("//*[text()='Folder']/ancestor::li")).click();
        getDriver().findElement(By.xpath("//*[@id=\"ok-button\"]")).click();
        getWait2().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@name=\"Submit\"]"))).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//*[@id=\"main-panel\"]/h1")).getText(),
                folderName);
    }


    @Test
    public void testCreateFolderWithDefaultConfigurationSimplestPOM() { // действие производится только внутри теста
        final HomePage homePage = new HomePage(getDriver());
        homePage.getNewItemSideMenu().click();

        final CreateNewItemPage createNewItemPage = new CreateNewItemPage(getDriver());
        createNewItemPage.getItemNameField().sendKeys(folderName);
        createNewItemPage.getFolderRadioButton().click();
        createNewItemPage.getOkButton().click();

        final OrganizationFolderConfigPage organizationFolderConfigPage = new OrganizationFolderConfigPage(getDriver());
        organizationFolderConfigPage.getSaveButton().click();

        final OrganizationFolderPage organizationFolderPage = new OrganizationFolderPage(getDriver());
        String actualFolderName = organizationFolderPage.getJobNameHeading().getText();

        Assert.assertEquals(actualFolderName, folderName);
    }


    @Test
    public void testCreateFolderSimplePOM() {
        final HomePage homePage = new HomePage(getDriver());
        homePage.clickNewItemSideMenu();

        final CreateNewItemPage createNewItemPage = new CreateNewItemPage(getDriver());
        createNewItemPage.inputItemName(folderName);
        createNewItemPage.clickFolderRadioButton();
        createNewItemPage.clickOkButton();

        final OrganizationFolderConfigPage organizationFolderConfigPage = new OrganizationFolderConfigPage(getDriver());
        organizationFolderConfigPage.clickSaveButton();

        final OrganizationFolderPage organizationFolderPage = new OrganizationFolderPage(getDriver());
        String actualFolderName = organizationFolderPage.getJobNameHeadingText();

        Assert.assertEquals(actualFolderName, folderName);
    }

    @Test
    public void testCreateFolderChainPOM() {
        final HomePage homePage = new HomePage(getDriver());
        homePage
                .clickNewItemSideMenuChain()
                .inputItemNameChain(folderName)
                .clickFolderRadioButtonChain()
                .clickOkButton();

        final OrganizationFolderConfigPage organizationFolderConfigPage = new OrganizationFolderConfigPage(getDriver());
        organizationFolderConfigPage.clickSaveButton();

        final OrganizationFolderPage organizationFolderPage = new OrganizationFolderPage(getDriver());
        String actualFolderName = organizationFolderPage.getJobNameHeadingText();

        Assert.assertEquals(actualFolderName, folderName);
    }

    @Ignore
    @Test
    public void testFolder() {
        PreconditionPage preconditionPage = new PreconditionPage(getDriver());
        preconditionPage.createFolder(folderName);

    }

}

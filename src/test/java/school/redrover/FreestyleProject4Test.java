package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.FreestyleProjectPage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

public class FreestyleProject4Test extends BaseTest {

    private static final String PROJECT_NAME = "JavaHashGroupProject";

    @Test
    public void testCreateNewFreestyleProject() {

        FreestyleProjectPage freestyleProjectPage = new HomePage(getDriver())
                .clickCreateJob()
                .setItemName(PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickSaveButton();

        Assert.assertTrue(freestyleProjectPage.isProjectNameDisplayed());
        Assert.assertEquals(freestyleProjectPage.getProjectName(),PROJECT_NAME);
    }

    @Test
    public void testDeleteNewFreestyleProject2() {

        String theFirstGreetings = new HomePage(getDriver())
                .clickCreateJob()
                .setItemName(PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .clickCreatedItemName()
                .deleteFreestyleProject()
                .confirmDeleteFreestyleProject()
                .getWelcomeJenkinsHeader();

        Assert.assertEquals(theFirstGreetings, "Welcome to Jenkins!");
    }

    @Test
    public void testCreateNewFreestyleProjectWithDescription (){
        final String projectItemDescription = "This is first Project";

        WebElement newItemButton = getWait2().until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@href='/view/all/newJob']")));
        newItemButton.click();

        WebElement inputNameField = getWait2().until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='name']")));
        inputNameField.sendKeys(PROJECT_NAME);

        WebElement freestyleProjectButton = getWait2().until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Freestyle project')]")));
        freestyleProjectButton.click();

        WebElement okButton = getWait2().until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@type='submit']")));
        okButton.click();

        WebElement inputDescriptonField = getWait2().until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//textarea[@name='description']")));
        inputDescriptonField.sendKeys(projectItemDescription);

        WebElement saveButton = getWait2().until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@name='Submit']")));
        saveButton.click();

        WebElement newProjectHeader = getWait2().until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='jenkins-app-bar__content jenkins-build-caption']")));
        WebElement newProjectDescription = getWait2().until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'" + projectItemDescription + "')]")));

        Assert.assertTrue(newProjectHeader.isDisplayed());
        Assert.assertEquals(newProjectHeader.getText(),PROJECT_NAME);

        Assert.assertTrue(newProjectDescription.isDisplayed());
        Assert.assertEquals(newProjectDescription.getText(),projectItemDescription);

        WebElement mainPageJenkinsButton = getWait2().until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@id='jenkins-head-icon']")));
        mainPageJenkinsButton.click();

        WebElement mainPageFreestyleProjectNameField = getWait2().until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//td/a[@href='job/" + PROJECT_NAME + "/']")));

        Assert.assertEquals(mainPageFreestyleProjectNameField.getText(),PROJECT_NAME);
    }
}

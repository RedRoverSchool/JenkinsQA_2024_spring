package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.FreestyleProjectPage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;

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
    public void testDeleteNewFreestyleProject() {

        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@href='/view/all/newJob']"))).click();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='name']"))).sendKeys(PROJECT_NAME);
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Freestyle project')]"))).click();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@type='submit']"))).click();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@name='Submit']"))).click();

        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Dashboard')]"))).click();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'" + PROJECT_NAME + "')]"))).click();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Delete Project')]"))).click();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-id='ok']"))).click();

        WebElement welcomeToJenkinsTitle = getWait2().until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(text(),'Welcome to Jenkins')]")));

        Assert.assertTrue(welcomeToJenkinsTitle.isDisplayed());
    }

    @Test
    public void testCreateNewFreestyleProjectWithDescription (){
        final String projectItemDescription = "This is first Project";

        FreestyleProjectPage freestyleProjectPage = new HomePage(getDriver())
                .clickCreateJob()
                .setItemName(PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .inputDescription(projectItemDescription)
                .clickSaveButton();

        Assert.assertTrue(freestyleProjectPage.isProjectNameDisplayed());
        Assert.assertEquals(freestyleProjectPage.getProjectName(),PROJECT_NAME);
        Assert.assertEquals(freestyleProjectPage.getProjectDescriptionText(),projectItemDescription);

        List<String> itemList = new HomePage(getDriver())
                .clickLogo()
                .getItemList();

        Assert.assertTrue((itemList.contains(PROJECT_NAME)));

        String description = new HomePage(getDriver()).clickCreatedFreestyleName()
                        .getProjectDescriptionText();
        Assert.assertEquals(description,projectItemDescription);


    }
}

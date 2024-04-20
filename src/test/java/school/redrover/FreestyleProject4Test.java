package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject4Test extends BaseTest {

    @Ignore
    @Test
    public void testCreateNewFreestyleProject() {

        final String projectItemName = "JavaHashGroupProject";

        WebElement newItemButton = getDriver().findElement(By.xpath("//*[@href='/view/all/newJob']"));
        newItemButton.click();

        WebElement inputNameField = getDriver().findElement(By.xpath("//input[@name='name']"));
        inputNameField.sendKeys(projectItemName);

        WebElement freestyleProjectButton = getDriver().findElement(By.xpath("//span[contains(text(),'Freestyle project')]"));
        freestyleProjectButton.click();

        WebElement okButton = getDriver().findElement(By.xpath("//button[@type='submit']"));
        okButton.click();

        WebElement saveButton = getDriver().findElement(By.xpath("//button[@name='Submit']"));
        saveButton.click();

        WebElement newProjectHeader = getDriver().findElement(By.xpath("//div[@class='jenkins-app-bar__content jenkins-build-caption']"));

        Assert.assertTrue(newProjectHeader.isDisplayed());
        Assert.assertEquals(newProjectHeader.getText(),projectItemName);
    }

    @Ignore
    @Test
    public void testCreateNewFreestyleProjectWithDescription (){
        final String projectItemName = "JavaHashGroupProject";
        final String projectItemDescription = "This is first Project";

        WebElement newItemButton = getDriver().findElement(By.xpath("//*[@href='/view/all/newJob']"));
        newItemButton.click();

        WebElement inputNameField = getDriver().findElement(By.xpath("//input[@name='name']"));
        inputNameField.sendKeys(projectItemName);

        WebElement freestyleProjectButton = getDriver().findElement(By.xpath("//span[contains(text(),'Freestyle project')]"));
        freestyleProjectButton.click();

        WebElement okButton = getDriver().findElement(By.xpath("//button[@type='submit']"));
        okButton.click();

        WebElement inputDescriptonField = getDriver().findElement(By.xpath("//textarea[@name='description']"));
        inputDescriptonField.sendKeys(projectItemDescription);

        WebElement saveButton = getDriver().findElement(By.xpath("//button[@name='Submit']"));
        saveButton.click();

        WebElement newProjectHeader = getDriver().findElement(By.xpath("//div[@class='jenkins-app-bar__content jenkins-build-caption']"));
        WebElement newProjectDescription = getDriver().findElement(By.xpath("//div[contains(text(),'" + projectItemDescription + "')]"));

        Assert.assertTrue(newProjectHeader.isDisplayed());
        Assert.assertEquals(newProjectHeader.getText(),projectItemName);

        Assert.assertTrue(newProjectDescription.isDisplayed());
        Assert.assertEquals(newProjectDescription.getText(),projectItemDescription);

        WebElement mainPageJenkinsButton = getDriver().findElement(By.xpath("//img[@id='jenkins-head-icon']"));
        mainPageJenkinsButton.click();

        WebElement mainPageFreestyleProjectNameField = getDriver().findElement(By.xpath("//td/a[@href='job/" + projectItemName + "/']"));

        Assert.assertEquals(mainPageFreestyleProjectNameField.getText(),projectItemName);
    }
}

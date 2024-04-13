package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class OperationJavaGroupTest extends BaseTest {
    public WebElement okButton(){
        return getDriver().findElement(By.id("ok-button"));
    }
    public WebElement submitButton(){
        return getDriver().findElement(By.xpath("//button[@name = 'Submit']"));}

    @Test
    public void testFreestyleProject() {
        getDriver().findElement(By.xpath("//a[.='New Item']")).click();

        WebElement nameInput = getDriver().findElement(By.id("name"));
        nameInput.click();
        nameInput.sendKeys("FreestyleProject");

        WebElement project = getDriver().findElement(By.xpath("//li[@class='hudson_model_FreeStyleProject']"));
        project.click();

        WebElement buttonOk = getDriver().findElement(By.id("ok-button"));
        buttonOk.click();

        WebElement buttonSave = getDriver().findElement(By.name("Submit"));
        buttonSave.click();

        WebElement dashboard = getDriver().findElement(By.xpath("//a[.='Dashboard']"));
        dashboard.click();

        WebElement nameDashboard = getDriver().findElement(By.xpath("//span[.='FreestyleProject']"));

        Assert.assertEquals(nameDashboard.getText(), "FreestyleProject");
    }

    @Test
    public void testFreestyleProjectMoveToFolder() {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input']"))
               .sendKeys("Folder");
        getDriver().findElement(By.xpath("//span[text()='Folder']")).click();
        okButton().click();
        submitButton().click();

        getDriver().findElement(By.xpath("//ol/li/a[@href='/']")).click();
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input']"))
                .sendKeys("FreestyleProject");
        getDriver().findElement(By.xpath("//span[contains(text(), 'Freestyle project')]")).click();
        okButton().click();
        submitButton().click();

        getDriver().findElement(By.xpath("//ol/li/a[@href='/']")).click();
        getDriver().findElement(By.xpath("//span[contains(text(), 'FreestyleProject')]")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/FreestyleProject/move']")).click();
        getDriver().findElement(By.xpath("//select/option[@value='/Folder']")).click();
        getDriver().findElement(By.xpath("//button[@formnovalidate='formNoValidate']")).click();

        getDriver().findElement(By.xpath("//ol/li/a[@href='/']")).click();
        getDriver().findElement(By.xpath("//td/a[@href='job/Folder/']")).click();
    }
}


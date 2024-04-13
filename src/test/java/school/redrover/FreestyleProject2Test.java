package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject2Test extends BaseTest {
    public WebElement okButton(){
        return getDriver().findElement(By.id("ok-button"));
    }
    public WebElement submitButton(){
        return getDriver().findElement(By.xpath("//button[@name = 'Submit']"));}

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

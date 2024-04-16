package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;
import org.openqa.selenium.WebElement;

public class FreestyleProject8Test extends BaseTest {
    public WebElement okButton() {
        return getDriver().findElement(By.id("ok-button"));
    }

    public WebElement submitButton() {
        return getDriver().findElement(By.xpath("//button[@name = 'Submit']"));
    }

    @Test
    public void testAddDescriptionInFreestyleProjectFromProject() {
        String description = "Testing adding a project description from the project.";

        TestUtils.createItem("Freestyle project", "Freestyle project test 1", getDriver());
        getDriver().findElement(By.xpath("//*[@class=\"model-link\" and text()='Dashboard']")).click();

        getDriver().findElement(By.xpath("//*[@id=\"job_Freestyle project test 1\"]/descendant::a")).click();
        getDriver().findElement(By.xpath("//*[@id=\"description-link\"]")).click();
        getDriver().findElement(By.xpath("//*[@name=\"description\"]")).sendKeys(description);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        String text = getDriver().findElement(By.xpath("//*[@id='description']/div")).getText();
        System.out.println(text);

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//*[@id='description']/div")).getText(),
                description);
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
        getDriver().findElement(By.xpath("//a/span[text()='Folder']")).click();

        WebElement projectName = getDriver().findElement(By.xpath("//a/span[text()='FreestyleProject']"));
        String projectNameText = projectName.getText();
        String expectedNameText = "FreestyleProject";
        Assert.assertEquals(expectedNameText, projectNameText);
    }
}

package school.redrover;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.*;
import org.testng.annotations.*;
import school.redrover.runner.*;

public class Folder2Test extends BaseTest {

    private String folderName = "MyFolder";

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

    @Test(dependsOnMethods = "testCreate")
    public void testCreateFreestyleProjectInFolder(){
        final String freestyleName = "InternalFreestyleProject";

        getDriver().findElement(By.xpath("//span[text()='" + folderName + "']")).click();
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(freestyleName);
        getDriver().findElement(By.cssSelector("[class$='_FreeStyleProject']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/" + folderName + "/']")).click();

        String actualFreestyleName = getDriver().findElement(By.xpath("//table//a[@href='job/InternalFreestyleProject/']")).getText();

        Assert.assertEquals(actualFreestyleName, freestyleName);
    }

    @Test(dependsOnMethods = "testCreateFreestyleProjectInFolder")
    public void testRenameFolder(){
        final String newFolderName = "NewProjectFolder";

        getDriver().findElement(By.xpath("//span[text()='" + folderName + "']")).click();
        getDriver().findElement(By.linkText("Rename")).click();

        WebElement renameInput = getDriver().findElement((By.name("newName")));
        renameInput.clear();
        renameInput.sendKeys(newFolderName);
        getDriver().findElement((By.name("Submit"))).click();

        getDriver().findElement(By.id("jenkins-head-icon")).click();

        folderName = newFolderName;
        final String actualFolderName = getDriver().findElement(By.xpath("//table//a[@href='job/NewProjectFolder/']")).getText();

        Assert.assertEquals(actualFolderName, newFolderName);
    }

    @Test(dependsOnMethods = "testRenameFolder")
    public void testRenameFolder2(){
        Actions actions=new Actions(getDriver());
        WebElement element = getDriver().findElement(By.xpath("//table//a[contains(@href,'job/')]"));
        actions.moveToElement(element).perform();
        getDriver().findElement(By.xpath("//table//button[@class='jenkins-menu-dropdown-chevron']")).click();
        getDriver().findElement(By.xpath("//a[contains(@href,'/confirm-rename')]")).click();
        WebElement input = getDriver().findElement(By.xpath("//input[@name='newName']"));
        input.clear();
        input.sendKeys("NFolder");
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(),"NFolder");
    }
}


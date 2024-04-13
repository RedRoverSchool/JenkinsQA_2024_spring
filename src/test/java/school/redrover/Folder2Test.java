package school.redrover;

import org.openqa.selenium.*;
import org.testng.*;
import org.testng.annotations.*;
import school.redrover.runner.*;

public class Folder2Test extends BaseTest {
    private void createFolder(String job, String name) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(name);
        getDriver().findElement(By.cssSelector("[class$='" + job + "']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
        getDriver().findElement(By.id("jenkins-home-link")).click();
    }

    @Test
    public void testCreateFreestyleProjectInFolder(){
        createFolder("_Folder", "ExternalFolder");

        final String freestyleName = "InternalFreestyleProject";

        getDriver().findElement(By.xpath("//span[text()='ExternalFolder']")).click();
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(freestyleName);
        getDriver().findElement(By.cssSelector("[class$='_FreeStyleProject']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/ExternalFolder/']")).click();

        String actualFreestyleName = getDriver().findElement(By.xpath("//table//a[@href='job/InternalFreestyleProject/']")).getText();

        Assert.assertEquals(actualFreestyleName, freestyleName);
    }

}

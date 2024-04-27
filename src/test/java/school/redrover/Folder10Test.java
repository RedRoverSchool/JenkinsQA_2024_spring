package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Folder10Test extends BaseTest {

    final String FOLDER_NAME = "Folder SV";

    public void goHome() {
        getDriver().findElement(By.id("jenkins-home-link")).click();
    }

    @Test
    public void testCreateFolder() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']/ancestor::span[@class='task-link-wrapper ']")).click();

        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys(FOLDER_NAME);
        getDriver().findElement(By.xpath("//li[contains(@class, '_folder')]")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), FOLDER_NAME);

        goHome();
        Assert.assertEquals(getDriver().findElement(By.xpath("(//*[@class=' job-status-']/td)[3]")).getText(), FOLDER_NAME);
    }

}

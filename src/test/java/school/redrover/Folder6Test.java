package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Folder6Test extends BaseTest {
    private void createFolder(String name) {
        getDriver().findElement(By.xpath("//*[@id=\"tasks\"]/div[1]/span/a")).click();
        getDriver().findElement(By.xpath("//input[@id=\"name\"]")).sendKeys(name);
        getDriver().findElement(By.xpath("//*[text()='Folder']/ancestor::li")).click();
        getDriver().findElement(By.xpath("//*[@id=\"ok-button\"]")).click();
        getDriver().findElement(By.xpath("//*[@name=\"Submit\"]")).click();
        openDashboard();
    }
    private void openDashboard() {
        getDriver().findElement(By.xpath("//*[@id=\"breadcrumbs\"]/li[1]/a")).click();
    }

    @Ignore
    @Test
    public void testRenameFolder() {
        createFolder("My new Folder");
        getDriver().findElement(By.xpath("//*[@id=\"job_My new Folder\"]/td[3]/a/span")).click();
        getDriver().findElement(By.xpath("//*[@id=\"tasks\"]/div[7]/span/a")).click();
        getDriver().findElement(By.xpath("//*[@id=\"main-panel\"]/form/div[1]/div[1]/div[2]/input")).clear();
        getDriver().findElement(By.xpath("//*[@id=\"main-panel\"]/form/div[1]/div[1]/div[2]/input")).sendKeys("Folder");
        getDriver().findElement(By.xpath("//*[@id=\"bottom-sticker\"]/div/button")).click();
        openDashboard();

        Assert.assertTrue(getDriver().findElement(By.xpath("//*[@id=\"job_Folder\"]/td[3]/a/span")).isDisplayed());
    }
}


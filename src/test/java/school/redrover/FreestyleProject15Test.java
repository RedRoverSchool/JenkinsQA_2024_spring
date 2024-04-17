package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;

import static java.sql.DriverManager.getDriver;

public class FreestyleProject15Test extends BaseTest {

    @Test
    public void testFirst() {
        WebElement newItem = getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']"));
        newItem.click();

        WebElement jobName = getDriver().findElement(By.id("name"));
        jobName.sendKeys("First job");

        WebElement jobType = getDriver().findElement(By.xpath("//span[text()='Freestyle project']"));
        jobType.click();

        WebElement okButton = getDriver().findElement(By.id("ok-button"));
        okButton.click();

        WebElement dashboard = getDriver().findElement(By.xpath("//a[text()='Dashboard']"));
        dashboard.click();

        WebElement newItem2 = getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']"));
        newItem2.click();

        WebElement jobName2 = getDriver().findElement(By.id("name"));
        jobName2.sendKeys("Second job");

        WebElement jobType2 = getDriver().findElement(By.xpath("//span[text()='Freestyle project']"));
        jobType2.click();

        WebElement okButton2 = getDriver().findElement(By.id("ok-button"));
        okButton2.click();

        WebElement dashboard2 = getDriver().findElement(By.xpath("//a[text()='Dashboard']"));
        dashboard2.click();

        List<WebElement> jobList = getDriver().findElements(
                By.xpath("//tr//td//a[contains(@href, 'job')]//span"));
        boolean isJobExist = false;
        for (WebElement job : jobList) {
            if (job.getText().equals("First job")) {
                isJobExist = true;
                break;
            }
        }

        Assert.assertTrue(isJobExist,"Проект не найден.");
    }
}


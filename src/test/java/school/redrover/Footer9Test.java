package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;


public class Footer9Test extends BaseTest {

    @Test
    public void testJenkinsVersion() {
        getDriver().findElement(By.cssSelector("[class$='jenkins_ver']")).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(getDriver().findElement(By.xpath("//a[@href='/manage/about']")))).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//p[.='Version 2.440.2']")).isDisplayed());
    }

    @Test
    public void testDropDownLink() {
        getDriver().findElement(By.cssSelector("[class$='jenkins_ver']")).click();

        WebElement dropDownMenu = getDriver().findElement(By.cssSelector("#tippy-1"));
        WebElement aboutJenkins = getWait5().until(ExpectedConditions.elementToBeClickable(getDriver().findElement(By.xpath("//a[@href='/manage/about']"))));

        Assert.assertTrue(aboutJenkins.isDisplayed());
        Assert.assertTrue(dropDownMenu.findElement(By.xpath("//a[@href='https://www.jenkins.io/participate/']")).isDisplayed());
        Assert.assertTrue(dropDownMenu.findElement(By.xpath("//a[@href='https://www.jenkins.io/']")).isDisplayed());
    }

    @Test
    public void testJenkinsInformationFooter() {
        List<String> tabBarMenu = List.of("Mavenized dependencies", "Static resources", "License and dependency information for plugins");

        getDriver().findElement(By.cssSelector("[class$='jenkins_ver']")).click();

        getWait5().until(ExpectedConditions.elementToBeClickable(getDriver().findElement(By.xpath("//a[@href='/manage/about']")))).click();

        WebElement tabBar = getDriver().findElement(By.xpath("//div[@class='tabBar']"));

        Assert.assertTrue(tabBar.getText().contains(tabBarMenu.get(0)), "Expected: " + tabBarMenu.get(0));
        Assert.assertTrue(tabBar.getText().contains(tabBarMenu.get(1)), "Expected: " + tabBarMenu.get(1));
        Assert.assertTrue(tabBar.getText().contains(tabBarMenu.get(2)), "Expected: " + tabBarMenu.get(2));
    }
}
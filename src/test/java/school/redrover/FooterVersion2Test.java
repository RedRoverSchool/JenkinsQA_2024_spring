package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

public class FooterVersion2Test extends BaseTest {
    private String version;

    public String getVersion(){
        getDriver().findElement(By.linkText("Manage Jenkins")).click();
        WebElement buttonAboutJenkins = getDriver().findElement(By.xpath("//a[@href='about']/dl/dt"));
        new Actions(getDriver()).scrollToElement(buttonAboutJenkins).perform();
        buttonAboutJenkins.click();

        String versionAll = getDriver().findElement(By.xpath("/html/body/div[2]/div/div[2]/div[1]/p")).getText();
        version = versionAll.substring(versionAll.indexOf(" ")+1);

        return version;
    }

    @Test
    public void testDashboardVersion(){
        String versionExpected = "Jenkins " + getVersion();
        Assert.assertEquals(getDriver().findElement(By.xpath("/html/body/footer/div/div[2]/button")).getText(),
                versionExpected,"DashboardVersion");
    }

    @Test
    public void testPeopleVersion(){
        String versionExpected = "Jenkins " + getVersion();

        getDriver().findElement(By.xpath("//*[@id=\"breadcrumbs\"]/li[1]/a")).click();
        getDriver().findElement(By.xpath("//a[@href='/asynchPeople/']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("/html/body/footer/div/div[2]/button")).getText(),
                versionExpected,"PeopleVersion");
    }

    @Test
    public void testBuildHistoryVersion(){
        String versionExpected = "Jenkins " + getVersion();

        getDriver().findElement(By.xpath("//*[@id=\"breadcrumbs\"]/li[1]/a")).click();
        getDriver().findElement(By.xpath("//a[@href='/view/all/builds']"));
        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id=\"jenkins\"]/footer/div/div[2]/button")).getText(),
                versionExpected,"BuildHistoryVersion");
    }


}

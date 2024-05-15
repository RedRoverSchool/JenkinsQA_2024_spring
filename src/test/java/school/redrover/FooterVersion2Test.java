package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

public class FooterVersion2Test extends BaseTest {
    private String version;

    public String getVersion(){

        return new HomePage(getDriver())
                .clickVersion()
                .selectAboutJenkins()
                .getJenkinsVersion();
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

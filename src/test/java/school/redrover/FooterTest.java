package school.redrover;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import school.redrover.model.HomePage;
import school.redrover.model.MultibranchPipelineConfigPage;
import school.redrover.runner.BaseTest;

public class FooterTest extends BaseTest {

    @Test
    public void testJenkinsVersionFooter() {
        WebElement jenkinsVersion = getDriver().findElement(By.xpath("//*[@class='jenkins-button jenkins-button--tertiary jenkins_ver']"));

        Assert.assertEquals(jenkinsVersion.getText(), "Jenkins 2.440.2");
    }

    @Test
    public void testApiInfo() {
        getDriver().findElement(By.xpath("//*[@class='jenkins-button jenkins-button--tertiary rest-api']")).click();

        Assert.assertEquals(getDriver().getCurrentUrl(), "http://localhost:8080/api/");
    }

    @Test
    public void testLinkButtonsListInVersionDropDown() {
        final List<String> expectedDropDownElementsValues = List.of("About Jenkins", "Get involved", "Website");

        List<String> actualDropDownElementsValues = new HomePage(getDriver())
            .clickVersion()
            .getVersionDropDownElementsValues();

        Assert.assertEquals(actualDropDownElementsValues, expectedDropDownElementsValues, "Allarm!");
    }

    @Test
    public void testRestAPIButtonTitle() {
        getDriver().findElement(By.xpath("//a[@href='api/']")).click();

        String titleText = getDriver().getTitle();

        Assert.assertEquals(titleText, "Remote API [Jenkins]");
    }
}
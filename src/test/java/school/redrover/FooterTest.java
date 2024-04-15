package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
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
}

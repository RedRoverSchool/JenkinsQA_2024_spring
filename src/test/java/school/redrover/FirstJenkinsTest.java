package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FirstJenkinsTest extends BaseTest {
    @Test
    public void testJenkins() {
        WebElement element = getDriver().findElement(By.xpath("//span[text()='People']//parent::a"));
        element.click();
        WebElement iconSizeSmall = getDriver().findElement(By.xpath("//a[@title='Small']"));
        iconSizeSmall.click();
        WebElement iconSizeMedium = getDriver().findElement(By.xpath("//a[@title='Medium']"));
        iconSizeMedium.click();
        WebElement iconSizeLarge = getDriver().findElement(By.xpath("//a[@title='Large']"));
        iconSizeLarge.click();
        WebElement peopleText = getDriver().findElement(By.xpath("//p[@class='jenkins-description']"));
        Assert.assertTrue(peopleText.getText().contains("including login"));
    }
}

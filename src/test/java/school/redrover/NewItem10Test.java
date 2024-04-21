package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class NewItem10Test extends BaseTest {

    @Ignore
    @Test
    public void newItemFromOtherExistingTest(){
        getDriver().findElement(By.xpath("//span[text()='Create a job']"))
                .click();
        getDriver().findElement(By.className("jenkins-input"))
                .sendKeys("ItemNameIsNewJob");
        getDriver().findElement(By.className("hudson_model_FreeStyleProject"))
                .click();
        getDriver().findElement(By.id("ok-button"))
                .click();
        getDriver().findElement(By.cssSelector(".jenkins-button.jenkins-button--primary"))
                .click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector(".job-index-headline.page-headline")).getText()
                , "ItemNameIsNewJob");
    }
}
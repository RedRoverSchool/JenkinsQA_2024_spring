package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class FreestyleProject16Test extends BaseTest {

    @Test
    public void testRenameFreestyleProject() {
        TestUtils.createItem(TestUtils.FREESTYLE_PROJECT, "test", getDriver());
        TestUtils.returnToDashBoard(this);
        getDriver().findElement(By.xpath("//a[@href='job/test/']")).click();
        getDriver().findElement(By.xpath("//div[@class='jenkins-dropdown']//a[@href='/job/test/confirm-rename']")).click();
        getDriver().findElement(By.xpath("//input[@checkdependson='newName']")).clear();
        getDriver().findElement(By.xpath("//input[@checkdependson='newName']")).sendKeys("newtest");
        getDriver().findElement(By.xpath("//button[@formnovalidate='formNoValidate']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1[@class='job-index-headline page-headline']")).getText(), "newtest");
    }
}

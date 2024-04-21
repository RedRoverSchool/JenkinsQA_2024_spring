package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class FreestyleProject22Test extends BaseTest {

    @Test
    public void testRenameFreestyleProjectFromDropdownMenu() {
        TestUtils.createItem(TestUtils.FREESTYLE_PROJECT, "test", this);
        TestUtils.returnToDashBoard(this);
        Actions actions = new Actions(getDriver());
        WebElement dropDownChevron = getDriver().findElement(By.xpath("//table//button[@class='jenkins-menu-dropdown-chevron']"));
        actions.moveToElement(dropDownChevron).perform();
        getDriver().findElement(By.xpath("//table//button[@class='jenkins-menu-dropdown-chevron']")).click();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='jenkins-dropdown']//a[@href='/job/test/confirm-rename']"))).click();
        getDriver().findElement(By.xpath("//input[@checkdependson='newName']")).clear();
        getDriver().findElement(By.xpath("//input[@checkdependson='newName']")).sendKeys("newtest");
        getDriver().findElement(By.xpath("//button[@formnovalidate='formNoValidate']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1[@class='job-index-headline page-headline']")).getText(), "newtest");
    }
}

package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class GroupUnitedByJava8Test extends BaseTest {

    @Test
    public void testDemoQADoubleClick() {
        getDriver().get("https://demoqa.com/");

        WebElement elementsPage = getDriver().findElement(By.xpath("//h5[text()='Elements']"));
        elementsPage.click();
        WebElement buttons = getDriver().findElement(By.xpath("//span[@class='text' and text()='Buttons']"));
        buttons.click();

        WebElement doubleClickMe = getDriver().findElement(By.id("doubleClickBtn"));
        new Actions(getDriver())
            .doubleClick(doubleClickMe)
            .perform();

        String doubleClickMessageText = getDriver().findElement(By.id("doubleClickMessage")).getText();

        Assert.assertEquals(doubleClickMessageText, "You have done a double click");
    }
}

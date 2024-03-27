package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;


public class AqaGroupUitestingplaygroundTest extends AqaGroupBaseTest {
    @Test
    public void testClientSideDelay() {
        getDriver().get("http://uitestingplayground.com/clientdelay");

        getWait25().until(ExpectedConditions.elementToBeClickable(By.id("ajaxButton"))).click();

        Assert.assertTrue(
                getWait25()
                        .until(ExpectedConditions.visibilityOfElementLocated(By.className("bg-success")))
                        .getText()
                        .startsWith("Data calculated"),
                "Label text wrong");
    }

    @Test
    public void testOverlappedElement() {
        getDriver().get("http://uitestingplayground.com/overlapped");

        final String myName = "myName";
        WebElement name = scrollIntoView(getDriver().findElement(By.id("name")));
        name.sendKeys(myName);

        Assert.assertEquals(
                name.getAttribute("value"),
                myName,
                "input field contains wrong value");
    }
}

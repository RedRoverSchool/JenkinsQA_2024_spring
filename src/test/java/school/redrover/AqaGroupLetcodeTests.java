package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AqaGroupLetcodeTests extends AqaGroupBaseTest {

    private static final String URL = "https://letcode.in/edit";

    @Test
    public void testSendKeys() {
        getDriver().get(URL);
        getDriver().findElement(By.id("fullName")).sendKeys("Ira");
        Assert.assertEquals(
                getDriver().findElement(By.id("fullName")).getAttribute("value"),
                "Ira");
    }

    @Test
    public void testKeyboardTAB() {
        getDriver().get(URL);
        getDriver().findElement(By.id("join")).sendKeys(" at all" + Keys.TAB);
//        getDriver().findElement(By.id("join")).sendKeys(" at all\t");
        Assert.assertEquals(
                getDriver().switchTo().activeElement().getAttribute("value"),
                "ortonikc");
    }

    @Test
    public void testClear() {
        getDriver().get(URL);
        getDriver().findElement(By.id("clearMe")).clear();
        Assert.assertEquals(
                getDriver().findElement(By.id("clearMe")).getAttribute("value"),
                "");
    }

    @Test
    public void testIsEnabled() {
        getDriver().get(URL);
        Assert.assertFalse(getDriver().findElement(By.id("noEdit")).isEnabled());
    }

    @Test
    public void testReadonly() {
        getDriver().get(URL);
        Assert.assertEquals(
                getDriver().findElement(By.id("dontwrite")).getAttribute("readonly"),
                "true");
    }
}
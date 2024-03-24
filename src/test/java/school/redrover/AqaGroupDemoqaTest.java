package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AqaGroupDemoqaTest extends AqaGroupBaseTest {
    private static final String URL = "https://demoqa.com/buttons";

    @Test
    public void testDoubleClickButton() {
        driver.get(URL);

        new Actions(driver)
                .doubleClick(scrollIntoView(driver.findElement(By.id("doubleClickBtn"))))
                .perform();

        Assert.assertEquals(
                driver.findElement(By.id("doubleClickMessage")).getText(),
                "You have done a double click",
                "Double click attempt failed.");
    }

    @Test
    public void testRightClickButton() {
        driver.get(URL);

        new Actions(driver)
                .contextClick(scrollIntoView(driver.findElement(By.id("rightClickBtn"))))
                .perform();

        Assert.assertEquals(
                driver.findElement(By.id("rightClickMessage")).getText(),
                "You have done a right click",
                "Right click attempt failed.");
    }

    @Test
    public void testDynamicClickButton() {
        driver.get(URL);

        scrollIntoView(driver
                .findElement(By.xpath("//*[@id=\"rightClickBtn\"]/../following-sibling::div/button")))
                .click();

        Assert.assertEquals(
                driver.findElement(By.id("dynamicClickMessage")).getText(),
                "You have done a dynamic click",
                "Right click attempt failed.");
    }
}

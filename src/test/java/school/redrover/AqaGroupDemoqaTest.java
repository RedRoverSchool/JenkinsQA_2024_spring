package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AqaGroupDemoqaTest extends AqaGroupBaseTest {
    private static final String BUTTONS_URL = "https://demoqa.com/buttons";
    private static final String BROWSER_WINDOWS_URL = "https://demoqa.com/browser-windows";

    @Test
    public void testDoubleClickButton() {
        driver.get(BUTTONS_URL);

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
        driver.get(BUTTONS_URL);

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
        driver.get(BUTTONS_URL);

        scrollIntoView(driver
                .findElement(By.xpath("//*[@id=\"rightClickBtn\"]/../following-sibling::div/button")))
                .click();

        Assert.assertEquals(
                driver.findElement(By.id("dynamicClickMessage")).getText(),
                "You have done a dynamic click",
                "Right click attempt failed.");
    }

    @Test
    public void testBrowserWindowOpenInNewTab() {
        driver.get(BROWSER_WINDOWS_URL);

        driver.findElement(By.id("tabButton")).click();
        getWait5().until(ExpectedConditions.numberOfWindowsToBe(2));
        String original = driver.getWindowHandle();
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(original)) {
                driver.switchTo().window(handle);
                break;
            }
        }
        String text = driver.findElement(By.id("sampleHeading")).getText();
//        close the new window and switch back to original one if we still need it
        driver.close();
        driver.switchTo().window(original);

        Assert.assertEquals(text, "This is a sample page");
    }

    @Test
    public void testBrowserWindowOpenInNewWindow() {
        driver.get(BROWSER_WINDOWS_URL);

        driver.findElement(By.id("windowButton")).click();
        getWait5().until(ExpectedConditions.numberOfWindowsToBe(2));
        String original = driver.getWindowHandle();
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(original)) {
                driver.switchTo().window(handle);
                break;
            }
        }
        String text = driver.findElement(By.id("sampleHeading")).getText();
//        close the new window and switch back to original one if we still need it
        driver.close();
        driver.switchTo().window(original);

        Assert.assertEquals(text, "This is a sample page");
    }

    @Test
    public void RadioButtonTest4() throws InterruptedException{
        driver.get("https://demoqa.com/radio-button");
        driver.findElement(By.xpath("//*[@for=\"impressiveRadio\"]")).click();
        Thread.sleep(5000);
        Assert.assertTrue(driver.findElement(By.className("text-success")).isDisplayed(), "radiobutton is not selected");
    }
}
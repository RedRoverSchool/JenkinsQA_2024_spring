package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class AqaGroupDemoQATest extends AqaGroupBaseTest {

    private static final String BUTTONS_URL = "https://demoqa.com/buttons";
    private static final String BROWSER_WINDOWS_URL = "https://demoqa.com/browser-windows";

    @Test
    public void testDoubleClickButton() {
        getDriver().get(BUTTONS_URL);

        new Actions(getDriver())
                .doubleClick(scrollIntoView(getDriver().findElement(By.id("doubleClickBtn"))))
                .perform();

        Assert.assertEquals(
                getDriver().findElement(By.id("doubleClickMessage")).getText(),
                "You have done a double click",
                "Double click attempt failed.");
    }

    @Test
    public void testRightClickButton() {
        getDriver().get(BUTTONS_URL);

        new Actions(getDriver())
                .contextClick(scrollIntoView(getDriver().findElement(By.id("rightClickBtn"))))
                .perform();

        Assert.assertEquals(
                getDriver().findElement(By.id("rightClickMessage")).getText(),
                "You have done a right click",
                "Right click attempt failed.");
    }

    @Test
    public void testDynamicClickButton() {
        getDriver().get(BUTTONS_URL);

        scrollIntoView(getDriver().findElement(By.xpath("//*[@id='rightClickBtn']/../following-sibling::div/button")))
                .click();

        Assert.assertEquals(
                getDriver().findElement(By.id("dynamicClickMessage")).getText(),
                "You have done a dynamic click",
                "Right click attempt failed.");
    }

    @DataProvider(name = "windowDataProvider")
    public Object[][] windowDataProvider() {
        return new Object[][] {{"tabButton"}, {"windowButton"}};
    }

    @Test(dataProvider = "windowDataProvider")
    public void testBrowserWindowOpen(String buttonId) {
        getDriver().get(BROWSER_WINDOWS_URL);

        getDriver().findElement(By.id(buttonId)).click();

        getWait5().until(ExpectedConditions.numberOfWindowsToBe(2));

        String original = getDriver().getWindowHandle();
        for (String handle : getDriver().getWindowHandles()) {
            if (!handle.equals(original)) {
                getDriver().switchTo().window(handle);
                break;
            }
        }

        Assert.assertEquals(
                getDriver().findElement(By.id("sampleHeading")).getText(),
                "This is a sample page");
    }

    @Test
    public void testRadioButton() {
        getDriver().get("https://demoqa.com/radio-button");

        getDriver().findElement(By.xpath("//*[@for='impressiveRadio']")).click();

        Assert.assertTrue(
                getDriver().findElement(By.className("text-success")).isDisplayed(),
                "radiobutton is not selected");
    }

    @Test
    public void testPracticeFillForm() {
        getDriver().get("https://demoqa.com/automation-practice-form");

        scrollIntoView(getDriver().findElement(By.id("firstName"))).sendKeys("Irina");
        scrollIntoView(getDriver().findElement(By.id("lastName"))).sendKeys("Kuperman");
        scrollIntoView(getDriver().findElement(By.id("userEmail"))).sendKeys("ama@ama.com");
        scrollIntoView(getDriver().findElement(By.cssSelector("[for='gender-radio-2'"))).click();
        scrollIntoView(getDriver().findElement(By.id("userNumber"))).sendKeys("1234567890");

        scrollIntoView(getDriver().findElement(By.id("submit"))).click();

        Assert.assertEquals(
                getDriver().findElement(By.id("example-modal-sizes-title-lg")).getText(),
                "Thanks for submitting the form");
    }

    @Test
    public void test2GetTextLinks() {
        getDriver().get("https://demoqa.com/links");

        scrollIntoView(getDriver().findElement(By.id("no-content"))).click();

        Assert.assertEquals(
                getDriver().findElement(By.id("linkResponse")).getText(),
                "Link has responded with staus 204 and status text No Content",
                "wrong answer");
    }
}
package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class WarningItemTest extends BaseTest {

    public static final String WARNING_BUTTON = "//a[@id='visible-sec-am-button']";

    public void clickOn(String xPath) {
        getDriver().findElement(By.xpath(xPath)).click();
    }

    @Test
    public void testWarningPopUp() {
        clickOn(WARNING_BUTTON);
        WebElement warningSection = getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@role='alert']")));

        Assert.assertTrue(warningSection.isDisplayed());
    }

    @Test
    public void testConfigurePage() {
        clickOn(WARNING_BUTTON);
        clickOn("//button[@name='configure']");
        WebElement securityPage = getDriver().findElement(By.xpath("//h1[text()='Security']"));

        Assert.assertEquals("Security", securityPage.getText());
    }

    @Test
    public void testManagePage() {
        clickOn(WARNING_BUTTON);
        clickOn("//a[text()='Manage Jenkins']");

        Assert.assertTrue(getDriver().getCurrentUrl().contains("/manage/"));
    }

}

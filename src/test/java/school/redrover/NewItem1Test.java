package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

public class NewItem1Test extends BaseTest {

    @Test
    public void testNewItemWithoutType() {
        getDriver().findElement(
                By.xpath("//a[@href='newJob']"))
                .click();
        getDriver().findElement(
                By.cssSelector("div.add-item-name > input#name"))
                .sendKeys("ProjectWithoutType");

        Assert.assertFalse(getDriver().findElement(
                By.cssSelector("div.btn-decorator > button#ok-button"))
                .isEnabled());
    }
    @Test
    public void testNewItemUnsafeCharInName() {
        String unsafeSymbol = "$";
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofMillis(1000));

        getDriver().findElement(
                By.xpath("//a[@href='newJob']"))
                .click();
        getDriver().findElement(
                By.cssSelector("div.add-item-name > input#name"))
                .sendKeys(unsafeSymbol);
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("div#itemname-invalid")));

        WebElement validationMessage = getDriver().findElement(
                By.cssSelector("div#itemname-invalid"));

        Assert.assertEquals((validationMessage).getText(),
                String.format("» ‘%s’ is an unsafe character", unsafeSymbol));
        assert (Color.fromString((validationMessage).getCssValue("color"))
                .asHex().equals("#ff0000"));
    }

}

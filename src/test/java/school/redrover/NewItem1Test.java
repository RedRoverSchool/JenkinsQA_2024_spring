package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NewItem1Test extends BaseTest {

    @Test
    public void testNewItemWithoutType() {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.cssSelector("div.add-item-name > input#name")).sendKeys("ProjectWithoutType");

        Assert.assertFalse(getDriver().findElement(By.cssSelector("div.btn-decorator > button#ok-button")).isEnabled());
    }
    @Test
    public void testTextMessageNewItemUnsafeCharInName() {
        String unsafeSymbol = "#";

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.cssSelector("div.add-item-name > input#name")).sendKeys(unsafeSymbol);

        WebElement validationMessage = getDriver().findElement(By.cssSelector("div#itemname-invalid"));
        Assert.assertEquals((validationMessage).getText(), String.format("» ‘%s’ is an unsafe character", unsafeSymbol));
    }
    @Test
    public void testColorMessageNewItemUnsafeCharInName() {
        String unsafeSymbol = "#";

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.cssSelector("div.add-item-name > input#name")).sendKeys(unsafeSymbol);

        WebElement validationMessage = getDriver().findElement(By.cssSelector("div#itemname-invalid"));

        assert (Color.fromString((validationMessage).getCssValue("color")).asHex().equals("#ff0000"));
    }
}

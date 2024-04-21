package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import java.time.Duration;

public class NewItemValidationTest extends BaseTest {

    @DataProvider(name = "unsafeCharacters")
    public Object [][] unsafeCharacters() {
        return new Object[][]{
                {"!"}, {"#"}, {"$"}, {"%"}, {"&"}, {"*"}, {"/"}, {";"}, {":"},
                {">"}, {"<"}, {"?"}, {"@"}, {"["}, {"\\"}, {"]"}, {"^"}, {"|"}
        };
    }

    @Test(dataProvider = "unsafeCharacters")
    public void testCreateNewItemWithUnsafeCharacterInName(String x) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));

        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys(x);
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("itemname-invalid")));

        Assert.assertEquals(errorMessage.getText(),"» ‘" + x + "’ is an unsafe character");
        Assert.assertEquals(errorMessage.getCssValue("color"), "rgba(255, 0, 0, 1)");
    }
}

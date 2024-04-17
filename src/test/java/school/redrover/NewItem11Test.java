package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class NewItem11Test extends BaseTest {

    @DataProvider(name = "unsafeCharactersProvider")
    public Object[][] unsafeCharactersProvider() {
        return new Object[][]{
                {"!"}, {"#"}, {"$"}, {"%"}, {"&"}, {"*"}, {"/"}, {";"},
                {">"}, {"<"}, {"?"}, {"@"}, {"["}, {"\\"}, {"]"}, {"^"}, {"|"}
        };
    }

    @Test(dataProvider = "unsafeCharactersProvider")
    public void testInvalidValuesForProjectNameInput(String x) {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys(x);

        Assert.assertEquals(
                getDriver().findElement(By.id("itemname-invalid")).getText(),
                "» ‘" + x + "’ is an unsafe character");
    }

    @Test
    public void testEmptyProjectNameInputField() {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.cssSelector("[class *= 'MatrixProject']")).click();

        WebElement errorMessage = getDriver().findElement(By.id("itemname-required"));

        Assert.assertTrue(
                errorMessage.getCssValue("color").equals("rgba(255, 0, 0, 1)") &&
                errorMessage.getText().equals("» This field cannot be empty, please enter a valid name"));
    }
}
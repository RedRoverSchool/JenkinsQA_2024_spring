package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class NewItem7 extends BaseTest {

    @DataProvider(name = "unsafeCharactersProvider")
    public Object[][] unsafeCharactersProvider() {
        return new Object[][]{
                {"!"}, {"#"}, {"$"}, {"%"}, {"&"}, {"*"}, {"/"}, {";"},
                {">"}, {"<"}, {"?"}, {"@"}, {"["}, {"\\"}, {"]"}, {"^"}, {"|"}
        };
    }

    @Test(dataProvider = "unsafeCharactersProvider")
    public void testInvalidValuesForProjectNameInput(String x) {
        getDriver().findElement(By.cssSelector("[href $= 'newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(x);

        Assert.assertEquals(
                getDriver().findElement(By.id("itemname-invalid")).getText(),
                "» ‘" + x + "’ is an unsafe character");
    }
}
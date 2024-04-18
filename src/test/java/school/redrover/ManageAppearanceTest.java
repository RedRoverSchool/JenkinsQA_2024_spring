package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import static school.redrover.runner.TestUtils.*;

public class ManageAppearanceTest extends BaseTest {

    @Test
    public void testAppearanceQuantityOfThemesViaDashboardDropDown() {
        WebElement dashboard = getDriver().findElement(By.linkText("Dashboard"));

        new Actions(getDriver())
                .moveToElement(dashboard)
                .pause(1000)
                .moveToElement(dashboard.findElement(By.cssSelector("[class$='chevron']")))
                .scrollToElement(dashboard.findElement(By.cssSelector("[class$='chevron']")))
                .click()
                .pause(1500)
                .perform();

          getDriver().findElement(By.cssSelector("[class='tippy-box'] [href='/manage']")).click();
          getDriver().findElement(By.cssSelector("[href=\"appearance\"]")).click();

//          sleep(this, 3);

//        new Actions(getDriver())
//                .moveToElement(getDriver().findElement(By.cssSelector("[class='tippy-box'] [href='/manage']")))
//                .sendKeys(Keys.ARROW_RIGHT, Keys.ARROW_DOWN, Keys.ARROW_DOWN, Keys.ARROW_DOWN, Keys.ARROW_DOWN,
//                        Keys.ARROW_DOWN, Keys.ARROW_DOWN, Keys.ENTER)
//                .perform();
//
        Assert.assertEquals(getDriver().findElements(By.className("app-theme-picker__item")).size(), 3);

    }
}

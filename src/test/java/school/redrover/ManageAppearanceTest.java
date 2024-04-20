package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class ManageAppearanceTest extends BaseTest{

        @Test
        public void testAppearanceQuantityOfThemesViaDashboardDropDown() {
            WebElement dashboard = getDriver().findElement(By.linkText("Dashboard"));

            new Actions(getDriver())
                    .moveToElement(dashboard)
                    .pause(1000)
//                .moveToElement(dashboard.findElement(By.cssSelector("[class$='chevron']")))
                    .scrollToElement(dashboard.findElement(By.cssSelector("[class$='chevron']")))
                    .click(dashboard.findElement(By.cssSelector("[class$='chevron']")))
                    .perform();

            TestUtils.sleep(this, 2);
            getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='tippy-box'] [href='/manage']"))).click();
            getDriver().findElement(By.cssSelector("[href=\"appearance\"]")).click();

//        new Actions(getDriver())
//                .moveToElement(getDriver().findElement(By.cssSelector("[class='tippy-box'] [href='/manage']")))
//                .sendKeys(Keys.ARROW_RIGHT, Keys.ARROW_DOWN, Keys.ARROW_DOWN, Keys.ARROW_DOWN, Keys.ARROW_DOWN,
//                        Keys.ARROW_DOWN, Keys.ARROW_DOWN, Keys.ENTER)
//                .perform();
//
            Assert.assertEquals(getDriver().findElements(By.className("app-theme-picker__item")).size(), 3);
        }
    }
package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class ManageAppearanceTest extends BaseTest{

    void goToManageAppearance() {
        getDriver().findElement(By.linkText("Manage Jenkins")).click();
        getDriver().findElement(By.cssSelector("[href=\"appearance\"]")).click();
    }

        @Test
        public void testAppearanceQuantityOfThemesViaDashboardDropDown() {
            WebElement dashboard = getDriver().findElement(By.cssSelector("div#breadcrumbBar a[href = '/']"));
            WebElement chevron = dashboard.findElement(By.cssSelector("[class$='chevron']"));

            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].dispatchEvent(new Event('mouseenter'));", chevron);
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].dispatchEvent(new Event('click'));", chevron);

            getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='tippy-box'] [href='/manage']"))).click();
            getDriver().findElement(By.cssSelector("[href=\"appearance\"]")).click();

            Assert.assertEquals(getDriver().findElements(By.className("app-theme-picker__item")).size(), 3);
        }

    @Test
        public void testDarkThemeSwitchNotification() {
            goToManageAppearance();

            getDriver().findElement(By.cssSelector("[for='radio-block-0']")).click();
            getDriver().findElement(By.name("Apply")).click();

            Assert.assertEquals(
                    getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.id("notification-bar"))).getText(),
                    "Saved");
        }

        @Test
        public void testDarkThemeSwitchColor() {
            goToManageAppearance();

            getDriver().findElement(By.cssSelector("[for='radio-block-0']")).click();
            getDriver().findElement(By.name("Apply")).click();

            Assert.assertEquals(
                    getDriver().findElement(By.tagName("body")).getCssValue("background-color"),
                    "rgba(31, 31, 35, 1)",
                    "The background color doesn't match the theme");
        }
    }
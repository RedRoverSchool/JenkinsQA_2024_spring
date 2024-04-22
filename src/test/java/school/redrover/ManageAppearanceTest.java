package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
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
            WebElement dashboard = getDriver().findElement(By.cssSelector("div#breadcrumbBar a[href = '/']"));
            WebElement chevron = dashboard.findElement(By.cssSelector("[class$='chevron']"));

            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].dispatchEvent(new Event('mouseenter'));", chevron);
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].dispatchEvent(new Event('click'));", chevron);

            getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='tippy-box'] [href='/manage']"))).click();
            getDriver().findElement(By.cssSelector("[href=\"appearance\"]")).click();

            Assert.assertEquals(getDriver().findElements(By.className("app-theme-picker__item")).size(), 3);
        }
    }
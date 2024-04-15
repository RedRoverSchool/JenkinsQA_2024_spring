package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class AppearanceThemeTest extends BaseTest {
    @Test
    public void testAppearanceThemesDark () {
        getDriver().findElement(By.cssSelector("[href='/manage']")).click();
        getDriver().findElement(By.cssSelector("[href='appearance']")).click();
        getDriver().findElement(By.cssSelector("[for='radio-block-0']")).click();

        getDriver().findElement(By.cssSelector("[class='attach-previous ']")).click();
        getDriver().findElement(By.cssSelector("[name='Apply']")).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("html[data-theme]")).
                getAttribute("data-theme"), "dark");
    }
    @Test
    public void testAppearanceThemesDefault () {
        getDriver().findElement(By.cssSelector("[href='/manage']")).click();
        getDriver().findElement(By.cssSelector("[href='appearance']")).click();
        getDriver().findElement(By.cssSelector("[for='radio-block-2']")).click();

        getDriver().findElement(By.cssSelector("[class='attach-previous ']")).click();
        getDriver().findElement(By.cssSelector("[name='Apply']")).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("html[data-theme]")).
                getAttribute("data-theme"), "none");
    }
    @Test
    public void testAppearanceThemesSystem () {
        getDriver().findElement(By.cssSelector("[href='/manage']")).click();
        getDriver().findElement(By.cssSelector("[href='appearance']")).click();
        getDriver().findElement(By.cssSelector("[for='radio-block-1']")).click();

        getDriver().findElement(By.cssSelector("[class='attach-previous ']")).click();
        getDriver().findElement(By.cssSelector("[name='Apply']")).click();

        Assert.assertTrue(getDriver().findElement(By.cssSelector("html[data-theme]")).
                getAttribute("data-theme").contains("system"));
    }
}

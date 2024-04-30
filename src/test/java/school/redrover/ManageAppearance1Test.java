package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class ManageAppearance1Test extends BaseTest {

    private void goToAppearance() {
        getDriver().findElement(By.cssSelector("[href='/manage']")).click();
        getDriver().findElement(By.cssSelector("[href='appearance']")).click();
    }

    private void clickSaveButton() {
        getDriver().findElement(By.name("Submit"));
    }

    private String getThemeAttribute() {
        return getDriver().findElement(By.cssSelector("html[data-theme]"))
                .getAttribute("data-theme");
    }

    @Test
    public void testDarkThemeApply() {
        goToAppearance();
        getDriver().findElement(By.cssSelector("[for='radio-block-0']")).click();
        clickSaveButton();

        Assert.assertEquals(getThemeAttribute(), "dark");
    }

    @Test
    public void testDefaultThemeApply() {
        goToAppearance();
        getDriver().findElement(By.cssSelector("[for='radio-block-2']")).click();
        clickSaveButton();

        Assert.assertEquals(getThemeAttribute(), "none");
    }

    @Test
    public void testSystemThemeApply() {
        goToAppearance();
        getDriver().findElement(By.cssSelector("[for='radio-block-1']")).click();
        clickSaveButton();

        Assert.assertTrue(getThemeAttribute().contains("system"));
    }
}
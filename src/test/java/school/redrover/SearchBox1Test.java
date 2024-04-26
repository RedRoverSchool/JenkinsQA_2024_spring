package school.redrover;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class SearchBox1Test extends BaseTest {
    private WebElement checkbox() {
        return getDriver().findElement(By.cssSelector("[name^='insensitive']"));
    }

    private static final By SEARCH_RESULT = By.cssSelector("[class$='__content']");

    private static final String EXPECTED_RESULT = "Log Recorders";

    private Actions getActions() {
        return new Actions(getDriver());
    }

    private void uppercaseInput() {
        getDriver().findElement(By.id("search-box")).sendKeys("Log");
        getDriver().findElement(By.id("search-box")).sendKeys(Keys.ENTER);
    }

    public void lowercaseInput() {
        getDriver().findElement(By.id("search-box")).sendKeys("log");
        getDriver().findElement(By.id("search-box")).sendKeys(Keys.ENTER);
    }

    private void goToConfigure() {
        getDriver().findElement(By.xpath("//div[3]/a[1]")).click();
        getDriver().findElement(By.cssSelector("[href$='configure']")).click();
    }

    private void flagInsensitiveCheckbox() {
        getActions().scrollToElement(getDriver().findElement(By.xpath("//div[3]/div[2]/span")))
                .sendKeys(Keys.TAB, Keys.SPACE)
                .perform();
        getDriver().findElement(By.cssSelector("[class$='primary ']")).click();
    }

    private void turnInsensitiveSearch(boolean flag) {
        goToConfigure();
        boolean isCheckboxSelected = checkbox().isSelected();
        if ((flag && !isCheckboxSelected) || (!flag && isCheckboxSelected)) {
            flagInsensitiveCheckbox();
        }
    }

    @Test
    public void testCaseSensitiveOnUppercaseInput() {
        turnInsensitiveSearch(false);
        uppercaseInput();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("[class='error']")).getText(),
                "Nothing seems to match.");
    }
    @Test
    public void testCaseSensitiveOnLowercaseInput() {
        turnInsensitiveSearch(false);
        lowercaseInput();

        Assert.assertEquals(getDriver().findElement(SEARCH_RESULT).getText(),
                EXPECTED_RESULT);
    }
    @Test
    public void testCaseSensitiveOffLowercaseInput() {
        turnInsensitiveSearch(true);
        lowercaseInput();

        Assert.assertEquals(getDriver().findElement(SEARCH_RESULT).getText(),
                EXPECTED_RESULT);
    }

    @Test
    public void testCaseSensitiveOffUppercaseInput() {
        turnInsensitiveSearch(true);
        uppercaseInput();

        Assert.assertEquals(getDriver().findElement(SEARCH_RESULT).getText(),
                EXPECTED_RESULT);
    }
}
package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.List;

public class Nodes0Test extends BaseTest {

    @Test
    public void testTooltipConfigureNodePage() {
        List<String> expectedList = List.of(
        "Help for feature: Architecture",
        "Help for feature: Clock Difference",
        "Help for feature: Free Disk Space",
        "Help for feature: Don&#039;t mark agents temporarily offline",
        "Help for feature: Free Space Threshold",
        "Help for feature: Free Space Warning Threshold",
        "Help for feature: Free Swap Space",
        "Help for feature: Free Temp Space",
        "Help for feature: Don&#039;t mark agents temporarily offline",
        "Help for feature: Free Space Threshold",
        "Help for feature: Free Space Warning Threshold",
        "Help for feature: Response Time",
        "Help for feature: Don&#039;t mark agents temporarily offline"
        );
        List<String> actualList = new ArrayList<>();

        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[href='/computer/']"))).click();
        getDriver().findElement(By.cssSelector("[href='configure']")).click();

        List<WebElement> elements = getDriver().findElements(By.cssSelector("[tooltip^='Help for feature']"));
        for (WebElement element : elements) {

            ((JavascriptExecutor) getDriver()).executeScript("return arguments[0].scrollIntoView({block:'center'});", element);

            new Actions(getDriver())
                    .pause(500)
                    .moveToElement(element)
                    .pause(500)
                    .perform();

            actualList.add(getDriver().findElement(By.cssSelector("[class='tippy-content']")).getText());
        }
        Assert.assertEquals(actualList, expectedList);
    }
}

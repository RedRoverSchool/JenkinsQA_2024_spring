package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class AqaGroupDynamicButtonsTest extends AqaGroupBaseTest {
    @Test
    public void testSimpleDynamicButtons() {
        getDriver().get("https://testpages.eviltester.com/styled/dynamic-buttons-simple.html");

        getDriver().findElement(By.id("button00")).click();
        getDriver().findElement(By.id("button01")).click();
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("button02")));
        getDriver().findElement(By.id("button02")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("button03")));
        getDriver().findElement(By.id("button03")).click();

        Assert.assertEquals(getDriver().findElement(By.id("buttonmessage")).getText(), "All Buttons Clicked", "Not All Buttons Clicked");
    }

    @Test
    public void testXHTTPMessages() {
        getDriver().get("https://testpages.eviltester.com/styled/sync/xhttp-messages.html");

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(60));
        wait.until(ExpectedConditions.textToBePresentInElement(getDriver().findElement(By.id("messagesstatus")), "Message Count: 0 : 0"));

        Assert.assertEquals(getDriver().findElement(By.id("messagesstatus")).getText(), "Message Count: 0 : 0", "message count is not zero");
    }
}

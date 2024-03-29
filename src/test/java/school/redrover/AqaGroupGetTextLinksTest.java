package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;


public class AqaGroupGetTextLinksTest extends AqaGroupBaseTest {

    @Ignore
    @Test
    public void test2GetTextLinks() {
        getDriver().get("https://demoqa.com/links");

        getDriver().findElement(By.id("no-content")).click();

        Assert.assertEquals(
                getDriver().findElement(By.id("linkResponse")).getText(),
                "Link has responded with staus 204 and status text No Content",
                "wrong answer");
    }
}
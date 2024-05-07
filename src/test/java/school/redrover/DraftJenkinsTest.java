package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.DraftPage;
import school.redrover.runner.BaseTest;

import java.util.List;

public class DraftJenkinsTest extends BaseTest {

    @Test
    public void testElementPeople() {
        new DraftPage(getDriver())
                .ElementPeople();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@class='jenkins-app-bar__content']")).getText(), "People");
    }

    @Test
    public void testElementWelcome() {
        new DraftPage(getDriver())
                .ElementWelcome();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1[contains(.,'Welcome to Jenkins!')]")).getText(), "Welcome to Jenkins!");
    }


}

package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject5Test extends BaseTest {

    private static final String PROJECT_NAME = "ProjectName";

    private static final By PROJECT_LINK = By.xpath("//*[@href='job/" + PROJECT_NAME + "/']/span");

    @Test
    public void testCheckFreestyleProjectNameOnTheDashboard() {
        getDriver().findElement(By.xpath("//*[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//*[@class='hudson_model_FreeStyleProject']")).click();
        getDriver().findElement(By.id("name")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//*[@name='Submit']")).click();
        getDriver().findElement(By.id("jenkins-home-link")).click();

        Assert.assertEquals(getDriver().findElement(PROJECT_LINK).getText(), PROJECT_NAME);

//        post condition
        getDriver().findElement(PROJECT_LINK).click();
        getDriver().findElement(By.xpath("//*[@data-url='/job/" + PROJECT_NAME + "/doDelete']")).click();
        getDriver().findElement(By.xpath("//*[@data-id='ok']")).click();

    }
}

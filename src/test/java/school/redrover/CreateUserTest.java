package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class CreateUserTest extends BaseTest {

    @Test
    public void testUserCreationWithoutEmail() {
        getDriver().findElement(By.xpath("//*[@href='/manage']")).click();
        getDriver().findElement(By.xpath("//dt[text() ='Users']")).click();
        getDriver().findElement(By.xpath("//a[@href = 'addUser']")).click();
        getDriver().findElement(By.xpath("//*[@id='username']")).sendKeys("user1");
        getDriver().findElement(By.xpath("//*[@name='password1']")).sendKeys("user1");
        getDriver().findElement(By.xpath("//*[@name='password2']")).sendKeys("user1");
        getDriver().findElement(By.xpath("//*[@name='Submit']")).click();

        String actualResult = getDriver().findElement(By.xpath("//*[@class='error jenkins-!-margin-bottom-2']")).getText();
        String expectedResult = "Invalid e-mail address";

        Assert.assertEquals(actualResult, expectedResult);
    }
}






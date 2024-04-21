package school.redrover;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class AddDescriptionFreestyleProjectTest extends BaseTest {
    private void createFreestyleProject() {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.name("name")).sendKeys("freestyleProjectName");
        getDriver().findElement(By.xpath("//label/span[text() ='Freestyle project']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
    }
    private void openDashboard(){
        getDriver().findElement(By.id("jenkins-name-icon")).click();
    }

    @Ignore
    @Test
    public void testAddDescriptionFreestyleProject() throws InterruptedException {

        final String expectedAddedDescription = "It is a new description";
        final String expectedMessage = "Edit description";

        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).sendKeys(expectedAddedDescription);
        getDriver().findElement(By.cssSelector("button[name='Submit']")).click();

        final String actualAddedDescription = getDriver().findElement(By.cssSelector("div[id='description'] div:nth-child(1)")).getText();
        final String actualMessage =  getDriver().findElement(By.cssSelector("#description-link")).getText();

        Assert.assertEquals(actualAddedDescription, expectedAddedDescription);
        Assert.assertEquals(actualMessage, expectedMessage);
    }
}

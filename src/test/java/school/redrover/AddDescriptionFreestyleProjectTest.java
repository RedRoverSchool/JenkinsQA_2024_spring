package school.redrover;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

public class AddDescriptionFreestyleProjectTest extends BaseTest {
    private void createFreestyleProject() {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.name("name")).sendKeys("freestyleProjectName");
        getDriver().findElement(By.xpath("//label/span[text() ='Freestyle project']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
    }

    private void openDashboard() {
        getDriver().findElement(By.id("jenkins-name-icon")).click();
    }


    @Test
    public void testAddDescriptionFreestyleProject() throws InterruptedException {

        final String expectedAddedDescription = "It is a new description";
        final String expectedMessage = "Edit description";

        HomePage homePage = new HomePage(getDriver())
                .clickAddDescriptionButton()
                .descriptionInput(expectedAddedDescription)
                .clickSaveButton();

        Assert.assertEquals(homePage.actualAddedDescriptionGetText(), expectedAddedDescription);
        Assert.assertEquals(homePage.descriptionButtonGetText(), expectedMessage);
    }
}

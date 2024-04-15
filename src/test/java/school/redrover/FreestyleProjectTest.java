package school.redrover;

import org.openqa.selenium.*;
import org.testng.*;
import org.testng.annotations.*;
import school.redrover.runner.*;

public class FreestyleProjectTest extends BaseTest {

    private static final String FREESTYLE_PROJECT_NAME = "Freestyle Project Name";
    private static final String NEW_FREESTYLE_PROJECT_NAME = "New Freestyle Project Name";

    private WebElement okButton(){
        return getDriver().findElement(By.id("ok-button"));
    }

    private WebElement submitButton(){
        return getDriver().findElement(By.xpath("//button[@name = 'Submit']"));
    }

    private WebElement jenkinsHomeLink(){
        return getDriver().findElement(By.id("jenkins-home-link"));
    }

     @Test
    public void testCreateFreestyleProjectJob() {
        String expectedHeading = "My First Freestyle project";

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(expectedHeading);
        getDriver().findElement(By.xpath("//li[@class='hudson_model_FreeStyleProject']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
        getDriver().findElement(By.id("jenkins-home-link")).click();

        String actualHeading = getDriver()
                .findElement(By.xpath("//table//a[@href='job/My%20First%20Freestyle%20project/']")).getText();

        Assert.assertEquals(actualHeading, expectedHeading);
    }

    @Test
    public void testCreateFreestyleProject() {
        final String ExpectedProjectName = "Vika Freestyle project";

        getDriver().findElement(By.xpath("//*[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(ExpectedProjectName);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        String newName = getDriver().findElement(By.tagName("h1")).getText();

        Assert.assertEquals(newName, ExpectedProjectName);
    }
    @Test
    public void testRenameFreestyleProjectFromConfigurationPage() {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input']"))
                .sendKeys(FREESTYLE_PROJECT_NAME);
        getDriver().findElement(By.xpath("//span[contains(text(),  'Freestyle project')]")).click();
        okButton().click();
        submitButton().click();
        jenkinsHomeLink().click();

        getDriver().findElement(By.xpath("//a[@class= 'jenkins-table__link model-link inside']")).click();
        getDriver().findElement(By.xpath("//*[@id='tasks']/div[7]/span")).click();
        getDriver().findElement(By.xpath("//input[@checkdependson='newName']")).clear();
        getDriver().findElement(By.xpath("//input[@checkdependson='newName']")).sendKeys(NEW_FREESTYLE_PROJECT_NAME);
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();

        String resultHeader = getDriver().findElement(By.xpath("//h1"))
                .getText();

        jenkinsHomeLink().click();

        String resultName = getDriver().findElement(By.xpath("//a[@class= 'jenkins-table__link model-link inside']"))
                .getText();

        Assert.assertEquals(resultHeader, NEW_FREESTYLE_PROJECT_NAME);
        Assert.assertEquals(resultName, NEW_FREESTYLE_PROJECT_NAME);
    }
}

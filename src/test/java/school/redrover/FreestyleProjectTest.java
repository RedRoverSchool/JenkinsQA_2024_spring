package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProjectTest extends BaseTest {
    @Test
    public void testFreestyleProjectCreate() {
        String newName = "Project8";
        getDriver().findElement(By.xpath("//*[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(newName);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        WebElement nameOfProject = getDriver().findElement(
                By.xpath("//h1[@class='job-index-headline page-headline']"));

        String actualResult = nameOfProject.getText();

        Assert.assertEquals(actualResult, newName);
    }

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
    @Test
    public void testCreatingFreestyleInvalidChar() {

        String[] invalidCharacters = {"!", "@", "#", "$", "%", "^", "&", "*", "?", "|", "/", "["};

        getDriver().findElement(By.xpath("//*[@href='/view/all/newJob']")).click();

        for (String invalidChar : invalidCharacters) {
            getDriver().findElement(By.xpath("//*[@class='jenkins-input']")).clear();
            getDriver().findElement(By.xpath("//*[@class='jenkins-input']")).sendKeys(invalidChar);

            String actualResult = getDriver().findElement(By.xpath("//div[@id='itemname-invalid']"))
                    .getText();
            String expectedResult = "» ‘" + invalidChar + "’ is an unsafe character";
            Assert.assertEquals(actualResult, expectedResult);

            boolean okButton = getDriver().findElement(By.xpath("//button[@type='submit']")).isEnabled();
            Assert.assertFalse(okButton);
        }
    }
}

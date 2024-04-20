package school.redrover;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.*;
import org.testng.annotations.*;
import school.redrover.runner.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class FreestyleProjectTest extends BaseTest {
    private static final String FREESTYLE_PROJECT_NAME = "Freestyle Project Name";
    private static final String NEW_FREESTYLE_PROJECT_NAME = "New Freestyle Project Name";

    private WebElement okButton() {
        return getDriver().findElement(By.id("ok-button"));
    }

    private WebElement submitButton() {
        return getDriver().findElement(By.xpath("//button[@name = 'Submit']"));
    }

    private WebElement jenkinsHomeLink() {
        return getDriver().findElement(By.id("jenkins-home-link"));
    }

    public void freestyleProjectCreate(String newName) {
        WebDriverWait wait5 = new WebDriverWait(getDriver(), Duration.ofSeconds(5));

        getDriver().findElement(By.xpath("//*[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(newName);
        wait5.until(ExpectedConditions.elementToBeClickable(getDriver().findElement(
                By.className("hudson_model_FreeStyleProject")))).click();
        wait5.until(ExpectedConditions.elementToBeClickable(getDriver().findElement(By.id("ok-button")))).click();
        submitButton().click();
    }

    public void createFolder(String folderName) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(folderName);
        getDriver().findElement(By.xpath("//span[@class='label'][text() = 'Folder']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
        submitButton().click();
    }

    public void createNewItemFromOtherExisting(String newProjectName, String existingProjectName) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(newProjectName);
        getDriver().findElement(By.xpath("//input[@name='from']")).sendKeys(existingProjectName);
        okButton().click();
        submitButton().click();
    }

    public void openElementDropdown(WebElement element) {
        JavascriptExecutor openElementDropdown = (JavascriptExecutor) getDriver();

        openElementDropdown.executeScript("arguments[0].dispatchEvent(new Event('mouseenter'));", element);
        openElementDropdown.executeScript("arguments[0].dispatchEvent(new Event('click'));", element);
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
        freestyleProjectCreate(FREESTYLE_PROJECT_NAME);
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
    public void testCreateFreestyleProjectInvalidChar() {

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

    @Test
    public void testRenameProject() {

        freestyleProjectCreate(FREESTYLE_PROJECT_NAME);

        getDriver().findElement(By.xpath("//li/a[@href='/']")).click();
        getDriver().findElement(By.xpath("//a[@class='jenkins-table__link model-link inside']")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/" +
                FREESTYLE_PROJECT_NAME.replaceAll(" ", "%20") + "/confirm-rename']")).click();
        getDriver().findElement(By.xpath("//input[@checkdependson='newName']")).clear();
        getDriver().findElement(By.xpath("//input[@checkdependson='newName']"))
                .sendKeys(NEW_FREESTYLE_PROJECT_NAME);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        getDriver().findElement(By.xpath("//li/a[@href='/']")).click();

        String expectedResult = NEW_FREESTYLE_PROJECT_NAME;
        String actualResult = getDriver().findElement
                (By.xpath("//a[@class='jenkins-table__link model-link inside']")).getText();

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Ignore
    @Test
    public void testFreestyleProjectCreate() {
        freestyleProjectCreate(FREESTYLE_PROJECT_NAME);

        WebElement nameOfProject = getDriver().findElement(
                By.xpath("//h1[@class='job-index-headline page-headline']"));

        String actualResult = nameOfProject.getText();

        Assert.assertEquals(actualResult, FREESTYLE_PROJECT_NAME);
    }

    @Ignore
    @Test
    public void testAddDescription() {
        final String projectName = "New Freestyle project";
        final String description = "Text description of the project";

        getDriver().findElement(By.xpath("//*[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement((By.id("description-link"))).click();
        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(description);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertTrue(
                getDriver().findElement(By.xpath("//div[text()='" + description + "']")).isDisplayed(),
                description);
    }

    @Test
    public void testRenameWithEmptyName() {
        freestyleProjectCreate(FREESTYLE_PROJECT_NAME);
        getDriver().findElement(By.id("jenkins-home-link")).click();

        WebElement projectName = getDriver().findElement(
                By.xpath("//span[text()='" + FREESTYLE_PROJECT_NAME + "']/following-sibling::button[@class='jenkins-menu-dropdown-chevron']"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].dispatchEvent(new Event('mouseenter'));", projectName);
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].dispatchEvent(new Event('click'));", projectName);

        getDriver().findElement(By.xpath("//a[contains(@href,'rename')]")).click();

        getDriver().findElement(By.xpath("//input[@name='newName']")).clear();

        getDriver().findElement(By.xpath("//button[contains(text(),'Rename')]")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//p[text()='No name is specified']")).getText(), "No name is specified");
    }

    @Ignore
    @Test
    public void testMoveToFolder() {

        String folderName = "Classic Models";
        String projectName = "Race Cars";

        String expectedResult = "Full project name: " + folderName + "/" + projectName;

        createFolder(folderName);
        jenkinsHomeLink().click();
        freestyleProjectCreate(projectName);
        jenkinsHomeLink().click();

        openElementDropdown(getDriver().findElement(
                By.xpath("//a[@href='job/" + projectName.replaceAll(" ", "%20")
                        + "/']/button[@class='jenkins-menu-dropdown-chevron']")));

        getDriver().findElement(By.xpath("//a[@href='/job/"
                + projectName.replaceAll(" ", "%20") + "/move']")).click();

        getDriver().findElement(By.xpath("//option[@value='/" + folderName + "']")).click();

        submitButton().click();

        String actualResult = getDriver().findElement(By.xpath("//div[@id='main-panel']")).getText();

        Assert.assertTrue(actualResult.contains(expectedResult));
    }

    @Ignore
    @Test
    public void testBuildNowFreestyleProject() {
        freestyleProjectCreate(FREESTYLE_PROJECT_NAME);

        getDriver().findElement(By.xpath("//a[@data-build-success='Build scheduled']")).click();
        String actualResult = getDriver().findElement(By.xpath("//*[@href='/job/"
                + FREESTYLE_PROJECT_NAME.replaceAll(" ", "%20") + "/1/']")).getText();

        Assert.assertEquals(actualResult, "#1");
    }

    @Test
    public void testDeleteFreestyleProjectFromConfigurationPage() {
        freestyleProjectCreate(FREESTYLE_PROJECT_NAME);
        jenkinsHomeLink().click();

        getDriver().findElement(By.xpath("//a[@class= 'jenkins-table__link model-link inside']")).click();
        getDriver().findElement(By.xpath("//*[@id='tasks']/div[6]/span")).click();
        getDriver().findElement(By.xpath("//button[@data-id = 'ok']")).click();
        String resultHeader = getDriver().findElement(By.xpath("//h1")).getText();

        Assert.assertEquals(resultHeader, "Welcome to Jenkins!");

    }

    @Test
    public void testCopyFromContainer() {

        String oldProjectName1 = "Race Cars";
        String oldProjectName2 = "Race Bikes";
        String newProjectName = "Vintage Cars";

        freestyleProjectCreate(oldProjectName1);
        jenkinsHomeLink().click();

        freestyleProjectCreate(oldProjectName2);
        jenkinsHomeLink().click();

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(newProjectName);
        getDriver().findElement(
                By.xpath("//input[@name='from']")).sendKeys(oldProjectName1.substring(0, 1));

        WebDriverWait wait20 = new WebDriverWait(getDriver(), Duration.ofSeconds(20));

        List<WebElement> elements = wait20.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.xpath("//div[@class='item-copy']//li[not(@style='display: none;')]")));

        List<String> elementsList = new ArrayList<>();
        for (WebElement element : elements) {
            elementsList.add(element.getText());
        }

        Assert.assertTrue(elementsList.contains(oldProjectName1));
    }
}
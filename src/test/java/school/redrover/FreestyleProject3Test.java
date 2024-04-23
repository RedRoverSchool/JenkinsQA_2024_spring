package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;

public class FreestyleProject3Test extends BaseTest {
    private final static String FREESTYLE_PROJECT_NAME = "new Freestyle project";
    private final static String RENAMED_PROJECT_NAME = "old Freestyle project";

    public void clickJenkinsHomePage() {
        getDriver().findElement(By.id("jenkins-home-link")).click();
    }

    private void createFreestyleProject(String projectName) {
        getDriver().findElement(By.xpath("//*[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).clear();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();
    }

    @Test
    public void testCreateFreestyleProject() {

        TestUtils.createJob(this, TestUtils.Job.FREESTYLE, FREESTYLE_PROJECT_NAME);
        getDriver().findElement(By.name("Submit")).click();

        String newProjectName = getDriver().findElement(By.tagName("h1")).getText();

        Assert.assertEquals(newProjectName, FREESTYLE_PROJECT_NAME);
    }

    @Ignore
    @Test
    public void testCreateFreestyleProject2(){

        final String EXPECTED_PROJECT_NAME = "new Freestyle project";

        getDriver().findElement(By.xpath("//*[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(EXPECTED_PROJECT_NAME);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        boolean isJobCreated = getDriver().findElement(By.xpath("//h1[text()='new Freestyle project']")).isDisplayed();
        Assert.assertTrue(isJobCreated, "FreestyleProject is not created.");
    }

    @Test
    public void deleteFreestyleProject() {
        createFreestyleProject(FREESTYLE_PROJECT_NAME);

        getDriver().findElement(By.xpath("//span[text() = '" + FREESTYLE_PROJECT_NAME + "']")).click();
        getDriver().findElement(By.xpath("//div[@id = 'tasks']/div[6]/span")).click();
        getDriver().findElement(By.xpath("//button[@data-id='ok']")).click();

        List<WebElement> projectList = getDriver().findElements(
                By.xpath("//span[text() = '" + FREESTYLE_PROJECT_NAME + "']"));

        Assert.assertTrue(projectList.isEmpty());
    }

    @Test
    public void testRenameFreestyleProjectFromDropdown() {

        createFreestyleProject(FREESTYLE_PROJECT_NAME);
        clickJenkinsHomePage();

        WebElement dropdownChevron  = getDriver().findElement(By.xpath("//span[text()=('" + FREESTYLE_PROJECT_NAME + "')]/following-sibling::button"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].dispatchEvent(new Event('mouseenter'));", dropdownChevron);
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].dispatchEvent(new Event('click'));", dropdownChevron);
        getWait5().until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Rename"))).click();

        WebElement projectNameInputField = getDriver().findElement(By.xpath("//input[@class='jenkins-input validated  ']"));
        projectNameInputField.clear();
        projectNameInputField.sendKeys(RENAMED_PROJECT_NAME);

        getDriver().findElement(By.name("Submit")).click();

        String ActualProjectName = getDriver().findElement(By.tagName("h1")).getText();

        Assert.assertEquals(ActualProjectName, RENAMED_PROJECT_NAME);
    }
}


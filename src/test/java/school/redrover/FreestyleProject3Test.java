package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.model.CreateNewItemPage;
import school.redrover.model.CreateNewViewPage;
import school.redrover.model.FreestyleConfigPage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;

public class FreestyleProject3Test extends BaseTest {
    private final static String FREESTYLE_PROJECT_NAME = "new Freestyle project";
    private final static String RENAMED_PROJECT_NAME = "old Freestyle project";

    public void clickJenkinsLogo() {
        getDriver().findElement(By.id("jenkins-home-link")).click();
    }

       @Test
    public void testCreateFreestyleProject() {

        String projectName = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .getProjectName();

        Assert.assertEquals(projectName, FREESTYLE_PROJECT_NAME);
    }

    @Test
    public void testCreateFreestyleProject2(){

        final String EXPECTED_PROJECT_NAME = "new Freestyle project";

        String newProjectName = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(EXPECTED_PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .getProjectName();

        Assert.assertEquals(newProjectName, EXPECTED_PROJECT_NAME, "FreestyleProject is not created.");
    }

    @Test
    public void deleteFreestyleProject() {
        List<WebElement> projectList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .chooseCreatedFreestyleProject(FREESTYLE_PROJECT_NAME)
                .deleteFreestyleProject()
                .confirmDeleteFreestyleProject()
                .getTheListOfFreestyleProjects(FREESTYLE_PROJECT_NAME);

        Assert.assertTrue(projectList.isEmpty());
    }

    @Test (dependsOnMethods = "testCreateFreestyleProject")
    public void testRenameFreestyleProjectFromDropdown() {

        clickJenkinsLogo();

        WebElement dropdownChevron  = getDriver().findElement(By.xpath("//span[text()=('" + FREESTYLE_PROJECT_NAME + "')]/following-sibling::button"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].dispatchEvent(new Event('mouseenter'));", dropdownChevron);
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].dispatchEvent(new Event('click'));", dropdownChevron);
        getDriver().findElement((By.partialLinkText("Rename"))).click();

        WebElement projectNameInputField = getDriver().findElement(By.xpath("//input[@class='jenkins-input validated  ']"));
        projectNameInputField.clear();
        projectNameInputField.sendKeys(RENAMED_PROJECT_NAME);

        getDriver().findElement(By.name("Submit")).click();

        String ActualProjectName = getDriver().findElement(By.tagName("h1")).getText();

        Assert.assertEquals(ActualProjectName, RENAMED_PROJECT_NAME);
    }
    @Test (dependsOnMethods = "testRenameFreestyleProjectFromDropdown")
    public void testDeleteFreestyleProjectFromDropdown() {

        clickJenkinsLogo();

        WebElement dropdownChevron = getDriver().findElement(By.xpath("//span[text()=('" + RENAMED_PROJECT_NAME + "')]/following-sibling::button"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].dispatchEvent(new Event('mouseenter'));", dropdownChevron);
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].dispatchEvent(new Event('click'));", dropdownChevron);
        getDriver().findElement((By.xpath("/html/body/div[3]/div/div/div/button[2]"))).click();
        getDriver().findElement(By.xpath("//button[@data-id='ok']")).click();

        Assert.assertTrue(getDriver().findElement(TestUtils.EMPTY_STATE_BLOCK).isDisplayed());
    }
}


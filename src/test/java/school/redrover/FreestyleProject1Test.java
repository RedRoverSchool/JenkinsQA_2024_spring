package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;

public class FreestyleProject1Test extends BaseTest {

    final String FREESTYLE_PROJECT_NAME = "Freestyle project";

    private void createFreestyleProject(){
        getDriver().findElement(By.xpath("//div[@id='tasks']/descendant::div[1]")).click();
        getDriver().findElement(By.id("name")).sendKeys(FREESTYLE_PROJECT_NAME);
        getDriver().findElement(
                By.xpath("//div[@id='items']//*[text()='Freestyle project']//ancestor::li")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).submit();
    }

    @Test
    public void testAddFreestyleProject() {
        createFreestyleProject();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//h1[text()='" + FREESTYLE_PROJECT_NAME + "']")).getText(),
                FREESTYLE_PROJECT_NAME);
    }

    @Test
    public void testAddedProjectIsDisplayedOnTheDashboardPanel() {
        createFreestyleProject();

        getDriver().findElement(By.xpath("//*[@id='breadcrumbBar']//a[@class='model-link']")).click();

        List<WebElement> displayedProjects = getDriver().findElements(
                By.xpath("//table[@id='projectstatus']//button/preceding-sibling::span"));

        boolean projectIsDisplayed = false;

        for (WebElement el : displayedProjects) {
            if (el.getText().equals(FREESTYLE_PROJECT_NAME)) {
                projectIsDisplayed = true;
                break;
            }
        }

        Assert.assertTrue(
                projectIsDisplayed,
                "Project with '" + FREESTYLE_PROJECT_NAME + "' name is not in the list");
    }

    @Test
    public void testOpenConfigurePageOfProject(){
        createFreestyleProject();

        getDriver().findElement(
                By.xpath("//*[@id='side-panel']//*[text()='Configure']//ancestor::a")).click();

        Assert.assertTrue(
                getDriver().findElement(By.xpath("//h1[text()='Configure']")).isDisplayed(),
                "Configure page of the project is not opened");
    }
}

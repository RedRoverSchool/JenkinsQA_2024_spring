package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;

public class FreestyleProject1Test extends BaseTest {

    final String FREESTYLE_PROJECT_NAME = "Freestyle project";

    @Test
    public void testAddFreestyleProject() {
        TestUtils.createItem(TestUtils.FREESTYLE_PROJECT, FREESTYLE_PROJECT_NAME, this);

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//h1[text()='" + FREESTYLE_PROJECT_NAME + "']")).getText(),
                FREESTYLE_PROJECT_NAME);
    }

    @Test (dependsOnMethods = "testAddFreestyleProject")
    public void testAddedProjectIsDisplayedOnTheDashboardPanel() {
        TestUtils.goToMainPage(getDriver());

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

    @Test (dependsOnMethods = "testAddFreestyleProject")
    public void testOpenConfigurePageOfProject(){
        getDriver().findElement(By.xpath("//span[text()=('Freestyle project')]")).click();

        getDriver().findElement(
                By.xpath("//*[@id='side-panel']//*[text()='Configure']//ancestor::a")).click();

        Assert.assertTrue(
                getDriver().findElement(By.xpath("//h1[text()='Configure']")).isDisplayed(),
                "Configure page of the project is not opened");
    }
}

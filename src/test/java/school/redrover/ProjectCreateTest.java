package school.redrover;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class ProjectCreateTest extends BaseTest {
    @Test
    public void testCreateFreestyleProject() {
        String text;

        getDriver().findElement(By.xpath(
                "//*[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("Test project");
        getDriver().findElement(By.xpath(
                "//*[@class='hudson_model_FreeStyleProject']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
        text = getDriver().findElement(By.xpath("//h1")).getText();

        Assert.assertEquals(text, "Test project");
    }
    private final WebDriver driver;

    public ProjectCreateTest(WebDriver driver) {
        this.driver = driver;
    }

    public void projectActions(String projectName) {
        projectName = "Freestyle project";
        driver.findElement(By.xpath(
                "//*[@href='/view/all/newJob']")).click();
        driver.findElement(By.id("name")).sendKeys(projectName);
        driver.findElement(By.xpath(
                "//*[@class='hudson_model_FreeStyleProject']")).click();
        driver.findElement(By.id("ok-button")).click();
        driver.findElement(By.name("Submit")).click();
    }

}

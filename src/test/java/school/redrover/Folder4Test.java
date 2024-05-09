package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class Folder4Test extends BaseTest {

    @Test
    public void testCreateNewFolder() {
        getDriver().findElement(By.cssSelector("[href *= 'newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("NewFolder");
        getDriver().findElement(By.cssSelector("[class *= 'plugins_folder_Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        TestUtils.returnToDashBoard(this);

        Assert.assertTrue(getDriver().findElement(By.xpath("//span[text()='NewFolder']")).isDisplayed());
    }

    @Test
    public void testCreateFolder() {
        final String projectName = "This is the folder name";
        Assert.assertTrue(new HomePage(getDriver())
                .openDashboardBreadcrumbsDropdown()
                .clickNewJobFromDashboardBreadcrumbsMenu()
                .createNewItem(projectName, "Folder")
                .clickLogo()
                .isItemExists(projectName));
    }
}

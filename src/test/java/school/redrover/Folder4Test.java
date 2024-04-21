package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
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
        WebElement dashboardElement = getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#breadcrumbBar a[href = '/']")));

        TestUtils.openElementDropdown(this, dashboardElement);

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.tippy-box a[href $= 'newJob']"))).click();
        String projectName = "This is the folder name";
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.id("name"))).sendKeys(projectName);
        getDriver().findElement(By.className(TestUtils.Item.FOLDER)).click();
        getDriver().findElement(By.id("ok-button")).click();

        Assert.assertTrue(
                getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.linkText(projectName))).isDisplayed());
    }
}

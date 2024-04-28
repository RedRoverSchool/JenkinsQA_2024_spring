package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class RenameFreestyleProjectNameTest extends BaseTest {
    private void createFreestyleProject(String projectName) {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.name("name")).sendKeys(projectName);
        getDriver().findElement(By.xpath("//label/span[text() ='Freestyle project']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
    }

    private void openDashboard() {
        getDriver().findElement(By.id("jenkins-name-icon")).click();
    }

    @Test
    public void testRenameFreestyleProjectName() {
        final String expectedProjectName = "ProjectNew";
        createFreestyleProject(expectedProjectName);
        openDashboard();

        Actions actions = new Actions(getDriver());

        WebElement projectLink = getDriver().findElement(By.linkText(expectedProjectName));
        actions.moveToElement(projectLink).perform();

        getWait10().until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//a[@href='job/"+ expectedProjectName
                        + "/']/button[@class='jenkins-menu-dropdown-chevron']")));

        WebElement dropdownArrow = getWait60().until(ExpectedConditions
                .elementToBeClickable(By.xpath("//a[@href='job/"+ expectedProjectName
                + "/']/button[@class='jenkins-menu-dropdown-chevron']")));
        actions.moveToElement(dropdownArrow).perform();
        JavascriptExecutor executor = (JavascriptExecutor)getDriver();
        executor.executeScript("arguments[0].click();", getDriver().findElement(By.xpath("//a[@href='job/"+ expectedProjectName
                + "/']/button[@class='jenkins-menu-dropdown-chevron']")));


//        dropdownArrow.click();

        getWait60().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@data-state='visible']")));
        WebElement renameMenu = getWait60().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-state='visible']"))).findElement(By.linkText("Rename"));
        actions.moveToElement(renameMenu).perform();

        JavascriptExecutor executor1 = (JavascriptExecutor)getDriver();
        executor1.executeScript("arguments[0].click();", getDriver().findElement(By.linkText("Rename")));

        getWait60().until(ExpectedConditions.elementToBeClickable(By.name("Submit")));
        // renameMenu.click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), "Rename Project "+ expectedProjectName);
    }
}

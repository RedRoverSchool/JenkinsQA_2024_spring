package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class PipelineProject7Test extends BaseTest {

    @Test
    public void testCreate() {
        getDriver().findElement(By.linkText("New Item")).click();

        getWait2().until(ExpectedConditions.elementToBeClickable(By.name("name"))).sendKeys("ProjectName");
        getDriver().findElement(By.xpath("//span[text() = 'Pipeline']")).click();
        getDriver().findElement(By.xpath("//button[text() = 'OK']")).click();

        getWait5().until(ExpectedConditions.elementToBeClickable(By.name("Submit"))).click();

        TestUtils.goToMainPage(getDriver());

        Assert.assertTrue(getDriver().findElement(By.linkText("ProjectName")).isDisplayed());
    }
    @Test(dependsOnMethods = "testCreate")
    public void testDelete() {
        By dropdownChevron = By.xpath("//table//button[@class='jenkins-menu-dropdown-chevron']");

        Actions action = new Actions(getDriver());
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table//a[@href='job/" + "ProjectName" + "/']")));
        action.moveToElement(getDriver().findElement(
                By.xpath("//table//a[@href='job/" + "ProjectName" + "/']"))).perform();

        action.moveToElement(getDriver().findElement(dropdownChevron)).perform();
        getWait5().until(ExpectedConditions.elementToBeClickable(dropdownChevron));
        int chevronHeight = getDriver().findElement(dropdownChevron).getSize().getHeight();
        int chevronWidth = getDriver().findElement(dropdownChevron).getSize().getWidth();
        action.moveToElement(getDriver().findElement(dropdownChevron), chevronWidth, chevronHeight).click()
                .perform();

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@href='/job/ProjectName/doDelete']")));
        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@href='/job/ProjectName/doDelete']"))).click();
        getDriver().findElement(By.xpath("//button[@data-id='ok']")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//h1[text()='Welcome to Jenkins!']")).isDisplayed());
    }
}

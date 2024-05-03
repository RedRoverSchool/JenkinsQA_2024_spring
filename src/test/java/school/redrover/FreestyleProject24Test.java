package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class FreestyleProject24Test extends BaseTest {
    private static final String FREESTYLE_NAME = "newFreestyleProject";
    private static final String FOLDER = "NewFolder";
    private static final By SAVE_BUTTON = By.xpath("//button[@formnovalidate]");
    private static final String DESCRIPTION_TEXT = "This project has been added into the folder";

    private void dropDown(By xpath) {
        WebElement dropdownChevron = getDriver().findElement(xpath);
        ((JavascriptExecutor) getDriver()).executeScript(
                "arguments[0].dispatchEvent(new Event('mouseenter'));", dropdownChevron);
        ((JavascriptExecutor) getDriver()).executeScript(
                "arguments[0].dispatchEvent(new Event('click'));", dropdownChevron);
    }

   @Test
   public void testCreateFreestyleProject() {
        getDriver().findElement(By.xpath("//a[@it]")).click();

        getDriver().findElement(By.cssSelector("#name")).sendKeys(FREESTYLE_NAME);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.xpath("//*[@id='ok-button']")).click();

        getDriver().findElement(SAVE_BUTTON).click();

        Assert.assertEquals((getDriver().findElement(By.xpath("//h1")).getText()), FREESTYLE_NAME);
    }

    @Test(dependsOnMethods = "testCreateFreestyleProject")
    public void testAddDescription() {
        getDriver().findElement(By.xpath("//td//a[@href='job/" + FREESTYLE_NAME + "/']")).click();

        getDriver().findElement(By.id("description-link")).click();

        getDriver().findElement(By.xpath("//textarea")).sendKeys(DESCRIPTION_TEXT);
        getDriver().findElement(SAVE_BUTTON).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//div[@id='description']/div")).getText().matches(DESCRIPTION_TEXT));
    }

    @Test(dependsOnMethods = "testEditDescription")
    public void testFreestyleMoveToFolder() {
        TestUtils.createNewJob(this, TestUtils.Job.FOLDER,FOLDER);

        dropDown(By.xpath("(//td//button[@class='jenkins-menu-dropdown-chevron'])[2]"));

        getWait10().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='jenkins-table__link model-link inside model-link--open']"))).click();

        getDriver().findElement(By.xpath("//*[@href='/job/" + FREESTYLE_NAME + "/move']")).click();

        WebElement move = getDriver().findElement(By.name("destination"));
        Select select = new Select(move);
        select.selectByValue("/" + FOLDER);
        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.className("jenkins-breadcrumbs__list-item")).click();

        getDriver().findElement(By.xpath("//td/*[@href]")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//span[text()='" + FREESTYLE_NAME + "']")).getText(),
                FREESTYLE_NAME);
    }

    @Test(dependsOnMethods = "testFreestyleMoveToFolder")
    public void testCheckFreestyleProjectViaBreadcrumb() {
        dropDown(By.xpath("(//li//button[@class='jenkins-menu-dropdown-chevron'])[1]"));

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class = 'jenkins-dropdown__item'][contains(@href, 'views')]"))).click();

        getDriver().findElement(By.xpath("(//li[@class='children'])[2]")).click();

        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class = 'jenkins-dropdown__item'][contains(@href," + FOLDER + ")]"))).click();

        getDriver().findElement(By.xpath("//td//a[@href='job/" + FREESTYLE_NAME + "/']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@class='jenkins-app-bar']")).getText(),
                FREESTYLE_NAME);
    }
}

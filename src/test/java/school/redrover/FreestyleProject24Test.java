package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;

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
       List<String> itemList = new HomePage(getDriver())
               .clickNewItem()
               .setItemName(FREESTYLE_NAME)
               .selectFreestyleAndClickOk()
               .clickSaveButton()
               .clickLogo()
               .getItemList();

       Assert.assertTrue(itemList.contains(FREESTYLE_NAME));
    }

    @Test(dependsOnMethods = "testCreateFreestyleProject")
    public void testAddDescription() {
        getDriver().findElement(By.xpath("//td//a[@href='job/" + FREESTYLE_NAME + "/']")).click();

        getDriver().findElement(By.id("description-link")).click();

        getDriver().findElement(By.xpath("//textarea")).sendKeys(DESCRIPTION_TEXT);
        getDriver().findElement(SAVE_BUTTON).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//div[@id='description']/div")).getText().matches(DESCRIPTION_TEXT));
    }

    @Test(dependsOnMethods = "testAddDescription")
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

    @Test(dependsOnMethods = "testCheckFreestyleProjectViaBreadcrumb")
    public void testDeleteFreestyleProjectViaDropdown() {
        getDriver().findElement(By.xpath("//tr//a[@href='job/NewFolder/']")).click();

        dropDown(By.xpath("//td//button[@class='jenkins-menu-dropdown-chevron']"));

        getDriver().findElement(By.xpath("//button[@class='jenkins-dropdown__item'][contains(@href, 'Delete')]")).click();

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-id='ok']"))).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h2")).getText(), "This folder is empty");
    }
}

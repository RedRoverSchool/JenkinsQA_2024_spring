package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class NewFreestyleProject24Test extends BaseTest {
    private static final By OK_BUTTON = By.xpath("//*[@id='ok-button']");
    private static final By MAIN_PAGE = By.xpath("//a[@it]");
    private static final By SAVE_BUTTON = By.xpath("//button[@formnovalidate]");
    private static final By JENKINS_INPUT = By.cssSelector("#name");
    private static final By JENKINS_DASHBOARD = By.className("jenkins-breadcrumbs__list-item");
    private static final String FREESTYLE_NAME = "newFreestyleProject";
    private static final String FOLDER = "NewFolder";

    private void dropDown() {
        By dropdownChevron = By.xpath("(//li//button[@class='jenkins-menu-dropdown-chevron'])[1]");

        Actions action = new Actions(getDriver());
        action.moveToElement(getDriver().findElement(dropdownChevron)).pause(2000).perform();
        getWait10().until(ExpectedConditions.elementToBeClickable(dropdownChevron));
        int chevronHeight = getDriver().findElement(dropdownChevron).getSize().getHeight();
        int chevronWidth = getDriver().findElement(dropdownChevron).getSize().getWidth();
        action.moveToElement(getDriver().findElement(dropdownChevron), chevronWidth, chevronHeight).click().perform();
    }

   @Test
   public void testCreateFreestyleProject() {
        getDriver().findElement(MAIN_PAGE).click();

        getDriver().findElement(JENKINS_INPUT).sendKeys(FREESTYLE_NAME);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(OK_BUTTON).click();

        getDriver().findElement(SAVE_BUTTON).click();

        Assert.assertEquals((getDriver().findElement(By.xpath("//h1")).getText()), FREESTYLE_NAME);
    }

    @Test(dependsOnMethods = "testCreateFreestyleProject")
    public void testFreestyleMoveToFolder() {
        TestUtils.createNewJob(this, TestUtils.Job.FOLDER,FOLDER);

        WebElement dropdownChevron = getDriver().findElement(By.xpath("//td//button[@class='jenkins-menu-dropdown-chevron'][contains(@data-href,'" + FREESTYLE_NAME + "')]"));
        Actions action = new Actions(getDriver());
        action.moveToElement(dropdownChevron).perform();
        dropdownChevron.click();

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class='jenkins-dropdown__item'][contains(@href,'move')]"))).click();

        WebElement move = getDriver().findElement(By.name("destination"));
        Select select = new Select(move);
        select.selectByValue("/" + FOLDER);
        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(JENKINS_DASHBOARD).click();

        getDriver().findElement(By.xpath("//td/*[@href]")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//span[text()='" + FREESTYLE_NAME + "']")).getText(),
                FREESTYLE_NAME);
    }

    @Test(dependsOnMethods = "testFreestyleMoveToFolder")
    public void testCheckFreestyleProjectViaBreadcrumb() {
        //getDriver().findElement(By.xpath("//div[@class='logo']")).click();

        dropDown();

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class = 'jenkins-dropdown__item'][contains(@href, 'views')]"))).click();

        getDriver().findElement(By.xpath("//li[@class='children'][2]")).click();

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class = 'jenkins-dropdown__item'][contains(@href," + FOLDER + ")]"))).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//td//a[@href='job/" + FREESTYLE_NAME + "/']")).getText(),
                FREESTYLE_NAME);
    }
}
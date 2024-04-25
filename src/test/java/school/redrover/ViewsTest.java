package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class ViewsTest extends BaseTest {
    @Test
    public void testGoToMyViewsFromHeaderUserMenu() {
        WebElement userElement  = getDriver().findElement(By.cssSelector("div.login a[href ^= '/user/']"));
        TestUtils.openElementDropdown(this, userElement);
        getDriver().findElement(By.cssSelector("div.tippy-box [href $= 'my-views']")).click();
        Assert.assertTrue(getWait10().until(ExpectedConditions.urlContains("my-views/view/all")));
    }

    @Test
    public void testGoToMyViewFromUsernameDropdown() {
        new Actions(getDriver())
                .moveToElement(getDriver().findElement(By.cssSelector("[data-href$='admin']")))
                .pause(1000)
                .click()
                .perform();

        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[href$='admin/my-views']"))).click();

        Assert.assertTrue(getDriver().findElement(By.cssSelector("[href$='my-views/']")).isDisplayed());
    }

    final String VIEW_NAME = "in progress";
    final String VISIBLE = "visible";

    public void createView(String VIEW_NAME) {
        getDriver().findElement(By.cssSelector("[tooltip='New View']")).click();
        getDriver().findElement(By.id("name")).sendKeys(VIEW_NAME);
        getDriver().findElement(By.cssSelector("[for$='ListView']")).click();
        getDriver().findElement(By.id("ok")).click();
        getDriver().findElement(By.cssSelector("label[title=" + VISIBLE + "]")).click();
        getDriver().findElement(By.name("Submit")).click();
    }

    @Test
    public void testDisplayViewWithListViewConstraints() {
        final String INVISIBLE = "invisible";

        TestUtils.createNewItemAndReturnToDashboard(this,VISIBLE, TestUtils.Item.FOLDER);
        TestUtils.createNewItemAndReturnToDashboard(this,INVISIBLE, TestUtils.Item.PIPELINE);

        getDriver().findElement(By.cssSelector("[tooltip='New View']")).click();
        getDriver().findElement(By.id("name")).sendKeys(VIEW_NAME);
        getDriver().findElement(By.cssSelector("[for$='ListView']")).click();
        getDriver().findElement(By.id("ok")).click();
        getDriver().findElement(By.cssSelector("label[title=" + VISIBLE + "]")).click();
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertTrue(
                getDriver().findElements(By.cssSelector("[id^='job']")).size() == 1 &&
                         getDriver().findElement(By.cssSelector(String.format("tr [href='job/%s/']", VISIBLE))).getText().equals(VISIBLE),
                "Error displaying projects in View");
    }

    @Test
    public void testAddColumnIntoListView() {
        TestUtils.createNewItemAndReturnToDashboard(this,VISIBLE, TestUtils.Item.FOLDER);
        createView(VIEW_NAME);

        getDriver().findElement(By.linkText("Edit View")).click();

        WebElement addColumn = getDriver().findElement(By.cssSelector("[suffix='columns']>svg"));
        ((JavascriptExecutor) getDriver()).executeScript("return arguments[0].scrollIntoView(true);", addColumn);
        addColumn.click();

        getDriver().findElement(By.cssSelector("div.jenkins-dropdown button:last-child")).click();
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.id("jenkins-home-link")).click();
        getDriver().findElement(By.linkText(VIEW_NAME)).click();

        Assert.assertEquals(
                getDriver().findElements(By.className("sortheader")).size(),
                7,
                "Description column is not added");
    }
}
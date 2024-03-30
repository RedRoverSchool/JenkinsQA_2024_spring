package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AqaGroupJavaScriptTest extends AqaGroupBaseTest {

    @Test
    public void testDragAndDrop() {
        getDriver().get("https://testpages.eviltester.com/styled/drag-drop-javascript.html");

        WebElement dragMe1 = getDriver().findElement(By.id("draggable1"));
        WebElement dropHere1 = getDriver().findElement(By.id("droppable1"));

        Actions actions = new Actions(getDriver());
        actions
                .dragAndDrop(dragMe1, dropHere1)
                .build()
                .perform();

        Assert.assertEquals(
                dropHere1.getText(),
                "Dropped!",
                "error");
    }

    @Test
    public void testDragAndDropBoth() {
        getDriver().get("https://testpages.eviltester.com/styled/drag-drop-javascript.html");

        WebElement dragMe1 = getDriver().findElement(By.id("draggable1"));
        WebElement dragMe2 = getDriver().findElement(By.id("draggable2"));
        WebElement dropHere1 = getDriver().findElement(By.id("droppable1"));

        Actions actions = new Actions(getDriver());
        actions
                .dragAndDrop(dragMe1, dropHere1)
                .build()
                .perform();

        actions
                .dragAndDrop(dragMe2, dropHere1)
                .build()
                .perform();

        Assert.assertEquals(
                dropHere1.getText(),
                "Get Off Me!",
                "error");
    }

    @Test
    public void testRedirectSlow() {
        getDriver().get("https://testpages.eviltester.com/styled/javascript-redirect-test.html");

        getDriver().findElement(By.id("delaygotobasic")).click();
        getWait15().until(ExpectedConditions.urlContains("redirected"));

        Assert.assertEquals(
                getDriver().findElement(By.className("explanation")).getText(),
                "You have been successfully redirected.");
    }

    @Test
    public void testRedirectFast() {
        getDriver().get("https://testpages.eviltester.com/styled/javascript-redirect-test.html");

        getDriver().findElement(By.id("delaygotobounce")).click();
        getWait15().until(ExpectedConditions.urlContains("redirected"));

        Assert.assertEquals(
                getDriver().findElement(By.className("explanation")).getText(),
                "You have been successfully redirected.");
    }

    @Test
    public void testBasicAjax() {
        getDriver().get("https://testpages.eviltester.com/styled/basic-ajax-test.html");

        Select category = new Select(getDriver().findElement(By.id("combo1")));
        category.selectByValue("2");

        getWait15().until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[value='10']")));

        Select language = new Select(getDriver().findElement(By.id("combo2")));
        language.selectByValue("11");

        getDriver().findElement(By.className("styled-click-button")).click();

        Assert.assertEquals(
                getDriver().findElement(By.id("_valueid")).getText(), "2");

        Assert.assertEquals(
                getDriver().findElement(By.id("_valuelanguage_id")).getText(), "11");
    }

    @Test
    public void testKeysEventDisplay() {
        getDriver().get("https://testpages.eviltester.com/styled/key-click-display-test.html");

        getDriver().findElement(By.id("button")).sendKeys("a");

        Assert.assertEquals(
                getDriver().findElement(By.id("event1")).getText(),
                "down a 65");
    }
}
package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AqaJavaScriptTest extends AqaGroupBaseTest {
    @Test
    public void dragAndDropTest() {
        driver.get("https://testpages.eviltester.com/styled/drag-drop-javascript.html");

        WebElement dragMe1 = driver.findElement(By.id("draggable1"));
        WebElement dropHere1 = driver.findElement(By.id("droppable1"));

        Actions actions = new Actions(driver);
        actions.
                dragAndDrop(dragMe1, dropHere1).
                build().
                perform();

        Assert.assertEquals(
                dropHere1.getText(),
                "Dropped!",
                "error");
    }

    @Test
    public void dragAndDropBothTest() {
        driver.get("https://testpages.eviltester.com/styled/drag-drop-javascript.html");

        WebElement dragMe1 = driver.findElement(By.id("draggable1"));
        WebElement dragMe2 = driver.findElement(By.id("draggable2"));
        WebElement dropHere1 = driver.findElement(By.id("droppable1"));

        Actions actions = new Actions(driver);
        actions.
                dragAndDrop(dragMe1, dropHere1).
                build().
                perform();

        actions.
                dragAndDrop(dragMe2, dropHere1).
                build().
                perform();

        Assert.assertEquals(
                dropHere1.getText(),
                "Get Off Me!",
                "error");
    }

    @Test
    public void redirectSlowTest() {
        driver.get("https://testpages.eviltester.com/styled/javascript-redirect-test.html");

        driver.findElement(By.id("delaygotobasic")).click();
        getWait15().until(ExpectedConditions.urlContains("redirected"));

        Assert.assertEquals(
                driver.findElement(By.className("explanation")).getText(),
                "You have been successfully redirected.");
    }

    @Test
    public void redirectFastTest() {
        driver.get("https://testpages.eviltester.com/styled/javascript-redirect-test.html");

        driver.findElement(By.id("delaygotobounce")).click();
        getWait15().until(ExpectedConditions.urlContains("redirected"));

        Assert.assertEquals(
                driver.findElement(By.className("explanation")).getText(),
                "You have been successfully redirected.");
    }

}

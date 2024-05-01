package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class NewItem8Test extends BaseTest {

    private final String FIRST_ITEM_NAME = "My first Multibranch Pipeline";
    private final String SECOND_ITEM_NAME = "My second Multibranch Pipeline";

    public void createItem() {
        enterToNewItemPage();
        getDriver().findElement(By.className("jenkins-input")).sendKeys(FIRST_ITEM_NAME);
        getDriver().findElement(By.xpath("//label[.='Multibranch Pipeline']")).click();
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();
    }

    public void enterToNewItemPage() {
        getDriver().findElement(By.className("task")).click();
    }

    public void goToHomePage() {
        getDriver().findElement(By.id("jenkins-home-link")).click();
    }

    @Test
    public void testCreateItem() {
        getDriver().findElement(By.className("task")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys(FIRST_ITEM_NAME);
        getDriver().findElement(By.xpath("//label[.='Multibranch Pipeline']")).click();
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();

        Assert.assertTrue(getDriver().findElement(By.linkText(FIRST_ITEM_NAME)).isDisplayed());
    }

    @Test
    public void testCreateItemFromNewJob() {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys(FIRST_ITEM_NAME);
        getDriver().findElement(By.xpath("//label[.='Multibranch Pipeline']")).click();
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();

        Assert.assertTrue(getDriver().findElement(By.linkText(FIRST_ITEM_NAME)).isDisplayed());
    }

    @Test
    public void testUserSeeTheNameEntryField() {
        enterToNewItemPage();

        Assert.assertTrue(getDriver().findElement(By.className("jenkins-input")).isDisplayed());
    }
}
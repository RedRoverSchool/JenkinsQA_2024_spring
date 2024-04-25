package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class Folder6Test extends BaseTest {
    private final String name = "My new Folder";
    @Test
    public void testCreate() {
        getDriver().findElement(By.xpath("//*[@id=\"tasks\"]/div[1]/span/a")).click();
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id=\"name\"]")))
                .sendKeys(name);
        getDriver().findElement(By.xpath("//*[text()='Folder']/ancestor::li")).click();
        getDriver().findElement(By.xpath("//*[@id=\"ok-button\"]")).click();
        getDriver().findElement(By.xpath("//*[@name=\"Submit\"]")).click();
        TestUtils.returnToDashBoard(this);

        String myNewName = getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"job_My new Folder\"]/td[3]/a/span"))).getText();
        Assert.assertEquals(myNewName, name);
    }
    @Test(dependsOnMethods = "testCreate")
    public void testAddDescription() {
        String addDescription = "some description";
        getDriver().findElement(By.xpath("//span[text()='" + name + "']")).click();
        getDriver().findElement(By.id("description-link")).click();
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"description\"]/form/div[1]/div[1]/textarea")))
                    .sendKeys(addDescription);
        getDriver().findElement(By.xpath("//*[@id=\"description\"]/form/div[2]/button")).click();

        String editNewDescription = "edit new description ";
        getDriver().findElement(By.id("description-link")).click();
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"description\"]/form/div[1]/div[1]/textarea")))
                .sendKeys(editNewDescription);
        getDriver().findElement(By.xpath("//*[@id=\"description\"]/form/div[2]/button")).click();

        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.xpath("//*[@id=\"description\"]/form/div[1]/div[1]/textarea")).clear();
        getDriver().findElement(By.xpath("//*[@id=\"description\"]/form/div[2]/button")).click();

        boolean isFolderEmpty = getDriver().findElements(By.xpath("//*[@id=\"description\"]/form/div[1]/div[1]/textarea")).isEmpty();
        Assert.assertTrue(isFolderEmpty);
        TestUtils.returnToDashBoard(this);
    }
    @Test(dependsOnMethods = "testAddDescription")
    public void testRename() {
        getDriver().findElement(By.xpath("//span[text()='" + name + "']")).click();
        getDriver().findElement(By.xpath("//*[@id=\"tasks\"]/div[7]/span/a")).click();
        getDriver().findElement(By.xpath("//*[@id=\"main-panel\"]/form/div[1]/div[1]/div[2]/input")).clear();

        String newName = "Folder";
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"main-panel\"]/form/div[1]/div[1]/div[2]/input")))
                .sendKeys(newName);
        getDriver().findElement(By.xpath("//*[@id=\"bottom-sticker\"]/div/button")).click();
        TestUtils.returnToDashBoard(this);

        String actualFolderName = getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"job_Folder\"]/td[3]/a/span"))).getText();
        Assert.assertEquals(actualFolderName, newName);
    }
}


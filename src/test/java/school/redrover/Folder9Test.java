package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class Folder9Test extends BaseTest {
    public static final String ROOT_FOLDER_NAME = "Ivan's Folder";
    public static final String FIRST_FOLDER_NAME = "Inner Folder";
    public static final String IVAN_S_FREE_STYLE_PROJECT = "Ivan's FreeStyle Project";

    @Test
    public void testCreateFolder() {
        TestUtils.createItem(TestUtils.FOLDER, ROOT_FOLDER_NAME, this);
        TestUtils.goToMainPage(getDriver());

        WebElement rootFolder = getWait5().until(ExpectedConditions.
                visibilityOfElementLocated(By.linkText(ROOT_FOLDER_NAME)));
        Assert.assertEquals(rootFolder.getText(), ROOT_FOLDER_NAME, "Root Folder was not created");
    }
    @Test(dependsOnMethods = "testCreateFolder")
    public void testCreateFolderInsideRootFolder() {
        getDriver().findElement(By.linkText(ROOT_FOLDER_NAME)).click();
        TestUtils.createItem(TestUtils.FOLDER, FIRST_FOLDER_NAME, this);
        TestUtils.goToMainPage(getDriver());
        getWait5().until(ExpectedConditions.
                elementToBeClickable(By.linkText(ROOT_FOLDER_NAME))).click();

        WebElement innerFolder = getWait5().until(ExpectedConditions.
                visibilityOfElementLocated(By.linkText(FIRST_FOLDER_NAME)));
        Assert.assertEquals(innerFolder.getText(), FIRST_FOLDER_NAME, "First Folder was not created");
    }
    @Test(dependsOnMethods = "testCreateFolderInsideRootFolder")
    public void testCreateFreeStyleProjectInsideRootFolder() {
        getDriver().findElement(By.linkText(ROOT_FOLDER_NAME)).click();
        TestUtils.createItem(TestUtils.FREESTYLE_PROJECT, IVAN_S_FREE_STYLE_PROJECT, this);
        TestUtils.goToMainPage(getDriver());
        getWait5().until(ExpectedConditions.
                elementToBeClickable(By.linkText(ROOT_FOLDER_NAME))).click();

        WebElement freeStyleProject = getWait5().until(ExpectedConditions.
                visibilityOfElementLocated(By.linkText(IVAN_S_FREE_STYLE_PROJECT)));
        Assert.assertEquals(freeStyleProject.getText(), IVAN_S_FREE_STYLE_PROJECT, "FreeStyle Project was not created");
    }
}

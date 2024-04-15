package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import static school.redrover.runner.TestUtils.*;

public class Folder1Test extends BaseTest {
    private static final String ROOT_FOLDER_NAME = "Root Folder";
    private static final String FIRST_FOLDER_NAME = "Inner Folder 1";
    private static final String SECOND_FOLDER_NAME = "Inner Folder 2";

    @Test
    public void testCreateFolderUsingValidName() {
        createItem(FOLDER, ROOT_FOLDER_NAME, getDriver());

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), ROOT_FOLDER_NAME);
        Assert.assertTrue(getDriver().findElement(By.className("empty-state-section")).isDisplayed());
        Assert.assertTrue(findLinkTextOnPage("New Item").isDisplayed());
    }

    @Test
    public void testCreateTwoFolderInFolder() {
        createItem(FOLDER, ROOT_FOLDER_NAME, getDriver());
        createItem(FOLDER, FIRST_FOLDER_NAME, getDriver());
        goToMainPage(getDriver());
        findLinkTextOnPage(ROOT_FOLDER_NAME).click();
        createItem(FOLDER, SECOND_FOLDER_NAME, getDriver());
        goToMainPage(getDriver());
        findLinkTextOnPage(ROOT_FOLDER_NAME).click();

        Assert.assertTrue(findLinkTextOnPage(FIRST_FOLDER_NAME).isDisplayed());
        Assert.assertTrue(findLinkTextOnPage(SECOND_FOLDER_NAME).isDisplayed());
    }

    private WebElement findLinkTextOnPage(String text) {
        return getDriver().findElement(By.xpath("//a[.='" + text + "']"));
    }
}

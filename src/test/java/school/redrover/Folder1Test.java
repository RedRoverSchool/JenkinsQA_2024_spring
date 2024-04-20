package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class Folder1Test extends BaseTest {
    private static final String ROOT_FOLDER_NAME = "Root Folder";
    private static final String FIRST_FOLDER_NAME = "Inner Folder 1";
    private static final String SECOND_FOLDER_NAME = "Inner Folder 2";

    @Test
    public void testCreate() {
        TestUtils.createItem(TestUtils.FOLDER, ROOT_FOLDER_NAME, this);

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), ROOT_FOLDER_NAME);
    }

    @Test(dependsOnMethods = "testCreate")
    public void testCheckNewFolderIsEmpty() {
        findLinkByText(ROOT_FOLDER_NAME).click();
        Assert.assertTrue(getDriver().findElement(By.className("empty-state-section")).isDisplayed());
    }

    @Test(dependsOnMethods = "testCheckNewFolderIsEmpty")
    public void testCreateTwoInnerFolder() {
        findLinkByText(ROOT_FOLDER_NAME).click();
        TestUtils.createItem(TestUtils.FOLDER, FIRST_FOLDER_NAME, this);
        TestUtils.goToMainPage(getDriver());
        findLinkByText(ROOT_FOLDER_NAME).click();
        TestUtils.createItem(TestUtils.FOLDER, SECOND_FOLDER_NAME, this);
        TestUtils.goToMainPage(getDriver());
        findLinkByText(ROOT_FOLDER_NAME).click();

        Assert.assertTrue(findLinkByText(FIRST_FOLDER_NAME).isDisplayed()
                && findLinkByText(SECOND_FOLDER_NAME).isDisplayed());
    }

    private WebElement findLinkByText(String text) {
        return getDriver().findElement(By.xpath("//a[.='" + text + "']"));
    }
}

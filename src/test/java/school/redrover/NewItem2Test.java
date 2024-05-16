package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.CreateNewItemPage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

import java.util.Objects;
import java.util.Random;

public class NewItem2Test extends BaseTest {

    private static final String PROJECT_NAME = "NewProject";

    public void verifySubmitButtonIsEnabledAndClickOn() {
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
    }

    public char generateRandomRestrictedChar() {
        char[] restrictedChars = {'!', '@', '#', '$', '%', '^', '&', '*', '/', '\\', '|', ':', ';', '[', ']'};
        Random random = new Random();
        int index = random.nextInt(restrictedChars.length);

        return restrictedChars[index];
    }

    public int generateRandomIndex(int maxIndex) {
        Random random = new Random();
        return random.nextInt(maxIndex) + 1;
    }

    public void selectItemTypeForProjectAndCheckPageTitleAfterSaving(String projectType) {
        int itemOptionIndex = generateRandomIndex(3);

        WebElement itemOption = getDriver().findElement(
                By.xpath(String.format("//div[contains(@id, \"%s\")]/ul/li[%d]", projectType, itemOptionIndex)));
        itemOption.click();
        Assert.assertTrue(Boolean.parseBoolean(itemOption.getAttribute("aria-checked")));

        verifySubmitButtonIsEnabledAndClickOn();

        String currentUrl = getDriver().getCurrentUrl();
        Assert.assertEquals(currentUrl, String.format("http://localhost:8080/job/%s/configure", PROJECT_NAME));

        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
        WebElement pageHeadline = getDriver().findElement(By.cssSelector("h1"));

        if (Objects.equals(projectType, "standalone-projects") && itemOptionIndex == 3) {
            Assert.assertEquals(pageHeadline.getText(), "Project " + PROJECT_NAME);
        } else {
            Assert.assertEquals(pageHeadline.getText(), PROJECT_NAME);
        }
    }

    @Test
    public void testCreateItemWithUnsafeChar() {
        char restrictedChar = generateRandomRestrictedChar();

        String actualHintText = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(restrictedChar + PROJECT_NAME)
                .getErrorMessageInvalidCharacterOrDuplicateName();

        String actualHintColor = new CreateNewItemPage(getDriver())
                .getColorOfErrorMessageWhenUnsafeChar();

        Assert.assertEquals(actualHintText, String.format("» ‘%s’ is an unsafe character", restrictedChar));
        Assert.assertEquals(actualHintColor, "rgba(255, 0, 0, 1)");
    }

    @Test
    public void testCreateItemWithoutSelectedItemType() {
        Boolean isOkButtonEnabled = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .isOkButtonEnabled();

        Assert.assertFalse(isOkButtonEnabled);
    }

    @Test
    public void testCreateItemForStandaloneProjects() {
        new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PROJECT_NAME);

        selectItemTypeForProjectAndCheckPageTitleAfterSaving("standalone-projects");
    }

    @Test
    public void testCreateItemForNestedProjects() {
        new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PROJECT_NAME);

        selectItemTypeForProjectAndCheckPageTitleAfterSaving("nested-projects");
    }
}

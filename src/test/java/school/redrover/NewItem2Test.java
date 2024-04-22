package school.redrover;

import net.bytebuddy.implementation.bind.annotation.IgnoreForBinding;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.Objects;
import java.util.Random;

public class NewItem2Test extends BaseTest {

    public void enterToNewItemPage() {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
    }

    public void enterProjectName(String projectName) {
        WebElement inputField = getDriver().findElement(By.xpath("//input[@class='jenkins-input']"));
        inputField.sendKeys(projectName);
    }

    public void verifyHintForInvalidProjectName(String expectedValidationText) {
        getDriver().findElement(By.cssSelector("#main-panel")).click();

        WebElement validationMessage = getDriver().findElement(
                By.xpath("//div[@class='input-validation-message']"));
        Assert.assertEquals(validationMessage.getText(), expectedValidationText);

        String validationMessageColor = Color.fromString(validationMessage.getCssValue("color")).asHex();
        Assert.assertEquals(validationMessageColor, "#ff0000");
    }

    public void verifySubmitButtonIsDisabled() {
        WebElement submitButton = getDriver().findElement(By.xpath("//button[@id='ok-button']"));
        Assert.assertFalse(submitButton.isEnabled());
    }

    public void verifySubmitButtonIsEnabledAndClickOn() {
        WebElement submitButton = getDriver().findElement(By.xpath("//button[@id='ok-button']"));
        Assert.assertTrue(submitButton.isEnabled());
        submitButton.click();
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

    private static final String PROJECT_NAME = "NewProject";

    @Test
    public void testCreateItemWithEmptyName() {
        enterToNewItemPage();
        verifySubmitButtonIsDisabled();
        enterProjectName("");
        verifyHintForInvalidProjectName("» This field cannot be empty, please enter a valid name");
    }

    @Test
    public void testCreateItemWithUnsafeChar() {
        char restrictedChar = generateRandomRestrictedChar();

        enterToNewItemPage();
        enterProjectName(restrictedChar + PROJECT_NAME);
        verifyHintForInvalidProjectName(String.format("» ‘%s’ is an unsafe character", restrictedChar));
    }

    @Test
    public void testCreateItemWithoutSelectedItemType() {
        enterToNewItemPage();
        enterProjectName(PROJECT_NAME);
        verifySubmitButtonIsDisabled();
    }

    @Ignore
    @Test
    public void testCreateItemForStandaloneProjects() {
        enterToNewItemPage();
        enterProjectName(PROJECT_NAME);
        selectItemTypeForProjectAndCheckPageTitleAfterSaving("standalone-projects");
    }

    @Ignore
    @Test
    public void testCreateItemForNestedProjects() {
        enterToNewItemPage();
        enterProjectName(PROJECT_NAME);
        selectItemTypeForProjectAndCheckPageTitleAfterSaving("nested-projects");
    }
}

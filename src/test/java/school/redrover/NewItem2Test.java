package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

import java.util.Objects;
import java.util.Random;

public class NewItem2Test extends BaseTest {

    private static final String PROJECT_NAME = "NewProject";

    public void selectItemTypeForProjectAndCheckPageTitleAfterSaving(String projectType) {
        Random random = new Random();
        int itemOptionIndex = random.nextInt(3) + 1;

        WebElement itemOption = getDriver().findElement(
                By.xpath(String.format("//div[contains(@id, '%s')]/ul/li[%d]", projectType, itemOptionIndex)));
        itemOption.click();
        Assert.assertTrue(Boolean.parseBoolean(itemOption.getAttribute("aria-checked")));

        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();

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

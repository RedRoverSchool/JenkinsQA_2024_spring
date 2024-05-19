package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.model.CreateNewItemPage;
import school.redrover.model.FreestyleConfigPage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

import java.util.Objects;
import java.util.Random;

public class NewItem2Test extends BaseTest {


    @DataProvider
    Object[][] projectTypes() {
        return new Object[][]{
                {"standalone-projects"},
                {"nested-projects"}};
    }

    @Test(dataProvider = "projectTypes")
    public void testCreateItemForStandaloneOrNestedProjects(String projectType) {
        String PROJECT_NAME = "NewProject";
        Random random = new Random();
        int itemOptionIndex = random.nextInt(3) + 1;

        Boolean isTypeChecked = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .clickItemOption(projectType, itemOptionIndex)
                .isAttributeAriaChecked(projectType, itemOptionIndex);

        String currentUrl = new CreateNewItemPage(getDriver())
                .clickOkButton()
                .getCurrentUrl();

        String pageHeading = new FreestyleConfigPage(getDriver())
                .clickSaveButton().getPageHeadingText();

        Assert.assertTrue(isTypeChecked);
        Assert.assertEquals(currentUrl, String.format("http://localhost:8080/job/%s/configure", PROJECT_NAME));
        Assert.assertTrue(pageHeading.contains(PROJECT_NAME));
    }
}

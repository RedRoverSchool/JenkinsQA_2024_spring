package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.*;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class FreestyleProject33test extends BaseTest {
    String projectName;
    @Test
    public void testCreateNewFreestyleProject() {
        projectName = TestUtils.getUniqueName("testProject");

        String actualProjectName = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(projectName)
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .getProjectName();

        Assert.assertEquals(actualProjectName, projectName);
    }

    @Test
    public void testCreateFreeStyleProjectWithSpecialSymbol() {
        projectName = TestUtils.getUniqueName("testproject/");

        String errorText = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(projectName)
                .selectFreeStyleProject()
                .clickOkAnyway(new CreateItemPage(getDriver()))
                .getErrorMessageText();

        Assert.assertTrue(errorText.contains("is an unsafe character"));
    }

    @Test
    public void testCreateFreeStyleProjectWithSpacesName() {
        projectName = "     ";

        String errorText = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(projectName)
                .selectFreeStyleProject()
                .clickOkAnyway(new CreateItemPage(getDriver()))
                .getErrorMessageText();

        Assert.assertTrue(errorText.contains("No name is specified"));
    }

    @Test
    public void testCreateFreeStyleProjectWithLongestName() {
        for (int i = 0; i < 260; i++) {
            projectName += "a";
        }

        String errorText = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(projectName)
                .selectFreeStyleProject()
                .clickOkAnyway(new CreateItemPage(getDriver()))
                .getErrorMessageText();
        System.out.println(errorText);

        Assert.assertTrue(errorText.contains("Logging ID="));
    }
    @Test(dependsOnMethods = {"testCreateNewFreestyleProject"})
    public void testCreateNewFreestyleProjectWithDuplicateName(){
        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(projectName)
                .selectFreeStyleProject()
                .clickOkAnyway(new CreateItemPage(getDriver()))
                .getErrorMessageText();

        Assert.assertTrue(errorMessage.contains("A job already exists with the name"));
    }

    @Test
    public void testCreateNewFreestyleProjectWithEmptyName() {
        Boolean errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName("")
                .selectFreeStyleProject()
                .getOkButtoneState();

        Assert.assertFalse(errorMessage);
    }

    @Test(dependsOnMethods = {"testCreateNewFreestyleProject"})
    public void testCreateNewFreestyleProjectHomePageView() {
        List<String> items = new HomePage(getDriver()).getItemList();

        Assert.assertTrue(items.contains(projectName));
    }
}

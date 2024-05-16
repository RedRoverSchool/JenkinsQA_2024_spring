package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.CreateItemPage;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;

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

        projectName += "a".repeat(260);

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
    public void testCreateNewFreestyleProjectWithDuplicateName() {

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

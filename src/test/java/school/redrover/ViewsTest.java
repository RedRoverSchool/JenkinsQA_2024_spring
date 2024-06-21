package school.redrover;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.model.ViewMyListConfigPage;
import school.redrover.model.ViewPage;
import school.redrover.runner.BaseTest;


import java.util.List;

@Epic("Views")
public class ViewsTest extends BaseTest {

    private static final String MY_VIEW_NAME = "EmployeeView";
    private static final String VIEW_NAME = "in progress";
    private static final String VISIBLE = "visible";

    @Test
    public void testGoToMyViewsFromUsernameDropdown() {
        String views = "My Views";

        boolean textVisibility = new HomePage(getDriver())
                .getHeader().clickMyViewsOnHeaderDropdown()
                .isThereTextInBreadcrumbs(views);

        Assert.assertTrue(textVisibility, "'My Views' didn't open");
    }

    @Test
    public void testDisplayViewWithListViewConstraints() {
        final String invisible = "invisible";

        List<String> projectNameList = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(VISIBLE)
                .selectFolderAndClickOk()
                .clickLogo()
                .clickNewItem()
                .typeItemName(invisible)
                .selectPipelineAndClickOk()
                .clickLogo()
                .clickPlusToCreateView()
                .setViewName(VIEW_NAME)
                .clickListViewRadioButton()
                .clickCreateViewButton()
                .clickProjectName(VISIBLE)
                .clickOkButton()
                .getProjectNames();

        List<String> expectedProjectNameList = List.of(VISIBLE);
        int expectedProjectListSize = 1;

        Assert.assertTrue(projectNameList.size() == expectedProjectListSize
                        && projectNameList.equals(expectedProjectNameList),
                "Error displaying projects in View");
    }

    public void createView(String viewName) {

        new HomePage(getDriver())
                .clickPlusToCreateView()
                .setViewName(viewName)
                .clickListViewRadioButton()
                .clickCreateViewButton();
    }


    @Test
    public void testAddColumnIntoListView() {

        new HomePage(getDriver())
                .clickCreateAJob()
                .typeItemName(VISIBLE)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .clickLogo();

        createView(VIEW_NAME);

        int numberOfColumns = new ViewMyListConfigPage(getDriver())
                .clickProjectName(VISIBLE)
                .clickAddColumn()
                .clickColumnName()
                .clickOkButton()
                .clickLogo()
                .clickViewName(VIEW_NAME)
                .getSizeColumnList();

        Assert.assertEquals(numberOfColumns, 7, "Description column is not added");
    }

    @Test(dependsOnMethods = "testAddColumnIntoListView")
    public void testChangeOrderOfColumns() {

        List<String> columnNameText = new HomePage(getDriver())
                .clickViewName(VIEW_NAME)
                .clickEditViewOnSidebar()
                .scrollIntoSubmit()
                .moveDescriptionToStatusColumn()
                .getColumnNameText();

        Assert.assertEquals(columnNameText.get(0), "Description");
    }

    @Test
    @Story("US_10.001 Create View")
    @Description("Verify Items in Views are sorted alphabetically")
    public void testItemsInViewsSortedAlphabeticallyByDefault() {
        final List<String> expectedSortedItemsByNameList = List.of("Freestyle", "OrganizationFolder", "Pipeline");

        ViewPage viewPage = new HomePage(getDriver())
                .clickCreateAJob()
                .typeItemName("Freestyle")
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .clickNewItem()
                .typeItemName("Pipeline")
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .clickNewItem()
                .typeItemName("OrganizationFolder")
                .selectOrganizationFolderAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .clickPlusToCreateView()
                .setViewName("ViewToVerifySorting")
                .clickMyViewRadioButton()
                .clickCreateButtonUponChoosingMyView();

        Allure.step("View contains all Item names in alphabetical order");
        Assert.assertEquals(viewPage.getNameColumnText(), "Name ↓");
        Assert.assertEquals(viewPage.getProjectNames(), expectedSortedItemsByNameList);
    }

    @Test
    @Story("US_10.002 Edit View")
    @Description("Check new column is added to the View Headline")
    public void testAddColumnToPipelineView() {
        final String pipelineName = "NewPipeline";
        final List<String> expectedPipelineViewList =
                List.of("S", "W", "Name" + "\n" + "  ↓",
                        "Last Success", "Last Failure", "Last Duration", "Git Branches");

        List<String> actualPipelineViewList = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(pipelineName)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .clickPlusToCreateView()
                .setViewName(MY_VIEW_NAME)
                .clickListViewRadioButton()
                .clickCreateViewButton()
                .clickProjectName(pipelineName)
                .scrollIntoSubmit()
                .clickAddColumn()
                .clickGitBranchColumn()
                .clickOkButton()
                .getProjectViewTitleList();

        Allure.step("Expected result: New column should be added to the view list");
        Assert.assertEquals(actualPipelineViewList, expectedPipelineViewList);
    }

    @Test(dependsOnMethods = "testAddColumnToPipelineView")
    @Story("US_10.002 Delete View")
    @Description("Verify a View list does not contain recently deleted View name")
    public void testDeletePipelineView() {
        int viewNameListSize = new HomePage(getDriver())
                .clickViewName(MY_VIEW_NAME)
                .clickDeleteViewSideBarAndConfirmDeletion()
                .getSizeViewNameList();

        Allure.step("Expected result:View name should be deleted from View List");
        Assert.assertEquals(viewNameListSize, 2);
    }
}

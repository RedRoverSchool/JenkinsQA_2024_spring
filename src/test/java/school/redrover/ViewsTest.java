package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.model.ViewMyListConfigPage;
import school.redrover.model.ViewPage;
import school.redrover.runner.BaseTest;

import java.util.List;

public class ViewsTest extends BaseTest {

    private static final String MY_VIEW_NAME = "EmpoyeeView";
    private static final String VIEW_NAME = "in progress";
    private static final String VISIBLE = "visible";

    @Test
    public void testGoToMyViewsFromUsernameDropdown() {
        String views = "My Views";

        boolean textVisibility = new HomePage(getDriver())
                .clickMyViewsFromDropdown()
                .isThereTextInBreadcrumbs(views);

        Assert.assertTrue(textVisibility, "'My Views' didn't open");
    }

    public void createView(String VIEW_NAME) {
        new HomePage(getDriver())
                .clickPlusToCreateView()
                .setViewName(VIEW_NAME)
                .clickListViewRadioButton()
                .clickCreateViewButton();
    }

    @Test
    public void testDisplayViewWithListViewConstraints() {
        final String INVISIBLE = "invisible";

        List<String> projectNameList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(VISIBLE)
                .selectFolderAndClickOk()
                .clickLogo()
                .clickNewItem()
                .setItemName(INVISIBLE)
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

        Assert.assertTrue(projectNameList.size() == expectedProjectListSize &&
                        projectNameList.equals(expectedProjectNameList),
                "Error displaying projects in View");
    }

    @Test
    public void testAddColumnIntoListView() {

        new HomePage(getDriver())
                .clickCreateAJob()
                .setItemName(VISIBLE)
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
    public void testAddColumnToPipelineView() {
        final String pipelineName = "NewPipeline";
        final List<String> expectedPipelineViewList =
                List.of("S", "W", "Name" + "\n" + "  ↓", "Last Success", "Last Failure", "Last Duration", "Git Branches");

        List<String> actualPipelineViewList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(pipelineName)
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

        Assert.assertEquals(actualPipelineViewList, expectedPipelineViewList);
    }

    @Test(dependsOnMethods = "testAddColumnToPipelineView")
    public void testDeletePipelineView() {
        int viewNameListSize = new HomePage(getDriver())
                .clickViewName(MY_VIEW_NAME)
                .clickDeleteViewSideBarAndConfirmDeletion()
                .getSizeViewNameList();

        Assert.assertEquals(viewNameListSize, 2);
    }

    @Test
    public void testItemsInViewsSortedAlphabeticallyByDefault() {
        final List<String> expectedSortedItemsByNameList = List.of("Freestyle", "OrganizationFolder", "Pipeline");

        ViewPage viewPage = new HomePage(getDriver())
                .clickCreateAJob()
                .setItemName("Freestyle")
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .clickNewItem()
                .setItemName("Pipeline")
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .clickNewItem()
                .setItemName("OrganizationFolder")
                .selectOrganizationFolderAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .clickPlusToCreateView()
                .setViewName("ViewToVerifySorting")
                .clickMyViewRadioButton()
                .clickCreateButtonUponChoosingMyView();

        Assert.assertEquals(viewPage.getNameColumnText(), "Name ↓");
        Assert.assertEquals(viewPage.getProjectNames(), expectedSortedItemsByNameList);
    }
}
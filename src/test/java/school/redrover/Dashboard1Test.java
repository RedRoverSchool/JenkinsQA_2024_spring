package school.redrover;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Dashboard1Test extends BaseTest {
    private final String VIEW_NAME = "RedRover";
    private final List<String> PROJECTS_NAMES = Arrays.asList(
            TestUtils.FREESTYLE_PROJECT,
            TestUtils.MULTIBRANCH_PIPELINE,
            TestUtils.ORGANIZATION_FOLDER,
            TestUtils.FOLDER,
            TestUtils.MULTI_CONFIGURATION_PROJECT,
            TestUtils.PIPELINE);

    private final List<String> FREESTYLE_PROJECT_MENU = List.of(
            "Changes",
            "Workspace",
            "Build Now",
            "Configure",
            "Delete Project",
            "Move",
            "Rename");

    private final List<String> PIPELINE_MENU = List.of(
            "Changes",
            "Build Now",
            "Configure",
            "Delete Pipeline",
            "Move",
            "Full Stage View",
            "Rename",
            "Pipeline Syntax");

    private final List<String> MULTI_CONFIGURATION_PROJECT_MENU = List.of(
            "Changes",
            "Workspace",
            "Build Now",
            "Configure",
            "Delete Multi-configuration project",
            "Move",
            "Rename");

    private final List<String> FOLDER_MENU = List.of(
            "Configure",
            "New Item",
            "Delete Folder",
            "People",
            "Build History",
            "Rename",
            "Credentials");

    private final List<String> MULTIBRANCH_PIPELINE_MENU = List.of(
            "Configure",
            "Scan Multibranch Pipeline Log",
            "Multibranch Pipeline Events",
            "Delete Multibranch Pipeline",
            "People",
            "Build History",
            "Move",
            "Rename",
            "Pipeline Syntax",
            "Credentials");

    private final List<String> ORGANIZATION_FOLDER_MENU = List.of(
            "Configure",
            "Scan Organization Folder Log",
            "Organization Folder Events",
            "Delete Organization Folder",
            "People",
            "Build History",
            "Move",
            "Rename",
            "Pipeline Syntax",
            "Credentials");
    private final Object[][] PROJECTS_MENUS = {
            {TestUtils.FREESTYLE_PROJECT, FREESTYLE_PROJECT_MENU},
            {TestUtils.MULTIBRANCH_PIPELINE, MULTIBRANCH_PIPELINE_MENU},
            {TestUtils.ORGANIZATION_FOLDER, ORGANIZATION_FOLDER_MENU},
            {TestUtils.FOLDER, FOLDER_MENU},
            {TestUtils.MULTI_CONFIGURATION_PROJECT, MULTI_CONFIGURATION_PROJECT_MENU},
            {TestUtils.PIPELINE, PIPELINE_MENU}};

    public void createItem(String type, String name) {
        new HomePage(getDriver())
                .clickNewItem()
                .setItemName(name)
                .selectTypeAndClickOk(type)
                .clickLogo();
    }

    private List<String> getChevronMenu(String jobName) {
        return Arrays.stream(new HomePage(getDriver())
                        .openItemDropdown(jobName)
                        .getDropdownMenu()
                        .getText()
                        .split("\\r?\\n"))
                .toList();
    }

    @Test
    public void testSortItemsByName() {
        Collections.sort(PROJECTS_NAMES);
        PROJECTS_NAMES.forEach(s -> createItem(s, s));

        List<String> descSortingByNameList = new HomePage(getDriver())
                .clickColumnNameTitle()
                .getItemList();
        Collections.reverse(PROJECTS_NAMES);

        Assert.assertEquals(descSortingByNameList, PROJECTS_NAMES);

        List<String> ascSortingByNameList = new HomePage(getDriver())
                .clickColumnNameTitle()
                .getItemList();
        Collections.reverse(PROJECTS_NAMES);

        Assert.assertEquals(ascSortingByNameList, PROJECTS_NAMES);
    }

    @DataProvider(name = "projects")
    public Object[][] projectsName() {

        return PROJECTS_MENUS;
    }

    @Test(dependsOnMethods = "testSortItemsByName", dataProvider = "projects")
    public void testProjectChevronMenu(String projectName, List<String> projectMenu) {
        List<String> chevronMenu = Arrays.stream(
                        new HomePage(getDriver())
                                .openItemDropdown(projectName)
                                .getDropdownMenu()
                                .getText()
                                .split("\\r?\\n"))
                .toList();

        Assert.assertEquals(chevronMenu, projectMenu);
    }

    @Test(dependsOnMethods = "testProjectChevronMenu")
    public void testCreateView() {
        String createdViewName = new HomePage(getDriver())
                .clickNewView()
                .setViewName(VIEW_NAME)
                .clickListViewRadioButton()
                .clickCreateView()
                .clickOkButton()
                .getActiveViewName();

        Assert.assertEquals(createdViewName, VIEW_NAME);
    }

    @Test(dependsOnMethods = "testCreateView")
    public void testAddItemsToView() {
        final String SELECTED_NAME1 = TestUtils.MULTIBRANCH_PIPELINE;
        final String SELECTED_NAME2 = TestUtils.FOLDER;

        List<String> namesFromView = new HomePage(getDriver())
                .clickViewName(VIEW_NAME)
                .clickEditViewButton()
                .selectProjectForAddToView(SELECTED_NAME1)
                .selectProjectForAddToView(SELECTED_NAME2)
                .clickOkButton()
                .getProjectNames();

        Assert.assertTrue(namesFromView.size() == 2
                && namesFromView.contains(SELECTED_NAME1)
                && namesFromView.contains(SELECTED_NAME2));
    }

    @Test(dependsOnMethods = "testAddItemsToView")
    public void testChangeIconSize() {
        List<Integer> size = List.of(16, 20, 24);

        for (int i = 0; i < size.size(); i++) {
            int iconHeight = new HomePage(getDriver())
                    .clickSizeIcon(i)
                    .getProjectIconHeight();

            Assert.assertEquals(iconHeight, size.get(i));
        }
    }
}
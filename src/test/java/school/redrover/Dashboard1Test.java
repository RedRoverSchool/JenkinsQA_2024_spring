package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Dashboard1Test extends BaseTest {
    private final String VIEW_NAME = "RedRover";
    private final List<String> projectsNames = getNamesList();

    private List<String> getNamesList() {
        List<String> names = new ArrayList<>();
        names.add(TestUtils.FREESTYLE_PROJECT);
        names.add(TestUtils.MULTIBRANCH_PIPELINE);
        names.add(TestUtils.ORGANIZATION_FOLDER);
        names.add(TestUtils.FOLDER);
        names.add(TestUtils.MULTI_CONFIGURATION_PROJECT);
        names.add(TestUtils.PIPELINE);
        return names;
    }

    private List<String> getFreestyleProjectMenu() {
        List<String> menu = new ArrayList<>();
        menu.add("Changes");
        menu.add("Workspace");
        menu.add("Build Now");
        menu.add("Configure");
        menu.add("Delete Project");
        menu.add("Move");
        menu.add("Rename");
        return menu;
    }

    private List<String> getPipelineMenu() {
        List<String> menu = new ArrayList<>();
        menu.add("Changes");
        menu.add("Build Now");
        menu.add("Configure");
        menu.add("Delete Pipeline");
        menu.add("Move");
        menu.add("Full Stage View");
        menu.add("Rename");
        menu.add("Pipeline Syntax");
        return menu;
    }

    private List<String> getMultiConfigurationProjectMenu() {
        List<String> menu = new ArrayList<>();
        menu.add("Changes");
        menu.add("Workspace");
        menu.add("Build Now");
        menu.add("Configure");
        menu.add("Delete Multi-configuration project");
        menu.add("Move");
        menu.add("Rename");
        return menu;
    }

    private List<String> getFolderMenu() {
        List<String> menu = new ArrayList<>();
        menu.add("Configure");
        menu.add("New Item");
        menu.add("Delete Folder");
        menu.add("People");
        menu.add("Build History");
        menu.add("Rename");
        menu.add("Credentials");
        return menu;
    }

    private List<String> getMultibranchPipelineMenu() {
        List<String> menu = new ArrayList<>();
        menu.add("Configure");
        menu.add("Scan Multibranch Pipeline Log");
        menu.add("Multibranch Pipeline Events");
        menu.add("Delete Multibranch Pipeline");
        menu.add("People");
        menu.add("Build History");
        menu.add("Move");
        menu.add("Rename");
        menu.add("Pipeline Syntax");
        menu.add("Credentials");
        return menu;
    }

    private List<String> getOrganizationFolderMenu() {
        List<String> menu = new ArrayList<>();
        menu.add("Configure");
        menu.add("Scan Organization Folder Log");
        menu.add("Organization Folder Events");
        menu.add("Delete Organization Folder");
        menu.add("People");
        menu.add("Build History");
        menu.add("Move");
        menu.add("Rename");
        menu.add("Pipeline Syntax");
        menu.add("Credentials");
        return menu;
    }

    public void createItem(String type, String name) {
        new HomePage(getDriver())
                .clickNewItem()
                .setItemName(name)
                .selectTypeAndClickOk(type)
                .clickLogo();
    }

    private List<String> getItemNamesAfterSortingByName() {
        return new HomePage(getDriver())
                .clickColumnNameTitle()
                .getItemList();
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
    public void testFreestyleProjectChevronMenu() {
        projectsNames.forEach(s -> createItem(s, s));
        Assert.assertEquals(getChevronMenu(TestUtils.FREESTYLE_PROJECT), getFreestyleProjectMenu());
    }

    @Test(dependsOnMethods = "testFreestyleProjectChevronMenu")
    public void testPipelineChevronMenu() {
        Assert.assertEquals(getChevronMenu(TestUtils.PIPELINE), getPipelineMenu());
    }

    @Test(dependsOnMethods = "testPipelineChevronMenu")
    public void testMultiConfigurationProjectChevronMenu() {
        Assert.assertEquals(getChevronMenu(TestUtils.MULTI_CONFIGURATION_PROJECT), getMultiConfigurationProjectMenu());
    }

    @Test(dependsOnMethods = "testMultiConfigurationProjectChevronMenu")
    public void testFolderChevronMenu() {
        Assert.assertEquals(getChevronMenu(TestUtils.FOLDER), getFolderMenu());
    }

    @Test(dependsOnMethods = "testFolderChevronMenu")
    public void testMultibranchPipelineChevronMenu() {
        Assert.assertEquals(getChevronMenu(TestUtils.MULTIBRANCH_PIPELINE), getMultibranchPipelineMenu());
    }

    @Test(dependsOnMethods = "testMultibranchPipelineChevronMenu")
    public void testOrganizationChevronMenu() {
        Assert.assertEquals(getChevronMenu(TestUtils.ORGANIZATION_FOLDER), getOrganizationFolderMenu());
    }

    @Test(dependsOnMethods = "testOrganizationChevronMenu")
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
    public void testSortItemsByName() {
        Collections.sort(projectsNames);
        Collections.reverse(projectsNames);
        boolean isDescendingSortingOk = getItemNamesAfterSortingByName().equals(projectsNames);

        Collections.reverse(projectsNames);
        boolean isAscendingSortingOk = getItemNamesAfterSortingByName().equals(projectsNames);

        Assert.assertTrue(isAscendingSortingOk && isDescendingSortingOk);
    }

    @Test(dependsOnMethods = "testSortItemsByName")
    public void testAddItemsToView() {
        final String SELECTED_NAME1 = TestUtils.MULTIBRANCH_PIPELINE;
        final String SELECTED_NAME2 = TestUtils.FOLDER;

        List<String> namesFromNewView = new HomePage(getDriver())
                .clickViewName(VIEW_NAME)
                .clickEditViewButton()
                .selectProjectForAddToView(SELECTED_NAME1)
                .selectProjectForAddToView(SELECTED_NAME2)
                .clickOkButton()
                .getProjectNames();

        boolean isName1InView = namesFromNewView.contains(SELECTED_NAME1);
        boolean isName2InView = namesFromNewView.contains(SELECTED_NAME2);

        Assert.assertTrue(namesFromNewView.size() == 2 && isName1InView && isName2InView);
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
package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.*;

public class Dashboard1Test extends BaseTest {
    private final String VIEW_NAME = "Classic";
    private final String SELECTED_NAME1 = "Vivaldi";
    private final String SELECTED_NAME2 = "Nic";
    private List<String> projectsNames = getNamesList();

    private List<String> getNamesList() {
        List<String> names = new ArrayList<>();
        names.add("Imagine Dragons");
        names.add("A-ha");
        names.add("Depeche Mode");
        names.add("Antonio Vivaldi");
        names.add("Niccolo Paganini");
        return names;
    }

    private List<String> getFreestyleProjectMenu() {
        List<String> menu = new ArrayList<>();
        menu.add("Changes");
        menu.add("Workspace");
        menu.add("Build Now");
        menu.add("Configure");
        menu.add("Delete Project");
        menu.add("Rename");
        return menu;
    }

    private List<String> getPipelineMenu() {
        List<String> menu = new ArrayList<>();
        menu.add("Changes");
        menu.add("Build Now");
        menu.add("Configure");
        menu.add("Delete Pipeline");
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
        menu.add("Delete Multi-configuration Project");
        menu.add("Rename");
        return menu;
    }

    private List<String> getFolderMenu() {
        List<String> menu = new ArrayList<>();
        menu.add("Configure");
        menu.add("NewItem");
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
        menu.add("Delete Multibranch Pipeline");
        menu.add("People");
        menu.add("Build History");
        menu.add("Rename");
        menu.add("Pipeline Syntax");
        menu.add("Credentials");
        return menu;
    }

    private List<String> getOrganizationFolderMenu() {
        List<String> menu = new ArrayList<>();
        menu.add("Configure");
        menu.add("Scan Organization Folder Log");
        menu.add("Delete Organization Folder");
        menu.add("People");
        menu.add("Build History");
        menu.add("Rename");
        menu.add("Pipeline Syntax");
        menu.add("Credentials");
        return menu;
    }

    private void createItemsFromList(List<String> list) {
        for (String name : list) {
            TestUtils.createItem(TestUtils.MULTIBRANCH_PIPELINE, name, this);
            TestUtils.goToMainPage(getDriver());
        }
    }

    private List<String> getItemNamesFromColumnAfterSortingByName() {
        getDriver().findElement(By.xpath("//a[@class='sortheader' and text()='Name']")).click();
        return TestUtils.getTexts(getDriver().findElements(By.xpath("//td/a[contains(@href, 'job/')]")));
    }

    private void clickElement(WebElement webElement) {
        new Actions(getDriver())
                .scrollToElement(webElement)
                .scrollByAmount(0, 100)
                .moveToElement(webElement)
                .click()
                .perform();
    }

    private boolean isItemMenuCorrect(List<String> menu, String itemType) {
        TestUtils.createItem(itemType, itemType, this);
        TestUtils.goToMainPage(getDriver());
        TestUtils.clickJobChevronOnDashboard(this, itemType);
        List<String> chevronMenu = TestUtils.getTexts(getDriver().findElements
                (By.xpath("//*[@class='jenkins-dropdown__item']")));
//        getDriver().findElement(By.xpath("//button[contains(@href, 'Delete')]")).click();
        System.out.println(menu);
//        getDriver().findElement(By.xpath("//button[@data-id='ok']")).click();

        System.out.println(chevronMenu);
        System.out.println(menu);
        return chevronMenu.equals(menu);
    }

    @Test
    public void testFreestyleProjectChevronMenu() {
        Assert.assertTrue(isItemMenuCorrect(getFreestyleProjectMenu(), TestUtils.FREESTYLE_PROJECT));
    }

    @Test(dependsOnMethods = "testFreestyleProjectChevronMenu")
    public void testPipelineChevronMenu() {
        Assert.assertTrue(isItemMenuCorrect(getPipelineMenu(), TestUtils.PIPELINE));
    }

    @Test(dependsOnMethods = "testPipelineChevronMenu")
    public void testMultiConfigurationProjectChevronMenu() {
        Assert.assertTrue(isItemMenuCorrect(getMultiConfigurationProjectMenu(), TestUtils.MULTI_CONFIGURATION_PROJECT));
    }

    @Test(dependsOnMethods = "testMultiConfigurationProjectChevronMenu")
    public void testFolderChevronMenu() {
        Assert.assertTrue(isItemMenuCorrect(getFolderMenu(), TestUtils.FOLDER));
    }

    @Test(dependsOnMethods = "testFolderChevronMenu")
    public void testMultibranchPipelineChevronMenu() {
        Assert.assertTrue(isItemMenuCorrect(getMultibranchPipelineMenu(), TestUtils.MULTIBRANCH_PIPELINE));
    }

    @Test(dependsOnMethods = "testMultibranchPipelineChevronMenu")
    public void testOrganizationChevronMenu() {
        Assert.assertTrue(isItemMenuCorrect(getOrganizationFolderMenu(), TestUtils.ORGANIZATION_FOLDER));
    }

    @Test(dependsOnMethods = "testOrganizationChevronMenu")
    public void testCreateView() {
        createItemsFromList(projectsNames);

        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@title='New View']"))).click();
        getDriver().findElement(By.id("name")).sendKeys(VIEW_NAME);
        getDriver().findElement(By.xpath("//label[@for='hudson.model.ListView']")).click();
        getDriver().findElement(By.id("ok")).click();
        clickElement(getDriver().findElement(By.name("Submit")));

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@class='tab active']")).getText(), VIEW_NAME);
    }


    @Test(dependsOnMethods = "testCreateView")
    public void testSortItemsByName() {
        Collections.sort(projectsNames);
        Collections.reverse(projectsNames);
        boolean isDescendingSortingOk = getItemNamesFromColumnAfterSortingByName().equals(projectsNames);

        Collections.reverse(projectsNames);
        boolean isAscendingSortingOk = getItemNamesFromColumnAfterSortingByName().equals(projectsNames);

        Assert.assertTrue(isAscendingSortingOk && isDescendingSortingOk);
    }

    @Test(dependsOnMethods = "testSortItemsByName")
    public void testAddItemsToView() {
        getDriver().findElement(By.linkText(VIEW_NAME)).click();
        getDriver().findElement(By.linkText("Edit View")).click();

        WebElement selectedProject1 = getDriver().findElement(
                By.xpath("//label[contains(@title, '" + SELECTED_NAME1 + "')]"));

        WebElement selectedProject2 = getDriver().findElement(
                By.xpath("//label[contains(@title, '" + SELECTED_NAME2 + "')]"));

        WebElement okButton = getDriver().findElement(
                By.name("Submit"));

        clickElement(selectedProject1);
        clickElement(selectedProject2);
        clickElement(okButton);

        List<String> namesFromNewView = TestUtils.getTexts
                (getDriver().findElements(By.xpath("//td/a[contains(@href, 'job/')]")));
        boolean isName1InView = namesFromNewView.stream().anyMatch(s -> s.contains(SELECTED_NAME1));
        boolean isName2InView = namesFromNewView.stream().anyMatch(s -> s.contains(SELECTED_NAME2));

        Assert.assertTrue(namesFromNewView.size() == 2 && isName1InView && isName2InView);
    }
}
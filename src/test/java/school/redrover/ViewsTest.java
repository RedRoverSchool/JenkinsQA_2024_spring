package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.model.ViewMyListConfigPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.ArrayList;
import java.util.List;

public class ViewsTest extends BaseTest {

    private static final String MY_VIEW_NAME = "EmpoyeeView";
    final String VIEW_NAME = "in progress";
    final String VISIBLE = "visible";

    @Test
    public void testGoToMyViewsFromHeaderUserMenu() {
        WebElement userElement = getDriver().findElement(By.cssSelector("div.login a[href ^= '/user/']"));
        TestUtils.openElementDropdown(this, userElement);
        getDriver().findElement(By.cssSelector("div.tippy-box [href $= 'my-views']")).click();
        Assert.assertTrue(getWait10().until(ExpectedConditions.urlContains("my-views/view/all")));
    }

    @Test
    public void testGoToMyViewsFromUsernameDropdown() {
        String views = "My Views";

        boolean textVisibility = new HomePage(getDriver())
                .clickMyViewsFromDropdown()
                .isThereTextInBreadcrumbs(views);

        Assert.assertTrue(textVisibility,"'My Views' didn't open");
    }

    public void createView(String VIEW_NAME) {
        new HomePage(getDriver())
                .clickPlusForCreateView()
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
                .clickPlusForCreateView()
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
    public void testAddDescriptionColumnToPipelineView() {
        final String pipelineName = "NewPipeline";

        final List<String> expectedPipelineViewHeader =
                List.of("S", "W", "Name" + "\n" + "  ↓", "Last Success", "Last Failure", "Last Duration", "Description");

        TestUtils.createNewItem(this, pipelineName, TestUtils.Item.PIPELINE);

        getDriver().findElement(By.xpath("//a[@href='/newView']")).click();
        getDriver().findElement(By.id("name")).sendKeys(MY_VIEW_NAME);
        getDriver().findElement(By.xpath("//label[contains(text(),'List View')]")).click();
        getDriver().findElement(By.id("ok")).click();

        getWait5().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//label[@title='" + pipelineName + "']"))).click();

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        WebElement scrollStopPoint = getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[@name='Submit']")));
        js.executeScript("arguments[0].scrollIntoView();", scrollStopPoint);

        getWait10().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@suffix='columns']"))).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[@class='jenkins-dropdown__item'])[last()]"))).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        List<String> actualPipelineViewHeader = new ArrayList<>();
        List<WebElement> projectViewTitles = getWait10().until(ExpectedConditions.numberOfElementsToBeMoreThan(
                By.xpath("//table[@id='projectstatus']//thead//tr/th"), 7));
        for (WebElement headerTitle : projectViewTitles) {
            String header = headerTitle.getText();
            if (!header.isEmpty()) {
                actualPipelineViewHeader.add(headerTitle.getText());
            }
        }

        Assert.assertTrue(projectViewTitles.get(projectViewTitles.size() - 1).getText().contains("Description"));
        Assert.assertEquals(actualPipelineViewHeader, expectedPipelineViewHeader);
    }

    @Test(dependsOnMethods = "testAddDescriptionColumnToPipelineView")
    public void testAlterOrderViewTitles() {
        final List<String> expectedAlteredPipelineViewHeader = List.of("Description", "S", "W", "Name" + "\n" + "  ↓", "Last Success", "Last Failure", "Last Duration");
        getDriver().findElement(By.xpath("//a[@href='/view/" + MY_VIEW_NAME + "/']")).click();
        getDriver().findElement(By.xpath("//a[@href='/view/" + MY_VIEW_NAME + "/configure']")).click();

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        WebElement scrollStopPoint = getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[@name='Submit']")));
        js.executeScript("arguments[0].scrollIntoView();", scrollStopPoint);

        WebElement sourceElementDescription = getDriver().findElement(
                By.xpath("//div[@descriptorid='jenkins.branch.DescriptionColumn']//div[@class='dd-handle']"));

        WebElement targetElementStatus = getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@descriptorid='hudson.views.StatusColumn']")));

        Actions actions = new Actions(getDriver());
        actions.clickAndHold(sourceElementDescription)
                .moveToElement(targetElementStatus)
                .release(targetElementStatus)
                .build()
                .perform();

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        List<String> actualAlteredPipelineViewHeader = new ArrayList<>();
        List<WebElement> projectViewTitles = getWait5().until(ExpectedConditions.numberOfElementsToBe(
                By.xpath("//table[@id='projectstatus']//thead//tr/th"), 8));
        for (WebElement headerTitle : projectViewTitles) {
            String header = headerTitle.getText();
            if (!header.isEmpty()) {
                actualAlteredPipelineViewHeader.add(headerTitle.getText());
            }
        }

        Assert.assertTrue(actualAlteredPipelineViewHeader.get(0).contains("Description"));
        Assert.assertEquals(actualAlteredPipelineViewHeader, expectedAlteredPipelineViewHeader);
    }

    @Test(dependsOnMethods = "testAlterOrderViewTitles")
    public void testDeletePipelineView() {
        getDriver().findElement(By.xpath("//a[@href='/view/" + MY_VIEW_NAME + "/']")).click();
        getDriver().findElement(By.xpath("//a[@data-title='Delete View']")).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-id='ok']"))).click();

        Assert.assertEquals(getDriver().findElements(By.xpath("//div[@class='tabBar']/div")).size(), 2);
    }
}
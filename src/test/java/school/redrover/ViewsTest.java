package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.ArrayList;
import java.util.List;

public class ViewsTest extends BaseTest {

    private static final String MY_VIEW_NAME = "EmpoyeeView";

    @Test
    public void testGoToMyViewsFromHeaderUserMenu() {
        WebElement userElement = getDriver().findElement(By.cssSelector("div.login a[href ^= '/user/']"));
        TestUtils.openElementDropdown(this, userElement);
        getDriver().findElement(By.cssSelector("div.tippy-box [href $= 'my-views']")).click();
        Assert.assertTrue(getWait10().until(ExpectedConditions.urlContains("my-views/view/all")));
    }

    @Test
    public void testGoToMyViewFromUsernameDropdown() {
        new Actions(getDriver())
                .moveToElement(getDriver().findElement(By.cssSelector("[data-href$='admin']")))
                .pause(1000)
                .click()
                .perform();

        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[href$='admin/my-views']"))).click();

        Assert.assertTrue(getDriver().findElement(By.cssSelector("[href$='my-views/']")).isDisplayed());
    }

    final String VIEW_NAME = "in progress";
    final String VISIBLE = "visible";

    public void createView(String VIEW_NAME) {
        getDriver().findElement(By.cssSelector("[tooltip='New View']")).click();
        getDriver().findElement(By.id("name")).sendKeys(VIEW_NAME);
        getDriver().findElement(By.cssSelector("[for$='ListView']")).click();
        getDriver().findElement(By.id("ok")).click();
        getDriver().findElement(By.cssSelector("label[title=" + VISIBLE + "]")).click();
        getDriver().findElement(By.name("Submit")).click();
    }

    @Test
    public void testDisplayViewWithListViewConstraints() {
        final String INVISIBLE = "invisible";

        TestUtils.createNewItemAndReturnToDashboard(this, VISIBLE, TestUtils.Item.FOLDER);
        TestUtils.createNewItemAndReturnToDashboard(this, INVISIBLE, TestUtils.Item.PIPELINE);

        getDriver().findElement(By.cssSelector("[tooltip='New View']")).click();
        getDriver().findElement(By.id("name")).sendKeys(VIEW_NAME);
        getDriver().findElement(By.cssSelector("[for$='ListView']")).click();
        getDriver().findElement(By.id("ok")).click();
        getDriver().findElement(By.cssSelector("label[title=" + VISIBLE + "]")).click();
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertTrue(
                getDriver().findElements(By.cssSelector("[id^='job']")).size() == 1 &&
                        getDriver().findElement(By.cssSelector(String.format("tr [href='job/%s/']", VISIBLE))).getText().equals(VISIBLE),
                "Error displaying projects in View");
    }

    @Test
    public void testAddColumnIntoListView() {
        TestUtils.createNewItemAndReturnToDashboard(this, VISIBLE, TestUtils.Item.FOLDER);
        createView(VIEW_NAME);

        getDriver().findElement(By.linkText("Edit View")).click();

        WebElement addColumn = getDriver().findElement(By.cssSelector("[suffix='columns']>svg"));
        ((JavascriptExecutor) getDriver()).executeScript("return arguments[0].scrollIntoView(true);", addColumn);
        addColumn.click();

        getDriver().findElement(By.cssSelector("div.jenkins-dropdown button:last-child")).click();
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.id("jenkins-home-link")).click();
        getDriver().findElement(By.linkText(VIEW_NAME)).click();

        Assert.assertEquals(
                getDriver().findElements(By.className("sortheader")).size(),
                7,
                "Description column is not added");
    }

    @Test(dependsOnMethods = "testAddColumnIntoListView")
    public void testChangeOrderOfColumns() {
        getDriver().findElement(By.linkText("in progress")).click();
        getDriver().findElement(By.linkText("Edit View")).click();

        WebElement submit = getDriver().findElement(By.name("Submit"));
        ((JavascriptExecutor) getDriver()).executeScript("return arguments[0].scrollIntoView(true);", submit);

        WebElement sourceElement = getDriver().findElement(By.cssSelector("[descriptorid $= 'DescriptionColumn'] .dd-handle"));
        WebElement targetElement = getDriver().findElement(By.cssSelector("[descriptorid $= 'StatusColumn']"));
        new Actions(getDriver())
                .clickAndHold(sourceElement)
                .moveToElement(targetElement)
                .release(targetElement)
                .build()
                .perform();

        submit.click();

        List<WebElement> actualOrder = getDriver().findElements(By.className("sortheader"));
        List<String> actualColumns = new ArrayList<>();
        for (WebElement column : actualOrder) {
            actualColumns.add(column.getText());
        }
        Assert.assertEquals(actualColumns.get(0), "Description");
    }

    @Test
    public void testAddDescriptionColumnToPipelineView() {
        final String pipelineName = "NewPipeline";

        final List<String> expectedPipelineViewHeader =
                List.of("S", "W", "Name" + "\n" + "  ↓", "Last Success", "Last Failure", "Last Duration", "Description");

        TestUtils.createNewItemAndReturnToDashboard(this, pipelineName, TestUtils.Item.PIPELINE);

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
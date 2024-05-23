package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.model.HeaderBlock;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.List;

public class SearchBoxTest extends BaseTest {
    private final static String UPPER_CASE_INPUT = "Log";
    private final static String LOWER_CASE_INPUT = "log";
    private static final String PIPELINE_NAME = "Pipeline";

    @Test
    public void testSearchWithValidData() {
        String systemPageTitle = new HeaderBlock(getDriver())
                .enterRequestIntoSearchBox("config")
                .makeClickToSearchBox()
                .getTitleText();

        Assert.assertEquals(systemPageTitle,"System");
    }

    @Test
    public void testSearchUsingSuggestList() {
        String systemPageTitle = new HeaderBlock(getDriver())
                .enterRequestIntoSearchBox("c")
                .chooseAndClickFirstSuggestListVariant()
                .makeClickToSearchBox()
                .getTitleText();

        Assert.assertEquals(systemPageTitle, "System");
    }

    @Ignore
    @Test
    public void testFindFolderByOneLetter() {
        final String lowerCaseLetter = "f";
        List<String> folders = new ArrayList<>(List.of("Folder1", "Folder2", "Folder3"));
        folders.forEach(this::createFolder);
        getWait60();
        WebElement searchBox = getDriver().findElement(By.id("search-box"));
        searchBox.sendKeys(lowerCaseLetter);
        searchBox.sendKeys(Keys.ENTER);

        WebElement ol = getDriver().findElement(By.xpath("//div[@id='main-panel']//ol"));
        List<WebElement> elements = ol.findElements(By.tagName("li"));
        elements.get(1).findElement(By.tagName("a")).click();
        WebElement actualElement = getDriver().findElement(By.xpath("//h1"));
        String expected = folders.get(0);

        Assert.assertEquals(actualElement.getText(), expected);
        Assert.assertTrue(getDriver().getCurrentUrl().contains(expected));
        Assert.assertTrue(getDriver().getTitle().contains(expected));
    }

    @Test
    public void testSearchWithCaseSensitiveOn() {
        new HeaderBlock(getDriver()).goToAdminConfigurePage()
                .turnInsensitiveSearch(false)
                .clickApplyButton();

        final String searchResult1 = new HeaderBlock(getDriver())
                .typeSearchQueryPressEnter(UPPER_CASE_INPUT)
                .getNoMatchText();
        final String searchResult2 = new HeaderBlock(getDriver())
                .typeSearchQueryPressEnter(LOWER_CASE_INPUT)
                .getMatchLogResult();

        Assert.assertFalse(searchResult1.matches(searchResult2));
    }

    @Test
    public void testSearchWithCaseSensitiveOff() {
        new HeaderBlock(getDriver()).goToAdminConfigurePage()
                .turnInsensitiveSearch(true)
                .clickApplyButton();

        final String searchResult1 = new HeaderBlock(getDriver())
                .typeSearchQueryPressEnter(UPPER_CASE_INPUT)
                .getMatchLogResult();
        final String searchResult2 = new HeaderBlock(getDriver())
                .typeSearchQueryPressEnter(LOWER_CASE_INPUT)
                .getMatchLogResult();

        Assert.assertTrue(searchResult1.matches(searchResult2));
    }

    @Test
    public void testSearchBox(){
        createNewPipeline(PIPELINE_NAME);
        goHomePage();

        WebElement searchBox = getWait5().until(ExpectedConditions.presenceOfElementLocated(By.id("search-box")));
        searchBox.sendKeys(PIPELINE_NAME);
        searchBox.sendKeys(Keys.ENTER);

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1[@class='job-index-headline page-headline']")).getText(), PIPELINE_NAME);
    }

    public void goHomePage(){
        getWait5().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//li[@class='jenkins-breadcrumbs__list-item']"))).click();
    }

    public void createFolder(String folderName) {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.name("name")).sendKeys(folderName);
        getDriver().findElement(By.xpath("//label/span[text() ='Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        HomePage homePage = new HomePage(getDriver());
        homePage.clickLogo();
    }

    public void createNewPipeline(String pipelineName){
        getWait5().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@href='/view/all/newJob']"))).click();
        getWait5().until(ExpectedConditions.presenceOfElementLocated(By.id("name"))).sendKeys(pipelineName);
        getDriver().findElement(By.xpath("//span[text()='Pipeline']")).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@name='Submit']"))).click();
    }

    @Test
    public void testStartSearchBox() {
        final String EXPECTED_RESULT_TEXT = "manage";
        final String SEARCHING_TEXT = "ma";

        String searchResult = new HomePage(getDriver())
                .getHeader().typeTextToSearchBox(SEARCHING_TEXT)
                .getTextFromMainPanel();

        Assert.assertTrue(searchResult.contains(EXPECTED_RESULT_TEXT));
    }

    @Test
    public void testSearchResultHeading() {
        final String SEARCHING_TEXT = "i";

        String resultHeading = new HomePage(getDriver())
                .getHeader().typeTextToSearchBox(SEARCHING_TEXT)
                .getMatchLogResult();

        String expectedSearchResult = "Search for '%s'".formatted(SEARCHING_TEXT);

        Assert.assertEquals(resultHeading, expectedSearchResult);
    }

    @Test
    public void testAccessToUserDoc(){
        getDriver().findElement(By.xpath("//a[@class='main-search__icon-trailing']")).click();

        String actualHandbook = getWait10().until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='sidebar-content']/h5"))).getText();
        String actualTutorials = getDriver().findElement(By.xpath("(//div[@id='sidebar-content']/h5)[2]")).getText();
        String actualResources = getDriver().findElement(By.xpath("(//div[@id='sidebar-content']/h5)[3]")).getText();

        Assert.assertEquals(actualHandbook, "User Handbook");
        Assert.assertEquals(actualTutorials, "Tutorials");
        Assert.assertEquals(actualResources, "Resources");
    }
}
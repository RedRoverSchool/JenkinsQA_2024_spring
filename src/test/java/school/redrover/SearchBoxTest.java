package school.redrover;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.model.PipelineProjectPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.ArrayList;
import java.util.List;

@Epic("Header")
public class SearchBoxTest extends BaseTest {
    private static final String UPPER_CASE_INPUT = "Log";
    private static final String LOWER_CASE_INPUT = "log";
    private static final String PIPELINE_NAME = "Pipeline";

    @Test
    @Story("US_14.002 Search box")
    @Description("Check redirection for valid data search input")
    public void testSearchWithValidData() {
        String systemPageTitle = new HomePage(getDriver())
                .getHeader()
                .typeSearchQueryAndPressEnter("config")
                .getHeadingText();

        Allure.step("Expected result: Redirected to correct page");
        Assert.assertEquals(systemPageTitle, "System");
    }

    @Test
    @Story("US_14.002 Search box")
    @Description("Check redirection from search box suggestion list")
    public void testSearchUsingSuggestList() {
        String systemPageTitle = new HomePage(getDriver())
                .getHeader()
                .typeTextToSearchField("c")
                .getHeader()
                .chooseAndClickFirstSuggestListVariant()
                .getHeader()
                .pressEnterOnSearchField()
                .getHeadingText();

        Allure.step("Expected result: Redirected to correct page");
        Assert.assertEquals(systemPageTitle, "System");
    }

    @Test
    @Story("US_14.002 Search box")
    @Description("Check search for folder projects by first letter")
    public void testFindFolderByOneLetter() {
        final String firstLetterOfFolderName = "F";

        List<String> folders = new ArrayList<>(List.of("Folder1", "Folder2", "Folder3"));
        folders.forEach(this::createFolder);

        List<String> searchResult = new HomePage(getDriver())
                .getHeader()
                .typeSearchQueryAndPressEnter(firstLetterOfFolderName)
                .getSearchResult();

        Allure.step("Expected result: All folders are present in search results");
        Assert.assertTrue(searchResult.containsAll(folders), "Folders aren't found");
    }

    public void createFolder(String folderName) {
        TestUtils.createFolderProject(this, folderName);
    }

    @Test
    @Story("US_14.002 Search box")
    @Description("Check case sensitive search")
    public void testSearchWithInsensitiveSearchOff() {
        TestUtils.setInsensitiveSearchUserSetting(this, false);

        final String uppercaseResult = new HomePage(getDriver())
                .getHeader()
                .typeSearchQueryAndPressEnter(UPPER_CASE_INPUT)
                .getNoMatchText();

        final String lowercaseResult = new HomePage(getDriver())
                .getHeader()
                .typeSearchQueryAndPressEnter(LOWER_CASE_INPUT)
                .getMatchLogResult();

        Allure.step("Expected result: Different search results for uppercase and lowercase input");
        Assert.assertNotEquals(uppercaseResult, lowercaseResult);
    }

    @Test
    @Story("US_14.002 Search box")
    @Description("Check case insensitive search")
    public void testSearchWithInsensitiveSearchOn() {
        TestUtils.setInsensitiveSearchUserSetting(this, true);

        final String uppercaseResult = new HomePage(getDriver())
                .getHeader()
                .typeSearchQueryAndPressEnter(UPPER_CASE_INPUT)
                .getMatchLogResult();

        final String lowercaseResult = new HomePage(getDriver())
                .getHeader()
                .typeSearchQueryAndPressEnter(LOWER_CASE_INPUT)
                .getMatchLogResult();

        Allure.step("Expected result: Same search results for uppercase and lowercase input");
        Assert.assertEquals(uppercaseResult, lowercaseResult);
    }

    @Test
    @Story("US_14.002 Search box")
    @Description("Check search for pipeline project by full name")
    public void testSearchPipeline() {
        TestUtils.createPipelineProject(this, PIPELINE_NAME);

        String searchResult = new HomePage(getDriver())
                .getHeader()
                .typeProjectNameToSearchInputFieldAndPressEnter(
                        PIPELINE_NAME,
                        new PipelineProjectPage(getDriver()))
                .getHeadingText();

        Allure.step("Expected result: Correct search results");
        Assert.assertEquals(searchResult, PIPELINE_NAME, "Pipeline is not found");
    }

    @Test
    @Story("US_14.002 Search box")
    @Description("Check search results")
    public void testStartSearchBox() {
        final String expectedResultText = "manage";
        final String searchingText = "ma";

        String searchResult = new HomePage(getDriver())
                .getHeader()
                .typeSearchQueryAndPressEnter(searchingText)
                .getTextFromMainPanel();

        Allure.step("Expected result: Correct search results");
        Assert.assertTrue(searchResult.contains(expectedResultText));
    }

    @Test
    @Story("US_14.002 Search box")
    @Description("Check heading for search results page")
    public void testSearchResultHeading() {
        final String searchingText = "i";

        String resultHeading = new HomePage(getDriver())
                .getHeader()
                .typeSearchQueryAndPressEnter(searchingText)
                .getMatchLogResult();

        Allure.step("Expected result: Correct heading displayed");
        Assert.assertEquals(resultHeading, "Search for '%s'".formatted(searchingText));
    }

    @Test
    @Story("US_14.006 Search-help (Question mark)")
    @Description("Check tutorial icon redirection")
    public void testAccessToUserDoc() {
        String tutorialPageTitle = new HomePage(getDriver())
                .openTutorial()
                .getHeadingText();

        Allure.step("Expected result: Redirection to search box tutorial page");
        Assert.assertEquals(tutorialPageTitle, "Search Box");
    }
}

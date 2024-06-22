package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.model.PipelineProjectPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.ArrayList;
import java.util.List;

public class SearchBoxTest extends BaseTest {
    private static final String UPPER_CASE_INPUT = "Log";
    private static final String LOWER_CASE_INPUT = "log";
    private static final String PIPELINE_NAME = "Pipeline";

    @Test
    public void testSearchWithValidData() {
        String systemPageTitle = new HomePage(getDriver())
                .getHeader().typeSearchQueryPressEnter("config")
                .getHeadingText();

        Assert.assertEquals(systemPageTitle, "System");
    }

    @Test
    public void testSearchUsingSuggestList() {
        String systemPageTitle = new HomePage(getDriver())
                .getHeader().typeTextToSearchField("c")
                .getHeader().chooseAndClickFirstSuggestListVariant()
                .getHeader().pressEnterOnSearchField()
                .getHeadingText();

        Assert.assertEquals(systemPageTitle, "System");
    }

    @Test
    public void testFindFolderByOneLetter() {
        final String firstLetterOfFolderName = "F";

        List<String> folders = new ArrayList<>(List.of("Folder1", "Folder2", "Folder3"));
        folders.forEach(this::createFolder);

        List<String> searchResult = new HomePage(getDriver())
                .getHeader().typeTextToSearchField(firstLetterOfFolderName)
                .getHeader().pressEnterOnSearchField()
                .getSearchResult();

        Assert.assertTrue(searchResult.containsAll(folders), "Folders aren't found");
    }

    public void createFolder(String folderName) {
        TestUtils.createFolderProject(this, folderName);
    }

    @Test
    public void testSearchWithInsensitiveSearchOff() {
        TestUtils.setInsensitiveSearchUserSetting(this, false);

        final String searchResult1 = new HomePage(getDriver())
                .getHeader().typeSearchQueryPressEnter(UPPER_CASE_INPUT)
                .getNoMatchText();
        final String searchResult2 = new HomePage(getDriver())
                .getHeader().typeSearchQueryPressEnter(LOWER_CASE_INPUT)
                .getMatchLogResult();

        Assert.assertFalse(searchResult1.matches(searchResult2));
    }

    @Test
    public void testSearchWithInsensitiveSearchOn() {
        TestUtils.setInsensitiveSearchUserSetting(this, true);

        final String searchResult1 = new HomePage(getDriver())
                .getHeader().typeSearchQueryPressEnter(UPPER_CASE_INPUT)
                .getMatchLogResult();
        final String searchResult2 = new HomePage(getDriver())
                .getHeader().typeSearchQueryPressEnter(LOWER_CASE_INPUT)
                .getMatchLogResult();

        Assert.assertTrue(searchResult1.matches(searchResult2));
    }

    @Test
    public void testSearchPipeline() {
        String searchResult = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickLogo()
                .getHeader().typeProjectNameToSearchInputFieldAndPressEnter(
                        PIPELINE_NAME, new PipelineProjectPage(getDriver()))
                .getHeadingText();

        Assert.assertEquals(searchResult, PIPELINE_NAME,  "Pipeline is not found");
    }

    @Test
    public void testStartSearchBox() {
        final String expectedResultText = "manage";
        final String searchingText = "ma";

        String searchResult = new HomePage(getDriver())
                .getHeader().typeSearchQueryPressEnter(searchingText)
                .getTextFromMainPanel();

        Assert.assertTrue(searchResult.contains(expectedResultText));
    }

    @Test
    public void testSearchResultHeading() {
        final String searchingText = "i";

        String resultHeading = new HomePage(getDriver())
                .getHeader().typeSearchQueryPressEnter(searchingText)
                .getMatchLogResult();

        String expectedSearchResult = "Search for '%s'".formatted(searchingText);

        Assert.assertEquals(resultHeading, expectedSearchResult);
    }

    @Test
    public void testAccessToUserDoc() {
        String tutorialPageTitle = new HomePage(getDriver())
                .openTutorial()
                .getHeadingText();

        Assert.assertEquals(tutorialPageTitle, "Search Box");
    }
}

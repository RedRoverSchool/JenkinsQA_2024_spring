package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HeaderBlock;
import school.redrover.model.HomePage;
import school.redrover.model.PipelineProjectPage;
import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.List;

public class SearchBoxTest extends BaseTest {
    private static final String UPPER_CASE_INPUT = "Log";
    private static final String LOWER_CASE_INPUT = "log";
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

    @Test
    public void testFindFolderByOneLetter() {
        final String firstLetterOfFolderName = "F";

        List<String> folders = new ArrayList<>(List.of("Folder1", "Folder2", "Folder3"));
        folders.forEach(this::createFolder);

        List<String> searchResult = new HomePage(getDriver())
                .typeTextToSearchBox(firstLetterOfFolderName)
                .getSearchResult();

        Assert.assertTrue(searchResult.containsAll(folders), "Folders aren't found");
    }

    public void createFolder(String folderName) {
        new HomePage(getDriver())
                .clickNewItem()
                .setItemName(folderName)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .clickLogo();
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
    public void testSearchPipeline(){
        String searchResult = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickLogo()
                .searchProjectByName(PIPELINE_NAME, new PipelineProjectPage(getDriver()))
                .getProjectName();

        Assert.assertEquals(searchResult, PIPELINE_NAME,  "Pipeline is not found");
    }

    @Test
    public void testStartSearchBox() {
        final String EXPECTED_RESULT_TEXT = "manage";
        final String SEARCHING_TEXT = "ma";

        String searchResult = new HomePage(getDriver())
                .typeTextToSearchBox(SEARCHING_TEXT)
                .getTextFromMainPanel();

        Assert.assertTrue(searchResult.contains(EXPECTED_RESULT_TEXT));
    }

    @Test
    public void testSearchResultHeading() {
        final String SEARCHING_TEXT = "i";

        String resultHeading = new HomePage(getDriver())
                .typeTextToSearchBox(SEARCHING_TEXT)
                .getMatchLogResult();

        String expectedSearchResult = "Search for '%s'".formatted(SEARCHING_TEXT);

        Assert.assertEquals(resultHeading, expectedSearchResult);
    }

    @Test
    public void testAccessToUserDoc(){
        String tutorialPageTitle = new HomePage(getDriver())
                .openTutorial()
                .getHeaderOneText();

        Assert.assertEquals(tutorialPageTitle, "Search Box");
    }
}
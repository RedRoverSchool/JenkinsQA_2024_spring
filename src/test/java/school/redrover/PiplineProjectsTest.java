package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.CreateNewItemPage;
import school.redrover.model.HeaderBlock;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;

public class PiplineProjectsTest extends BaseTest {

    private final String PIPELINE = "PipelineProject";

    @Test
    public void testCreatePipline() {

        List<String> itemPipline = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PIPELINE)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .getItemList();

        Assert.assertTrue(itemPipline.contains(PIPELINE));
    }

    @Test(dependsOnMethods = "testCreatePipline")
    public void testCreatePiplineSameName() {

        String itemPipline = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PIPELINE)
                .clickOkAnyway(new CreateNewItemPage(getDriver()))
                .getErrorMessage();

        Assert.assertEquals(itemPipline, "» A job already exists with the name ‘" + PIPELINE + "’");
    }

    @Test
    public void testCreatePiplineEmptyName() {

        String itemPipline = new HomePage(getDriver())
                .clickNewItem()
                .setItemName("")
                .clickOkAnyway(new CreateNewItemPage(getDriver()))
                .getItemNameHintText();

        Assert.assertEquals(itemPipline, "» This field cannot be empty, please enter a valid name");
    }

    @Test(dependsOnMethods = "testCreatePipline")
    public void testFindPiplineProject() {

        String searchResult = new HeaderBlock(getDriver())
                .enterRequestIntoSearchBox(PIPELINE)
                .makeClickToSearchBox()
                .getTitleText();

        Assert.assertEquals(searchResult, PIPELINE);
    }
}

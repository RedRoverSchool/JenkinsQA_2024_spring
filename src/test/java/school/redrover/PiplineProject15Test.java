package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HeaderBlock;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;

public class PiplineProject15Test extends BaseTest {

    @Test
    public void testCreatePipline() {

        List<String> itemPipline = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(TestUtils.PIPELINE)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .getItemList();

        Assert.assertTrue(itemPipline.contains(TestUtils.PIPELINE));
    }

    @Test(dependsOnMethods = "testCreatePipline")
    public void testFindPiplineProject() {

        String searchResult = new HeaderBlock(getDriver())
                .enterRequestIntoSearchBox(TestUtils.PIPELINE)
                .makeClickToSearchBox()
                .getTitleText();

        Assert.assertEquals(searchResult, TestUtils.PIPELINE);
    }

    @Test(dependsOnMethods = "testCreatePipline")
    public void testCreatePiplineSameName() {

        getDriver().findElement(By.xpath("//*[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.cssSelector("#name")).sendKeys(TestUtils.PIPELINE);

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//*[@id='itemname-invalid']")).getText(),
                "» A job already exists with the name ‘" + TestUtils.PIPELINE + "’");
    }

    @Test
    public void testCreatePiplineEmptyName() {

        getDriver().findElement(By.xpath("//*[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//span[text()='Pipeline']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//*[@id='itemname-required']")).getText(),
                "» This field cannot be empty, please enter a valid name");
    }
}

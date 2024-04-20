package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class PipelineDescription extends BaseTest {

    @Test
    public void editPipelineDescription() {
        TestUtils.createItem(TestUtils.FREESTYLE_PROJECT, "test", getDriver());
        getDriver().findElement(By.xpath("//a[@id='description-link']")).click();
        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys("description");
        getDriver().findElement(By.xpath("//div[@id='description']//button[@formnovalidate='formNoValidate']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='description']//div")).getText(), "description");
    }
}

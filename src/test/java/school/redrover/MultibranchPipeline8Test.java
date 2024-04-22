package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class MultibranchPipeline8Test extends BaseTest {

    @Test
    public void testNewMultibranchPipelineIsEmpty() {
        final String multibranchPipelineName = "FidelityNewPipeline";
        final String thisFolderIsEmptyMessage = "This folder is empty";

        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.name("name")).sendKeys(multibranchPipelineName);
        getDriver().findElement(By.xpath("//label/span[text() ='Multibranch Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        TestUtils.goToMainPage(getDriver());

        getDriver().findElement(By.linkText(multibranchPipelineName)).click();

        final String actualMultibranchPipelineName = getDriver().findElement(By.xpath("//h1")).getText();
        final String actualEmptyStateMessage = getDriver().findElement(By.xpath("//section[@class='empty-state-section']/h2")).getText();

        Assert.assertEquals(actualMultibranchPipelineName, multibranchPipelineName);
        Assert.assertEquals(actualEmptyStateMessage, thisFolderIsEmptyMessage);
    }
}



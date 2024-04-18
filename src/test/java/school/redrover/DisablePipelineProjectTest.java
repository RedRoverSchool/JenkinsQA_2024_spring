package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;

import static school.redrover.runner.TestUtils.goToMainPage;
import static school.redrover.runner.TestUtils.sleep;

public class DisablePipelineProjectTest extends BaseTest {

    private void CreatePipelineProject() {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys("MyNewPipeline");
        getDriver().findElement(By.cssSelector("#j-add-item-type-standalone-projects > ul > li:nth-child(2)")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals("Stage View\n" +
                "No data available. This Pipeline has not yet run.", getDriver().findElement(By.id("pipeline-box")).getText());
    }

    @Test
    public void testDisablePipelineProject() {
        CreatePipelineProject();
        getDriver().findElement(By.cssSelector("#tasks > div:nth-child(4) > span > a")).click();

        sleep(this, 5);

        Assert.assertEquals("Stage View\n" +
                "This Pipeline has run successfully, but does not define any stages. Please use the stage step to define some stages in this Pipeline.", getDriver().findElement(By.id("pipeline-box")).getText());

        getDriver().findElement(By.cssSelector("#disable-project > button")).click();

        Assert.assertEquals("This project is currently disabled\n" + "Enable", getDriver().findElement(By.id("enable-project")).getText());

        List<WebElement> listItems = getDriver().findElements(By.className("task"));
        String[] myArray = listItems.stream().map(WebElement::getText).toArray(String[]::new);
        for (String s : myArray) {
            if (s.equals("Build Now")) {
                throw new RuntimeException("It's possible to run this pipeline job");
            }
        }

        goToMainPage(getDriver());

        Assert.assertTrue(getDriver().findElement(By.xpath("(//*[name()='svg'][@tooltip='Disabled'])[1]")).isDisplayed());

        getDriver().findElement(By.linkText("MyNewPipeline")).click();
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.cssSelector("#tasks > div:nth-child(4) > span > a")).click();

        sleep(this, 5);

        Assert.assertTrue(getDriver().findElement(By.cssSelector("#disable-project > button")).isDisplayed());

        List<WebElement> buildList = getDriver().findElements(By.className("build-row-cell"));
        String[] bArray = buildList.stream().map(WebElement::getText).toArray(String[]::new);

        Assert.assertTrue(bArray.length >= 2);
    }
}

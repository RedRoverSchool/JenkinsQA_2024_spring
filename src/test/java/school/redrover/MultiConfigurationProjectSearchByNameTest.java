package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import static school.redrover.runner.TestUtils.*;

public class MultiConfigurationProjectSearchByNameTest extends BaseTest {
    private final String projectName = "New Multi-configuration project";

    @Ignore
    @Test
    public void testSearchMultiConfigurationProjectByName() {
        createNewItemAndReturnToDashboard(this, projectName, TestUtils.Item.MULTI_CONFIGURATION_PROJECT);

        WebElement searchBox = getDriver().findElement(By.id("search-box"));
        searchBox.sendKeys(projectName);
        searchBox.sendKeys(Keys.ENTER);

        final String actualResult = getDriver().findElement(By.xpath("//div[@id='main-panel']/h1")).getText();

        Assert.assertEquals(actualResult, "Project " + projectName);
    }
}

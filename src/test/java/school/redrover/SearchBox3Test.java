package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HeaderBlock;
import school.redrover.runner.BaseTest;

public class SearchBox3Test extends BaseTest {
    private static final By SEARCH_BOX = By.xpath("//input[@id='search-box']");
    private static final By SYSTEM_PAGE = By.xpath("//h1[.='System']");

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
        getDriver().findElement(SEARCH_BOX).sendKeys("c");
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='yui-ac-bd']//ul//li[.='config']"))).click();
        getDriver().findElement(SEARCH_BOX).sendKeys(Keys.ENTER);

        Assert.assertTrue(getDriver().findElement(SYSTEM_PAGE).isDisplayed());
    }
}

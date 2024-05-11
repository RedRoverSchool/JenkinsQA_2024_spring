package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.model.ItemPage;
import school.redrover.runner.BaseTest;

public class HeaderTest extends BaseTest {

    @Test
    public void testElementPeople() {
        new ItemPage(getDriver())
                .elementPeopleClick();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@class='jenkins-app-bar__content']")).getText(), "People");
    }

    @Test
    public void testElementWelcome() {
        new ItemPage(getDriver())
                .elementWelcomeClic();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1[contains(.,'Welcome to Jenkins!')]")).getText(), "Welcome to Jenkins!");
    }

    @Ignore
    @Test
    public void testSearchBox() {

        getDriver().findElement(By.id("search-box")).sendKeys("ma");
        getDriver().findElement(By.className("yui-ac-bd")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@class='yui-ac-content']//li[1]")).getText(),
                "manage");

        getDriver().findElement(By.id("search-box")).sendKeys(Keys.ENTER);

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), "Manage Jenkins");
    }

    @Ignore
    @Test
    public void testSearchResult() {
        String searchText = "i";
        WebElement headerSearchLine = getDriver().findElement(By.xpath("//input[@id='search-box']"));
        headerSearchLine.click();
        headerSearchLine.sendKeys(searchText);
        headerSearchLine.sendKeys(Keys.ENTER);

        String actualSearchResult = getDriver().findElement(By.xpath("//h1")).getText();
        String expectedSearchResult = "Search for '%s'".formatted(searchText);

        Assert.assertEquals(actualSearchResult, expectedSearchResult);
    }

    @Test
    public void testSearchHelp() {
        getDriver().findElement(By.xpath("//a[@class='main-search__icon-trailing']")).click();
        String webLink = getWait5().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//link[@rel='canonical']"))).getAttribute("href");

        Assert.assertEquals(webLink, "https://www.jenkins.io/doc/book/using/searchbox/");
    }
}
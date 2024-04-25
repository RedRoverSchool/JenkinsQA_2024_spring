package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Nodes4Test extends BaseTest {

    public void createNode() {

        final String nodName = "TestNode";

        getDriver().findElement(By.linkText("Manage Jenkins")).click();
        getDriver().findElement(By.xpath("//a[@href='computer']")).click();
        getDriver().findElement(By.xpath("//a[@href='new']")).click();
        getDriver().findElement(By.id("name")).sendKeys(nodName);
        getDriver().findElement(By.xpath("//label[@class='jenkins-radio__label']")).click();
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.xpath("//button[@formnovalidate='formNoValidate' and @name='Submit']")).click();
    }

    @Test
    public void testDeleteExistingNode() {

        final String searchNode = "TestNode";

        createNode();

        getDriver().findElement(By.linkText(searchNode)).click();
        getDriver().findElement(By.xpath("//a[.='Delete Agent']")).click();
        getDriver().findElement(By.xpath("//button[@data-id='ok']")).click();

        WebElement searchBox = getDriver().findElement(By.id("search-box"));
        searchBox.sendKeys(searchNode);
        searchBox.sendKeys(Keys.ENTER);

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@class='error']")).getText(), "Nothing seems to match.");
    }
}

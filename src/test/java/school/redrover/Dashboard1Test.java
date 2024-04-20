package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.*;

public class Dashboard1Test extends BaseTest {

    public List<String> getNamesList() {
        List<String> names = new ArrayList<>();
        names.add("Imagine Dragons");
        names.add("Queen");
        names.add("A-ha");
        names.add("Depeche Mode");
        names.add("Rammstein");
        names.add("Antonio Vivaldi");
        return names;
    }

    public void createItemsFromList(List<String> list) {
        for (String name : list) {
            TestUtils.createItem(TestUtils.MULTIBRANCH_PIPELINE, name, this);
            TestUtils.goToMainPage(getDriver());
        }
    }

    public List<String> getItemNamesFromColumnAfterSortingByName() {
        getDriver().findElement(By.xpath("//a[@class='sortheader' and text()='Name']")).click();
        return TestUtils.getTexts(getDriver().findElements(By.xpath("//td/a[contains(@href, 'job/')]")));
    }

    @Test
    public void testSortItems() {
        List<String> names = getNamesList();
        createItemsFromList(names);
        Collections.sort(names);
        Collections.reverse(names);

        Assert.assertEquals(getItemNamesFromColumnAfterSortingByName(), names);

        Collections.reverse(names);

        Assert.assertEquals(getItemNamesFromColumnAfterSortingByName(), names);
    }

//    @Ignore
//    @Test
//    public void testCreateViewWithSelectedItem() {
//        createItemsFromList(getNamesList());
//
//        getDriver().findElement(By.xpath("//a[@title='New View']")).click();
//        getDriver().findElement(By.id("Name")).sendKeys("Classic");
//        getDriver().findElement(By.id("hudson.model.ListView")).click();
//        getDriver().findElement(By.id("ok")).click();
//
//    }
}
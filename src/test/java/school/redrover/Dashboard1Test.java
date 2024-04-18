package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import static school.redrover.runner.TestUtils.*;
import java.util.*;

public class Dashboard1Test extends BaseTest {

    public List<String> getItemNamesFromColumnAfterSortingByName() {
        getDriver().findElement(By.xpath("//a[@class='sortheader' and text()='Name']")).click();
        return getTexts(getDriver().findElements(By.xpath("//td/a[contains(@href, 'job/')]")));
    }

    @Test
    public void testSortItems() {
        List<String> names = new ArrayList<>();
        names.add("One");
        names.add("Two");
        names.add("Three");
        names.add("Four");
        names.add("Five");
        names.add("Six");
        names.add("Seven");
        names.add("Eight");
        names.add("Nine");

        for (String name : names) {
            createItem(MULTIBRANCH_PIPELINE, name, getDriver());
            goToMainPage(getDriver());
        }
        Collections.sort(names);
        Collections.reverse(names);

        Assert.assertEquals(getItemNamesFromColumnAfterSortingByName(), names);

        Collections.reverse(names);

        Assert.assertEquals(getItemNamesFromColumnAfterSortingByName(), names);
    }
}
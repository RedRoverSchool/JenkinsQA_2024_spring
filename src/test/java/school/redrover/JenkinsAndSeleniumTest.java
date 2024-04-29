package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.ArrayList;
import java.util.List;

public class JenkinsAndSeleniumTest extends BaseTest {
    private List<String> projectsNames = getNamesList();

    private List<String> getNamesList() {
        List<String> names = new ArrayList<>();
        names.add(TestUtils.FREESTYLE_PROJECT);
        names.add(TestUtils.FOLDER);
        names.add(TestUtils.MULTI_CONFIGURATION_PROJECT);
        return names;
    }

    private void createItemsFromList(List<String> list) {
        for (String name : list) {
            TestUtils.createItem(name, name, this);
            TestUtils.goToMainPage(getDriver());
        }
    }

    @Test
    public void testSelenium() {
        createItemsFromList(projectsNames);

        String jobName = TestUtils.FREESTYLE_PROJECT;
        WebElement job = getDriver().findElement(By.linkText(jobName));
        Actions actions = new Actions(getDriver());

        actions.moveToElement(job)
                .perform();

        WebElement chevron = getDriver().findElement(
                By.xpath("//a[contains(@href, '" + TestUtils.asURL(jobName) + "')]/button"));

        actions.moveToElement(chevron)
                .scrollToElement(chevron)
                .click()
                .perform();

        Assert.assertEquals(1, 2);

        //        Assert.assertEquals(getChevronMenu(TestUtils.FREESTYLE_PROJECT), getFreestyleProjectMenu());
    }
}

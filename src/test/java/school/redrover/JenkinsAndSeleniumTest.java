package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JenkinsAndSeleniumTest extends BaseTest {
    private List<String> projectsNames = getNamesList();

    private List<String> getNamesList() {
        List<String> names = new ArrayList<>();
        names.add(TestUtils.FREESTYLE_PROJECT);
        names.add(TestUtils.MULTIBRANCH_PIPELINE);
        names.add(TestUtils.ORGANIZATION_FOLDER);
        names.add(TestUtils.FOLDER);
        names.add(TestUtils.MULTI_CONFIGURATION_PROJECT);
        names.add(TestUtils.PIPELINE);
        return names;
    }

    private void createItemsFromList(List<String> list) {
        for (String name : list) {
            TestUtils.createItem(name, name, this);
            TestUtils.goToMainPage(getDriver());
        }
    }

    private List<String> getChevronMenu(String jobName) {
        TestUtils.openElementDropdown(this, getDriver().findElement(By.linkText(jobName)));

        WebElement dropdownMenu = getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='jenkins-dropdown']")));

        return Arrays.stream(dropdownMenu.getText().split("\\r?\\n")).toList();
    }

    @Test
    public void testSelenium() {
        createItemsFromList(projectsNames);

        Assert.assertEquals(1, 2);

        //        Assert.assertEquals(getChevronMenu(TestUtils.FREESTYLE_PROJECT), getFreestyleProjectMenu());
    }
}

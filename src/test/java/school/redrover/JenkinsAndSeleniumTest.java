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
import java.util.NoSuchElementException;

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
    public void testSelenium() throws InterruptedException {
        createItemsFromList(projectsNames);
        String jobName = TestUtils.FREESTYLE_PROJECT;
        WebElement job = getDriver().findElement(By.linkText(jobName));
        int jobX = job.getLocation().getX();
        int jobY = job.getLocation().getY();
        int jobSizeX = job.getSize().width;
        int jobSizeY = job.getSize().height;

        Actions actions = new Actions(getDriver());

        actions.moveToElement(job)
                .perform();

        WebElement chevron = getDriver().findElement(
                By.xpath("//a[contains(@href, '" + TestUtils.asURL(jobName) + "')]/button"));

        int chevronSizeX = chevron.getSize().width;
        int chevronSizeY = chevron.getSize().height;

        String dashboardURL = getDriver().getCurrentUrl();
        for (int i = 100; i < 150; i++) {
            actions.moveToLocation(jobX + i, jobY + jobSizeY / 2)
                    .click().pause(200)
                    .perform();

            if (!dashboardURL.equals(getDriver().getCurrentUrl())) {
//                TestUtils.goToMainPage(getDriver());
            } else {
                try {
                    getDriver().findElement(By.xpath("//*[@class='jenkins-dropdown']"));
                    System.out.printf("Menu #%d", i - 102);
                    System.out.println();
                    actions.click().perform();
                } catch (Exception e) {
                    System.out.println("Menu not displayed. Chevron is clicked " + (i - 103) + " times " );
                    break;
                }
            }
        }

        Assert.assertEquals(1, 2);
    }
}
package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.*;

public class Dashboard1Test extends BaseTest {

    private final String SELECTED_NAME1 = "Vivaldi";
    private final String SELECTED_NAME2 = "Nic";

    private List<String> getNamesList() {
        List<String> names = new ArrayList<>();
        names.add("Imagine Dragons");
        names.add("A-ha");
        names.add("Depeche Mode");
        names.add("Antonio Vivaldi");
        names.add("Niccolo Paganini");
        return names;
    }

    private void createItemsFromList(List<String> list) {
        for (String name : list) {
            TestUtils.createItem(TestUtils.MULTIBRANCH_PIPELINE, name, this);
            TestUtils.goToMainPage(getDriver());
        }
    }

    private List<String> getItemNamesFromColumnAfterSortingByName() {
        getDriver().findElement(By.xpath("//a[@class='sortheader' and text()='Name']")).click();
        return TestUtils.getTexts(getDriver().findElements(By.xpath("//td/a[contains(@href, 'job/')]")));
    }

    private void clickElement(WebElement webElement) {
        new Actions(getDriver())
                .scrollToElement(webElement)
                .scrollByAmount(0, 100)
                .moveToElement(webElement)
                .click()
                .perform();
    }

    @Test
    public void testSortItemsByName() {
        List<String> names = getNamesList();
        createItemsFromList(names);
        Collections.sort(names);
        Collections.reverse(names);
        boolean isDescendingSortingOk = getItemNamesFromColumnAfterSortingByName().equals(names);

        Collections.reverse(names);
        boolean isAscendingSortingOk = getItemNamesFromColumnAfterSortingByName().equals(names);

        Assert.assertTrue(isAscendingSortingOk && isDescendingSortingOk);
    }

    @Test(dependsOnMethods = "testSortItemsByName")
    public void testCreateViewWithSelectedItems() {

        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@title='New View']"))).click();
        getDriver().findElement(By.id("name")).sendKeys("Classic");
        getDriver().findElement(By.xpath("//label[@for='hudson.model.ListView']")).click();
        getDriver().findElement(By.id("ok")).click();

        WebElement selectedProject1 = getDriver().findElement(
                By.xpath("//label[contains(@title, '" + SELECTED_NAME1 + "')]"));

        WebElement selectedProject2 = getDriver().findElement(
                By.xpath("//label[contains(@title, '" + SELECTED_NAME2 + "')]"));

        WebElement okButton = getDriver().findElement(
                By.name("Submit"));

        clickElement(selectedProject1);
        clickElement(selectedProject2);
        clickElement(okButton);

        List<String> namesFromNewView = TestUtils.getTexts
                (getDriver().findElements(By.xpath("//td/a[contains(@href, 'job/')]")));
        boolean isName1InView = namesFromNewView.stream().anyMatch(s -> s.contains(SELECTED_NAME1));
        boolean isName2InView = namesFromNewView.stream().anyMatch(s -> s.contains(SELECTED_NAME2));

        Assert.assertTrue(namesFromNewView.size() == 2 && isName1InView && isName2InView);
    }

}
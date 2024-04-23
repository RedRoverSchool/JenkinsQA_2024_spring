package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.List;

public class FooterVersion3Test extends BaseTest {

    private static final String FOOTER_VERSION = "//button[@type='button']";

    private void clickOn(String xPath) {
        getDriver().findElement(By.xpath(xPath)).click();
    }

    private String getText(String xPath) {
        return getDriver().findElement(By.xpath(xPath)).getText();
    }

    @Test
    public void testVersionFooterOnEachPage() {
        List<String> versionList = new ArrayList<>();

        clickOn("//li/a[text()='Dashboard']");
        versionList.add(getText(FOOTER_VERSION));

        clickOn("//a[@href='/view/all/newJob']");
        versionList.add(getText(FOOTER_VERSION));

        clickOn("//img[@alt='Jenkins']");

        clickOn("//a[@href='/asynchPeople/']");
        versionList.add(getText(FOOTER_VERSION));

        clickOn("//a[@href='/view/all/builds']");
        versionList.add(getText(FOOTER_VERSION));

        clickOn("//a[@href='/manage']");
        versionList.add(getText(FOOTER_VERSION));

        clickOn("//a[@href='/me/my-views']");
        versionList.add(getText(FOOTER_VERSION));

        for (int i = 0; i < versionList.size(); i++) {
            Assert.assertEquals(versionList.get(i), "Jenkins 2.440.2");

        }
    }
}

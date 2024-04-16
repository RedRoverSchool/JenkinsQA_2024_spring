package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import javax.swing.text.Element;
import java.util.ArrayList;
import java.util.List;

public class FooterVersionTest extends BaseTest {
    private static final String FOOTER_VERSION = "//button[@type='button']";

    private void clickOn(String xpath) {
        getDriver().findElement(By.xpath(xpath)).click();
    }

    private String getText(String xpath) {
        return getDriver().findElement(By.xpath(xpath)).getText();
    }

    @Test
    public void testCheckVersionOnFooter() {
        List<String> versionList = new ArrayList<>();

        versionList.add(getText(FOOTER_VERSION));

        clickOn("//a[@href='/view/all/newJob']");
        versionList.add(getText(FOOTER_VERSION));
        clickOn("//img[@alt='Jenkins']");

        clickOn("//a[@href='/asynchPeople/']");
        versionList.add(getText(FOOTER_VERSION));

        clickOn("//span[1][normalize-space()='Build History']");
        versionList.add(getText(FOOTER_VERSION));

        clickOn("//span[normalize-space()='Manage Jenkins']");
        versionList.add(getText(FOOTER_VERSION));

        clickOn("//span[normalize-space()='My Views']");
        versionList.add(getText(FOOTER_VERSION));

        for(int i = 0; i < versionList.size(); i++) {
            Assert.assertEquals(versionList.get(i), "Jenkins 2.440.2");
        }
    }
}

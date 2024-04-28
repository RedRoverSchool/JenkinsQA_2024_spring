package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.ProjectUtils;
import school.redrover.runner.TestUtils;

import javax.swing.text.Element;
import java.util.ArrayList;
import java.util.List;

public class FooterVersionTest extends BaseTest {
    private String getText(String xpath) {
        return getDriver().findElement(By.xpath(xpath)).getText();
    }

    @Test
    public void testFooterVersion() {
        String baseUrl = getDriver().getCurrentUrl();
        List<String> urlList = List.of(
                baseUrl + "/view/all/newJob",
                baseUrl + "/asynchPeople/",
                baseUrl + "/view/all/builds",
                baseUrl + "/manage/",
                baseUrl + "/me/my-views/view/all/");

        for (int i = 0; i < urlList.size(); i++) {
            getDriver().switchTo().newWindow(WindowType.TAB).navigate().to(urlList.get(i));

            Assert.assertEquals(getText("//button[@type='button']"), "Jenkins 2.440.2");
        }
    }
}

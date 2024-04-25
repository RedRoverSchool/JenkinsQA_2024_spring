package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PeopleTest extends BaseTest {

    @Test
    private void testSortPeople(){
        for (int i = 0; i < 5; i++) {
            TestUtils.createPeople(this);
            TestUtils.goToMainPage(getDriver());
        }

        getDriver().findElement(By.xpath("//a[@href='/asynchPeople/']")).click();

        List<String> userIDListExpected = getDriver()
                .findElements(By.xpath("//a[@class='jenkins-table__link']")).stream()
                .map(WebElement::getText).collect(Collectors.toList());
        Collections.sort(userIDListExpected, Collections.reverseOrder(String.CASE_INSENSITIVE_ORDER));

        getDriver().findElement(By.xpath("//a[text()='User ID']")).click();
        List<String> userIDListActual = getDriver()
                .findElements(By.xpath("//a[@class='jenkins-table__link']")).stream()
                .map(WebElement::getText).collect(Collectors.toList());
        for (int i = 0; i < userIDListActual.size(); i++) {
            Assert.assertEquals(userIDListActual.get(i), userIDListExpected.get(i));
        }

        Collections.sort(userIDListExpected, String.CASE_INSENSITIVE_ORDER);
        getDriver().findElement(By.xpath("//a[text()='User ID']")).click();
        userIDListActual = getDriver()
                .findElements(By.xpath("//a[@class='jenkins-table__link']")).stream()
                .map(WebElement::getText).collect(Collectors.toList());
        for (int i = 0; i < userIDListActual.size(); i++) {
            Assert.assertEquals(userIDListActual.get(i), userIDListExpected.get(i));
        }
    }
    
}

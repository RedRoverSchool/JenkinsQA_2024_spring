package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;
import java.util.*;
import static school.redrover.runner.TestUtils.goToMainPage;

public class PeopleTest extends BaseTest {
    private void createPeople() {
        final String[] usernames = {
                "johndoe21",
                "janesmith1985",
                "david_lee92",
                "emily.williams",
                "alex_johnson",
                "chris_evans",
                "mary_jones",
                "michael.brown",
                "steve_rogers",
                "lisa_taylor"

        };

        for (String username : usernames) {
            getDriver().findElement(By.linkText("Manage Jenkins")).click();
            getDriver().findElement(By.xpath("//dt[text()='Users']")).click();
            getDriver().findElement(By.xpath("//a[@href='addUser']")).click();
            getDriver().findElement(By.id("username")).clear();
            getDriver().findElement(By.id("username")).sendKeys(username);
            getDriver().findElement(By.name("password1")).sendKeys(username);
            getDriver().findElement(By.name("password2")).sendKeys(username);
            getDriver().findElement(By.name("fullname")).sendKeys(username.replaceAll("\\d", ""));
            getDriver().findElement(By.name("email")).sendKeys(username + "@example.com");
            getDriver().findElement(By.name("Submit")).click();
            goToMainPage(getDriver());
        }
    }

    @Test
    public void testSortPeople() {
        createPeople();

        getDriver().findElement(By.linkText("People")).click();

        //Sort by UserID
        List<WebElement> expectedPeopleLists = getDriver().findElements(By.className("jenkins-table__link"));
        String[] myArray = expectedPeopleLists.stream().map(WebElement::getText).toArray(String[]::new);

        Arrays.sort(myArray, Comparator.reverseOrder());

        getDriver().findElement(By.xpath("//a[normalize-space()=\"User ID\"]")).click();

        List<WebElement> ProvidedPeopleLists = getDriver().findElements(By.className("jenkins-table__link"));
        String[] mySecArray = ProvidedPeopleLists.stream().map(WebElement::getText).toArray(String[]::new);

        for (int i = 0; i < myArray.length && i < mySecArray.length; i++) {
            Assert.assertEquals(mySecArray[i], myArray[i]);
        }

        //Sort by UserName
        WebElement table = getDriver().findElement(By.id("people"));
        List<WebElement> rows = table.findElements(By.tagName("tr"));

        List<String> expectedSecColumnData = new ArrayList<>();
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (!cells.isEmpty()) {
                String cellText = cells.get(2).getText();
                expectedSecColumnData.add(cellText);
            }
        }

        String[] nameArray = expectedSecColumnData.toArray(String[]::new);

        Arrays.sort(nameArray, Comparator.reverseOrder());

        getDriver().findElement(By.xpath("//a[normalize-space()=\"Name\"]")).click();

        List<String> secColumnData = new ArrayList<>();
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (!cells.isEmpty()) {
                String cellText = cells.get(2).getText();
                secColumnData.add(cellText);
            }
        }

        String[] nNameArray = secColumnData.toArray(String[]::new);

        for (int i = 0; i < nNameArray.length && i < nameArray.length; i++) {
            Assert.assertEquals(nNameArray[i], nameArray[i]);
        }
        //Sort by Last Commit Activity
        List<String> expectedThirdColumnData = new ArrayList<>();
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (!cells.isEmpty()) {
                String cellText = cells.get(3).getText();
                expectedThirdColumnData.add(cellText);
            }
        }

        expectedThirdColumnData.sort(Collections.reverseOrder());

        getDriver().findElement(By.xpath("//th[@initialsortdir=\"up\"]//a[@class=\"sortheader\"]")).click();

        List<String> thirdColumnData = new ArrayList<>();
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (!cells.isEmpty()) {
                String cellText = cells.get(3).getText();
                thirdColumnData.add(cellText);
            }
        }

        for (int i = 0; i < thirdColumnData.size() && i < expectedThirdColumnData.size(); i++) {
            Assert.assertEquals(thirdColumnData.get(i), expectedThirdColumnData.get(i));
        }
        //Sort by ON/OFF
        List<String> expectedFourthColumnData = new ArrayList<>();
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (!cells.isEmpty()) {
                String cellText = cells.get(4).getText();
                expectedFourthColumnData.add(cellText);
            }
        }

        expectedFourthColumnData.sort(Comparator.reverseOrder());

        getDriver().findElement(By.xpath("//a[normalize-space()=\"On\"]")).click();

        List<String> fourthColumnData = new ArrayList<>();
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (!cells.isEmpty()) {
                String cellText = cells.get(4).getText();
                fourthColumnData.add(cellText);
            }
        }

        for (int i = 0; i < fourthColumnData.size() && i < expectedFourthColumnData.size(); i++) {
            Assert.assertEquals(fourthColumnData.get(i), expectedFourthColumnData.get(i));
        }
    }

    @Test
    public void testIconSizeChangeToSmall() {
        final Dimension actualIconSize = new HomePage(getDriver())
                .clickPeopleButton()
                .clickSmallIconButton()
                .getUserIconSize();

        Assert.assertEquals(actualIconSize, new Dimension(16, 16));
    }

    @Test
    public void testIconSizeChangeToMedium() {
        final Dimension actualIconSize = new HomePage(getDriver())
                .clickPeopleButton()
                .clickMediumIconButton()
                .getUserIconSize();

        Assert.assertEquals(actualIconSize, new Dimension(20, 20));
    }

    @Test
    public void testIconSizeChangeToLarge() {
        final Dimension actualIconSize = new HomePage(getDriver())
                .clickPeopleButton()
                .clickLargeIconButton()
                .getUserIconSize();

        Assert.assertEquals(actualIconSize, new Dimension(24, 24));
    }
}
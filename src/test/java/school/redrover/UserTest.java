package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserTest extends BaseTest {

    public String randomString() {
        return UUID.randomUUID()
                .toString()
                .substring(0, 7);
    }

    public String randomEmail() {
        return randomString() + "@" + randomString() + ".com";
    }

    public void createUser() {
        final String password = randomString();
        getDriver().findElement(By.cssSelector("[href='addUser']")).click();
        getDriver().findElement(By.id("username")).sendKeys(randomString());
        getDriver().findElement(By.name("password1")).sendKeys(password);
        getDriver().findElement(By.name("password2")).sendKeys(password);
        getDriver().findElement(By.name("fullname")).sendKeys(randomString());
        getDriver().findElement(By.name("email")).sendKeys(randomEmail());
        getDriver().findElement(By.name("Submit")).click();
    }

    @Test
    public void testUsersSortingByName() {

        getDriver().findElement(By.cssSelector("[href='/manage']")).click();
        getDriver().findElement(By.cssSelector("[href='securityRealm/']")).click();

        createUser();
        createUser();
        createUser();
        createUser();

        getDriver().findElement(By.cssSelector("thead th:nth-child(3)>a")).click();
        getDriver().findElement(By.cssSelector("thead th:nth-child(3)>a")).click();

        List<WebElement> elements = getDriver().findElements(By.cssSelector("tr>td:nth-child(3)"));
        List<String> names = new ArrayList<>();
        for (WebElement element : elements) {
            names.add(element.getText());
        }

        Assert.assertEquals(names, names.stream().sorted().collect(Collectors.toList()));
    }

    @Test
    public void testUsersSortingByUserID() {

        getDriver().findElement(By.cssSelector("[href='/manage']")).click();
        getDriver().findElement(By.cssSelector("[href='securityRealm/']")).click();

        createUser();
        createUser();
        createUser();
        createUser();

        getDriver().findElement(By.cssSelector("thead th:nth-child(2)>a")).click();
        getDriver().findElement(By.cssSelector("thead th:nth-child(2)>a")).click();

        List<WebElement> elements = getDriver().findElements(By.cssSelector("tr>td:nth-child(2)"));

        List<String> names = elements
                .stream()
                .map(WebElement::getText)
                .toList();

        Assert.assertEquals(names, names.stream().sorted().collect(Collectors.toList()));
    }
}
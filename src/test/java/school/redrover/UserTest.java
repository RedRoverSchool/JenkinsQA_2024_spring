package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;

public class UserTest extends BaseTest {
    @Test
    public void testDeleteUsingMenuDropdownChevron() {
        final String dataTest = "one";

        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
        getDriver().findElement(By.xpath("//dt[text() = 'Users']")).click();
        getDriver().findElement(By.xpath("//*[@href='addUser']")).click();
        getDriver().findElement(By.id("username")).sendKeys(dataTest);
        getDriver().findElement(By.name("password1")).sendKeys(dataTest);
        getDriver().findElement(By.name("password2")).sendKeys(dataTest);
        getDriver().findElement(By.name("fullname")).sendKeys(dataTest);
        getDriver().findElement(By.name("email")).sendKeys(dataTest + "@gmail.com");
        getDriver().findElement(By.name("Submit")).click();


        getDriver().findElement(
                By.xpath("//a[(@href='user/" + dataTest + "/')]")).click();

        getDriver().findElement(By.xpath("//button[@href='/user/"+dataTest+"/doDelete']")).click();

        getDriver().findElement(By.xpath("//button[@data-id='ok']")).click();
        getDriver().findElement(By.xpath("//a[@href='/asynchPeople/']")).click();

        List<WebElement> elementList = getDriver().findElements(By.xpath("//*[@id='people']//tbody"));
        List<String> resultList = elementList.stream().map(WebElement::getText).toList();

        Assert.assertFalse(resultList.contains(dataTest));
    }
}

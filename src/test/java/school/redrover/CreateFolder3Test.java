package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class CreateFolder3Test extends BaseTest {

    @Test
    public void testSpecialCharactersNameFolder(){
        getDriver().findElement(By.xpath("//a[.='New Item']")).click();
        getDriver().findElement(By.id("j-add-item-type-nested-projects")).click();
        getDriver().findElement(By.id("name")).sendKeys("Fold%erdate");
        WebElement errorMassage = getDriver().findElement(By.id("itemname-invalid"));

        assertEquals(errorMassage.getText(),"» ‘%’ is an unsafe character");
        assertTrue(errorMassage.isDisplayed());
    }
}

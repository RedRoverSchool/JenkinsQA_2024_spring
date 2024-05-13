package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class CreateFolder3Test extends BaseTest {

    @Test
    public void testSpecialCharactersNameFolder(){
        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName("Fold%erdate")
                .getErrorMessage();

        assertEquals(errorMessage,"» ‘%’ is an unsafe character");
    }
}

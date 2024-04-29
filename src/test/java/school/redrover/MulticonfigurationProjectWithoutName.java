package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class MulticonfigurationProjectWithoutName extends BaseTest {
    private final static String ALLERT_NAME_REQUIRED = "This field cannot be empty, please enter a valid name";

    @Test
    public void testCreateMulticonfigurationProject(){
        getDriver().findElement(By.xpath("//*[@id=\"tasks\"]/div[1]/span/a")).click(); //NewItem

        getDriver().findElement(By.cssSelector("ul > li.hudson_matrix_MatrixProject")).click(); //Multi-configuration project

        Assert.assertTrue(getDriver().findElement(By.id("itemname-required")).getText().contains(ALLERT_NAME_REQUIRED));
    }
}

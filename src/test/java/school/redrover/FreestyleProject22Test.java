package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import static school.redrover.runner.TestUtils.*;

public class FreestyleProject22Test extends BaseTest {
    private static final String PROJECT_NAME = "Freestyle_first";
    private static final String PROJECT_DESCRIPTION = "my new build";

    @Test
    public void testEditDescription(){
        final String editDescribe = "Create one more build apps";

        createNewItem(this, PROJECT_NAME, Item.FREESTYLE_PROJECT);
        getDriver().findElement(By.linkText(PROJECT_NAME)).click();
        getDriver().findElement(By.linkText("Configure")).click();
        getDriver().findElement(By.xpath("//*[@id=\"main-panel\"]/form/div[1]/div[2]/div/div[2]/textarea")).sendKeys(PROJECT_DESCRIPTION);
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.xpath("//*[@id='jenkins-home-link']")).click();

        getDriver().findElement(By.xpath("//*[@class='jenkins-table__link model-link inside']")).click();
        getDriver().findElement(By.xpath("//*[@id='description-link']")).click();
        getDriver().findElement(By.xpath("//*[@name='description']")).clear();
        getDriver().findElement(By.xpath("//*[@name='description']")).sendKeys(editDescribe);
        getDriver().findElement(By.xpath("//*[@class='jenkins-button jenkins-button--primary ']")).click();

        Assert.assertTrue(getDriver().findElement(By.id("description")).getText().contains(editDescribe));
    }

}

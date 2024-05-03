package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class ManageJenkins3Test extends BaseTest {

    private void goToManageJenkins() {
        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
    }

    @Test
    public void testUserSeeTheSearchSettingsField() {
        goToManageJenkins();

        Assert.assertTrue(getDriver().findElement(By.className("jenkins-search__input")).isDisplayed());
    }
}

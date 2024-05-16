package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;


public class FreeStyleProject25Test extends BaseTest {
    @Test
    public void testCreate(){
        List<String> jobList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName("StasM")
                .selectFreestyleAndClickOk()
                .clickSaveButton()
                .clickLogo()
                .getItemList();

        Assert.assertTrue(jobList.contains("StasM"));
    }

       @Test(dependsOnMethods = "testCreate")
        public void testCreateFolder(){
            getDriver().findElement(By.linkText("New Item")).click();
            getDriver().findElement(By.id("name")).sendKeys("Folder_1");
            getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
            getDriver().findElement(By.id("ok-button")).click();
            getDriver().findElement(By.name("Submit")).click();
            TestUtils.returnToDashBoard(this);
            Assert.assertEquals(
                    getDriver().findElement(By.xpath("//*[@id=\"job_Folder_1\"]/td[3]/a/span")).getText(),
                    "Folder_1");
    }
}





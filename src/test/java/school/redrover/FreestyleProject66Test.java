import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class FreestyleProject66Test extends BaseTest {


    @Test
    public void testCreateProject() {

        getDriver().findElement(By.xpath("//*[@id='tasks']/div[1]/span/a")).click();
        getDriver().findElement(By.xpath("//*[@id='name']")).sendKeys("NGV");
        getDriver().findElement(By.xpath("//*[@id='j-add-item-type-standalone-projects']/ul/li[1]/label/span")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
        TestUtils.returnToDashBoard(this);

        String actualProjectName = getDriver().findElement(By.xpath("//*[@id='job_NGV']/td[3]/a/span")).getText();
        Assert.assertEquals(actualProjectName, "NGV");
    }

    @Test(dependsOnMethods ="testCreateProject")
    public void testCreatefolder() {

            getDriver().findElement(By.linkText("New Item")).click();
            getDriver().findElement(By.id("name")).sendKeys("Folder_1");
            getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
            getDriver().findElement(By.id("ok-button")).click();
            getDriver().findElement(By.name("Submit")).click();
            TestUtils.returnToDashBoard(this);

            Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id=\"job_Folder_1\"]/td[3]/a/span")).getText(),
                    "Folder_1");

        }

    }

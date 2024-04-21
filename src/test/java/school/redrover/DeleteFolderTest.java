package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class DeleteFolderTest extends BaseTest {
    @Test
    public void testCreateFolder(){
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys("Folder_1");
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
    }    public void testDeleteFolder (){
        getDriver().findElement(By.className("task-link  confirmation-link")).click();
        getDriver().findElement(By.className("jenkins-button jenkins-button--primary")).click();

        Assert.assertFalse(getDriver().findElement(By.name("Welcome to Jenkins!")).isDisplayed());
    }
}
